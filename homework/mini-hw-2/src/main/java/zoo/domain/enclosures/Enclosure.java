package zoo.domain.enclosures;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zoo.domain.animals.Animal;
import zoo.domain.exceptions.ZooException;

/**
 * Класс, представляющий животных.
 */
@Entity
@Table(name = "enclosure")
@Getter
@Setter
@NoArgsConstructor
public class Enclosure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enclosure_id")
    Integer id;

    @OneToMany(mappedBy = "enclosure", cascade = CascadeType.DETACH, orphanRemoval = false)
    List<Animal> animals;

    String type;

    int size;

    int capacity;

    int animalsCount;

    /**
     * Конструктор для класса Enclosure.
     */
    public Enclosure(String type, Integer size, Integer capacity, Integer animalsCount) {
        this.type = type;
        this.size = size;
        this.capacity = capacity;
        this.animalsCount = animalsCount;
    }

    public Enclosure addAnimal() {
        animalsCount++;
        return this;
    }

    public Enclosure removeAnimal() {
        animalsCount--;
        return this;
    }

    /**
     * Устанавливает значение вместимости для вольера.
     *
     * @param capacity новая вместимость
     */
    public void setCapacity(Integer capacity) {
        if (animalsCount > capacity) {
            throw new ZooException("Animals count exceeds capacity");
        }
        this.capacity = capacity;
    }
}
