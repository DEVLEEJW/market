package hello.market.common.address.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hello.market.common.address.dto.entity.AddressSgg;

public interface AddressSggRepository extends JpaRepository<AddressSgg, Long> {
	
	List<AddressSgg> findAll();
}
