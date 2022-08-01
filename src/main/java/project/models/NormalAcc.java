package project.models;

public class NormalAcc extends User {




    public NormalAcc(String userID ,  String username, String password, String securityAnswer, Integer securityQuestion) {

        super(userID , username, password, securityAnswer, securityQuestion);
        this.isNormal= true;
    }
}