package com.easycommerce.payment;

import com.easycommerce.response.APIResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/orders")
public class PaymentController {

    private final StripeService stripeService;

    @GetMapping("/{orderId}/pay/stripe")
    public ResponseEntity<APIResponse> payByStripe(@PathVariable Long orderId) throws StripeException {
        Session session = stripeService.createCheckoutSession(orderId);
        return new ResponseEntity<>(new APIResponse(session.getUrl(), new Date()), HttpStatus.SEE_OTHER);
    }

    @GetMapping("/{orderId}/pay/verify")
    public ResponseEntity<Boolean> verifyPayment(@PathVariable Long orderId) throws StripeException {
        boolean isPaid = stripeService.verifyOrderPaymentStatus(orderId);
        return new ResponseEntity<>(isPaid, HttpStatus.OK);
    }
}
