package NaNSsoGong.MrDaeBakDining.preset.dinner.service;

import NaNSsoGong.MrDaeBakDining.item.decoration.domain.Decoration;
import NaNSsoGong.MrDaeBakDining.item.decoration.repository.DecorationRepository;
import NaNSsoGong.MrDaeBakDining.item.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.item.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.preset.dinner.domain.Dinner;
import NaNSsoGong.MrDaeBakDining.preset.dinner.domain.DinnerDecoration;
import NaNSsoGong.MrDaeBakDining.preset.dinner.domain.DinnerFood;
import NaNSsoGong.MrDaeBakDining.preset.dinner.dto.DinnerDTO;
import NaNSsoGong.MrDaeBakDining.preset.dinner.repository.DinnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Optional<Dinner> register(DinnerDTO dinnerDTO){
        Dinner dinner = new Dinner();
        dinner.setName(dinnerDTO.getName());
        dinner.setDinnerFoodList(makeDinnerFoodList(dinner, dinnerDTO));
        dinner.setDinnerDecorationList(makeDinnerDecorationList(dinner, dinnerDTO));
        Dinner savedDinner = dinnerRepository.save(dinner);

        return Optional.of(savedDinner);
    }

    public Map<Long, Integer> FoodMap(Dinner dinner){
        Map<Long, Integer> ret = new ConcurrentHashMap<>();
        List<DinnerFood> dinnerFoodList = dinner.getDinnerFoodList();
        for(var dinnerFood : dinnerFoodList){
            Long foodId = dinnerFood.getFood().getId();
            Integer foodQuantity = dinnerFood.getFoodQuantity();
            ret.put(foodId, foodQuantity);
        }
        return ret;
    }

    public Map<Long, Integer> DecorationMap(Dinner dinner){
        Map<Long, Integer> ret = new ConcurrentHashMap<>();
        List<DinnerDecoration> dinnerDecorationList = dinner.getDinnerDecorationList();
        for(var dinnerDecoration : dinnerDecorationList){
            Long decorationId = dinnerDecoration.getDecoration().getId();
            Integer decorationQuantity = dinnerDecoration.getDecorationQuantity();
            ret.put(decorationId, decorationQuantity);
        }
        return ret;
    }

    private List<DinnerFood> makeDinnerFoodList(Dinner dinner, DinnerDTO dinnerDTO){
        var ret = new ArrayList<DinnerFood>();
        Map<Long, Integer> foodIdAndQuantity = dinnerDTO.getFoodIdAndQuantity();
        for(var foodId : foodIdAndQuantity.keySet()){
            Optional<Food> foundFood = foodRepository.findById(foodId);
            if(foundFood.isEmpty())
                continue;
            DinnerFood dinnerFood = new DinnerFood();
            dinnerFood.setDinner(dinner);
            dinnerFood.setFood(foundFood.get());
            dinnerFood.setFoodQuantity(foodIdAndQuantity.get(foodId));
            ret.add(dinnerFood);
        }
        return ret;
    }

    private List<DinnerDecoration> makeDinnerDecorationList(Dinner dinner, DinnerDTO dinnerDTO){
        var ret = new ArrayList<DinnerDecoration>();
        Map<Long, Integer> decorationIdAndQuantity = dinnerDTO.getDecorationIdAndQuantity();
        for(var decorationId : decorationIdAndQuantity.keySet()){
            Optional<Decoration> foundDecoration = decorationRepository.findById(decorationId);
            if(foundDecoration.isEmpty())
                continue;
            DinnerDecoration dinnerDecoration = new DinnerDecoration();
            dinnerDecoration.setDinner(dinner);
            dinnerDecoration.setDecoration(foundDecoration.get());
            dinnerDecoration.setDecorationQuantity(decorationIdAndQuantity.get(decorationId));
            ret.add(dinnerDecoration);
        }
        return ret;
    }
}
