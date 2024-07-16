package com.example.infinitiumassignment.service;

import com.example.infinitiumassignment.model.*;
import com.example.infinitiumassignment.utility.Utilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class MainServiceTest {

    @Autowired
    MainService mainService;

    Map<Long, Account> accountBefore;

    @BeforeEach
    public void loadInitialAccounts() throws Exception {
        accountBefore = Utilities.getAccounts();
    }

    @Test
    public void testSendSuccessIDRToUSD() throws Exception {
        System.out.println("IDR To USD");
        SendRequest request = new SendRequest();
        request.setAccountIDSender(1L);
        request.setAmount(1D);
        request.setCurrency(Currency.USD);
        request.setAccountIDReceiver(2L);

        SendResponse response = mainService.sendMoney(request);
        assert response.getStatus().equals(SendStatus.SUCCESS);

        validate(request);
    }

    @Test
    public void testSendSuccessUSDToIDR() throws Exception {
        System.out.println("USD to IDR");
        SendRequest request = new SendRequest();
        request.setAccountIDSender(2L);
        request.setAmount(15000D);
        request.setCurrency(Currency.IDR);
        request.setAccountIDReceiver(1L);

        SendResponse response = mainService.sendMoney(request);
        assert response.getStatus().equals(SendStatus.SUCCESS);

        validate(request);
    }

    @Test
    public void testSendSuccessIDRToMYR() throws Exception {
        System.out.println("IDR to MYR");
        SendRequest request = new SendRequest();
        request.setAccountIDSender(1L);
        request.setAmount(5D);
        request.setCurrency(Currency.MYR);
        request.setAccountIDReceiver(3L);

        SendResponse response = mainService.sendMoney(request);
        assert response.getStatus().equals(SendStatus.SUCCESS);

        validate(request);
    }

    @Test
    public void testSendSuccessMYRToIDR() throws Exception {
        System.out.println("MYR to IDR");
        SendRequest request = new SendRequest();
        request.setAccountIDSender(3L);
        request.setAmount(15000D);
        request.setCurrency(Currency.IDR);
        request.setAccountIDReceiver(1L);

        SendResponse response = mainService.sendMoney(request);
        assert response.getStatus().equals(SendStatus.SUCCESS);

        validate(request);
    }

    @Test
    public void testSendSuccessUSDToMYR() throws Exception {
        System.out.println("USD to MYR");
        SendRequest request = new SendRequest();
        request.setAccountIDSender(2L);
        request.setAmount(5D);
        request.setCurrency(Currency.MYR);
        request.setAccountIDReceiver(3L);

        SendResponse response = mainService.sendMoney(request);
        assert response.getStatus().equals(SendStatus.SUCCESS);

        validate(request);
    }

    @Test
    public void testSendSuccessMYRtoUSD() throws Exception {
        System.out.println("MYR to USD");
        SendRequest request = new SendRequest();
        request.setAccountIDSender(3L);
        request.setAmount(1D);
        request.setCurrency(Currency.USD);
        request.setAccountIDReceiver(2L);

        SendResponse response = mainService.sendMoney(request);
        assert response.getStatus().equals(SendStatus.SUCCESS);

        validate(request);
    }

//    @Test
//    public void testSendFailWrongCurrency() throws Exception {
//        SendRequest request = new SendRequest();
//        request.setAccountIDSender(3L);
//        request.setAmount(1F);
//        request.setCurrency(Currency.valueOf("ASD"));
//        request.setAccountIDReceiver(1L);
//
//        SendResponse response = mainService.sendMoney(request);
//        assert response.getStatus().equals(SendStatus.FAILED);
//
////        validate(request);
//    }

    @Test
    public void testSendFailAmountInsufficient() throws Exception {
        SendRequest request = new SendRequest();
        request.setAccountIDSender(1L);
        request.setAmount(1000001D);
        request.setCurrency(Currency.IDR);
        request.setAccountIDReceiver(2L);

        SendResponse response = mainService.sendMoney(request);
        assert response.getStatus().equals(SendStatus.FAILED);
    }

    @Test
    public void testSendFailSenderNotExist() throws Exception {
        SendRequest request = new SendRequest();
        request.setAccountIDSender(4L);
        request.setAmount(1000001D);
        request.setCurrency(Currency.IDR);
        request.setAccountIDReceiver(2L);

        SendResponse response = mainService.sendMoney(request);
        assert response.getStatus().equals(SendStatus.FAILED);
    }

    @Test
    public void testSendFailReceiverNotExist() throws Exception {
        SendRequest request = new SendRequest();
        request.setAccountIDSender(1L);
        request.setAmount(1000001D);
        request.setCurrency(Currency.IDR);
        request.setAccountIDReceiver(4L);

        SendResponse response = mainService.sendMoney(request);
        assert response.getStatus().equals(SendStatus.FAILED);
    }

    private void validate(SendRequest request) throws Exception {
        Map<Long, Account> accountAfter = Utilities.getAccounts();
        Account oldSenderAccount = accountBefore.get(request.getAccountIDSender());
        System.out.println("old sender account: " + oldSenderAccount);
        Account newSenderAccount = accountAfter.get(request.getAccountIDSender());
        System.out.println("new sender account: " + newSenderAccount);
        Account oldReceiverAccount = accountBefore.get(request.getAccountIDReceiver());
        System.out.println("old receiver account: " + oldReceiverAccount);
        Account newReceiverAccount = accountAfter.get(request.getAccountIDReceiver());
        System.out.println("new receiver account: " + newReceiverAccount);

        System.out.println();

        assert newSenderAccount.getAmount() ==
                oldSenderAccount.getAmount() - request.getCurrency().convert(request.getAmount(), oldSenderAccount.getCurrency());
        assert newReceiverAccount.getAmount() ==
                oldReceiverAccount.getAmount() + request.getCurrency().convert(request.getAmount(), oldReceiverAccount.getCurrency());
    }
}