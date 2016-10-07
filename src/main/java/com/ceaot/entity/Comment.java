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
public class Comment implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // uniqe key for each user and used as FK in other tables
    
    //each comment can only have 1 owner but collector can have multiple comments
    @ManyToOne
    private Collector owner;
    
    @ManyToOne
    private Item itm;
    
    //length changed to 250 because 50 just isn't enough for a proper comment.
    @Column(length = 250, nullable = false)
    private String comment;

    public Long getId() {
        return id;
    }

    public void setCommentId(Long id) {
        this.id = id;
    }

    public Collector getOwner() {
        return owner;
    }

    public void setOwner(Collector owner) {
        this.owner = owner;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public void setItm(Item itm){
        this.itm = itm;
    }
    
    public Item getItm(){
        return itm;
    }
    
    
}
