package app.milanpizza.menucatalog.mapper.config;

import org.mapstruct.MappingTarget;

public interface AbstractEntityMapper<C, U, SR, DR, E> {
    E toEntity(C createDto);
    void updateEntity(U updateDto, @MappingTarget E entity);
    SR toSummaryResponse(E entity);
    DR toDetailedResponse(E entity);
}