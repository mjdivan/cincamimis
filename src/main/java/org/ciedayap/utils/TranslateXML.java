/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Class responsible for translating and recovering from objects to xml strings
 * 
 * @author Mario Diván
 * @version 1.0
 */
public class TranslateXML {
   /**
    * It converts an object from a given class to xml
    * @param clase The JAXB compatible class
    * @param instancia The object to be converted
    * @return a string with the xml (sucess), and null in other case (failure).
    */
   public static String toXml(Class<?> clase,Object instancia)
   {
       if(instancia==null) return null;
       if(clase==null) return null;
       if(instancia.getClass()!=clase) return null;

       StringWriter writer=new StringWriter();                
       JAXBContext contexto;
       try {
            contexto = JAXBContext.newInstance(clase);
            Marshaller m=contexto.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);        
            m.marshal(instancia,writer);            
       } catch (JAXBException ex) {
           
           return null;
       }

       return writer.toString();       
   }

   /**
    * It converts an object using its own class type to xml
    * @param instancia The object to be converted
    * @return a string with the xml (sucess), and null in other case (failure).
    */
   public static String toXml(Object instancia)
   {
       if(instancia==null) return null;

       StringWriter writer=new StringWriter();                
       JAXBContext contexto;
       try {
            contexto = JAXBContext.newInstance(instancia.getClass());
            Marshaller m=contexto.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);        
            m.marshal(instancia,writer);            
       } catch (JAXBException ex) {
           
           return null;
       }

       return writer.toString();       
   }

   /**
    * It converts a string xml in a object representation 
    * @param clase The JAXB compatible class
    * @param xml The xml string to be converted
    * @return an object representation of the XML(sucess), and null in other case (failure).
    */
   public static Object toObject(Class<?> clase,String xml)
   {
       if(xml==null || xml.length()==0) return null;
       if(clase==null) return null;
               
       JAXBContext contexto;

       Object ptr=null;
       try {
            contexto = JAXBContext.newInstance(clase);
            Unmarshaller um=contexto.createUnmarshaller();
            InputStream is;
            is = new ByteArrayInputStream(xml.getBytes());
            ptr=um.unmarshal(is);
       } catch (JAXBException ex) {ex.printStackTrace();
           return null;
       }

       return ptr;       
   }    
}
