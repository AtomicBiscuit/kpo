package hse.kpo.factories;

import hse.kpo.domains.Catamaran;
import hse.kpo.domains.PedalEngine;
import hse.kpo.interfaces.CatamaranFactory;
import hse.kpo.params.PedalEngineParams;
import org.springframework.stereotype.Component;

/**
 * Класс для порождения катамаранов с ручным двигателем.
 */
@Component
public class PedalCatamaranFactory implements CatamaranFactory<PedalEngineParams> {

    /**
     * Создаёт катамаран с ручным двигателем.
     *
     * @param catamaranParams параметры двигателя
     * @param catamaranNumber уникальный идентификатор катамарана
     * @return новый катамаран
     */
    @Override
    public Catamaran createCatamaran(PedalEngineParams catamaranParams, int catamaranNumber) {
        var engine = new PedalEngine(catamaranParams.pedalSize());

        return new Catamaran(catamaranNumber, engine);
    }
}
