package hse.kpo.interfaces;

import hse.kpo.domains.Customer;
import hse.kpo.domains.Report;
import hse.kpo.enums.ProductionTypes;

/**
 * Интерфейс наблюдателя за продажами.
 */
public interface SalesObserver {

    /**
     * Составляет отчёт по результатам наблюдений.
     *
     * @return готовый отчёт
     */
    Report buildReport();

    /**
     * Обрабатывает текущее состояние покупателей.
     */
    void checkCustomers();

    /**
     * Обрабатывает продажу товара.
     *
     * @param customer    покупатель
     * @param productType тип товара
     * @param vin         идентификатор товара
     */
    void onSale(Customer customer, ProductionTypes productType, int vin);
}
