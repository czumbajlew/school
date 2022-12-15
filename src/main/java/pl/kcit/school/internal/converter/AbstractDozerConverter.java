package pl.kcit.school.internal.converter;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AbstractDozerConverter{

    private final Mapper mapper;

    protected AbstractDozerConverter() {
        this.mapper = DozerBeanMapperBuilder
                .create()
                .withMappingBuilder(configure())
                .build();
    }

    protected abstract BeanMappingBuilder configure();

    public <T, R> T convert(R source, Class<T> clazz) {
        if (source == null) {
            return null;
        }

        return mapper.map(source, clazz);
    }

    public <T, R> Collection<T> convertAll(Collection<R> source, Class<T> clazz, Supplier<Collection<T>> factory) {
        if (source == null) {
            return factory.get();
        }

        return source.stream()
                .map(e -> convert(e, clazz))
                .collect(Collectors.toCollection(factory));
    }
}
