package zoo.domain.animals;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zoo.domain.enclosures.Enclosure;
import zoo.domain.schedule.FeedingSchedule;

/**
 * Класс, представляющий животных.
 */
@Entity
@Table(name = "animal")
@Getter
@Setter
@NoArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id")
    int id;

    String type;

    String name;

    Date birthday;

    @Enumerated(EnumType.STRING)
    AnimalSex sex;

    String favoriteFood;

    Boolean healthy;

    @JsonSerialize(using = DateSerializer.class)
    @ManyToOne
    @JoinColumn(name = "enclosure_id", nullable = true, updatable = true)
    Enclosure enclosure;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
    List<FeedingSchedule> feedingSchedules;


    /**
     * Конструктор для класса Animal.
     */
    public Animal(String type, String name, Date birthday, AnimalSex sex, String favoriteFood, Boolean healthy) {
        this.type = type;
        this.name = name;
        this.birthday = birthday;
        this.sex = sex;
        this.favoriteFood = favoriteFood;
        this.healthy = healthy;
    }

    public void treat() {
        healthy = true;
    }

    /**
     * Перемещает животное в новый вольер.
     *
     * @param newEnclosure новый вольер
     */
    public Animal moveTo(Enclosure newEnclosure) {
        enclosure = newEnclosure;
        return this;
    }
}
