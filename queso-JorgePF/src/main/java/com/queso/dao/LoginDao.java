
package com.queso.dao;

import com.quesos.entities.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class LoginDao {
    @PersistenceContext(unitName = "quesoPU")//se usa cuando se hace un pool de conexion, para tomar control de la unidad de persistencia
    private EntityManager em; //para usar los metodos de transaccion de JPA
    
    public Usuario usuarioLogin(String usuario, String pass){
        Usuario usuarioLogeado=null; //usuario que se va a retornar
        try {
            //retornando los datos del usuario
            usuarioLogeado = (Usuario)em.createNamedQuery("Usuario.findByUsuarioPass").setParameter("usuario", usuario).setParameter("pass", pass).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarioLogeado;
    }
}
