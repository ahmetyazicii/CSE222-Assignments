public interface User {

    boolean LogInSystem(String name,String password);
    void setUsername(String name);
    void setPassword(String password);
    String getUsername();
    String getPassword();
}
