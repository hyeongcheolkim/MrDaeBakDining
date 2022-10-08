package NaNSsoGong.MrDaeBakDining.domain.dinner.domain;

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
public class Dinner {
    @Id
    @GeneratedValue
    @Column(name="dinner_id")
    private Long id;
    @Column(unique = true)
    private String name;
    @Lob
    private String description;
    private Boolean enable = true;
    private Boolean orderable;
    @OneToMany(mappedBy = "dinner", cascade = CascadeType.ALL, orphanRemoval = true)
    List<DinnerFood> dinnerFoodList = new ArrayList<>();
    @OneToMany(mappedBy = "dinner", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ExcludedStyle> excludedStyleList = new ArrayList<>();
}
