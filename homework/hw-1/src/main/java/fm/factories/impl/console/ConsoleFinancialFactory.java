package fm.factories.impl.console;

import fm.domains.BankAccount;
import fm.domains.Category;
import fm.domains.Operation;
import fm.domains.types.Identifier;
import fm.exceptions.IdentifyNotFoundException;
import fm.factories.FinancialFactory;
import fm.params.console.ConsoleBankAccountParams;
import fm.params.console.ConsoleCategoryParams;
import fm.params.console.ConsoleOperationParams;
import fm.storages.AccountStorage;
import fm.storages.CategoryStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Фабрика, порождающая счета, операции и категории.
 * Считывает данные из консоли/потока ввода переданного в параметрах.
 */
@Component
@AllArgsConstructor
public class ConsoleFinancialFactory implements FinancialFactory<ConsoleBankAccountParams, ConsoleCategoryParams,
                                                                        ConsoleOperationParams> {
    private AccountStorage accountStorage;

    private CategoryStorage categoryStorage;

    /**
     * Возвращает новый банковский счёт.
     *
     * @param params параметры для создания
     * @return {@link BankAccount}
     */
    @Override
    public BankAccount createAccount(ConsoleBankAccountParams params, Identifier id) {
        return new BankAccount(id, params.getName(), params.getBalance());
    }

    /**
     * Возвращает новую категорию.
     *
     * @param params параметры для создания
     * @return {@link Category}
     */
    @Override
    public Category createCategory(ConsoleCategoryParams params, Identifier id) {
        return new Category(id, params.getName(), params.getType());
    }

    /**
     * Возвращает новую операцию.
     *
     * @param params параметры для создания
     * @return {@link Operation}
     */
    @Override
    public Operation createOperation(ConsoleOperationParams params, Identifier id) {
        var bankId = params.getBankAccountId();
        if (accountStorage.getAccount(bankId).isEmpty()) {
            throw new IdentifyNotFoundException("Bank account with ID  " + bankId.getId() + " not found");
        }
        var categoryId = params.getCategoryId();
        if (categoryStorage.getCategory(categoryId).isEmpty()) {
            throw new IdentifyNotFoundException("Category with ID  " + bankId.getId() + " not found");
        }
        return new Operation(
                id,
                bankId,
                categoryId,
                params.getType(),
                params.getAmount(),
                params.getName(),
                params.getDescription(),
                params.getDate()
        );
    }
}
