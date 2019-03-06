package com.ltc.miaosha.vo;

import com.ltc.miaosha.validator.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @Author: luotiecheng
 */
@Data
public class RegisterVo {
    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min=32)
    private String password;

    @NotNull
    private String nickname;
}
