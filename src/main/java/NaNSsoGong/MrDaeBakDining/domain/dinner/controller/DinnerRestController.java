package NaNSsoGong.MrDaeBakDining.domain.dinner.controller;

import NaNSsoGong.MrDaeBakDining.domain.DeleteRequest;
import NaNSsoGong.MrDaeBakDining.domain.PageListRequest;
import NaNSsoGong.MrDaeBakDining.domain.dinner.controller.form.SaveRequest;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.Dinner;
import NaNSsoGong.MrDaeBakDining.domain.dinner.dto.DinnerDto;
import NaNSsoGong.MrDaeBakDining.domain.dinner.repository.DinnerRepository;
import NaNSsoGong.MrDaeBakDining.domain.dinner.service.DinnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/dinner")
@RequiredArgsConstructor
public class DinnerRestController {
    private final DinnerRepository dinnerRepository;
    private final DinnerService dinnerService;

    @GetMapping("/page-list")
    public Page<Dinner> pageList(@RequestBody PageListRequest pageListRequest){
        return dinnerRepository.findAll(pageListRequest.of());
    }

    @DeleteMapping("/delete")
    public Long delete(DeleteRequest deleteRequest){
        dinnerRepository.deleteById(deleteRequest.getId());
        return 1L;
    }
}
