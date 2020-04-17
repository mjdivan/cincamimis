/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.cincamimis;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import org.ciedayap.cincamimis.envelope.MAEnvelope;
import org.ciedayap.utils.Conversions;
import org.ciedayap.utils.TranslateJSON;
import org.ciedayap.utils.TranslateXML;
import org.ciedayap.utils.ZipUtil;

/**
 *
 * @author Mario
 */
public class test {
    public static void main(String args[]) throws LikelihoodDistributionException, NoSuchAlgorithmException, Exception
    {
        //testCincamimis_xml_and_compression();
        //testCincamimis_json_and_compression(); 
        //generarEstadisticasXML(1000,100);
        //generarEstadisticasJSON(1000,100);
        //oneExample();
        //generarEstadisticasJSON_EnvelopeVaryingMeasurements(5000,100);
        //generarEstadisticasJSON_EnvelopeVaryingTime(500,5L);
        testCincamimis_xmlVsjson();
    }
    
    public static void testCincamimis_xmlVsjson() throws LikelihoodDistributionException, NoSuchAlgorithmException, Exception
    {
       LikelihoodDistribution ld=LikelihoodDistribution.factoryRandomDistributionEqualLikelihood(3L, 5L);        
       MeasurementItem m=MeasurementItem.factory("idEntity1", "dsid1", "format", "idMetric1", ld,"PRJ1","EC1");
       MeasurementItemSet mis=new MeasurementItemSet();
       mis.add(m);
       mis.add(m);
       
       Cincamimis flujo=new Cincamimis();
       flujo.setDsAdapterID("adapter1");
       flujo.setMeasurements(mis);
       
       //It transforms the CINCAMI/MIS objects to a XML string
       String xml=TranslateXML.toXml(flujo.getClass(),flujo);
       String json=TranslateJSON.toJSON(flujo);
       System.out.println(xml);   
       System.out.println(json);
       
       //Some indicative measures
       System.out.println("Original bytes (XML): "+xml.getBytes().length);//Bytes without compression
       System.out.println("Original bytes (JSON): "+json.getBytes().length);//Bytes without compression       
    }
    
    public static void testCincamimis_xml_and_compression() throws LikelihoodDistributionException, NoSuchAlgorithmException, Exception
    {
       LikelihoodDistribution ld=LikelihoodDistribution.factoryRandomDistributionEqualLikelihood(3L, 5L);        
       MeasurementItem m=MeasurementItem.factory("idEntity1", "dsid1", "format", "idMetric1", ld,"PRJ1","EC1");
       MeasurementItemSet mis=new MeasurementItemSet();
       mis.add(m);
       mis.add(m);
       
       Cincamimis flujo=new Cincamimis();
       flujo.setDsAdapterID("adapter1");
       flujo.setMeasurements(mis);
       
       //It transforms the CINCAMI/MIS objects to a XML string
       String xml=TranslateXML.toXml(flujo.getClass(),flujo);
       System.out.println(xml);   
       
       //It compresses the xml representation of CINCAMI/MIS
       byte[] compGZIP=ZipUtil.compressGZIP(xml);
       
       //It decompress the byte array using GZIP and recover a string representation based on UTF-8
       String xmlD=ZipUtil.decompressGZIP(compGZIP);
       
       //It transforms the decompressed xml string to CINCAMI/MIS JAXB Objects
       Cincamimis q10b=(Cincamimis)TranslateXML.toObject(flujo.getClass(), xmlD);
       
       //It transforms again in xml string the CINCAMI/MIS obtained from the decompression for comparing the versions.
       System.out.println(TranslateXML.toXml(q10b.getClass(), q10b));
       
       //Some indicative measures
       System.out.println("Original bytes: "+xml.getBytes().length);//Bytes without compression
       System.out.println("Compressed bytes: "+compGZIP.length);//Bytes in the compressed version        
       System.out.println("Equality: "+xml.equalsIgnoreCase(xmlD));//It verifies the previous and posterior xml
       System.out.println("Uncompressed bytes: "+xmlD.getBytes().length);//Bytes without compression obtained from the compressed version
    }
    
