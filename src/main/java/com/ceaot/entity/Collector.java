/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceaot.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.*;

/**
 *
 * @author stephankranenfeld
 */
@Entity
@NamedQueries({
    //used to check a login.
    @NamedQuery(name = "FindCollectorQuery", query = "SELECT c FROM Collector c WHERE c.username = :uname"),
    //not needed for now but may way want this for something later.
    @NamedQuery(name = "EmailQuery", query = "SELECT c FROM Collector c WHERE c.emailAddress = :email")
})
public class Collector implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // uniqe key for each user and used as FK in other tables

    @Column(length = 10, nullable = false, unique = true)
    private String username;
    
    @Column(length = 10, nullable = false)
    private String firstName;
    
    @Column(length = 10, nullable = false)
    private String lastName;

    //@Column(nullable = false)
    private byte[] password;
    
   // @Column(nullable = false)
    private byte[] salt;
    
    @Column(nullable = false)
    private String phoneNumber;

    @Column(length = 30, nullable = false, unique = true)
    private String emailAddress; // cant have the same email with multiple accounts. kept private for admin/server use only

    private Boolean isAdmin = false;// must be true to access admin controlls set on pages

    private Boolean loggedIn = false;// makes sure only 1 user is logged in at a time.
    
    @OneToMany (cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Item> items;
    
    //this is comments from other users onto the users home page eg: "good collecter, honest and quick to respond"
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> commentsAboutMe;

    public Long getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Comment> getcommentsAboutMe() {
        return commentsAboutMe;
    }

    public void setcommentsAboutMe(List<Comment> commentsAboutMe) {
        this.commentsAboutMe = commentsAboutMe;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
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
    
    
}
