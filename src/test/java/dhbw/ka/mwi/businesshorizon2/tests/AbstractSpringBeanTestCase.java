package dhbw.ka.mwi.businesshorizon2.tests;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import dhbw.ka.mwi.businesshorizon2.tests.ui.assets.TestExecutionListener;

@TestExecutionListeners({TestExecutionListener.class, 
    DependencyInjectionTestExecutionListener.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:testContext.xml"})
abstract public class AbstractSpringBeanTestCase extends TestCase {
	
	@Autowired
	protected ApplicationContext context;
	
	protected <P> P getBean(Class<P> beanClass) {
		return (P) context.getBean(beanClass);
	}

}
