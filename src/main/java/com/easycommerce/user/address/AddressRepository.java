package com.easycommerce.user.address;

import com.easycommerce.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByIdAndUser(Long id, User user);
}
