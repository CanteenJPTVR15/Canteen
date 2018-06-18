/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Role;
import entity.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Класс служит для написания sql запросов для таблицы Role.
 * @author Anton Kovalevskiy
 */
@Stateless
public class RoleFacade extends AbstractFacade<Role> {

    @PersistenceContext(unitName = "CanteenPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RoleFacade() {
        super(Role.class);
    }

    public List<Role> findRoles(User user) {
        try {
            List<Role> rolles = em.createQuery("SELECT r FROM Role r WHERE r.user=:user").
                    setParameter("user", user)
                    .getResultList();
            return rolles;
        } catch (Exception e) {
            return null;
        }

    }

    public List<Role> findUserRoles(User user) {
        try {
            List<Role> roles = em.createQuery("SELECT r FROM Role r WHERE r.user =:user")
                    .setParameter("user", user)
                    .getResultList();
            return roles;
        } catch (Exception e) {
            Logger.getLogger(RoleFacade.class.getName()).log(Level.INFO, "Не удалось найти роли у пользователя", e);
            return null;
        }

    }
}
