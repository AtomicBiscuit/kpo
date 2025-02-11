package hse.kpo.factories;

import hse.kpo.domains.Catamaran;
import hse.kpo.domains.LevitateEngine;
import hse.kpo.interfaces.CatamaranFactory;
import hse.kpo.params.EmptyEngineParams;
import org.springframework.stereotype.Component;

/**
 * Класс для порождения летающих катамаранов.
 */
@Component
public class LevitateCatamaranFactory implements CatamaranFactory<EmptyEngineParams> {

    /**
     * Создаёт катамаран с левитирующим двигателем.
     *
     * @param catamaranParams параметры двигателя
     * @param catamaranNumber уникальный идентификатор катамарана
     * @return новый катамаран
     */
    @Override
    public Catamaran createCatamaran(EmptyEngineParams catamaranParams, int catamaranNumber) {
        var engine = new LevitateEngine();

        return new Catamaran(catamaranNumber, engine);
    }
}
