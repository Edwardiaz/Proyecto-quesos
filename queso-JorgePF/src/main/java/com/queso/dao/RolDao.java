
package com.queso.dao;

import com.quesos.entities.Roles;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jorge.diazusam
 */
@Stateless
public class RolDao {
    @PersistenceContext(unitName = "quesoPU")
    private EntityManager em;
    private List<Roles> rolesList;//declaramos la lista de roles
    
    public List<Roles> obtenerListaRoles(){
        try {
            rolesList = new ArrayList<Roles>();//instanciamos lista de roles
            rolesList = em.createNamedQuery("Roles.findAll").getResultList();//usando query nativo para obtener roles
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rolesList;
    }
    
}
