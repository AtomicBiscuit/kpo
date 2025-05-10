package fm.storages;

import fm.domains.BankAccount;
import fm.domains.types.Identifier;
import fm.visitors.Visitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Хранилище информации о банковских счетах.
 */
@Component
public class AccountStorage {
    private final List<BankAccount> accounts = new ArrayList<>();

    /**
     * Добавляет {@link BankAccount} в хранилище.
     *
     * @param account счёт для добавления
     */
    public void addAccount(BankAccount account) {
        accounts.add(account);
    }

    /**
     * Возвращает список всех хранимых счетов.
     *
     * @return Список из {@link BankAccount}
     */
    public List<BankAccount> getAllAccounts() {
        return accounts;
    }


    /**
     * Возвращает {@link BankAccount}по {@link Identifier}.
     *
     * @param id идентификатор целевого счёта
     * @return {@link BankAccount}, если такой счёт есть в хранилище
     */
    public Optional<BankAccount> getAccount(Identifier id) {
        return accounts.stream().filter(account -> account.getId().equals(id)).findFirst();
    }

    /**
     * Удаляет банковский счёт по {@link Identifier}.
     *
     * @param id идентификатор целевого счёта
     */
    public void removeAccount(Identifier id) {
        accounts.removeIf(account -> account.getId().equals(id));
    }

    /**
     * Принимает посетителя для всех хранимых объектов
     *
     * @param visitor посетитель
     */
    public void accept(Visitor visitor) {
        accounts.forEach(visitor::visit);
    }
}
