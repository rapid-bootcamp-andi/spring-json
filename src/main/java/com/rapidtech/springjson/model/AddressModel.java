package com.rapidtech.springjson.model;

import com.rapidtech.springjson.entity.AddressEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressModel {
    private Long id;
    private String name;
    private String village;
    private String district;
    private String city;
    private String province;

    public AddressModel(AddressEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
