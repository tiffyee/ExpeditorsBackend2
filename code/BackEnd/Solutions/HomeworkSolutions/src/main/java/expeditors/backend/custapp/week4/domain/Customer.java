package expeditors.backend.custapp.week4.domain;

import java.time.LocalDate;

/**
 * @author whynot
 */
public class Customer {

    public enum Status {
        PRIVILEGED,
        NORMAL,
        RESTRICTED
    }

    /*
        1. Constructors
    a. Create appropriate Constructors for your class.  Try and initialize
    your object in only one place in the code.
    b. Make sure your object is always properly initialized, no matter
    which constructor is called.  All variables that should not be null should have an appropriate value.
    c. Write Unit tests for all your constructors. Aim for 100% coverage on the constructors.

     */

    private int id;
    private String name;
    private LocalDate dob;
    private Customer.Status status;

    public Customer(int id, String name, LocalDate dob, Customer.Status status) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.status = status;
    }

    public Customer(String name, LocalDate dob, Status status) {
        this(0, name, dob, status);
    }

    public Customer(String name, LocalDate dob) {
        this(0, name, dob, Status.NORMAL);
    }

    public Customer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", status=" + status +
                '}';
    }
}
