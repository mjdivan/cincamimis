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

/**
 * It is responsible for managing the picture complementary data 
 * @author Mario Diván``
 * @version 1.0
 */
@XmlRootElement(name="videoData")
@XmlType(propOrder={"initialTimestamp","duration","videoValue","geographyData"})
public class VideoData {
    /**
     * Instant in which the video track has been initiated
     */
    private java.time.ZonedDateTime initialTimestamp;
    /**
     * Duration in seconds associated with the video track
     */
    private Integer duration;
    /**
     * The video track itself as byte sequence
     */
    private byte[] videoValue;
    /**
     * The geography data related to the video track
     */
    private Gml geographyData;
    
    @Override
    public String toString()
    {
        StringBuilder sb=new StringBuilder();
        sb.append(" Video [").append(getInitialTimestamp()).append("] ").append(getDuration()).append(" secs");
        return sb.toString();
    }

    /**
     * @return the initialTimestamp
     */
    @XmlAttribute(name="initialTimestamp")
    public java.time.ZonedDateTime getInitialTimestamp() {
        return initialTimestamp;
    }

    /**
     * @param initialTimestamp the initialTimestamp to set
     */
    public void setInitialTimestamp(java.time.ZonedDateTime initialTimestamp) {
        this.initialTimestamp = initialTimestamp;
    }

    /**
     * @return the duration in seconds
     */
    @XmlAttribute(name="duration")
    public Integer getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * @return the videoValue
     */
    @XmlElement(name="videoValue")
    public byte[] getVideoValue() {
        return videoValue;
    }

    /**
     * @param videoValue the videoValue to set
     */
    public void setVideoValue(byte[] videoValue) {
        this.videoValue = videoValue;
    }

    /**
     * @return the geographyData
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
