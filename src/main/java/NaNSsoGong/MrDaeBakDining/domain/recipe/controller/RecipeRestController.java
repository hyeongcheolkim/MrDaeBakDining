package NaNSsoGong.MrDaeBakDining.domain.recipe.controller;

import NaNSsoGong.MrDaeBakDining.domain.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.repository.IngredientRepository;
import NaNSsoGong.MrDaeBakDining.domain.recipe.controller.request.RecipeCreateRequest;
import NaNSsoGong.MrDaeBakDining.domain.recipe.controller.request.RecipeUpdateRequest;
import NaNSsoGong.MrDaeBakDining.domain.recipe.controller.response.RecipeCreateResponse;
import NaNSsoGong.MrDaeBakDining.domain.recipe.controller.response.RecipeInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.recipe.controller.response.RecipeUpdateResponse;
import NaNSsoGong.MrDaeBakDining.domain.recipe.domain.Recipe;
import NaNSsoGong.MrDaeBakDining.domain.recipe.repository.RecipeRepository;
import NaNSsoGong.MrDaeBakDining.domain.recipe.service.RecipeService;
import NaNSsoGong.MrDaeBakDining.error.exception.NoExistEntityException;
import io.swagger.v3.oas.annotations.Operation;
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
public class RecipeRestController {
    private final RecipeRepository recipeRepository;
    private final RecipeService recipeService;
    private final FoodRepository foodRepository;
    private final IngredientRepository ingredientRepository;

    @Operation(summary = "레시피단건조회 by recipeId")
    @GetMapping("/{recipeId}")
    public ResponseEntity<RecipeInfoResponse> recipeInfo(@PathVariable(name = "recipeId") Long recipeId) {
        Optional<Recipe> foundRecipe = recipeRepository.findById(recipeId);
        if (foundRecipe.isEmpty())
            throw new NoExistEntityException("존재하지 않는 레시피입니다");
        return ResponseEntity.ok().body(new RecipeInfoResponse(foundRecipe.get()));
    }

    @Operation(summary = "레시피 리스트조회")
    @GetMapping("/list")
    public Page<RecipeInfoResponse> recipeInfoList(Pageable pageable) {
        return recipeRepository.findAll(pageable).map(RecipeInfoResponse::new);
    }

    @Operation(summary = "레시피 생성", description = "이미 존재하던 레시피면, ingredientQuantity를 업데이트합니다")
    @Transactional
    @PostMapping("")
    public ResponseEntity<RecipeCreateResponse> recipeCreate(@RequestBody @Validated RecipeCreateRequest recipeCreateRequest) {
        Long foodId = recipeCreateRequest.getFoodId();
        Long ingredientId = recipeCreateRequest.getIngredientId();
        if (foodRepository.findById(foodId).isEmpty())
            throw new NoExistEntityException("존재하지 않는 푸드입니다");
        if (ingredientRepository.findById(ingredientId).isEmpty())
            throw new NoExistEntityException("존재하지 않는 재료입니다");

        Optional<Recipe> foundRecipe = recipeRepository.findByFoodIdAndIngredientId(foodId, ingredientId);
        if(foundRecipe.isPresent()){
            foundRecipe.get().setIngredientQuantity(recipeCreateRequest.getIngredientQuantity());
            return ResponseEntity.ok().body(new RecipeCreateResponse(foundRecipe.get().getId(), true));
        }

        Integer ingredientQuantity = recipeCreateRequest.getIngredientQuantity();
        Long recipeId = recipeService.makeRecipe(foodId, ingredientId, ingredientQuantity);
        return ResponseEntity.ok().body(new RecipeCreateResponse(recipeId, false));
    }

    @Operation(summary = "레시피업데이트")
    @Transactional
    @PutMapping("")
    public ResponseEntity<RecipeUpdateResponse> recipeUpdate(@RequestBody @Validated RecipeUpdateRequest recipeUpdateRequest){
        Long foodId = recipeUpdateRequest.getFoodId();
        Long ingredientId = recipeUpdateRequest.getIngredientId();
        Integer ingredientQuantity = recipeUpdateRequest.getIngredientQuantity();
        Optional<Recipe> foundRecipe = recipeRepository.findByFoodIdAndIngredientId(foodId, ingredientId);
        if(foundRecipe.isEmpty())
            throw new NoExistEntityException("존재하지 않는 레시피입니다");
        foundRecipe.get().setIngredientQuantity(ingredientQuantity);
        return ResponseEntity.ok().body(new RecipeUpdateResponse(foundRecipe.get().getId()));
    }

    @Operation(summary = "레시피삭제", description = "연관되어있는 푸드나 재료는 삭제되지 않습니다")
    @DeleteMapping("/{recipeId}")
    public ResponseEntity recipeDelete(@PathVariable(name = "recipeId") Long recipeId){
        recipeRepository.deleteById(recipeId);
        return ResponseEntity.ok().body("삭제완료");
    }
}
