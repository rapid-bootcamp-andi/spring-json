package com.rapidtech.springjson.model;

import com.rapidtech.springjson.entity.SchoolEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolModel {
    private Long id;
    private String title;
    private String name;
    private String level;

    public SchoolModel(SchoolEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
