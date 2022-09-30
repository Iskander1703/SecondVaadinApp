package iskander.tabaev.admin;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import iskander.tabaev.admin.models.Admin;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;


@SpringComponent
@UIScope
@SpringView
public class AuthView extends VerticalLayout implements View {
    public AuthView() {
        this.addStyleName("authBackground");
        VerticalLayout authlayout = new VerticalLayout();
        authlayout.setWidth(100, Unit.PERCENTAGE);
        VerticalLayout formlayout = new VerticalLayout();
        Label label = new Label(" Вход в личный кабинет администратора");
        label.addStyleName(ValoTheme.LABEL_HUGE);
        label.addStyleName("unit_margin_50");


        TextField username = new TextField("Логин");
        username.setWidth(20, Unit.PERCENTAGE);
        username.setId("usrLogin");

        PasswordField password = new PasswordField("Пароль");
        password.setWidth(20, Unit.PERCENTAGE);
        password.setId("usrPassword");

        Button login = new Button("Войти");
        login.setWidth(20, Unit.PERCENTAGE);
        login.setHeight(40, Unit.PIXELS);
        login.addStyleName("light-green");
        login.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        formlayout.addComponents(username, password, new Label(), login);
        formlayout.setSpacing(true);
        formlayout.setMargin(true);
        formlayout.addStyleName("unit_margin_20_top");
        formlayout.setWidth(100, Unit.PERCENTAGE);
        authlayout.addComponents(label, formlayout);
        authlayout.setSpacing(true);
        authlayout.setMargin(true);


        authlayout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
        formlayout.setComponentAlignment(username, Alignment.MIDDLE_CENTER);
        formlayout.setComponentAlignment(password, Alignment.MIDDLE_CENTER);
        formlayout.setComponentAlignment(login, Alignment.MIDDLE_CENTER);
        authlayout.setComponentAlignment(formlayout, Alignment.MIDDLE_CENTER);
        authlayout.addStyleName("border");
        this.addComponent(authlayout);
        this.setComponentAlignment(authlayout, Alignment.MIDDLE_CENTER);



        login.addClickListener(ClickEvent -> {
            Subject user = SecurityUtils.getSubject();
            try {
                user.login(new UsernamePasswordToken(username.getValue(), password.getValue()));
                Admin userinfo = (Admin) user.getPrincipal();

                try {
                    if (user.isAuthenticated())
                        UI.getCurrent().getNavigator().navigateTo("shows");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Notification.show("Ошибка авторизации", Notification.Type.HUMANIZED_MESSAGE);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                Notification.show("Ошибка авторизации. Обратитесь в службу поддержки.", Notification.Type.HUMANIZED_MESSAGE);

            }

        });
    }
}

