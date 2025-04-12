package fm.visitors;

import fm.domains.BankAccount;
import fm.domains.Category;
import fm.domains.Operation;

/**
 * Интерфейс посетителя.
 */
public interface Visitor {
    void visit(BankAccount account);

    void visit(Category category);

    void visit(Operation operation);
}
