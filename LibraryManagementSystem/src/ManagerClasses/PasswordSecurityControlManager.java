package ManagerClasses;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordSecurityControlManager {
	public  static boolean isValidPassword(String password)
    {
  
        // Regex to check valid password.
        String regex = "^(?=.*[0-9])"
                       + "(?=.[a-z])(?=.[A-Z])"
                       + "(?=.*[@#$%^&+=])"
                       + "(?=\\S+$).{8,20}$";
  
      
        Pattern p = Pattern.compile(regex);
  
       
        if (password == null) {
            return false;
        }
  
       
        Matcher m = p.matcher(password);
  
        return m.matches();
    }
	

}
