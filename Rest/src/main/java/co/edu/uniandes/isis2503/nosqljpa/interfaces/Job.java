/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.interfaces;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author cm.alba10
 */
 
  public interface Job {

    public void execute(JobExecutionContext context)
            throws JobExecutionException;
}
