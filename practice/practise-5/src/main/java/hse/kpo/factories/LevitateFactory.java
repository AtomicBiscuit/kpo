package hse.kpo.factories;

import hse.kpo.interfaces.AbstractFactory;
import hse.kpo.interfaces.CarFactory;
import hse.kpo.interfaces.CatamaranFactory;
import hse.kpo.params.EmptyEngineParams;
import org.springframework.stereotype.Component;

/**
 * Класс для порождения левитирующих транспортных средств.
 */
@Component
public class LevitateFactory implements AbstractFactory<EmptyEngineParams> {
    /**
     * Создаёт фабрику левитирующих автомобилей.
     *
     * @return новая фабрика автомобилей
     */
    @Override
    public CarFactory<EmptyEngineParams> createCarFactory() {
        return new LevitateCarFactory();
    }

    /**
     * Создаёт фабрику левитирующих катамаранов.
     *
     * @return новая фабрика катамаранов
     */
    @Override
    public CatamaranFactory<EmptyEngineParams> createCatamaranFactory() {
        return new LevitateCatamaranFactory();
    }
}
