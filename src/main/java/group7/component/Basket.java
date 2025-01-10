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