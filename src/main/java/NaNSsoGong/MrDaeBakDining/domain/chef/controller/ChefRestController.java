package NaNSsoGong.MrDaeBakDining.domain.chef.controller;

import NaNSsoGong.MrDaeBakDining.domain.PageListRequest;
import NaNSsoGong.MrDaeBakDining.domain.SessionConst;
import NaNSsoGong.MrDaeBakDining.domain.chef.controller.form.*;
import NaNSsoGong.MrDaeBakDining.domain.chef.domain.Chef;
import NaNSsoGong.MrDaeBakDining.domain.chef.repository.ChefRepository;
import NaNSsoGong.MrDaeBakDining.domain.chef.service.ChefService;
import NaNSsoGong.MrDaeBakDining.domain.member.controller.form.LoginRequest;
import NaNSsoGong.MrDaeBakDining.domain.member.controller.form.LoginResponse;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.Order;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderStatus;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @GetMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletRequest request){
        Optional<Chef> loginChef = chefService.login(loginRequest.getLoginId(), loginRequest.getPassword());
        if(loginChef.isEmpty())
            return new LoginResponse(null);
        HttpSession session = request.getSession();
        if(session == null)
            return null;
        session.setMaxInactiveInterval(1800);
        session.setAttribute(SessionConst.LOGIN_CHEF, loginChef);
        return new LoginResponse(session.getId());
    }

    @PostMapping("/logout")
    public LogoutResponse logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session == null)
            return new LogoutResponse(false);
        session.invalidate();
        return new LogoutResponse(true);
    }

    @PostMapping("/sign")
    public SignResponse sign(@RequestBody SignRequest signRequest){
        Boolean loginIdAvailable = chefService.isLoginIdAvailable(signRequest.getLoginId());
        if(!loginIdAvailable)
            return new SignResponse(null);
        Chef chef = new Chef();
        chef.setEnable(true);
        chef.setName(signRequest.getName());
        chef.setLoginId(signRequest.getLoginId());
        chef.setPassword(signRequest.getPassword());
        Chef savedChef = chefRepository.save(chef);
        return new SignResponse(savedChef.getId());
    }

    @PutMapping("/signout")
    public Long signout(@SessionAttribute(name = SessionConst.LOGIN_CHEF, required = false) Chef chef){
        Optional<Chef> foundChef = chefRepository.findById(chef.getId());
        foundChef.get().setEnable(false);
        return 1L;
    }

    @GetMapping("/page-list")
    public Page<Chef> pageList(@RequestBody PageListRequest pageListRequest){
        return chefRepository.findAll(pageListRequest.of());
    }

    @PostMapping("/receive-order")
    public Long receiveOrder(@RequestBody MakeRequest makeRequest){
        Long orderId = makeRequest.getOrderId();
        Order foundOrder = orderRepository.findById(orderId).get();
        if(foundOrder.getOrderStatus().equals(OrderStatus.CANCEL))
            return 1L;
        if(orderService.isMakeAbleOrder(orderId)){
            foundOrder.setOrderStatus(OrderStatus.ACCEPTED);
            orderService.makeDecoration(orderId);
            orderService.makeTableware(orderId);
            orderService.makeFood(orderId);
        }
        else{
            foundOrder.setOrderStatus(OrderStatus.DENIED);
        }
        return 1L;
    }
}
