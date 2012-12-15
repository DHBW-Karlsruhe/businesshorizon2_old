package dhbw.ka.mwi.businesshorizon2.ui.methodlist;

import java.util.SortedSet;

import com.mvplite.view.View;

import dhbw.ka.mwi.businesshorizon2.methods.Method;

public interface MethodListView extends View {

	void setMethods(SortedSet<Method> methods);

}
