package com.goorm.BITA.domain.user.service;

import com.goorm.BITA.domain.user.domain.EmailAuth;
import com.goorm.BITA.domain.user.dto.request.EmailAuthCheckRequest;
import com.goorm.BITA.domain.user.dto.response.EmailAuthCheckResponse;
import com.goorm.BITA.domain.user.dto.response.EmailAuthCreateResponse;
import com.goorm.BITA.domain.user.repository.EmailAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class EmailAuthService {
    final EmailAuthRepository emailAuthRepository;
    final JavaMailSender mailSender;

    private static final String ADMIN_EMAIL = "break.it.all.IDE@gmail.com";

    /* 인증을 위한 이메일 발송 */
    public EmailAuthCreateResponse saveEmailAuth(String email) {
        EmailAuth emailAuth = new EmailAuth(email, UUID.randomUUID());
        emailAuthRepository.save(emailAuth);

        sendEmail(emailAuth.getEmail(), emailAuth.getEmailToken());

        return new EmailAuthCreateResponse(emailAuth.getEmail(), emailAuth.getId());
    }

    private void sendEmail(String email, UUID emailToken) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // TODO: 이메일 링크 프론트엔드 주소로 변경
            String url = "http://localhost:3000/email-auth/verify?emailToken=" + emailToken;
            String messageText =
                    "<div style=\"font-family: Arial, sans-serif; display: flex; justify-content: center; align-items: center; padding: 3em\">\n" +
                            "<div style=\"display:flex; flex-direction:column; align-items:center; gap: 1em; padding: 3em; width: 100%; height: 100%; box-shadow: 0px 0px 1em 0px rgba(0, 0, 0, 0.1); border: 1px solid rgba(0, 0, 0, 0.15); border-radius: 2em; text-align: center;\">" +
                            "<h1>BITA IDE 이메일 인증</h1>\n" +
                            "<p>이메일 인증을 위해 아래의 <strong>인증하기</strong> 버튼을 눌러주세요.</p>\n" +
                            "<a href=" + url + " style=\"width: 6em; padding: 0.5em 1em; border: none; background: darkturquoise; border-radius: 1em; color: white; text-decoration: none;\">인증하기</a>\n" +
                            "</div>\n" +
                            "</div>";

            helper.setTo(email);
            helper.setFrom(new InternetAddress(ADMIN_EMAIL, "BITA IDE"));
            helper.setSubject("[BITA IDE] 이메일 인증");
            helper.setText(messageText, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /* 이메일 인증 완료 */
    public void updateEmailAuth(String token) {
        UUID uuid = UUID.fromString(token);
        EmailAuth emailAuth = emailAuthRepository.findByEmailToken(uuid);
        if (emailAuth == null) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        } else if (emailAuth.isEmailVerified()) {
            throw new IllegalArgumentException("이미 인증된 이메일입니다.");
        } else if (emailAuth.isExpired()) {
            throw new IllegalArgumentException("만료된 토큰입니다.");
        }

        emailAuth.verifyEmail();
    }

    /* 이메일 인증 확인 */
    public EmailAuthCheckResponse isEmailVerified(String email, long id) {
        EmailAuth emailAuth = emailAuthRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 아이디입니다."));

        if (!emailAuth.getEmail().equals(email)) {
            throw new RuntimeException("유효하지 않은 이메일입니다.");
        }

        return new EmailAuthCheckResponse(emailAuth.getEmail(), emailAuth.isEmailVerified());
    }
}
