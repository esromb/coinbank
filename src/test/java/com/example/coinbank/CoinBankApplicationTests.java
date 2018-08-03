package com.example.coinbank;

import com.example.coinbank.dto.CoinProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static java.lang.System.exit;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class CoinBankApplicationTests {
//
//	@Autowired
//	ApplicationContext ctx;
//
//	@Test
//	public void contextLoads() {
//		assertNotNull(this.ctx);
//		SpringApplication.exit(ctx, () -> 0);
//
//	}
//
//}
