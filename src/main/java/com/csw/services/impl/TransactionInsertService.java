package com.csw.services.impl;

import com.csw.services.interfaces.ITransactionInsertService;
import com.csw.transaction.util.CreateTransactionResponseDocument;

public class TransactionInsertService implements ITransactionInsertService {
    public String transactionInsertResponse(String type, String owner, String data) {
        return CreateTransactionResponseDocument.createTransactionInsertResponseDocument(data,type,owner);
    }
}
