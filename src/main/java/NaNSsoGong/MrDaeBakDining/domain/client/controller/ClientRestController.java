package NaNSsoGong.MrDaeBakDining.domain.client.controller;

import NaNSsoGong.MrDaeBakDining.domain.client.ClientGradeConst;
import NaNSsoGong.MrDaeBakDining.domain.client.controller.request.ClientSignRequest;
import NaNSsoGong.MrDaeBakDining.domain.client.controller.response.ClientGradeInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.client.controller.response.ClientInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.ClientGrade;
import NaNSsoGong.MrDaeBakDining.domain.client.repository.ClientRepository;
import NaNSsoGong.MrDaeBakDining.domain.client.service.ClientService;
import NaNSsoGong.MrDaeBakDining.domain.member.service.MemberService;
import NaNSsoGong.MrDaeBakDining.domain.order.SalePolicy;
import NaNSsoGong.MrDaeBakDining.exception.exception.NoExistEntityException;
import NaNSsoGong.MrDaeBakDining.exception.exception.SignFailException;
import NaNSsoGong.MrDaeBakDining.exception.response.BusinessExceptionResponse;
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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static NaNSsoGong.MrDaeBakDining.domain.session.SessionConst.LOGIN_CLIENT;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
@ApiResponse(responseCode = "400", description = "business error", content = @Content(schema = @Schema(implementation = BusinessExceptionResponse.class)))
public class ClientRestController {
    private final ClientRepository clientRepository;
    private final ClientService clientService;
    private final MemberService memberService;

    @Operation(summary = "회원가입")
    @Transactional
    @PostMapping("/sign")
    public ResponseEntity<ClientInfoResponse> sign(@RequestBody @Validated ClientSignRequest clientSignRequest) {
        if (!memberService.isLoginIdExist(clientSignRequest.getLoginId()))
            throw new SignFailException("아이디가 중복입니다");
        if (clientSignRequest.getPersonalInformationCollectionAgreement() && clientSignRequest.getAddress() == null)
            throw new SignFailException("정보제공에 동의했을경우, 주소를 필수로 입력해야 합니다");
        if (clientSignRequest.getPersonalInformationCollectionAgreement() && clientSignRequest.getCardNumber().isEmpty())
            throw new SignFailException("정보제공에 동의했을경우, 카드번호를 필수로 입력해야 합니다");

        Client client = clientSignRequest.toClient();
        Client savedClient = clientRepository.save(client);
        return ResponseEntity.ok().body(new ClientInfoResponse(savedClient));
    }

    @Operation(summary = "회원정보조회 by clientId")
    @GetMapping("/{clientId}")
    public ResponseEntity<ClientInfoResponse> clientInfoByClientId(@PathVariable(name = "clientId") Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 클라이언트입니다");
        });
        return ResponseEntity.ok().body(new ClientInfoResponse(client));
    }

    @Operation(summary = "회원정보조회 by session")
    @GetMapping("")
    public ResponseEntity<ClientInfoResponse> clientInfoByClientSession(
            @Parameter(name = "clientId", hidden = true, allowEmptyValue = true)
            @SessionAttribute(value = LOGIN_CLIENT) Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 클라이언트입니다");
        });
        return ResponseEntity.ok().body(new ClientInfoResponse(client));
    }

    @Operation(summary = "등급정보 조회", description = "등급종류, 등급컷, 할인율이 포함됩니다, cut 이상일경우 해당 등급이 됩니다." +
            "<br>[세일가계산공식]:할인전가격 * (100-saleRate) / 100. 모든값은 정수이며 나누기도 정수나누기입니다")
    @GetMapping("/grade/list")
    public ResponseEntity<List<ClientGradeInfoResponse>> clientGradeList(){
        List<ClientGradeInfoResponse> clientGradeInfoResponseList = Arrays.stream(ClientGrade.values())
                .map(e -> ClientGradeInfoResponse.builder()
                        .clientGradeName(e.name())
                        .cut(ClientGradeConst.getGradeCut(e))
                        .saleRate(SalePolicy.saleRate(e))
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(clientGradeInfoResponseList);
    }

    @Operation(summary = "등급평가", description = "모든 클라이언트에 대해 등급평가를 실시합니다 무거운 연산이니 요청량이 적을때 사용할 것")
    @PatchMapping("/grade")
    public ResponseEntity<String> evaluateClientGrade(){
        clientService.evaluateClientGrade();
        return ResponseEntity.ok().body("등급산정완료");
    }
}
