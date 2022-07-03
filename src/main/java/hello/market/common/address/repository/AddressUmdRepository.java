package hello.market.common.address.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hello.market.common.address.dto.entity.AddressUmd;

public interface AddressUmdRepository extends JpaRepository<AddressUmd, Long> {
	
	List<AddressUmd> findAll();
}
