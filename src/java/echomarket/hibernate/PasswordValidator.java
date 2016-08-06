/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echomarket.hibernate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
//Be between 8 and 40 characters long     Emcgurty123!  mkyong1A@  EMcGurty123!
//Contain at least one digit.
//Contain at least one lower case character.
//Contain at least one upper case character.
//Contain at least on special character from [ @ # $ % !  ].
*/
 
public class PasswordValidator{
	
	  private Pattern pattern;
	  private Matcher matcher;
 
	  private static final String PASSWORD_PATTERN = 
              "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!]).{8,16})";
	        
	  public PasswordValidator(){
		  pattern = Pattern.compile(PASSWORD_PATTERN);
	  }
	  
	  /**
	   * Validate password with regular expression
	   * @param password password for validation
	   * @return true valid password, false invalid password
	   */
	  public boolean validate(final String password){
		  
		  matcher = pattern.matcher(password);
		  return matcher.matches();
	    	    
	  }
}