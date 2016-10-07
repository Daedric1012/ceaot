/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceaot.controller;

import java.io.Serializable;
import com.ceaot.ejb.CollectorEJB;
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
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author stephankranenfeld
 */
@SessionScoped
@Named(value = "collectorController")
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
    private String password2;//used to match passwords, not implimented yet
    private byte[] encryptedPass;

    //set only when user is logged in
    private Collector cltr;
    //used to handle sorting people who ar logged in or out.
    //this allows showing different controls when someone is logged in or out
    private boolean loggedIn;

    //to test cltr creation
    public String register() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            //add in check passwords match
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
            loggedIn = true;
            cltr = c;
            cltr.setLoggedIn(loggedIn);
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
                return "index.xhtml";
            } else {//if username is found but no password. same error message returned.
                loggedIn = false;
                ctx.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username or password incorrect", ""));
            }
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {

        }
        return null;
    }
    
    public void refreshCltr(){
        cltr = collectorEJB.loggingIn(cltr.getUsername());
    }

    //when a collecter adds an item it's not updating this clt updates the user
    public String updateCltr(){
        cltr = collectorEJB.loggingIn(userName);
        return null;
    }

    //clears the session details. before deltion or on logout.
    //for some reason have to press the sign out button twice to work.
    public String logMeOut() {
        loggedIn = false;
        cltr = null;
        return "index.xhtml";
    }
    
    //used to redirect the user back to the home page if they are not logged in
    public String amiloggedin(){
        if(!loggedIn){
            return "index.xhtml";
        }
        //if logged in do nothing.
        return null;
    }

    // Update the collectors details 
    public String updateCollector() {
        collectorEJB.updateCollector(cltr);
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Your details have been updated","")); 
        return null;
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

    public String getPassword2() {
        return password;
    }

    public void setPassword2(String password) {
        this.password = password;
    }
    //</editor-fold>
}
