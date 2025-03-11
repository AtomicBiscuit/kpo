package fm.facade;

import fm.domains.types.Identifier;
import fm.exceptions.IdentifyNotFoundException;
import fm.factories.BankAccountFactory;
import fm.params.BankAccountParams;
import fm.storages.AccountStorage;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BankAccountFacade {
    AccountStorage storage;

    BankAccountFactory factory;

    @Setter
    BankAccountParams params;

    int num = 1;

    public int createAccount() {
        var account = factory.createAccount(params, new Identifier(num++));
        storage.addAccount(account);
        return num - 1;
    }

    public void removeAccount(Identifier id) {
        storage.removeAccount(id);
    }

    public void changeAccount(Identifier id) {
        var rawAccount = storage.getAccount(id);
        if (rawAccount.isEmpty()) {
            throw new IdentifyNotFoundException("Account with id " + id + " not found");
        }
        var account = rawAccount.get();
        account.setName(params.getName());
        account.setBalance(params.getBalance());
    }

    public void addSumToAccount(Identifier id, int delta) {
        var rawAccount = storage.getAccount(id);
        if (rawAccount.isEmpty()) {
            throw new IdentifyNotFoundException("Account with id " + id + " not found");
        }
        var account = rawAccount.get();
        account.setBalance(account.getBalance() + delta);
    }
}
