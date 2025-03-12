package fm.facades;

import fm.domains.BankAccount;
import fm.domains.Category;
import fm.domains.Operation;
import fm.domains.types.Identifier;
import fm.enums.OperationType;
import fm.facade.BankAccountFacade;
import fm.facade.CategoryFacade;
import fm.facade.OperationFacade;
import fm.helpers.ConsoleHelper;
import fm.storages.AccountStorage;
import fm.storages.CategoryStorage;
import fm.storages.OperationStorage;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
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
class OperationFacadeTest {
    @Autowired
    OperationFacade facade;

    @Autowired
    CategoryFacade categoryFacade;

    @Autowired
    BankAccountFacade accountFacade;

    @Autowired
    CategoryStorage categoryStorage;

    @Autowired
    AccountStorage accountStorage;

    @Autowired
    OperationStorage operationStorage;

    @MockitoBean
    ConsoleHelper helperMock;

    List<Operation> operations;

    ByteArrayOutputStream output = new ByteArrayOutputStream();


    @BeforeEach
    void setUp() {
        operations = new ArrayList<>();
        output.reset();
        Mockito.when(helperMock.getOutput()).thenReturn(new PrintStream(output));
        Mockito.when(helperMock.readLine(Mockito.contains("name"))).thenAnswer(new Answer() {
            private int id = 0;

            public String answer(InvocationOnMock invocation) {
                return operations.get(id++).getName();
            }
        });
        Mockito.when(helperMock.readLine(Mockito.contains("description"))).thenAnswer(new Answer() {
            private int id = 0;

            public String answer(InvocationOnMock invocation) {
                return operations.get(id++).getDescription();
            }
        });
        Mockito.when(helperMock.readInt(Mockito.contains("account"), Mockito.anyInt(), Mockito.anyInt()))
               .thenAnswer(new Answer() {
                   private int id = 0;

                   public Integer answer(InvocationOnMock invocation) {
                       return operations.get(id++).getBankAccountId().getId();
                   }
               });
        Mockito.when(helperMock.readInt(Mockito.contains("category"), Mockito.anyInt(), Mockito.anyInt()))
               .thenAnswer(new Answer() {
                   private int id = 0;

                   public Integer answer(InvocationOnMock invocation) {
                       return operations.get(id++).getCategoryId().getId();
                   }
               });
        Mockito.when(helperMock.readInt(Mockito.contains("value"), Mockito.anyInt(), Mockito.anyInt()))
               .thenAnswer(new Answer() {
                   private int id = 0;

                   public Integer answer(InvocationOnMock invocation) {
                       return operations.get(id++).getAmount();
                   }
               });
        Mockito.when(helperMock.readDate(Mockito.anyString())).thenAnswer(new Answer() {
            private int id = 0;

            public Date answer(InvocationOnMock invocation) {
                return operations.get(id++).getDate();
            }
        });
    }

    @Test
    @DisplayName("Создание простой операции, баланс изменяется на значение с учетом знака")
    void simpleOperation_ShouldCreate() {
        accountStorage.addAccount(new BankAccount(new Identifier(1), "Biscuit", 0));
        categoryStorage.addCategory(new Category(new Identifier(1), "Job", OperationType.INCOME));
        operations.add(new Operation(
                new Identifier(1),
                new Identifier(1),
                new Identifier(1),
                OperationType.INCOME,
                5000,
                "Salary",
                "By February",
                new Date(5000000)
        ));

        var id = facade.createOperation();
        var raw = operationStorage.getOperation(new Identifier(id));

        Assertions.assertTrue(raw.isPresent());
        var operation = raw.get();
        Assertions.assertEquals(operations.getFirst(), operation);

        Assertions.assertEquals(
                operation.calculateSignedAmount(),
                accountStorage.getAccount(new Identifier(1)).get().getBalance()
        );
    }

    void addOperation(int id, int bankId, int categoryId, int income, int value, String name, String desc, int date) {
        operations.add(new Operation(
                new Identifier(id),
                new Identifier(bankId),
                new Identifier(categoryId),
                income == 1 ? OperationType.INCOME : OperationType.EXPENSES,
                value,
                name,
                desc,
                new Date(date)
        ));
    }


