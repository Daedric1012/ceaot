/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceaot.controller;

import java.io.Serializable;
import javax.ejb.EJB;
import com.ceaot.ejb.UserEJB;
import com.ceaot.entity.Collector;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author stephankranenfeld
 */
@Named(value = "userController")
@RequestScoped
public class UserController {

    // sets up the UserEJB
    @Inject
    private UserEJB userEJB;

    //to test usr creation
    public String run() {
        FacesContext ctx = FacesContext.getCurrentInstance();

        Collector c = new Collector();
        c.setFirstName("test");
        c.setLastName("test");
        c.setEmailAddress("test");
        c.setUsername("test");
        c.setPhoneNumber("test");
        c.setPassword("test");

      /*  try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[8];
            random.nextBytes(salt);
            c.setPassword(salt);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Salt failed ", ""));
        return "errorpage.xhtml";

        }
*/
        try {
            userEJB.createCollector(c);
        } catch (Exception e) {
            ctx.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Failed to create new collector "+e, ""));
        return "errorpage.xhtml";
        }

        FacesMessage msg = new FacesMessage("worked", "worked");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        ctx.addMessage("registerForum", msg);

        return null;

    }

}
