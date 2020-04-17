/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.cincamimis;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;

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
    
    /**
     * It generates a String under the brief data message following the object model
     * 
     * @return A String expressed under the brief data messagee
     */
    public String measureToText()
    {
        if(dsAdapterID==null || dsAdapterID.trim().length()==0 ||
                measurements==null || measurements.getMeasurementItems()==null) return null;
        
        StringBuilder sb=new StringBuilder();
        
        sb.append("{").append(dsAdapterID).append("}");
        String txt=measurements.measureToText();
        if(txt==null) return null;
        sb.append(txt);
                
        return sb.toString();
    }
    
    /**
     * It reads a String expressed under the brief data message and it regenerates the object model
     * @param val The String value under the brieef data message
     * @return A data window 
     * @throws LikelihoodDistributionException It is raised when some abnormality is detected during the generation of the likelihood distribution
     * @throws NoSuchAlgorithmException It is raised when MD5 is not present
     */
    public static Cincamimis fromText(String val) throws LikelihoodDistributionException, NoSuchAlgorithmException
    {
        if(val==null || val.trim().equalsIgnoreCase("")) return null;
        
        int s=val.indexOf("{");
        int e=val.indexOf("}");
        if(s<0 || e<0) return null;
        String dsadapter=(val.substring(s+1, e));
        if(dsadapter==null || dsadapter.trim().length()==0) return null;
                
        String rest=val.substring(e+1, val.length());
        
        MeasurementItemSet mis=MeasurementItemSet.fromText(rest);
        if(mis==null) return null;
        
        Cincamimis mess=new Cincamimis();
        mess.setDsAdapterID(dsadapter);
        mess.setMeasurements(mis);
        
        return mess;
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
        
       Cincamimis mess=new Cincamimis();
       mess.setDsAdapterID("dsadap1");
       mess.setMeasurements(mis);
       
       String brief=mess.measureToText();
       String xml=TranslateXML.toXml(mess);
       String json=TranslateJSON.toJSON(mess);
       System.out.println(brief);
       System.out.println("XML: "+(xml.getBytes().length)+" JSON: "+(json.getBytes().length)+"  Brief: "+(brief.getBytes().length));
       
       Cincamimis mess2=Cincamimis.fromText(brief);
       if(mess2!=null)
       {
           System.out.println("DSAdapter: "+mess2.getDsAdapterID()+" Version: "+mess.getVersion());
           MeasurementItemSet mis2=mess2.getMeasurements();
           
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
    
}
