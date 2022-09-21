package NaNSsoGong.MrDaeBakDining.domain.rider.repositroy;

import NaNSsoGong.MrDaeBakDining.domain.member.domain.Member;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RiderRepository extends JpaRepository<Rider,Long> {
    public List<Member> findAllByLoginId(String loginId);
}
