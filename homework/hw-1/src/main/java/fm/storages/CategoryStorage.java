package fm.storages;

import fm.domains.Category;
import fm.domains.types.Identifier;
import fm.visitors.Visitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Хранилище информации о категориях.
 */
@Component
public class CategoryStorage {
    private final List<Category> categories = new ArrayList<>();

    /**
     * Добавляет {@link Category} в хранилище.
     *
     * @param category счёт для добавления
     */
    public void addCategory(Category category) {
        categories.add(category);
    }

    /**
     * Возвращает список всех хранимых счетов.
     *
     * @return Список из {@link Category}
     */
    public List<Category> getAllCategories() {
        return categories;
    }


    /**
     * Возвращает {@link Category} по {@link Identifier}.
     *
     * @param id идентификатор целевого счёта
     * @return {@link Category}, если такая категория есть в хранилище
     */
    public Optional<Category> getCategory(Identifier id) {
        return categories.stream().filter(category -> category.getId().equals(id)).findFirst();
    }

    /**
     * Удаляет категорию по {@link Identifier}.
     *
     * @param id идентификатор целевой категории
     */
    public void removeCategory(Identifier id) {
        categories.removeIf(category -> category.getId().equals(id));
    }

    /**
     * Принимает посетителя для всех хранимых объектов
     *
     * @param visitor посетитель
     */
    public void accept(Visitor visitor) {
        categories.forEach(visitor::visit);
    }
}
