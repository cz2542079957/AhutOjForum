package com.ahutoj.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

//ahutoj数据库 User表
@Data
@NoArgsConstructor
@ToString
public class User implements Serializable
{
    private String UID;
    private String HeadURL;
    private String UserName;
    private String Pass;
    private String School;
    private String Classes;
    private String Major;
    private String Adept;
    private String Vjid;
    private String Vjpwd;
    private String Email;
    private String CodeForceUser;

}
