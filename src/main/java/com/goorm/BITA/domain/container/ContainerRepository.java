package com.goorm.BITA.domain.container;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerRepository extends JpaRepository<Container, Long> {
    boolean existsByName(String name);


//    @Query("select c from Container c where c.id = :id")
//    Optional<Container> findById(Long id); findByIdWithFolders

    @Query("select distinct c from Container c left join fetch c.folders where c.id = :id")
    Optional<Container> findById(@Param("id")Long id);
}