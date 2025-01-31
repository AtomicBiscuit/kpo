package studying.params;

/**
 Пустой класс для двигателей без параметров
 <p>
 Не позволяет создавать новые объекты кроме DEFAULT
 */
public record EmptyEngineParams() {

    /**
     Единственный экземпляр класса
     */
    public static final EmptyEngineParams DEFAULT = new EmptyEngineParams();
}
