package fm.params;

import fm.domains.types.Identifier;
import java.util.Date;


/**
 * Интерфейс для получения параметров операции.
 */
public interface OperationParams {
    Identifier getBankAccountId();

    Identifier getCategoryId();

    int getAmount();

    String getName();

    String getDescription();

    Date getDate();
}