package NaNSsoGong.MrDaeBakDining.domain.client.controller;

import NaNSsoGong.MrDaeBakDining.domain.client.controller.request.ClientSignRequest;
import NaNSsoGong.MrDaeBakDining.domain.client.controller.response.ClientInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.client.controller.response.ClientSignResponse;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.client.repository.ClientRepository;
import NaNSsoGong.MrDaeBakDining.domain.member.service.MemberService;
import NaNSsoGong.MrDaeBakDining.domain.order.service.OrderService;
import NaNSsoGong.MrDaeBakDining.error.exception.NoExistEntityException;
import NaNSsoGong.MrDaeBakDining.error.exception.SignFailException;
import NaNSsoGong.MrDaeBakDining.error.response.BusinessErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Optional;

import static NaNSsoGong.MrDaeBakDining.domain.session.SessionConst.LOGIN_CLIENT;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
@ApiResponse(responseCode = "400", description = "business error", content = @Content(schema = @Schema(implementation = BusinessErrorResponse.class)))
public class ClientRestController {
    private final ClientRepository clientRepository;
    private final MemberService memberService;

    @Operation(summary = "회원가입")
    @Transactional
    @PostMapping("/sign")
    public ResponseEntity<ClientSignResponse> sign(@RequestBody @Validated ClientSignRequest clientSignRequest) {
        if (!memberService.isLoginIdAvailable(clientSignRequest.getLoginId()))
            throw new SignFailException("아이디가 중복입니다");

        Client client = clientSignRequest.toClient();
        Client savedClient = clientRepository.save(client);
        return ResponseEntity.ok().body(new ClientSignResponse(savedClient.getId()));
    }

    @Operation(summary = "회원정보조회 by clientId")
    @GetMapping("/{clientId}")
    public ResponseEntity<ClientInfoResponse> clientInfoByClientId(@PathVariable(name = "clientId") Long clientId){
        Optional<Client> foundClient = clientRepository.findById(clientId);
        if(foundClient.isEmpty())
            throw new NoExistEntityException("존재하지 않는 클라이언트입니다");
        return ResponseEntity.ok().body(new ClientInfoResponse(foundClient.get()));
    }

    @Operation(summary = "회원정보조회 by session")
    @GetMapping("")
    public ResponseEntity<ClientInfoResponse> clientInfoByClientSession(
            @Parameter(name = "clientId", hidden = true, allowEmptyValue = true)
            @SessionAttribute(value = LOGIN_CLIENT) Long clientId){
        Optional<Client> foundClient = clientRepository.findById(clientId);
        if(foundClient.isEmpty())
            throw new NoExistEntityException("존재하지 않는 클라이언트입니다");
        return ResponseEntity.ok().body(new ClientInfoResponse(foundClient.get()));
    }

}
