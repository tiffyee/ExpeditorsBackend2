package ttl.larku.misc.certtests;

import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;


/**
 * Just for fun.  You should not do this unless you
 * are a Security God.  And others think so, not just you.
 */
public class OurTrustSelfSignedStrategy {

   public static final OurTrustSelfSignedStrategy
         INSTANCE = new OurTrustSelfSignedStrategy();

   private X509Certificate ourSelfSignedCertificate;

   public OurTrustSelfSignedStrategy() {
      try {
         CertificateFactory cf = CertificateFactory.getInstance("X.509");
         InputStream finStream = getClass().getClassLoader().getResourceAsStream("larkUPublicKey.cer");
         ourSelfSignedCertificate = (X509Certificate) cf.generateCertificate(finStream);

//            finStream = getClass().getClassLoader().getResourceAsStream("larkUKeyfile.p12");
//            CertPath cp = cf.generateCertPath(finStream, "PKCS7");
//            // print each certificate in the path
//            List<Certificate> certs = (List<Certificate>) cp.getCertificates();
//            for (Certificate cert : certs) {
//               System.out.println("CERT: " + cert);
//            }
      }catch(Exception e) {
         e.printStackTrace();
      }
   }

   public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
      return chain.length == 1;
   }

//      private boolean validateCert(X509Certificate certificate) {
//         CertPathValidator cpv = CertPathValidator.getInstance("PKIX");
//         PKIXRevocationChecker rc = (PKIXRevocationChecker)cpv.getRevocationChecker();
//         rc.setOptions(EnumSet.of(Option.SOFT_FAIL));
//         params.addCertPathChecker(rc);
//         CertPathValidatorResult cpvr = cpv.validate(path, params);
//      }
}
