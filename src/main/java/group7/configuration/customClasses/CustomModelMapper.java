package group7.configuration.customClasses;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CustomModelMapper extends ModelMapper {
    public <D, E> List<E> mapList(List<D> sourceList, Class<E> destinationType) {
        return sourceList.stream()
                .map(source -> this.map(source, destinationType))
                .collect(Collectors.toList());
    }
}
