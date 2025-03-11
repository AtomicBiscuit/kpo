package fm.params;

import fm.domains.types.Identifier;
import fm.enums.OperationType;
import java.util.Date;


/**
 * Интерфейс для получения параметров операции.
 */
public interface OperationParams {
    Identifier getBankAccountId();

    Identifier getCategoryId();

    OperationType getType();

    int getAmount();

    String getName();

    String getDescription();

    Date getDate();
}