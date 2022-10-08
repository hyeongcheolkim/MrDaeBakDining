package NaNSsoGong.MrDaeBakDining.domain.dinner.service;

import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.Dinner;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.DinnerFood;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.ExcludedStyle;
import NaNSsoGong.MrDaeBakDining.domain.dinner.repository.DinnerRepository;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.domain.style.domain.Style;
import NaNSsoGong.MrDaeBakDining.domain.style.repository.StyleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(value = false)
class DinnerServiceTest {
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private StyleRepository styleRepository;
    @Autowired
    private DinnerRepository dinnerRepository;

    @Test
    void makeDinner() {
        Food food1 = new Food();
        Food food2 = new Food();
        foodRepository.save(food1);
        foodRepository.save(food2);

        Style style1 = new Style();
        Style style2 = new Style();
        styleRepository.save(style1);
        styleRepository.save(style2);

        Dinner dinner = new Dinner();

        DinnerFood dinnerFood = new DinnerFood();
        dinnerFood.setDinner(dinner);
        dinnerFood.setFood(food1);

        ExcludedStyle excludedStyle = new ExcludedStyle();
        excludedStyle.setDinner(dinner);
        excludedStyle.setStyle(style1);

        dinner.getDinnerFoodList().add(dinnerFood);
        dinner.getExcludedStyleList().add(excludedStyle);

        dinnerRepository.save(dinner);
    }
}