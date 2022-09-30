package iskander.tabaev.admin;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import iskander.tabaev.admin.show.ShowView;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
public class MyUI extends UI {

    @Autowired
    private AuthView authView;

    @Autowired
    private ShowView showView;

    @Override
    protected void init(VaadinRequest request) {

        Navigator navigator = new Navigator(this, this);
        navigator.addView("login", authView);
        navigator.addView("shows", showView);

    }
}
