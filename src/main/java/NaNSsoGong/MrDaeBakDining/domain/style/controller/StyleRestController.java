package NaNSsoGong.MrDaeBakDining.domain.style.controller;

import NaNSsoGong.MrDaeBakDining.domain.DeleteRequest;
import NaNSsoGong.MrDaeBakDining.domain.PageListRequest;
import NaNSsoGong.MrDaeBakDining.domain.style.controller.form.SaveRequest;
import NaNSsoGong.MrDaeBakDining.domain.style.domain.Style;
import NaNSsoGong.MrDaeBakDining.domain.style.dto.StyleDto;
import NaNSsoGong.MrDaeBakDining.domain.style.repository.StyleRepository;
import NaNSsoGong.MrDaeBakDining.domain.style.service.StyleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/style")
@RequiredArgsConstructor
public class StyleRestController {
    private final StyleRepository styleRepository;
    private final StyleService styleService;

    @PostMapping("/save")
    public Long save(SaveRequest saveRequest){
        StyleDto styleDto = new StyleDto();
        styleDto.setName(saveRequest.getName());
        styleDto.setTablewareIdAndQuantity(saveRequest.getTablewareIdAndQuantity());
        Optional<Style> style = styleService.makeStyle(styleDto);
        return 1L;
    }

    @GetMapping("page-list")
    public Page<Style> pageList(@RequestBody PageListRequest pageListRequest){
        return styleRepository.findAll(pageListRequest.of());
    }

    @DeleteMapping("/delete")
    public Long delete(DeleteRequest deleteRequest){
        styleRepository.deleteById(deleteRequest.getId());
        return 1L;
    }
}

