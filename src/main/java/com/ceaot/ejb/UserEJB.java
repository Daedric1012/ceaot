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

import com.ceaot.entity.Collector;

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
    public Collector createCollector(Collector usr){
        em.persist(usr);
        return usr;
    }
    
    //updates the user
    public void updateCollector(Collector usr){
        em.merge(usr);
    }
    
    //gets the user by usrname.
    public Collector loggingIn(String usrN){
        TypedQuery<Collector> query = em.createNamedQuery("FindCollectorQuery", Collector.class);
        query.setParameter("uname", usrN);
        try{
        Collector usr = query.getSingleResult();
        return usr;
        } catch (NoResultException e){
            //might change this to throw an exception to catch on the higher level.
            return null;
        }
    }
}
