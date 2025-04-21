package zoo.domain.exceptions;

/**
 * Базовый класс для исключений зоопарка.
 */
public class ZooException extends RuntimeException {
    public ZooException(String message) {
        super(message);
    }
}
