/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
 * Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 2013-2014 Marcel Rosenberger, Mirko Göpfrich, Annika Weis, Katharina Narlock, 
 * Volker Meier
 * 
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/


package dhbw.ka.mwi.businesshorizon2.tests.ui.initialscreen.projectlist.assets;

import java.util.List;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ProjectListViewInterface;

public class ProjectListViewMock implements ProjectListViewInterface {

	private List<Project> projects;
	private boolean projectAddDialogShown = false;

	public boolean isProjectAddDialogShown() {
		return projectAddDialogShown;
	}


	@Override
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	
	
	public List<Project> getProjects() {
		return projects;
	}



	@Override
	public void showAddProjectDialog() {

		projectAddDialogShown  = true;
	}


	@Override
	public void showEditProjectDialog(Project project) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void showErrorMessage(String message) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void clearProjectList() {
		// TODO Auto-generated method stub
		
	}

}
