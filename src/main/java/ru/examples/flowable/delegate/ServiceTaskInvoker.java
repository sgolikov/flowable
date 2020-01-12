package ru.examples.flowable.delegate;

import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.cfg.TransactionState;
import org.flowable.common.engine.impl.context.Context;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@Slf4j
public class ServiceTaskInvoker implements JavaDelegate {

    public static CountDownLatch latch = new CountDownLatch(1);

    public void execute(DelegateExecution delegateExecution) {
        Context.getTransactionContext().addTransactionListener(TransactionState.COMMITTED,
                commandContext -> {
                    try {
                        log.info("{} Start execute ServiceTaskInvoker", delegateExecution.getId());
                        latch.countDown();

                        Thread.sleep(5000);
                        log.info("{} Finish execute ServiceTaskInvoker with OK-Result", delegateExecution.getId());
                    } catch (Exception ex) {
                        log.info("{} Finish execute ServiceTaskInvoker with Error-Result", delegateExecution.getId());
                    }
                });
    }
}
