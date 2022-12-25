package com.rapidtech.springjson.service.impl;

import com.rapidtech.springjson.entity.CustomerEntity;
import com.rapidtech.springjson.model.CustomerModel;
import com.rapidtech.springjson.model.CustomerRequestModel;
import com.rapidtech.springjson.model.CustomerResponseModel;
import com.rapidtech.springjson.model.CustomerResponseModel;
import com.rapidtech.springjson.repository.CustomerRepo;
import com.rapidtech.springjson.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepo repo;

    @Autowired
    public CustomerServiceImpl(CustomerRepo repo) {
        this.repo = repo;
    }

    @Override
    public List<CustomerModel> getAll() {
        return this.repo.findAll().stream().map(CustomerModel::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerModel> getById(Long id) {
        if(id == 0) {
            return Optional.empty();
        }
        Optional<CustomerEntity> result = this.repo.findById(id);
        return result.map(CustomerModel::new);
    }

    @Override
    public CustomerResponseModel saveAll(CustomerRequestModel request) {
        if(request.getCustomer().isEmpty()) {
            return new CustomerResponseModel();
        }
        CustomerResponseModel response = new CustomerResponseModel();
        int countSuccess = 0;
        int countFailed = 0;
        List<CustomerModel> customerModels = new ArrayList<>();
        for (CustomerModel model: request.getCustomer()){
            // panggil method save
            Optional<CustomerModel> customerModel = this.save(model);
            // check datanya
            if(customerModel.isPresent()){
                customerModels.add(model);
                countSuccess++;
            }else {
                countFailed++;
            }
        }
        // return  new CustomerResponse(customerModels, countSuccess, countFailed);
        response.setData(customerModels);
        response.setSuccessSave(countSuccess);
        response.setFailedSave(countFailed);
        return response;
    }

    @Override
    public Optional<CustomerModel> save(CustomerModel model) {
        if(model == null) {
            return Optional.empty();
        }
        CustomerEntity entity = new CustomerEntity(model);

        if(!model.getAddress().isEmpty()){
            entity.addAddressList(model.getAddress());
        }

        if(!model.getSchools().isEmpty()){
            entity.addSchoolList(model.getSchools());
        }

        try {
            this.repo.save(entity);
            return Optional.of(new CustomerModel(entity));
        }catch (Exception e){
            log.error("Customer save is failed, error: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<CustomerModel> update(Long id, CustomerModel model) {
        if(id == 0) {
            return Optional.empty();
        }

        CustomerEntity result = this.repo.findById(id).orElse(null);
        if(result == null){
            return Optional.empty();
        }

        // copy property
        BeanUtils.copyProperties(model, result);
        try {
            this.repo.save(result);
            return Optional.of(new CustomerModel(result));
        }catch (Exception e){
            log.error("Customer update is failed, error: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<CustomerModel> delete(Long id) {
        if(id == 0) {
            return Optional.empty();
        }

        CustomerEntity result = this.repo.findById(id).orElse(null);
        if(result == null){
            return Optional.empty();
        }

        try {
            this.repo.delete(result);
            return Optional.of(new CustomerModel(result));
        }catch (Exception e){
            log.error("Customer delete is failed, error: {}", e.getMessage());
            return Optional.empty();
        }
    }
}
