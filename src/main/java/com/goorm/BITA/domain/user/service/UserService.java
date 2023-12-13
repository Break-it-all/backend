package com.goorm.BITA.domain.user.service;

import com.goorm.BITA.domain.user.domain.User;
import com.goorm.BITA.domain.user.dto.request.*;
import com.goorm.BITA.domain.user.dto.response.UserSignInResponse;
import com.goorm.BITA.domain.user.dto.response.UserResponse;
import com.goorm.BITA.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    final UserRepository userRepository;

    /* 회원가입 */
    public UserResponse saveUser(UserSignUpRequest userSignUpRequest) {
        User user = userRepository.save(userSignUpRequest.toEntity());
        return UserResponse.toDto(user);
    }

    /* 로그인 */
    public UserSignInResponse signin(UserSignInRequest userSignInRequest) {
        // TODO: Exception 처리
        User user = userRepository.findByEmail(userSignInRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));

        if (!user.getPassword().equals(userSignInRequest.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return UserSignInResponse.toDto(user.getEmail(), user.getName());
    }

    /* 회원정보 수정 */
    public UserResponse updateUser(UserUpdateInfoRequest userUpdateInfoRequest) {
        // TODO: Exception 처리
        User user = userRepository.findByEmail(userUpdateInfoRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));
        user.updateName(userUpdateInfoRequest.getName());

        return UserResponse.toDto(user);
    }

    /* 비밀번호 재설정 */
    public void resetPassword(UserUpdatePasswordRequest userUpdatePasswordRequest) {
        User user = userRepository.findByEmail(userUpdatePasswordRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));
        // TODO: EmailAuth ID 확인 로직 추가

        user.updatePassword(userUpdatePasswordRequest.getPassword());
    }

    /* 회원 탈퇴 */
    public void deleteUser(UserDeleteRequest userDeleteRequest) {
        // TODO: Exception 처리
        User user = userRepository.findByEmail(userDeleteRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));
        // TODO: 비밀번호 확인

        userRepository.delete(user);
    }
}
