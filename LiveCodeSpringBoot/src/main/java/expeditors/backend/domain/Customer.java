package expeditors.backend.domain;

public class Customer {

   private int customerId;
   private String name;
   private String address;
   private String taxId;

   public Customer(int customerId, String name, String address, String taxId) {
      this.customerId = customerId;
      this.name = name;
      this.address = address;
      this.taxId = taxId;
   }

   public int getCustomerId() {
      return customerId;
   }

   public void setCustomerId(int customerId) {
      this.customerId = customerId;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public String getTaxId() {
      return taxId;
   }

   public void setTaxId(String taxId) {
      this.taxId = taxId;
   }
}
