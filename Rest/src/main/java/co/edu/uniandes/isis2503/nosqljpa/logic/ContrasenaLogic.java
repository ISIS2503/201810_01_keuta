package co.edu.uniandes.isis2503.nosqljpa.logic;

import co.edu.uniandes.isis2503.nosqljpa.interfaces.IConstrasenaLogic;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IInmuebleLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.OrdenDTO;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class ContrasenaLogic implements IConstrasenaLogic {

    private static final String SEPARADOR_TOPICO = "/";

    private static final String SEPARADOR_MENSAJE = ";";

    private final IInmuebleLogic inmuebleLogic = new InmuebleLogic();

    public ContrasenaLogic() {
    }

    @Override
    public OrdenDTO add(OrdenDTO dto, String usuario) throws Exception{
        tienePermiso(dto, usuario);
        publicarOrden(dto.getIdUnidad() + SEPARADOR_TOPICO + dto.getIdInmueble() + SEPARADOR_TOPICO + dto.getIdCerradura()
        + SEPARADOR_TOPICO + "entrada", "addPassword" + SEPARADOR_MENSAJE + dto.getClave() + SEPARADOR_MENSAJE + dto.getIdClave());
        return dto;
    }

    @Override
    public OrdenDTO update(OrdenDTO dto, String usuario) throws Exception{
        tienePermiso(dto, usuario);
        publicarOrden(dto.getIdUnidad() + SEPARADOR_TOPICO + dto.getIdInmueble() + SEPARADOR_TOPICO + dto.getIdCerradura()
                + SEPARADOR_TOPICO + "entrada", "updatePassword" + SEPARADOR_MENSAJE + dto.getClave() + SEPARADOR_MENSAJE + dto.getIdClave());
        return dto;
    }

    @Override
    public void delete(OrdenDTO dto, String usuario) throws Exception{
        tienePermiso(dto, usuario);
        publicarOrden(dto.getIdUnidad() + SEPARADOR_TOPICO + dto.getIdInmueble() + SEPARADOR_TOPICO + dto.getIdCerradura()
                + SEPARADOR_TOPICO + "entrada", "deletePassword" + SEPARADOR_MENSAJE + "0" + SEPARADOR_MENSAJE + dto.getIdClave());
    }

    @Override
    public void deleteAll(OrdenDTO dto, String usuario) throws Exception{
        tienePermiso(dto, usuario);
        publicarOrden(dto.getIdUnidad() + SEPARADOR_TOPICO + dto.getIdInmueble() + SEPARADOR_TOPICO + dto.getIdCerradura()
                + SEPARADOR_TOPICO + "entrada", "deleteAllPasswords" + SEPARADOR_MENSAJE + "0" + SEPARADOR_MENSAJE + "0");
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
            System.out.println("Connecting to broker: "+broker);
            mqttClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: "+content);
            System.out.println("Topic: "+topic);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            mqttClient.publish(topic, message);
            System.out.println("Message published");
            mqttClient.disconnect();
            System.out.println("Disconnected");
        } catch (MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }

    private void tienePermiso(OrdenDTO dto, String usuario) throws Exception {
        System.out.println(inmuebleLogic.find(dto.getIdInmueble()).getNombrePropietario() + ":" + usuario);
        if(!inmuebleLogic.find(dto.getIdInmueble()).getNombrePropietario().equals(usuario)){
            throw new  Exception("El usuario no es el dueño del inmueble.");
        }
        System.out.println("Permiso - OK");
    }
}
