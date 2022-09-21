package NaNSsoGong.MrDaeBakDining.domain.ingredient.controller;

import NaNSsoGong.MrDaeBakDining.domain.PageListRequest;
import NaNSsoGong.MrDaeBakDining.domain.DeleteRequest;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.controller.form.SaveRequest;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.controller.form.SaveResponse;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.domain.Ingredient;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.repository.IngredientRepository;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/ingredient")
@RequiredArgsConstructor
public class IngredientRestController {
    private final IngredientService ingredientService;
    private final IngredientRepository ingredientRepository;

    @PostMapping("/save")
    public SaveResponse save(@RequestBody SaveRequest saveRequest){
        Ingredient ingredient = new Ingredient();
        ingredient.setName(saveRequest.getName());
        ingredient.setStockQuantity(saveRequest.getQuantity());
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return new SaveResponse(savedIngredient.getId());
    }

    @DeleteMapping("/delete")
    public Long delete(@RequestBody DeleteRequest deleteRequest){
        ingredientRepository.deleteById(deleteRequest.getId());
        return 1L;
    }

    @GetMapping("/page-list")
    public Page<Ingredient> pageList(@RequestBody PageListRequest pageListRequest){
        return ingredientRepository.findAll(pageListRequest.of());
    }
}
