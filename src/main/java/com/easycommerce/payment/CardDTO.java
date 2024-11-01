package com.easycommerce.payment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CardDTO {

    private String currency;
    private String number;
    private String expMonth;
    private String expYear;
    private String cvc;
}
