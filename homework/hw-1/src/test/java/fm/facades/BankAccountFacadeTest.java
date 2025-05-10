package fm.facades;

import fm.domains.BankAccount;
import fm.domains.types.Identifier;
import fm.facade.BankAccountFacade;
import fm.helpers.ConsoleHelper;
import fm.storages.AccountStorage;
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
class BankAccountFacadeTest {
    @Autowired
    BankAccountFacade facade;

    @Autowired
    AccountStorage storage;

    @MockitoBean
    ConsoleHelper helperMock;

    List<BankAccount> accounts;

    ByteArrayOutputStream output = new ByteArrayOutputStream();


    @BeforeEach
    void setUp() {
        accounts = new ArrayList<>();
        output.reset();
        Mockito.when(helperMock.getOutput()).thenReturn(new PrintStream(output));
        Mockito.when(helperMock.readLine(Mockito.anyString())).thenAnswer(new Answer() {
            private int id = 0;

            public String answer(InvocationOnMock invocation) {
                return accounts.get(id++).getName();
            }
        });
        Mockito.when(helperMock.readInt(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
               .thenAnswer(new Answer() {
                   private int id = 0;

                   public Integer answer(InvocationOnMock invocation) {
                       return accounts.get(id++).getBalance();
                   }
               });
    }

    @Test
    @DisplayName("Создание простого счёта")
    void simpleAccount_ShouldCreate() {
        accounts.add(new BankAccount(new Identifier(1), "Name", 101));

        var id = facade.createAccount();
        var accountRaw = storage.getAccount(new Identifier(id));
        Assertions.assertTrue(accountRaw.isPresent());
        BankAccount account = accountRaw.get();
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, id),
                () -> Assertions.assertEquals("Name", account.getName()),
                () -> Assertions.assertEquals(101, account.getBalance())
        );
    }

    @Test
    @DisplayName("Создание 5 новых счетов")
    void multiCreateAccount_ShouldCreate() {
        accounts.addAll(List.of(
                new BankAccount(new Identifier(1), "First", -100_00_000),
                new BankAccount(new Identifier(2), "First", 100_00_000),
                new BankAccount(new Identifier(3), "Middle", 0),
                new BankAccount(new Identifier(4), "Pre-last account", 42),
                new BankAccount(new Identifier(5), "Last", 11)
        ));

        facade.createAccount();
        facade.createAccount();
        facade.createAccount();
        facade.createAccount();
        facade.createAccount();

        for (int i = 0; i < 5; i++) {
            final int id = i + 1;
            var accountRaw = storage.getAccount(new Identifier(id));
            Assertions.assertTrue(accountRaw.isPresent());
            BankAccount account = accountRaw.get();
            Assertions.assertAll(
                    () -> Assertions.assertEquals(accounts.get(id - 1).getId(), account.getId()),
                    () -> Assertions.assertEquals(accounts.get(id - 1).getName(), account.getName()),
                    () -> Assertions.assertEquals(accounts.get(id - 1).getBalance(), account.getBalance())
            );
        }
    }

    @Test
    @DisplayName("Удаление 1 счёта")
    void removeAccount_ShouldRemove() {
        accounts.add(new BankAccount(new Identifier(1), "First", -100_00_000));
        facade.createAccount();

        Assertions.assertEquals(1, storage.getAllAccounts().size());

        facade.removeAccount(new Identifier(1));

        Assertions.assertTrue(storage.getAllAccounts().isEmpty());
    }

    @Test
    @DisplayName("Удаление несуществующего счёта не должно ничего делать")
    void removeNonExistingAccount_ShouldDoNothing() {
        accounts.add(new BankAccount(new Identifier(1), "First", -100_00_000));
        accounts.add(new BankAccount(new Identifier(2), "Second", 101));

        facade.createAccount();
        facade.createAccount();

        var oldStorage = List.copyOf(storage.getAllAccounts());

        facade.removeAccount(new Identifier(3));
        Assertions.assertEquals(oldStorage, storage.getAllAccounts());
    }

    @Test
    @DisplayName("Изменение 1 счёта")
    void changeAccount_ShouldChange() {
        accounts.add(new BankAccount(new Identifier(1), "First", 500_000));
        accounts.add(new BankAccount(new Identifier(1), "First-Collapsed", -120));
        facade.createAccount();

        facade.changeAccount(new Identifier(1));

        var account = storage.getAccount(new Identifier(1));
        Assertions.assertTrue(account.isPresent());
        Assertions.assertEquals(accounts.get(1), account.get());
    }

    @Test
    @DisplayName("Изменение несуществующего счёта не должно ничего менять")
    void changeNonExistentAccount_ShouldDoNothing() {
        accounts.add(new BankAccount(new Identifier(1), "Tester", 50_000));
        facade.createAccount();

        facade.changeAccount(new Identifier(5));

        Assertions.assertEquals(accounts.getFirst(), storage.getAccount(new Identifier(1)).get());
        Assertions.assertEquals(1, storage.getAllAccounts().size());
    }

    @Test
    @DisplayName("Сохранение информации о 3 аккаунтах в поток вывода")
    void printAllAccounts() {
        accounts.add(new BankAccount(new Identifier(1), "Tester", 5_123));
        accounts.add(new BankAccount(new Identifier(2), "Atom", 1_000_000));
        accounts.add(new BankAccount(new Identifier(3), "Electron", -1));
        facade.createAccount();
        facade.createAccount();
        facade.createAccount();

        facade.printAll(new PrintStream(output));

        accounts.forEach(account -> Assertions.assertTrue(output.toString().contains(account.toString())));
    }
}