package hse.kpo.domains;

import lombok.*;

/**
 * Класс, представляющий клиентов сервиса.
 */
@Getter
@ToString
@Builder
public class Customer {
    public final String name;

    private final int legPower;

    private final int handPower;

    private final int iq;

    @Setter
    private Car car;
}
