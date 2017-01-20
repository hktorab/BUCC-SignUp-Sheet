package BUCC;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Created by Torab on 07-Jan-17.
 */

public class LogInInfoChecker {
    protected String errorMsg="";

    private String query="";


  protected boolean userNameCheck(String username) throws SQLException {

        if (username.isEmpty()) {
            errorMsg = "User Field can not be Empty";
            return false;
        }
        String[] userNameArr = username.split("");
        if (userNameArr[0].matches("[0-9]"))
        {
            errorMsg = "User Field can not start with number";
            return false;
        }
        if(userNameArr.length>40)
        {
            errorMsg = "User Field can not exceed 40 character";
            return false;
        }

        return true;
    }

    protected boolean passWordCheck(String password){
     if (password.isEmpty())
     {
         errorMsg="Password Field Is empty ";
         return false;
     }
     if(password.length()>40)
     {
         errorMsg="password field cannot exceed 40 character";
         return false;
     }
     return true;

     }

    protected boolean passwordMismatch(String password,String rePassword)
     {
         errorMsg="password does not match";
         return password.equals(rePassword);
     }
}
