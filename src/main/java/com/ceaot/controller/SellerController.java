/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceaot.controller;

import com.ceaot.ejb.CollectorEJB;
import com.ceaot.entity.Collector;
import com.ceaot.entity.Comment;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author stephankranenfeld
 */
@SessionScoped
@Named(value = "sellerController")
public class SellerController implements Serializable {
    
    private Collector seller;
    private String sellerID;
    private String comment;
    
    // sets up the CollectorEJB
    @Inject
    private CollectorEJB collectorEJB;
    
    public void updateSeller(){
        //grabs the user based off ID
        seller = collectorEJB.loggingIn(sellerID);
    }
    
    public String addComment(String sellerID){
        seller = collectorEJB.loggingIn(sellerID);
        Comment cmt = new Comment();
        cmt.setComment(comment);
        cmt.setOwner(seller);
        
        seller.addCommentsAboutMe(cmt);
        
        collectorEJB.updateCollector(seller);
        
        //basically refreshes the page
        return "membersViewItem.xhtml?id=" + sellerID;
    }

    public Collector getSeller() {
        return seller;
    }

    public void setSeller(Collector seller) {
        this.seller = seller;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    
}
