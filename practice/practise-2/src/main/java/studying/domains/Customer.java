package studying.domains;

import lombok.*;

/**
 Класс, представляющий клиентов сервиса
 */
@Getter
@ToString
@RequiredArgsConstructor
public class Customer {
    private final String name;

    private final int legPower;

    private final int handPower;

    private final int iq;

    @Setter
    private Car car;
}
