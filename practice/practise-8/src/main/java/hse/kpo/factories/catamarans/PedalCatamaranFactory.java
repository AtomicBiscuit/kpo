package hse.kpo.factories.catamarans;


import hse.kpo.domains.catamarans.Catamaran;
import hse.kpo.domains.engines.PedalEngine;
import hse.kpo.interfaces.catamarans.CatamaranFactory;
import hse.kpo.params.PedalEngineParams;
import org.springframework.stereotype.Component;

/**
 * Фабрика для создания катамаранов с {@link PedalEngine} типом двигателя.
 */
@Component
public class PedalCatamaranFactory implements CatamaranFactory<PedalEngineParams> {
    @Override
    public Catamaran create(PedalEngineParams catamaranParams, int catamaranNumber) {
        var engine = new PedalEngine(catamaranParams.pedalSize()); // создаем двигатель на основе переданных параметров

        return new Catamaran(catamaranNumber, engine); // создаем катамаран с педальным приводом
    }
}
