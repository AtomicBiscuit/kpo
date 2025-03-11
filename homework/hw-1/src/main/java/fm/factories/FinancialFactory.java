package fm.factories;

/**
 * Интерфейс фабрики, порождающей все доменные классы.
 *
 * @param <T1> параметры для создания счёта
 * @param <T2> параметры для создания категории
 * @param <T3> параметры для создания операции
 */
public interface FinancialFactory<T1, T2, T3> extends BankAccountFactory<T1>, CategoryFactory<T2>,
                                                              OperationFactory<T3> {

}
