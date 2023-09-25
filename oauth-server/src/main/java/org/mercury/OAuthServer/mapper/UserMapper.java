package org.mercury.OAuthServer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mercury.OAuthServer.bean.AuthUser;
import org.mercury.OAuthServer.dto.UserDto;

/**
 * @ClassName UserMapper
 * @Description TODO
 * @Author katefu
 * @Date 9/24/23 10:16 PM
 * @Version 1.0
 **/
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.login", target = "login")
    @Mapping(source = "token", target = "token")
    @Mapping(target = "password", ignore = true)
    UserDto toUserDto(AuthUser user, String token);

    @Mapping(source = "encodedPassword", target = "password")
    AuthUser toAuthUser(UserDto userDto, String encodedPassword);
}
