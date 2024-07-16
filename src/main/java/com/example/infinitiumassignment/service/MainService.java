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

        Account senderAccount = accountMap.getOrDefault(request.getAccountIDSender(), null);
        Account receiverAccount = accountMap.getOrDefault(request.getAccountIDReceiver(), null);

        if (senderAccount == null) {
            response.setAccount(null);
            response.setStatus(SendStatus.FAILED);
            response.setMessage("Sender account not exist");
            return response;
        }

        if (receiverAccount == null) {
            response.setAccount(senderAccount);
            response.setStatus(SendStatus.FAILED);
            response.setMessage("Receiver account not exist");
            return response;
        }

        Double currentBalance = senderAccount.getAmount();
        Double sendAmountInSenderCurrency = request.getCurrency().convert(request.getAmount(), senderAccount.getCurrency());
        Double sendAmountInReceiverCurrency = request.getCurrency().convert(request.getAmount(), receiverAccount.getCurrency());

        if (currentBalance < sendAmountInSenderCurrency) {
            response.setAccount(senderAccount);
            response.setStatus(SendStatus.FAILED);
            response.setMessage("Amount not sufficient to do this transaction");
            return response;
        } else {
            // set new data to sender account
            senderAccount.setAmount(senderAccount.getAmount() - sendAmountInSenderCurrency);
            // set new data to receiver account
            receiverAccount.setAmount(receiverAccount.getAmount() + sendAmountInReceiverCurrency);

            response.setAccount(senderAccount);
            response.setStatus(SendStatus.SUCCESS);
        }

        accountMap.replace(senderAccount.getAccountID(), senderAccount);
        accountMap.replace(receiverAccount.getAccountID(), receiverAccount);

        Utilities.saveData(accountMap);

        return response;
    }
}
