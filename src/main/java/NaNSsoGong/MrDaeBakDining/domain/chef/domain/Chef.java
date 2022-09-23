package NaNSsoGong.MrDaeBakDining.domain.chef.domain;

import NaNSsoGong.MrDaeBakDining.domain.member.domain.Member;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Chef extends Member {
}
