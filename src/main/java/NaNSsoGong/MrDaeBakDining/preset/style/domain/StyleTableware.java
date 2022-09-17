package NaNSsoGong.MrDaeBakDining.preset.style.domain;

import NaNSsoGong.MrDaeBakDining.item.tableware.domain.Tableware;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class StyleTableware {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="style_id")
    private Style style;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Tableware tableware;
    private Integer tablewareQuantity;
}
