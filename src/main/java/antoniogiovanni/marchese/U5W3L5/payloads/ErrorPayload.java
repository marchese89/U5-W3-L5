package antoniogiovanni.marchese.U5W3L5.payloads;
import java.time.LocalDateTime;
public record ErrorPayload(String message,
                           LocalDateTime timestamp) {
}
