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

/**
 * It is responsible for managing the quantitative measures. The quantitative measure could be deterministic or
 * a likelihood distribution, but not both at the same time. The quantitative measure is exclusively deterministic,
 * or it is exclusively represented through a likelihood distribution.
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="quantitative")
@XmlType(propOrder={"likelihoodDistribution","deterministicValue"})
public class Quantitative implements Serializable{
    /**
     * This expresses the quantitative measure such as a likelihood distribution
     */
    private LikelihoodDistribution likelihoodDistribution;
    /**
     * This expresses the quantitative measure such as a deterministic value
     */
    private BigDecimal deterministicValue;

    public Quantitative()
    {
        likelihoodDistribution=null;
        deterministicValue=null;
    }
    
    /**
     * @return the likelihood Distribution related to the measure
     */
    @XmlElement(name="likelihoodDistribution")
    public LikelihoodDistribution getLikelihoodDistribution() {
        return likelihoodDistribution;
    }

    /**
     * @param likelihoodDistribution the likelihood Distribution to set
     */
    public void setLikelihoodDistribution(LikelihoodDistribution likelihoodDistribution) {
        this.likelihoodDistribution = likelihoodDistribution;
        
        if(deterministicValue!=null && likelihoodDistribution!=null)
            this.setDeterministicValue(null);
    }

    /**
     * @return the deterministic Value related to the measure
     */
    @XmlElement(name="deterministicValue")
    public BigDecimal getDeterministicValue() {
        return deterministicValue;
    }

    /**
     * @param deterministicValue the deterministicValue to set
     */
    public void setDeterministicValue(BigDecimal deterministicValue) {
        this.deterministicValue = deterministicValue;
        if(this.getLikelihoodDistribution()!=null && deterministicValue!=null)
            this.getLikelihoodDistribution().clearAll();
    }

    @Override
    public String toString()
    {
        StringBuilder sb=new StringBuilder();
        if(this.getDeterministicValue()!=null) sb.append("[Deterministic] ").append(this.getDeterministicValue());
        if(this.getLikelihoodDistribution()!=null && this.getLikelihoodDistribution().getLikelihoodDistributions()!=null) sb.append("[LikelihoodDistribution] Items: ").append(this.getLikelihoodDistribution().getLikelihoodDistributions().size());
        
        return sb.toString();
    }
    
    /**
     * It creates an Quantitative object from the deterministic value
     * @param deterministicValue The deterministic value to be incorporated in the Quantitative 
     * @return The Quantitative object representing the deterministic value
     */
    public static Quantitative factoryDeterministicQuantitativeMeasure(BigDecimal deterministicValue)
    {
        if(deterministicValue==null) return null;
        
        Quantitative q=new Quantitative();
        q.setDeterministicValue(deterministicValue);
        
        return q;
    }        

    /**
     * It creates an Quantitative object with the likelihood distribution
     * @param ld the likelihhod distribution to be included inside the Quantitative object
     * @return The Quantitative object with the likelihood distribution
     */
    public static Quantitative factoryEstimatedQuantitativeMeasure(LikelihoodDistribution ld)
    {

        if(ld==null || ld.getLikelihoodDistributions()==null) return null;
        if(ld.getLikelihoodDistributions().isEmpty()) return null;

        Quantitative q=new Quantitative();
        q.setLikelihoodDistribution(ld);
        
        return q;
    }        

    /**
     * It creates an Quantitative object with a likelihood distribution but from a ArrayList in which
     * each object is Estimated
     * @param ld_list array list with objects related to the Estimated type
     * @return A Quantitative object with the likelihood distribution
     * @throws LikelihoodDistributionException 
     */
    public static Quantitative factoryEstimatedQuantitativeMeasure(ArrayList<Estimated> ld_list) throws LikelihoodDistributionException
    {
        if(ld_list==null || ld_list.isEmpty()) return null;
                
        Quantitative q=new Quantitative();
        q.setLikelihoodDistribution(LikelihoodDistribution.factoryLikelihoodDistribution(ld_list));
        
        return q;
    }        
    
}
