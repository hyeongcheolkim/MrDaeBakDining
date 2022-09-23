package NaNSsoGong.MrDaeBakDining.domain.rider.controller;

import NaNSsoGong.MrDaeBakDining.domain.SessionConst;
import NaNSsoGong.MrDaeBakDining.domain.member.controller.form.LogoutResponse;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.service.OrderService;
import NaNSsoGong.MrDaeBakDining.domain.rider.controller.form.LoginRequest;
import NaNSsoGong.MrDaeBakDining.domain.rider.controller.form.SignRequest;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.Rider;
import NaNSsoGong.MrDaeBakDining.domain.rider.repositroy.RiderRepository;
import NaNSsoGong.MrDaeBakDining.domain.rider.service.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/api/rider")
@RequiredArgsConstructor
public class RiderRestController {
    private final RiderRepository riderRepository;
    private final RiderService riderService;
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @PostMapping("/login")
    public Long login(@RequestBody LoginRequest loginRequest, HttpServletRequest request){
        Optional<Rider> loginRider = riderService.login(loginRequest.getLoginId(), loginRequest.getPassword());
        if(loginRider.isEmpty())
            return 0L;
        HttpSession session = request.getSession(true);
        if(session == null)
            return null;
        session.setMaxInactiveInterval(1800);
        session.setAttribute(SessionConst.LOGIN_RIDER, loginRider);
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
        Boolean loginIdAvailable = riderService.isLoginIdAvailable(signRequest.getLoginId());
        if(!loginIdAvailable)
            return 1L;
        Rider rider = new Rider();
        rider.setName(signRequest.getName());
        rider.setLoginId(signRequest.getLoginId());
        rider.setPassword(signRequest.getPassword());
        rider.setEnable(true);
        Rider save = riderRepository.save(rider);
        return 1L;
    }

    @PutMapping("/signout")
    public String signout(@SessionAttribute(name = SessionConst.LOGIN_RIDER, required = false) Rider rider, HttpSession session) {
        if (rider == null)
            return "세션이존재하지않음";
        Optional<Rider> foundRider = riderRepository.findById(rider.getId());
        if (foundRider.isEmpty())
            return "fail";
        foundRider.get().setEnable(false);
        session.invalidate();
        return "ok";
    }
}
