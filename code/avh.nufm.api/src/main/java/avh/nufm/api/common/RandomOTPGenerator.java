package avh.nufm.api.common;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class RandomOTPGenerator {
	private static SecureRandom random = new SecureRandom();
	
	public String generateOTP(int len) {  
		String NUMERIC = "0123456789";
		String result = "";
	    for (int i = 0; i < len; i++) {
	        int index = random.nextInt(NUMERIC.length());
	        result += NUMERIC.charAt(index);
	    }
	    return result;
    }
}
