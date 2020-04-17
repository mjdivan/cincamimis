/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.cincamimis;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

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

    public String measureToText()
    {
        if(measurementItems==null || measurementItems.isEmpty()) return null;
        
        StringBuilder sb=new StringBuilder();
        for(MeasurementItem mi: measurementItems)
        {
            sb.append(mi.measureToText());
        }
        
        return sb.toString();
    }
    
    public static MeasurementItemSet fromText(String val) throws LikelihoodDistributionException, NoSuchAlgorithmException
    {
        if(val==null || val.trim().equalsIgnoreCase("")) return null;
        
        String items[]=val.split("\\*");
        if(items==null || items.length<1) return null;
        
        MeasurementItemSet mis=new MeasurementItemSet();
        for(int i=0;i<items.length;i++)
        {
            MeasurementItem it=MeasurementItem.fromText(items[i]);
            if(it!=null) mis.add(it);
        }
        
        return (mis.getItemsQuantity()>0)?mis:null;
    }
    
   public static void main(String args[]) throws LikelihoodDistributionException, NoSuchAlgorithmException
   {
       MeasurementItemSet mis=new MeasurementItemSet();
       MeasurementItem mi;
       for(int i=0;i<5;i++) 
       {
           mi=MeasurementItem.factory("IDEntity1","dsid_1", "plaintext", "idMetric1", LikelihoodDistribution.factoryRandomDistributionEqualLikelihood(3L, 5l),"PRJ1","EC1");
           mis.add(mi);
       }
       
       String text=mis.measureToText();
       String xml=TranslateXML.toXml(mis);
       String json=TranslateJSON.toJSON(mis);
       
       System.out.println(text);
       System.out.println("XML: "+(xml.getBytes().length)+" JSON: "+(json.getBytes().length)+"  Brief: "+(text.getBytes().length));
       
       MeasurementItemSet mis2=MeasurementItemSet.fromText(text);
       if(mis2!=null)
       {
           System.out.println("ProjectID\t\tEC-ID\t\tEntityID\t\tdsID\t\tDatetime\t\tMetricID\t\tValue");
           
           for(MeasurementItem mit:mis2.getMeasurementItems())
           {
            System.out.println(mit.getProjectID()+"\t"+
                   mit.getEntityCategoryID() + "\t"+
                   mit.getIdEntity() + "\t"+
                   mit.getDataSourceID()+"\t"+
                   mit.getMeasurement().getDatetime()+ "\t"+
                   mit.getMeasurement().getIdMetric()+ "\t"+
                   mit.getMeasurement().getMeasure().getQuantitative().toString());
           }
       }
       
   }
}
