package NaNSsoGong.MrDaeBakDining.domain.tableware.service;

import NaNSsoGong.MrDaeBakDining.domain.tableware.domain.Tableware;
import NaNSsoGong.MrDaeBakDining.domain.tableware.repository.TablewareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TablewareService {
    private final TablewareRepository tablewareRepository;

    public Boolean isTablewareNameExist(String name) {
        return tablewareRepository.findAllByName(name).stream()
                .map(Tableware::getEnable)
                .anyMatch(e -> e == true);
    }
}
