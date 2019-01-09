package com.msjf.finance.mcs.modules.utils;


import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionManage {
    public TransactionManage() {
    }

    public TransactionStatus newTransaction() throws Exception {
        try {
            PlatformTransactionManager transactionManager = this.getTransactionManager();
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(3);
            return transactionManager.getTransaction(def);
        } catch (Exception var3) {
            var3.printStackTrace();
            String errorDetail = "获取一个新的事务失败";
            System.out.println(errorDetail);
            throw new Exception(errorDetail);
        }
    }

    private PlatformTransactionManager getTransactionManager() throws Exception {
        try {
            return (PlatformTransactionManager)SpringContextUtil.getBean("transactionManager");
        } catch (Exception var2) {
//            LogUtil.error("获取新事务失败");
            String errDetail = "获取事务管理器，【" + "transactionManager" + "】失败";
            throw new Exception(errDetail);
        }
    }

    public void commit(TransactionStatus status) throws Exception {
        PlatformTransactionManager transactionManager = this.getTransactionManager();

        try {
            transactionManager.commit(status);
            status.flush();
        } catch (Exception var4) {
//            LogUtil.error("手动提交事务失败");
            throw new Exception("手动提交事务失败");
        }
    }
    public void rollback(TransactionStatus status) throws Exception {
        PlatformTransactionManager transactionManager = getTransactionManager();
        try {
            transactionManager.rollback(status);
            status.flush();
        } catch (Exception e) {
//            LogUtil.error("手动提交事务失败");
            throw new Exception("手动提交事务失败");
        }
    }
}