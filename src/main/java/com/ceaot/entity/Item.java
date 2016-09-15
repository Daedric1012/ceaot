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
public class Item implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId; // uniqe key for each user and used as FK in other tables
    
    @ManyToOne
    private Users owner;
    
    @OneToMany
    private List<Users> interestedIn;
    
}
