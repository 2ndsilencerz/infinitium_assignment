package com.example.infinitiumassignment.service;

import com.example.infinitiumassignment.model.Account;
import com.example.infinitiumassignment.model.SendRequest;
import com.example.infinitiumassignment.model.SendResponse;
import com.example.infinitiumassignment.model.SendStatus;
import com.example.infinitiumassignment.utility.Utilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MainService {

    final ObjectMapper objectMapper;

    public static String dbFileLocation = "./account_list.json";

    public MainService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public SendResponse sendMoney(SendRequest request) throws Exception {

        SendResponse response = new SendResponse();
        Map<Long, Account> accountMap = Utilities.getAccounts();

        // get account data
        Account senderAccount = accountMap.getOrDefault(request.getAccountID(), null);

        if (senderAccount == null) {
            response.setAccount(null);
            response.setStatus(SendStatus.FAILED);
            response.setMessage("Sender account not exist");
            return response;
        }

        Double currentBalance = senderAccount.getAmount();
        // calculate converted amount with conversion
        Double sendAmount = request.getCurrency().convert(request.getAmount(), senderAccount.getCurrency());

        if (currentBalance < sendAmount) {
            response.setAccount(senderAccount);
            response.setStatus(SendStatus.FAILED);
            response.setMessage("Amount insufficient to do this transaction");
            return response;
        } else {
            // set new data to sender account
            senderAccount.setAmount(currentBalance - sendAmount);
            response.setAccount(senderAccount);
            response.setStatus(SendStatus.SUCCESS);
        }

        return response;
    }
}
