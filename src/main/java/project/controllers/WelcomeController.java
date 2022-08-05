package project.controllers;
import project.database.DBGetter;
import project.database.UserDB;
import project.enums.Message;
import project.models.User;

public class WelcomeController extends Controller {
    //singleton
    private static void setInstance(WelcomeController instance) {
        WelcomeController.instance = instance;
    }

    private static WelcomeController instance = null;

    private WelcomeController() {

    }

    public static WelcomeController getInstance() {
        if (WelcomeController.instance == null) {
            WelcomeController.setInstance(new WelcomeController());
        }

        return WelcomeController.instance;
    }


    public Message handleRegistration(String userID ,String username , String password, String repeatedPassword, Integer questionNum, String answerS , Boolean isNormal) {
        if (this.doesUsernameExist(username)) {
            return Message.USER_EXIST;
        }


           User nwUser= new User(userID ,username , password , answerS , questionNum , isNormal);
           UserDB.addUser(nwUser);


        return Message.SUCCESS;
    }


    private Boolean doesUsernameExist(String userID) {
        return DBGetter.findUserByUserID(userID) != null;
    }

    public Message handleLogin(String userID, String password) {

        User user = DBGetter.findUserByUserID(userID);
        if (user != null ) {
            if (user.getPassword().equals(password)){
                return Message.SUCCESS;
            }
            else return Message.WRONG_PASSWORD;
        }
        else return Message.NO_EXIST;
    }


    public void handleChangingPass(String username, String password) {
        User user = DBGetter.findUserByUserID(username);
        user.setPassword(password);
        UserDB.updateUser(user);

    }
}