package fm.domains;

import static fm.enums.OperationType.INCOME;

import fm.domains.types.Identifier;
import fm.enums.OperationType;
import java.util.Date;
import lombok.*;

/**
 * Доменный класс, представляющий операцию.
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Operation {
    private Identifier id;

    private Identifier bankAccountId;

    private Identifier categoryId;

    private OperationType type;

    private int amount;

    private String name;

    private String description;

    private Date date;

    public int calculateSignedAmount() {
        return amount * (type == INCOME ? 1 : -1);
    }
}