    public static void testCincamimis_json_and_compression() throws LikelihoodDistributionException, NoSuchAlgorithmException, Exception
    {
       LikelihoodDistribution ld=LikelihoodDistribution.factoryRandomDistributionEqualLikelihood(3L, 5L);        
       MeasurementItem m=MeasurementItem.factory("idEntity1", "dsid1", "format", "idMetric1", ld,"PRJ1","EC1");
       MeasurementItemSet mis=new MeasurementItemSet();
       mis.add(m);
       mis.add(m);
       
       Cincamimis flujo=new Cincamimis();
       flujo.setDsAdapterID("adapter1");
       flujo.setMeasurements(mis);
       
       //It transforms the CINCAMI/MIS objects to a XML string
       String json=TranslateJSON.toJSON(flujo);
       System.out.println(json);   
       
       //It compresses the xml representation of CINCAMI/MIS
       byte[] compGZIP=ZipUtil.compressGZIP(json);
       
       //It decompress the byte array using GZIP and recover a string representation based on UTF-8
       String jsonD=ZipUtil.decompressGZIP(compGZIP);
       
       //It transforms the decompressed xml string to CINCAMI/MIS JAXB Objects
       Cincamimis q10b=(Cincamimis)TranslateJSON.toObject(flujo.getClass(), jsonD);
       
       //It transforms again in xml string the CINCAMI/MIS obtained from the decompression for comparing the versions.
       System.out.println(TranslateJSON.toJSON(q10b));
       
       //Some indicative measures
       System.out.println("Original bytes: "+json.getBytes().length);//Bytes without compression
       System.out.println("Compressed bytes: "+compGZIP.length);//Bytes in the compressed version        
       System.out.println("Equality: "+json.equalsIgnoreCase(jsonD));//It verifies the previous and posterior xml
       System.out.println("Uncompressed bytes: "+jsonD.getBytes().length);//Bytes without compression obtained from the compressed version       
    }
    
    public static final void generarEstadisticasXML(Integer volMax,Integer salto) throws LikelihoodDistributionException, NoSuchAlgorithmException, Exception
    {
        LikelihoodDistribution ld;
        ld = LikelihoodDistribution.factoryRandomDistributionEqualLikelihood(3L, 5L);        
        Context myContext=Context.factoryEstimatedValuesWithoutCD("idMetricContextProperty", ld);
        Random r=new Random();
        
        ArrayList tabla=new ArrayList();

        ArrayList registro;

        for(Integer i=salto; i<=volMax;i=i+salto)
        {
          registro=new ArrayList();
          Cincamimis flujo=new Cincamimis();
          flujo.setDsAdapterID("dsAdapter1");
          MeasurementItemSet mis=new MeasurementItemSet();
          for(int j=0;j<=i;j++)
          {//Genero desde 1 hasta i (multiplo de salto) mensajes hastya llegar a volMax
              MeasurementItem mi=MeasurementItem.factory("idEntity1", "dataSource1", "myFormat", "idMetric"+j, 
                      BigDecimal.TEN.multiply(BigDecimal.valueOf(r.nextGaussian())),"PRJ1","EC1");
              if((j%2)==0) mi.setContext(myContext);
              mis.add(mi);
          }
          
          flujo.setMeasurements(mis);
          registro.add(i);//# measurements
                  
          Long before=System.nanoTime();
          String xml=TranslateXML.toXml(flujo);
          Long after=System.nanoTime();
          registro.add(after-before);//elapsed time in translating 
          
          registro.add(xml.getBytes().length);//Bytes associated with the translated message
         
          before=System.nanoTime();
          byte bxml[]=ZipUtil.compressGZIP(xml);
          after=System.nanoTime();
          registro.add(after-before); //elapsed time compression gzip
          registro.add(bxml.length);//compressed bytes
          
          before=System.nanoTime();
          String ebxml=ZipUtil.decompressGZIP(bxml);
          after=System.nanoTime();
          registro.add(after-before);//elapsed time descompressed

          before=System.nanoTime();
          Cincamimis restauraxml=(Cincamimis) TranslateXML.toObject(Cincamimis.class, ebxml);
          after=System.nanoTime();
          registro.add(after-before);//elapsed tyme xml to object
          
          Boolean check_xml=(xml.equalsIgnoreCase(ebxml));
          registro.add(check_xml);//equal   
          tabla.add(registro);
          
          if(Objects.equals(i, salto))
          {
            System.out.println("1.#Measurements   2.(ns)Translating     3.Translated_bytes   4.(ns)GZIP     5.Compressed_bytes  6.(ns)UnGZIP    7.(ns)xmlToObject   8.Integrity");
          }
          
          for(Object ptr:registro)
            {
                System.out.print(ptr+" ");
            }
          System.out.println();
        }
        
        for(Object rec:tabla)
        {
            ((ArrayList)rec).clear();            
        }
        tabla.clear();
    }

