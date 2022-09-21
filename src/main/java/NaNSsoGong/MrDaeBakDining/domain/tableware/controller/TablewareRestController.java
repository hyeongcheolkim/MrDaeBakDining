package NaNSsoGong.MrDaeBakDining.domain.tableware.controller;

import NaNSsoGong.MrDaeBakDining.domain.DeleteRequest;
import NaNSsoGong.MrDaeBakDining.domain.PageListRequest;
import NaNSsoGong.MrDaeBakDining.domain.tableware.controller.form.SaveRequest;
import NaNSsoGong.MrDaeBakDining.domain.tableware.domain.Tableware;
import NaNSsoGong.MrDaeBakDining.domain.tableware.repository.TablewareRepository;
import NaNSsoGong.MrDaeBakDining.domain.tableware.service.TablewareService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tableware")
@RequiredArgsConstructor
public class TablewareRestController {
    private final TablewareRepository tablewareRepository;
    private final TablewareService tablewareService;

    @PostMapping("/save")
    public Long save(SaveRequest saveRequest){
        Tableware tableware = new Tableware();
        tableware.setName(saveRequest.getName());
        tableware.setStockQuantity(saveRequest.getStockQuantity());
        return 1L;
    }

    @GetMapping("/page-list")
    public Page<Tableware> pageList(@RequestBody PageListRequest pageListRequest){
        return tablewareRepository.findAll(pageListRequest.of());
    }

    @DeleteMapping("/delete")
    public Long delete(@RequestBody DeleteRequest deleteRequest){
        tablewareRepository.deleteById(deleteRequest.getId());
        return 1L;
    }
}
