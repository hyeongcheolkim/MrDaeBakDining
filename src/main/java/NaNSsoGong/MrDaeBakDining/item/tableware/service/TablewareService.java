package NaNSsoGong.MrDaeBakDining.item.tableware.service;

import NaNSsoGong.MrDaeBakDining.item.tableware.domain.Tableware;
import NaNSsoGong.MrDaeBakDining.item.tableware.repository.TablewareRepository;
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