    public static final void generarEstadisticasJSON(Integer volMax,Integer salto) throws LikelihoodDistributionException, NoSuchAlgorithmException, Exception
    {
        LikelihoodDistribution ld;
        ld = LikelihoodDistribution.factoryRandomDistributionEqualLikelihood(3L, 5L);        
        Context myContext=Context.factoryEstimatedValuesWithoutCD("idMetricContextProperty", ld);
        Random r=new Random();
        
        ArrayList tabla=new ArrayList();
        ArrayList registro;

        for(Integer i=salto; i<=volMax;i=i+salto)
        {
          registro=new ArrayList();
          Cincamimis flujo=new Cincamimis();
          flujo.setDsAdapterID("dsAdapter1");
          MeasurementItemSet mis=new MeasurementItemSet();
          for(int j=0;j<=i;j++)
          {//Genero desde 1 hasta i (multiplo de salto) mensajes hastya llegar a volMax
              MeasurementItem mi=MeasurementItem.factory("idEntity1", "dataSource1", "myFormat", "idMetric"+j, 
                      BigDecimal.TEN.multiply(BigDecimal.valueOf(r.nextGaussian())),"PRJ1","EC1");
              if((j%2)==0) mi.setContext(myContext);
              mis.add(mi);
          }
          
          flujo.setMeasurements(mis);
          registro.add(i);//# measurements
                  
          Long before=System.nanoTime();
          String json=TranslateJSON.toJSON(flujo);
          Long after=System.nanoTime();
          registro.add(after-before);//elapsed time in translating 
          
          registro.add(json.getBytes().length);//Bytes associated with the translated message
         
          before=System.nanoTime();
          byte bjson[]=ZipUtil.compressGZIP(json);
          after=System.nanoTime();
          registro.add(after-before); //elapsed time compression gzip
          registro.add(bjson.length);//compressed bytes
          
          before=System.nanoTime();
          String ebjson=ZipUtil.decompressGZIP(bjson);
          after=System.nanoTime();
          registro.add(after-before);//elapsed time descompressed

          before=System.nanoTime();
          Cincamimis restaurajson=(Cincamimis) TranslateJSON.toObject(Cincamimis.class, ebjson);
          after=System.nanoTime();
          registro.add(after-before);//elapsed tyme xml to object
          
          Boolean check_json=(json.equalsIgnoreCase(ebjson));
          registro.add(check_json);//equal   
          tabla.add(registro);
          
          if(Objects.equals(i, salto))
          {
            System.out.println("JSON");
            System.out.println("1.#Measurements   2.(ns)Translating     3.Translated_bytes   4.(ns)GZIP     5.Compressed_bytes  6.(ns)UnGZIP    7.(ns)xmlToObject   8.Integrity");
          }
          
          for(Object ptr:registro)
            {
                System.out.print(ptr+" ");
            }
          System.out.println();
        }
        
        for(Object rec:tabla)
        {
            ((ArrayList)rec).clear();            
        }
        tabla.clear();
    }

    private static void oneExample() throws LikelihoodDistributionException, NoSuchAlgorithmException, UnsupportedEncodingException {
       LikelihoodDistribution ld=LikelihoodDistribution.factoryRandomDistributionEqualLikelihood(2L, 5L);        
       MeasurementItem m=MeasurementItem.factory("idEntity1", "dsid1", "format", "idMetric1", ld,"PRJ1","EC1");
       MeasurementItemSet mis=new MeasurementItemSet();
       mis.add(m);
       Context c=Context.factoryDeterministicValueWithoutCD("idMetric3", BigDecimal.valueOf(30.5));
       MeasurementItem m2=MeasurementItem.factory("idEntity1", "dsid2", "format", "idMetric2", BigDecimal.valueOf(37.5),"PRJ1","EC1");
       m2.setContext(c);
       mis.add(m2);
       
       Cincamimis flujo=new Cincamimis();
       flujo.setDsAdapterID("adapter1");
       flujo.setMeasurements(mis);
       
       //It transforms the CINCAMI/MIS objects to a XML string
       String xml=TranslateXML.toXml(flujo.getClass(),flujo);
       System.out.println(xml);   
       
       String json=TranslateJSON.toJSON(flujo);
       System.out.println(json);
       
       MessageDigest md=MessageDigest.getInstance("MD5");
       md.update(xml.getBytes());                
       String fp=Conversions.toHexString(md.digest());
       
       MAEnvelope origin=MAEnvelope.create("adapter1", fp, xml, 60L);
       origin.addMA("adapter2", (short)2);
       origin.addMA("adapter3", (short)3);
       String envelope_xml=TranslateXML.toXml(origin);
       System.out.println(envelope_xml);
       MAEnvelope retorno=(MAEnvelope) TranslateXML.toObject(MAEnvelope.class, envelope_xml);
       
       md.update(retorno.getOriginalMessage().getBytes("UTF-8"));                
       String fp2=Conversions.toHexString(md.digest());
       System.out.println("Original Fingerprint: "+fp+" Cmp: "+fp2);
       System.out.println("Sending GF the Original Message...");
       System.out.println(new String(retorno.getOriginalMessage().getBytes("UTF-8")));              
    }
    
