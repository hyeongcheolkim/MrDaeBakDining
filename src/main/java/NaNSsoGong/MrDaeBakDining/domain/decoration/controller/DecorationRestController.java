package NaNSsoGong.MrDaeBakDining.domain.decoration.controller;

import NaNSsoGong.MrDaeBakDining.domain.DeleteRequest;
import NaNSsoGong.MrDaeBakDining.domain.PageListRequest;
import NaNSsoGong.MrDaeBakDining.domain.decoration.controller.form.SaveRequest;
import NaNSsoGong.MrDaeBakDining.domain.decoration.domain.Decoration;
import NaNSsoGong.MrDaeBakDining.domain.decoration.repository.DecorationRepository;
import NaNSsoGong.MrDaeBakDining.domain.decoration.service.DecorationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/decoration")
@RequiredArgsConstructor
public class DecorationRestController {
    private final DecorationRepository decorationRepository;
    private final DecorationService decorationService;

    @PostMapping("/save")
    public Long save(SaveRequest saveRequest){
        Decoration decoration = new Decoration();
        decoration.setName(saveRequest.getName());
        decoration.setStockQuantity(saveRequest.getStockQuantity());
        decorationRepository.save(decoration);
        return 1L;
    }

    @GetMapping("/page-list")
    public Page<Decoration> pageList(@RequestBody PageListRequest pageListRequest){
        return decorationRepository.findAll(pageListRequest.of());
    }

    @DeleteMapping("/delete")
    public Long delete(DeleteRequest deleteRequest) {
        decorationRepository.deleteById(deleteRequest.getId());
        return 1L;
    }
}
