package NaNSsoGong.MrDaeBakDining.domain.ingredient.controller;

import NaNSsoGong.MrDaeBakDining.domain.ingredient.controller.request.IngredientCreateRequest;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.controller.request.IngredientUpdateRequest;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.controller.response.IngredientCreateResponse;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.controller.response.IngredientInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.controller.response.IngredientUpdateResponse;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.domain.Ingredient;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.repository.IngredientRepository;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.service.IngredientService;
import NaNSsoGong.MrDaeBakDining.error.exception.MinusQuantityException;
import NaNSsoGong.MrDaeBakDining.error.exception.NoExistEntityException;
import NaNSsoGong.MrDaeBakDining.error.response.BusinessErrorResponse;
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

import static NaNSsoGong.MrDaeBakDining.domain.ResponseConst.DISABLE_COMPLETE;

@RestController
@RequestMapping("/api/ingredient")
@RequiredArgsConstructor
@ApiResponse(responseCode = "400", description = "business error", content = @Content(schema = @Schema(implementation = BusinessErrorResponse.class)))
public class IngredientRestController {
    private final IngredientService ingredientService;
    private final IngredientRepository ingredientRepository;

    @Operation(summary = "재료조회")
    @GetMapping("/{ingredientId}")
    public ResponseEntity<IngredientInfoResponse> ingredientInfo(@PathVariable(name = "ingredientId") Long ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 재료입니다");
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

    @Operation(summary = "새로운 재료생성", description = "기존에 존재하던 재료와 이름이 같다면, 그 재료에 수량을 더합니다. 이경우 update=true입니다")
    @Transactional
    @PostMapping("")
    public ResponseEntity<IngredientCreateResponse> ingredientCreate(@RequestBody @Validated IngredientCreateRequest ingredientCreateRequest) {
        Optional<Ingredient> foundIngredient = ingredientRepository.findByName(ingredientCreateRequest.getName());

        Ingredient ingredient;
        if (foundIngredient.isPresent() && foundIngredient.get().getEnable()) {
            IngredientUpdateRequest ingredientUpdateRequest = new IngredientUpdateRequest();
            ingredientUpdateRequest.setQuantityDiff(ingredientCreateRequest.getStockQuantity());
            ingredientUpdate(foundIngredient.get().getId(), ingredientUpdateRequest);
            return ResponseEntity.ok().body(new IngredientCreateResponse(foundIngredient.get().getId(), true));
        }
        else if(foundIngredient.isPresent() && !foundIngredient.get().getEnable())
            ingredient = foundIngredient.get();
        else
            ingredient = new Ingredient();

        ingredient.setEnable(true);
        ingredient.setName(ingredientCreateRequest.getName());
        ingredient.setStockQuantity(ingredientCreateRequest.getStockQuantity());
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return ResponseEntity.ok().body(new IngredientCreateResponse(savedIngredient.getId(), false));
    }

    @Operation(summary = "수량 증감", description = "재고량을 0미만으로 만들 수 없다면 요청은 무시되고, Exception이 발생합니다")
    @Transactional
    @PatchMapping("/{ingredientId}")
    public ResponseEntity<IngredientUpdateResponse> ingredientUpdate(@PathVariable(name = "ingredientId") Long ingredientId,
                                                                     @RequestBody @Validated IngredientUpdateRequest ingredientUpdateRequest) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 재료입니다");
        });
        Integer newQuantity = ingredient.getStockQuantity() + ingredientUpdateRequest.getQuantityDiff();
        if (newQuantity < 0)
            throw new MinusQuantityException("재료 수량은 0보다 작을 수 없습니다");
        ingredient.setStockQuantity(newQuantity);
        return ResponseEntity.ok().body(new IngredientUpdateResponse(ingredient.getId(), ingredient.getStockQuantity()));
    }

    @Operation(summary = "재료 비활성화", description = "enable = false")
    @PatchMapping("/disable/{ingredientId}")
    public ResponseEntity<String> ingredientDisable(@PathVariable(value = "ingredientId") Long ingredientId){
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 재료입니다");
        });
        ingredient.setEnable(false);
        return ResponseEntity.ok().body(DISABLE_COMPLETE);
    }
}
