/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.cincamimis;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class represents a (value, likelihood) pair which belong to a likelihood distribution
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="Estimated")
@XmlType(propOrder={"value","likelihood"})
public class Estimated implements Serializable{
    /**
     * It represents the value related to a given likelihhood
     */
    private BigDecimal value;
    /**
     * It represents the likelihood related to the given value
     */
    private BigDecimal likelihood;
    
    /**
     * Default constructor
     */
    public Estimated()
    {
        value=null;
        likelihood=null;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb=new StringBuilder();
        sb.append("[").append(getValue()).append("; ").append(getLikelihood()).append("]");
        return sb.toString();
    }

    /**
     * @return the value associated with the likelihood
     */
    @XmlElement(name="value")
    public BigDecimal getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    /**
     * @return the likelihood related to the value
     */
    @XmlElement(name="likelihood")
    public BigDecimal getLikelihood() {
        return likelihood;
    }

    /**
     * @param likelihood the likelihood to set
     * @throws org.ciedayap.cincamimis.LikelihoodDistributionException
     */
    public void setLikelihood(BigDecimal likelihood) throws LikelihoodDistributionException {
        if(likelihood==null) throw new LikelihoodDistributionException("The likelihood is null");
        if(likelihood.compareTo(BigDecimal.ZERO)<0) throw new LikelihoodDistributionException("The likelihood value must be contained between 0 and 1");
        if(likelihood.compareTo(BigDecimal.ONE)>0) throw new LikelihoodDistributionException("The likelihood value must be contained between 0 and 1");
       
        this.likelihood = likelihood;
    }
    
    /**
     * It creates an Estimated object from the given value and likelihood
     * @param value The value to be incorporated in the Estimated object
     * @param likelihood The likelihood to be incorporated in the Estimated object
     * @return An Estimated object with the (value, likelihood) pair
     * @throws LikelihoodDistributionException 
     */
    public static Estimated factory(BigDecimal value, BigDecimal likelihood) throws LikelihoodDistributionException    
    {
        if(value==null || likelihood==null) return null;
        Estimated v=new Estimated();
        v.setValue(value);
        v.setLikelihood(likelihood);
        
        return v;
    }
}
