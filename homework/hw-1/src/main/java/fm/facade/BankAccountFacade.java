package fm.facade;

import static fm.formater.PrettyText.format;

import fm.domains.Operation;
import fm.domains.types.Identifier;
import fm.exceptions.IdentifyNotFoundException;
import fm.factories.BankAccountFactory;
import fm.filter.OperationFilter;
import fm.formater.Format;
import fm.helpers.ConsoleHelper;
import fm.params.BankAccountParams;
import fm.storages.AccountStorage;
import fm.storages.OperationStorage;
import java.io.PrintStream;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class BankAccountFacade {
    AccountStorage accountStorage;

    OperationStorage operationStorage;

    BankAccountFactory factory;

    @Setter
    BankAccountParams params;

    ConsoleHelper helper;

    int num = 1;

    public BankAccountFacade(AccountStorage accountStorage, OperationStorage operationStorage,
                             BankAccountFactory factory, BankAccountParams params, ConsoleHelper helper) {
        this.accountStorage = accountStorage;
        this.operationStorage = operationStorage;
        this.factory = factory;
        this.params = params;
        this.helper = helper;
    }

    public void printAll(PrintStream writer) {
        writer.println(format("Accounts: ", Format.CYAN));
        accountStorage.getAllAccounts().forEach(account -> writer.println("  " + account));
        writer.println(format("End", Format.CYAN));
    }

    public int createAccount() {
        var account = factory.createAccount(params, new Identifier(num++));
        accountStorage.addAccount(account);
        return num - 1;
    }

    public void removeAccount(Identifier id) {
        var cascadeOperations = OperationFilter.builder(operationStorage.getAllOperations())
                                               .filterByAccount(id)
                                               .build();
        cascadeOperations.stream().map(Operation::getId).forEach(operationStorage::removeOperation);
        accountStorage.removeAccount(id);
    }

    public boolean changeAccount(Identifier id) {
        var rawAccount = accountStorage.getAccount(id);
        if (rawAccount.isEmpty()) {
            helper.getOutput()
                  .println(format("Account with id " + id + " not found", Format.RED, Format.BOLD, Format.UNDERLINE));
            return false;
        }
        helper.getOutput().println("Now enter new data");
        var account = rawAccount.get();
        account.setName(params.getName());
        account.setBalance(params.getBalance());
        return true;
    }

    public void addSumToAccount(Identifier id, int delta) {
        var rawAccount = accountStorage.getAccount(id);
        if (rawAccount.isEmpty()) {
            throw new IdentifyNotFoundException("Account with id " + id + " not found");
        }
        var account = rawAccount.get();
        account.setBalance(account.getBalance() + delta);
    }
}
