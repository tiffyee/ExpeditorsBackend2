package ttl.mie.domain.track;

public enum ErrorCoded
{
   SERIOUS(10),
   NORMAL(25),
   NOT_SO_SERIOUS(200);

   private int errorCode;
   ErrorCoded(int errorCode) {
      this.errorCode = errorCode;
   }

   public int getErrorCode() {
     return errorCode;
   }

}
