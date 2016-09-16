/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceaot.ejb;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import javax.ejb.EJBException;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.persistence.NoResultException;

import com.ceaot.entity.Users;

/**
 *
 * @author stephankranenfeld
 */

@Stateless
@LocalBean
public class UserEJB {
    
    //persistent unit setup
    @PersistenceContext(unitName = "ceaotPU")
    private EntityManager em;
    @Resource
    SessionContext ctx;
    
    //creates the user
    public Users createUser(Users usr){
        em.persist(usr);
        return usr;
    }
    
    //updates the user
    public void updateUser(Users usr){
        em.merge(usr);
    }
    
    //gets the user by usrname.
    public Users loggingIn(String usrN){
        TypedQuery<Users> query = em.createNamedQuery("FindUserQuery", Users.class);
        query.setParameter("uname", usrN);
        try{
        Users usr = query.getSingleResult();
        return usr;
        } catch (NoResultException e){
            //might change this to throw an exception to catch on the higher level.
            return null;
        }
    }
}
