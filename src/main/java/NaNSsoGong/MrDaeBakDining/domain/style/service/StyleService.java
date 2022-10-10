package NaNSsoGong.MrDaeBakDining.domain.style.service;

import NaNSsoGong.MrDaeBakDining.domain.style.domain.Style;
import NaNSsoGong.MrDaeBakDining.domain.style.domain.StyleTableware;
import NaNSsoGong.MrDaeBakDining.domain.style.dto.StyleDto;
import NaNSsoGong.MrDaeBakDining.domain.style.repository.StyleRepository;
import NaNSsoGong.MrDaeBakDining.domain.tableware.domain.Tableware;
import NaNSsoGong.MrDaeBakDining.domain.tableware.repository.TablewareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StyleService {
    private final StyleRepository styleRepository;
    private final TablewareRepository tablewareRepository;

    public Style makeStyle(StyleDto styleDto) {
        Style style = new Style();

        styleRepository.save(style);
        style.setName(styleDto.getName());
        style.setSellPrice(styleDto.getSellPrice());

        List<StyleTableware> styleTablewareList = makeStyleTablewareList(style, styleDto);
        for (var styleTableware : styleTablewareList)
            style.getStyleTablewareList().add(styleTableware);
        return style;
    }

    public List<Tableware> toTablewareList(Style style) {
        var ret = new ArrayList<Tableware>();
        List<StyleTableware> styleTablewareList = style.getStyleTablewareList();
        for (var styleTableware : styleTablewareList)
            ret.add(styleTableware.getTableware());
        return ret;
    }

    public Boolean isStyleNameExist(String name) {
        return styleRepository.findAllByName(name).stream()
                .map(Style::getEnable)
                .anyMatch(e -> e == true);
    }

    public List<StyleTableware> makeStyleTablewareList(Style style, StyleDto styleDto) {
        var ret = new ArrayList<StyleTableware>();
        List<Long> tablewareIdList = styleDto.getTablewareIdList();
        for (var tablewareId : tablewareIdList) {
            Tableware foundTableware = tablewareRepository.findById(tablewareId).get();
            StyleTableware styleTableware = new StyleTableware();
            styleTableware.setStyle(style);
            styleTableware.setTableware(foundTableware);

            foundTableware.getStyleTablewareList().add(styleTableware);
            ret.add(styleTableware);
        }
        return ret;
    }
}
