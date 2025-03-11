package fm.facade;

import fm.domains.types.Identifier;
import fm.exceptions.IdentifyNotFoundException;
import fm.factories.OperationFactory;
import fm.params.OperationParams;
import fm.storages.OperationStorage;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OperationFacade {
    BankAccountFacade banks;

    OperationStorage storage;

    OperationFactory factory;

    @Setter
    OperationParams params;

    int num = 1;

    public int createOperation() {
        var operation = factory.createOperation(params, new Identifier(num++));
        storage.addOperation(operation);
        banks.addSumToAccount(operation.getBankAccountId(), operation.calculateSignedAmount());
        return num - 1;
    }

    public void removeOperation(Identifier id) {
        storage.removeOperation(id);
    }

    public void changeOperation(Identifier id) {
        var rawOperation = storage.getOperation(id);
        if (rawOperation.isEmpty()) {
            throw new IdentifyNotFoundException("Account with id " + id + " not found");
        }
        var operation = rawOperation.get();

        int oldAmount = operation.calculateSignedAmount();

        operation.setAmount(params.getAmount());
        operation.setName(params.getName());
        operation.setDescription(params.getDescription());
        operation.setDate(params.getDate());

        banks.addSumToAccount(operation.getBankAccountId(), operation.calculateSignedAmount() - oldAmount);
    }
}
