package com.queso.mb;

import com.queso.dao.GenericDao;
import com.queso.dao.RolDao;
import com.queso.dao.UsuarioDao;
import com.queso.util.UtilVarios;
import com.quesos.entities.Roles;
import com.quesos.entities.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class UsuariosMB {

    private Usuario usuarioVista;
    @ManagedProperty(value = "#{loginMB}")
    private LoginMB loginMB;
    @EJB
    private GenericDao gd;
    private Map<String, String> rolesSelect;
    private List<Roles> rolesList;
    @EJB
    private RolDao rolDao;
    private Integer selectRolVal;
    private List<Usuario> listUsuario;
    @EJB
    private UsuarioDao usuarioDao;
    
 
    @PostConstruct
    public void init() {
        usuarioVista = new Usuario();
        rolesSelect = new HashMap<String, String>();
        rolesList = new ArrayList<Roles>();
        selectRolVal = 0;
        listUsuario = new ArrayList<Usuario>();
        obtenerUsuarios();
        obtenerRoles();
    }

    public void guardarUsuario() {
        FacesMessage msg = null;//inicializo el mensaje de la vista
        UtilVarios uv = new UtilVarios();//hago instancia de utilVarios para usar encriptador
        usuarioVista.setPass(uv.convertiraMd5(usuarioVista.getPass()));//encripto password de usuario
        Roles rolUsuario = new Roles();//hago instancia de rol para asignarla a usuario
        rolUsuario.setIdRole(selectRolVal);//asigno idRol a rol
        usuarioVista.setIdRol(rolUsuario);//asigno rol a usuario
        if (loginMB.isEstaLogeado()) {
            Usuario usuarioGuardar = new Usuario();           
            usuarioGuardar = (Usuario) gd.guardarEntidad(usuarioVista);//envio usuario a la capa dao
            if (null != usuarioGuardar) {//si el usuario no es nulo quiere decir que lo ha guardado, se manda mensaje correspondiente
                msg = new FacesMessage("Usuario guardado con exito "+usuarioGuardar.getIdUsuario());
            } else {
                msg = new FacesMessage("Error guardando usuario");
            }
            //FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            msg = new FacesMessage("Usted no tiene permiso para hacer esta accion");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void obtenerRoles(){
        rolesList = rolDao.obtenerListaRoles();//obtenemos lista de roles desde dao
        for(Roles r:rolesList){ //itero la lista para llenar el objeto Map para selectOneMenu
            rolesSelect.put(r.getRol(), String.valueOf(r.getIdRole())); //entero péro le hacemos un Cast en getIdRol
            //tambien seteo los valores en el Map
        }
        //System.out.println("tamaño de lista roles: " + rolesList.size());
    }
    
    public void obtenerUsuarios(){
        if(loginMB.isEstaLogeado()){
        listUsuario = usuarioDao.obtenerUsuario();
        }
    }
    
    public void seleccionarUsuario(Usuario user){
        if(loginMB.isEstaLogeado()){
        usuarioVista = usuarioDao.obtenerUsuario(user);
        }
    }
    public void actualizarUsuario(){
        Roles rol = new Roles();
        rol.setIdRole(selectRolVal);//asigno valor de rol a objeto rol
        usuarioVista.setIdRol(rol);//asigno el rol al usuario
        UtilVarios uv = new UtilVarios();//hago instancia de utilVarios
        usuarioVista.setPass(uv.convertiraMd5(usuarioVista.getPass()));//encripto nuevo password y seteo a usuario a actualizar
        
        String mensaje = gd.actualizarEntidad(usuarioVista);//mando usuario a dao
        FacesMessage msg = new FacesMessage(mensaje);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        obtenerUsuarios();//obtengo a usuarios de la BD luego de actualizar
    }
    
    public void eliminarUsuario(){
        
    }
    
    public Usuario getUsuarioVista() {
        return usuarioVista;
    }

    public void setUsuarioVista(Usuario usuarioVista) {
        this.usuarioVista = usuarioVista;
    }

    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }

    public Integer getSelectRolVal() {
        return selectRolVal;
    }

    public void setSelectRolVal(Integer selectRolVal) {
        this.selectRolVal = selectRolVal;
    }

    public Map<String, String> getRolesSelect() {
        return rolesSelect;
    }

    public void setRolesSelect(Map<String, String> rolesSelect) {
        this.rolesSelect = rolesSelect;
    }

    public List<Usuario> getListUsuario() {
        return listUsuario;
    }

    public void setListUsuario(List<Usuario> listUsuario) {
        this.listUsuario = listUsuario;
    }
    
    
}
