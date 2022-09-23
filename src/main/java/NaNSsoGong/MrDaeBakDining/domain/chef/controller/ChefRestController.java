package NaNSsoGong.MrDaeBakDining.domain.chef.controller;

import NaNSsoGong.MrDaeBakDining.domain.SessionConst;
import NaNSsoGong.MrDaeBakDining.domain.chef.controller.form.LoginRequest;
import NaNSsoGong.MrDaeBakDining.domain.chef.controller.form.SignRequest;
import NaNSsoGong.MrDaeBakDining.domain.chef.domain.Chef;
import NaNSsoGong.MrDaeBakDining.domain.chef.repository.ChefRepository;
import NaNSsoGong.MrDaeBakDining.domain.chef.service.ChefService;
import NaNSsoGong.MrDaeBakDining.domain.member.controller.form.LogoutResponse;
import NaNSsoGong.MrDaeBakDining.domain.member.domain.Member;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/api/chef")
@RequiredArgsConstructor
public class ChefRestController {
    private final ChefRepository chefRepository;
    private final ChefService chefService;
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @PostMapping("/login")
    public Long login(@RequestBody LoginRequest loginRequest, HttpServletRequest request){
        Optional<Chef> loginChef = chefService.login(loginRequest.getLoginId(), loginRequest.getPassword());
        if(loginChef.isEmpty())
            return 0L;
        HttpSession session = request.getSession(true);
        if(session == null)
            return null;
        session.setMaxInactiveInterval(1800);
        session.setAttribute(SessionConst.LOGIN_CHEF, loginChef);
        return 1L;
    }

    @PostMapping("/logout")
    public LogoutResponse logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null)
            return new LogoutResponse(false);
        session.invalidate();
        return new LogoutResponse(true);
    }

    @PostMapping("/sign")
    public Long sign(@RequestBody SignRequest signRequest){
        Boolean loginIdAvailable = chefService.isLoginIdAvailable(signRequest.getLoginId());
        if(!loginIdAvailable)
            return 1L;
        Chef chef = new Chef();
        chef.setName(signRequest.getName());
        chef.setLoginId(signRequest.getLoginId());
        chef.setPassword(signRequest.getPassword());
        chef.setEnable(true);
        Chef save = chefRepository.save(chef);
        return 1L;
    }

    @PutMapping("/signout")
    public String signout(@SessionAttribute(name = SessionConst.LOGIN_CHEF, required = false) Chef chef, HttpSession session) {
        if (chef == null)
            return "세션이존재하지않음";
        Optional<Chef> foundChef = chefRepository.findById(chef.getId());
        if (foundChef.isEmpty())
            return "fail";
        foundChef.get().setEnable(false);
        session.invalidate();
        return "ok";
    }
}
