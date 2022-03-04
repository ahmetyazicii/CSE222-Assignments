public class AbstractUser implements User{

    private int userID;
    private String userName;
    private String userPassword;

    AbstractUser(int userID,String userPassword,String userName){
        setUserID(userID);
        setUserPassword(userPassword);
        setUserName(userName);
    }


    @Override
    public void setUserID(int ID) {
        userID = ID;
    }


    @Override
    public void setUserPassword(String password) {
        userPassword = password;
    }

    @Override
    public void setUserName(String name) {
        userName = name;
    }


    @Override
    public int getUserID() {
        return userID;
    }


    @Override
    public String getUserPassword() {
        return userPassword;
    }
    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void performTasks(DataBase db) {
    }

}
