package pl.kcit.school.internal.converter;

public interface ObjectConverter<TARGET, SOURCE> {

    TARGET convertTo(SOURCE source);
    SOURCE convertFrom(TARGET source);

}
