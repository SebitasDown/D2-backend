package com.backend.d2.mappers;

import com.backend.d2.dtos.users.requests.RegisterUserDTO;
import com.backend.d2.dtos.users.responses.UserResponseDTO;
import com.backend.d2.entity.UserEntity;
import com.backend.d2.models.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserModel toModel(UserEntity entity);
    UserEntity toEntity(UserModel model);

    UserModel toModel(RegisterUserDTO dto);

    UserResponseDTO toResponseDTO(UserModel model);
    List<UserResponseDTO> toResponseDTOList(List<UserModel> models);
}
