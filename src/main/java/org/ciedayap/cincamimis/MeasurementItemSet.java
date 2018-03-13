/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.cincamimis;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Container of the set of measurements
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="measurementItemSet")
@XmlType(propOrder={"itemsQuantity"})
public class MeasurementItemSet {
    /**
     * Quantity of measurements inside the stream
     */
    private Integer itemsQuantity;

    /**
     * @return the itemsQuantity
     */
    @XmlElement(name="itemsQuantity")
    public Integer getItemsQuantity() {
        return itemsQuantity;
    }

    /**
     * @param itemsQuantity the itemsQuantity to set
     */
    public void setItemsQuantity(Integer itemsQuantity) {
        this.itemsQuantity = itemsQuantity;
    }
    
    @Override
    public String toString()
    {
        return String.valueOf(this.getItemsQuantity());
    }
}
