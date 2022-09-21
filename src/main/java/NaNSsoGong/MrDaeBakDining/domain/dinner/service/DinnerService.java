package NaNSsoGong.MrDaeBakDining.domain.dinner.service;

import NaNSsoGong.MrDaeBakDining.domain.decoration.domain.Decoration;
import NaNSsoGong.MrDaeBakDining.domain.decoration.repository.DecorationRepository;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.Dinner;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.DinnerDecoration;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.DinnerFood;
import NaNSsoGong.MrDaeBakDining.domain.dinner.dto.DinnerDto;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.domain.dinner.repository.DinnerRepository;
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

    public Optional<Dinner> makeDinner(DinnerDto dinnerDto){
        Dinner dinner = new Dinner();
        dinner.setName(dinnerDto.getName());
        dinner.setDinnerFoodList(makeDinnerFoodList(dinner, dinnerDto));
        dinner.setDinnerDecorationList(makeDinnerDecorationList(dinner, dinnerDto));
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

    private List<DinnerFood> makeDinnerFoodList(Dinner dinner, DinnerDto dinnerDTO){
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

    private List<DinnerDecoration> makeDinnerDecorationList(Dinner dinner, DinnerDto dinnerDTO){
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
