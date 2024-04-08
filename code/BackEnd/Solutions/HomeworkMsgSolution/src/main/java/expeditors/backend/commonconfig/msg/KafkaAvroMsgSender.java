package expeditors.backend.commonconfig.msg;

import expeditors.backend.avro.SimpleCustomerMessage;
import expeditors.backend.custapp.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author whynot
 */

@Component
//@Profile("kafkaevents & avromsg")
@Profile("kkavroevents")
public class KafkaAvroMsgSender implements MessageSender {

    @Value("${ttl.kafka.adopter.topic}")
    private String topic;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final KafkaTemplate<String, Object> template;

    public KafkaAvroMsgSender(KafkaTemplate<String, Object> template) {
        this.template = template;
    }

    @Override
    public void sendMessage(Object message) {
        sendMessage(message, topic);
    }
    @Override
    public void sendMessage(Object message, String resolvedTopic) {
        if (message instanceof Customer customer) {
            var dob = customer.getDob() != null ? customer.getDob().toString() : "";
            SimpleCustomerMessage ssm = SimpleCustomerMessage.newBuilder()
                    .setName(customer.getName())
//                    .setPhoneNumber(customer.getPhoneNumber())
                    .setDob(dob)
                    .setStatus(customer.getStatus().toString())
                    .setTimeStamp(LocalDateTime.now().toString())
                    .build();


            System.err.println("KafkaAvroSender Sending to topic: " + resolvedTopic + ", Our topic: " + topic);
            var result = template.send(resolvedTopic, ssm);

        }
    }
}