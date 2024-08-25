package com.ivanalimin.spring_mvc_json_view.repository;

import com.ivanalimin.spring_mvc_json_view.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, UUID> {

    @EntityGraph(attributePaths = {"orders"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<User> getWithOrdersById(UUID id);
}
