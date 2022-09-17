package NaNSsoGong.MrDaeBakDining.member.repository;

import NaNSsoGong.MrDaeBakDining.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public List<Member> findAllByLoginId(String loginId);
}
