package hello.hellospring2.service;

import java.util.List;
import java.util.Optional;

import hello.hellospring2.domain.Member;
import hello.hellospring2.repository.MemberRepository;
import hello.hellospring2.repository.MemoryMemberRepository;

public class MemberService {
	private final MemberRepository memberRepository = new MemoryMemberRepository();

	/**
	 * 회원가입
	 */
	public Long join(Member member) {
		validateDuplicateMember(member); // 중복 회원 검증
		memberRepository.save(member);
		return member.getId();
	}

	private void validateDuplicateMember(Member member) {
		//ifPresent : 만일 값이 있으면 (Optional)
		//orElseGet : 많이 값이 있으면 꺼내고, 없으면 메소드 및 default값을 실행해
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
