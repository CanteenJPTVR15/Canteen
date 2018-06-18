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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
 * Класс получает данные, которые были отправлены с сайта путем отправки формы
 * и, если не возникает ошибок в процессе то записывает полученные данные в
 * таблицу Menu в базу данных сайт.
 *
 * @author Anton Kovalevskiy
 */
public class MakeMenuCommand implements ActionCommand {

    private UserFacade userFacade;
    private GroupNameFacade groupNameFacade;
    private CategoryFacade categoryFacade;
    private DishesFacade dishesFacade;
    private MenuFacade menuFacade;

    public MakeMenuCommand() {
        try {
            Context context;
            context = new InitialContext();
            userFacade = (UserFacade) context.lookup("java:module/UserFacade");
            dishesFacade = (DishesFacade) context.lookup("java:module/DishesFacade");
            groupNameFacade = (GroupNameFacade) context.lookup("java:module/GroupNameFacade");
            categoryFacade = (CategoryFacade) context.lookup("java:module/CategoryFacade");
            menuFacade = (MenuFacade) context.lookup("java:module/MenuFacade");
        } catch (NamingException ex) {
            Logger.getLogger(MakeMenuCommand.class.getName()).log(Level.SEVERE, "Не удалось добавить группу", ex);
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

        List<Category> categorys = categoryFacade.findAll();
        request.setAttribute("categories", categorys);

        List<Dishes> dishe = dishesFacade.findAll();
        request.setAttribute("dishList", dishe);

        List<GroupName> groupName = groupNameFacade.findAll();
        request.setAttribute("groups", groupName);

        Map<Category, List<Dishes>> mapCategories = new HashMap<>();
        List<Category> categoriess = categoryFacade.findAll();
        request.setAttribute("mapCategories", mapCategories);

        for (int i = 0; i < categoriess.size(); i++) {
            Category cat = categoriess.get(i);
            List<Dishes> dishes = dishesFacade.findByCategory(cat);
            if (dishes != null) {
                mapCategories.put(cat, dishes);
            } else {
                Logger.getLogger(EnterCommand.class.getName()).log(Level.SEVERE, "Не удалось найти блюда");
                mapCategories.put(cat, new ArrayList<Dishes>());
            }

        }

        String kookId = request.getParameter("kookId");

        String[] categories = (String[]) request.getParameterValues("kats[]");

        String mDate = request.getParameter("menuDate");

        String[] arrData = mDate.split("-");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Menu menu = new Menu();

        List<Dishes> dish = new ArrayList<>();

        for (int i = 0; i < categories.length; i++) {
            String category = categories[i];
            dish.add(dishesFacade.find(new Long(category)));
        }
        menu.setDish(dish);

        if (kookId != null) {
            menu.setGroupname(groupNameFacade.find(new Long(kookId)));
        } else {
            request.setAttribute("Info", "Ошибка, группа не указана");
            List<Menu> menuList = menuFacade.findCurrentWeek();
            request.setAttribute("menus", menuList);
            return RoutingManager.getRoute("path.page.master");
        }

        try {
            Date tDate = dateFormat.parse(arrData[0] + "-" + arrData[1] + "-" + arrData[2]);
            Menu menuCheck = menuFacade.findCurrentDate(tDate);
            if (menuCheck != null) {
                request.setAttribute("Info", "Ошибка, меню на это число уже существует.");
                List<Menu> menuList = menuFacade.findCurrentWeek();
                request.setAttribute("menus", menuList);
                List<Menu> menuListAll = menuFacade.findAll();
                request.setAttribute("menuall", menuListAll);
                return RoutingManager.getRoute("path.page.master");
            }
            menu.setDateM(tDate);
        } catch (ParseException ex) {
            Logger.getLogger(MakeMenuCommand.class.getName()).log(Level.SEVERE, "Ошибка создания даты", ex);
        }

        menuFacade.create(menu);
        List<Menu> menuList = menuFacade.findCurrentWeek();
        request.setAttribute("menus", menuList);
        List<Menu> menuListAll = menuFacade.findAll();
        request.setAttribute("menuall", menuListAll);
        request.setAttribute("info", "Успешно создано");
        return RoutingManager.getRoute("path.page.master");
    }

}
