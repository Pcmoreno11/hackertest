package com.hotelbeds.supplierintegrations.hackertest.detector;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.hotelbeds.supplierintegrations.hackertest.SiTestApplication;
import com.hotelbeds.supplierintegrations.hackertest.dto.Action;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SiTestApplication.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HackerDetectorTest {
	
	private static final String ANY_IP = "80.238.9.179";
	private static final String OTHER_IP = "90.187.5.154";
	private static final String ANY_NAME = "Will.Smith";
	private static final Long NOW = LocalDateTime.now().atZone(ZoneId.systemDefault())
			.toInstant().toEpochMilli();
	private static final Long ONE_MINUTE = 60000L;
	
	@Autowired
	private HackerDetector hackerDetector;
	
	@Test
    public void test1FiveConsecutiveSuccess() {

		//There are no errors
		Assert.assertNull(hackerDetector.parseLine(this.buildSuccessLine()));
		Assert.assertNull(hackerDetector.parseLine(this.buildSuccessLine()));
		Assert.assertNull(hackerDetector.parseLine(this.buildSuccessLine()));
		Assert.assertNull(hackerDetector.parseLine(this.buildSuccessLine()));
		Assert.assertNull(hackerDetector.parseLine(this.buildSuccessLine()));
    }

	@Test
    public void test2ConsecutiveErrors() {

		//Errors at 4 and 5 minutes
		Long anyMinutes = plusMinutes(0L);
		Assert.assertNull(hackerDetector.parseLine(this.buildFailureLine(anyMinutes)));
		Assert.assertNull(hackerDetector.parseLine(this.buildFailureLine(this.plusMinutes(1L))));   //+1min
		Assert.assertNull(hackerDetector.parseLine(this.buildFailureLine(this.plusMinutes(2L))));   //+2min
		Assert.assertNull(hackerDetector.parseLine(this.buildFailureLine(this.plusMinutes(3L))));   //+3min
		Assert.assertNotNull(hackerDetector.parseLine(this.buildFailureLine(this.plusMinutes(4L))));//+4min
		Assert.assertNotNull(hackerDetector.parseLine(this.buildFailureLine(this.plusMinutes(5L))));//+5min
		Assert.assertNull(hackerDetector.parseLine(this.buildFailureLine(this.plusMinutes(8L))));   //+8min
    }
	
	
	@Test
    public void test3FiveErrorsInSixMinutes() {

		//There are no errors
		Long anyMinutes = plusMinutes(15L);
		Assert.assertNull(hackerDetector.parseLine(this.buildFailureLine(anyMinutes)));
		Assert.assertNull(hackerDetector.parseLine(this.buildFailureLine(this.plusMinutes(anyMinutes, 1L))));//+1min
		Assert.assertNull(hackerDetector.parseLine(this.buildFailureLine(this.plusMinutes(anyMinutes, 2L))));//+2min
		Assert.assertNull(hackerDetector.parseLine(this.buildFailureLine(this.plusMinutes(anyMinutes, 3L))));//+3min
		Assert.assertNull(hackerDetector.parseLine(this.buildFailureLine(this.plusMinutes(anyMinutes, 6L))));//+6min
    }
	
	@Test
    public void test4FiveConsecutiveErrorsDifferentIps() {

		//There are no errors
		Long anyMinutes = plusMinutes(30L);
		
		Assert.assertNull(hackerDetector.parseLine(this.buildFailureLine(anyMinutes)));
		Assert.assertNull(hackerDetector.parseLine(this.buildFailureLine(this.plusMinutes(anyMinutes, 1L))));//+1min
		Assert.assertNull(hackerDetector.parseLine(this.buildFailureLine(this.plusMinutes(anyMinutes, 2L))));//+2min
		Assert.assertNull(hackerDetector.parseLine(this.buildFailureLine(OTHER_IP, this.plusMinutes(anyMinutes, 3L))));//+3min
		Assert.assertNull(hackerDetector.parseLine(this.buildFailureLine(this.plusMinutes(anyMinutes, 4L))));//+4min
    }
	
	@Test
    public void test5InvalidData() {

		//There are no errors
		Assert.assertNull(hackerDetector.parseLine(""));
    }
	
	
	private String buildSuccessLine() {
		return ANY_IP + "," + NOW + "," + Action.SIGNIN_SUCCESS + "," + ANY_NAME;
	}
	
	private String buildFailureLine(Long time) {
		return ANY_IP + "," + time + "," + Action.SIGNIN_FAILURE + "," + ANY_NAME;
	}
	
	private String buildFailureLine(String ip, Long time) {
		return ip + "," + time + "," + Action.SIGNIN_FAILURE + "," + ANY_NAME;
	}
	
	private Long plusMinutes(Long plusMinutes) {
		return plusMinutes(NOW, plusMinutes);
	}
	
	private Long plusMinutes(Long minutes, Long plusMinutes) {
		return minutes + (plusMinutes * ONE_MINUTE);
	}
}
