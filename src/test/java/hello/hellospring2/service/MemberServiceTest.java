package hello.hellospring2.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hello.hellospring2.domain.Member;
import hello.hellospring2.repository.MemoryMemberRepository;

class MemberServiceTest {

	MemberService memberService;
	MemoryMemberRepository memberRepository;

	@BeforeEach
	public void beforeEach() {
		memberRepository = new MemoryMemberRepository(); // 1. memberRepository생성하고
		memberService = new MemberService(memberRepository); // 2. memberService에 memberRepository 넣어준다. 그러면 같은 memberRepository를 사용한다.
		// 2번 처럼 memberService입장에서 외부에서 memberRepository넣어주는 것을 DI라고 한다.
	}

	@AfterEach
	public void afterEach() {
		memberRepository.clearStore();
	}

	@Test
	public void 회원가입() throws Exception {
		// Given = 주어질때
		Member member = new Member();
		member.setName("hello");

		// When = 실행했을때
		Long saveId = memberService.join(member);

		// Then = 이 결과가 나와야된다.
		Member findMember = memberRepository.findById(saveId).get();
		assertEquals(member.getName(), findMember.getName());
	}

	@Test
	public void 중복_회원_예외() throws Exception {
		// Given
		Member member1 = new Member();
		member1.setName("spring");

		Member member2 = new Member();
		member2.setName("spring");

		// When
		/*
		 * memberService.join(member1); try { memberService.join(member2); fail(); }
		 * catch (IllegalStateException e) {
		 * assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다."); }
		 */

		IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));// 예외가
																												// 발생해야
																												// 한다.
		assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
	}
}