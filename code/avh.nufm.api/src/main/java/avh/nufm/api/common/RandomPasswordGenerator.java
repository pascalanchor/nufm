package avh.nufm.api.common;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class RandomPasswordGenerator {
	private static SecureRandom random = new SecureRandom();

    public String generatePassword(int len) {
        String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String ALPHA = "abcdefghijklmnopqrstuvwxyz";
        String NUMERIC = "0123456789";
        String SPECIAL_CHARS = "!@#$%^&*";
        String values = ALPHA_CAPS + ALPHA + SPECIAL_CHARS + NUMERIC;
	    String result = "";
	    for (int i = 0; i < len; i++) {
	        int index = random.nextInt(values.length());
	        result += values.charAt(index);
	    }
	    return result;
    }
}
