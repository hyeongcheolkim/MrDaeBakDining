package NaNSsoGong.MrDaeBakDining.domain.recipe.controller;

import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.domain.Ingredient;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.repository.IngredientRepository;
import NaNSsoGong.MrDaeBakDining.domain.recipe.controller.request.RecipeCreateRequest;
import NaNSsoGong.MrDaeBakDining.domain.recipe.controller.request.RecipeUpdateRequest;
import NaNSsoGong.MrDaeBakDining.domain.recipe.controller.response.RecipeCreateResponse;
import NaNSsoGong.MrDaeBakDining.domain.recipe.controller.response.RecipeInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.recipe.controller.response.RecipeUpdateResponse;
import NaNSsoGong.MrDaeBakDining.domain.recipe.domain.Recipe;
import NaNSsoGong.MrDaeBakDining.domain.recipe.repository.RecipeRepository;
import NaNSsoGong.MrDaeBakDining.domain.recipe.service.RecipeService;
import NaNSsoGong.MrDaeBakDining.error.exception.EntityCreateFailException;
import NaNSsoGong.MrDaeBakDining.error.exception.NoExistEntityException;
import NaNSsoGong.MrDaeBakDining.error.response.BusinessExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
@ApiResponse(responseCode = "400", description = "business error", content = @Content(schema = @Schema(implementation = BusinessExceptionResponse.class)))
public class RecipeRestController {
    private final RecipeRepository recipeRepository;
    private final RecipeService recipeService;
    private final FoodRepository foodRepository;
    private final IngredientRepository ingredientRepository;

    @Operation(summary = "레시피단건조회 by recipeId")
    @GetMapping("/{recipeId}")
    public ResponseEntity<RecipeInfoResponse> recipeInfo(@PathVariable(name = "recipeId") Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 레시피입니다");
        });
        return ResponseEntity.ok().body(new RecipeInfoResponse(recipe));
    }

    @Operation(summary = "레시피 리스트조회")
    @GetMapping("/list")
    public Page<RecipeInfoResponse> recipeInfoList(Pageable pageable) {
        return recipeRepository.findAll(pageable).map(RecipeInfoResponse::new);
    }

    @Operation(summary = "레시피 생성")
    @Transactional
    @PostMapping("")
    public ResponseEntity<RecipeCreateResponse> recipeCreate(@RequestBody @Validated RecipeCreateRequest recipeCreateRequest) {
        Food food = foodRepository.findById(recipeCreateRequest.getFoodId()).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 푸드입니다");
        });
        Ingredient ingredient = ingredientRepository.findById(recipeCreateRequest.getIngredientId()).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 재료입니다");
        });

        Optional<Recipe> foundRecipe = recipeRepository.findByFoodIdAndIngredientId(food.getId(), ingredient.getId());
        if (foundRecipe.isPresent())
            throw new EntityCreateFailException();

        Integer ingredientQuantity = recipeCreateRequest.getIngredientQuantity();
        Recipe recipe = recipeService.makeRecipe(food, ingredient, ingredientQuantity);
        return ResponseEntity.ok().body(new RecipeCreateResponse(recipe.getId(), false));
    }

    @Operation(summary = "레시피업데이트")
    @Transactional
    @PutMapping("")
    public ResponseEntity<RecipeUpdateResponse> recipeUpdate(@RequestBody @Validated RecipeUpdateRequest recipeUpdateRequest) {
        Long foodId = recipeUpdateRequest.getFoodId();
        Long ingredientId = recipeUpdateRequest.getIngredientId();
        Integer ingredientQuantity = recipeUpdateRequest.getIngredientQuantity();
        Recipe recipe = recipeRepository.findByFoodIdAndIngredientId(foodId, ingredientId).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 레시피입니다");
        });
        recipe.setIngredientQuantity(ingredientQuantity);
        return ResponseEntity.ok().body(new RecipeUpdateResponse(recipe.getId()));
    }

    @Operation(summary = "레시피삭제", description = "연관되어있는 푸드나 재료는 삭제되지 않습니다")
    @DeleteMapping("/{recipeId}")
    public ResponseEntity recipeDelete(@PathVariable(name = "recipeId") Long recipeId) {
        recipeRepository.deleteById(recipeId);
        return ResponseEntity.ok().body("삭제완료");
    }
}
