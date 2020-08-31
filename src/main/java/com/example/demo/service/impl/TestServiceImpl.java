package com.example.demo.service.impl;

import com.example.demo.dao.ProductInfoMapper;
import com.example.demo.entity.ProductInfo;
import com.example.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Override
    public ProductInfo queryById(Long id) {
        return productInfoMapper.selectByPrimaryKey(id);
    }
}
