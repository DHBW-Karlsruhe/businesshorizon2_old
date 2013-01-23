package dhbw.ka.mwi.businesshorizon2.tests.ui.initialscreen.projectlist;


import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.User;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.tests.ui.AbstractPresenterTestCase;
import dhbw.ka.mwi.businesshorizon2.tests.ui.initialscreen.projectlist.assets.ProjectListViewMock;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ProjectAddEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ProjectListPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ProjectRemoveEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ShowProjectEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ShowProjectListEvent;

public class ProjectListPresenterTest extends AbstractPresenterTestCase {
	
	@Autowired
	private ProjectListPresenter presenter;
	
	private ProjectListViewMock view;
	
	@Override
	protected void setUp() throws Exception {
		presenter.setView(view);
	}
	
	@Test
	public void testProjectSelected(){
		Project project = new Project("testProject");
		presenter.projectSelected(project);
		
		assertEventFired(ShowProjectEvent.class);
		assertEquals(project, getBean(ProjectProxy.class).getSelectedProject());
	}
	
//	@Test
//	public void testOnShowProjectList(){
//			User user = new User();
//			user.addProject(new Project("Test"));	
//			ShowProjectListEvent event = new ShowProjectListEvent(user);	
//			
//			presenter.onShowProjectList(event);
//								
//			assertEquals(user.getProjects(), view.getProjects());
//		
//	}
//	
//	@Test
//	public void testRemoveProject() {
//		
//		Project project1 = new Project("Projekt1");
//		Project project2 = new Project("Projekt2");
//		User user = new User();
//		user.addProject(project1);
//		user.addProject(project2);
//		
//		presenter.removeProject(project1);
//		
//		assertEquals(project2, view.getProjects().get(0));
//
//	}
//	
//	@Test
//	public void addProject() {
//		
//		String name = "Testname";
//		
//		presenter.addProject(name);
//		
//		assertEquals(name, view.getProjects().get(0).getName());
//		assertEventFired(ProjectAddEvent.class);
//
//	}
}
