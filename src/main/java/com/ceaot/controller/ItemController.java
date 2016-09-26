/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceaot.controller;

import com.ceaot.ejb.ItemEJB;
import com.ceaot.entity.Item;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author stephankranenfeld
 */

@Named(value = "itemController") 
@RequestScoped
public class ItemController {
    
    // sets up the UserEJB
    @Inject
    private ItemEJB itemEJB;
    
    private String category; // Stores item category.
    private Long itemNum; //Stores Item Number
    private String searchString; // Holds the search string from search pages
    private List<Item> items; // holds results of Item search
    
    
    //TO DO search by Item Number
    private String searchByNum(){
        return "searchList.xhtml";
    }
    
    //TO DO Search by description
    private String searchByDes(){
        return "searchList.xhtml";
    }

    /**
     * @return the itemEJB
     */
    public ItemEJB getItemEJB() {
        return itemEJB;
    }

    /**
     * @param itemEJB the itemEJB to set
     */
    public void setItemEJB(ItemEJB itemEJB) {
        this.itemEJB = itemEJB;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
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
    
}
