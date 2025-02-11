package hse.kpo.factories;

import hse.kpo.domains.Catamaran;
import hse.kpo.domains.HandEngine;
import hse.kpo.interfaces.CatamaranFactory;
import hse.kpo.params.EmptyEngineParams;
import org.springframework.stereotype.Component;

/**
 * Класс для порождения катамаранов с ручным двигателем.
 */
@Component
public class HandCatamaranFactory implements CatamaranFactory<EmptyEngineParams> {

    /**
     * Создаёт катамаран с ручным двигателем.
     *
     * @param catamaranParams параметры двигателя
     * @param catamaranNumber уникальный идентификатор катамарана
     * @return новый катамаран
     */
    @Override
    public Catamaran createCatamaran(EmptyEngineParams catamaranParams, int catamaranNumber) {
        var engine = new HandEngine();

        return new Catamaran(catamaranNumber, engine);
    }
}
