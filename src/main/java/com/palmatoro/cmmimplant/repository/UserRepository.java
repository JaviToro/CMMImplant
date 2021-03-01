package com.palmatoro.cmmimplant.repository;

import com.palmatoro.cmmimplant.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}