package NaNSsoGong.MrDaeBakDining.domain.food.Controller;

import NaNSsoGong.MrDaeBakDining.domain.PageListRequest;
import NaNSsoGong.MrDaeBakDining.domain.food.Controller.form.SaveRequest;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.FoodCategory;
import NaNSsoGong.MrDaeBakDining.domain.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.domain.food.service.FoodService;
import NaNSsoGong.MrDaeBakDining.domain.DeleteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodRestController {
    private final FoodRepository foodRepository;
    private final FoodService foodServicel;

    @GetMapping("/page-list")
    public Page<Food> pageList(@RequestBody PageListRequest pageListRequest){
        return foodRepository.findAll(pageListRequest.of());
    }

    @GetMapping("/category-list")
    public List<String> categoryList(){
        return Arrays.stream(FoodCategory.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @PostMapping("/save")
    public Long save(SaveRequest saveRequest){
        Food food = new Food();
        food.setName(saveRequest.getName());
        food.setSellPrice(saveRequest.getSellPrice());
        food.setFoodCategory(saveRequest.getFoodCategory());
        foodRepository.save(food);
        return 1L;
    }

    @DeleteMapping("/delete")
    public Long delete(DeleteRequest deleteRequest){
        foodRepository.deleteById(deleteRequest.getId());
        return 1L;
    }
}
