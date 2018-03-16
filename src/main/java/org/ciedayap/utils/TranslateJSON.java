/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.utils;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.security.NoSuchAlgorithmException;

/**
 * This class incorporates utilities for converting to/from JSON and Objects using Google GSON
 * library.
 * Google GSON is distributed under license Apache 2.0
 * 
 * @author Mario Diván
 * @version 1.0
 */
public class TranslateJSON {
    /**
     * It is responsible for translating the ptr object to JSON representation
     * using GSON library and GSON-javatime-serialisers for dealing with the Java 8 java.time.*
     * @param ptr The object to be translated
     * @return A string representation related to JSON
     */
    public static String toJSON(Object ptr) 
    {
        if(ptr==null) return null;
        
        final GsonBuilder builder = Converters.registerAll(new GsonBuilder());
        final Gson gson = builder.create();
        return gson.toJson(ptr);
    }
    
    /**
     * It is responsible for translating the JSON string to Object using GSON library from Google
     * and GSON-javatime-serialisers for dealing with the Java 8 java.time.* from Fatbolyindustrial.
     * @param origin The target class
     * @param json The JSON string representation
     * @return A instance related to the target class
     */
    public static Object toObject(Class<?> origin,String json) 
    {
        if(origin==null || json==null) return null;
        final GsonBuilder builder = Converters.registerAll(new GsonBuilder());
        final Gson gson = builder.create();       
        
        return gson.fromJson(json, origin);
    }
}
