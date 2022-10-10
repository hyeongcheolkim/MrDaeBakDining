package NaNSsoGong.MrDaeBakDining.domain.dinner.service;

import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.Dinner;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.DinnerFood;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.ExcludedStyle;
import NaNSsoGong.MrDaeBakDining.domain.dinner.dto.DinnerDto;
import NaNSsoGong.MrDaeBakDining.domain.dinner.repository.DinnerRepository;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.domain.style.domain.Style;
import NaNSsoGong.MrDaeBakDining.domain.style.repository.StyleRepository;
import NaNSsoGong.MrDaeBakDining.exception.exception.NoExistInstanceException;
import NaNSsoGong.MrDaeBakDining.exception.exception.NoOderableInstanceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
@RequiredArgsConstructor
public class DinnerService {
    private final DinnerRepository dinnerRepository;
    private final StyleRepository styleRepository;
    private final FoodRepository foodRepository;

    public Dinner makeDinner(DinnerDto dinnerDto) {
        Dinner dinner = new Dinner();

        dinnerRepository.save(dinner);
        dinner.setName(dinnerDto.getName());
        dinner.setDescription(dinnerDto.getDescription());

        List<DinnerFood> dinnerFoodList = makeDinnerFoodList(dinner, dinnerDto);
        for (var dinnerFood : dinnerFoodList)
            dinner.getDinnerFoodList().add(dinnerFood);

        List<ExcludedStyle> excludedStyleList = makeExcludedStyleList(dinner, dinnerDto);
        for (var excludedStyle : excludedStyleList)
            dinner.getExcludedStyleList().add(excludedStyle);

        return dinner;
    }

    private List<DinnerFood> makeDinnerFoodList(Dinner dinner, DinnerDto dinnerDto) {
        var ret = new ArrayList<DinnerFood>();
        Map<Long, Integer> itemIdAndQuantity = dinnerDto.getFoodIdAndQuantity();
        for (var itemId : itemIdAndQuantity.keySet()) {
            Food food = foodRepository.findById(itemId).orElseThrow(() -> {
                throw new NoExistInstanceException(Food.class);
            });

            DinnerFood dinnerFood = new DinnerFood();
            dinnerFood.setDinner(dinner);
            dinnerFood.setFood(food);
            dinnerFood.setFoodQuantity(itemIdAndQuantity.get(itemId));

            food.getDinnerFoodList().add(dinnerFood);
            ret.add(dinnerFood);
        }
        return ret;
    }

    public Map<Food, Integer> toFoodAndQuantity(Long dinnerId) {
        var ret = new ConcurrentHashMap<Food, Integer>();
        Dinner dinner = dinnerRepository.findById(dinnerId).orElseThrow(() -> {
            throw new NoExistInstanceException(Dinner.class);
        });

        List<DinnerFood> dinnerFoodList = dinner.getDinnerFoodList();
        for (var dinnerFood : dinnerFoodList) {
            Food food = dinnerFood.getFood();
            ret.put(food, dinnerFood.getFoodQuantity());
        }
        return ret;
    }

    private List<ExcludedStyle> makeExcludedStyleList(Dinner dinner, DinnerDto dinnerDto) {
        var ret = new ArrayList<ExcludedStyle>();
        List<Long> excludedStyleIdList = dinnerDto.getExcludedStyleIdList();
        for (var styleId : excludedStyleIdList) {
            Style foundStyle = styleRepository.findById(styleId).get();
            ExcludedStyle excludedStyle = new ExcludedStyle();
            excludedStyle.setDinner(dinner);
            excludedStyle.setStyle(foundStyle);

            ret.add(excludedStyle);
        }
        return ret;
    }

    public Boolean isDinnerNameExist(String name) {
        return dinnerRepository.findAllByName(name).stream()
                .map(Dinner::getEnable)
                .anyMatch(e -> e == true);
    }
}
