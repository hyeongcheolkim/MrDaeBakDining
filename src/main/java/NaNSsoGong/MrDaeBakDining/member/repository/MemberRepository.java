package NaNSsoGong.MrDaeBakDining.member.repository;

import NaNSsoGong.MrDaeBakDining.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
