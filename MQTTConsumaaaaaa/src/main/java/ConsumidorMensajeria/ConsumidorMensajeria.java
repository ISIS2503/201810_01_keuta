package ConsumidorMensajeria;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.json.JSONObject;
import Utils.SslUtil;

public class ConsumidorMensajeria implements MqttCallback{

    public static void main(String[] args) {

        String topic        = "prioridad/idconjunto/nroresidencia/dispositivo";
        int qos             = 0;
        String broker       = "ssl://172.24.41.162:8083";
        String clientId     = "JavaSample";

        try {
            ConsumidorMensajeria sampleClient = new ConsumidorMensajeria(broker, clientId);
            sampleClient.subscribe(topic,qos);
        } catch (MqttException me) {
            // Display full details of any exception that occurs
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }

    }

    // Private instance variables
    private MqttClient 			client;
    private String 				brokerUrl;
    private MqttConnectOptions 	conOpt;

    /**
     * Constructs an instance of the client wrapper
     * @param brokerUrl the url of the server to connect to
     * @param clientId the client id to connect with
     */
    public ConsumidorMensajeria(String brokerUrl, String clientId) {
        this.brokerUrl = brokerUrl;
        //This client stores in a temporary directory... where messages temporarily
        // stored until the message has been delivered to the server.
        //..a real application ought to store them somewhere
        // where they are not likely to get deleted or tampered with
        String tmpDir = System.getProperty("java.io.tmpdir");
        MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);

        try {
            // Construct the connection options object that contains connection parameters
            // such as cleanSession and LWT
            conOpt = new MqttConnectOptions();
            conOpt.setUserName("microcontrolador");
            conOpt.setPassword("Isis2503.".toCharArray());
            conOpt.setSocketFactory(SslUtil.getSocketFactory("C:\\Users\\f.reyes948\\git\\201810_01_keuta\\ssl\\m2mqtt_ca.crt", "C:\\Users\\f.reyes948\\git\\201810_01_keuta\\ssl\\m2mqtt_cli.crt", "C:\\Users\\f.reyes948\\git\\201810_01_keuta\\ssl\\m2mqtt_cli.key", "Isis2503."));

            // Construct an MQTT blocking mode client
            client = new MqttClient(this.brokerUrl,clientId, dataStore);

            // Set this wrapper as the callback handler
            client.setCallback(this);

        } catch (MqttException e) {
            e.printStackTrace();
            System.out.println("Unable to set up client: "+e.toString());
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to set up client: "+e.toString());
            System.exit(1);
        }
    }

    /**
     * Subscribe to a topic on an MQTT server
     * Once subscribed this method waits for the messages to arrive from the server
     * that match the subscription. It continues listening for messages until the enter key is
     * pressed.
     * @param topicName to subscribe to (can be wild carded)
     * @param qos the maximum quality of service to receive messages at for this subscription
     * @throws MqttException
     */
    public void subscribe(String topicName, int qos) throws MqttException {

        // Connect to the MQTT server
        client.connect(conOpt);
        System.out.println("Connected to "+brokerUrl+" with client ID "+client.getClientId());

        // Subscribe to the requested topic
        // The QoS specified is the maximum level that messages will be sent to the client at.
        // For instance if QoS 1 is specified, any messages originally published at QoS 2 will
        // be downgraded to 1 when delivering to the client but messages published at 1 and 0
        // will be received at the same level they were published at.
        System.out.println("Subscribing to topic \""+topicName+"\" qos "+qos);
        client.subscribe(topicName, qos);

        // Continue waiting for messages until the Enter is pressed
        System.out.println("Press <Enter> to exit");
        try {
            System.in.read();
        } catch (IOException e) {
            //If we can't read we'll just exit
        }

        // Disconnect the client from the server
        client.disconnect();
        System.out.println("Disconnected");
    }

    /****************************************************************/
    /* Methods to implement the MqttCallback interface              */
    /****************************************************************/

    /**
     * @see MqttCallback#connectionLost(Throwable)
     */
    public void connectionLost(Throwable cause) {
        // Called when the connection to the server has been lost.
        // An application may choose to implement reconnection
        // logic at this point. This client simply exits.
        System.out.println("Connection to " + brokerUrl + " lost!" + cause);
        System.exit(1);
    }

    /**
     * @see MqttCallback#deliveryComplete(IMqttDeliveryToken)
     */
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Called when a message has been delivered to the
        // server. The token passed in here is the same one
        // that was passed to or returned from the original call to publish.
        // This allows applications to perform asynchronous
        // delivery without blocking until delivery completes.
        //
        // This client demonstrates asynchronous deliver and
        // uses the token.waitForCompletion() call in the ConsumidorMensajeria.consumidorMensajeria thread which
        // blocks until the delivery has completed.
        // Additionally the deliveryComplete method will be called if
        // the callback is set on the client
        //
        // If the connection to the server breaks before delivery has completed
        // delivery of a message will complete after the client has re-connected.
        // The getPendingTokens method will provide tokens for any messages
        // that are still to be delivered.
    }

    /**
     * @see MqttCallback#messageArrived(String, MqttMessage)
     */
    public void messageArrived(String topic, MqttMessage message) throws MqttException {
        // Called when a message arrives from the server that matches any
        // subscription made by the client
        String time = new Timestamp(System.currentTimeMillis()).toString();
        System.out.println("Time:\t" +time +
                "  Topic:\t" + topic +
                "  Message:\t" + new String(message.getPayload()) +
                "  QoS:\t" + message.getQos());

        try {
            String mensajeCrudo = new String(message.getPayload());
            String[] partesDelMensaje = mensajeCrudo.split(";");
            String remitente = partesDelMensaje[0];
            String destinatario = partesDelMensaje[1];
            String asunto = "Alerta de cerradura";
            String cuerpo = partesDelMensaje[2];

            URL url = new URL("http://localhost:9090/correo");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());

            JSONObject tomJsonObj = new JSONObject();
            tomJsonObj.put("remitente", remitente);
            tomJsonObj.put("destinatarios", new String[] { destinatario });
            tomJsonObj.put("asunto", asunto);
            tomJsonObj.put("cuerpo", cuerpo);
            System.out.println(tomJsonObj);

            out.writeBytes(tomJsonObj.toString());
            out.flush();
            out.close();

            int status = con.getResponseCode();
            System.out.println("Status is: " + status);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            System.out.println("Response is: " + content);
            in.close();
            con.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /****************************************************************/
    /* End of MqttCallback methods                                  */
    /****************************************************************/
}