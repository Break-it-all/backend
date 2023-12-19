package com.goorm.BITA.domain.container.repository;

import com.goorm.BITA.domain.container.domain.Container;
import com.goorm.BITA.domain.user.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerRepository extends JpaRepository<Container, Long> {
    boolean existsByName(String name);

    @Query("select distinct c from Container c left join fetch c.folders where c.id = :id")
    Optional<Container> findById(@Param("id")Long id);

    List<Container> findByCreatedBy(User user);
}