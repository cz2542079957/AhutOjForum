package com.ahutoj.aspect;

import com.ahutoj.AhutOjForumApplication;
import com.ahutoj.utils.MoreTransaction;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.beans.beancontext.BeanContext;
import java.util.Stack;

@Aspect
@Component
public class TransactionAop {
    @Pointcut("@annotation(com.ahutoj.utils.MoreTransaction)")
    public void MoreTransaction() {
    }

    @Pointcut("execution(* com.ahutoj.controller.*.*(..))")
    public void excudeController() {
    }

    @Around(value = "MoreTransaction()&&excudeController()&&@annotation(annotation)")
    public Object twiceAsOld(ProceedingJoinPoint thisJoinPoint, MoreTransaction annotation) throws Throwable {
        Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack = new Stack<>();
        Stack<TransactionStatus> transactionStatuStack = new Stack<>();

        try {
            if (!openTransaction(dataSourceTransactionManagerStack, transactionStatuStack, annotation)) {
                return null;
            }
            Object ret = thisJoinPoint.proceed();
            commit(dataSourceTransactionManagerStack, transactionStatuStack);
            return ret;
        } catch (Throwable e) {
            rollback(dataSourceTransactionManagerStack, transactionStatuStack);
            throw e;
        }
    }

    private boolean openTransaction(Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack,
                                    Stack<TransactionStatus> transactionStatuStack,MoreTransaction multiTransactional) {

        String[] transactionMangerNames = multiTransactional.value();
        if (multiTransactional.value().length == 0) {
            return false;
        }
        for (String beanName : transactionMangerNames) {
            DataSourceTransactionManager dataSourceTransactionManager =(DataSourceTransactionManager) AhutOjForumApplication.ac.getBean(beanName);
            TransactionStatus transactionStatus = dataSourceTransactionManager
                    .getTransaction(new DefaultTransactionDefinition());
            transactionStatuStack.push(transactionStatus);
            dataSourceTransactionManagerStack.push(dataSourceTransactionManager);
        }
        return true;
    }

    private void commit(Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack,
                        Stack<TransactionStatus> transactionStatuStack) {
        while (!dataSourceTransactionManagerStack.isEmpty()) {
            dataSourceTransactionManagerStack.pop().commit(transactionStatuStack.pop());
        }
    }

    private void rollback(Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack,
                          Stack<TransactionStatus> transactionStatuStack) {
        while (!dataSourceTransactionManagerStack.isEmpty()) {
            dataSourceTransactionManagerStack.pop().rollback(transactionStatuStack.pop());
        }
    }
}