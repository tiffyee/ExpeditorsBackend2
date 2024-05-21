package expeditors.backend.adoptapp.exceptions;

public class OurAuthWebException extends Exception{
   public OurAuthWebException(String message, Exception rootCause) {
      super(message, rootCause);
   }
}
