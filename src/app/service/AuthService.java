package app.service;

import app.model.Role;
import app.model.User;
import app.session.Session;

public class AuthService {

    private final Session session;

    public AuthService(Session session){
        this.session = session;
    }

    public String getCurrentUserStatus(){
        if (!session.isLoggedIn()){
            return "Текущий пользователь: вход не выполнен";
        }
        User user = session.getCurrentUser();
        return "Текущий пользователь: "
                + user.getUsername()
                + " (" + user.getRole() + ")";
    }

    public void login(String username, String roleString){
        Role role;

        if (roleString.equalsIgnoreCase("admin")) {
            role = Role.ADMIN;
        } else{
            role = Role.CASHIER;
        }

        User user = new User(username, role);
        session.login(user);
    }

    public void logout(){
        session.logout();
    }

    public boolean isAdmin(){
        if(!session.isLoggedIn()){
            System.out.println("В системе никого нет.");
            return false;
        }
        if(session.getCurrentUser().getRole() != Role.ADMIN){
            System.out.println("Добавлять / удалять лекарства может только администратор.");
            return false;
        }
        return true;
    }

    public boolean canSell() {
        if(!session.isLoggedIn()){
            System.out.println("В системе никого нет.");
            return false;
        }
        Role role = session.getCurrentUser().getRole();
        if(role != Role.ADMIN && role != Role.CASHIER){
            System.out.println("Продавать могут только администратор и кассир.");
            return false;
        }
        return true;
    }
}
