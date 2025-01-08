//package group7.component;
//
//import group7.dto.BeverageResponseDto;
//import jakarta.validation.constraints.NotEmpty;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.annotation.SessionScope;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Getter
//@Data
//@Component
//@SessionScope
//@NoArgsConstructor
//@AllArgsConstructor
//public class Basket {
//
//    @NotEmpty
//    private Map<BeverageResponseDto, Integer> beverages = new HashMap<>();
//
//    public void addBeverage(BeverageResponseDto beverage) {
//        this.beverages.put(beverage, this.beverages.getOrDefault(beverage, 0) + 1);
//    }
//
//    public void removeBeverage(BeverageResponseDto beverage) {
//        this.beverages.remove(beverage);
//    }
//
//    public void updateQuantity(BeverageResponseDto beverage, int quantity) {
//        if (quantity <= 0) {
//            this.beverages.remove(beverage);
//        } else {
//            this.beverages.put(beverage, quantity);
//        }
//    }
//
//    public void clearBasket() {
//        this.beverages.clear();
//    }
//
//    public Map<BeverageResponseDto, Integer> getBeverages() {
//        return this.beverages;
//    }
//
//    public double getTotalPrice() {
//        return beverages.entrySet().stream()
//                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
//                .sum();
//    }
//
//    public double getItemTotalPrice(BeverageResponseDto beverage) {
//        return beverage.getPrice() * beverages.get(beverage);
//    }
//}
////import group7.dto.BeverageResponseDto;
////import group7.entity.Beverage;
////import jakarta.validation.constraints.NotEmpty;
////import lombok.AllArgsConstructor;
////import lombok.Data;
////import lombok.Getter;
////import lombok.NoArgsConstructor;
////import org.springframework.stereotype.Component;
////import org.springframework.web.context.annotation.SessionScope;
////
////import java.util.ArrayList;
////import java.util.List;
//
////@Getter
////@Data
////@Component
////@SessionScope
////@NoArgsConstructor
////@AllArgsConstructor
////public class Basket {
////
////    @NotEmpty
////    private List<BeverageResponseDto> beverages = new ArrayList<>();
////
////    public void addBeverage(BeverageResponseDto beverage)  {
////        this.beverages.add(beverage);
////    }
////
////    // Clear the basket, replace clearBeverage with it.
////    public void clearBasket() {
////        this.beverages.clear();
////    }
////
////    // Remove beverage from basket
////    public void removeBeverage(Beverage beverage) {
////        this.beverages.remove(beverage);
////    }
////
////    // Get the list of beverages in the basket
////    public List<BeverageResponseDto> getBeverages() {
////        return this.beverages;
////    }
////
////    // Helper method to get total price
////    public double getTotalPrice() {
////        return beverages.stream().mapToDouble(BeverageResponseDto::getPrice).sum();
////    }
////}

package group7.component;

import group7.dto.BasketItemDto;
import group7.dto.BeverageResponseDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Data
@Component
@SessionScope
@NoArgsConstructor
@AllArgsConstructor
public class Basket {

    @NotEmpty
    private List<BasketItemDto> items = new ArrayList<>();

    public void addBeverage(BeverageResponseDto beverage) {
        Optional<BasketItemDto> existingItem = items.stream()
                .filter(item -> item.getBeverage().getId().equals(beverage.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + 1);
        } else {
            items.add(new BasketItemDto(beverage, 1));
        }
    }

    public void removeBeverage(BeverageResponseDto beverage) {
        items.removeIf(item -> item.getBeverage().getId().equals(beverage.getId()));
    }

    public void updateQuantity(BeverageResponseDto beverage, int quantity) {
        items.stream()
                .filter(item -> item.getBeverage().getId().equals(beverage.getId()))
                .findFirst()
                .ifPresent(item -> {
                    if (quantity <= 0) {
                        items.remove(item);
                    } else {
                        item.setQuantity(quantity);
                    }
                });
    }

    public void clearBasket() {
        items.clear();
    }

    public List<BasketItemDto> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return items.stream()
                .mapToDouble(item -> item.getBeverage().getPrice() * item.getQuantity())
                .sum();
    }

    public double getItemTotalPrice(BeverageResponseDto beverage) {
        return items.stream()
                .filter(item -> item.getBeverage().getId().equals(beverage.getId()))
                .mapToDouble(item -> item.getBeverage().getPrice() * item.getQuantity())
                .sum();
    }
}