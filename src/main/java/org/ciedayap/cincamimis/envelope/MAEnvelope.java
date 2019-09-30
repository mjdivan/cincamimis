/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.cincamimis.envelope;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashMap;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.ciedayap.cincamimis.adapters.ZonedDateTimeAdapter;

/**
 *
 * @author mjdivan
 */
@XmlRootElement(name="MAEnvelope")
@XmlType(propOrder={"source","fingerprint","originTimestamp","lifeSpan","jumps","originalMessage","knownMA"})
public class MAEnvelope implements Serializable {
    /**
     * The measurement adapter directly associated with the IoT devices which provide the measures
     */
    private String source;
    /**
     * A MD5 fingerprint associated with the original message
     */
    private String fingerprint;
    /**
     * The timestamp related to the moment in which the original MA derives the message
     */
    private java.time.ZonedDateTime originTimestamp;
    /**
     * The data lifespan related to the message and expressed in seconds and accounted from the originTimestamp
     */
    private Long lifeSpan;
    /**
     * The number of jumps made from the originTimestamp
     */
    private Short jumps;
    /**
     * The original CINCAMI/MIS message
     */
    private String originalMessage;
    /**
     * The measurement adapters whom have been visited in the path
     */
    private HashMap<String,Short> knownMA;      

    public MAEnvelope()
    {
        source=null;
        fingerprint=null;
        originTimestamp=null;
        lifeSpan=60L;
        jumps=(short)0;
        originalMessage=null;
        knownMA=new HashMap();
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb=new StringBuilder();
        if(source!=null) sb.append("Source: ").append(source);
        if(fingerprint!=null) sb.append("Fingerprint: ").append(fingerprint);
        if(originTimestamp!=null) sb.append("OriginTimestamp: ").append(originTimestamp);
        if(lifeSpan!=null) sb.append("LifeSpan: ").append(lifeSpan);
        if(jumps!=null) sb.append("Jumps: ").append(jumps);
        if(originalMessage!=null) sb.append("Original Message: ").append(originalMessage);
        if(knownMA!=null) sb.append("KnownMA: ").append(knownMA);
        
        return sb.toString();
    }
    
    /**
     * @return The source. The origin measurement adapter located at the beginning of the tracing
     */
    @XmlElement(name="source")
    public String getSource() {
        return source;
    }

    /**
     * @param source The source. The origin measurement adapter located at the beginning of the tracing
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return The fingerprint related to the original message
     */
    @XmlElement(name="fingerprint")
    public String getFingerprint() {
        return fingerprint;
    }

    /**
     * @param fingerprint The fingerprint related to the original message
     */
    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    /**
     * @return the timestamp related to the first time in which the original message was derived to an alternative MA
     */
    @XmlElement(name="originTimestamp")
    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)  
    public java.time.ZonedDateTime getOriginTimestamp() {
        return originTimestamp;
    }

    /**
     * @param originTimestamp the timestamp related to the first time in which the original message was derived to an alternative MA
     */
    public void setOriginTimestamp(java.time.ZonedDateTime originTimestamp) {
        this.originTimestamp = originTimestamp;
    }

    /**
     * @return the lifeSpan expressed in seconds and accounted from the originTimestamp
     */
    @XmlElement(name="lifeSpan")
    public Long getLifeSpan() {
        return lifeSpan;
    }

    /**
     * @param lifeSpan the lifeSpan expressed in seconds and accounted from the originTimestamp
     */
    public void setLifeSpan(Long lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    /**
     * @return The number of made jumps from the originTimestamp
     */
    @XmlElement(name="jumps")
    public Short getJumps() {
        return jumps;
    }

    /**
     * @param jumps the jumps to set
     */
    public void setJumps(Short jumps) {
        this.jumps = jumps;
    }

    /**
     * @return the originalMessage expressed as a CINCAMI/MIS String
     */
    @XmlElement(name="originalMessage")
    public String getOriginalMessage() {
        return originalMessage;
    }

    /**
     * @param originalMessage the originalMessage expressed as a CINCAMI/MIS String
     */
    public void setOriginalMessage(String originalMessage) {
        this.originalMessage = originalMessage;
    }

    /**
     * @return the visited MAs by the message as part of its route
     */
    @XmlElement(name="knownMA")
    public HashMap<String,Short> getKnownMA() {
        return knownMA;
    }

    /**
     * @param knownMA the visited MAs by the message as part of its route
     */
    public void setKnownMA(HashMap<String,Short> knownMA) {
        this.knownMA = knownMA;
    }
    
    /**
     * A new MAID is incorporated or it is updated when exists and the position is bigger than before.
     * @param MAid The MA_ID
     * @param order The order related to the MA_ID
     */
    public void addMA(String MAid,Short order)
    {
        if(this.knownMA==null) return;
        
        if(knownMA.containsKey(MAid))
        {
            Short previous=knownMA.get(MAid);
            if(previous!=null || previous>=order) return;//No Update
        }
        //It is a new order or bigger than before
        knownMA.put(MAid, order);
    }
    
    /**
     * It clear the MAs' history
     */
    public void clearHistory()
    {
        if(knownMA!=null) knownMA.clear();
    }
    
    /**
     * it deletes the MAid record when it exists
     * @param MAid The MAid to be removed
     * @return It returns the last associated position to the MAid
     */
    public Short remove(String MAid)
    {
        if(knownMA==null) return null;
        
        return knownMA.remove(MAid);
    }
    
    /**
     * A Factory method 
     * @param source The MA_ID which is message's source
     * @param fingerprint The fingerprint related to the original CINCAMIMIS message
     * @param oMessage The original CINCAMIMIS message
     * @param lifeSpan The maximum life span for the message computed from the origin timestamp
     * @return A new instance
     */
    public synchronized static MAEnvelope create(String source, String fingerprint,String oMessage,Long lifeSpan)
    {
        MAEnvelope mae=new MAEnvelope();
        mae.setFingerprint(fingerprint);
        mae.setJumps((short)0);        
        mae.setLifeSpan(lifeSpan);
        mae.setOriginTimestamp(ZonedDateTime.now());
        mae.setOriginalMessage(oMessage);
        mae.setSource(source);
        
        return mae;
    }
}
