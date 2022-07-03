package hello.market.common.address.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hello.market.common.address.dto.entity.AddressSd;

public interface AddressSdRepository extends JpaRepository<AddressSd, Long> {
	
	List<AddressSd> findAll();
}