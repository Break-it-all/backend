package com.goorm.BITA.domain.container.repository;

import com.goorm.BITA.domain.container.domain.Container;
import com.goorm.BITA.domain.container.domain.ContainerUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerUserRepository extends JpaRepository<ContainerUser, Long> {
        @Query("SELECT cu.container FROM ContainerUser cu WHERE cu.user.id = :userId")
        List<Container> findSharedContainersByUserId(@Param("userId") Long userId);

    List<ContainerUser> findByContainerId(Long containerId);
}