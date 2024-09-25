package com.mayxstudios.converters;

public interface EntityConverter<Entity, DTO> {
    DTO toDTO(Entity entity);

    Entity toEntity(DTO dto);
}
