package fm.factories;

import fm.domains.BankAccount;
import fm.domains.types.Identifier;

/**
 * Интерфейс фабрики для порождения банковских счетов.
 *
 * @param <T> параметры создания
 */
public interface BankAccountFactory<T> {
    /**
     * Возвращает новый банковский счёт.
     *
     * @param params параметры для создания
     * @return {@link BankAccount}
     */
    BankAccount createAccount(T params, Identifier id);
}
