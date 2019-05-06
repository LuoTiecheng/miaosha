package com.ltc.miaosha.vo;

import com.ltc.miaosha.validator.IsMobile;
import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author: luotiecheng
 */
@Data
@Getter
public class RegisterVo {
    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min=32)
    private String password;

    @NotNull
    private String nickname;

    private Date registerDate;

    public void setRegisterDate(Date registerDate){
        this.registerDate = registerDate;
    }

    public Date getRegisterDate(){
         return registerDate;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile(){
        return mobile;
    }

    public String getPassword(){
        return password;
    }

    public String getNickname(){
        return nickname;
    }
}
