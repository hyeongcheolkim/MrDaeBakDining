package NaNSsoGong.MrDaeBakDining.domain.tableware.domain;

import NaNSsoGong.MrDaeBakDining.domain.item.domain.Item;
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
public class Tableware extends Item {
    private Integer stockQuantity;
}
