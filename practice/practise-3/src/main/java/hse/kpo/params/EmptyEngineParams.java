package hse.kpo.params;

/**
 * Пустой класс для двигателей без параметров.
 * Не позволяет создавать новые объекты кроме DEFAULT
 */
public record EmptyEngineParams() {

    /**
     * Единственный экземпляр класса.
     */
    public static final EmptyEngineParams DEFAULT = new EmptyEngineParams();
}
