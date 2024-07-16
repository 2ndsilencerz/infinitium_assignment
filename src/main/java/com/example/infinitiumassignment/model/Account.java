package com.example.infinitiumassignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    Long accountID;
    Double amount;
    Currency currency;
}
