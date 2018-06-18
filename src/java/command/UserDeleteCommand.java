/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import classes.AccessUser;
import classes.FindUserRole;
import classes.ListRoles;
import command.creator.RoutingManager;
import entity.Category;
import entity.Dishes;
import entity.GroupName;
import entity.Kook;
import entity.Menu;
import entity.Role;
import entity.User;
import interfaces.ActionCommand;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import session.CategoryFacade;
import session.DishesFacade;
import session.GroupNameFacade;
import session.KookFacade;
import session.MenuFacade;
import session.RoleFacade;
import session.UserFacade;

/** 
* Класс получает ID пользователя, после чего, если не возникает ошибок в процессе, то удаляет данного пользователя из всех таблиц в базе данных в которых он есть.
* @author Anton Kovalevskiy
*/

public class UserDeleteCommand implements ActionCommand {

    private KookFacade kookFacade;
    private UserFacade userFacade;
    private GroupNameFacade groupNameFacade;
    private CategoryFacade categoryFacade;
    private DishesFacade dishesFacade;
    private MenuFacade menuFacade;
    private RoleFacade roleFacade;

    public UserDeleteCommand() {
        try {
            Context context;
            context = new InitialContext();
            userFacade = (UserFacade) context.lookup("java:module/UserFacade");
            dishesFacade = (DishesFacade) context.lookup("java:module/DishesFacade");
            groupNameFacade = (GroupNameFacade) context.lookup("java:module/GroupNameFacade");
            categoryFacade = (CategoryFacade) context.lookup("java:module/CategoryFacade");
            kookFacade = (KookFacade) context.lookup("java:module/KookFacade");
            menuFacade = (MenuFacade) context.lookup("java:module/MenuFacade");
            roleFacade = (RoleFacade) context.lookup("java:module/RoleFacade");
        } catch (NamingException ex) {
            Logger.getLogger(UserDeleteCommand.class.getName()).log(Level.SEVERE, "Не удалось найти бин", ex);
        }
    }

    @Override
    public String execute(HttpServletRequest request) {
        AccessUser au = new AccessUser();
        User regUser = au.onAccess(request);

        if (regUser == null) {
            return RoutingManager.getRoute("path.page.login");
        }

        FindUserRole roleUser = new FindUserRole();
        String role = roleUser.getRole(regUser);
        request.setAttribute("role", role);

        ListRoles[] rolesEnum = ListRoles.values();
        request.setAttribute("roles", rolesEnum);

        List<Category> category = categoryFacade.findAll();
        request.setAttribute("categories", category);

        List<Dishes> dish = dishesFacade.findAll();
        request.setAttribute("dishList", dish);

        List<GroupName> groupName = groupNameFacade.findAll();
        request.setAttribute("groups", groupName);

        List<Menu> menuList = menuFacade.findCurrentWeek();
        request.setAttribute("menus", menuList);

        Map<Category, List<Dishes>> mapCategories = new HashMap<>();
        List<Category> categories = categoryFacade.findAll();

        for (int i = 0; i < categories.size(); i++) {
            Category cat = categories.get(i);
            List<Dishes> dishes = dishesFacade.findByCategory(cat);
            if (dishes != null) {
                mapCategories.put(cat, dishes);
            } else {
                Logger.getLogger(EnterCommand.class.getName()).log(Level.SEVERE, "Не удалось найти блюда");
                mapCategories.put(cat, new ArrayList<Dishes>());
            }

        }

        request.setAttribute("mapCategories", mapCategories);


        String userId = request.getParameter("userId");
        if (userId != null) {
            User findUser = userFacade.find(new Long(userId));
            Kook findKook = kookFacade.findKook(new Long(userId));

            List<Role> roles = roleFacade.findRoles(findUser);
            if (roles != null) {
                for (int i = 0; i < roles.size(); i++) {
                    Role roless = roles.get(i);
                    roleFacade.remove(roless);
                }
            }

            if (findKook != null) {
                kookFacade.remove(findKook);
            }

            if (findUser != null) {
                userFacade.remove(findUser);
                request.setAttribute("info", "Успешно удалено");
                List<User> users = userFacade.findAll();
                request.setAttribute("users", users);
            }

            return RoutingManager.getRoute("path.page.master");
        }

        List<User> users = userFacade.findAll();
        request.setAttribute("users", users);
        request.setAttribute("info", "Ошибка удаления");
        return RoutingManager.getRoute("path.page.master");
    }

}
