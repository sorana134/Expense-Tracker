package phase2;


import java.io.IOException;
import java.util.List;

public class Password {



    PasswordDB operation= new PasswordDB();
    public boolean checkPassWord(String user_name, String pass) throws IOException{

        String passWord = getPassWord(user_name);

        if(pass.equals(passWord)){
            return true;
        }
        else{
            return false;
        }


    }

    public void changePassWord(String user_name, String new_password) throws IOException{
        //change password knowing the user_name
        operation.updatePassword(new_password, user_name);


    }

    private String getPassWord(String user_name) throws IOException{

      //get the password from the database table password

        return operation.getPassword(user_name);


    }

    public boolean addPassWord(String user_name, String pass) throws IOException{
        //add a password to the database
        //check if the user_name already exists using streams
        if(checkUserName(user_name)){
            return false;
        }

        operation.addPassword(pass, user_name);

        return true;

    }
    public boolean checkUserName(String user_name) throws IOException{
        //check if the user_name already exists using streams
        List<String> userNames = operation.getUserNames();
        //print(userNames);

        return userNames.stream().anyMatch(user_name::equals);
    }
}
