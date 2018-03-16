/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.cincamimis;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAttribute;
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
@XmlType(propOrder={"itemsQuantity","measurementItems"})
public class MeasurementItemSet implements Serializable{
    /**
     * Quantity of measurements inside the stream
     */
    private Integer itemsQuantity;
    /**
     * Set of measurementItem associated with this window
     */
    private ArrayList<MeasurementItem> measurementItems;
    
    /**
     * Default Constructor
     */
    public MeasurementItemSet()            
    {
        itemsQuantity=0;
        measurementItems=new ArrayList<>();
    }
    /**
     * @return the itemsQuantity
     */
    @XmlAttribute(name="itemsQuantity")
    public Integer getItemsQuantity() {
        return itemsQuantity;
    }

    /**
     * It represents the quantity of items in the list
     * @param itemsQuantity the itemsQuantity to set
     */
    public void setItemsQuantity(Integer itemsQuantity) {
        this.itemsQuantity = itemsQuantity;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb=new StringBuilder();
        if(this.getItemsQuantity()!=null) sb.append("[Items in window: ").append(this.getItemsQuantity()).append("]");
        
        return sb.toString();
    }

    /**
     * @return the measurementItems
     */
    @XmlElement(name="measurementItem")
    public ArrayList<MeasurementItem> getMeasurementItems() {
        return measurementItems;
    }

    /**
     * @param measurementItems the measurementItems to set
     */
    public void setMeasurementItems(ArrayList<MeasurementItem> measurementItems) {
        this.measurementItems = measurementItems;
        
        itemsQuantity=(measurementItems!=null)?measurementItems.size():0;
    }
    
    /**
     * Add a measurement item to the list
     * @param mi The measurement item to be added
     */
    public void add(MeasurementItem mi)
    {
        if(this.measurementItems==null) this.measurementItems=new ArrayList();
        measurementItems.add(mi);
        
        itemsQuantity=(measurementItems!=null)?measurementItems.size():0;
    }
    
    /**
     * It allows deleting by index an object from the list
     * @param index index between 0 and measurementItems.size()
     */
    public void remove(int index)
    {
        if(index<0 || measurementItems==null || measurementItems.size()<index) return;
        measurementItems.remove(index);
        
        itemsQuantity=(this.getMeasurementItems()!=null)?this.getMeasurementItems().size():0;
    }
    
    /**
     * It allows deleting a measurement item from the list
     * @param mi measurement item to be deleted 
     */
    public void remove(MeasurementItem mi)
    {
        if(measurementItems==null || mi==null) return;
        measurementItems.remove(mi);
        itemsQuantity=(measurementItems!=null)?measurementItems.size():0;
    }
    
    /**
     * it removes all the objects from the list
     */
    public void clearAll()
    {
        if(measurementItems!=null) measurementItems.clear();
        itemsQuantity=(measurementItems!=null)?measurementItems.size():0;
    }

   
}
