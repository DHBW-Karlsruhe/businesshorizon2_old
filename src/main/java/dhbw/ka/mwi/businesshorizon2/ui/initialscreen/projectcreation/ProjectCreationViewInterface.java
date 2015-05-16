/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2014-2015 Marco Glaser
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
package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectcreation;

import com.mvplite.view.View;

import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.InitialScreenViewInterface;

public interface ProjectCreationViewInterface extends View {
	
	public void setInitialScreen(InitialScreenViewInterface view);

	public void showErrorMessage(String message);
	
	public void addProject(int nextStep);
	
	public void editProject(int nextStep);
	
	public void setProjectData();
	
	public void clearProjectData();

}
