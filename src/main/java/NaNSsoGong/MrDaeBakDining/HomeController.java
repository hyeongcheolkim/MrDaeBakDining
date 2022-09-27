package NaNSsoGong.MrDaeBakDining;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final DataInitiator dataInitiator;

    @PostMapping("/data-init")
    void init(){
        dataInitiator.init();
    }
}
