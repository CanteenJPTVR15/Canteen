/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import classes.ChangeRole;
import classes.ListRoles;
import command.creator.RoutingManager;
import entity.User;
import interfaces.ActionCommand;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import security.EncriptPass;
import session.UserFacade;

/** 
* Класс отвечает за регистрацию нового пользователя.<br>
* Класс получает все данные и если в процессе не возникает ошибок, то регистрирует нового пользователя.
* @author Anton Kovalevskiy
*/

public class DoRegistrationCommand implements ActionCommand{

    private UserFacade userFacade;

    public DoRegistrationCommand() {
        try {
            Context context;
            context = new InitialContext();
            this.userFacade = (UserFacade) context.lookup("java:module/UserFacade");
        } catch (NamingException ex) {
            Logger.getLogger(DoRegistrationCommand.class.getName()).log(Level.SEVERE, "Не удалось найти бин", ex);
        }
    }
        
    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("info", "Эта строка создана классом NewUserCommand");
        String Name = request.getParameter("name");
        String LastName = request.getParameter("lastname");
        String login = request.getParameter("login");
        String Email = request.getParameter("email");
        String password1 = request.getParameter("pass");
        String password2 = request.getParameter("pass2");
        
        if(!password1.equals(password2)) {
            request.setAttribute("info", "Пароли не совпадают!");
            request.setAttribute("name", Name);
            request.setAttribute("lastname", LastName);
            request.setAttribute("login", login);
            return RoutingManager.getRoute("path.page.registration");
        }
        
        EncriptPass encriptPass = new EncriptPass();
        String salts = encriptPass.getSalts();
        String password = salts+password1;
        password = encriptPass.getEncriptString(password);
        if(password == null) {
            throw new Error("Возникла ошибка, обратитесь к разработчику!");
        }
        User newUser = new User(login, password, Email, Name, LastName, salts);
        userFacade.create(newUser);
        ChangeRole chR = new ChangeRole();
        chR.changeRole(newUser,ListRoles.USER);
        return RoutingManager.getRoute("path.page.login");
    }
    
}
