package group7.dto;

import group7.entity.Bottle;
import group7.validation.group.BeverageGroup;
import group7.validation.group.BottleGroup;
import group7.validation.group.CrateGroup;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class BeverageResponseDto {
    private Long id;

    private String name;

    private double price;

    private int inStock;

    private String picture;

    private String type;

    private double volume;

    private boolean isAlcoholic;

    private double volumePercent;

    private String supplier;

    private int noOfBottles;

    //private Bottle bottle;
}
