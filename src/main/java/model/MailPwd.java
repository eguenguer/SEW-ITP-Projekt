package model;

import java.util.HashMap;

public class MailPwd {
    private final HashMap<String, String> loginInfo = new HashMap<>();

    public MailPwd(){
        loginInfo.put("mstaettner@student.tgm.ac.at", "123");
    }

    public HashMap<String, String> getLoginInfo() {
        return loginInfo;
    }

    public void addUser(String mail, String pwd){
        if(mail != null || pwd != null) loginInfo.put(mail, pwd);
    }

    public void changePwd(String mail, String newPwd){
        loginInfo.put(mail, newPwd);
    }
}
