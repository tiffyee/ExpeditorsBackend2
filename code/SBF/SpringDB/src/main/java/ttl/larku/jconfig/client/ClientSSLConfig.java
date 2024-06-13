package ttl.larku.jconfig.client;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLContext;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.client.RestClientSsl;
import org.springframework.boot.ssl.SslBundle;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

/**
 * To configure a Client to use SSL.  We have a server that is using a
 * self-signed certificate.  The JDK that runs our client is going to
 * refuse to connect to that server unless:
 * 1) we configure our client to allow self-signed certificates, which we
 * do in 'sslRestTemplate' and 'sslRestClientBuilder' below.
 * 2) we can avoid all that configuration if we add the self-signed certificate
 * to the JDK that is going to run the client.  (Instructions in README.SSL)
 *
 * Brief Instruction on making and using self-signed certificate.
 * (More complete instruction in README.SSL)
 * 1) Make a self signed certificate.  Important - the CN field
 * where it asks you for first and last name, should be set to
 * the hostname, e.g. localhost.
 * keytool -genkeypair -alias larkuspring
 * -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore larkUKeyfile.p12
 * <p>
 * 2) Make sure you point to this file with the appropriate properties in
 * application.[properties|yml]
 * Look at application-ssl.properties
 *
 * <p>
 * To configure RestTemplate to use SSL see the 'sslRestTemplate' method below.
 */
@Configuration
@Profile("ssl | ssltest")
public class ClientSSLConfig {

   private static Logger logger = LoggerFactory.getLogger(ClientSSLConfig.class);

   @Autowired
   private SslBundles sslBundles;
   private SslBundle ourBundle;

   /**
    */
   public ClientSSLConfig() {
//      ourBundle.getManagers().getTrustManagers()[0] = myTrustManager[0];
   }

   /**
    * Make a RestTemplate from an ssl-bundle declared in application-ssl.properties.
    * This will work perfectly if we are accessing sites that have valid certificates.
    * This will *FAIL* perfectly on sites that use self-signed certificates,
    * *UNLESS* we add those certificates to the 'cacerts' file of the JDK running the
    * client code.  (More in README.SSL)
    * @return
    */
   @Bean("bundledRestTemplate")
   public RestTemplate bundleSSLTemplateThatDoesNotWork() {
      var rt = new RestTemplateBuilder()
            .setSslBundle(sslBundles.getBundle("web-server"))
            .build();

      return rt;
   }

   /**
    * Make a RestClient.Builder from an ssl-bundle declared in application-ssl.properties.
    * This will work perfectly if we are accessing sites that have valid certificates.
    * This will *FAIL* perfectly on sites that use self-signed certificates,
    * *UNLESS* we add those certificates to the 'cacerts' file of the JDK running the
    * client code.  (Or, more generally, to the TrustStore we set up in our
    * application-ssl.properties file) (More in README.SSL)
    *
    * We are returning a RestClient.Builder here rather than a RestClient because we want
    * to allow the user of this bean to be able to further configure the client.  e.g. look
    * in RestClientFactory.
    *
    * @return
    */
   @Bean("bundledRestClient")
   public RestClient.Builder bundleRestClient(RestClientSsl restClientSsl) {
      var rt = RestClient.builder()
            .apply(restClientSsl.fromBundle("web-server"));

      return rt;
   }

   /**
    * A RestTemplate that allows us to access servers that uses self-signed certificates.
    */
   @Bean("sslRestTemplate")
   public RestTemplate sslRestTemplate(@Value("${CLIENT_PASSWORD}") String password) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
      KeyStore clientStore = KeyStore.getInstance("PKCS12");
      //We point it at the same keystore as the server
      clientStore.load(getClass().getResourceAsStream("larkUKeyfile.p12"), password.toCharArray());

      SSLContext sslContext = SSLContexts.custom()
            .loadKeyMaterial(clientStore, password.toCharArray())
            .loadTrustMaterial(new TrustSelfSignedStrategy())
            .build();

      SSLConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactoryBuilder.create()
            .setSslContext(sslContext)
            .setHostnameVerifier(NoopHostnameVerifier.INSTANCE)
            .build();
      HttpClientConnectionManager cm =
            PoolingHttpClientConnectionManagerBuilder.create()
                  .setSSLSocketFactory(sslSocketFactory).build();

      CloseableHttpClient httpClient = HttpClients.custom()
            .setConnectionManager(cm)
            .build();

      HttpComponentsClientHttpRequestFactory requestFactory =
            new HttpComponentsClientHttpRequestFactory();

      requestFactory.setHttpClient(httpClient);
      requestFactory.setConnectTimeout(10000); // 10 seconds

      RestTemplate restTemplate = new RestTemplateBuilder()
            .requestFactory(() -> requestFactory)
            .build();

      return restTemplate;
   }

   /**
    * A RestClient.Builder that allows us to access servers that uses self-signed certificates.
    *
    * We are returning a RestClient.Builder here rather than a RestClient because we want
    * to allow the user of this bean to be able to further configure the client.  e.g. look
    * in RestClientFactory.
    */
   @Bean
   public RestClient.Builder sslRestClientBuilder(@Value("${CLIENT_PASSWORD}") String password) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
      KeyStore clientStore = KeyStore.getInstance("PKCS12");
      //We point it at the same keystore as the server
      //clientStore.load(getClass().getResourceAsStream("larkUKeyfile.p12"), password.toCharArray());
      clientStore.load(getClass().getResourceAsStream("larkUKeyfile.p12"), password.toCharArray());
//      InputStream is = new FileInputStream("/tmp/certs/larkUKeyfile.p12");
//      clientStore.load(is, password.toCharArray());

      SSLContext sslContext = SSLContexts.custom()
            .loadKeyMaterial(clientStore, password.toCharArray())
            .loadTrustMaterial(new TrustSelfSignedStrategy())
//            .loadTrustMaterial(ResourceUtils.getFile("classpath:larkUTrustStore.p12"), password.toCharArray())
            .build();

      SSLConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactoryBuilder.create()
            .setSslContext(sslContext)
            .setHostnameVerifier(NoopHostnameVerifier.INSTANCE)
            .build();
      HttpClientConnectionManager cm =
            PoolingHttpClientConnectionManagerBuilder.create()
                  .setSSLSocketFactory(sslSocketFactory).build();

      CloseableHttpClient httpClient = HttpClients.custom()
            .setConnectionManager(cm)
            .build();

      HttpComponentsClientHttpRequestFactory requestFactory =
            new HttpComponentsClientHttpRequestFactory();

      requestFactory.setHttpClient(httpClient);
      requestFactory.setConnectTimeout(10000); // 10 seconds

      var restClientBuilder = RestClient.builder()
            .requestFactory(requestFactory);
//            .build();

      return restClientBuilder;
   }
}