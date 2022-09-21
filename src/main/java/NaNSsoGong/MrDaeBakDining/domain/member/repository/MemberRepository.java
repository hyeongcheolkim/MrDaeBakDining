package NaNSsoGong.MrDaeBakDining.domain.member.repository;

import NaNSsoGong.MrDaeBakDining.domain.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public List<Member> findAllByLoginId(String loginId);
    Page<Member> findAllByEnable(Boolean enable, Pageable pageable);
}
