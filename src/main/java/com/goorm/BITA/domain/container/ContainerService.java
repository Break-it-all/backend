package com.goorm.BITA.domain.container;

import com.goorm.BITA.domain.container.dto.ContainerCreateRequestDto;
import com.goorm.BITA.domain.container.dto.ContainerUpdateRequestDto;
import com.goorm.BITA.domain.file.File;
import com.goorm.BITA.domain.file.FileRepository;
import com.goorm.BITA.domain.folder.Folder;
import com.goorm.BITA.domain.folder.FolderRepository;
import java.util.List;
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
    private final FolderRepository folderRepository;

    // create
    @Transactional
    public Container createContainer(ContainerCreateRequestDto requestDto) {
        // TODO user 검증
        log.info(containerRepository.existsByName(requestDto.getName()) + "");
        if (containerRepository.existsByName(requestDto.getName())) {
            throw new RuntimeException("이미 존재하는 이름입니다.");
        }
        return containerRepository.save(requestDto.from());
    }

    // delete
    @Transactional
    public void deleteContainer(Long containerId) {
        // TODO user 검증
        if (!containerRepository.existsById(containerId)) {
            throw new RuntimeException("존재하지 않는 컨테이너입니다.");
        }
        containerRepository.deleteById(containerId);
    }

    // update
    @Transactional
    public boolean updateController(ContainerUpdateRequestDto requestDto, Long containerId) {
        // TODO user 검증
        if (containerRepository.existsByName(requestDto.getName())) {
            throw new RuntimeException("중복된 이름입니다.");
        }
        Container findContainer = containerRepository.findById(containerId).orElseThrow(
            () -> new RuntimeException("존재하지 않는 컨테이너입니다."));
        findContainer.update(requestDto.getName());
        return true;
    }

    // 단일 조회
    public Container getContainer(Long containerId) {
        // TODO user 검증
        Container container = containerRepository.findById(containerId)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 컨테이너입니다."));
//        for (Folder folder : container.getFolders()) {
//            List<Folder> subFolders = folderRepository.findSubFolders(folder.getId());
//            List<File> files = folderRepository.findFiles(folder.getId());
//            folder.setSubFolders(subFolders);
//            folder.setFiles(files);
//        }
        return container;
    }

    // 전체조회
    // TODO 회원정보 받으면 개발하기
}