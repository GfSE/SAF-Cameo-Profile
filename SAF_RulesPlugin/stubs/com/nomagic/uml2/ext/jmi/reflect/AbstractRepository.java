package com.nomagic.uml2.ext.jmi.reflect;

import com.nomagic.uml2.transaction.TransactionManager;

public abstract class AbstractRepository {
    public abstract TransactionManager getTransactionManager();
}
