package NaNSsoGong.MrDaeBakDining.domain.recipe.controller;

import NaNSsoGong.MrDaeBakDining.domain.PageListRequest;
import NaNSsoGong.MrDaeBakDining.domain.recipe.controller.form.MakeRecipeRequest;
import NaNSsoGong.MrDaeBakDining.domain.recipe.domain.Recipe;
import NaNSsoGong.MrDaeBakDining.domain.recipe.repository.RecipeRepository;
import NaNSsoGong.MrDaeBakDining.domain.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
public class RecipeRestController {
    private final RecipeRepository recipeRepository;
    private final RecipeService recipeService;

    @PostMapping("/make-recipe")
    public Long makeRecipe(@RequestBody MakeRecipeRequest makeRecipeRequest){
        Optional<Recipe> recipe = recipeService.makeRecipe(
                makeRecipeRequest.getFoodId(),
                makeRecipeRequest.getIngredientId(),
                makeRecipeRequest.getIngredientQuantity()
        );
        return 1L;
    }

    @GetMapping("/page-list")
    public Page<Recipe> pageList(@RequestBody PageListRequest pageListRequest){
        return recipeRepository.findAll(pageListRequest.of());
    }
}
