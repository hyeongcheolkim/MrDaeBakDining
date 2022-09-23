package NaNSsoGong.MrDaeBakDining.domain.client.controller;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import NaNSsoGong.MrDaeBakDining.domain.member.service.MemberService;
import NaNSsoGong.MrDaeBakDining.domain.SessionConst;
import NaNSsoGong.MrDaeBakDining.domain.client.controller.form.*;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.ClientGrade;
import NaNSsoGong.MrDaeBakDining.domain.client.repository.ClientRepository;
import NaNSsoGong.MrDaeBakDining.domain.client.service.ClientService;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/client")
@RequiredArgsConstructor
public class ClientRestController {
    private final ClientService clientService;
    private final ClientRepository clientRepository;
    private final MemberService memberService;

    @PostMapping("/sign")
    public SignResponse sign(@RequestBody SignRequest signRequest) {
        Boolean loginIdAvailable = memberService.isLoginIdAvailable(signRequest.getLoginId());
        if (!loginIdAvailable)
            return new SignResponse(false);
        Client client = new Client();
        client.setName(signRequest.getName());
        client.setLoginId(signRequest.getLoginId());
        client.setPassword(signRequest.getPassword());
        client.setCardNumber(signRequest.getCardNumber());
        client.setAddress(new Address(
                signRequest.getAddress().getCity(),
                signRequest.getAddress().getStreet(),
                signRequest.getAddress().getCity()
                ));
        client.setClientGrade(ClientGrade.BRONZE);
        client.setEnable(true);
        clientRepository.save(client);
        return new SignResponse(true);
    }

    @PutMapping("/signout")
    public String signout(@SessionAttribute(name = SessionConst.LOGIN_CLIENT, required = false) Client client, HttpSession session) {
        if (client == null)
            return "세션이존재하지않음";
        Optional<Client> foundClient = clientRepository.findById(client.getId());
        if (foundClient.isEmpty())
            return "fail";
        foundClient.get().setEnable(false);
        session.invalidate();
        return "ok";
    }

    @GetMapping("/info")
    public InfoResponse info(@SessionAttribute(name = SessionConst.LOGIN_CLIENT, required = false) Client client) {
        return new InfoResponse(client);
    }

    @GetMapping("/order-list")
    public List<Order> orderList() {
        return new ArrayList<>();
    }

    @GetMapping("/page-list")
    public Page<Client> pageList(@RequestBody ClientPageListRequest clientPageListRequest) {
        return clientRepository.findAllByEnable(clientPageListRequest.getEnable(), clientPageListRequest.of());
    }
}
