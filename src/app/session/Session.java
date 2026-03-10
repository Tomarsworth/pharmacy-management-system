package app.session;

import app.model.User;

public class Session {

    private User currentUser;       // текущий юзер
    private long loginTime;         // время входа

    public void login(User user){
        this.currentUser = user;
        this.loginTime = System.currentTimeMillis();
    }

    // метод выхода
    public void logout(){
        currentUser = null;
    }

    // проверка на юзера сейчас (проверка доступа)
    public boolean isLoggedIn(){
        return currentUser != null;
    }

    // текущий юзер
    public User getCurrentUser(){
        return currentUser;
    }

    // под вопросом
    public long getLoginTime(){
        return loginTime;
    }
}
