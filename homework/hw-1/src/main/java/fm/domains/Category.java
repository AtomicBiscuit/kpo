package fm.domains;

import fm.domains.types.Identifier;
import fm.enums.OperationType;
import lombok.*;

/**
 * Доменный класс, представляющий категорию.
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Category {
    private Identifier id;

    private String name;

    private OperationType type;
}
