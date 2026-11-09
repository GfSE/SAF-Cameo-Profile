package com.nomagic.uml2.transaction;

import java.beans.PropertyChangeEvent;
import java.util.Collection;

public interface TransactionCommitListener {
    Runnable transactionCommited(Collection<PropertyChangeEvent> events);
    default void allTransactionsCommitted() {}
}
