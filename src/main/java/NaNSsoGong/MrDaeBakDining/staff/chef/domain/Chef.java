package NaNSsoGong.MrDaeBakDining.staff.chef.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Chef {
    @Id
    @GeneratedValue
    @Column(name="chef_id")
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private Boolean enable;
}
