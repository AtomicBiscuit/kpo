package hse.kpo.interfaces.catamarans;

import hse.kpo.domains.Catamaran;

/**
 * Интерфейс для определения методов фабрик.
 *
 * @param <ProductionParamsT> параметры для фабрик
 */
public interface CatamaranFactory<ProductionParamsT> {
    /**
     * Метод создания катамаранов.
     *
     * @param catamaranParams параметры для создания
     * @param catamaranNumber номер
     * @return {@link Catamaran}
     */
    Catamaran create(ProductionParamsT catamaranParams, int catamaranNumber);
}
