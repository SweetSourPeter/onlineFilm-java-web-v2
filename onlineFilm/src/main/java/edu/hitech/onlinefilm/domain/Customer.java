package edu.hitech.onlinefilm.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
@Data
@Entity
@Table(name = "t_customer")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message="账号长度在3~20个字符")
    @Length(min = 5,max = 20,message="账号长度在3~20个字符")
    private String account;

//    @NotBlank(message="用户密码在6~20个字符")
//    @Length(min=6,max=20,message="用户密码在6~20个字符")
    private String password;

    private String salt;

    @NotEmpty(message="昵称在2~20个字符之间")
    @Length(min=2,max=20)
    private String alias;

    @NotEmpty(message="性别不能为空")
    @Pattern(regexp = "m|w",message = "性别只能是w或m")
    private String gender;

    private String phone;

    @Email(message="邮箱地址不正确")
    @Length(min=10,max=30,message = "邮箱在10到30个字符")
    private String email;

    @Column(name = "register_time")
    private Date registerTime;
}
