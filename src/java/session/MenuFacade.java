/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Menu;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Класс служит для написания sql запросов для таблицы Menu.
 * @author Anton Kovalevskiy
 */
@Stateless
public class MenuFacade extends AbstractFacade<Menu> {

    @PersistenceContext(unitName = "CanteenPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MenuFacade() {
        super(Menu.class);
    }
   

    public List<Menu> findCurrentWeek() {
//        Calendar c = new GregorianCalendar();
//        c.getTime();
//        c.set(Calendar.DAY_OF_WEEK_IN_MONTH, c.getFirstDayOfWeek());
//        c.set(Calendar.DAY_OF_WEEK, 1);
//        System.out.println(c.getTime());
//        Date monday = new Date();
//        monday = c.getTime();
//        
//
//        c.getTime();
//        c.set(Calendar.DAY_OF_WEEK_IN_MONTH, c.getFirstDayOfWeek()+1);
//        c.set(Calendar.DAY_OF_WEEK,-1);
//        System.out.println(c.getTime());
//        Date friday = new Date();
//        friday = c.getTime();
         
        Calendar c = Calendar.getInstance();
        System.out.println("Current date: " + c.getTime().toString());
        int currentWeekDey = c.get(Calendar.DAY_OF_WEEK);
        System.out.println("Day of week: " + currentWeekDey);//начало недели - воскресенье
        c.add(Calendar.DAY_OF_MONTH, -(currentWeekDey - 2)-1); //сдвигаем дату от текущей назад (-) на (currentWeekDey-2)
        Date monday = c.getTime();
        c.add(Calendar.DAY_OF_MONTH, 6);
        Date saturday = c.getTime();
        System.out.println(saturday.toString());
        return em.createQuery("SELECT m FROM Menu m WHERE m.dateM >= :dateMonday AND m.dateM <=:dateFriday ORDER BY m.dateM")
                .setParameter("dateMonday", monday)
                .setParameter("dateFriday", saturday)
                .getResultList();
    }

    public Menu findCurrentDate(Date currentDate){
           try{ 
               return (Menu) em.createQuery("SELECT m FROM Menu m WHERE m.dateM =:currentDate")
                .setParameter("currentDate", currentDate)
                .getSingleResult();
        }
     catch (Exception e) {
            return null;
        }

    }
}
    
    


