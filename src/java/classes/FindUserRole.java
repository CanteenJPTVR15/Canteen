/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import entity.Role;
import entity.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import session.RoleFacade;
import session.UserFacade;

/**
 * Класс получает данные пользователя и по этим данным получает роль пользователя из базы данных сайта.
 * @author Anton Kovalevskiy
 */
public class FindUserRole {

    private UserFacade userFacade;
    private RoleFacade roleFacade;
    private User user;

    public FindUserRole() {
        Context context;
        try {
            context = new InitialContext();
            this.userFacade = (UserFacade) context.lookup("java:module/UserFacade");
            this.roleFacade = (RoleFacade) context.lookup("java:module/RoleFacade");
        } catch (NamingException ex) {
            Logger.getLogger(FindUserRole.class.getName()).log(Level.INFO, "Не удалось найти сессионый бин", ex);
        }
    }

    public String getRole(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        User regUser = (User) session.getAttribute("regUser");
        if (regUser == null) {
            return null;
        }
        return getRole(regUser);
    }

    public String getRole(User user) {
        if (user == null) {
            return null;
        }
        for (ListRoles roleEnum : ListRoles.values()) {
            if (this.contains(roleEnum.toString(), user)) {
                return roleEnum.toString();
            }
        }
        return null;
    }

    public String getRole(String userId) {
        if (userId == null) {
            return null;
        }
        if ("".equals(userId)) {
            return null;
        }
        try {
            user = userFacade.find(new Long(userId));
            return getRole(user);
        } catch (NumberFormatException e) {
            Logger.getLogger(FindUserRole.class.getName()).log(Level.INFO, "Не удалось найти роль", e);
            return null;
        }
    }

    public boolean contains(String role, User user) {

        List<Role> roles = roleFacade.findUserRoles(user);
        if (roles == null) {
            return false;
        }
        for (int i = 0; i < roles.size(); i++) {
            Role get = roles.get(i);
            if (role.equals(get.getRole().name())) {
                return true;
            }
        }
        return false;
    }

}
