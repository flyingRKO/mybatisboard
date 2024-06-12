package com.example.mybatisboard.service.impl;

import com.example.mybatisboard.dto.UserDTO;
import com.example.mybatisboard.exception.DuplicateIdException;
import com.example.mybatisboard.mapper.UserProfileMapper;
import com.example.mybatisboard.service.UserService;
import com.example.mybatisboard.utils.SHA256Util;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserProfileMapper userProfileMapper;
    @Autowired
    public UserServiceImpl(UserProfileMapper userProfileMapper) {
        this.userProfileMapper = userProfileMapper;
    }

    @Override
    public void register(UserDTO userDTO) {

        boolean duplIdResult = isDuplicatedId(userDTO.getUserId());
        if (duplIdResult) {
            throw new DuplicateIdException("중복된 아이디입니다.");
        }
        userDTO.setCreateTime(new Date());
        userDTO.setPassword(SHA256Util.encryptSHA256(userDTO.getPassword()));
        int insertCount = userProfileMapper.register(userDTO);

        if (insertCount != 1){
            log.error("insertMember ERROR! {}", userDTO);
            throw new RuntimeException(
                    "insertUser ERROR! 회원가입 메서드를 확인해주세요\n" + "Params : " + userDTO);
        }

    }

    @Override
    public UserDTO login(String id, String password) {
        String cryptoPassword = SHA256Util.encryptSHA256(password);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id, cryptoPassword);
        return memberInfo;
    }

    @Override
    public boolean isDuplicatedId(String id) {
        return userProfileMapper.idCheck(id) == 1;
    }

    @Override
    public UserDTO getUserInfo(String userId) {
        return userProfileMapper.getUserProfile(userId);
    }

    @Override
    public void updatePassword(String id, String beforePassword, String afterPassword) {
        String cryptoPassword = SHA256Util.encryptSHA256(beforePassword);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id, cryptoPassword);
        if (memberInfo != null) {
            memberInfo.setPassword(SHA256Util.encryptSHA256(afterPassword));
            int insertCount = userProfileMapper.updatePassword(memberInfo);
            if (insertCount != 1) {
                log.error("updatePassword ERROR! Password update failed for {}", memberInfo);
                throw new RuntimeException("updatePassword ERROR! 비밀번호 업데이트 실패\n" + "Params : " + memberInfo);
            }
        } else {
            log.error("updatePassword ERROR! {}", memberInfo);
            throw new IllegalArgumentException("updatePassword ERROR! 비밀번호 변경 메서드를 확인해주세요\n" + "Params : " + memberInfo);
        }
    }

    @Override
    public void deleteId(String id, String passWord) {
        String cryptoPassword = SHA256Util.encryptSHA256(passWord);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id, cryptoPassword);
        if (memberInfo != null){
            userProfileMapper.deleteUserProfile(memberInfo.getUserId());
        } else {
          log.error("deleteId ERROR! {}", memberInfo);
          throw new RuntimeException("deleteId ERROR! id 삭제 메서드를 확인해주세요\n" + "Params : " + memberInfo);
        }
    }
}
