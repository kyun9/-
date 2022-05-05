package hello.hellospring2.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import hello.hellospring2.domain.Member;

/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */
public class MemoryMemberRepository implements MemberRepository {
	private static Map<Long, Member> store = new HashMap<>(); //동시성 HashMap -> ConcurrentHashMap 사용
	private static long sequence = 0L; //동시성 long -> AtomicLong 사용 ,  0L의미 : 0은 묵시적으로 int값을 의미하고 0L은 명시적으로 long형의 0을 의미함 = long형 변수와 값 비교할 떄 0L이 좋음

	@Override
	public Member save(Member member) {
		member.setId(++sequence);
		store.put(member.getId(), member);
		return member;
	}

	@Override
	public Optional<Member> findById(Long id) {
		// 과거의 코드 : reaturn store.get(id);
		return Optional.ofNullable(store.get(id));  // java 8 : Optional : 결과가 Null일때, Null이여도 ofNullable로 감싸서 반환하여 Client에서 처리가능
	}

	@Override
	public List<Member> findAll() {
		return new ArrayList<>(store.values()); // .values() : Map을 모두 반환한다.
	}

	@Override
	public Optional<Member> findByName(String name) {
		// java 8 : 람다식
		return store.values().stream()    // loop 돌리기
							.filter(member -> member.getName().equals(name)) //필터로 값이 같은 경우만 찾아서 반환 (다음단계로 넘어감)
							.findAny();  // 하나를 찾으면 바로 넘어가버린다.  
		// 반환값이 없으면 Optional에 Null을 포함하여 넘긴다.
	}

	public void clearStore() {
		store.clear();
	}
}