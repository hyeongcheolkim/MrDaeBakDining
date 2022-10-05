package NaNSsoGong.MrDaeBakDining.domain.dinner.service;

import NaNSsoGong.MrDaeBakDining.domain.decoration.domain.Decoration;
import NaNSsoGong.MrDaeBakDining.domain.decoration.repository.DecorationRepository;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.Dinner;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.DinnerDecoration;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.DinnerFood;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.ExcludedStyle;
import NaNSsoGong.MrDaeBakDining.domain.dinner.dto.DinnerDto;
import NaNSsoGong.MrDaeBakDining.domain.dinner.repository.DinnerRepository;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.domain.style.repository.StyleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class DinnerService {
    private final DinnerRepository dinnerRepository;
    private final FoodRepository foodRepository;
    private final DecorationRepository decorationRepository;
    private final StyleRepository styleRepository;

    @Transactional
    public Dinner makeDinner(DinnerDto dinnerDto) {
        Dinner dinner = new Dinner();
        dinnerRepository.save(dinner);
        dinner.setName(dinnerDto.getName());

        List<DinnerFood> dinnerFoodList = makeDinnerFoodList(dinner, dinnerDto);
        for(var dinnerFood : dinnerFoodList)
            dinner.getDinnerFoodList().add(dinnerFood);

        List<DinnerDecoration> dinnerDecorationList = makeDinnerDecorationList(dinner, dinnerDto);
        for(var dinnerDecoration : dinnerDecorationList)
            dinner.getDinnerDecorationList().add(dinnerDecoration);

        List<ExcludedStyle> excludedStyleList = makeExcludedStyleList(dinner, dinnerDto);
        for(var excludedStyle : excludedStyleList)
            dinner.getExcludedStyleList().add(excludedStyle);

        return dinner;
    }

    public Map<Food, Integer> toFoodAndQuantity(Long dinnerId) {
        var ret = new ConcurrentHashMap<Food, Integer>();
        Optional<Dinner> foundDinner = dinnerRepository.findById(dinnerId);
        if (foundDinner.isEmpty())
            return ret;
        List<DinnerFood> dinnerFoodList = foundDinner.get().getDinnerFoodList();
        for (var dinnerFood : dinnerFoodList) {
            Food food = dinnerFood.getFood();
            ret.put(food, dinnerFood.getFoodQuantity());
        }
        return ret;
    }

    public List<Decoration> toDecorationList(Long dinnerId) {
        var ret = new ArrayList<Decoration>();
        Optional<Dinner> foundDinner = dinnerRepository.findById(dinnerId);
        if (foundDinner.isEmpty())
            return ret;
        List<DinnerDecoration> dinnerDecorationList = foundDinner.get().getDinnerDecorationList();
        for (var dinnerDecoration : dinnerDecorationList)
            ret.add(dinnerDecoration.getDecoration());
        return ret;
    }

    private List<DinnerFood> makeDinnerFoodList(Dinner dinner, DinnerDto dinnerDto) {
        var ret = new ArrayList<DinnerFood>();
        Map<Long, Integer> itemIdAndQuantity = dinnerDto.getFoodIdAndQuantity();
        for (var itemId : itemIdAndQuantity.keySet()) {
            DinnerFood dinnerFood = new DinnerFood();
            dinnerFood.setDinner(dinner);
            dinnerFood.setFood(foodRepository.findById(itemId).get());
            dinnerFood.setFoodQuantity(itemIdAndQuantity.get(itemId));
            ret.add(dinnerFood);
        }
        return ret;
    }

    private List<DinnerDecoration> makeDinnerDecorationList(Dinner dinner, DinnerDto dinnerDto) {
        var ret = new ArrayList<DinnerDecoration>();
        List<Long> decorationIdList = dinnerDto.getDecorationIdList();
        for (var decorationId : decorationIdList) {
            DinnerDecoration dinnerDecoration = new DinnerDecoration();
            dinnerDecoration.setDinner(dinner);
            dinnerDecoration.setDecoration(decorationRepository.findById(decorationId).get());
            ret.add(dinnerDecoration);
        }
        return ret;
    }

    private List<ExcludedStyle> makeExcludedStyleList(Dinner dinner, DinnerDto dinnerDto){
        var ret = new ArrayList<ExcludedStyle>();
        List<Long> excludedStyleIdList = dinnerDto.getExcludedStyleIdList();
        for(var styleId : excludedStyleIdList){
            ExcludedStyle excludedStyle = new ExcludedStyle();
            excludedStyle.setStyle(styleRepository.findById(styleId).get());
            excludedStyle.setDinner(dinner);
            ret.add(excludedStyle);
        }
        return ret;
    }
}
