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
 * It is responsible for grouping the measures associated with the entity´s context
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="context")
@XmlType(propOrder={"measurements"})
public class Context implements Serializable{
    private ArrayList<Measurement> measurements;
    
    @Override
    public String toString()
    {
        StringBuilder sb=new StringBuilder();
        if(getMeasurements()!=null) sb.append(" Context Property Measurements: ").append(getMeasurements().size());
        
        return sb.toString();
    }
    
    /**
     * Default constructor
     */
    public Context()
    {
        measurements=new ArrayList<>();
    }

    /**
     * @return the measurements related to the context properties
     */
    @XmlElement(name="Measurements")
    public ArrayList<Measurement> getMeasurements() {
        return measurements;
    }

    /**
     * @param measurements the measurements to set
     */
    public void setMeasurements(ArrayList<Measurement> measurements) {
        this.measurements = measurements;
    }
    
    /**
     * Add a measurements to the list
     * @param cpm The context property measurement
     */
    public void add(Measurement cpm)
    {
        if(this.measurements==null) this.measurements=new ArrayList();
        measurements.add(cpm);
    }
    
    /**
     * It allows deleting by index an object from the list
     * @param index index between 0 and measurements.size()
     */
    public void remove(int index)
    {
        if(index<0 || measurements==null || measurements.size()<index) return;
        measurements.remove(index);
    }
    
    /**
     * It allows deleting a context property measurement from the list
     * @param cpm The context property measurement to be removed
     */
    public void remove(Measurement cpm)
    {
        if(measurements==null || cpm==null) return;
        measurements.remove(cpm);
    }
    
    /**
     * it removes all the objects from the list
     */
    public void clearAll()
    {
        if(measurements!=null) measurements.clear();
    }

    /**
     * It creates a context with a measurement associted with the idMetric (Context Property)
     * for a deterministic value. There are not complementary data.
     * @param idMetric ID of the metric related to the context property
     * @param detValue Deterministic value associated with the IDMetric
     * @return a Context object with the deterministic value incorporated
     */
    public static Context factoryDeterministicValueWithoutCD(String idMetric,BigDecimal detValue)
    {
        if(idMetric==null || detValue==null) return null;
        
        Context c=new Context();
        c.add(Measurement.factoryMeasurementWithoutCD(idMetric, detValue));
        
        return c;        
    }
    
    /**
     * It creastes a context with a likelihood distribution associated with the IDMetric (Context Property).
     * Neither complementary data are provided.
     * @param idMetric The id metric associated with the context property
     * @param ld The likelihood distribution to be incorporated in the context
     * @return a Context object incorporating the likelihood distribution for the given context property.
     * @throws LikelihoodDistributionException 
     */
    public static Context factoryEstimatedValuesWithoutCD(String idMetric,LikelihoodDistribution ld) throws LikelihoodDistributionException
    {
        if(idMetric==null || ld==null) return null;
        
        Context c=new Context();
        c.add(Measurement.factoryMeasurementWithoutCD(idMetric, ld));
        
        return c;        
    }    
}
