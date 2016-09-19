/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceaot.controller;

import com.ceaot.ejb.ItemEJB;
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
    
}
