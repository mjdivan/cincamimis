/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.cincamimis.complementary;

import java.io.Serializable;
import java.util.Base64;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * It is responsible for managing the plain text complementary data 
 * @author Mario Diván``
 * @version 1.0
 */
@XmlRootElement(name="plainText")
@XmlType(propOrder={"language","plainDocument"})
public class PlainTextData implements Serializable{
    /**
     * Language in which the document is expressed
     */
    private String language;
    /**
     * The document itself as plain text expressed base64
     */
    private byte[] plainDocument;
    
    /**
     * Default Constructor
     */
    public PlainTextData()
    {
        language=null;
        plainDocument=null;
    }
    
    @Override
    public String toString()
    {
        return (getPlainDocument()!=null)?new String(Base64.getDecoder().decode(getPlainDocument())):"<empty>";
    }

    /**
     * @return the language in which the document is expressed
     */
    @XmlAttribute(name="language")
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the document language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the plainDocument
     */
    @XmlElement(name="plainDocument")
    public byte[] getPlainDocument() {
        return plainDocument;
    }

    /**
     * @param plainDocument the plainDocument to set
     */
    public void setPlainDocument(byte[] plainDocument) {
        this.plainDocument = plainDocument;
    }
}