    public static final void generarEstadisticasJSON_EnvelopeVaryingMeasurements(Integer volMax,Integer salto) throws LikelihoodDistributionException, NoSuchAlgorithmException, Exception
    {
        LikelihoodDistribution ld;
        ld = LikelihoodDistribution.factoryRandomDistributionEqualLikelihood(3L, 5L);        
        Context myContext=Context.factoryEstimatedValuesWithoutCD("idMetricContextProperty", ld);
        Random r=new Random();
        
        ArrayList tabla=new ArrayList();
        ArrayList registro;

        for(Integer i=salto; i<=volMax;i=i+salto)
        {
          System.gc();
          
          registro=new ArrayList();
          Cincamimis flujo=new Cincamimis();
          flujo.setDsAdapterID("dsAdapter1");
          MeasurementItemSet mis=new MeasurementItemSet();
          for(int j=0;j<=i;j++)
          {//Genero desde 1 hasta i (multiplo de salto) mensajes hastya llegar a volMax
              MeasurementItem mi=MeasurementItem.factory("idEntity1", "dataSource1", "myFormat", "idMetric"+j, 
                      BigDecimal.TEN.multiply(BigDecimal.valueOf(r.nextGaussian())),"PRJ1","EC1");
              if((j%2)==0) mi.setContext(myContext);
              mis.add(mi);
          }
          
          flujo.setMeasurements(mis);
          registro.add(i);//# measurements
                  
          Long before,after;
          before=after=0L;
                              
          String json=TranslateJSON.toJSON(flujo);
          
          before=System.nanoTime();
          MessageDigest md=MessageDigest.getInstance("MD5");
          md.update(json.getBytes());                
          String fp=Conversions.toHexString(md.digest());
          after=System.nanoTime();// 
          registro.add(after-before);//1. elapsed time in the fingerprint calculus
          
          registro.add(json.getBytes().length);//4. Bytes associated with the original message
          
          before=System.nanoTime();
          MAEnvelope origin=MAEnvelope.create("adapter1", fp, json, 60L);
          origin.addMA("adapter2", (short)2);
          origin.addMA("adapter3", (short)3);
          after=System.nanoTime();
          registro.add(after-before);//4. elapsed time in the envelope creation

          before=System.nanoTime();
          String enveloped=TranslateJSON.toJSON(origin);
          after=System.nanoTime();
          registro.add(after-before);//5. Elapsed time in Object-JSON translation for the enveloped message                    
          
          registro.add(enveloped.getBytes().length);//6. Enveloped Byte Size
          
          before=System.nanoTime();
          byte bjson[]=ZipUtil.compressGZIP(enveloped);
          after=System.nanoTime();
          registro.add(after-before); //7. elapsed time compression gzip
          registro.add(bjson.length);//8. compressed bytes
          
          before=System.nanoTime();
          String ebjson=ZipUtil.decompressGZIP(bjson);
          after=System.nanoTime();
          registro.add(after-before);//9.elapsed time descompressed

          before=System.nanoTime();
          MAEnvelope restaurajson=(MAEnvelope) TranslateJSON.toObject(MAEnvelope.class, ebjson);
          after=System.nanoTime();
          registro.add(after-before);//10.elapsed tyme json to object
          
          Boolean check_json=(json.equalsIgnoreCase(restaurajson.getOriginalMessage()));
          registro.add(check_json);//11. equal   
          tabla.add(registro);
          
          if(Objects.equals(i, salto))
          {
            System.out.println("JSON");
            System.out.println("1.#Measurements 2.(ns) Fingerprint 3.(bytes)Original Message  4.(ns) Envelope 5.(ns) CreatingEnvelopedJSON "
                    + "6.(bytes) Enveloped Message Size 7.(ns)GZIP 8.Compressed_bytes  9.(ns)UnGZIP  10.(ns)xmlToObject  11.Integrity");
          }
          
          for(Object ptr:registro)
            {
                System.out.print(ptr+";");
            }
          System.out.println();
        }
        
        for(Object rec:tabla)
        {
            ((ArrayList)rec).clear();            
        }
        tabla.clear();
    }
    
