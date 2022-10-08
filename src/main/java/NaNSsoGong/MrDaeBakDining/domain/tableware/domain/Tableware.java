package NaNSsoGong.MrDaeBakDining.domain.tableware.domain;

import NaNSsoGong.MrDaeBakDining.domain.style.domain.StyleTableware;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Tableware {
    @Id
    @GeneratedValue
    @Column(name = "tableware_id")
    private Long id;
    private String name;
    private Boolean enable;
    @OneToMany(mappedBy = "tableware")
    private List<StyleTableware> styleTablewareList = new ArrayList<>();
}
