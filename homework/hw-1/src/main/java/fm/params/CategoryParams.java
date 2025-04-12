package fm.params;

import fm.enums.OperationType;

/**
 * Интерфейс для получения параметров создания категории.
 */
public interface CategoryParams {
    String getName();

    OperationType getType();
}
