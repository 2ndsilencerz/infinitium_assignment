package com.example.infinitiumassignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendRequest {

    Long accountIDSender;
    Double amount;
    Currency currency;
    Long accountIDReceiver;
}
