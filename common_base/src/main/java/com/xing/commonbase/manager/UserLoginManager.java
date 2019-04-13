package com.xing.commonbase.manager;

public class UserLoginManager {

    /**
     * 是否已登录
     */
    private boolean loggedIn = false;

    private UserLoginManager() {

    }

    public static class UserLoginManagerHolder {
        private static final UserLoginManager instance = new UserLoginManager();
    }

    public static UserLoginManager getInstance() {
        return UserLoginManagerHolder.instance;
    }

    public boolean isLoggedin() {
        return loggedIn;
    }

    public void setLoggedin(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
