package hello.market.common.address.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hello.market.common.address.dto.entity.AddressSd;
import hello.market.common.address.dto.entity.AddressSgg;
import hello.market.common.address.dto.entity.AddressUmd;
import hello.market.common.address.repository.AddressSdRepository;
import hello.market.common.address.repository.AddressSggRepository;
import hello.market.common.address.repository.AddressUmdRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AddressService {
	
	private final AddressSdRepository addressSdRepository;
	private final AddressSggRepository addressSggRepository;
	private final AddressUmdRepository addressUmdRepository;
	
	public List<AddressSd> findAllSd() {
		return addressSdRepository.findAll();
	}
	
	public List<AddressSgg> findAllSgg() {
		return addressSggRepository.findAll();
	}
	
	public List<AddressUmd> findAllUmd() {
		return addressUmdRepository.findAll();
	}
}
