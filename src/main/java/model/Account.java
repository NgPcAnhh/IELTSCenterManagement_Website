package model;

public class Account {
    private String id;
    private String loginName;
    private String password;

    public Account(String id, String loginName, String password) {
        this.id = id;
        this.loginName = loginName;
        this.password = password;
    }

    // Getters và Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
