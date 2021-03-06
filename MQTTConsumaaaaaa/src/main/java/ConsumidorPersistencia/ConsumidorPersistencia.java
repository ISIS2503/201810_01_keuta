package ConsumidorPersistencia;

import Utils.SslUtil;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ConsumidorPersistencia implements MqttCallback{

    public static void main(String[] args) {

        String topic        = "prioridad/idconjunto/nroresidencia/dispositivo";
        int qos             = 0;
        String broker       = "ssl://172.24.41.162:8083";
        String clientId     = "JavaSample2";

        try {
            ConsumidorPersistencia sampleClient = new ConsumidorPersistencia(broker, clientId);
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

    //Auth0 Token
    private String token;

    /**
     * Constructs an instance of the client wrapper
     * @param brokerUrl the url of the server to connect to
     * @param clientId the client id to connect with
     */
    public ConsumidorPersistencia(String brokerUrl, String clientId) {
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

            //Get auth0 token.
            URL url = new URL("https://isis2503-daramirezv.auth0.com/oauth/token");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());

            JSONObject tomJsonObj = new JSONObject();
            tomJsonObj.put("grant_type", "client_credentials");
            tomJsonObj.put("client_id", "6SGnWSj-Us9t63VJHwgJC-S223qCvqSM");
            tomJsonObj.put("client_secret", "m8sfjctqF-w8Y6i7543c6X2yokMRjopiVARfbUNg5C97Zwi1TIjKxJeLgGoRJ61Y");
            tomJsonObj.put("audience", "uniandes.edu.co/thermalcomfort");

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

            tomJsonObj = new JSONObject(content.toString());
            token = tomJsonObj.getString("access_token");

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
        // uses the token.waitForCompletion() call in the ConsumidorMensajeria.ConsumidorPersistencia thread which
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
            String mensaje = partesDelMensaje[2];

            String[] partesTopico = topic.split("/");
            String prioridad = partesTopico[0];
            String unidadResidencial = partesTopico[1];
            String inmueble = partesTopico[2];
            String dispositivo = partesTopico[3];

            URL url = new URL("http://localhost:8080/alarma");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Bearer " + token);
            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());

            JSONObject tomJsonObj = new JSONObject();
            tomJsonObj.put("idMensaje", new Double(Math.random()*1000).intValue());
            tomJsonObj.put("mensaje", mensaje);
            tomJsonObj.put("prioridad", prioridad);
            tomJsonObj.put("unidadResidencial", unidadResidencial);
            tomJsonObj.put("inmueble", inmueble);
            tomJsonObj.put("dispositivo", dispositivo);
            tomJsonObj.put("fecha", LocalDateTime.now());

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