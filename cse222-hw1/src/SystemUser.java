public class SystemUser implements User{
    private String Username;
    private String Password;

    SystemUser(String name,String password){
        setUsername(name);
        setPassword(password);
    }
    @Override
    public boolean LogInSystem(String name, String password) {
        return Username.equals(name) && Password.equals(password);
    }
    @Override
    public void setUsername(String username) {
        Username = username;
    }
    @Override
    public void setPassword(String password) {
        Password = password;
    }
    @Override
    public String getUsername() {
        return Username;
    }
    @Override
    public String getPassword() {
        return Password;
    }
}
