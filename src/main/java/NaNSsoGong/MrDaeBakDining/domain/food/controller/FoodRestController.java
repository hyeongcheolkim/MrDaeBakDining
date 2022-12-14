package NaNSsoGong.MrDaeBakDining.domain.food.controller;

import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.Dinner;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.DinnerFood;
import NaNSsoGong.MrDaeBakDining.domain.food.controller.request.FoodCreateRequest;
import NaNSsoGong.MrDaeBakDining.domain.food.controller.request.FoodUpdateOderableRequest;
import NaNSsoGong.MrDaeBakDining.domain.food.controller.response.FoodInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.food.controller.response.FoodMakeResponse;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.FoodCategory;
import NaNSsoGong.MrDaeBakDining.domain.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.domain.food.service.FoodService;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.domain.Ingredient;
import NaNSsoGong.MrDaeBakDining.exception.exception.BusinessException;
import NaNSsoGong.MrDaeBakDining.exception.exception.DisabledEntityContainException;
import NaNSsoGong.MrDaeBakDining.exception.exception.DuplicatedFieldValueException;
import NaNSsoGong.MrDaeBakDining.exception.exception.NoExistInstanceException;
import NaNSsoGong.MrDaeBakDining.exception.response.DisabledEntityContainInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static NaNSsoGong.MrDaeBakDining.exception.response.ResponseConst.DISABLE_COMPLETE;

@Tag(name = "food")
@RestController
@Transactional
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodRestController {
    private final FoodRepository foodRepository;
    private final FoodService foodService;

    @Operation(summary = "???????????? by foodId")
    @GetMapping("/{foodId}")
    public ResponseEntity<FoodInfoResponse> foodInfoByFoodId(@PathVariable(value = "foodId") Long foodId) {
        Food food = foodRepository.findById(foodId).orElseThrow(() -> {
            throw new NoExistInstanceException(Food.class);
        });
        return ResponseEntity.ok().body(new FoodInfoResponse(food));
    }

    @Operation(summary = "?????????????????????", description = "enable = true??? ???????????????")
    @GetMapping("/list")
    public Page<FoodInfoResponse> foodInfoList(Pageable pageable) {
        return foodRepository
                .findAllByEnable(true, pageable)
                .map(FoodInfoResponse::new);
    }

    @Operation(summary = "?????????????????? ????????????????????????")
    @GetMapping("/category/value/list")
    public ResponseEntity<List<FoodCategory>> foodCategoryList(){
        return ResponseEntity.ok().body(List.of(FoodCategory.values()));
    }

    @Operation(summary = "????????????????????? By FoodCategory")
    @GetMapping("/category/list")
    public Page<FoodInfoResponse> foodInfoListByFoodCategory(@RequestParam FoodCategory foodCategory, Pageable pageable){
        return foodRepository.findAllByFoodCategory(foodCategory, pageable).map(FoodInfoResponse::new);
    }


    @Operation(summary = "????????????", description = "????????? ??????????????? ????????????")
    @PostMapping("")
    public ResponseEntity<FoodInfoResponse> foodCreate(@RequestBody @Validated FoodCreateRequest foodCreateRequest) {
        if (foodService.isFoodNameExist(foodCreateRequest.getName()))
            throw new DuplicatedFieldValueException();

        Food food = foodCreateRequest.toFood();
        Food savedFood = foodRepository.save(food);
        return ResponseEntity.ok().body(new FoodInfoResponse(savedFood));
    }

    @Operation(summary = "?????? ??? ?????? ???????????? ??????", description = "????????? ???????????? ????????? ???????????? ???????????????")
    @GetMapping("/make/{foodId}")
    public ResponseEntity<Boolean> isMakeAble(@PathVariable(value = "foodId") Long foodId) {
        Food food = foodRepository.findById(foodId).orElseThrow(() -> {
            throw new NoExistInstanceException(Food.class);
        });
        return ResponseEntity.ok().body(foodService.isMakeAble(food));
    }

    @Operation(summary = "?????? ?????????", description = "????????? ???????????? ????????? ??????????????????, ????????? ???????????? ????????? ?????? ????????????")
    @PostMapping("/make/{foodId}")
    public ResponseEntity<FoodMakeResponse> foodMake(@PathVariable(value = "foodId") Long foodId) {
        Food food = foodRepository.findById(foodId).orElseThrow(() -> {
            throw new NoExistInstanceException(Food.class);
        });
        if (!foodService.isMakeAble(food))
            throw new BusinessException("????????? ????????? ?????? ??? ?????? ???????????????");
        Map<Ingredient, Integer> ingredientAndConsumedQuantity = foodService.makeFood(food);
        return ResponseEntity
                .ok()
                .body(new FoodMakeResponse(foodId, food.getName(), ingredientAndConsumedQuantity));
    }

    @Operation(summary = "?????? ????????????", description = "??? ????????? ???????????? ????????? ???????????? ???????????? ???????????? ??? ??? ????????????")
    @PatchMapping("/disable/{foodId}")
    public ResponseEntity<String> foodDisable(@PathVariable(value = "foodId") Long foodId) {
        Food food = foodRepository.findById(foodId).orElseThrow(() -> {
            throw new NoExistInstanceException(Food.class);
        });
        if (food.getDinnerFoodList().stream().filter(e -> e.getDinner().getEnable()).count() != 0)
            throw new DisabledEntityContainException(
                    food.getDinnerFoodList().stream()
                            .map(DinnerFood::getDinner)
                            .filter(Dinner::getEnable)
                            .map(e -> DisabledEntityContainInfo.builder()
                                    .classTypeName(Hibernate.getClass(e).getSimpleName())
                                    .instanceName(e.getName())
                                    .instanceId(e.getId())
                                    .build())
                            .collect(Collectors.toList())
            );
        food.setEnable(false);
        return ResponseEntity.ok().body(DISABLE_COMPLETE);
    }

    @Operation(summary = "?????? ?????? ????????????", description = "??? ????????? ???????????? ????????? ?????? ???????????? ????????? ??? ????????? ???????????? ?????????")
    @PatchMapping("/disable-cascade/{foodId}")
    public ResponseEntity<String> foodDisableCascade(@PathVariable(value = "foodId") Long foodId) {
        Food food = foodRepository.findById(foodId).orElseThrow(() -> {
            throw new NoExistInstanceException(Food.class);
        });
        for(var dinnerFood : food.getDinnerFoodList())
            dinnerFood.getDinner().setEnable(false);
        food.setEnable(false);
        return ResponseEntity.ok().body(DISABLE_COMPLETE);
    }

    @Operation(summary = "?????????????????? ??????")
    @PutMapping("/{foodId}")
    public ResponseEntity<FoodInfoResponse> foodUpdateOderable(
            @PathVariable(value = "foodId") Long foodId,
            @RequestBody @Validated FoodUpdateOderableRequest foodUpdateOderableRequest) {
        Food food = foodRepository.findById(foodId).orElseThrow(() -> {
            throw new NoExistInstanceException(Food.class);
        });

        food.setOrderable(foodUpdateOderableRequest.getOrderable());
        return ResponseEntity.ok().body(new FoodInfoResponse(food));
    }
}
