/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import classes.FindUserRole;
import command.creator.RoutingManager;
import entity.Menu;
import entity.User;
import interfaces.ActionCommand;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import security.EncriptPass;
import session.MenuFacade;
import session.UserFacade;

/** 
* Класс отвечает за вход пользователя на сайт.<br>
* Если полученные данные верны и существуют, то происходит вход на сайт.
* @author Anton Kovalevskiy
*/

public class EnterCommand implements ActionCommand {

    private UserFacade userFacade;
    private MenuFacade menuFacade;

    public EnterCommand() {
        try {
            Context context;
            context = new InitialContext();
            userFacade = (UserFacade) context.lookup("java:module/UserFacade");
            menuFacade = (MenuFacade) context.lookup("java:module/MenuFacade");
        } catch (NamingException ex) {
            Logger.getLogger(EnterCommand.class.getName()).log(Level.SEVERE, "Не удалось найти бин", ex);
        }
    }

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        User regUser = userFacade.find(login);
        if (regUser == null) {
            request.setAttribute("info", "Такой логин или пароль не существует!");
            return RoutingManager.getRoute("path.page.login");
        }
        EncriptPass encriptPass = new EncriptPass();
        String salts = regUser.getSalts();
        password = salts + password;
        password = encriptPass.getEncriptString(password);
        if (password == null) {
            throw new Error("Возникла ошибка, обратитесь к разработчику!");
        }
        if (!password.equals(regUser.getPassword())) {
            request.setAttribute("info", "Такой логин или пароль не существует!");
            return RoutingManager.getRoute("path.page.login");
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("regUser", regUser);
        List<Menu> menuList = menuFacade.findCurrentWeek();
        request.setAttribute("menus", menuList);

        FindUserRole roleUser = new FindUserRole();
        String role = roleUser.getRole(regUser);
        request.setAttribute("role", role);
        return RoutingManager.getRoute("path.page.menu");
    }

}
