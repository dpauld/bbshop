package group7.dto;

import group7.entity.Beverage;
import group7.entity.Bottle;
import group7.entity.Crate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.ConditionalConverter;
import org.modelmapper.spi.MappingContext;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddBeverageRequestDTO {

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Name can only contain letters and digits")
    private String name;
    @Positive
    private double price;

    public static class FromEntityConverter implements ConditionalConverter<Beverage, AddBeverageRequestDTO> {
        private final ModelMapper modelMapper;

        public FromEntityConverter(ModelMapper modelMapper) {
            this.modelMapper = modelMapper;
        }

        @Override
        public MatchResult match(Class<?> sourceType, Class<?> destinationType) {
            if (destinationType != AddBeverageRequestDTO.class)
                return MatchResult.NONE;
            if (!Beverage.class.isAssignableFrom(sourceType))
                return MatchResult.NONE;
            return MatchResult.FULL;
        }

        @Override
        public AddBeverageRequestDTO convert(MappingContext<Beverage, AddBeverageRequestDTO> context) {
            Beverage src = context.getSource();
            if (src instanceof Bottle bottle)
                return modelMapper.map(bottle, AddBottleRequestDTO.class);
            if (src instanceof Crate crate)
                return modelMapper.map(crate, AddCrateRequestDTO.class);
            throw new RuntimeException("Unknown beverage subclass: " + src.getClass());
        }
    }

    public static class ToEntityConverter implements ConditionalConverter<AddBeverageRequestDTO, Beverage> {
        private final ModelMapper modelMapper;

        public ToEntityConverter(ModelMapper modelMapper) {
            this.modelMapper = modelMapper;
        }

        @Override
        public MatchResult match(Class<?> sourceType, Class<?> destinationType) {
            if (destinationType != Beverage.class)
                return MatchResult.NONE;
            if (!AddBeverageRequestDTO.class.isAssignableFrom(sourceType))
                return MatchResult.NONE;
            return MatchResult.FULL;
        }

        @Override
        public Beverage convert(MappingContext<AddBeverageRequestDTO, Beverage> context) {
            AddBeverageRequestDTO src = context.getSource();
            if (src instanceof AddBottleRequestDTO bottle)
                return modelMapper.map(bottle, Bottle.class);
            if (src instanceof AddCrateRequestDTO crate)
                return modelMapper.map(crate, Crate.class);
            throw new RuntimeException("Unknown beverage DTO subclass: " + src.getClass());
        }
    }
}
