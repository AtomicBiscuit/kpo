package fm.domains;

import fm.domains.types.Identifier;
import fm.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Доменный класс, представляющий категорию.
 */
@Getter
@Setter
@AllArgsConstructor
public class Category {
    private Identifier id;

    private String name;

    private OperationType type;
}
