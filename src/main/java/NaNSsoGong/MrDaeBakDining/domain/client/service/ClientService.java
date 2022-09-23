package NaNSsoGong.MrDaeBakDining.domain.client.service;

import NaNSsoGong.MrDaeBakDining.domain.member.service.MemberService;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {
    private final ClientRepository clientRepository;
    private final MemberService memberService;

    public Optional<Client> sign(Client client) {
        if (!memberService.isLoginIdAvailable(client.getLoginId()))
            return Optional.empty();
        Client savedClient = clientRepository.save(client);
        return Optional.of(savedClient);
    }
}
