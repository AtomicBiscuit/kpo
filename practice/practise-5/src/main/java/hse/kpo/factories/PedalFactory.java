package hse.kpo.factories;

import hse.kpo.domains.Car;
import hse.kpo.domains.HandEngine;
import hse.kpo.interfaces.AbstractFactory;
import hse.kpo.interfaces.CarFactory;
import hse.kpo.interfaces.CatamaranFactory;
import hse.kpo.params.EmptyEngineParams;
import hse.kpo.params.PedalEngineParams;
import org.springframework.stereotype.Component;

/**
 * Класс для порождения транспортных средств с педальным двигателем.
 */
@Component
public class PedalFactory implements AbstractFactory<PedalEngineParams> {
    /**
     * Создаёт фабрику автомобилей с педальными двигателями.
     *
     * @return новая фабрика автомобилей
     */
    @Override
    public CarFactory<PedalEngineParams> createCarFactory() {
        return new PedalCarFactory();
    }

    /**
     * Создаёт фабрику катамаранов с педальными двигателями.
     *
     * @return новая фабрика катамаранов
     */
    @Override
    public CatamaranFactory<PedalEngineParams> createCatamaranFactory() {
        return new PedalCatamaranFactory();
    }
}
