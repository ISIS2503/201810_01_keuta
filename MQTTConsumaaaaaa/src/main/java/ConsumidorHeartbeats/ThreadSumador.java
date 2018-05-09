/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConsumidorHeartbeats;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author da.ramirezv
 */
public class ThreadSumador implements Runnable {

    @Override
    public void run() {

        int s = 60;
        try {

            while (true) {
                Thread.sleep(1000 * s);
                ConsumidorHeartbeat.TIEMPO += 1;
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadSumador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
