/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.cincamimis;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.ciedayap.cincamimis.adapters.ZonedDateTimeAdapter;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

/**
 * It responsible for managing the measurement information in a given instant
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="Measurement")
@XmlType(propOrder={"datetime","idMetric","measure"})
public class Measurement implements Serializable{
    /**
     * It represents the instant in which the measurement was made
     */
    private java.time.ZonedDateTime datetime;
    /**
     * It represents the ID metric in CINCAMI project related to this specific measurement
     */
    private String idMetric;
    /**
     * It represents the measure (deterministic or not) including the complementary data (optional)
     */
    private Measure measure;
    
    /**
     * Contructor by default. It is responsible for assigning the time instant to the datatime attribute
     */
    public Measurement()
    {
        datetime=java.time.ZonedDateTime.now();
        idMetric=null;
        measure=null;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb=new StringBuilder();
        if(getDatetime()!=null) sb.append("[").append(getDatetime()).append("] ");
        if(getIdMetric()!=null) sb.append("IDMetric: ").append(getIdMetric());
        
        return sb.toString();
    }

    /**
     * @return the datetime
     */
    @XmlElement(name="datetime")
    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)    
    public java.time.ZonedDateTime getDatetime() {
        return datetime;
    }

    /**
     * @param datetime the datetime to set
     */
    public void setDatetime(java.time.ZonedDateTime datetime) {
        this.datetime = datetime;
    }

    /**
     * @return the idMetric
     */
    @XmlElement(name="idMetric")
    public String getIdMetric() {
        return idMetric;
    }

    /**
     * @param idMetric the idMetric to set
     */
    public void setIdMetric(String idMetric) {
        this.idMetric = idMetric;
    }

    /**
     * @return the measure
     */
    @XmlElement(name="Measure")
    public Measure getMeasure() {
        return measure;
    }

    /**
     * @param measure the measure to set
     */
    public void setMeasure(Measure measure) {
        this.measure = measure;
    }
    
    /**
     * It creates a Measurement object from the idMetric and the deterministic value
     * 
     * @param idmetric ID associated with the metric in the M and E project
     * @param deterministicValue The deterministic value to be incorporated in the Measurement object
     * @return A measurement objetct with the deterministic value
     */
    public static Measurement factoryMeasurementWithoutCD(String idmetric,BigDecimal deterministicValue)
    {
        if(idmetric==null || deterministicValue==null) return null;
        
        Measurement m=new Measurement();
        m.setIdMetric(idmetric);
        m.setMeasure(Measure.factoryDeterministicMeasureWithoutCD(deterministicValue));
        
        return m;
    }
   
    /**
     * It creates a Measurement object from the idmetric and the likelihood distribution
     * @param idmetric ID associated with the metric in the M and E projects
     * @param ld The likelihood distribution
     * @return A Measurement object with the idmetric and the likehihood distribution
     * @throws LikelihoodDistributionException 
     */
    public static Measurement factoryMeasurementWithoutCD(String idmetric,LikelihoodDistribution ld) throws LikelihoodDistributionException
    {
        if(idmetric==null || ld==null || ld.getLikelihoodDistributions()==null) return null;
        if(ld.getLikelihoodDistributions().isEmpty()) return null;
        
        Measurement m=new Measurement();
        m.setIdMetric(idmetric);
        m.setMeasure(Measure.factoryEstimatedMeasureWithoutCD(ld));
        
        return m;
    }

    /**
     * It creates a Measurement object from the idMetric and an ArrayList object with a set of Estimated instance
     * @param idmetric ID associated with the metric in the M and E project
     * @param ld_list List of Estimated objects
     * @return A Measurement object with the idmetric and the likelihood distribution
     * @throws LikelihoodDistributionException 
     */
    public static Measurement factoryMeasurementWithoutCD(String idmetric,ArrayList<Estimated> ld_list) throws LikelihoodDistributionException
    {
        if(idmetric==null || ld_list==null || ld_list.isEmpty()) return null;
        
        Measurement m=new Measurement();
        m.setIdMetric(idmetric);
        m.setMeasure(Measure.factoryEstimatedMeasureWithoutCD(ld_list));
        
        return m;
    }
    
    /**
     * This represents the datetime jointly with the measure as a text plain
     * @return Datetime and measure represented as a text plain, null when one of them is absent
     */
    public String measureToText()
    {
        if(datetime==null ||  this.measure==null) return null;
        if(measure.getQuantitative()==null) return null;
        if(this.idMetric==null) return null;
        
        StringBuilder sb=new StringBuilder();
        sb.append("{").append(datetime.toString()).append(";")
                .append(idMetric).append("}").append(measure.getQuantitative().measureToText());
        
        return sb.toString();
    }

    /**
     * It regenerates the Measurement instance from a plain text
     * @param val The plain text to be regenerated
     * @return a Measurement instance, null otherwise
     * @throws org.ciedayap.cincamimis.LikelihoodDistributionException
     */
    public static Measurement fromText(String val) throws LikelihoodDistributionException
    {
        if(val==null ||  val.trim().equalsIgnoreCase("")) return null;
        
        if(!val.contains("(")) return null;  
        
        StringBuilder sb=new StringBuilder();
        String mydt=val.substring(0, val.indexOf("("));
        mydt=mydt.replace("{", "");
        mydt=mydt.replace("}", "");
        
        String comp[]=mydt.split(";");
        if(comp==null || comp.length!=2) return null;
        
        ZonedDateTime zdt=ZonedDateTime.parse(comp[0]);
        if(zdt==null) return null;
        
        Quantitative q=Quantitative.fromText(val.substring(val.indexOf("("),val.length()));
        if(q==null) return null;
        
        if(q.getDeterministicValue()!=null) {
            Measurement ret= Measurement.factoryMeasurementWithoutCD(comp[1], q.getDeterministicValue());
            if(ret!=null) ret.setDatetime(zdt);            
        }
        
        Measurement ret=Measurement.factoryMeasurementWithoutCD(comp[1], q.getLikelihoodDistribution());
        if(ret!=null)ret.setDatetime(zdt);
        
        return ret;
    }
    
    public static void main(String args[]) throws LikelihoodDistributionException
    {
        LikelihoodDistribution ld=LikelihoodDistribution.factoryRandomDistributionEqualLikelihood(3L, 5L);        
        Measurement m=Measurement.factoryMeasurementWithoutCD("idmetric1", ld);
        String text=m.measureToText();
        System.out.println(text);
        
       
        Measurement m2=Measurement.fromText(text);
        if(m2!=null)
        {
            System.out.println("ZDT: "+m2.getDatetime());
            System.out.println("Metric: "+m2.getIdMetric());
            if(m2.getMeasure().getQuantitative().getDeterministicValue()!=null)
                System.out.println(m2.getMeasure().getQuantitative().getDeterministicValue());
            else
            {
                ArrayList<Estimated> list=m2.getMeasure().getQuantitative().getLikelihoodDistribution().getLikelihoodDistributions(); 
                for(Estimated est:list)
                {
                    System.out.println("Value: "+est.getValue()+" Likelihood: "+est.getLikelihood());
                }
                
            }
        }
        
        //System.out.println(TranslateXML.toXml(m));
        //System.out.println(TranslateJSON.toJSON(m));
        
        
    }
}
