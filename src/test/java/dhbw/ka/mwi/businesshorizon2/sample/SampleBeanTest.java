package dhbw.ka.mwi.businesshorizon2.sample;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations="classpath:testContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SampleBeanTest {

	@Autowired
	protected SampleBeanInterface bean;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGet() {
		assertEquals("test-text", bean.get());
	}

}
