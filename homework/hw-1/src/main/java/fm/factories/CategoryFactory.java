package fm.factories;

import fm.domains.Category;
import fm.domains.types.Identifier;
import fm.params.CategoryParams;

/**
 * Интерфейс фабрики для порождения категорий.
 */
public interface CategoryFactory {
    /**
     * Возвращает новую категорию.
     *
     * @param params параметры для создания
     * @return {@link Category}
     */
    Category createCategory(CategoryParams params, Identifier id);
}
