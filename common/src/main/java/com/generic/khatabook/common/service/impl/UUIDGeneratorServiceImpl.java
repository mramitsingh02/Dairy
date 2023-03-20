package com.generic.khatabook.common.service.impl;

import com.generic.khatabook.common.service.IdGeneratorService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDGeneratorServiceImpl implements IdGeneratorService {

    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }
}
