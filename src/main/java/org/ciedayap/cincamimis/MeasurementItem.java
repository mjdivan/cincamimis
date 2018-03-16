/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.cincamimis;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.ciedayap.utils.Conversions;
import org.ciedayap.utils.TranslateXML;

/**
 * It responsible for managing each measurement item
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="measurementItem")
@XmlType(propOrder={"dataSourceID","originalDataFormat","footprint","idEntity","measurement","context"})
public class MeasurementItem implements Serializable{
    /**
     * It represents the hasher. The "transient" modifier is specified for avoiding the serialization of this attribute
     */
    private transient java.security.MessageDigest md5;
    /**
     * This is the ID entity defined in the M&E projectd based on C-INCAMI framework
     */
    private String idEntity;
    /**
     * The measurement expressed such as logic unit containing the instant, the value and the associated metric
     */
    private Measurement measurement;
    /**
     * The context information related to the measurement
     */
    private Context context;
    /**
     * It represents the data source identification along the M&E project
     */
    private String dataSourceID;
    /**
     * It represents the original data format associated with the data source.
     */
    private String originalDataFormat;
    /**
     * It represents the MD5 hash associated just with the measurement information (Measurement tag)
     */
    private String footprint;
    /**
     * It represents the MD5 hash obtained from the XML translation. It does not overwrite.
     * This field does not have to be serialized.
     */
    private transient String originalFootprint;
    
    public MeasurementItem() throws NoSuchAlgorithmException
    {
        md5=MessageDigest.getInstance("MD5");
        idEntity=null;
        measurement=null;
        context=null;
        dataSourceID=null;
        originalDataFormat=null;
        footprint=null;
        originalFootprint=null;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb=new StringBuilder();
        if(getIdEntity()!=null) sb.append("IDEntity: ").append(getIdEntity());
        if(getMeasurement()!=null) sb.append(" Measurement ").append(getMeasurement().toString());
        if(getContext()!=null) sb.append(" with context information");
        
        return sb.toString();
    }

    /**
     * @return the idEntity under monitoring defined in the M&E project
     */
    @XmlElement(name="idEntity")
    public String getIdEntity() {
        return idEntity;
    }

    /**
     * @param idEntity the idEntity to set
     */
    public void setIdEntity(String idEntity) {
        this.idEntity = idEntity;
    }

    /**
     * @return the measurement
     */
    @XmlElement(name="Measurement")
    public Measurement getMeasurement() {
        return measurement;
    }

    /**
     * @param measurement the measurement to set
     */
    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
        try{
            this.computeHash();
        }catch(NoSuchAlgorithmException e){}        
    }

    /**
     * @return the context information associated with the measurement
     */
    @XmlElement(name="context")
    public Context getContext() {
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * @return the dataSourceID
     */
    @XmlAttribute(name="dataSourceID",required=true)
    public String getDataSourceID() {
        return dataSourceID;
    }

    /**
     * @param dataSourceID the dataSourceID to set
     */
    public void setDataSourceID(String dataSourceID) {
        this.dataSourceID = dataSourceID;
    }

    /**
     * @return the originalDataFormat
     */
    @XmlAttribute(name="originalDataFormat",required=true)
    public String getOriginalDataFormat() {
        return originalDataFormat;
    }

    /**
     * @param originalDataFormat the originalDataFormat to set
     */
    public void setOriginalDataFormat(String originalDataFormat) {
        this.originalDataFormat = originalDataFormat;
    }

    /**
     * @return the footprint computed from the Measurement tag
     */
    @XmlAttribute(name="footprint",required=true)
    public String getFootprint() {
        return footprint;
    }

    /**
     * @param footprint the original footprint to set. It is not neccesar
     */
    public void setFootprint(String footprint) {
        this.originalFootprint = footprint;
    }
    
    /**
     * It computes the hash associated with the Measurement tag
     * @throws NoSuchAlgorithmException 
     */
    private void computeHash() throws NoSuchAlgorithmException
    {       
        String xml=null;
        if(this.getMeasurement()!=null) xml=TranslateXML.toXml(this.getMeasurement().getClass(), this.getMeasurement());
        
        if(xml!=null)
        {
            if(md5!=null) 
            {
                md5.update(xml.getBytes());
                this.footprint= Conversions.toHexString(md5.digest());
            }
            
            md5=MessageDigest.getInstance("MD5");
            this.footprint= Conversions.toHexString(md5.digest(xml.getBytes()));
        }
    }
    
    /**
     * The originalFootprint is established by the method setFootprint.
     * However, the footprint attribute is directly computed from the Measurement tag.
     * When the two hash are available, an evaluation is executed in this method.
     * @return It will return null if the footprint or originalFootprint are null, 
     * else an equality evaluation will be performed.
     */
    public Boolean evaluateFootprint()
    {
        if(this.originalFootprint==null) return null;
        if(this.footprint==null) return null;
        
        return originalFootprint.equalsIgnoreCase(footprint);
    }

    /**
     * It creates a MeasurementItem object with the given parameters. This object does not contain complementary data 
     * and context information
     * @param idEntity ID entity in the M&E project
     * @param dsid Data source ID
     * @param format Native format related to the data source
     * @param idMetric ID Metric associated with the entity´s attribute
     * @param ld The likelihood distribution for the IDMetric measurement
     * @return A MeasurementItem including the given parameters
     * @throws LikelihoodDistributionException 
     */
    public static MeasurementItem factory(String idEntity,String dsid, String format,String idMetric,LikelihoodDistribution ld) throws LikelihoodDistributionException, NoSuchAlgorithmException
    {
        if(idEntity==null || idMetric==null || ld==null || dsid==null || format==null) return null;
        
        MeasurementItem mi=new MeasurementItem();
        mi.setDataSourceID(dsid);
        mi.setIdEntity(idEntity);
        mi.setMeasurement(Measurement.factoryMeasurementWithoutCD(idMetric,ld));
        mi.setOriginalDataFormat(format);
        
        return mi;
    }
    
    
    public static void main(String args[]) throws LikelihoodDistributionException, NoSuchAlgorithmException
    {
        MeasurementItem mi=MeasurementItem.factory("IDEntity1","dsid_1", "plaintext", "idMetric1", LikelihoodDistribution.factoryRandomDistributionEqualLikelihood(3L, 5l));
        System.out.println(TranslateXML.toXml(mi.getClass(), mi));
    }
}
