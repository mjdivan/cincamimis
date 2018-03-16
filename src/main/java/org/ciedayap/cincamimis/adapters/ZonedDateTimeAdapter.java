/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.cincamimis.adapters;

import com.google.gson.JsonSerializer;
import java.time.ZonedDateTime;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * JAXB Adapter between String and java.time.ZonedTimeAdapter
 * @author Mario Diván
 * @version 
 * @see java.lang.String
 * @see java.time.ZonedDateTime
 * @since Java 8 and upper
 */
public class ZonedDateTimeAdapter extends XmlAdapter<String,ZonedDateTime>{

    @Override
    public ZonedDateTime unmarshal(String v) throws Exception {
            return (v!=null)?ZonedDateTime.parse(v):null;
    }

    @Override
    public String marshal(ZonedDateTime v) throws Exception {
        return (v!=null)?v.toString():null;
    }
    
}
