/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceaot.controller;

import com.ceaot.ejb.ItemEJB;
import com.ceaot.entity.Collector;
import com.ceaot.entity.Item;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author stephankranenfeld
 */
@RequestScoped
@Named(value = "itemController")
public class ItemController {

    // sets up the UserEJB
    @Inject
    private ItemEJB itemEJB;

    //gives the item access to the user information
    @Inject
    private CollectorController usr;

    private String searchCategory; // Stores item category for search.
    private Long itemNum; //Stores Item Number for search.
    private String searchString; // Holds the search string from search pages
    private List<Item> items; // holds results of Item search
    private Item singleItem;

    //used for creating a new item and updating an item.
    private Collector owner;// set this to the user logged in.
    private String itemName;
    private String itemDes;
    private String category;
    private boolean isForSale;
    private String photoLinks;
    private String price;
    private String paymentMethod;

    FacesContext ctx = FacesContext.getCurrentInstance();

    //regester an item
    public String addItem() {
        owner = usr.getCltr();
        Item tempItem = new Item();
        if(owner == null){
            
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "owner = null", ""));
            return null;
        }
        tempItem.setOwner(owner);
        tempItem.setItemName(itemName);
        tempItem.setItemDes(itemDes);
        tempItem.setCategory(category);
        tempItem.setIsForSale(isForSale);
        tempItem.setPhotoLinks(photoLinks);
        tempItem.setPrice(price);
        tempItem.setPaymentMethod(paymentMethod);
        try {

            //itemEJB.createItem(tempItem);
            owner.setItem(tempItem);
            itemEJB.updateCollector(owner);
            ctx.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "New Item Added", ""));
            usr.updateCltr();
            return "membersHome.xhtml";
        } catch (Exception e) {
            ctx.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registration failed", ""));
            return null;//change to refresh the page.
        }
    }

    //singleItem should be set before this!
    public String updateItem() {
        //owner or ID can't/shouldn't be changed so not here.
        singleItem.setItemName(itemName);
        singleItem.setItemDes(itemDes);
        singleItem.setCategory(category);
        singleItem.setIsForSale(isForSale);
        singleItem.setPhotoLinks(photoLinks);
        singleItem.setPrice(price);
        singleItem.setPaymentMethod(paymentMethod);
        try {
            itemEJB.updateItem(singleItem);
            return null;//change to refresh page and add user feedabck
        } catch (Exception e) {
            ctx.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "update failed", ""));
            return null;//change to refresh the page.
        }
    }


    public String searchByNum() {
        singleItem = itemEJB.getItemById(itemNum);
        return "searchList.xhtml";
    }
    
    public String getItem(){
        singleItem = itemEJB.getItemById(itemNum);
        return "viewMyItem.xhtml";
    }

    //TO DO Search by description into a List<Items> items
    public String searchByDes(){
        return "searchList.xhtml";
    }
    
    //TO DO Search by description into a List<Items> items, where items are For Sale
    public String searchByDesForSale(){
        return "searchList.xhtml";
    }

    //TO DO Search by description into a List<Items> items, where items are not for sale
    public String searchByDesNotForSale(){
        return "searchList.xhtml";
    }    

    //gets all items in database
    public List<Item> getAllItems() {
        items = itemEJB.getAllItems();
        return items;
    }
    
    // TO DO
    //Removes an Item from view without deleting it
    public String deleteItem(Item item) {
        ctx.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Removed", ""));
        return "membersHome.xhtml";
    }

    //<editor-fold defaultstate="collapsed" desc="getters and setters for the xhtml code to use.">
    public Item getSingleItem() {
        return singleItem;
    }

    public void setSingleItem(Item singleItem) {
        this.singleItem = singleItem;
    }

    public Collector getOwner() {
        return owner;
    }

    public void setOwner(Collector owner) {
        this.owner = owner;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDes() {
        return itemDes;
    }

    public void setItemDes(String itemDes) {
        this.itemDes = itemDes;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    /**
     * @return the category
     */
    public String getSearchCategory() {
        return searchCategory;
    }

    /**
     * @param category the category to set
     */
    public void setSearchCategory(String category) {
        this.searchCategory = category;
    }

    /**
     * @return the itemNum
     */
    public Long getItemNum() {
        return itemNum;
    }

    /**
     * @param itemNum the itemNum to set
     */
    public void setItemNum(Long itemNum) {
        this.itemNum = itemNum;
    }

    /**
     * @return the searchString
     */
    public String getSearchString() {
        return searchString;
    }

    /**
     * @param searchString the searchString to set
     */
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    /**
     * @return the items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    //</editor-fold>
}
