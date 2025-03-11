package fm.storages;

import fm.domains.Operation;
import fm.domains.types.Identifier;
import fm.visitors.Visitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Хранилище информации об операциях.
 */
@Component
public class OperationStorage {
    private final List<Operation> operations = new ArrayList<>();

    /**
     * Добавляет {@link Operation} в хранилище.
     *
     * @param operation операция для добавления
     */
    public void addOperation(Operation operation) {
        operations.add(operation);
    }

    /**
     * Возвращает операцию по {@link Identifier}.
     *
     * @param id идентификатор целевой операции
     * @return {@link Operation}, если такая операция есть в хранилище
     */
    public Optional<Operation> getOperation(Identifier id) {
        return operations.stream().filter(operation -> operation.getId().equals(id)).findFirst();
    }

    /**
     * Удаляет операцию по {@link Identifier}.
     *
     * @param id идентификатор целевой операции
     */
    public void removeOperation(Identifier id) {
        operations.removeIf(operation -> operation.getId().equals(id));
    }

    /**
     * Возвращает список всех хранимых операций.
     *
     * @return Список из {@link Operation}
     */
    public List<Operation> getAllOperations() {
        return operations;
    }

    /**
     * Принимает посетителя для всех хранимых объектов
     *
     * @param visitor посетитель
     */
    public void accept(Visitor visitor) {
        operations.forEach(visitor::visit);
    }
}