    public static final void generarEstadisticasJSON_EnvelopeVaryingTime(Integer messageSize,Long minutes) throws LikelihoodDistributionException, NoSuchAlgorithmException, Exception
    {
        LikelihoodDistribution ld;
        ld = LikelihoodDistribution.factoryRandomDistributionEqualLikelihood(3L, 5L);        
        Context myContext=Context.factoryEstimatedValuesWithoutCD("idMetricContextProperty", ld);
        Random r=new Random();
        
        ArrayList tabla=new ArrayList();
        ArrayList registro;

        Long start=System.nanoTime();
        boolean title=true;

        //The same stream in each message
          Cincamimis flujo=new Cincamimis();
          flujo.setDsAdapterID("dsAdapter1");
          MeasurementItemSet mis=new MeasurementItemSet();
          for(int j=0;j<=messageSize;j++)
          {//Genero desde 1 hasta i (multiplo de salto) mensajes hastya llegar a volMax
              MeasurementItem mi=MeasurementItem.factory("idEntity1", "dataSource1", "myFormat", "idMetric"+j, 
                      BigDecimal.TEN.multiply(BigDecimal.valueOf(r.nextGaussian())),"PRJ1","EC1");
              if((j%2)==0) mi.setContext(myContext);
              mis.add(mi);
          }
          
          flujo.setMeasurements(mis);

        while((System.nanoTime()-start)<=(minutes*60000000000L))
        {          
          registro=new ArrayList();
          registro.add(ZonedDateTime.now());//# TimeStamp
          
          Long before,after;
          before=after=0L;
                              
          String json=TranslateJSON.toJSON(flujo);          
          before=System.nanoTime();
          MessageDigest md=MessageDigest.getInstance("MD5");
          md.update(json.getBytes());                
          String fp=Conversions.toHexString(md.digest());
          after=System.nanoTime();// 
          registro.add(after-before);//1. elapsed time in the fingerprint calculus
          
          registro.add(json.getBytes().length);//4. Bytes associated with the original message
          
          before=System.nanoTime();
          MAEnvelope origin=MAEnvelope.create("adapter1", fp, json, 60L);
          origin.addMA("adapter2", (short)2);
          origin.addMA("adapter3", (short)3);
          after=System.nanoTime();
          registro.add(after-before);//4. elapsed time in the envelope creation

          before=System.nanoTime();
          String enveloped=TranslateJSON.toJSON(origin);
          after=System.nanoTime();
          registro.add(after-before);//5. Elapsed time in Object-JSON translation for the enveloped message                    
          
          registro.add(enveloped.getBytes().length);//6. Enveloped Byte Size
          
          before=System.nanoTime();
          byte bjson[]=ZipUtil.compressGZIP(enveloped);
          after=System.nanoTime();
          registro.add(after-before); //7. elapsed time compression gzip
          registro.add(bjson.length);//8. compressed bytes
          
          before=System.nanoTime();
          String ebjson=ZipUtil.decompressGZIP(bjson);
          after=System.nanoTime();
          registro.add(after-before);//9.elapsed time descompressed

          before=System.nanoTime();
          MAEnvelope restaurajson=(MAEnvelope) TranslateJSON.toObject(MAEnvelope.class, ebjson);
          after=System.nanoTime();
          registro.add(after-before);//10.elapsed tyme json to object
          
          Boolean check_json=(json.equalsIgnoreCase(restaurajson.getOriginalMessage()));
          registro.add(check_json);//11. equal   
          tabla.add(registro);
          
          if(title)
          {
            System.out.println("JSON");
            System.out.println("1.#TimeStamp 2.(ns) Fingerprint 3.(bytes)Original Message  4.(ns) Envelope 5.(ns) CreatingEnvelopedJSON "
                    + "6.(bytes) Enveloped Message Size 7.(ns)GZIP 8.Compressed_bytes  9.(ns)UnGZIP  10.(ns)xmlToObject  11.Integrity");
            title=false;
          }
          
          for(Object ptr:registro)
            {
                System.out.print(ptr+";");
            }
          System.out.println();
        }
        
        for(Object rec:tabla)
        {
            ((ArrayList)rec).clear();            
        }
        tabla.clear();
    }
    
}
