package fm.filter;

import fm.domains.Operation;
import fm.domains.types.Identifier;
import fm.enums.OperationType;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * Класс для фильтрации операции по различным признакам.
 */
public class OperationFilter {
    Stream<Operation> data;

    private OperationFilter(List<Operation> operations) {
        this.data = operations.stream();
    }

    /**
     * Создаёт фильтр.
     *
     * @param operations список из операций для фильтрации
     * @return {@link OperationFilter}
     */
    public static OperationFilter builder(List<Operation> operations) {
        return new OperationFilter(operations);
    }

    /**
     * Создаёт список из отфильтрованных операций.
     *
     * @return список из {@link Operation}
     */
    public List<Operation> build() {
        return data.toList();
    }

    /**
     * Отбирает операции с выбранным типом из списка.
     *
     * @param operations список операции для фильтрации
     * @param type       тип операции
     */
    public OperationFilter filterByOperation(List<Operation> operations, OperationType type) {
        data = data.filter(operation -> operation.getType() == type);
        return this;
    }

    /**
     * Отбирает операции с выбранной категорией из списка.
     *
     * @param categoryId Идентификатор категории
     */
    public OperationFilter filterByCategory(Identifier categoryId) {
        data = data.filter(operation -> operation.getCategoryId().equals(categoryId));
        return this;
    }

    /**
     * Отбирает операции с выбранным счётом.
     *
     * @param bankAccountId Идентификатор счёта
     */
    public OperationFilter filterByAccount(Identifier bankAccountId) {
        data = data.filter(operation -> operation.getBankAccountId().equals(bankAccountId));
        return this;
    }

    /**
     * Отбирает операции в выбранном временном отрезке.
     *
     * @param from начало отрезка
     * @param to   конец отрезка
     */
    public OperationFilter filterByDate(Date from, Date to) {
        data = data.filter(operation -> !(from.after(operation.getDate()) || to.before(operation.getDate())));
        return this;
    }
}
