package fm.factories;

import fm.domains.Category;
import fm.domains.types.Identifier;

/**
 * Интерфейс фабрики для порождения категорий.
 *
 * @param <T> параметры создания
 */
public interface CategoryFactory<T> {
    /**
     * Возвращает новую категорию.
     *
     * @param params параметры для создания
     * @return {@link Category}
     */
    Category createCategory(T params, Identifier id);
}
