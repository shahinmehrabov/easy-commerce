package com.easycommerce.user.address;

import java.util.List;

public interface AddressService {

    List<AddressDTO> getUserAddresses();
    AddressDTO getAddressById(Long id);
    AddressDTO addAddress(AddressDTO addressDTO);
    AddressDTO updateAddressById(AddressDTO addressDTO, Long id);
    void deleteAddressById(Long id);
}
