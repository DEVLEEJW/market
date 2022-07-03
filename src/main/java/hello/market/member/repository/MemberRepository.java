package hello.market.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hello.market.member.dto.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmail(String email);
	boolean existsByEmail(String email);
}
