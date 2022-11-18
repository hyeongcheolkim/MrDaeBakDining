package NaNSsoGong.MrDaeBakDining.domain.chef.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ChefSign {
    @Id
    @GeneratedValue
    @Column(name = "chef_sign_id")
    private Long id;
    private String name;
    private String loginId;
    private String password;

    public Chef toChef(){
        Chef chef = new Chef();
        chef.setName(this.name);
        chef.setLoginId(this.loginId);
        chef.setPassword(this.password);
        return chef;
    }
}
