package group7.component;

import group7.dto.BeverageResponseDTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Getter
@Data
@Component
@SessionScope
@NoArgsConstructor
@AllArgsConstructor
public class Basket {

    @NotEmpty
    private List<BeverageResponseDTO> beverages = new ArrayList<>();

    public void addBeverage(BeverageResponseDTO beverage)  {
        this.beverages.add(beverage);
    }

    public void clearBeverages(){
        this.beverages.clear();
    }



}
