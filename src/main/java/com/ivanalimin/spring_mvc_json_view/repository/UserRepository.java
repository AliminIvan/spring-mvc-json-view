package com.ivanalimin.spring_mvc_json_view.repository;

import com.ivanalimin.spring_mvc_json_view.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("select u from User u left join fetch u.orders where u.id=?1")
    Optional<User> getWithOrders(UUID id);

    @EntityGraph(attributePaths = {"orders"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select u from User u where u.id=?1")
    User getWithOrdersById(UUID id);
}
