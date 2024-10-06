package com.choandyoo.jett.user.service;

import com.choandyoo.jett.user.entity.UserEntity;
import com.choandyoo.jett.user.repository.UserRepository;
import com.choandyoo.jett.user.dto.MemberInfoRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    // 생성자를 통해 UserRepository를 주입
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Long signUp(MemberInfoRequestDto memberInfoRequestDto) {
        // 비밀번호를 암호화하는 로직이 필요함 (예: BCryptPasswordEncoder 사용)
        String encodedPassword = encodePassword(memberInfoRequestDto.getPassword());
        memberInfoRequestDto.encodePassword(encodedPassword);

        // UserEntity로 변환하여 DB에 저장
        UserEntity userEntity = memberInfoRequestDto.toSaveMember();
        UserEntity savedUser = userRepository.save(userEntity);
        return savedUser.getUserId(); // 회원 가입 후 생성된 사용자 ID 반환
    }

    private String encodePassword(String password) {
        // 비밀번호 인코딩 로직을 여기에 추가
        // 예를 들어, BCryptPasswordEncoder 사용
        return password; // 임시로 그대로 반환 (나중에 수정)
    }
}
