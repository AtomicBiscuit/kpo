package fm.visitors;

import fm.domains.BankAccount;
import fm.domains.Category;
import fm.domains.Operation;

/**
 * Интерфейс посетителя.
 */
public interface Visitor {
    public void visit(BankAccount account);

    public void visit(Category category);

    public void visit(Operation operation);
}
