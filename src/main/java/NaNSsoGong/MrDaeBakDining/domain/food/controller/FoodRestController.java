package NaNSsoGong.MrDaeBakDining.domain.food.controller;

import NaNSsoGong.MrDaeBakDining.domain.food.controller.request.FoodCreateRequest;
import NaNSsoGong.MrDaeBakDining.domain.food.controller.request.FoodUpdateRequest;
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
import NaNSsoGong.MrDaeBakDining.exception.exception.NoExistEntityException;
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

import static NaNSsoGong.MrDaeBakDining.domain.ResponseConst.DISABLE_COMPLETE;

@Tag(name = "food")
@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodRestController {
    private final FoodRepository foodRepository;
    private final FoodService foodService;

    @Operation(summary = "푸드조회 by foodId")
    @GetMapping("/{foodId}")
    public ResponseEntity<FoodInfoResponse> foodInfoByFoodId(@PathVariable(value = "foodId") Long foodId) {
        Food food = foodRepository.findById(foodId).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 푸드입니다");
        });
        return ResponseEntity.ok().body(new FoodInfoResponse(food));
    }

    @Operation(summary = "푸드리스트조회", description = "enable = true만 조회합니다")
    @GetMapping("/list")
    public Page<FoodInfoResponse> foodInfoList(Pageable pageable) {
        return foodRepository
                .findAllByEnable(true, pageable)
                .map(FoodInfoResponse::new);
    }

    @Operation(summary = "푸드카테고리 후보값리스트조회")
    @GetMapping("/category/value/list")
    public ResponseEntity<List<FoodCategory>> foodCategoryList(){
        return ResponseEntity.ok().body(List.of(FoodCategory.values()));
    }

    @Operation(summary = "푸드리스트조회 By FoodCategory")
    @GetMapping("/category/list")
    public Page<FoodInfoResponse> foodInfoListByFoodCategory(@RequestParam FoodCategory foodCategory, Pageable pageable){
        return foodRepository.findAllByFoodCategory(foodCategory, pageable).map(FoodInfoResponse::new);
    }


    @Operation(summary = "푸드생성", description = "새로운 푸드메뉴를 만듭니다")
    @Transactional
    @PostMapping("")
    public ResponseEntity<FoodInfoResponse> foodCreate(@RequestBody @Validated FoodCreateRequest foodCreateRequest) {
        if (foodService.isFoodNameExist(foodCreateRequest.getName()))
            throw new DuplicatedFieldValueException();

        Food food = foodCreateRequest.toFood();
        Food savedFood = foodRepository.save(food);
        return ResponseEntity.ok().body(new FoodInfoResponse(savedFood));
    }

    @Operation(summary = "만들 수 있는 푸드인지 확인", description = "푸드에 들어가는 재료의 재고량에 의존합니다")
    @GetMapping("/make/{foodId}")
    public ResponseEntity<Boolean> isMakeAble(@PathVariable(value = "foodId") Long foodId) {
        Food food = foodRepository.findById(foodId).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 푸드입니다");
        });
        return ResponseEntity.ok().body(foodService.isMakeAble(food));
    }

    @Operation(summary = "음식 만들기", description = "푸드에 들어가는 재료를 감소시킵니다, 재료가 부족할때 요청할 경우 예외발생")
    @PostMapping("/make/{foodId}")
    public ResponseEntity<FoodMakeResponse> foodMake(@PathVariable(value = "foodId") Long foodId) {
        Food food = foodRepository.findById(foodId).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 푸드입니다");
        });
        if (!foodService.isMakeAble(food))
            throw new BusinessException("재료가 부족해 만들 수 없는 음식입니다");
        Map<Ingredient, Integer> ingredientAndConsumedQuantity = foodService.makeFood(food);
        return ResponseEntity
                .ok()
                .body(new FoodMakeResponse(foodId, food.getName(), ingredientAndConsumedQuantity));
    }

    @Operation(summary = "푸드 비활성화", description = "이 푸드가 포함되는 디너가 존재하지 않을때만 비활성화 할 수 있습니다")
    @Transactional
    @PatchMapping("/disable/{foodId}")
    public ResponseEntity<String> talbewareDisable(@PathVariable(value = "foodId") Long foodId) {
        Food food = foodRepository.findById(foodId).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 푸드입니다");
        });
        if (food.getDinnerFoodList().stream().filter(e -> e.getDinner().getEnable()).count() != 0)
            throw new DisabledEntityContainException(
                    food.getDinnerFoodList().stream()
                            .map(e -> e.getDinner())
                            .filter(e -> e.getEnable())
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

    @Operation(summary = "푸드업데이트")
    @Transactional
    @PutMapping("/{foodId}")
    public ResponseEntity<FoodInfoResponse> foodUpdate(
            @PathVariable(value = "foodId") Long foodId,
            @RequestBody @Validated FoodUpdateRequest foodUpdateRequest) {
        Food food = foodRepository.findById(foodId).orElseThrow(() -> {
            throw new NoExistEntityException();
        });
        if (!food.getName().equals(foodUpdateRequest.getName())
                && foodService.isFoodNameExist(foodUpdateRequest.getName()))
            throw new DuplicatedFieldValueException();

        food.setName(foodUpdateRequest.getName());
        food.setFoodCategory(foodUpdateRequest.getFoodCategory());
        food.setSellPrice(foodUpdateRequest.getSellPrice());
        food.setOrderable(foodUpdateRequest.getOrderable());

        return ResponseEntity.ok().body(new FoodInfoResponse(food));
    }
}
