/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceaot.ejb;

import com.ceaot.entity.Collector;
import com.ceaot.entity.Item;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author stephankranenfeld
 */
@Stateless
@LocalBean
public class ItemEJB {

    //persistent unit setup

    @PersistenceContext(unitName = "ceaotPU")
    private EntityManager em;
    @Resource
    SessionContext ctx;

    //creates the item
    public Item createItem(Item itm) {
        em.persist(itm);
        return itm;
    }

    //updates the item
    public void updateItem(Item itm) {
        em.merge(itm);
    }

    //returns the list of topic groups
    public List<Item> getAllItems() {

        //grabs the complete list of topic groups
        Query query = em.createNamedQuery("getAllItems");
        List<Item> itemList = query.getResultList();
        return itemList;
    }

    //get item by id
    public Item getItemById(Long id) {
        TypedQuery<Item> query = em.createNamedQuery("getItemById", Item.class);
        query.setParameter("id", id);
        Item itm = query.getSingleResult();
        return itm;
    }

    public List<Item> getAllItemsByDes(String des, String catagory) {
        List<Item> itemList;
        if (catagory.equals("All")) {
            Query query = em.createNamedQuery("searchByDescription");
            query.setParameter("des", "%" + des + "%");
            itemList = query.getResultList();
        }else{
            Query query = em.createNamedQuery("searchByDescriptionAndCatagory");
            query.setParameter("des", "%" + des + "%");
            query.setParameter("category", catagory);
            itemList = query.getResultList();
        }
        return itemList;
    }

    //updates the user, used when creating a new item.
    public void updateCollector(Collector cltr) {
        em.merge(cltr);
    }
}
