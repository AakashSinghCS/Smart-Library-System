import org.mindrot.jbcrypt.BCrypt;

public class AuthUtil {
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plaintext, String hashed) {
        return BCrypt.checkpw(plaintext, hashed);
    }
}
