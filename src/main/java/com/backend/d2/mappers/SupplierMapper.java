package com.backend.d2.mappers;

import com.backend.d2.dtos.supplier.request.SupplierCreateDTO;
import com.backend.d2.dtos.supplier.response.SupplierResponseDTO;
import com.backend.d2.entity.SupplierEntity;
import com.backend.d2.models.SupplierModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SupplierMapper {

    SupplierMapper INSTANCE = Mappers.getMapper(SupplierMapper.class);

    // Mapeo de dto a modelo
    SupplierModel toModelCreate(SupplierCreateDTO supplierCreateDTO);

    // Mapeo de modelo a dto
    SupplierResponseDTO toDtoCreateResponse(SupplierModel supplierModel);

    // Mapeo de modelo a entidad
    SupplierEntity toEntity(SupplierModel supplierModel);

    // Mapeo de entidad a modelo
    SupplierModel entityToModel(SupplierEntity supplierEntity);
}
