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
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

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
    
    /**
     * This represents one or more values such as pairs  (value, likelihood).
     * When there not exist value, a null is returned.
     * @return A string organized as a sequence of (value; likelihood), null when there not exist a value
     */
    public String measureToText()
    {
        if(this.deterministicValue==null && this.likelihoodDistribution==null) return null;
        StringBuilder sb=new StringBuilder();
        
        if(deterministicValue!=null)
            sb.append("(").append(deterministicValue).append(";1)");
        else
        {
            if(this.likelihoodDistribution.getLikelihoodDistributions()==null ||
                    this.likelihoodDistribution.getLikelihoodDistributions().isEmpty()) return null;
            
            for(Estimated est:this.likelihoodDistribution.getLikelihoodDistributions())
            {
                sb.append("(").append(est.getValue()).append(";").append(est.getLikelihood()).append(")");
            }
        }
        
        return sb.toString();
    }
    
    public static Quantitative fromText(String val) throws LikelihoodDistributionException
    {
        if(val==null || val.trim().equalsIgnoreCase("")) return null;
        
        ArrayList<Estimated> volume=new ArrayList();
        String elems[]=val.split("\\(");
        for(int i=0;i<elems.length;i++)
        {
            elems[i]=elems[i].replace(")", "");
            if(elems[i]!=null && elems[i].contains(";"))
            {
                String components[]=elems[i].split(";");
                BigDecimal value=new BigDecimal(components[0]);
                BigDecimal likelihood=new BigDecimal(components[1]);
                
                volume.add(Estimated.factory(value, likelihood));
            }
        }
        
        if(volume.isEmpty()) return null;
        
        if(volume.size()==1)
        {//Deterministic
            return Quantitative.factoryDeterministicQuantitativeMeasure(volume.get(0).getValue());
        }

        return Quantitative.factoryEstimatedQuantitativeMeasure(volume);
    }
    
    public static void main(String args[]) throws LikelihoodDistributionException
    {
        LikelihoodDistribution ld=LikelihoodDistribution.factoryRandomDistributionEqualLikelihood(3L, 5L);        
        Quantitative q=Quantitative.factoryEstimatedQuantitativeMeasure(ld);
        String text=q.measureToText();
        System.out.println(text);
        
       //Obtaining the Object
       Quantitative ptr=Quantitative.fromText(text);
       System.out.println(ptr.toString());
       ArrayList<Estimated> list=ptr.getLikelihoodDistribution().getLikelihoodDistributions();
       for(Estimated est:list)
       {
           System.out.println("Value: "+est.getValue()+" Likelihood: "+est.getLikelihood());
       }
               
        q=Quantitative.factoryDeterministicQuantitativeMeasure(new BigDecimal("7.68954"));
        text=q.measureToText();
        System.out.println(text);
       System.out.println(TranslateXML.toXml(ptr));
       System.out.println(TranslateJSON.toJSON(ptr));
        
        
       //Obtaining the Object
       ptr=Quantitative.fromText(text);
       System.out.println(ptr.toString());              
       
       System.out.println(TranslateXML.toXml(ptr));
       System.out.println(TranslateJSON.toJSON(ptr));
       
    }
}
