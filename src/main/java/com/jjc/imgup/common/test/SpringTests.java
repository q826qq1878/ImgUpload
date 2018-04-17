package com.jjc.imgup.common.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/applicationContext.xml"})
@TransactionConfiguration(transactionManager="txManager" ,defaultRollback= true)
@ActiveProfiles("test")
public class SpringTests extends AbstractTransactionalJUnit4SpringContextTests {
	
}
