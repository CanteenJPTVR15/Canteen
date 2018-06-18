/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.GroupName;
import entity.Kook;
import entity.User;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Класс служит для написания sql запросов для таблицы Kook.
 * @author Anton Kovalevskiy
 */
@Stateless
public class KookFacade extends AbstractFacade<Kook> {

    @PersistenceContext(unitName = "CanteenPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public KookFacade() {
        super(Kook.class);
    }
    
     public Kook findRoles(User user) {
        try {
            Kook kook = (Kook) em.createQuery("SELECT k FROM Kook k WHERE k.user=:user").
                    setParameter("user", user)
                    .getSingleResult();
            return kook;
        } catch (Exception e) {
            return null;
        }

    }
     
     public Kook findKook(Long userId) {
        try {
            return (Kook) em.createQuery("SELECT k FROM Kook k WHERE k.user.id=:userId").
                    setParameter("userId", userId)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
     }
     
    public int deleteKooks(GroupName findGroup) {
        try {
           
            return em.createQuery("DELETE FROM Kook k WHERE k.groupname=:findGroup")
                    .setParameter("findGroup", findGroup)
                    .executeUpdate();
        } catch (Exception e) {
            
            Logger.getLogger(KookFacade.class.getName()).log(Level.SEVERE, "Не удалось удалить поваров", e);
            return 0;
        }
        
    }
    
    
}
