package NaNSsoGong.MrDaeBakDining.domain.style.service;

import NaNSsoGong.MrDaeBakDining.domain.style.domain.Style;
import NaNSsoGong.MrDaeBakDining.domain.style.domain.StyleTableware;
import NaNSsoGong.MrDaeBakDining.domain.style.dto.StyleDto;
import NaNSsoGong.MrDaeBakDining.domain.style.repository.StyleRepository;
import NaNSsoGong.MrDaeBakDining.domain.tableware.domain.Tableware;
import NaNSsoGong.MrDaeBakDining.domain.tableware.repository.TablewareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StyleService {
    private final StyleRepository styleRepository;
    private final TablewareRepository tablewareRepository;

    public Style makeStyle(StyleDto styleDto) {
        Style style = new Style();
        styleRepository.save(style);
        style.setName(styleDto.getName());
        List<StyleTableware> styleTablewareList = makeStyleItemList(style, styleDto);
        for (var styleItem : styleTablewareList)
            style.getStyleTablewareList().add(styleItem);
        return style;
    }

    public List<Tableware> toTablewareList(Long styleId) {
        var ret = new ArrayList<Tableware>();
        Optional<Style> foundStyle = styleRepository.findById(styleId);
        if (foundStyle.isEmpty())
            return ret;
        List<StyleTableware> styleTablewareList = foundStyle.get().getStyleTablewareList();
        for (var styleTableware : styleTablewareList)
            ret.add(styleTableware.getTableware());
        return ret;
    }

    private List<StyleTableware> makeStyleItemList(Style style, StyleDto styleDto) {
        var ret = new ArrayList<StyleTableware>();
        List<Long> tablewareIdList = styleDto.getTablewareIdList();
        for (var tablewareId : tablewareIdList) {
            StyleTableware styleTableware = new StyleTableware();
            styleTableware.setStyle(style);
            styleTableware.setTableware(tablewareRepository.findById(tablewareId).get());
            ret.add(styleTableware);
        }
        return ret;
    }
}
