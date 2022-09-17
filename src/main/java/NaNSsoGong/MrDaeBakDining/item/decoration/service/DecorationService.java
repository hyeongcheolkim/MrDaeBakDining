package NaNSsoGong.MrDaeBakDining.item.decoration.service;

import NaNSsoGong.MrDaeBakDining.item.decoration.domain.Decoration;
import NaNSsoGong.MrDaeBakDining.item.decoration.repository.DecorationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DecorationService {
    private final DecorationRepository decorationRepository;

    public Optional<Decoration> register(Decoration decoration){
        return Optional.ofNullable(decorationRepository.save(decoration));
    }
}
