package iskander.tabaev.admin.auth;

import iskander.tabaev.admin.models.Admin;
import iskander.tabaev.admin.repositories.AdminRepositories;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.mindrot.jbcrypt.BCrypt;


import java.util.Optional;

public class AdminAuthentificator implements Authenticator {

    private static  AdminRepositories adminRepositories;



    @Override
    public AuthenticationInfo authenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        String log = (String) authenticationToken.getPrincipal();
        String pass = new String((char[]) authenticationToken.getCredentials());

        Optional<Admin> optionalAdmin = adminRepositories.findByLogin(log);

        if (optionalAdmin.isEmpty()) {
            throw new UnknownAccountException("User with login = " + log + " does not exists");
        }

        if (!BCrypt.checkpw(pass, optionalAdmin.get().getPass())) {
            throw new AuthenticationException("Incorrect password");
        }

        SimpleAuthenticationInfo ret = new SimpleAuthenticationInfo();
        ret.setPrincipals(new SimplePrincipalCollection(optionalAdmin.get(), "admin"));
        return ret;
    }

    public static void setAdminRepositories(AdminRepositories adminRepositories) {
       AdminAuthentificator.adminRepositories=adminRepositories;
    }
}
