/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

/**
 * This class allows compressing and/or uncompressing a String using GZIP/Deflater
 * @author Mario Diván
 * @version 1.0
 */
public class ZipUtil {
    /**
     * Internal method which allows copying from the InputStream to the OutputStream
     * 
     * @param is data origin
     * @param os The selected destination for the data to be copied 
     * @throws Exception In any inconvenient along the copy process
     */
    protected static void doCopy(InputStream is, OutputStream os) throws Exception 
    {
        int oneByte;
	while ((oneByte = is.read()) != -1) 
        {
		os.write(oneByte);
	}

        os.close();
	is.close();
    }
    
    /**
     * It compresses the string using Deflater 
     * 
     * @param mystring String (UTF-8) to be compressed
     * @return Compressed byte array
     * @throws Exception Any inconvenient along the copy operation and/or the Deflater object creation
     */
    protected static byte[] zip(String mystring) throws Exception
    {
        if(mystring==null || mystring.length()==0) return null;
        byte[] ret;
        try (ByteArrayInputStream in = new ByteArrayInputStream(mystring.getBytes("UTF-8"))) {
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            try (OutputStream out = new DeflaterOutputStream(baos)) {
                doCopy(in,out);
                ret = baos.toByteArray();
            }
        }
        
        return ret;
    }

    /**
     * It compresses the string using Deflater, deriving the results through the indicated OutputStream
     * 
     * @param mystring The string (UTF-8) to be compressed
     * @param baos  The selected destination for the compressed bytes
     * @throws Exception Along the compression operation
     */    
    protected static void zip(String mystring,OutputStream baos) throws Exception
    {
        if(mystring==null || mystring.length()==0) return;
        try (ByteArrayInputStream in = new ByteArrayInputStream(mystring.getBytes("UTF-8"))) {
            OutputStream out=new DeflaterOutputStream(baos);
            
            doCopy(in,out);
        }        
    }

    /**
     * It compresses the string using GZIP
     * 
     * @param mystring String (UTF-8) to be compressed
     * @return A compressed byte array
     * @throws Exception Along the compression process by GZip
     */    
    protected static byte[] gzip(String mystring) throws Exception
    {
        if(mystring==null || mystring.length()==0) return null;
        byte[] ret;
        try (ByteArrayInputStream in = new ByteArrayInputStream(mystring.getBytes("UTF-8"))) {
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            try (OutputStream out = new GZIPOutputStream(baos)) {
                doCopy(in,out);
                ret = baos.toByteArray();
            }
        }
        
        return ret;
    }

    /**
     * It compressed the string using GZIP, deriving the output to the indicated OutputStream
     * 
     * @param mystring String (UTF-8) to be compressed
     * @param baos  The indicated output
     * @throws Exception it can firing due any inconvenient along the GZIP compression process
     */        
    protected static void gzip(String mystring,OutputStream baos) throws Exception
    {
        if(mystring==null || mystring.length()==0) return;
        try (ByteArrayInputStream in = new ByteArrayInputStream(mystring.getBytes("UTF-8"))) {
            OutputStream out=new GZIPOutputStream(baos);
            
            doCopy(in,out);
        }
    }

    /**
     * It decompresses a compressed byte array using  Inflater
     * 
     * @param compressed The UTF-8 compressed String by Deflater
     * @return decompressed string
     * @throws Exception Along the decompression process
     */
    protected static String unzip(byte[] compressed) throws Exception
    {
        if(compressed==null || compressed.length==0) 
        {
            System.out.println("1");
            return null;
        }
        
        ByteArrayInputStream comes=new ByteArrayInputStream(compressed);        
        String cadena;
        try (InputStream in = new InflaterInputStream(comes); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer=new byte[8192];
            int len;
            while((len=in.read(buffer))>0)
                baos.write(buffer,0,len);
            cadena = new String(baos.toByteArray(),"UTF-8");
        }
                
        return cadena;
    }

