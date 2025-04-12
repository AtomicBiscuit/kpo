package fm.domains;

import fm.domains.types.Identifier;
import lombok.*;

/**
 * Доменный класс, представляющий банковский счёт.
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class BankAccount {
    private Identifier id;

    private String name;

    private int balance;
}
