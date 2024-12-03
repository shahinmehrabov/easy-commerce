package com.easycommerce.payment;

import com.easycommerce.exception.APIException;
import com.easycommerce.exception.ResourceNotFoundException;
import com.easycommerce.order.Order;
import com.easycommerce.order.OrderRepository;
import com.easycommerce.product.Product;
import com.stripe.Stripe;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StripeService {

    @Value("${stripe.api.secret-key}")
    private String stripeApiKey;

    private final OrderRepository orderRepository;

    @PostConstruct
    public void init(){
        Stripe.apiKey = stripeApiKey;
    }

    public Session createCheckoutSession(Long orderId) throws StripeException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        if (order.isPaid())
            throw new APIException("The Order has already been paid for");

        String verifyPaymentURL = String.format("http://localhost:8080/api/user/orders/%s/pay/verify", orderId);
        String ordersURL = "http://localhost:8080/api/user/orders";
        List<SessionCreateParams.LineItem> lineItems = order.getOrderItems().stream()
                .map(orderItem -> {
                    Product product = orderItem.getProduct();
                    SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData
                            .builder()
                            .setName(product.getName())
                            .setDescription(product.getDescription())
                            .build();
                    SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData
                            .builder()
                            .setProductData(productData)
                            .setCurrency("pln")
                            .setUnitAmount((long) (product.getPrice() * 100))
                            .build();
                    return SessionCreateParams.LineItem
                            .builder()
                            .setPriceData(priceData)
                            .setQuantity(Long.valueOf(orderItem.getQuantity()))
                            .build();
                })
                .toList();

        SessionCreateParams params = SessionCreateParams
                .builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(verifyPaymentURL)
                .setCancelUrl(ordersURL)
                .addAllLineItem(lineItems)
                .build();

        Session session = Session.create(params);
        order.setStripeSessionId(session.getId());
        orderRepository.save(order);

        return session;
    }

    public boolean verifyOrderPaymentStatus(Long orderId) throws StripeException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        try {
            Session session = Session.retrieve(order.getStripeSessionId());
            if (session.getPaymentStatus().equalsIgnoreCase("paid")) {
                order.setPaid(true);
                order.setPaymentDate(LocalDateTime.now());
            } else
                order.setPaid(false);

            orderRepository.save(order);
            return order.isPaid();
        } catch (InvalidRequestException e) {
            return false;
        }
    }
}
