package zoo.web.dto.responses;

import lombok.Builder;
import zoo.domain.enclosures.Enclosure;

/**
 * DTO для записи вольера.
 */
@Builder
public record EnclosureResponse(
        Integer id,

        String type,

        Integer size,

        Integer capacity,

        Integer animalsCount
) {
    /**
     * Создаёт DTO из объекта.
     *
     * @param enclosure вольер для сериализации.
     * @return {@link EnclosureResponse}
     */
    public static EnclosureResponse from(Enclosure enclosure) {
        var builder = EnclosureResponse.builder();
        builder.id(enclosure.getId());
        builder.type(enclosure.getType());
        builder.size(enclosure.getSize());
        builder.capacity(enclosure.getCapacity());
        builder.animalsCount(enclosure.getAnimalsCount());
        return builder.build();
    }
}
