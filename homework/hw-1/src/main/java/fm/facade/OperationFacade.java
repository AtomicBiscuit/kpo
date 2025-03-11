package fm.facade;

import static fm.formater.PrettyText.format;

import fm.domains.types.Identifier;
import fm.factories.OperationFactory;
import fm.formater.Format;
import fm.helpers.ConsoleHelper;
import fm.params.OperationParams;
import fm.storages.OperationStorage;
import java.io.PrintStream;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OperationFacade {
    BankAccountFacade banks;

    OperationStorage storage;

    OperationFactory factory;

    @Setter
    OperationParams params;

    ConsoleHelper helper;

    int num = 1;

    @Autowired
    public OperationFacade(BankAccountFacade banks, OperationStorage storage, OperationFactory factory,
                           OperationParams params, ConsoleHelper helper) {
        this.banks = banks;
        this.storage = storage;
        this.factory = factory;
        this.params = params;
        this.helper = helper;
    }

    public void printAll(PrintStream writer) {
        writer.println(format("Operations: ", Format.CYAN));
        storage.getAllOperations().forEach(operation -> writer.println("  " + operation));
        writer.println(format("End", Format.CYAN));
    }

    public int createOperation() {
        try {
            var operation = factory.createOperation(params, new Identifier(num++));
            storage.addOperation(operation);
            banks.addSumToAccount(operation.getBankAccountId(), operation.calculateSignedAmount());
        } catch (Exception e) {
            num--;
            helper.getOutput().println(format(e.getMessage(), Format.RED, Format.BOLD, Format.UNDERLINE));
            return 0;
        }
        return num - 1;
    }

    public void removeOperation(Identifier id) {
        storage.removeOperation(id);
    }

    public boolean changeOperation(Identifier id) {
        var rawOperation = storage.getOperation(id);
        if (rawOperation.isEmpty()) {
            helper.getOutput()
                  .println(format("Operation with id " + id + " not found", Format.RED, Format.BOLD, Format.UNDERLINE));
            return false;
        }
        helper.getOutput().println("Now enter new data");

        var operation = rawOperation.get();
        int oldAmount = operation.calculateSignedAmount();

        operation.setAmount(params.getAmount());
        operation.setName(params.getName());
        operation.setDescription(params.getDescription());
        operation.setDate(params.getDate());

        banks.addSumToAccount(operation.getBankAccountId(), operation.calculateSignedAmount() - oldAmount);
        return true;
    }
}
