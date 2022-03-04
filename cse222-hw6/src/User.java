public interface User {

    void setUserID(int ID);
    void setUserPassword(String password);
    void setUserName(String name);
    int getUserID();
    String getUserPassword();
    String getUserName();
    void performTasks(DataBase db);


}
