package com.goSmarter.gemfire.claimcheckpattern;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gemstone.gemfire.cache.Region;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test-config.xml",
		"classpath:claimcheck-in-integration-config.xml",
		"classpath:publisher-integration-config.xml", 
		"classpath:jms-publisher-config.xml"})
public class PublisherFlowTest {
	static {
		System.setProperty("spring.profiles.active", "dev");
	}
	private static Logger logger = Logger
			.getLogger(PublisherFlowTest.class);

	@Autowired
	@Qualifier("common.claimcheck.in.inputChannel")
	private MessageChannel inputChannel;

	@Autowired
	ApplicationContext context;

	Region<UUID, Object> claimChecks;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		claimChecks = context.getBean("Customer", Region.class);
	}
	
	@Test
	public void distributionIntegrationTest() {

		try {

			String Request = streamToString(getClass()
					.getResourceAsStream("/data/payload.xml"));

			Message<String> message = MessageBuilder.withPayload(Request)
					.build();

			inputChannel.send(message);

		} catch (Exception e) {

			e.printStackTrace();
			fail("Test Failed due to Unexpected Exception in Integration Flow Test Case: "
					+ e.getMessage());
		}
		logger.info("#################### Done with Integration Flow Test Case");
	}

	public static String streamToString(InputStream inputStream) throws IOException {
		Writer writer = new StringWriter();
		 byte[] b = new byte[4096];
		 for (int n; (n = inputStream.read(b)) != -1;) {     
		      writer.append(new String(b, 0, n));
		 }
	     return writer.toString();
	}
}