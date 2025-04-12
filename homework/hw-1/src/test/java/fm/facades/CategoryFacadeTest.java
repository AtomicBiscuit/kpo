package fm.facades;

import fm.domains.Category;
import fm.domains.types.Identifier;
import fm.enums.OperationType;
import fm.facade.CategoryFacade;
import fm.helpers.ConsoleHelper;
import fm.storages.CategoryStorage;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@Component
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CategoryFacadeTest {
    @Autowired
    CategoryFacade facade;

    @Autowired
    CategoryStorage storage;

    @MockitoBean
    ConsoleHelper helperMock;

    List<Category> categories;

    ByteArrayOutputStream output = new ByteArrayOutputStream();


    @BeforeEach
    void setUp() {
        categories = new ArrayList<>();
        output.reset();
        Mockito.when(helperMock.getOutput()).thenReturn(new PrintStream(output));
        Mockito.when(helperMock.readLine(Mockito.anyString())).thenAnswer(new Answer() {
            private int id = 0;

            public String answer(InvocationOnMock invocation) {
                return categories.get(id++).getName();
            }
        });
        Mockito.when(helperMock.readInt(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
               .thenAnswer(new Answer() {
                   private int id = 0;

                   public Integer answer(InvocationOnMock invocation) {
                       return categories.get(id++).getType().ordinal() + 1;
                   }
               });
    }

    @Test
    @DisplayName("Создание простой категории")
    void simpleCategory_ShouldCreate() {
        categories.add(new Category(new Identifier(1), "Name", OperationType.EXPENSES));

        var id = facade.createCategory();
        var raw = storage.getCategory(new Identifier(id));

        Assertions.assertTrue(raw.isPresent());
        var category = raw.get();
        Assertions.assertEquals(categories.getFirst(), category);
    }

    @Test
    @DisplayName("Создание 3 новых категорий")
    void multiCreateCategory_ShouldCreate() {
        categories.addAll(List.of(
                new Category(new Identifier(1), "Internet", OperationType.EXPENSES),
                new Category(new Identifier(2), "Job", OperationType.INCOME),
                new Category(new Identifier(3), "Cafe", OperationType.EXPENSES)
        ));

        facade.createCategory();
        facade.createCategory();
        facade.createCategory();

        for (int i = 0; i < 3; i++) {
            final int id = i + 1;
            var raw = storage.getCategory(new Identifier(id));
            Assertions.assertTrue(raw.isPresent());
            Category category = raw.get();
            Assertions.assertEquals(categories.get(id - 1), category);
        }
    }

    @Test
    @DisplayName("Удаление 1 категории")
    void removeCategory_ShouldRemove() {
        categories.add(new Category(new Identifier(1), "Donations", OperationType.INCOME));
        facade.createCategory();

        Assertions.assertEquals(1, storage.getAllCategories().size());

        facade.removeCategory(new Identifier(1));

        Assertions.assertTrue(storage.getAllCategories().isEmpty());
    }

    @Test
    @DisplayName("Удаление несуществующей категории не должно ничего делать")
    void removeNonExistingCategory_ShouldDoNothing() {
        categories.add(new Category(new Identifier(1), "Income", OperationType.INCOME));
        categories.add(new Category(new Identifier(2), "Trades", OperationType.EXPENSES));

        facade.createCategory();
        facade.createCategory();

        var oldStorage = List.copyOf(storage.getAllCategories());

        facade.removeCategory(new Identifier(42));
        Assertions.assertEquals(oldStorage, storage.getAllCategories());
    }

    @Test
    @DisplayName("Изменение 1 категории")
    void changeCategory_ShouldChange() {
        categories.add(new Category(new Identifier(1), "Jon", OperationType.INCOME));
        categories.add(new Category(new Identifier(1), "Job", OperationType.INCOME));
        facade.createCategory();

        facade.changeCategory(new Identifier(1));

        var category = storage.getCategory(new Identifier(1));
        Assertions.assertTrue(category.isPresent());
        Assertions.assertEquals(categories.get(1), category.get());
    }

    @Test
    @DisplayName("Изменение несуществующей категории не должно ничего менять")
    void changeNonExistentCategory_ShouldDoNothing() {
        categories.add(new Category(new Identifier(1), "Fortuna", OperationType.INCOME));
        facade.createCategory();

        facade.changeCategory(new Identifier(5));

        Assertions.assertEquals(categories.getFirst(), storage.getCategory(new Identifier(1)).get());
        Assertions.assertEquals(1, storage.getAllCategories().size());
    }

    @Test
    @DisplayName("Сохранение информацию о 3 категориях в поток вывода")
    void printAllAccounts() {
        categories.add(new Category(new Identifier(1), "SMS", OperationType.EXPENSES));
        categories.add(new Category(new Identifier(2), "Mobile", OperationType.INCOME));
        categories.add(new Category(new Identifier(3), "Electronic", OperationType.EXPENSES));
        facade.createCategory();
        facade.createCategory();
        facade.createCategory();

        facade.printAll(new PrintStream(output));

        categories.forEach(account -> Assertions.assertTrue(output.toString().contains(account.toString())));
    }
}