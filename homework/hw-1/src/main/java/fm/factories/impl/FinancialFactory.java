package fm.factories.impl;

import fm.domains.BankAccount;
import fm.domains.Category;
import fm.domains.Operation;
import fm.domains.types.Identifier;
import fm.exceptions.IdentifyNotFoundException;
import fm.factories.BankAccountFactory;
import fm.factories.CategoryFactory;
import fm.factories.OperationFactory;
import fm.params.BankAccountParams;
import fm.params.CategoryParams;
import fm.params.OperationParams;
import fm.storages.AccountStorage;
import fm.storages.CategoryStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Фабрика, порождающая счета, операции и категории.
 */
@Component
@AllArgsConstructor
public class FinancialFactory implements BankAccountFactory, CategoryFactory, OperationFactory {
    private AccountStorage accountStorage;

    private CategoryStorage categoryStorage;

    /**
     * Возвращает новый банковский счёт.
     *
     * @return {@link BankAccount}
     */
    @Override
    public BankAccount createAccount(BankAccountParams accountParams, Identifier id) {
        return new BankAccount(id, accountParams.getName(), accountParams.getBalance());
    }

    /**
     * Возвращает новую категорию.
     *
     * @return {@link Category}
     */
    @Override
    public Category createCategory(CategoryParams categoryParams, Identifier id) {
        return new Category(id, categoryParams.getName(), categoryParams.getType());
    }

    /**
     * Возвращает новую операцию.
     *
     * @return {@link Operation}
     */
    @Override
    public Operation createOperation(OperationParams operationParams, Identifier id) {
        var bankId = operationParams.getBankAccountId();
        if (accountStorage.getAccount(bankId).isEmpty()) {
            throw new IdentifyNotFoundException("Bank account with ID " + bankId.getId() + " not found");
        }
        var categoryId = operationParams.getCategoryId();
        var category = categoryStorage.getCategory(categoryId);
        if (category.isEmpty()) {
            throw new IdentifyNotFoundException("Category with ID  " + bankId.getId() + " not found");
        }
        return new Operation(
                id,
                bankId,
                categoryId,
                category.get().getType(),
                operationParams.getAmount(),
                operationParams.getName(),
                operationParams.getDescription(),
                operationParams.getDate()
        );
    }
}
