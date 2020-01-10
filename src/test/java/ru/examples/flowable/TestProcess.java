package ru.examples.flowable;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.runtime.Execution;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.examples.flowable.delegate.ServiceTaskInvoker;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestProcessConfig.class)
@Slf4j
public class TestProcess {
	@Autowired
	private ProcessEngine processEngine;

	@Before
	public void deploy() {
		processEngine.getRepositoryService().createDeployment()
			.addClasspathResource("TestProcessAsync.bpmn20.xml")
			.deploy();
	}

	@Test
	public void testAsyncFlowableOptimisticLockingException() throws InterruptedException {
		String processId = processEngine.getRuntimeService().startProcessInstanceByKey("testProcessAsync")
			.getProcessInstanceId();
		log.info("Start process with processId: {}", processId);

		Execution execution = processEngine.getRuntimeService().createExecutionQuery().processInstanceId(processId)
			.activityId("serviceInvocation").singleResult();
		assertNotNull(execution);
		ServiceTaskInvoker.latch.await(10, TimeUnit.SECONDS);

		log.info("Start triggering Async with executionId: {}", execution.getId());
		processEngine.getRuntimeService().triggerAsync(execution.getId());
		log.info("TriggerAsync with executionId: {}", execution.getId());

		Thread.sleep(20_000);
	}
}
