/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Класс служит для создания таблицы Menu в базе данных сайта.
 * @author Anton Kovalevskiy
 */
@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.DETACH)
    private List<Dishes> dish = new ArrayList<>();
    @OneToOne
    private GroupName groupname;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateM;

    public Menu() {
    }

    public Menu(List<Dishes> dish, GroupName groupname, Date dateM) {
        this.dish = dish;
        this.groupname = groupname;
        this.dateM = dateM;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Dishes> getDish() {
        return dish;
    }

    public void setDish(List<Dishes> dish) {
        this.dish = dish;
    }

    public GroupName getGroupname() {
        return groupname;
    }

    public void setGroupname(GroupName groupname) {
        this.groupname = groupname;
    }

    public Date getDateM() {
        return dateM;
    }

    public void setDateM(Date dateM) {
        this.dateM = dateM;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.dish);
        hash = 47 * hash + Objects.hashCode(this.groupname);
        hash = 47 * hash + Objects.hashCode(this.dateM);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Menu other = (Menu) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.dish, other.dish)) {
            return false;
        }
        if (!Objects.equals(this.groupname, other.groupname)) {
            return false;
        }
        if (!Objects.equals(this.dateM, other.dateM)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Menu{" + "id=" + id + ", dish=" + dish + ", groupname=" + groupname + ", dateM=" + dateM + '}';
    }

    
 
    
}

    
