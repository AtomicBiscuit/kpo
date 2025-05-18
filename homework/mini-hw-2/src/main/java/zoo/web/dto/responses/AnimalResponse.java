package zoo.web.dto.responses;

import java.time.LocalDate;
import lombok.Builder;
import zoo.domain.animals.Animal;

/**
 * DTO для записи животных.
 */
@Builder
public record AnimalResponse(
        Integer id,

        Integer encloseId,

        String type,

        String name,

        LocalDate birthday,

        String sex,

        String favoriteFood,

        Boolean isHealthy
) {
    /**
     * Создаёт DTO из объекта.
     *
     * @param animal животное для сериализации.
     * @return {@link AnimalResponse}
     */
    public static AnimalResponse from(Animal animal) {
        var builder = AnimalResponse.builder();
        builder.id(animal.getId());
        if (animal.getEnclosure() != null) {
            builder.encloseId(animal.getEnclosure().getId());
        }
        builder.type(animal.getType());
        builder.name(animal.getName());
        builder.birthday(animal.getBirthday());
        builder.sex(animal.getSex().getName());
        builder.favoriteFood(animal.getFavoriteFood());
        builder.isHealthy(animal.getHealthy());
        return builder.build();
    }
}
