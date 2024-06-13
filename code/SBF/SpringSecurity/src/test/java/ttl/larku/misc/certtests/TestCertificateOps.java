package ttl.larku.misc.certtests;

/**
 * From https://github.com/eugenp/tutorials/blob/master/core-java-modules/core-java-security-4/src/main/java/com/baeldung/certificate/RootCertificateUtil.java
 */

import java.security.KeyStore;
import java.security.SignatureException;
import java.security.cert.X509Certificate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
class TestCertificateOps {

   private KeyStore keyStore;

   private KeyStore trustStore;

   @BeforeEach
   public void setUp() throws Exception {
      char[] passwd = "password".toCharArray();
      keyStore = KeyStore.getInstance("PKCS12");
      keyStore.load(this.getClass().getClassLoader().getResourceAsStream("larkUKeyfile.p12"), passwd);
      //trustStore = KeyStore.getInstance("PKCS12");
      var trustStoreType = KeyStore.getDefaultType();
      trustStore = KeyStore.getInstance(trustStoreType);
//      trustStore.load(this.getClass().getClassLoader().getResourceAsStream("larkUPublicKey.cer"), passwd);
   }

   @Test
   void whenCertificateIsSelfSigned_thenSubjectIsEqualToIssuer() throws Exception {
      X509Certificate certificate = (X509Certificate) keyStore.getCertificate("larkuspring");
      assertEquals(certificate.getSubjectDN(), certificate.getIssuerDN());
   }

   @Test
   void whenCertificateIsSelfSigned_thenItCanBeVerifiedWithItsOwnPublicKey() throws Exception {
      X509Certificate certificate = (X509Certificate) keyStore.getCertificate("larkuspring");
      var publicKey = certificate.getPublicKey();
      assertDoesNotThrow(() -> certificate.verify(certificate.getPublicKey()));
   }

   @Test
   void whenCertificateIsCASigned_thenItCantBeVerifiedWithItsOwnPublicKey() throws Exception {
      X509Certificate certificate = (X509Certificate) keyStore.getCertificate("baeldung");
      assertThrows(SignatureException.class, () -> certificate.verify(certificate.getPublicKey()));
   }

   @Test
   void whenCertificateIsCASigned_thenRootCanBeFoundInTruststore() throws Exception {
      X509Certificate endEntityCertificate = (X509Certificate) keyStore.getCertificate("baeldung");
      X509Certificate rootCertificate = RootCertUtils.getRootCertificate(endEntityCertificate, trustStore);
      assertNotNull(rootCertificate);
   }

   @Test
   void whenCertificateIsCA_thenItCanBeUsedToSignOtherCertificates() throws Exception {
      X509Certificate certificate = (X509Certificate) keyStore.getCertificate("cloudflare");
      assertTrue(certificate.getKeyUsage()[5]);
   }

   @Test
   void whenCertificateIsCA_thenBasicConstrainsReturnsZeroOrGreaterThanZero() throws Exception {
      X509Certificate certificate = (X509Certificate) keyStore.getCertificate("cloudflare");
      assertNotEquals(-1, certificate.getBasicConstraints());
   }

   @Test
   void whenCertificateIsSelfSigned_thenItCantBeUsedToSignOtherCertificates() throws Exception {
      X509Certificate certificate = (X509Certificate) keyStore.getCertificate("larkuspring");
      assertNull(certificate.getKeyUsage());
   }
}
