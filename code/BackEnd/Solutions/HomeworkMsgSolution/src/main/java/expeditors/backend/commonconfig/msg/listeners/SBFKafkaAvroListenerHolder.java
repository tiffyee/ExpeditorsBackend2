package expeditors.backend.commonconfig.msg.listeners;

import expeditors.backend.avro.SimpleCustomerMessage;
import expeditors.backend.custapp.domain.Customer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Profile("kkavroevents")
public class SBFKafkaAvroListenerHolder {

    public SBFKafkaAvroListenerHolder(@Value("${ttl.kafka.adopter.topic}") String tp) {
        int stop = 0;
    }

    @KafkaListener(groupId = "adoptapp-avro-consumer-group", topics = "${ttl.kafka.adopter.topic}")
    public void listen(ConsumerRecord<String, Object> consumerRecord, Object payload) {
        System.err.println("########################################\n");
        System.err.println("payload: " + payload);


        if(payload instanceof SimpleCustomerMessage scm) {
            Customer receivedCustomer = new Customer(scm.getName(),
                    LocalDate.parse(scm.getDob()),
                    Customer.Status.valueOf(scm.getStatus()));
        }
        //Process the customer
    }
}
