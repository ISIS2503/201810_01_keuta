package co.edu.uniandes.isis2503.nosqljpa.logic;

import co.edu.uniandes.isis2503.nosqljpa.interfaces.IConstrasenaLogic;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IInmuebleLogic;
import static co.edu.uniandes.isis2503.nosqljpa.model.dto.converter.OrdenConverter.CONVERTER;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.OrdenDTO;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import co.edu.uniandes.isis2503.nosqljpa.persistence.OrdenPersistence;
import static org.quartz.JobBuilder.newJob;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import org.quartz.Trigger;
import static org.quartz.TriggerBuilder.newTrigger;
import java.text.ParseException;

public class ContrasenaLogic implements IConstrasenaLogic {

    private static final String SEPARADOR_TOPICO = "/";

    private static final String SEPARADOR_MENSAJE = ";";

    private final IInmuebleLogic inmuebleLogic = new InmuebleLogic();

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private final OrdenPersistence persistence;

    public ContrasenaLogic() {
        this.persistence = new OrdenPersistence();
    }

    @Override
    public OrdenDTO add(OrdenDTO dto, String usuario) throws Exception {
        //Verifico que tenga permiso
       
        tienePermiso(dto, usuario);
       
        //Persisto la orden
        OrdenDTO result = CONVERTER.entityToDto(persistence.add(CONVERTER.dtoToEntity(dto)));
       
        //Creo una date para saber la fecha y hora actual
        Date date = new Date();
     
        //Convierto las fechaYhora de String a Date para poder comparar
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy-HH:mm");
        try {
            
            Date dateInicio = simpleDateFormat.parse(result.getFechaYhoraInicial());
            Date dateFinal = simpleDateFormat.parse(result.getFechaYhoraFinal());
          
            //Si la fecha actual está dentro del intervalo mando la orden ya
            if ((date.after(dateInicio) || sdf.format(date).equals(dateInicio)) && (date.before(dateFinal) || sdf.format(date).equals(dateFinal))) {
                publicarOrden(dto.getIdUnidad() + SEPARADOR_TOPICO + dto.getIdInmueble() + SEPARADOR_TOPICO + dto.getIdOrden()
                        + SEPARADOR_TOPICO + "entrada", "addPassword" + SEPARADOR_MENSAJE + dto.getClave() + SEPARADOR_MENSAJE + dto.getIdClave());
                //el estado ahora es activo
             
                dto.estaActivo = true;
                persistence.update(CONVERTER.dtoToEntity(dto));
            }

        } catch (ParseException ex) {
            System.out.println("Exception " + ex);
        }

        funcionProgramada();

        return result;
    }

    @Override
    public OrdenDTO update(OrdenDTO dto, String usuario) throws Exception {
        tienePermiso(dto, usuario);
        OrdenDTO result = CONVERTER.entityToDto(persistence.update(CONVERTER.dtoToEntity(dto)));
        publicarOrden(dto.getIdUnidad() + SEPARADOR_TOPICO + dto.getIdInmueble() + SEPARADOR_TOPICO + dto.getIdOrden()
                + SEPARADOR_TOPICO + "entrada", "updatePassword" + SEPARADOR_MENSAJE + dto.getClave() + SEPARADOR_MENSAJE + dto.getIdClave());
        return result;
    }

    @Override
    public void delete(OrdenDTO dto, String usuario) throws Exception {
        tienePermiso(dto, usuario);
        persistence.delete(String.valueOf(dto.getIdClave()));
        publicarOrden(dto.getIdUnidad() + SEPARADOR_TOPICO + dto.getIdInmueble() + SEPARADOR_TOPICO + dto.getIdOrden()
                + SEPARADOR_TOPICO + "entrada", "deletePassword" + SEPARADOR_MENSAJE + "0" + SEPARADOR_MENSAJE + dto.getIdClave());
    }

