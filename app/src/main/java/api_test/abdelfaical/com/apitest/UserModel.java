package api_test.abdelfaical.com.apitest;

/**
 * Created by AbdelFaical on 22/11/2017.
 */

public class UserModel {

    private int userId;
    private String userFirstname;
    private String userSurname;
    private String userFunction;

    public UserModel() {
    }

    public UserModel(int userId, String userFirstname, String userSurname, String userFunction) {
        this.userId = userId;
        this.userFirstname = userFirstname;
        this.userSurname = userSurname;
        this.userFunction = userFunction;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserFirstname() {
        return userFirstname;
    }

    public void setUserFirstname(String userFirstname) {
        this.userFirstname = userFirstname;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserFunction() {
        return userFunction;
    }

    public void setUserFunction(String userFunction) {
        this.userFunction = userFunction;
    }
}
