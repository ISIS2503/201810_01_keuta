package co.edu.uniandes.isis2503.nosqljpa.logic;

import co.edu.uniandes.isis2503.nosqljpa.interfaces.IConstrasenaLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.OrdenDTO;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class ContrasenaLogic implements IConstrasenaLogic {

    private static final String SEPARADOR_TOPICO = "/";

    private static final String SEPARADOR_MENSAJE = ";";

    public ContrasenaLogic() {
    }

    @Override
    public OrdenDTO add(OrdenDTO dto) {
        publicarOrden(dto.getIdUnidad() + SEPARADOR_TOPICO + dto.getIdInmueble() + SEPARADOR_TOPICO + dto.getIdCerradura()
        + SEPARADOR_TOPICO + "entrada", "AGREGAR_CLAVE" + SEPARADOR_MENSAJE + dto.getIdClave() + SEPARADOR_MENSAJE + dto.getClave());
        return dto;
    }

    @Override
    public OrdenDTO update(OrdenDTO dto) {
        publicarOrden(dto.getIdUnidad() + SEPARADOR_TOPICO + dto.getIdInmueble() + SEPARADOR_TOPICO + dto.getIdCerradura()
                + SEPARADOR_TOPICO + "entrada", "CAMBIAR_CLAVE" + SEPARADOR_MENSAJE + dto.getIdClave() + SEPARADOR_MENSAJE + dto.getClave());
        return dto;
    }

    @Override
    public void delete(String idUnidad, String idInmueble, String idDispositivo, String numclave) {
        publicarOrden(idUnidad + SEPARADOR_TOPICO + idInmueble + SEPARADOR_TOPICO + idDispositivo
                + SEPARADOR_TOPICO + "entrada", "ELIMINAR_CLAVE" + SEPARADOR_MENSAJE + numclave + SEPARADOR_MENSAJE + "");
    }

    @Override
    public void deleteAll(String idUnidad, String idInmueble, String idDispositivo) {
        publicarOrden(idUnidad + SEPARADOR_TOPICO + idInmueble + SEPARADOR_TOPICO + idDispositivo
                + SEPARADOR_TOPICO + "entrada", "ELIMINAR_CLAVES" + SEPARADOR_MENSAJE + "" + SEPARADOR_MENSAJE + "");
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
}
