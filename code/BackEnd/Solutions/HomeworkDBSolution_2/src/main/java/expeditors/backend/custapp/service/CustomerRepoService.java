package expeditors.backend.custapp.service;


import expeditors.backend.custapp.dao.repo.CustomerRepo;
import expeditors.backend.custapp.domain.CustNameWithPhonesDTO;
import expeditors.backend.custapp.domain.CustWithPhoneProjection;
import expeditors.backend.custapp.domain.Customer;
import expeditors.backend.custapp.domain.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

/**
 * @author whynot
 */

@Service
@Transactional
public class CustomerRepoService {

    private final int MIN_AGE = 18;

    @Autowired
    private CustomerRepo customerDAO; // = DAOFactory.customerDAO(); //new InMemoryCustomerDAO();

    public Customer addCustomer(Customer c) {
        //Maybe do some validation on customer
        //Must be older than 18
        long age = c.getDob().until(LocalDate.now(), ChronoUnit.YEARS);
        if (age < 18) {
            throw new RuntimeException("Customer age must be at least " + MIN_AGE);
        }
        return customerDAO.save(c);

    }

    public boolean deleteCustomer(int id) {
        try {
            if (customerDAO.existsById(id)) {
                customerDAO.deleteById(id);
                return true;
            }
        }catch(JpaSystemException ex) {
            System.out.println(ex);
        }
        return false;
    }

    public boolean updateCustomer(Customer c) {
        try {
            if (customerDAO.existsById(c.getId())) {
                customerDAO.save(c);
                return true;
            }
        }catch(JpaSystemException ex) {
            System.out.println(ex);
        }
        return false;
    }

    public CustomerDTO getCustomer(int id) {
        return customerDAO.findOnlyCustomer(id).orElse(null);
    }

    public Customer getCustomerWithPhones(int id) {
        return customerDAO.findCustomerWithPhoneNumberById(id).orElse(null);
    }

    public List<CustomerDTO> getCustomers() {
        return customerDAO.findOnlyCustomers();
    }

    public List<Customer> getCustomersBy(Map<String, Object> queryStrings) {
        Customer example = new Customer();
        queryStrings.forEach((k, v) -> {
            if(k.equals("name")) {
                example.setName((String)v);
            } else if(k.equals("dob")) {
                example.setDob((LocalDate)v);
            } else if(k.equals("status")) {
                example.setStatus((Customer.Status)v);
            }
        });

        List<Customer> result = findByExample(example);
        return result;
    }

    /**
     * An example of creating a DTO.  Here we are using our custom JPARepo
     * implementation to call a method we have written that uses the
     * JPA api and Hibernate specific code to extract a Tuple result set
     * into a CustWithPhone DTO.  Look in JPARepo and JPARepoImpl for code.
     */
    public List<CustNameWithPhonesDTO> getAllCustNameWithPhonesDTO() {
        List<CustNameWithPhonesDTO> cwps = customerDAO.findAllCustNameWithPhones();

        return cwps;
    }

    /**
     * Get CustNameWithPhone for particular phone
     * @return
     */
    public List<CustNameWithPhonesDTO> getCustNameWithPhonesDTO(String phoneNumber) {
        List<CustNameWithPhonesDTO> cwps = customerDAO.findSingleCustWithNameDTO(phoneNumber);

        return cwps;
    }

    /**
     * Here we are going to use Spring Data Projections to do the
     * same thing.  In this case, what we get back is a JDK Dynamic
     * Proxy object, in which we can only see what our CustWithPhoneProjection
     * exposes.
     */
    public List<CustWithPhoneProjection> getAllCustomersWithPhoneUsingProjection() {
        List<CustWithPhoneProjection> cwps = customerDAO.findAllCustWithPhoneProjectionBy();

        return cwps;
    }

    /**
     * We are using the Query By Example feature of Spring Data JPA.
     * We can make an Example from the "probe" (example) passed
     * in.  We can also specify how we would like things to
     * be matched using the ExampleMatcher.  The actual matching
     * is done by the library.
     * @param probe
     * @return
     */
    public List<Customer> findByExample(Customer probe) {
        //Here we can set up rules on how to do the matching.
        //We are selecting anything that matches on any property (matchingAny),
        // matching the "name" property with contains,
        // matching the "dob" property with equals,
        // matching the "status" property with equals,
        // not looking at the "id" property,
        // ignoring case for all matches,
        //and not matching null values.
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("name", contains())
                .withMatcher("dob", exact())
                .withMatcher("status", exact())
                .withIgnorePaths("id")
                .withIgnoreCase()
                .withIgnoreNullValues();

        //Now we make our Example from the probe and the matcher
        Example<Customer> example = Example.of(probe, matcher);

        List<Customer> found = customerDAO.findAll(example);

        return found;
    }
}
