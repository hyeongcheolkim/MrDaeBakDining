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
    @ManyToOne
    @JoinColumn(name="style_id")
    private Style style;
    @ManyToOne
    @JoinColumn(name="item_id")
    private Tableware tableware;
    private Integer tablewareQuantity;
}
