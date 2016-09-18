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
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
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
    //user entered values
    private String userName;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String password;
    private byte[] encryptedPass;

    //to test usr creation
    public String register() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            Collector c = new Collector();
            c.setFirstName("test");
            c.setLastName("test");
            c.setEmailAddress(userName);//for testing
            c.setUsername(userName);
            c.setPhoneNumber("test");

            byte[] salt = generateSalt();
            //will throw null pointer if password is not set. please remember this!
            encryptedPass = getEncryptedPassword(password, salt);

            c.setSalt(salt);
            c.setPassword(encryptedPass);

            try {
                userEJB.createCollector(c);
            } catch (Exception e) {
                ctx.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Failed to create new collector " + e, ""));
                return "errorpage.xhtml";
            }

            FacesMessage msg = new FacesMessage("worked", "worked");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            ctx.addMessage("registerForum", msg);
            return null;
            
            //encryption throws these erros so it contains all this.
        } catch (InvalidKeySpecException | NoSuchAlgorithmException ex) {
            return null;
        }

    }

    
    //<editor-fold defaultstate="collapsed" desc="used for password encryption">
    public boolean authenticate(String attempt, byte[] encryptedPassword, byte[] salt) throws InvalidKeySpecException, NoSuchAlgorithmException {

        byte[] encryptedAttemptedPassword = getEncryptedPassword(attempt, salt);

        return Arrays.equals(encryptedAttemptedPassword, encryptedPassword);
    }

    public byte[] getEncryptedPassword(String password, byte[] salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String algorithm = "PBKDF2WithHmacSHA1";
        int derivedKeyLength = 160;
        int iterations = 20000;

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);

        SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
        return f.generateSecret(spec).getEncoded();

    }

    public byte[] generateSalt() throws NoSuchAlgorithmException {

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

        byte[] salt = new byte[8];
        random.nextBytes(salt);

        return salt;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getters and setters for the xhtml code to use.">
    public UserEJB getUserEJB() {
        return userEJB;
    }

    public void setUserEJB(UserEJB userEJB) {
        this.userEJB = userEJB;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //</editor-fold>
}
