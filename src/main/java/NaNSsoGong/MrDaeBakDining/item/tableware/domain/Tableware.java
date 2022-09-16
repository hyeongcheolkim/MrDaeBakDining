package NaNSsoGong.MrDaeBakDining.item.tableware.domain;

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
public class Tableware{
    @Id
    @GeneratedValue
    @Column(name = "tableware_id")
    private Long id;
    private String name;
    private Integer stockQuantity;
}
