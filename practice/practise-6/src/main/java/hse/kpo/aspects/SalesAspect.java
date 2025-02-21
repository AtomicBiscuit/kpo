package hse.kpo.aspects;

import hse.kpo.annotaion.Sales;
import hse.kpo.interfaces.SalesObserver;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Аспект для автоматического сбора информации о покупателях после сделок.
 */
@Component
@Aspect
@RequiredArgsConstructor
public class SalesAspect {
    private final SalesObserver salesObserver;

    /**
     * Обрабатывает аннотацию {@link Sales}.
     * Метод с такой аннотацией будет добавлять информацию о покупателях в отчёт после исполнения.
     *
     * @param pjp   представление обрабатываемого метода
     * @param sales представление аннотации
     * @return значение выполненного метода
     */
    @Around("@annotation(sales)")
    public Object sales(ProceedingJoinPoint pjp, Sales sales) throws Throwable {
        salesObserver.checkCustomers();

        String operationName = sales.value().isEmpty() ? pjp.getSignature().toShortString() : sales.value();
        try {
            Object result = pjp.proceed();
            salesObserver.checkCustomers();
            return result;
        } catch (Throwable e) {
            throw e;
        }
    }
}