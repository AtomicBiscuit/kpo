package hse.kpo.interfaces;

import hse.kpo.domains.Car;
import hse.kpo.domains.Catamaran;

/**
 * Интерфейс для фабрик, порождающих катамараны.
 */
public interface CatamaranFactory<ParamsT> {

    /**
     * Создаёт катамаран.
     *
     * @param catamaranParams параметры двигателя
     * @param catamaranNumber       уникальный идентификатор катамарана
     * @return новый катамаран
     */
    Catamaran createCatamaran(ParamsT catamaranParams, int catamaranNumber);
}
