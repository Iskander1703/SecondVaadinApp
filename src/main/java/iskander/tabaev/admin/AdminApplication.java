package iskander.tabaev.admin;

import com.vaadin.spring.annotation.EnableVaadin;
import iskander.tabaev.admin.auth.AdminAuthentificator;
import iskander.tabaev.admin.repositories.AdminRepositories;
import org.apache.shiro.web.env.EnvironmentLoaderListener;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletContext;
import java.security.NoSuchAlgorithmException;

import static org.apache.shiro.web.env.EnvironmentLoader.CONFIG_LOCATIONS_PARAM;

@SpringBootApplication
@ServletComponentScan
@EnableVaadin
public class AdminApplication extends SpringBootServletInitializer {

    private final AdminRepositories adminRepositories;
    private final ServletContext context;

    @Autowired
    public AdminApplication(AdminRepositories adminRepositories, ServletContext context) {
        this.adminRepositories = adminRepositories;
        this.context = context;
    }

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean filter() throws NoSuchAlgorithmException {
        FilterRegistrationBean shiroFilterBean = new FilterRegistrationBean();

        final String shiroConfName = System.getProperty("shiro.conf.name", "shiro");

        context.setInitParameter(CONFIG_LOCATIONS_PARAM, "classpath:" + shiroConfName + ".ini");
        EnvironmentLoaderListener ell = new EnvironmentLoaderListener();
        ell.initEnvironment(context);

        ShiroFilter filter = new ShiroFilter();
        filter.setServletContext(context);

        shiroFilterBean.setFilter(filter);
        shiroFilterBean.addUrlPatterns("/login");

        AdminAuthentificator.setAdminRepositories(adminRepositories);
        return shiroFilterBean;
    }


}