    @Test
    @DisplayName("Создание 3 новых категорий")
    void multiCreateOperations_ShouldCreate() {
        accountStorage.addAccount(new BankAccount(new Identifier(1), "Biscuit", 0));
        categoryStorage.addCategory(new Category(new Identifier(1), "Job", OperationType.INCOME));
        categoryStorage.addCategory(new Category(new Identifier(2), "Shopping", OperationType.EXPENSES));
        addOperation(1, 1, 1, 1, 350, "Name", "Description", 19_000_000);
        addOperation(2, 1, 2, 2, 120, "Products", "bananas killo", 55_000_001);
        addOperation(3, 1, 2, 2, 11, "Pen", "bought new pen!", 4_000_002);

        facade.createOperation();
        facade.createOperation();
        facade.createOperation();

        Assertions.assertEquals(3, operationStorage.getAllOperations().size());

        for (int i = 0; i < 3; i++) {
            final int id = i + 1;
            var raw = operationStorage.getOperation(new Identifier(id));
            Assertions.assertTrue(raw.isPresent());
            var operation = raw.get();
            Assertions.assertEquals(operations.get(id - 1), operation);
        }
        Assertions.assertEquals(
                operations.stream().mapToInt(Operation::calculateSignedAmount).sum(),
                accountStorage.getAccount(new Identifier(1)).get().getBalance()
        );
    }

    @Test
    @DisplayName("Удаление 1 категории должно удалять все связанные операции")
    void removeCategoryCascade_ShouldRemoveOperationsWithSameCategory() {
        accountStorage.addAccount(new BankAccount(new Identifier(1), "Biscuit", 0));
        categoryStorage.addCategory(new Category(new Identifier(1), "Job", OperationType.INCOME));
        categoryStorage.addCategory(new Category(new Identifier(2), "Shopping", OperationType.EXPENSES));
        addOperation(1, 1, 1, 1, 350, "Name", "Description", 19_000_000);
        addOperation(2, 1, 2, 2, 120, "Products", "bananas killo", 55_000_001);
        addOperation(3, 1, 2, 2, 11, "Pen", "bought new pen!", 4_000_002);

        facade.createOperation();
        facade.createOperation();
        facade.createOperation();

        categoryFacade.removeCategory(new Identifier(2));

        Assertions.assertEquals(350, accountStorage.getAccount(new Identifier(1)).get().getBalance());

        Assertions.assertEquals(1, operationStorage.getAllOperations().size());
        Assertions.assertEquals(operations.getFirst(), operationStorage.getAllOperations().getFirst());
    }

    @Test
    @DisplayName("Удаление несуществующей операции не должно ничего делать")
    void removeNonExistingOperation_ShouldDoNothing() {
        accountStorage.addAccount(new BankAccount(new Identifier(1), "Biscuit", 0));
        categoryStorage.addCategory(new Category(new Identifier(1), "Job", OperationType.INCOME));

        addOperation(1, 1, 1, 1, 350, "Name", "Description", 19_000_000);
        facade.createOperation();

        facade.removeOperation(new Identifier(42));

        Assertions.assertEquals(1, operationStorage.getAllOperations().size());
    }

    @Test
    @DisplayName("Изменение 1 операции, должен меняться также баланс счёта")
    void changeOperation_ShouldChange() {
        accountStorage.addAccount(new BankAccount(new Identifier(1), "Biscuit", 0));
        categoryStorage.addCategory(new Category(new Identifier(1), "Job", OperationType.INCOME));
        addOperation(1, 1, 1, 1, 350, "Job", "Happy", 19_000_000);
        addOperation(1, 1, 1, 1, -600, "Fired", "Sad", 21_000_001);

        facade.createOperation();

        facade.changeOperation(new Identifier(1));

        var operation = operationStorage.getOperation(new Identifier(1));
        Assertions.assertTrue(operation.isPresent());
        Assertions.assertEquals(operations.get(1), operation.get());
        Assertions.assertEquals(-600, accountStorage.getAllAccounts().getFirst().getBalance());
    }
}