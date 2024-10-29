package com.easycommerce.user.address;

import com.easycommerce.exception.ResourceNotFoundException;
import com.easycommerce.user.User;
import com.easycommerce.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final ModelMapper modelMapper;
    private final AddressRepository addressRepository;
    private final UserService userService;

    @Override
    public List<AddressDTO> getUserAddresses() {
        User user = userService.getLoggedInUser();
        Set<Address> addresses = user.getAddresses();

        return addresses.stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .toList();
    }

    @Override
    public AddressDTO getAddressById(Long id) {
        List<AddressDTO> addressDTOs = getUserAddresses();

        return addressDTOs.stream()
                .filter(addressDTO -> Objects.equals(id, addressDTO.getId()))
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", id));
    }

    @Override
    public AddressDTO addAddress(AddressDTO addressDTO) {
        Address address = modelMapper.map(addressDTO, Address.class);
        User user = userService.getLoggedInUser();

        address.setUser(user);

        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress, AddressDTO.class);
    }

    @Override
    public AddressDTO updateAddressById(AddressDTO addressDTO, Long id) {
        Address address = findUserAddressById(id);

        address.setStreet(addressDTO.getStreet());
        address.setBuilding(addressDTO.getBuilding());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setCountry(addressDTO.getCountry());
        address.setZip(addressDTO.getZip());

        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress, AddressDTO.class);
    }

    @Override
    public void deleteAddressById(Long id) {
        Address address = findUserAddressById(id);
        addressRepository.delete(address);
    }

    private Address findUserAddressById(Long id) {
        User user = userService.getLoggedInUser();

        return addressRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
    }
}
