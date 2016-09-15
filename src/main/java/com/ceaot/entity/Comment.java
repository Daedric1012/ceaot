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
    private Long commentId; // uniqe key for each user and used as FK in other tables
    
    @ManyToOne
    private Users owner;
    
    //length changed to 250 because 50 just isn't enough for a proper comment.
    @Column(length = 250, nullable = false)
    private String comment;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    
}
