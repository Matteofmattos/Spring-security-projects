package com.matteof.mattos.springSecurityOne.repository;

import java.util.List;
import java.util.Optional;

import com.matteof.mattos.springSecurityOne.model.Users;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"authorities"})
    Optional<Users> findByUsername(String username);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"authorities"})
    List<Users> findAll();
}