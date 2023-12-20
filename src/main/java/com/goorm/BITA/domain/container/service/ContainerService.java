package com.goorm.BITA.domain.container.service;

import com.goorm.BITA.common.enums.ContainerMode;
import com.goorm.BITA.common.enums.UserRole;
import com.goorm.BITA.domain.container.domain.Container;
import com.goorm.BITA.domain.container.domain.ContainerUser;
import com.goorm.BITA.domain.container.dto.request.ContainerUpdateRequestDto;
import com.goorm.BITA.domain.container.dto.response.ContainerListResponseDto;
import com.goorm.BITA.domain.container.dto.response.ContainerResponseDto;
import com.goorm.BITA.domain.container.info.CreateContainerInfo;
import com.goorm.BITA.domain.container.info.FindContainerInfo;
import com.goorm.BITA.domain.container.repository.ContainerRepository;
import com.goorm.BITA.domain.container.repository.ContainerUserRepository;
import com.goorm.BITA.domain.user.domain.User;
import com.goorm.BITA.domain.user.repository.UserRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ContainerService {

    private final ContainerRepository containerRepository;
    private final ContainerUserRepository containerUserRepository;
    private final UserRepository userRepository;

    // create
    @Transactional
    public ContainerResponseDto createContainer(CreateContainerInfo requestInfo) {
        User user = getUser(requestInfo.getUserId());
        if (containerRepository.existsByName(requestInfo.getName())) {
            throw new RuntimeException("이미 존재하는 이름입니다.");
        }
        Container newContainer = requestInfo.from(user);
        containerRepository.save(newContainer);
        assignRoleToCreator(newContainer, user);
        return ContainerResponseDto.from(newContainer);
    }

    // update
    @Transactional
    public void updateController(ContainerUpdateRequestDto requestDto, Long containerId, Long userId) {
        User user = getUser(userId);
        if (containerRepository.existsByName(requestDto.getName())) {
            throw new RuntimeException("중복된 이름입니다.");
        }
        Container findContainer = getContainer(containerId);
        findContainer.update(requestDto.getName(), user);
    }

    // delete
    @Transactional
    public void deleteContainer(Long containerId, Long userId) {
        User user = getUser(userId);
        Container container = getContainer(containerId);
        extracted(container, user);
        containerRepository.deleteById(containerId);
    }

    // 단일 조회
    public ContainerResponseDto findContainer(FindContainerInfo containerInfo) {
        User user = getUser(containerInfo.getUserId());
        Container container = getContainer(containerInfo.getContainerId());
        extracted(container, user);
        return ContainerResponseDto.from(container);
    }


    // 내가 만든 컨테이너 조회
    public List<ContainerListResponseDto> getMyContainers(long userId) {
        User user = getUser(userId);
        List<Container> findContainers = containerRepository.findByCreatedBy(user);
        return findContainers.stream()
            .map(ContainerListResponseDto::from)
            .toList();
    }

    // 공유된 컨테이너 조회
    public List<ContainerListResponseDto> getSharedContainers(long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        return containerUserRepository.findSharedContainersByUserId(userId).stream()
            .filter(container -> !container.getCreatedBy().equals(user))
            .map(ContainerListResponseDto::from)
            .toList();
    }

    // 역할 변경
    @Transactional
    public void switchUserRole(Long containerId) {
        List<ContainerUser> containerUsers = containerUserRepository.findByContainerId(containerId);
        for (ContainerUser containerUser : containerUsers) {
            containerUser.switchRole();
        }
    }

    // 유저 초대
    @Transactional
    public ContainerUser addUserToContainer(Long containerId, String email) {
        Container container = getContainer(containerId);
        User user = getUserByEmail(email);
        ContainerUser containerUser = assignUserRole(container, user);
        containerUserRepository.save(containerUser);
        return containerUser;
    }

    public ContainerUser assignUserRole(Container container, User user) {
        UserRole role = determineRoleBasedOnContainerMode(container.getMode());
        return new ContainerUser(container, user, role);
    }


    private static void extracted(Container container, User user) {
        if (!container.isUser(user)) {
            throw new RuntimeException("권한이 없습니다.");
        }
    }

    private void assignRoleToCreator(Container container, User creator) {
        ContainerUser containerUser = new ContainerUser(container, creator, UserRole.driver);
        container.addContainerUser(containerUser);
        containerUserRepository.save(containerUser);
    }

    private UserRole determineRoleBasedOnContainerMode(String mode) {
        return (ContainerMode.multi.getMode().equals(mode)) ? UserRole.driver : UserRole.navigator;
    }

    private Container getContainer(Long containerId) {
        return containerRepository.findById(containerId)
            .orElseThrow(() -> new EntityNotFoundException("컨테이너가 없습니다."));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
    }
}