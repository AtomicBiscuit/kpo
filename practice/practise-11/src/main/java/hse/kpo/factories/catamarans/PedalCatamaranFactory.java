package hse.kpo.factories.catamarans;


import hse.kpo.domains.Catamaran;
import hse.kpo.domains.PedalEngine;
import hse.kpo.interfaces.catamarans.CatamaranFactory;
import hse.kpo.params.PedalEngineParams;
import org.springframework.stereotype.Component;

/**
 * Фабрика для создания катамаранов с {@link PedalEngine} типом двигателя.
 */
@Component
public class PedalCatamaranFactory implements CatamaranFactory<PedalEngineParams> {
    @Override
    public Catamaran create(PedalEngineParams catamaranParams) {
        var engine = new PedalEngine(catamaranParams.pedalSize()); // создаем двигатель на основе переданных параметров

        return new Catamaran(engine); // создаем катамаран с педальным приводом
    }
}
