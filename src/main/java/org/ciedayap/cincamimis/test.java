/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.cincamimis;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
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
          testCincamimis_json_and_compression(); 
    }
    
    public static void testCincamimis_xml_and_compression() throws LikelihoodDistributionException, NoSuchAlgorithmException, Exception
    {
       LikelihoodDistribution ld=LikelihoodDistribution.factoryRandomDistributionEqualLikelihood(3L, 5L);        
       MeasurementItem m=MeasurementItem.factory("idEntity1", "dsid1", "format", "idMetric1", ld);
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
       MeasurementItem m=MeasurementItem.factory("idEntity1", "dsid1", "format", "idMetric1", ld);
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
}
