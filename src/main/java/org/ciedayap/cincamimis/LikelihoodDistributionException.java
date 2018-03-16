/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ciedayap.cincamimis;

/**
 * It represents an exception associated with a likelihood distribution
 * e.g. if the likelihood sumatory is upper than 1.
 * 
 * @author Mario Diván
 * @version 1.0
 */
public class LikelihoodDistributionException extends Exception{
    public LikelihoodDistributionException()
    { 
    }
    
    public LikelihoodDistributionException(String message)
    {
        super(message);
    }
}
