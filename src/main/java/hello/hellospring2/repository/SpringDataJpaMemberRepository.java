package hello.hellospring2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hello.hellospring2.domain.Member;

//JpaRepository를 상속받으면 스프링데이터JPA가 인터페이스에 대한 구현체를 만들어낸다.
//만들어낸 후, 스프링 빈에 등록해둔다. 
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
	
	// 네이밍 규칙에 따라 쿼리를 짜준다.
	// JPQL : select m from Member m where m.name = ?
	Optional<Member> findByName(String name);
	
}