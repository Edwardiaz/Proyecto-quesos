
package com.queso.dao;

import com.quesos.entities.Usuario;
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
public class UsuarioDao {
    @PersistenceContext(unitName = "quesoPU")
    private EntityManager em;
    private List<Usuario> listUsuario;
    private Usuario usuarioDao;
    
    public List<Usuario> obtenerUsuario(){
        try {
            listUsuario = new ArrayList<Usuario>();
            listUsuario = em.createNamedQuery("Usuario.findAll").getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return listUsuario;
    }
    
    public Usuario obtenerUsuario(Usuario user){
        try {
            usuarioDao = new Usuario();
            usuarioDao = (Usuario)em.createNamedQuery("Usuario.findByIdUsuario").setParameter("idUsuario", user.getIdUsuario()).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return usuarioDao;
    }
}
