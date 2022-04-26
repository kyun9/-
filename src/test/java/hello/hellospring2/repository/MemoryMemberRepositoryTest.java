package hello.hellospring2.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import hello.hellospring2.domain.Member;

class MemoryMemberRepositoryTest {
	MemoryMemberRepository repository = new MemoryMemberRepository();

	// @AfterEach : 
	// 테스트케이스 순서는 보장이 안된다. 모든 TEST 순서와 상관없이 독립적으로 설계되어져야한다.
	// 테스트 순서가 의존관계를 가지면 안된다.
	// 테스트가 끝날때마다 깔끔이 지워줘야한다.
	// 테스트 > afterEach() > 테스트 > afterEach() > ...
	@AfterEach
	public void afterEach() {
		repository.clearStore();
	}

	@Test
	public void save() { // SAVE가 잘동작하는지 확인하는 테스트
		// given
		Member member = new Member();
		member.setName("spring");

		// when
		repository.save(member);

		// then
		Member result = repository.findById(member.getId()).get();
		// 단순하게 syso해도된다. : System.out.println("result = " + (result == member));
		assertThat(result).isEqualTo(member); // Assertions junit에서 제공해줌 -> assertThat

		// 실무에서는 빌드 툴이랑 엮어서 빌드할때 테스트케이스를 통과하지못하면 못넘어가게 해버림
	}

	@Test
	public void findByName() { // findByName가 잘동작하는지 확인하는 테스트
		// given
		Member member1 = new Member();
		member1.setName("spring1");
		repository.save(member1);

		Member member2 = new Member();
		member2.setName("spring2");
		repository.save(member2);

		// when
		Member result = repository.findByName("spring1").get();

		// then
		assertThat(result).isEqualTo(member1);
	}

	@Test
	public void findAll() { // findAll가 잘동작하는지 확인하는 테스트
		// given
		Member member1 = new Member();
		member1.setName("spring1");
		repository.save(member1);

		Member member2 = new Member();
		member2.setName("spring2");
		repository.save(member2);

		// when
		List<Member> result = repository.findAll();

		// then
		assertThat(result.size()).isEqualTo(2);
	}
}