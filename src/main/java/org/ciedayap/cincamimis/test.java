/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.cincamimis;

import org.ciedayap.utils.TranslateXML;

/**
 *
 * @author Mario
 */
public class test {
    public static void main(String args[])
    {
        Cincamimis obj=new Cincamimis();
        obj.setDsAdapterID("DS1");
        MeasurementItemSet conj=new MeasurementItemSet();
        conj.setItemsQuantity(10);
        obj.setMeasurements(conj);
        System.out.println(obj.toString());
        System.out.println(TranslateXML.toXml(obj.getClass(), obj));
        
    }
}
