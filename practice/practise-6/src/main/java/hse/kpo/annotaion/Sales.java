package hse.kpo.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для добавления информации в отчёт продаж.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Sales {
    /**
     * Название выполняемой операции.
     *
     * @return название
     */
    String value() default "";
}