    /**
     * It decompresses a compressed byte array using Inflater
     * 
     * @param compressed A InputStream in where it is possible read the compressed string using
     * Deflater
     * @return A decompressed string (UTF-8)
     * @throws Exception Any inconvenient along the decompression process with deflater
     */    
    public static String unzip(InputStream compressed) throws Exception
    {
        if(compressed==null) 
        {
            return null;
        }
        
        String mystring;
        try (InputStream in = new InflaterInputStream(compressed); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer=new byte[8192];
            int len;
            while((len=in.read(buffer))>0)
                baos.write(buffer,0,len);
            mystring = new String(baos.toByteArray(),"UTF-8");
        }
                
        return mystring;
    }

    /**
     * Decompress a string using GZIP
     * 
     * @param compressed compressed string using GZIP
     * @return Decompressed string (UTF-8)
     * @throws Exception Any inconvenient in relation to the decompression process using GZIP
     */        
    protected static String ungzip(byte[] compressed) throws Exception
    {
        if(compressed==null || compressed.length==0) 
        {
            return null;
        }
        
        ByteArrayInputStream viene=new ByteArrayInputStream(compressed);        
        String mystring;
        try (InputStream in = new GZIPInputStream(viene); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer=new byte[8192];
            int len;
            while((len=in.read(buffer))>0)
                baos.write(buffer,0,len);
            mystring = new String(baos.toByteArray(),"UTF-8");
        }
                
        return mystring;
    }

    /**
     * Decompress a string using GZIP
     * 
     * @param comes InputStream in where it is possible read the compressed string
     * @return Decompressed string (UTF-8)
     * @throws java.lang.Exception Any inconvenient along the decompression process using GZIP
     */            
    protected static String ungzip(InputStream comes) throws Exception
    {
        if(comes==null) 
        {
            return null;
        }
        
        String mystring;
        try (InputStream in = new GZIPInputStream(comes); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer=new byte[8192];
            int len;
            while((len=in.read(buffer))>0)
                baos.write(buffer,0,len);
            mystring = new String(baos.toByteArray(),"UTF-8");
        }
                
        return mystring;
    }
    
    /**
     * It allows compressing a string using Deflactor
     * 
     * @param mystring String to be compressed
     * @return Compressed byte arrays
     * @throws Exception Any inconveniente along the compression process
     */
    public static byte[] compress(String mystring) throws Exception
    {
        return zip(mystring);
    }
   
    /**
     * It compresses a string using GZIP
     * 
     * @param mystring String to be compressed
     * @return Compressed byte array using GZIP
     * @throws Exception Any inconvenient along the compression process
     */
    public static byte[] compressGZIP(String mystring) throws Exception
    {
        return gzip(mystring);
    }

    /**
     * It compress a string using GZIP and then, it will directly send the output 
     *  to the OutputStream object.
     * 
     * @param mystring The string to be compressed
     * @param out The gate in which the output will be written
     * @throws Exception Any incovenient along the compression process using GZIP
     */
    public static void comprimirGZIP(String mystring,OutputStream out) throws Exception
    {
        gzip(mystring,out);
    }
   
    /**
     * It decompress a compressed byte array using Deflater
     * 
     * @param mystring Compressed byte array using Deflater
     * @return Decompressed String (UTF-8)
     * @throws Exception Any incovenient along the decompression process
     */
    public static String decompress(byte[] mystring) throws Exception
    {        
        return unzip(mystring);
    }    

    /**
     * It decompressed the compressed byte array using GZIP
     * 
     * @param mystring Compressed byte array using GZIP
     * @return Decompressed string (UTF-8)
     * @throws Exception Any inconvenient along the decompression process using GZIP
     */    
    public static String decompressGZIP(byte[] mystring) throws Exception
    {        
        return ungzip(mystring);
    }    

    /**
     * It decompresses a compressed byte array using Deflater from an InputStream
     * 
     * @param in The InputStream from which the compressed bytes will be read
     * @return Decompressed String (UTF-8)
     * @throws Exception Any inconvenient along the decompression process
     */    
    public static String decompress(InputStream in) throws Exception
    {        
        return unzip(in);
    }    

    /**
     * It decompresses a compressed byte array using GZIP
     * 
     * @param in InputStream from which the compressed byte array will be read for decompressing using GZIP
     * @return Decompressed string (UTF-8)
     * @throws Exception Any inconvenient along the decompression process
     */    
    public static String decompressGZIP(InputStream in) throws Exception
    {        
        return ungzip(in);
    }    
    
}
