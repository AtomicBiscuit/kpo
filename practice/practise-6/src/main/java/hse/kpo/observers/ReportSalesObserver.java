package hse.kpo.observers;

import hse.kpo.builders.ReportBuilder;
import hse.kpo.domains.Customer;
import hse.kpo.domains.Report;
import hse.kpo.enums.ProductionTypes;
import hse.kpo.interfaces.SalesObserver;
import hse.kpo.storages.CustomerStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Реализация класса {@link SalesObserver} для записи продаж.
 */
@Component
@RequiredArgsConstructor
public class ReportSalesObserver implements SalesObserver {
    private final CustomerStorage customerStorage;

    private final ReportBuilder reportBuilder = new ReportBuilder();

    /**
     * Составляет отчёт по результатам наблюдений.
     *
     * @return готовый отчёт
     */
    public Report buildReport() {
        return reportBuilder.build();
    }

    /**
     * Обрабатывает текущее состояние покупателей.
     */
    public void checkCustomers() {
        reportBuilder.addCustomers(customerStorage.getCustomers());
    }

    /**
     * Обрабатывает продажу товара.
     *
     * @param customer    покупатель
     * @param productType тип товара
     * @param vin         идентификатор товара
     */
    @Override
    public void onSale(Customer customer, ProductionTypes productType, int vin) {
        String message = String.format(
                "Продажа: %s VIN-%d клиенту %s (Сила рук: %d, Сила ног: %d, IQ: %d)",
                productType, vin, customer.getName(),
                customer.getHandPower(), customer.getLegPower(), customer.getIq()
        );
        reportBuilder.addOperation(message);
    }
}