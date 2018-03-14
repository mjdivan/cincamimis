/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.cincamimis.complementary;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * It is responsible for managing the picture complementary data 
 * @author Mario Diván``
 * @version 1.0
 */
@XmlRootElement(name="pictureData")
@XmlType(propOrder={"timestamp","pictureValue","geographyData"})
public class PictureData {
    /**
     * Picture´s datetime
     */
    private java.time.ZonedDateTime timestamp;
    /**
     * Picture expressed as byte array
     */
    private byte[] pictureValue;
    /**
     * Optionally, a picture could be associated with a GML data expressed as byte array
     */
    private Gml geographyData;
    
    @Override
    public String toString()
    {
        StringBuilder sb=new StringBuilder();
        sb.append("[Pciture]");
        if(getTimestamp()!=null) sb.append(getTimestamp());
        return sb.toString();
    }

    /**
     * @return the timestamp
     */
    @XmlAttribute(name="timestamp", required=true)
    @XmlJavaTypeAdapter(org.ciedayap.cincamimis.adapters.ZonedDateTimeAdapter.class)
    public java.time.ZonedDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(java.time.ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the pictureValue
     */
    @XmlElement(name="pictureValue")
    public byte[] getPictureValue() {
        return pictureValue;
    }

    /**
     * @param pictureValue the pictureValue to set
     */
    public void setPictureValue(byte[] pictureValue) {
        this.pictureValue = pictureValue;
    }

    /**
     * @return the geographyData (Optional)
     */
    @XmlElement(name="geographyData")
    public Gml getGeographyData() {
        return geographyData;
    }

    /**
     * @param geographyData the geographyData to set
     */
    public void setGeographyData(Gml geographyData) {
        this.geographyData = geographyData;
    }
    
}
