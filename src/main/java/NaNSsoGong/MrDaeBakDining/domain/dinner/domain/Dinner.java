package NaNSsoGong.MrDaeBakDining.domain.dinner.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private String name;
    @Lob
    private String description;
    private String imagePath;
    private String imageAbsolutePath;
    private Boolean enable = true;
    private Boolean orderable = true;
    @OneToMany(mappedBy = "dinner", cascade = CascadeType.ALL, orphanRemoval = true)
    List<DinnerFood> dinnerFoodList = new ArrayList<>();
    @OneToMany(mappedBy = "dinner", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ExcludedStyle> excludedStyleList = new ArrayList<>();

}
