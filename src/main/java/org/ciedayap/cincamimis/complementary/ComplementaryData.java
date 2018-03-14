/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.cincamimis.complementary;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * It is responsible for consolidating all the complementary data
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="complementaryData")
@XmlType(propOrder={"complementaryDatum"})
public class ComplementaryData {
    /**
     * The set of complementary data associated with a given measurement
     */
    private ArrayList<ComplementaryDatum> complementaryDatum;

    /**
     * @return the complementaryDatum
     */
    @XmlElement(name="complementaryDatum")
    public ArrayList<ComplementaryDatum> getComplementaryDatum() {
        return complementaryDatum;
    }

    /**
     * @param complementaryDatum the complementaryDatum to set
     */
    public void setComplementaryDatum(ArrayList<ComplementaryDatum> complementaryDatum) {
        this.complementaryDatum = complementaryDatum;
    }
    
    @Override
    public String toString()
    {
        if(complementaryDatum!=null) return "Items: "+complementaryDatum.size();
        
        return "0";
    }
    
    /**
     * Add a complementaryDatum to the list
     * @param cd The complementary datum to be added
     */
    public void add(ComplementaryDatum cd)
    {
        if(this.complementaryDatum==null) this.complementaryDatum=new ArrayList();
        complementaryDatum.add(cd);
    }
    
    /**
     * It allows deleting by index an object from the list
     * @param index index between 0 and complementaryDatum.size()
     */
    public void remove(int index)
    {
        if(index<0 || complementaryDatum==null || complementaryDatum.size()<index) return;
        complementaryDatum.remove(index);
    }
    
    /**
     * It allows deleting a complementary datum from the list
     * @param cd 
     */
    public void remove(ComplementaryDatum cd)
    {
        if(complementaryDatum==null || cd==null) return;
        complementaryDatum.remove(cd);
    }
    
    /**
     * it removes all the objects from the list
     */
    public void clearAll()
    {
        if(complementaryDatum!=null) complementaryDatum.clear();
    }
}
