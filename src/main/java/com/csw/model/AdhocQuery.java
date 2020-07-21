package com.csw.model;

public class AdhocQuery extends RegistryObject {
    private QueryExpression queryExpression;

    public QueryExpression getQueryExpression() {
        return queryExpression;
    }

    public void setQueryExpression(QueryExpression queryExpression) {
        this.queryExpression = queryExpression;
    }
}
