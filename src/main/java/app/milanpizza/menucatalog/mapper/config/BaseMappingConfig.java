package app.milanpizza.menucatalog.mapper.config;

import org.mapstruct.*;

@MapperConfig(
        componentModel = "spring",                                                              // Auto-wires mappers as Spring beans
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,             // Silently skips null fields during updates
        unmappedTargetPolicy = ReportingPolicy.IGNORE,                                          // No warnings for unmapped target fields
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG,       // Inherits mapping strategies from this config
        injectionStrategy = InjectionStrategy.CONSTRUCTOR                                       // For better testability
)
public interface BaseMappingConfig {

}