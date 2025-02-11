package hse.kpo.factories;

import hse.kpo.interfaces.AbstractFactory;
import hse.kpo.interfaces.CarFactory;
import hse.kpo.interfaces.CatamaranFactory;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import org.springframework.stereotype.Component;

/**
 * Класс для порождения транспортных средств с ручным двигателем.
 */
@Component
public class HandFactory implements AbstractFactory<EmptyEngineParams> {
    /**
     * Создаёт фабрику автомобилей с ручными двигателями.
     *
     * @return новая фабрика автомобилей
     */
    @Override
    public CarFactory<EmptyEngineParams> createCarFactory() {
        return new HandCarFactory();
    }

    /**
     * Создаёт фабрику катамаранов с ручными двигателями.
     *
     * @return новая фабрика катамаранов
     */
    @Override
    public CatamaranFactory<EmptyEngineParams> createCatamaranFactory() {
        return new HandCatamaranFactory();
    }
}
