/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceaot.controller;

import java.io.Serializable;
import javax.ejb.EJB;
import com.ceaot.ejb.UserEJB;
import com.ceaot.entity.Users;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author stephankranenfeld
 */
@Named(value = "UserController")
//session timeout is set to 30 minutes.
@SessionScoped
//this controller is set to session so it stays with the user while they brows the sight.
//it also handles login and registration for users.
//it will not handle retriving random users or messages to avoid buildup of junk on
//a sessionScopped item. my attempt to keep memory use down.
public class UserController implements Serializable {

    // sets up the UserEJB
    @EJB
    private UserEJB userEJB;

    //to test usr creation
    public String run() {
        FacesContext ctx = FacesContext.getCurrentInstance();

        Users u = new Users();
        u.setFirstName("test");
        u.setLastName("test");
        u.setEmailAddress("test");
        u.setUsername("test");

        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[8];
            random.nextBytes(salt);
            u.setPassword(salt);
        } catch (NoSuchAlgorithmException e) {

        }

        try {
            userEJB.createUser(u);
        } catch (Exception e) {
            return "errorpage.xhtml";
        }

        FacesMessage msg = new FacesMessage("worked", "worked");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        ctx.addMessage("registerForum", msg);
        
        return null;

    }

}
