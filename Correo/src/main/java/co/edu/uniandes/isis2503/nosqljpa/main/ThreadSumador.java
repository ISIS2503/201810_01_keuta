/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.main;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author da.ramirezv
 */
public class ThreadSumador implements Runnable{

    @Override
    public void run() {
        
        int s = 60;
        try {

            while (true) {
                Thread.sleep(1000 * s);
                Main.TIEMPO += 1;
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
