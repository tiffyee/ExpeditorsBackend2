package ttl.larku.jconfig.client;

import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.client.RestClientSsl;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Component
public class RestClientFactory {
   @Autowired
   private ApplicationContext context;

   @Value("${spring.profiles.active}")
   private String profiles;

   @Autowired
   private RestClientSsl restClientSsl;

   /**
    * Convenience method to get a RestClient which is set up for SSL (or not)
    * depending on whether the 'ssl' profile is active.
    * @param baseUrl
    * @param user
    * @param pw
    * @return
    */
   public RestClient get(String baseUrl, String user, String pw) {
      //var restClient = profiles.contains("ssl") ? sslFromRestTemplate(baseUrl, user, pw)
      var restClient = profiles.contains("ssl") ? sslClientFromRestClient(baseUrl, user, pw)
            : basicAuth(baseUrl, user, pw);

      return restClient;
   }

   /**
    * A RestClient with no Authorization header.
    * @param baseUrl
    * @return
    */
   public RestClient noAuth(String baseUrl) {
      var restClient = RestClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader("Accept", "application/json")
            .defaultHeader("Content-Type", "application/json")
            .build();

      return restClient;
   }

   /**
    * A RestClient with an Authorization header, but no SSL.
    *
    * @param baseUrl
    * @param user
    * @param pw
    * @return
    */
   public RestClient basicAuth(String baseUrl, String user, String pw) {
      String basicAuthHeader = "basic " + Base64.getEncoder()
            .encodeToString((user + ":" + pw).getBytes());

      var restClient = RestClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader("Accept", "application/json")
            .defaultHeader("Content-Type", "application/json")
            .defaultHeader("Authorization", basicAuthHeader)
            .build();

      return restClient;
   }

   /**
    * Here we are making a RestClient in the Spring Security 6 way, with "sslBundles".
    * This will only work if we have the public certificate of the server installed in
    * the 'cacerts' file for the JDK that is running the client code.  Look in "README.SSL"
    * in the resources directory for instructions on how to do that.
    */
   public RestClient sslClientFromBundle(String baseUrl, String user, String pw) {
      baseUrl = baseUrl.replace("http:", "https:");
      String basicAuthHeader = "basic " + Base64.getEncoder()
            .encodeToString((user + ":" + pw).getBytes());

      //var sslClientBuilder = context.getBean("bundledRestClient", RestClient.Builder.class);
      var sslClientBuilder = RestClient.builder()
            .apply(restClientSsl.fromBundle("web-server"));

      var restClient = sslClientBuilder
            .baseUrl(baseUrl)
            .defaultHeader("Accept", "application/json")
            .defaultHeader("Content-Type", "application/json")
            .defaultHeader("Authorization", basicAuthHeader)
            .uriBuilderFactory(new DefaultUriBuilderFactory(baseUrl))
            .build();

      return restClient;
   }

   /**
    * Here we have are using a Bean called 'sslRestClientBuilder' to build our RestClient.
    * That bean gets an SSL context which will accept the self-signed SSL certificate that
    * our local server is going to send when we try to connect.
    * @param baseUrl baseURL
    * @param user    Username
    * @param pw      User password
    * @return  A Configured RestClient
    */
   public RestClient sslClientFromRestClient(String baseUrl, String user, String pw) {
      baseUrl = baseUrl.replace("http:", "https:");
      String basicAuthHeader = "basic " + Base64.getEncoder()
            .encodeToString((user + ":" + pw).getBytes());

      var sslClientBuilder = context.getBean("sslRestClientBuilder", RestClient.Builder.class);

      var restClient = sslClientBuilder
            .baseUrl(baseUrl)
            .defaultHeader("Accept", "application/json")
            .defaultHeader("Content-Type", "application/json")
            .defaultHeader("Authorization", basicAuthHeader)
            .uriBuilderFactory(new DefaultUriBuilderFactory(baseUrl))
            .build();

      return restClient;
   }

   /**
    * Here we are going to create the client from a RestTemplate that has been
    * configured in the "sslRestTemplate" bean.  That RestTemplate is configured
    * to accept self-signed certificates, which is what we are going to get from our
    * local server.
    * @param baseUrl
    * @param user
    * @param pw
    * @return
    */
   public RestClient sslFromRestTemplate(String baseUrl, String user, String pw) {
      baseUrl = baseUrl.replace("http:", "https:");
      String basicAuthHeader = "basic " + Base64.getEncoder()
            .encodeToString((user + ":" + pw).getBytes());

      var sslRestTemplate = context.getBean("sslRestTemplate", RestTemplate.class);

      sslRestTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(baseUrl));
      var restClient = RestClient.builder(sslRestTemplate)
            .baseUrl(baseUrl)
            .defaultHeader("Accept", "application/json")
            .defaultHeader("Content-Type", "application/json")
            .defaultHeader("Authorization", basicAuthHeader)
            .build();

      return restClient;
   }
}