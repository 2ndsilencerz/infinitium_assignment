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
    public void testSendSuccessIDRtoUSD() throws Exception {
        System.out.println("Send IDR to USD");
        SendRequest request = new SendRequest();
        request.setAccountID(1L);
        request.setAmount(1D);
        request.setCurrency(Currency.USD);

        SendResponse response = mainService.sendMoney(request);
        assert response.getStatus().equals(SendStatus.SUCCESS);
        assert response.getAccount().getAmount().equals(985000D);
    }

    @Test
    public void testSendSuccessUSDtoIDR() throws Exception {
        System.out.println("Send USD to IDR");
        SendRequest request = new SendRequest();
        request.setAccountID(2L);
        request.setAmount(15000D);
        request.setCurrency(Currency.IDR);

        SendResponse response = mainService.sendMoney(request);
        assert response.getStatus().equals(SendStatus.SUCCESS);
        assert response.getAccount().getAmount().equals(1999D);
    }

    @Test
    public void testSendSuccessIDRtoMYR() throws Exception {
        System.out.println("Send IDR to MYR");
        SendRequest request = new SendRequest();
        request.setAccountID(1L);
        request.setAmount(5D);
        request.setCurrency(Currency.MYR);

        SendResponse response = mainService.sendMoney(request);
        assert response.getStatus().equals(SendStatus.SUCCESS);
        assert response.getAccount().getAmount().equals(985000D);
    }

    @Test
    public void testSendSuccessMYRtoIDR() throws Exception {
        System.out.println("Send MYR to IDR");
        SendRequest request = new SendRequest();
        request.setAccountID(3L);
        request.setAmount(15000D);
        request.setCurrency(Currency.IDR);

        SendResponse response = mainService.sendMoney(request);
        assert response.getStatus().equals(SendStatus.SUCCESS);
        assert response.getAccount().getAmount().equals(4995D);
    }

    @Test
    public void testSendSuccessUSDtoMYR() throws Exception {
        System.out.println("Send USD to MYR");
        SendRequest request = new SendRequest();
        request.setAccountID(2L);
        request.setAmount(5D);
        request.setCurrency(Currency.MYR);

        SendResponse response = mainService.sendMoney(request);
        assert response.getStatus().equals(SendStatus.SUCCESS);
        assert response.getAccount().getAmount().equals(1999D);
    }

    @Test
    public void testSendSuccessMYRtoUSD() throws Exception {
        System.out.println("Send MYR to USD");
        SendRequest request = new SendRequest();
        request.setAccountID(3L);
        request.setAmount(1D);
        request.setCurrency(Currency.USD);

        SendResponse response = mainService.sendMoney(request);
        assert response.getStatus().equals(SendStatus.SUCCESS);
        assert response.getAccount().getAmount().equals(4995D);
    }

    @Test
    public void testSendFailAccountNotExist() throws Exception {
        System.out.println("Failed account not exist");
        SendRequest request = new SendRequest();
        request.setAccountID(4L);
        request.setAmount(1D);
        request.setCurrency(Currency.USD);

        SendResponse response = mainService.sendMoney(request);
        assert response.getStatus().equals(SendStatus.FAILED);
        assert response.getMessage().equals("Sender account not exist");
    }

    @Test
    public void testSendFailInsufficientAmount() throws Exception {
        System.out.println("Failed amount insufficient");
        SendRequest request = new SendRequest();
        request.setAccountID(1L);
        request.setAmount(1000001D);
        request.setCurrency(Currency.IDR);

        SendResponse response = mainService.sendMoney(request);
        assert response.getStatus().equals(SendStatus.FAILED);
        assert response.getMessage().equals("Amount insufficient to do this transaction");
    }
}