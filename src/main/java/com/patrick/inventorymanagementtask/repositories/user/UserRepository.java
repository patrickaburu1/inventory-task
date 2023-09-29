package com.patrick.inventorymanagementtask.repositories.user;



import com.patrick.inventorymanagementtask.entities.user.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<Users, Integer> {

    Users findByEmail(String email);

    Users findByPhone(String phone);

    Users findByPhoneAndUserType(String phone, Long userType);

    Optional<Users> findByEmailAndActive(String email,int active);

    Optional<Users> findByPhoneAndActive(String phone,int active);

    @Query(nativeQuery = true, value = "select * from user where email like :email")
    List<Users> findUsersWhereIsEmailIsLike( @Param("email") String email);

    List<Users> findAllByActiveAndUserType(Integer active, Long userTypeNo);

    Users findAllByIdAndUserType(Integer id, Long userType );
}
