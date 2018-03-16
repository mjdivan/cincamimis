/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.cincamimis;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Class responsible for the generation of CINCAMI/MIS streams
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="cincamimis")
@XmlType(propOrder={"version","dsAdapterID","measurements"})
public class Cincamimis implements Serializable{
    /**
     * Version associated with the CINCAMI/MIS schema
     */
    private String version;
    /**
     * ID related to the measurement adapter
     */
    private String dsAdapterID;
    /**
     * Container related to the set of measurements
     */
    private MeasurementItemSet measurements;
    
    @Override
    public String toString()
    {
        StringBuilder sb=new StringBuilder();
        sb=sb.append(" Version: ").append(this.getVersion())
          .append(" dsAdapterID: ").append(this.getDsAdapterID())
          .append(" MeasurementItemSet: ").append(this.getMeasurements().toString());
        
        return sb.toString();
    }

    /**
     * Default constructor
     */
    public Cincamimis()
    {
        version="2.0";
        dsAdapterID=null;
        measurements=null;
    }   
    
    /**
     * @return the version associated with the CINCAMI/MIS schema
     */
    @XmlAttribute(name="Version", required=true)
    public String getVersion() {
        return version;
    }

    /**
     * @param version It allows set up the version of the CINCAMI/MIS schema
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the ID related to the responsible measurement adapter
     */
    @XmlAttribute(name="dsAdapterID", required=true)
    public String getDsAdapterID() {
        return dsAdapterID;
    }

    /**
     * @param dsAdapterID the ID associated with the responsible measurement adapter
     */
    public void setDsAdapterID(String dsAdapterID) {
        this.dsAdapterID = dsAdapterID;
    }

    /**
     * @return the measurements
     */
    @XmlElement(name="measurementItemSet")
    public MeasurementItemSet getMeasurements() {
        return measurements;
    }

    /**
     * @param measurements the measurements to set
     */
    public void setMeasurements(MeasurementItemSet measurements) {
        this.measurements = measurements;
    }
    
}
