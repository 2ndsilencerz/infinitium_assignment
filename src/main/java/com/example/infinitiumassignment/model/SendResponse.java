package com.example.infinitiumassignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendResponse {

    Account account;
    SendStatus status;
    String message;
}
