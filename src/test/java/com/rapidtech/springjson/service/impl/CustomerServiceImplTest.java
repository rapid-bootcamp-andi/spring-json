package com.rapidtech.springjson.service.impl;

import com.rapidtech.springjson.entity.CustomerEntity;
import com.rapidtech.springjson.model.CustomerModel;
import com.rapidtech.springjson.repository.CustomerRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    @InjectMocks
    @Autowired
    private CustomerServiceImpl service;

    @Mock
    private CustomerRepo repo;

    private List<CustomerEntity> customerEntityList;

    @BeforeEach
    void setUp() {
        log.info("Setup Run ...");
        customerEntityList = Arrays.asList(
                new CustomerEntity(1L, "Kezia", "Wanita Sholehah"),
                new CustomerEntity(2L, "Yoga", "Pria Perkasa"),
                new CustomerEntity(3L, "Umi", "Wanita Tangguh")
        );
    }

    @AfterEach
    void tearDown() {
        log.info("Setup Clear ..");
    }

    @Test
    void getAll() {
        //Mocking => perumpamaan // pura pura // boong
        // ketika ada request repo.findAll , maka kembalikan value List kosong
        when(this.repo.findAll()).thenReturn(Collections.emptyList());
        List<CustomerModel> result = service.getAll();
        assertNotNull(result);
        assertEquals(0, result.size());

        when(this.repo.findAll()).thenReturn(customerEntityList);
        result = service.getAll();
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Kezia", result.get(0).getFullName());
        assertEquals("Wanita Sholehah", result.get(0).getGender());
    }

    @Test
    void getById() {
    }

    @Test
    void saveAll() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}
