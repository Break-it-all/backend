package com.goorm.BITA.domain.folder;

import com.goorm.BITA.domain.container.Container;
import com.goorm.BITA.domain.container.ContainerRepository;
import com.goorm.BITA.domain.folder.dto.request.FolderCreateRequestDto;
import com.goorm.BITA.domain.folder.dto.request.FolderUpdateRequestDto;
import com.goorm.BITA.domain.folder.dto.response.FolderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;
    private final ContainerRepository containerRepository;

    @Transactional
    public FolderResponseDto createFolder(Long containerId, FolderCreateRequestDto requestDto) {
        String name = requestDto.getName();
        Long parentFolderId = requestDto.getParentFolderId();

        Container container = containerRepository.findById(containerId)
            .orElseThrow(() -> new RuntimeException("컨테이너가 존재하지 않습니다."));

        Folder folder;
        if (parentFolderId == null) {
            folder = Folder.createRootFolder(name, container);
        } else {
            Folder parentFolder = getFolder(parentFolderId);
            folder = Folder.createSubFolder(name, container, parentFolder);
        }

        container.addFolder(folder);
        return FolderResponseDto.toDto(folderRepository.save(folder));
    }

    @Transactional
    public FolderResponseDto updateFolder(Long id, FolderUpdateRequestDto requestDto) {
        Folder folder = getFolder(id);
        folder.updateName(requestDto.getName());
        Folder saveFolder = folderRepository.save(folder);
        return FolderResponseDto.toDto(saveFolder);
    }

    @Transactional
    public void deleteFolder(Long id) {
        Folder folder = getFolder(id);
        folderRepository.delete(folder);
    }

    public Folder getFolder(Long id) {
        return folderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("폴더를 찾을 수 없습니다."));
    }
}
