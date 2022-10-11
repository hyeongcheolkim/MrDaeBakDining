package NaNSsoGong.MrDaeBakDining.domain.ingredient.controller;

import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.controller.request.IngredientCreateRequest;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.controller.request.IngredientQuantityUpdateRequest;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.controller.request.IngredientUpdateRequest;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.controller.response.IngredientInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.domain.Ingredient;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.repository.IngredientRepository;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.service.IngredientService;
import NaNSsoGong.MrDaeBakDining.domain.recipe.domain.Recipe;
import NaNSsoGong.MrDaeBakDining.exception.exception.*;
import NaNSsoGong.MrDaeBakDining.exception.response.BusinessExceptionResponse;
import NaNSsoGong.MrDaeBakDining.exception.response.DisabledEntityContainInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static NaNSsoGong.MrDaeBakDining.exception.response.ResponseConst.DISABLE_COMPLETE;

@Tag(name = "ingredient")
@RestController
@Transactional
@RequestMapping("/api/ingredient")
@RequiredArgsConstructor
@ApiResponse(responseCode = "400", description = "business error", content = @Content(schema = @Schema(implementation = BusinessExceptionResponse.class)))
public class IngredientRestController {
    private final IngredientRepository ingredientRepository;
    private final IngredientService ingredientService;

    @Operation(summary = "재료조회")
    @GetMapping("/{ingredientId}")
    public ResponseEntity<IngredientInfoResponse> ingredientInfo(@PathVariable(name = "ingredientId") Long ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(() -> {
            throw new NoExistInstanceException(Ingredient.class);
        });
        return ResponseEntity.ok().body(new IngredientInfoResponse(ingredient));
    }

    @Operation(summary = "재료리스트조회", description = "enable = true만 조회합니다")
    @GetMapping("/list")
    public Page<IngredientInfoResponse> ingredientInfoList(Pageable pageable) {
        return ingredientRepository
                .findAllByEnable(true, pageable)
                .map(IngredientInfoResponse::new);
    }

    @Operation(summary = "새로운 재료생성")
    @PostMapping("")
    public ResponseEntity<IngredientInfoResponse> ingredientCreate(@RequestBody @Validated IngredientCreateRequest ingredientCreateRequest) {
        if (ingredientService.isIngredientNameExist(ingredientCreateRequest.getName()))
            throw new DuplicatedFieldValueException();

        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientCreateRequest.getName());
        ingredient.setStockQuantity(ingredientCreateRequest.getStockQuantity());
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return ResponseEntity.ok().body(new IngredientInfoResponse(savedIngredient));
    }

    @Operation(summary = "수량 증감", description = "재고량을 0미만으로 만들 수 없다면 요청은 무시되고, Exception이 발생합니다")
    @PatchMapping("/{ingredientId}")
    public ResponseEntity<IngredientInfoResponse> ingredientQuantityUpdate(@PathVariable(name = "ingredientId") Long ingredientId,
                                                                           @RequestBody @Validated IngredientQuantityUpdateRequest ingredientQuantityUpdateRequest) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(() -> {
            throw new NoExistInstanceException(Ingredient.class);
        });
        Integer newQuantity = ingredient.getStockQuantity() + ingredientQuantityUpdateRequest.getQuantityDiff();
        if (newQuantity < 0)
            throw new MinusQuantityException("재료 수량은 0보다 작을 수 없습니다");
        ingredient.setStockQuantity(newQuantity);
        return ResponseEntity.ok().body(new IngredientInfoResponse(ingredient));
    }

    @Operation(summary = "재료 비활성화", description = "이 재료를 필요로 하는 레시피가 존재하지 않을때 비활성화할 수 있습니다")
    @PatchMapping("/disable/{ingredientId}")
    public ResponseEntity<String> ingredientDisable(@PathVariable(value = "ingredientId") Long ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(() -> {
            throw new NoExistInstanceException(Ingredient.class);
        });
        if (ingredient.getRecipeList().stream().filter(e -> e.getFood().getEnable()).count() != 0)
            throw new DisabledEntityContainException(
                    ingredient.getRecipeList().stream()
                            .map(Recipe::getFood)
                            .filter(Food::getEnable)
                            .map(e -> DisabledEntityContainInfo.builder()
                                    .classTypeName(Hibernate.getClass(e).getSimpleName())
                                    .instanceName(e.getName())
                                    .instanceId(e.getId())
                                    .build())
                            .collect(Collectors.toList())
            );

        ingredient.setEnable(false);
        return ResponseEntity.ok().body(DISABLE_COMPLETE);
    }

    @Operation(summary = "재료업데이트")
    @PutMapping("/{ingredientId}")
    public ResponseEntity<IngredientInfoResponse> ingredientUpdate(
            @PathVariable(value = "ingredientId") Long ingredientId,
            @RequestBody @Validated IngredientUpdateRequest ingredientUpdateRequest) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(() -> {
            throw new NoExistInstanceException(Ingredient.class);
        });
        if (!ingredient.getName().equals(ingredientUpdateRequest.getName())
                && ingredientService.isIngredientNameExist(ingredientUpdateRequest.getName()))
            throw new DuplicatedFieldValueException();
        ingredient.setName(ingredientUpdateRequest.getName());
        ingredient.setStockQuantity(ingredient.getStockQuantity());

        return ResponseEntity.ok().body(new IngredientInfoResponse(ingredient));
    }
}
