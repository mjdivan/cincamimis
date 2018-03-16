/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.cincamimis.complementary;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Responsible for managing the GML as complementary data
 * 
 * @author Mario Diván
 * @version 1.0
 */
@XmlRootElement(name="GML")
@XmlType(propOrder={"version","gmlDocument"})
public class Gml implements Serializable{
   /**
    * It represents the GML Document version
    */
   private String version;
   /**
    * It is the byte sequence related to the GML document. Remember that the bytes will be base64
    */
   private Byte[] gmlDocument;
   
   /**
    * Default Constructor
    */
   public Gml()
   {
       version=null;
       gmlDocument=null;
   }
   
   @Override
   public String toString()
   {
       return getVersion();
   }

    /**
     * @return the GML Document version
     */
    @XmlAttribute(name="Version", required=true)
    public String getVersion() {
        return version;
    }

    /**
     * @param version the GML Document version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the gmlDocument
     */
    @XmlElement(name="gmlDocument")
    public Byte[] getGmlDocument() {
        return gmlDocument;
    }

    /**
     * @param gmlDocument the gmlDocument to set
     */
    public void setGmlDocument(Byte[] gmlDocument) {
        this.gmlDocument = gmlDocument;
    }
}
