package com.ltc.miaosha.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ltc.miaosha.domain.MiaoshaUser;

@Mapper
public interface MiaoshaUserDao {
	
	@Select("select * from miaosha_user where id = #{id}")
	public MiaoshaUser getById(@Param("id") long id);

	@Insert("insert into miaosha_user(id,password,nickname,salt) values(#{id},#{password},#{nickname},#{salt})")
	public int insert(@Param("id") Long id,@Param("password") String password, @Param("nickname") String nickname,@Param("salt") String salt);
}
