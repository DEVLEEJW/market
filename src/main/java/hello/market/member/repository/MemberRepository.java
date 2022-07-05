package hello.market.member.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hello.market.member.dto.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmail(String email);
	boolean existsByEmail(String email);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Member m SET m.name = :name, m.sdId = :sdId, m.sggId = :sggId, m.umdId = :umdId WHERE m.id = :id")
	int updateByMember(@Param("name") String name, @Param("sdId") int sdId, @Param("sggId") int sggId, @Param("umdId") int umdId, @Param("id") long id);
}
