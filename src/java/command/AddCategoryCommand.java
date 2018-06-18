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
import entity.Menu;
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
import session.MenuFacade;
import session.UserFacade;

/** 
* Класс получает данные, которые были отправлены с сайта путем отправки формы и, если не возникает ошибок в процессе то записывает полученные данные в таблицу Category которая находится в базе данных сайта.
* @author Anton Kovalevskiy
*/


public class AddCategoryCommand implements ActionCommand {

    private UserFacade userFacade;
    private GroupNameFacade groupNameFacade;
    private CategoryFacade categoryFacade;
    private DishesFacade dishesFacade;
    private MenuFacade menuFacade;

    public AddCategoryCommand() {
        try {
            Context context;
            context = new InitialContext();
            userFacade = (UserFacade) context.lookup("java:module/UserFacade");
            dishesFacade = (DishesFacade) context.lookup("java:module/DishesFacade");
            groupNameFacade = (GroupNameFacade) context.lookup("java:module/GroupNameFacade");
            categoryFacade = (CategoryFacade) context.lookup("java:module/CategoryFacade");
            menuFacade = (MenuFacade) context.lookup("java:module/MenuFacade");
        } catch (NamingException ex) {
            Logger.getLogger(AddCategoryCommand.class.getName()).log(Level.SEVERE, "Не удалось добавить группу", ex);
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

        List<User> users = userFacade.findAll();
        request.setAttribute("users", users);

        List<Dishes> dish = dishesFacade.findAll();
        request.setAttribute("dishList", dish);

        List<GroupName> groupName = groupNameFacade.findAll();
        request.setAttribute("groups", groupName);

        List<Menu> menuList = menuFacade.findCurrentWeek();
        request.setAttribute("menus", menuList);

        Map<Category, List<Dishes>> mapCategories = new HashMap<>();
        List<Category> categories = categoryFacade.findAll();

        List<Menu> menuListAll = menuFacade.findAll();
        request.setAttribute("menuall", menuListAll);        
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

        String categoryName = request.getParameter("nameCategory");
        if (categoryName == null) {
            return RoutingManager.getRoute("path.page.master");
        }

        Category findCategory = categoryFacade.find(categoryName);
        if (findCategory != null) {
            request.setAttribute("info", "Такая категория уже есть.");
            List<Category> category = categoryFacade.findAll();
            request.setAttribute("categories", category);
            return RoutingManager.getRoute("path.page.master");
        }
        request.setAttribute("info", "Добавлено.");
        Category newCategory = new Category(categoryName);
        categoryFacade.create(newCategory);
        List<Category> category = categoryFacade.findAll();
        request.setAttribute("categories", category);

        return RoutingManager.getRoute("path.page.master");
    }

}
