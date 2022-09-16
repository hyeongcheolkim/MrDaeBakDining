package NaNSsoGong.MrDaeBakDining.preset.style.domain;

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
public class Style {
    @Id
    @GeneratedValue
    @Column(name="style_id")
    private Long id;
    private String name;
    @OneToMany(mappedBy = "style")
    private List<StyleTableware> styleTablewareList = new ArrayList<>();
}
