package Utils;
//    SSL/TLS connection from Eclipse Paho Java client to mosquitto MQTT broker
//
//    By Sharon Ben Asher
//    AVG Mobilation
//    Sharon.Ben-Asher@AVG.com
//
//    Mosquitto is an Open Source MQTT v3.1 Broker written in C (http://mosquitto.org)
//    Eclipse Paho project has a Java MQTT client (http://eclipse.org/paho/)
//
//    The code snippet below demonstrates how to establish a secured connection from a Paho client to a mosquitto broker.
//    The connection includes server and client authentication through openssl (PEM formatted) certificates.
//
//    1) Follow the instructions on the mosquitto site to produce all the necessary certificates
//    http://mosquitto.org/man/mosquitto-tls-7.html
//
//    2) Configure the broker to expect SSL connections.
//    example configuration:
//    listener 1883
//    cafile /home/ubuntu/etc/ca.crt
//    certfile /home/ubuntu/etc/server.crt
//    keyfile /home/ubuntu/etc/server.key
//    require_certificate true
//    use_identity_as_username true
//
//    3) On the client side, Paho has several options for specifying properties for the creation of SSL sockets
//    (Properties, JVM arguments, etc).  However, none of them will work with mosquitto (historically, Paho worked with IBM brokers).
//    Fortunately, it also accepts a custom made instance of javax.net.ssl.SSLSocketFactory through the method MqttConnectOptions.setSocketFactory() and this works.
//
//    example code using Paho API to establish connection:
//
//    String serverUrl = "ssl://myMosquittoServer.com:1883";
//    MqttClient client = new MqttClient(serverUrl, "consumerId" , null);
//    client.setCallback(new MyCallback());
//    MqttConnectOptions options = new MqttConnectOptions();
//    options.setConnectionTimeout(60);
//    options.setKeepAliveInterval(60);
//    options.setSocketFactory(SslUtil.getSocketFactory("caFilePath", "clientCrtFilePath", "clientKeyFilePath", "password"));
//    client.connect(options);
//    client.subscribe("topic", 0);
//
//    The interesting bit is, of course, SslUtil.getSocketFactory() method.  The code is attached seperately.
//    Since Java cannot read PEM formatted certificates, the method is using bouncy castle (http://www.bouncycastle.org/) to load the necessary files:
//    ca.crt is used to authenticate the server and is used to init an instance of javax.net.ssl.TrustManagerFactory.
//    client.crt/.key are sent to mosquitto for client authentication, and therefore are used to init an instance of javax.net.ssl.KeyManagerFactory.
//    The method expects all files as String full paths.
//    The method is using Files.readAllBytes() which is available in JDK 7.
//    basically, you need to load the file into byte array and pass that array to the constructor of ByteArrayInputStream as is demonstrated in the code.


import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.security.cert.*;
import javax.net.ssl.*;

import org.bouncycastle.jce.provider.*;
import org.bouncycastle.openssl.*;

public class SslUtil
{
    public static SSLSocketFactory getSocketFactory (final String caCrtFile, final String crtFile, final String keyFile,
                                              final String password) throws Exception
    {
        Security.addProvider(new BouncyCastleProvider());

        // load CA certificate
        PEMReader reader = new PEMReader(new InputStreamReader(new ByteArrayInputStream(Files.readAllBytes(Paths.get(caCrtFile)))));
        X509Certificate caCert = (X509Certificate)reader.readObject();
        reader.close();

        // load client certificate
        reader = new PEMReader(new InputStreamReader(new ByteArrayInputStream(Files.readAllBytes(Paths.get(crtFile)))));
        X509Certificate cert = (X509Certificate)reader.readObject();
        reader.close();

        // load client private key
        reader = new PEMReader(
                new InputStreamReader(new ByteArrayInputStream(Files.readAllBytes(Paths.get(keyFile)))),
                new PasswordFinder() {
                    @Override
                    public char[] getPassword() {
                        return password.toCharArray();
                    }
                }
        );
        KeyPair key = (KeyPair)reader.readObject();
        reader.close();

        // CA certificate is used to authenticate server
        KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
        caKs.load(null, null);
        caKs.setCertificateEntry("ca-certificate", caCert);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(caKs);

        // client key and certificates are sent to server so it can authenticate us
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null, null);
        ks.setCertificateEntry("certificate", cert);
        ks.setKeyEntry("private-key", key.getPrivate(), password.toCharArray(), new java.security.cert.Certificate[]{cert});
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, password.toCharArray());

        // finally, create SSL socket factory
        SSLContext context = SSLContext.getInstance("TLSv1.1");
        context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        return context.getSocketFactory();
    }
}

