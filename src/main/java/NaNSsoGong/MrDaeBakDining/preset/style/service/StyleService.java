package NaNSsoGong.MrDaeBakDining.preset.style.service;

import NaNSsoGong.MrDaeBakDining.item.tableware.domain.Tableware;
import NaNSsoGong.MrDaeBakDining.item.tableware.repository.TablewareRepository;
import NaNSsoGong.MrDaeBakDining.preset.style.domain.Style;
import NaNSsoGong.MrDaeBakDining.preset.style.domain.StyleTableware;
import NaNSsoGong.MrDaeBakDining.preset.style.dto.StyleDTO;
import NaNSsoGong.MrDaeBakDining.preset.style.repository.StyleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class StyleService {
    private final StyleRepository styleRepository;
    private final TablewareRepository tablewareRepository;

    public Optional<Style> register(StyleDTO styleDTO){
        Style style = new Style();
        style.setName(styleDTO.getName());
        style.setStyleTablewareList(makeStyleTableware(style, styleDTO));
        Style savedStyle = styleRepository.save(style);
        return Optional.of(savedStyle);
    }

    public Map<Long, Integer> TablewareMap(Style style){
        Map<Long, Integer> ret = new ConcurrentHashMap<>();
        List<StyleTableware> styleTablewareList = style.getStyleTablewareList();
        for(var styleTableware : styleTablewareList){
            Long tablewareId = styleTableware.getTableware().getId();
            Integer tablewareQuantity = styleTableware.getTablewareQuantity();
            ret.put(tablewareId, tablewareQuantity);
        }
        return ret;
    }

    private List<StyleTableware> makeStyleTableware(Style style, StyleDTO styleDTO){
        var ret = new ArrayList<StyleTableware>();
        Map<Long, Integer> tablewareIdAndQuantity = styleDTO.getTablewareIdAndQuantity();
        for(var tablewareId : tablewareIdAndQuantity.keySet()){
            Optional<Tableware> foundTableware = tablewareRepository.findById(tablewareId);
            if(foundTableware.isEmpty())
                continue;
            StyleTableware styleTableware = new StyleTableware();
            styleTableware.setStyle(style);
            styleTableware.setTableware(foundTableware.get());
            styleTableware.setTablewareQuantity(tablewareIdAndQuantity.get(tablewareId));
            ret.add(styleTableware);
        }
        return ret;
    }
}
