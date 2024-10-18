package com.easycommerce.user.address;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/addresses")
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<List<AddressDTO>> getUserAddresses() {
        List<AddressDTO> addressDTOs = addressService.getUserAddresses();
        return new ResponseEntity<>(addressDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long id) {
        AddressDTO addressDTO = addressService.getAddressById(id);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AddressDTO> addAddress(@Valid @RequestBody AddressDTO addressDTO) {
        AddressDTO savedAddressDTO = addressService.addAddress(addressDTO);
        return new ResponseEntity<>(savedAddressDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> updateAddressById(@Valid @RequestBody AddressDTO addressDTO, @PathVariable Long id) {
        AddressDTO updatedAddressDTO = addressService.updateAddressById(addressDTO, id);
        return new ResponseEntity<>(updatedAddressDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddressById(@PathVariable Long id) {
        addressService.deleteAddressById(id);
        return new ResponseEntity<>("Address deleted successfully", HttpStatus.OK);
    }
}
