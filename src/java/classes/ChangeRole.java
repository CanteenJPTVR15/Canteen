/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import command.ChangeRoleCommand;
import entity.Role;
import entity.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import session.RoleFacade;

/**
 * Класс получает данные пользователя и роль, после чего переназначает роль у пользователя на роль, которая была получена.
 * @author Anton Kovalevskiy
 */
public class ChangeRole {
   
    private RoleFacade roleFacade;
        public ChangeRole() {
            try{
            Context context;
            context = new InitialContext();
            this.roleFacade = (RoleFacade) context.lookup("java:module/RoleFacade");
            }
            catch(NamingException ex){
                Logger.getLogger(ChangeRoleCommand.class.getName()).log(Level.SEVERE,"Не удалось добавить группу",ex);
            }
        }

        
    public void changeRole(User user, ListRoles roleName) {
        List<Role> roles = roleFacade.findRoles(user);
        if(roles != null){
            for (int i = 0; i < roles.size(); i++) {
                Role role = roles.get(i);
                roleFacade.remove(role);
            }
        }
        
        Role roleUser = new Role();
        
        if(null != roleName)
        switch (roleName.toString()) {
        case "ADMIN":
            roleUser.setUser(user);
            roleUser.setRole(roleName.ADMIN);
            roleFacade.create(roleUser);
            roleUser.setUser(user);
            roleUser.setRole(roleName.MASTER);
            roleFacade.create(roleUser);
            roleUser.setUser(user);
            roleUser.setRole(roleName.USER);
            roleFacade.create(roleUser);
            break;
        case "MASTER":
            roleUser.setUser(user);
            roleUser.setRole(roleName.MASTER);
            roleFacade.create(roleUser);
            roleUser.setUser(user);
            roleUser.setRole(roleName.USER);
            roleFacade.create(roleUser);
            break;
        case "USER":
            roleUser.setUser(user);
            roleUser.setRole(roleName.USER);
            roleFacade.create(roleUser);
            break;
        default:
            break;
    }
    }
   
}