    @Override
    public void deleteAll(OrdenDTO dto, String usuario) throws Exception {
        tienePermiso(dto, usuario);
        publicarOrden(dto.getIdUnidad() + SEPARADOR_TOPICO + dto.getIdInmueble() + SEPARADOR_TOPICO + dto.getIdOrden()
                + SEPARADOR_TOPICO + "entrada", "deleteAllPasswords" + SEPARADOR_MENSAJE + "0" + SEPARADOR_MENSAJE + "0");
    }

    public void funcionProgramada() throws SchedulerException {
        SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();

        Scheduler sched = schedFact.getScheduler();

        sched.start();

        // define the job and tie it to our HelloJob class
        JobDetail job = newJob(HelloJob2.class)
                .withIdentity("myJob", "group1")
                .build();

        // Trigger the job to run now, and then every 40 seconds
        Trigger trigger = newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(30)
                        .repeatForever())
                .build();

        // Tell quartz to schedule the job using our trigger
        sched.scheduleJob(job, trigger);

    }

    public void verificarPeriodicamente(OrdenDTO dto) {
        //Creo una date para saber la fecha y hora actual
        Date date = new Date();
        //Pregunto si la fecha actual está dentro del intervalo 
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy-HH:mm");
        try {
            Date dateInicio = simpleDateFormat.parse(dto.getFechaYhoraInicial());
           
            Date dateFinal = simpleDateFormat.parse(dto.getFechaYhoraFinal());
           
            //Si la fecha actual está dentro del intervalo mando la orden ya
            if ((date.after(dateInicio) || sdf.format(date).equals(dateInicio)) && (date.before(dateFinal) || sdf.format(date).equals(dateFinal))) {
                
                   //pregunto si ya está activo
                if (!dto.getEstaActivo()) {
                    
                    //Si no está activo mando la orden de agregarla a la EPROM
                    publicarOrden(dto.getIdUnidad() + SEPARADOR_TOPICO + dto.getIdInmueble() + SEPARADOR_TOPICO + dto.getIdOrden()
                            + SEPARADOR_TOPICO + "entrada", "addPassword" + SEPARADOR_MENSAJE + dto.getClave() + SEPARADOR_MENSAJE + dto.getIdClave());
                    //el estado ahora es activo
                    dto.estaActivo = true;
                persistence.update(CONVERTER.dtoToEntity(dto));

                }

            } else {
                if (dto.getEstaActivo()) {
                //Si la fecha actual no está dentro del intervalo mando la orden de eliminarla de la EPROM
                publicarOrden(dto.getIdUnidad() + SEPARADOR_TOPICO + dto.getIdInmueble() + SEPARADOR_TOPICO + dto.getIdOrden()
                        + SEPARADOR_TOPICO + "entrada", "deletePassword" + SEPARADOR_MENSAJE + "0" + SEPARADOR_MENSAJE + dto.getIdClave());
                //Ahora la desactivo
                dto.estaActivo = false;
                persistence.update(CONVERTER.dtoToEntity(dto));
                }
            }

        } catch (ParseException ex) {
            System.out.println("Exception " + ex);
        }
    }

    private void publicarOrden(String topic, String content) {
        try {
            int qos = 0;
            String broker = "tcp://localhost:8083";
            String clientId = "PublicadorYale";
            MemoryPersistence persistence = new MemoryPersistence();
            MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: " + broker);
            mqttClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: " + content);
            System.out.println("Topic: " + topic);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            mqttClient.publish(topic, message);
            System.out.println("Message published");
            mqttClient.disconnect();
            System.out.println("Disconnected");
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }

    private void tienePermiso(OrdenDTO dto, String usuario) throws Exception {
        //  System.out.println(inmuebleLogic.find(dto.getIdInmueble()).getNombrePropietario() + ":" + usuario);
        //   if (!inmuebleLogic.find(dto.getIdInmueble()).getNombrePropietario().equals(usuario)) {
        //     throw new Exception("El usuario no es el dueño del inmueble.");
        // }
        System.out.println("Permiso - OK");
    }
}
