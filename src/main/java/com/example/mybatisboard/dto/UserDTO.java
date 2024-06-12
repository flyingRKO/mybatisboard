package com.example.mybatisboard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class UserDTO {
    public enum Status {
        DEFAULT, ADMIN, DELETED
    }

    private int id;
    private String userId;
    private String password;
    private String nickName;
    private boolean isAdmin;
    private Date createTime;
    private boolean isWithDraw;
    private Status status;
    private Date updateTime;

    public UserDTO(){}

    public UserDTO(String userId, String password, String nickName, boolean isAdmin, Date createTime, Status status, Date updateTime) {
        this.userId = userId;
        this.password = password;
        this.nickName = nickName;
        this.isAdmin = isAdmin;
        this.createTime = createTime;
        this.status = status;
        this.updateTime = updateTime;
    }

    public static boolean hasNullDataBeforeSingUp(UserDTO userDTO){
        return userDTO.getUserId() == null || userDTO.getPassword() == null || userDTO.getNickName() == null;
    }
}
