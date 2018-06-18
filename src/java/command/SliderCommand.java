/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import classes.AccessUser;
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
import session.MenuFacade;

/** 
* Класс осуществляет переход на страницу с меню.
* @author Anton Kovalevskiy
*/

public class SliderCommand implements ActionCommand {

    private MenuFacade menuFacade;

    public SliderCommand() {
        try {
            Context context;
            context = new InitialContext();
            menuFacade = (MenuFacade) context.lookup("java:module/MenuFacade");
        } catch (NamingException ex) {
            Logger.getLogger(SliderCommand.class.getName()).log(Level.SEVERE, "Не удалось найти бин", ex);
        }
    }

    @Override
    public String execute(HttpServletRequest request) {

        AccessUser au = new AccessUser();
        User regUser = au.onAccess(request);
        FindUserRole roleUser = new FindUserRole();
        String role = roleUser.getRole(regUser);
        request.setAttribute("role", role);
        if (regUser == null) {
            return RoutingManager.getRoute("path.page.login");
        }
        List<Menu> menuList = menuFacade.findCurrentWeek();
        request.setAttribute("menus", menuList);

        return RoutingManager.getRoute("path.page.menu");
    }

}
