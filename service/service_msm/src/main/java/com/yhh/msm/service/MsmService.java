package com.yhh.msm.service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface MsmService {
    boolean send(Map<String, Object> param, String phone);
}
