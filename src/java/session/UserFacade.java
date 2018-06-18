/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Класс служит для написания sql запросов для таблицы User.
 * @author Anton Kovalevskiy
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "CanteenPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }
    
    public User find(String login) {
        try {
            return (User) em.createQuery("SELECT u FROM User u WHERE u.login=:login")
                .setParameter("login", login)
                .getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }
    
    public User delete(Long id) {
        try {
            return (User) em.createQuery("DELETE FROM User u WHERE u.id=:id")
                .setParameter("id", id)
                .getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }
    }
    

