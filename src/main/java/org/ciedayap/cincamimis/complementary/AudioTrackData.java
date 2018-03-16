/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.cincamimis.complementary;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.ciedayap.cincamimis.adapters.ZonedDateTimeAdapter;

/**
 * It is responsible for managing the audio as complementary data 
 * @author Mario Diván``
 * @version 1.0
 */
@XmlRootElement(name="audioData")
@XmlType(propOrder={"initialTimestamp","duration","audioValue","geographyData"})
public class AudioTrackData implements Serializable{
    /**
     * Instant in which the audio track has been initiated
     */
    private java.time.ZonedDateTime initialTimestamp;
    /**
     * Duration in seconds associated with the audio track
     */
    private Integer duration;
    /**
     * The audio track itself as byte sequence
     */
    private byte[] audioValue;
    /**
     * The geography data related to the audio track
     */
    private Gml geographyData;
    
    /**
     * Default constructor
     */
    public AudioTrackData()
    {
        initialTimestamp=null;
        duration=null;
        audioValue=null;
        geographyData=null;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb=new StringBuilder();
        sb.append("Audio [").append(getInitialTimestamp()).append("] ").append(getDuration()).append(" secs");
        return sb.toString();
    }

    /**
     * @return the initialTimestamp
     */
    @XmlAttribute(name="initialTimestamp")
    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)
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
     * @return the audioValue
     */
    @XmlElement(name="audioValue")
    public byte[] getAudioValue() {
        return audioValue;
    }

    /**
     * @param audioValue the audioValue to set
     */
    public void setAudioValue(byte[] audioValue) {
        this.audioValue = audioValue;
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
