package com.csw.services.impl;

import com.csw.services.interfaces.ITransactionDeleteService;
import com.csw.transaction.util.CreateTransactionResponseDocument;

public class TransactionDeleteService implements ITransactionDeleteService {
    public String transactionDeleteResponse(String id) {
        String result = CreateTransactionResponseDocument.createTransactionDeleteResponseDocument(id);
        return result;
    }
}
