package loginscreen.solution.example.com.loginscreen.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }
	
	public static boolean validatePhone(String s) {
		//remove ()-
		s = s.replace("(", "");
		s = s.replace(")", "");
		s = s.replace("-", "");
		if(s.equals("")) {
			return true; //we accept empty phones//
		}
		
		if(s.length()<10) {
			return false;
		} else if (s.length()==10 && s.substring(0,3).equals("119")) { //celular from sp must have 11 chars
			return false;
		}
		
		return true;
	}
}
