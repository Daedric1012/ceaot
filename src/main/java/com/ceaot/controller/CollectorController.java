/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceaot.controller;

import java.io.Serializable;
import javax.ejb.EJB;
import com.ceaot.ejb.CollectorEJB;
import com.ceaot.entity.Collector;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import javax.faces.bean.ManagedBean;

/**
 *
 * @author stephankranenfeld
 */
@Named(value = "collectorController")
@SessionScoped
public class CollectorController implements Serializable {

    // sets up the CollectorEJB
    @Inject
    private CollectorEJB collectorEJB;

    //user entered values
    private String userName;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String password;
    private byte[] encryptedPass;

    //set only when user is logged in
    private Collector cltr;
    //used to handle sorting people who ar logged in or out.
    //this allows showing different controls when someone is logged in or out
    private boolean loggedIn = false;

    //to test cltr creation
    public String register() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            Collector c = new Collector();
            c.setFirstName(firstName);
            c.setLastName(lastName);
            c.setEmailAddress(emailAddress);
            c.setUsername(userName);
            c.setPhoneNumber(phoneNumber);

            byte[] salt = generateSalt();
            //will throw null pointer if password is not set. please remember this!
            encryptedPass = getEncryptedPassword(password, salt);
            c.setSalt(salt);
            c.setPassword(encryptedPass);

            try {
                collectorEJB.createCollector(c);
                //if user 
            } catch (Exception e) {
                ctx.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to create new account " + e, ""));
                return null;

            }
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Your account has been created ", ""));
            return "membersHome.xhtml";

            //encryption throws these erros so it contains all this.
        } catch (InvalidKeySpecException | NoSuchAlgorithmException ex) {
            ctx.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to create new account " + ex, ""));
            return null;
        }
    }

    public String login() {
        try {
            FacesContext ctx = FacesContext.getCurrentInstance();
            cltr = collectorEJB.loggingIn(userName);
            if (cltr == null) {//if no cltr with username is found return this.
                ctx.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username or Password incorrect", "")); 
                return null;
            } else if (authenticate(password, cltr.getPassword(), cltr.getSalt())) {// checks the password matches.
                loggedIn = true;
                cltr.setLoggedIn(loggedIn);
                return "membersHome.xhtml";
            } else {//if username is found but no password. same error message returned.
                cltr = null;//sets back to null on failed to avoid any possible abuse of trying to log into another account.
                ctx.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username or password incorrect", ""));
            }
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {

        }
        return null;
    }

    //clears the session details. before deltion or on logout.
    //@PreDestroy
    public String logout() {
        loggedIn = false;
        cltr = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "You have logged out successfully", ""));
        return "index.xhtml";
    }

    //<editor-fold defaultstate="collapsed" desc="used for password encryption">
    //compares the arrays created after the password is hashed.
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
    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Collector getCltr() {
        return cltr;
    }

    public void setCltr(Collector cltr) {
        this.cltr = cltr;
    }

    public CollectorEJB getCollectorEJB() {
        return collectorEJB;
    }

    public void setCollectorEJB(CollectorEJB collectorEJB) {
        this.collectorEJB = collectorEJB;
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
