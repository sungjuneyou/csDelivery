package carshare;

import carshare.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    DeliveryRepository deliveryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaid_Ship(@Payload Paid paid){

        if(paid.isMe()){
            System.out.println("##### listener Ship : " + paid.toJson());

            Delivery delivery = new Delivery();
            delivery.setOrderId(paid.getOrderId());
            delivery.setProductId(paid.getProductId());
            delivery.setQty(paid.getQty());
            delivery.setPaymentId(paid.getId());
            delivery.setStatus("Shipped");

            deliveryRepository.save(delivery) ;
        }
    }

}
