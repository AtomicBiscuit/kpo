package fm.facade;

import fm.domains.types.Identifier;
import fm.exceptions.IdentifyNotFoundException;
import fm.factories.CategoryFactory;
import fm.params.CategoryParams;
import fm.storages.CategoryStorage;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CategoryFacade {
    CategoryStorage storage;

    CategoryFactory factory;

    @Setter
    CategoryParams params;

    int num = 1;

    public int createCategory() {
        var account = factory.createCategory(params, new Identifier(num++));
        storage.addCategory(account);
        return num - 1;
    }

    public void removeCategory(Identifier id) {
        storage.removeCategory(id);
    }

    public void changeCategory(Identifier id) {
        var rawCategory = storage.getCategory(id);
        if (rawCategory.isEmpty()) {
            throw new IdentifyNotFoundException("Category with id " + id + " not found");
        }
        var category = rawCategory.get();
        category.setName(params.getName());
        category.setType(params.getType());
    }
}
