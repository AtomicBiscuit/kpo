package fm.factories;

import fm.domains.Operation;
import fm.domains.types.Identifier;
import fm.params.OperationParams;

/**
 * Интерфейс фабрики для порождения операций.
 */
public interface OperationFactory {
    /**
     * Возвращает новую операцию.
     *
     * @param params параметры для создания
     * @return {@link Operation}
     */
    Operation createOperation(OperationParams params, Identifier id);
}
