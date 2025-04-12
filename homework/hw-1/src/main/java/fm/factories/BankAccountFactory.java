package fm.factories;

import fm.domains.BankAccount;
import fm.domains.types.Identifier;
import fm.params.BankAccountParams;

/**
 * Интерфейс фабрики для порождения банковских счетов.
 */
public interface BankAccountFactory {
    /**
     * Возвращает новый банковский счёт.
     *
     * @param params параметры для создания
     * @return {@link BankAccount}
     */
    BankAccount createAccount(BankAccountParams params, Identifier id);
}
