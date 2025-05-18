package zoo.domain.schedule;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zoo.domain.animals.Animal;

/**
 * Класс-представление для расписания кормления животных.
 */
@Entity
@Table(name = "feeding_schedule")
@Getter
@Setter
@NoArgsConstructor
public class FeedingSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    Integer id;

    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false, updatable = true)
    Animal animal;

    LocalTime schedule;

    String foodName;

    /**
     * Конструктор для класса FeedingSchedule.
     */
    public FeedingSchedule(Animal animal, LocalTime schedule, String foodName) {
        this.animal = animal;
        this.schedule = schedule;
        this.foodName = foodName;
    }
}
