package expeditors.backend.custapp.week2.domain;

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
        4. Customer class.  This is going to be the start of a long running
        exercise you are going to build on for the rest of the class.  You should
        put all code for this exercise into it’s own root package e.g. expeditors.backend.custapp.  Create sub-packages under the root as necessary.
    a. Create a class called Customer with at least the following properties
            i. id of type int
            ii. name of type String
            iii. dob of type LocalDate – see below on how to create LocalDate objects
            iv. status which can be one of
                1. Normal
                2. Restricted
                3. Privileged
    c. Make sure you encapsulate your properties appropriately
        i. private variables
        ii. getters and setters
     */

    private int id;
    private String name;
    private LocalDate dob;
    private Status status;

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
}
