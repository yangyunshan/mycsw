package com.csw.services.impl;

import com.csw.services.interfaces.ITransactionUpdateService;
import com.csw.transaction.util.CreateTransactionResponseDocument;

public class TransactionUpdateService implements ITransactionUpdateService {
    public String transactionUpdateResponse(String id, String type, String owner, String information) {
        return CreateTransactionResponseDocument.createTransactionUpdateResponseDocument(id,information,type,owner);
    }
}
