package fm.domains;

import fm.domains.types.Identifier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Доменный класс, представляющий банковский счёт.
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class BankAccount {
    private Identifier id;

    private String name;

    private int balance;
}
