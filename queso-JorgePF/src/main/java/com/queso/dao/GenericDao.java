package com.queso.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jorge.diazusam
 */
@Stateless
public class GenericDao {

    @PersistenceContext(unitName = "quesoPU")
    EntityManager em;

    public Object guardarEntidad(Object obj) {
        try {
            em.persist(obj);//guardar objeto en la base de datos
            em.flush();//Obtiene el objeto ya guardado en la base de datos
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return obj;
    }
    public String actualizarEntidad(Object obj){
        String msg = "";
        try {
            em.merge(obj);
            msg = "Dato actualizado con éxito";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Error actualizando dato";
        }
        return msg;
    }
}

