package iskander.tabaev.admin.utils;

import org.mindrot.jbcrypt.BCrypt;

import java.security.NoSuchAlgorithmException;

public class PassGenerator {

    public PassGenerator() {
    }

    public static String convert(String password) throws NoSuchAlgorithmException {

        //Хэшируем пароль с помощью библиотеки Bcrypt
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(10));
       return hashed;

    }
}