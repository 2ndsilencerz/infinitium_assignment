package com.example.infinitiumassignment.utility;

import com.example.infinitiumassignment.model.Account;
import com.example.infinitiumassignment.service.MainService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utilities {

    public static Map<Long, Account> getAccounts() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        FileReader fileDB = new FileReader(MainService.dbFileLocation);
        List<?> accountList = objectMapper.readValue(fileDB, List.class);

        Map<Long, Account> accountMap = new HashMap<>();
        for (Object object : accountList) {
            Account account = objectMapper.convertValue(object, Account.class);
            accountMap.put(account.getAccountID(), account);
        }

        fileDB.close();
        return accountMap;
    }

    public static boolean saveData(Map<Long, Account> accountMap) throws Exception {

        // default false
        boolean success;
        ObjectMapper objectMapper = new ObjectMapper();

        List<Account> accountList = accountMap.values().stream().toList();

        // recreate new file to replace the value
        File newFile = new File(MainService.dbFileLocation);
        newFile.delete();
        newFile.createNewFile();

        // rewrite the value
        FileOutputStream outputStream = new FileOutputStream(newFile.getAbsolutePath());
        byte[] strBytes = objectMapper.writeValueAsBytes(accountList);
        outputStream.write(strBytes);
        outputStream.close();

        success = true;

        return success;
    }
}
