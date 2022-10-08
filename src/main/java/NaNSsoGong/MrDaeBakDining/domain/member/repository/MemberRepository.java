package NaNSsoGong.MrDaeBakDining.domain.member.repository;

import NaNSsoGong.MrDaeBakDining.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAllByLoginId(String loginId);
}
