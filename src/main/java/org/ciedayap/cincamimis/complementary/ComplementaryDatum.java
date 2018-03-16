/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.cincamimis.complementary;

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
 * It is responsible for managing each complementary datum.
 * Just one complementary datum is allowed at the same time. In this sense,
 * if you try initially to set pictureData, and in second order you try to
 * set GML data, the GML data is kept and the pictureData become null.
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="complementaryDatum")
@XmlType(propOrder={"mimeVersion","mimeContentType","hashControl","GML","pictureData","plainTextData","audioTrackData","videoData"})
public class ComplementaryDatum implements Serializable{
    /**
     * It represents the hasher. This field must not be serialized.
     */
    private transient java.security.MessageDigest md5;
    /**
     * mime version associated with the complementary datum
     */
    private String mimeVersion;
    /**
     * mime content type associated with the complementary datum
     */
    private String mimeContentType;
    /**
     * hashcontrol computed directly from the complementary datum.
     */
    private String hashControl;
    /**
     * original hashcontrol received by the stream.
     * This field must not be serialized.
     */
    private transient String originalHashControl;
    /**
     * Geography information as complementary datum
     */
    private Gml GML;
    private PictureData pictureData;
    private PlainTextData plainTextData;
    private AudioTrackData audioTrackData;
    private VideoData videoData;
    
    /**
     * Default constructor
     * @throws NoSuchAlgorithmException When MD5 is not available
     */
    public ComplementaryDatum() throws NoSuchAlgorithmException
    {
        md5=MessageDigest.getInstance("MD5");
        mimeVersion=null;
        mimeContentType=null;
        hashControl=null;
        originalHashControl=null;
        GML=null;
        pictureData=null;
        plainTextData=null;
        audioTrackData=null;
        videoData=null;
    }
    
    @Override
    public String toString()
    {
        return "["+((this.getHashControl()!=null)?this.getHashControl():"-")+"";        
    }

    /**
     * @return the mimeVersion
     */
    @XmlAttribute(name="mimeVersion")
    public String getMimeVersion() {
        return mimeVersion;
    }

    /**
     * @param mimeVersion the mimeVersion to set
     */
    public void setMimeVersion(String mimeVersion) {
        this.mimeVersion = mimeVersion;
    }

    /**
     * @return the mimeContentType
     */
    @XmlAttribute(name="mimeContentType")
    public String getMimeContentType() {
        return mimeContentType;
    }

    /**
     * @param mimeContentType the mimeContentType to set
     */
    public void setMimeContentType(String mimeContentType) {
        this.mimeContentType = mimeContentType;
    }

    /**
     * @return the hashControl
     */
    @XmlAttribute(name="hashControl")
    public String getHashControl() {
        return hashControl;
    }

    /**
     * @param hashControl the hashControl to set
     */
    public void setHashControl(String hashControl) {
        this.originalHashControl = hashControl; 
    }

    /**
     * @return the GML
     */
    @XmlElement(name="GML")
    public Gml getGML() {
        return GML;
    }

    /**
     * @param GML the GML to set
     */
    public void setGML(Gml GML) {
        this.GML = GML;
        
        this.setVideoData(null);
        this.setAudioTrackData(null);
        this.setPictureData(null);
        this.setPlainTextData(null);
        try{
            this.computeHash();
        }catch(NoSuchAlgorithmException e){}
    }

    /**
     * @return the pictureData
     */
    @XmlElement(name="pictureData")
    public PictureData getPictureData() {
        return pictureData;
    }

    /**
     * @param pictureData the pictureData to set
     */
    public void setPictureData(PictureData pictureData) {
        this.pictureData = pictureData;
        
        this.setGML(null);
        this.setAudioTrackData(null);
        this.setVideoData(null);
        this.setPlainTextData(null);      
        try{
            this.computeHash();
        }catch(NoSuchAlgorithmException e){}       
    }

    /**
     * @return the plainTextData
     */
    @XmlElement(name="plainText")
    public PlainTextData getPlainTextData() {
        return plainTextData;
    }

    /**
     * @param plainTextData the plainTextData to set
     */
    public void setPlainTextData(PlainTextData plainTextData) {
        this.plainTextData = plainTextData;
        
        this.setGML(null);
        this.setAudioTrackData(null);
        this.setPictureData(null);
        this.setVideoData(null);
        try{
            this.computeHash();
        }catch(NoSuchAlgorithmException e){}        
    }

    /**
     * @return the audioTrackData
     */
    @XmlElement(name="audioTrackData")
    public AudioTrackData getAudioTrackData() {
        return audioTrackData;
    }

    /**
     * @param audioTrackData the audioTrackData to set
     */
    public void setAudioTrackData(AudioTrackData audioTrackData) {
        this.audioTrackData = audioTrackData;
        
        this.setGML(null);
        this.setVideoData(null);
        this.setPictureData(null);
        this.setPlainTextData(null);        
        try{
            this.computeHash();
        }catch(NoSuchAlgorithmException e){}
    }

    /**
     * @return the videoData
     */
    @XmlElement(name="videoData")
    public VideoData getVideoData() {
        return videoData;
    }

    /**
     * @param videoData the videoData to set
     */
    public void setVideoData(VideoData videoData) {
        this.videoData = videoData;
        
        this.setGML(null);
        this.setAudioTrackData(null);
        this.setPictureData(null);
        this.setPlainTextData(null);
        try{
            this.computeHash();
        }catch(NoSuchAlgorithmException e){}        
    }
          
    private void computeHash() throws NoSuchAlgorithmException
    {       
        String compl=null;
        if(this.getGML()!=null) compl=TranslateXML.toXml(this.getGML().getClass(), this.getGML());
        if(this.getAudioTrackData()!=null) compl=TranslateXML.toXml(this.getAudioTrackData().getClass(),this.getAudioTrackData());
        if(this.getPictureData()!=null) compl=TranslateXML.toXml(this.getPictureData().getClass(), this.getPictureData());
        if(this.getPlainTextData()!=null) compl=TranslateXML.toXml(this.getPlainTextData().getClass(), this.getPlainTextData());
        if(this.getVideoData()!=null) compl=TranslateXML.toXml(this.getVideoData().getClass(), this.getVideoData());
        
        if(compl!=null)
        {
            if(md5!=null) 
            {
                md5.update(compl.getBytes());
                this.hashControl= Conversions.toHexString(md5.digest());
            }
            
            md5=MessageDigest.getInstance("MD5");
            this.hashControl= Conversions.toHexString(md5.digest(compl.getBytes()));
        }
    }
    
    /**
     * The originalHashControl is established by the method setHashcontrol.
     * However, the hashcontrol attribute is directly computed from the complementary datum.
     * When the two hash are available, an evaluation is executed in this method.
     * @return It will return null if the footprint or originalFootprint are null, 
     * else an equality evaluation will be performed.
     */
    public Boolean evaluateFootprint()
    {
        if(this.originalHashControl==null) return null;
        if(this.hashControl==null) return null;
        
        return originalHashControl.equalsIgnoreCase(hashControl);
    }
}
