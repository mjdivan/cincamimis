/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.cincamimis;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class is responsible for managing a likelihood distribution as measurement result
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="likelihoodDistribution")
@XmlType(propOrder={"likelihoodDistributions"})
public class LikelihoodDistribution implements Serializable{
    /**
     * A likelihood distribution as measurement result
     */
    private ArrayList<Estimated> likelihoodDistributions;
    
    /**
     * It represents the default constructor
     */
    public LikelihoodDistribution()
    {
     likelihoodDistributions=new ArrayList<>();   
    }
    
    /**
     * @return the likelihood Distribution
     */
    @XmlElement(name="Estimated")
    public ArrayList<Estimated> getLikelihoodDistributions() {
        return likelihoodDistributions;
    }

    /**
     * @param likelihoodDistribution the likelihood Distribution to set
     * @throws org.ciedayap.cincamimis.LikelihoodDistributionException 
     */
    public void setLikelihoodDistribution(ArrayList<Estimated> likelihoodDistribution) throws LikelihoodDistributionException {
        this.likelihoodDistributions = likelihoodDistribution;
        this.verifyDistribution();
    }

    @Override
    public String toString()
    {
        if(this.likelihoodDistributions!=null) return "Items: "+this.likelihoodDistributions.size();
        
        return "0";
    }
    
    /**
     * Add an estimated to the list
     * @param es The estimated pair to be incorporated to the list
     * @throws org.ciedayap.cincamimis.LikelihoodDistributionException
     */
    public void add(Estimated es) throws LikelihoodDistributionException
    {
        if(this.likelihoodDistributions==null) this.likelihoodDistributions=new ArrayList();
        this.likelihoodDistributions.add(es);
        this.verifyDistribution();
    }
    
    /**
     * It allows deleting by index an object from the list
     * @param index index between 0 and likelihoodDistribution.size()
     */
    public void remove(int index)
    {
        if(index<0 || this.likelihoodDistributions==null || this.likelihoodDistributions.size()<index) return;
        this.likelihoodDistributions.remove(index);
    }
    
    /**
     * It allows deleting an estimated pair from the list
     * @param es Estimated pair to be deleted 
     */
    public void remove(Estimated es)
    {
        if(this.likelihoodDistributions==null || es==null) return;
        this.likelihoodDistributions.remove(es);
    }
    
    /**
     * it removes all the objects from the list
     */
    public void clearAll()
    {
        if(this.likelihoodDistributions!=null) this.likelihoodDistributions.clear();
    }  
    
    /**
     * It is responsible for verifying that the accumulative likelihood associated with a likelihood distribution
     * is always lower than 1 (one).
     * @throws LikelihoodDistributionException When the accumulative likelihood is upper than one, or when an estimated value
     * does not have an associated likelihood.
     */
    private void verifyDistribution() throws LikelihoodDistributionException
    {
        if(this.likelihoodDistributions==null || likelihoodDistributions.isEmpty()) return;
        
        BigDecimal ac=BigDecimal.ZERO;
        for(Estimated e:likelihoodDistributions)
        {
            if(e.getLikelihood()==null) throw new LikelihoodDistributionException("An estimated value does not have an associated likelihood");
            ac=ac.add(e.getLikelihood(), MathContext.DECIMAL128);
            
            if(ac.compareTo(BigDecimal.ONE)>0)
                throw new LikelihoodDistributionException("The accumulative likelihood must be lower than one.");
        }         
    }
    
    /**
     * It creates a LikelihoodDistribution from an ArrayList object with a set of Estimated instance 
     * @param ld_list ArrayList with Estimated objects
     * @return A LikelihoodDistribution object incorporating the values from the ArreayList object
     * @throws LikelihoodDistributionException 
     */
    public static LikelihoodDistribution factoryLikelihoodDistribution(ArrayList<Estimated> ld_list) throws LikelihoodDistributionException
    {
        LikelihoodDistribution ld=new LikelihoodDistribution();
        ld.setLikelihoodDistribution(ld_list);
        
        return ld;
    }
    
    /**
     * It creates a likelihood distribution with "number" aleatory numbers from a Gaussian distribution N(0,1).
     * @param number The quantity of random numbers to be generated
     * @param seed A given seed. In the case that the seed has not been provided, the 1L will be established.
     * @return a LikelihoodDistribution with "number" aleatory numbers
     * @throws LikelihoodDistributionException 
     */
    public static LikelihoodDistribution factoryRandomDistributionEqualLikelihood(Long number,Long seed) throws LikelihoodDistributionException
    {
        if(number==null) return null;
        number=Math.abs(number);
        
        BigDecimal likelihood=BigDecimal.ONE.divide(BigDecimal.valueOf(number), MathContext.DECIMAL128);
        Random r=new Random();
        r.setSeed((seed!=null)?seed:1L);
        LikelihoodDistribution ld=new LikelihoodDistribution();
        
        for(int i=0;i<number;i++)
        {
         ld.add(Estimated.factory(BigDecimal.valueOf(r.nextGaussian()), likelihood));
        }
        
        return ld;
    }
}
