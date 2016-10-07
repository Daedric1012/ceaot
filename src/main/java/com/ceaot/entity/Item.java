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
    //find all items by collecter
    @NamedQuery(name = "FindItemsByCollector", query = "SELECT c FROM Item c WHERE c.owner = :uname AND c.removed = false"),
    //find items for sale
    @NamedQuery(name = "ItemsForSale", query = "SELECT c FROM Item c WHERE c.isForSale = true AND c.removed = false"),
    //find all items that fit into a category
    @NamedQuery(name = "allItemsByCatagory", query = "SELECT c FROM Item c WHERE c.category = :category AND c.removed = false"),
    //get all items
    @NamedQuery(name = "getAllItems", query = "SELECT c FROM Item c WHERE c.removed = false"),
    
    @NamedQuery(name = "searchByDescription", query = "SELECT c FROM Item c WHERE c.removed = false AND UPPER(c.itemDes) LIKE UPPER(:des)"),
    
    @NamedQuery(name = "searchByDescriptionAndCatagory", query = "SELECT c FROM Item c WHERE c.removed = false AND UPPER(c.itemDes) LIKE UPPER(:des) AND  c.category = :category"),
    //get item by id
    @NamedQuery(name = "getItemById", query = "SELECT c FROM Item c WHERE c.id = :id AND c.removed = false")
})
public class Item implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // uniqe key for each user and used as FK in other tables
    
    @ManyToOne
    private Collector owner;
    
    private String itemDes;
    
    private String itemName;
    
    private String category;
    
    private boolean isForSale;
    
    private String photoLinks;
    
    private String price;
    
    private String paymentMethod;
    
    //because im not deleting items just making them not show up anymore.
    private boolean removed = false;
    
    @ManyToMany (cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Collector> interestedIn;
    
    //comments will be retrived through the ItemEJB or the CollectorEJB as they don't need their own controller.
    @ManyToMany (cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> comments;

    //getters and setters
    
    //used to provide string version of id
    
    public String getStringID(){
        if(id == null){
            return null;
        }
        String temp = Long.toString(id);
        return temp;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Collector getOwner() {
        return owner;
    }

    public void setOwner(Collector owner) {
        this.owner = owner;
    }

    public String getItemDes() {
        return itemDes;
    }

    public void setItemDes(String itemDes) {
        this.itemDes = itemDes;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isIsForSale() {
        return isForSale;
    }

    public void setIsForSale(boolean isForSale) {
        this.isForSale = isForSale;
    }

    public String getPhotoLinks() {
        return photoLinks;
    }

    public void setPhotoLinks(String photoLinks) {
        this.photoLinks = photoLinks;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public List<Collector> getInterestedIn() {
        return interestedIn;
    }

    public void setInterestedIn(List<Collector> interestedIn) {
        this.interestedIn = interestedIn;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    
}
