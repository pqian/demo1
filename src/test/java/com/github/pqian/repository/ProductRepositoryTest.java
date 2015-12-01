package com.github.pqian.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = { "/applicationContext.xml" })
@ActiveProfiles("test")
@TransactionConfiguration
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository repo;

	@Test
	public void test() throws InterruptedException {
		// wait for data warming up
		Thread.sleep(5000);
		Assert.assertTrue(repo.count()>0);
	}

}
