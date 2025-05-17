package hse.kpo.kafka.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hse.kpo.kafka.CustomerAddedEvent;
import hse.kpo.kafka.KafkaProducerService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@Component
public class OutboxProcessor {
    private final OutboxEventRepository outboxEventRepository;

    private final ObjectMapper objectMapper;

    private final KafkaProducerService kafkaProducerService;

    @Transactional
    @Scheduled(fixedRate = 5_000)
    public void processOutbox() throws JsonProcessingException {
        List<OutboxEvent> outboxEvents = outboxEventRepository.findAllBySentFalseOrderByCreatedAtAsc();

        for (var event : outboxEvents) {
            try {
                var customerEvent = objectMapper.readValue(event.getPayload(), CustomerAddedEvent.class);

                kafkaProducerService.sendCustomerToTraining(customerEvent);
                event.setSent(true);
                outboxEventRepository.save(event);
            } catch (Exception e) {
                log.error("error: " + e.getMessage());
                throw e;
            }
        }
    }
}
