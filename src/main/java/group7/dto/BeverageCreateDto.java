package group7.dto;

import group7.entity.Bottle;
import group7.validation.group.BeverageGroup;
import group7.validation.group.BottleGroup;
import group7.validation.group.CrateGroup;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class BeverageCreateDto {
    private Long id;

    @NotNull(message = "name cannot be null", groups = {BeverageGroup.class})
    @NotEmpty(message = "name cannot be empty", groups = {BeverageGroup.class})
    @Pattern(regexp = "^[a-zA-Z0-9 ÄäÜüÖöß_-]+$", message = "name can only contain letters and digits", groups = {BeverageGroup.class})
    private String name;

    @Positive(message = "price must be greater than 0", groups = {BeverageGroup.class})
    private double price;

    @PositiveOrZero(message = "number of item in stock must not be negative.", groups = {BeverageGroup.class})
    private int inStock;

    @URL(message = "picture must be a valid url.", groups = {BeverageGroup.class})
    private String picture;

    @Positive(message = "volume must be greater than 0", groups = {BottleGroup.class})
    private double volume;

    private boolean isAlcoholic;

    @PositiveOrZero(message = "volume percent must be positive or zero", groups = {BottleGroup.class})
    private double volumePercent;

    @NotNull(message = "supplier name cannot be null", groups = {BottleGroup.class})
    @NotEmpty(message = "supplier name cannot be empty", groups = {BottleGroup.class})
    @NotBlank(message = "supplier is required.", groups = {BottleGroup.class})
    private String supplier;

    //additional fields for crate
    @Min(value = 1, message = "must be 1 at minimum.", groups = {CrateGroup.class})
    @Positive(message = "number of bottles must be greater than 0", groups = {CrateGroup.class})
    private int noOfBottles;

    private Long cratesBottleId;
    private Bottle bottle;

    //useful extra variable
    private String type;
}

//@Data
//public class BeverageCreateDto {
//    private Integer id;
//
//    @NotNull(message = "name cannot be null")
//    @NotEmpty(message = "name cannot be empty")
//    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "name can only contain letters and digits")
//    protected String name;
//
//    @Positive(message = "price must be greater than 0")
//    @Column(name = "price",nullable = false)
//    protected double price;
//
//    @PositiveOrZero(message = "number of item in stock must not be negative.")
//    private int inStock;
//
//    @URL(message = "picture must be a valid url.")
//    private String picture;
//}