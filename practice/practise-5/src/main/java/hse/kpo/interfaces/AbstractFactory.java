package hse.kpo.interfaces;

/**
 * Интерфейс для фабрик, порождающих фабрики транспортных средств.
 *
 * @param <ParamsT> тип параметров двигателя
 */
public interface AbstractFactory<ParamsT> {
    /**
     * Создаёт фабрику автомобилей.
     *
     * @return фабрика автомобилей
     */
    public CarFactory<ParamsT> createCarFactory();

    /**
     * Создаёт фабрику катамаранов.
     *
     * @return фабрика катамаранов
     */
    public CatamaranFactory<ParamsT> createCatamaranFactory();
}
