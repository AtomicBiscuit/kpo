package fm.factories;

import fm.domains.Operation;
import fm.domains.types.Identifier;

/**
 * Интерфейс фабрики для порождения операций.
 *
 * @param <T> параметры создания
 */
public interface OperationFactory<T> {
    /**
     * Возвращает новую операцию.
     *
     * @param params параметры для создания
     * @return {@link Operation}
     */
    Operation createOperation(T params, Identifier id);
}
