package hello.hellospring2.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import hello.hellospring2.domain.Member;

public class JpaMemberRepository implements MemberRepository {
	// JPA는 EntityManager를 통해 동작한다.
	// 스프링 부트가 data-JPA를 gradle에 선언해주면 EntityManager를 자동으로 만들어준다.
	private final EntityManager em;

	public JpaMemberRepository(EntityManager em) {
		this.em = em;
	}

	public Member save(Member member) {
		em.persist(member);
		return member;
	}

	public Optional<Member> findById(Long id) {
		Member member = em.find(Member.class, id);
		return Optional.ofNullable(member);
	}

	//PK기반이 아닌, 단건이 아닌것은
	// JPQL 활용한다.
	public List<Member> findAll() {
		// JPQL이란 쿼리언어 
		// 쿼리를 테이블 대상이 아닌 테이블대상으로 넘긴다.
		return em.createQuery("select m from Member m", Member.class).getResultList();  //객체자체를 Select하는거
	}

	public Optional<Member> findByName(String name) {
		// JPQL이란 쿼리언어 
		// 쿼리를 테이블 대상이 아닌 테이블대상으로 넘긴다.
		List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
				.setParameter("name", name).getResultList();
		return result.stream().findAny();
	}
}