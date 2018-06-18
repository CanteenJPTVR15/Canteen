/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Category;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Класс служит для написания sql запросов для таблицы Category.
 * @author Anton Kovalevskiy
 */
@Stateless
public class CategoryFacade extends AbstractFacade<Category> {

    @PersistenceContext(unitName = "CanteenPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoryFacade() {
        super(Category.class);
    }
    
    public Category find(String categoryName){
        try {
            return (Category) em.createQuery("SELECT c FROM Category c WHERE c.name=:name")
                    .setParameter("name", categoryName)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
        
    }
    
}
