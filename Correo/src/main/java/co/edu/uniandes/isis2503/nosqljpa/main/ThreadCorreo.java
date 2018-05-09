/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.main;

import org.hyperic.sigar.SysInfo;

/**
 *
 * @author da.ramirezv
 */
public class ThreadCorreo implements Runnable{

    @Override
    public void run() {

        while (true) {

            if (Main.TIEMPO > 3) {
                
                System.out.println("HUB FUERA DE LINEA");
                
                break;
            }
        }
    }
   
}
