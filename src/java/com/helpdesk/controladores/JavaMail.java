/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpdesk.controladores;

import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author ALEX
 */
public class JavaMail {

    

    public static boolean SendMessage(String destino,String subject,String html) {
        boolean success = false;
        try {
            String remitente = "soporte24hd@gmail.com";
            String clave = "fikmtcqsqtvrjjvy";
            Properties p = new Properties();
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.put("mail.smtp.port", "587");
            p.put("mail.smtp.auth", "true");
            p.put("mail.smtp.starttls.enable", "true");
            p.put("mail.smtp.user", remitente);
            p.put("mail.smtp.clave", clave);
            Session s = Session.getDefaultInstance(p);
            MimeMessage mensaje = new MimeMessage(s);
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
            mensaje.setSubject(subject);
            //mensaje.setText("hola mundo :v");
            BodyPart texto = new MimeBodyPart();
            texto.setContent(html, "text/html");
            /*
            BodyPart archivos = new MimeBodyPart();
            archivos.setDataHandler(new DataHandler(new FileDataSource("C:\\Users\\Hernandez\\carritocompras\\Desktop\\oracle.png")));
            archivos.setFileName("base de datos 2");*/

            MimeMultipart todo = new MimeMultipart();
            todo.addBodyPart(texto);
            //todo.addBodyPart(archivos);
            mensaje.setContent(todo);

            Transport t = s.getTransport("smtp");
            t.connect("smtp.gmail.com", remitente, clave);
            t.sendMessage(mensaje, mensaje.getAllRecipients());
            t.close();
            System.out.println("correo Enviado");
            success = true;
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
        return success;
    }

}
