package NaNSsoGong.MrDaeBakDining.domain.tableware.service;

import NaNSsoGong.MrDaeBakDining.domain.tableware.domain.Tableware;
import NaNSsoGong.MrDaeBakDining.domain.tableware.repository.TablewareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TablewareService {
    private final TablewareRepository tablewareRepository;

    public Optional<Tableware> register(Tableware tableware){
        return Optional.ofNullable(tablewareRepository.save(tableware));
    }
}
