package com.nomagic.uml2.transaction;

public interface TransactionManager {
    void addTransactionCommitListener(TransactionCommitListener listener);
    void addTransactionCommitListenerIncludingUndoAndRedo(TransactionCommitListener listener);
    void addTransactionCommitListenerForExecute(TransactionCommitListener listener);
    void removeTransactionCommitListener(TransactionCommitListener listener);
}
