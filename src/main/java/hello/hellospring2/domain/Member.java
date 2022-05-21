package hello.hellospring2.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity  // JPA가 관리하는 엔티티 
public class Member {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) // id는 pk 시퀀스이다. DB가 알아서 생성해주는게 IDENTITY이다. 
	private Long id;
	
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}