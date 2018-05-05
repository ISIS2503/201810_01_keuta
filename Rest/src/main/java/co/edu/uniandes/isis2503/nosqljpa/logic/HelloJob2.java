/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.logic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.converter.OrdenConverter;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.OrdenDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.entity.OrdenEntity;
import co.edu.uniandes.isis2503.nosqljpa.persistence.OrdenPersistence;
import java.util.ArrayList;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 *
 * @author cm.alba10
 */
 public class HelloJob2 implements Job {

    public HelloJob2() {
    }

    public void execute(JobExecutionContext context)
      throws JobExecutionException
    {
      System.err.println("Hello!  HelloJob is executing.");
      //Creo una instacna de la logica de la contraseña
      ContrasenaLogic logica= new ContrasenaLogic();
      //Obtengo todas las ordenes de la BD
      OrdenPersistence persistence = new OrdenPersistence();
      ArrayList<OrdenEntity> entities = (ArrayList<OrdenEntity>) persistence.all();
      //Convierto las ordenes de entity a DTO
      OrdenConverter converter = new OrdenConverter();
      ArrayList<OrdenDTO> dtos =(ArrayList<OrdenDTO>) converter.listEntitiesToListDTOs(entities);
      //Recorro cada una de las ordenes DTO
      for (OrdenDTO dtoActual : dtos) {
          //Verifico cada una de las ordenes
            logica.verificarPeriodicamente(dtoActual);
        }
      
      
    }

   
  }
