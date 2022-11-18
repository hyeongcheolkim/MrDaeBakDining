package NaNSsoGong.MrDaeBakDining.domain.member.service;

import NaNSsoGong.MrDaeBakDining.domain.chef.repository.ChefSignRepository;
import NaNSsoGong.MrDaeBakDining.domain.member.domain.Member;
import NaNSsoGong.MrDaeBakDining.domain.member.repository.MemberRepository;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.RiderSign;
import NaNSsoGong.MrDaeBakDining.domain.rider.repositroy.RiderSignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final ChefSignRepository chefSignRepository;
    private final RiderSignRepository riderSignRepository;

    public Boolean isLoginIdExist(String loginId) {
        boolean memberExist = memberRepository.findAllByLoginId(loginId).stream()
                .map(Member::getEnable)
                .anyMatch(e -> e == true);
        boolean chefSignExist = chefSignRepository.existsByLoginId(loginId);
        boolean riderSignExist = riderSignRepository.existsByLoginId(loginId);

        return memberExist || chefSignExist || riderSignExist;
    }

    public Optional<Member> login(String loginId, String password) {
        List<Member> memberList = memberRepository.findAllByLoginId(loginId);
        for (var member : memberList)
            if (member.getEnable() && member.getPassword().equals(password))
                return Optional.of(member);
        return Optional.empty();
    }
}
