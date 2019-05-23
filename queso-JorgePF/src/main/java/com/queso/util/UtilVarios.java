package com.queso.util;

import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;

public class UtilVarios {

    public String convertiraMd5(String str) {
        String miPass = null;//String quye va devolver en md5
        str = str.trim();//al argumento le quitamos los espacios de derecha a inzquierda
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");//instanciamos el encriptador
            md.update(str.getBytes());//seteamos valor en encriptador
            byte[] digest = md.digest();//obtenemos objeto digest para convertir en md5
            miPass = DatatypeConverter.printHexBinary(digest).toLowerCase();
            System.out.println("miPass = " + miPass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return miPass;
    }
}
