package com.easycommerce.response;

import lombok.*;

import java.util.Date;

@Getter
@AllArgsConstructor
public class APIResponse {

    private String message;
    private Date timeStamp;
}
