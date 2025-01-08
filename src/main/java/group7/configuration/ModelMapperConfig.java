package group7.configuration;

//import group7.dto.AddBeverageRequestDTO;
//import group7.dto.BeverageResponseDTO;
import group7.configuration.customClasses.CustomModelMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig extends ModelMapper {

    @Bean
    public CustomModelMapper customModelMapper() {
        CustomModelMapper modelMapper = new CustomModelMapper();

        // By default, ModelMapper maps public fields, this line enables mapping of private fields.
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        //by default modelmapper maps public field, so enabled private field.
        modelMapper.getConfiguration().setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        // Enables field matching based on similar names (e.g., 'userId' and 'userID').
        modelMapper.getConfiguration().setFieldMatchingEnabled(true);
        // Skips copying null values from source to destination objects.
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        // Commented out: This line attempts to set a null value handling strategy if we don't want to skip nulls, but for now the strategy is not specified.
        //modelMapper.setNullValueMappingStrategy(MatchingStrategies.);

        // Mapping with abstract inheritance
//        modelMapper.getConfiguration().getConverters().add(0, new BeverageResponseDTO.FromEntityConverter(modelMapper));
//        modelMapper.getConfiguration().getConverters().add(0, new BeverageResponseDTO.ToEntityConverter(modelMapper));
//        modelMapper.getConfiguration().getConverters().add(0, new AddBeverageRequestDTO.FromEntityConverter(modelMapper));
//        modelMapper.getConfiguration().getConverters().add(0, new AddBeverageRequestDTO.ToEntityConverter(modelMapper));

        return modelMapper;
    }
}
