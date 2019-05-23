package com.queso.mb;

import com.queso.dao.LoginDao;
import com.queso.util.UtilVarios;
import com.quesos.entities.Usuario;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped //inyeccion de mb para traer algun tipo de servicio
public class LoginMB {

    @EJB//inyeccion de dependencia de EJB (Enterprisse JAVA Bean)
    private LoginDao loginDao; //para acceder a los datos del usuario desde la base de datos
    private Usuario usuario; //para usar los datos del usuario en la lista
    private String rol;
    private boolean verBotonGuardarFactura; //para mostrar o no el boton guardar factura
    private boolean verBotonActualizarFactura; //para mostrar o no el botn de actualizar factura
    private boolean verBotonGuardarUsuario;
    private boolean estaLogeado;//para verificar que esté logeado

    @PostConstruct//inicializamos aca porque el EJB ya lo inicializo
    public void init() {
        usuario = new Usuario();//inicializando obj usaurio
        verBotonGuardarFactura = false;
        verBotonActualizarFactura = false;
        verBotonGuardarUsuario = false;
        estaLogeado = false;
    }

    public String logearUsuario() {
        //trae usuario desde dao que existe en la base de datos y coincide con
        //nombre de usuario y password
        UtilVarios uv = new UtilVarios();//instanciamos el objeto en utilidades
        String passEnMd5 = uv.convertiraMd5(usuario.getPass());//obtenemos md5
        usuario = loginDao.usuarioLogin(usuario.getUsuario(), passEnMd5);//pasamos valores a dao

        if (null != usuario) {
            rol = usuario.getIdRol().getRol();//seteo el rol del usuario
            //seteo los permisos
            if (rol.equalsIgnoreCase("Administrador")) 
            { //debido a que el admin no puede guardar ni actualizar facturas
                verBotonGuardarFactura = false;
                verBotonActualizarFactura = false;
                verBotonGuardarUsuario = true;
                estaLogeado = true;
            } 
            else if (rol.equalsIgnoreCase("Vendedor")) 
            {
                verBotonActualizarFactura = true;
                verBotonGuardarFactura = true;
                verBotonGuardarUsuario = false;
                estaLogeado = true;
            }
            return "menu.xhtml";
        }
     
        FacesMessage msg = new FacesMessage("Error en usuario o contraseña");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return "index.xhtml";
    }
    
    public void guardarFacturas(){
        
    }

    public LoginDao getLoginDao() {
        return loginDao;
    }

    public void setLoginDao(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isEstaLogeado() {
        return estaLogeado;
    }

    public void setEstaLogeado(boolean estaLogeado) {
        this.estaLogeado = estaLogeado;
    }

    public boolean isVerBotonGuardarUsuario() {
        return verBotonGuardarUsuario;
    }

    public void setVerBotonGuardarUsuario(boolean verBotonGuardarUsuario) {
        this.verBotonGuardarUsuario = verBotonGuardarUsuario;
    }
    
    
}
