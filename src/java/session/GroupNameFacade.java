/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.GroupName;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Класс служит для написания sql запросов для таблицы GroupName.
 * @author Anton Kovalevskiy
 */
@Stateless
public class GroupNameFacade extends AbstractFacade<GroupName> {

    @PersistenceContext(unitName = "CanteenPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GroupNameFacade() {
        super(GroupName.class);
    }
    
    
    public GroupName find(String groupName){
        try {
            return (GroupName) em.createQuery("SELECT g FROM GroupName g WHERE g.name=:name")
                    .setParameter("name", groupName)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
        
    }
    
}
