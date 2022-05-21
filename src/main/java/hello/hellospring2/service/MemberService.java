package hello.hellospring2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import hello.hellospring2.domain.Member;
import hello.hellospring2.repository.MemberRepository;

@Transactional
public class MemberService {
//	private final MemberRepository memberRepository = new MemoryMemberRepository();
	private final MemberRepository memberRepository;


	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;                //memberService를 외부에서 넣어주는 것으로 바꾸기  : 이전에는 테스트하려면 new로 새로 만들어서 서로 instance가 달랐어. 그렇게 해서 같은 인스턴스를 공유할 수 있어
 	}

	/**
	 * 회원가입
	 */
	public Long join(Member member) {
		validateDuplicateMember(member); // 중복 회원 검증
		memberRepository.save(member);
		return member.getId();
	}

	private void validateDuplicateMember(Member member) {
		// ifPresent : 만일 값이 있으면 (Optional)
		// orElseGet : 많이 값이 있으면 꺼내고, 없으면 메소드 및 default값을 실행해
		memberRepository.findByName(member.getName()).ifPresent(m -> {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		});
	}

	/**
	 * 전체 회원 조회
	 */
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	public Optional<Member> findOne(Long memberId) {
		return memberRepository.findById(memberId);
	}
}
