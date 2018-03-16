/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.cincamimis;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.cincamimis.complementary.ComplementaryData;

/**
 * It is responsible for managing the measures 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="Measure")
@XmlType(propOrder={"quantitative","complementaryData"})
public class Measure implements Serializable{
    /**
     * It represents the measure itself which could be deterministic or not.
     */
    private Quantitative quantitative;
    /**
     * It represents the complementary data associated with the measure
     */
    private ComplementaryData complementaryData;
    
    /**
     * Default constructor
     */
    public Measure()
    {
        quantitative=null;
        complementaryData=null;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb=new StringBuilder();
        if(getQuantitative()!=null) sb.append(getQuantitative().toString());
        if(getComplementaryData()!=null) sb.append(" [with complementary data]");
        
        return sb.toString();
    }

    /**
     * @return the quantitative measure which could be deterministic or not
     */
    @XmlElement(name="quantitative")
    public Quantitative getQuantitative() {
        return quantitative;
    }

    /**
     * @param quantitative the quantitative to set
     */
    public void setQuantitative(Quantitative quantitative) {
        this.quantitative = quantitative;
    }

    /**
     * @return the complementaryData associated with the measure
     */
    @XmlElement(name="complementaryData")
    public ComplementaryData getComplementaryData() {
        return complementaryData;
    }

    /**
     * @param complementaryData the complementaryData to set
     */
    public void setComplementaryData(ComplementaryData complementaryData) {
        this.complementaryData = complementaryData;
    }
 
    /**
     * It creates a deterministic measure from a given value
     * @param detValue the deterministic value to be incorporated in the Measure object
     * @return The measure object containing the deterministic value
     */
    public static Measure factoryDeterministicMeasureWithoutCD(BigDecimal detValue)
    {
        if(detValue==null) return null;
        
        Measure m=new Measure();
        m.setQuantitative(Quantitative.factoryDeterministicQuantitativeMeasure(detValue));
        
        return m;
    }

    /**
     * It creates an estimated measure from an ArrayList object
     * @param ld_list The ArrayList object with one or more Estimated instances
     * @return A Measure object containing the likelihood distribution
     * @throws LikelihoodDistributionException 
     */
    public static Measure factoryEstimatedMeasureWithoutCD(ArrayList<Estimated> ld_list) throws LikelihoodDistributionException
    {
        if(ld_list==null || ld_list.isEmpty()) return null;
        
        Measure m=new Measure();
        m.setQuantitative(Quantitative.factoryEstimatedQuantitativeMeasure(ld_list));
        
        return m;
    }

    /**
     * It creates an estimated measure object from a LikelihoodDistribution object
     * @param ld The likelihood object to be included in the Measure object
     * @return A Measure object containing the likelihood distribution
     * @throws LikelihoodDistributionException 
     */
    public static Measure factoryEstimatedMeasureWithoutCD(LikelihoodDistribution ld) throws LikelihoodDistributionException
    {
        if(ld==null || ld.getLikelihoodDistributions()==null || ld.getLikelihoodDistributions().isEmpty()) return null;
        
        Measure m=new Measure();
        m.setQuantitative(Quantitative.factoryEstimatedQuantitativeMeasure(ld));
        
        return m;
    }
    
}
