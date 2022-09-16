package NaNSsoGong.MrDaeBakDining.item.decoration.domain;

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
public class Decoration {
    @Id
    @GeneratedValue
    @Column(name = "decoration_id")
    private Long id;
    private String name;
    private Integer stockQuantity;
}
