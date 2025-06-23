package org.jeecg.modules.demo.testdict.service.impl;

import org.jeecg.modules.demo.testdict.entity.Testdict;
import org.jeecg.modules.demo.testdict.mapper.TestdictMapper;
import org.jeecg.modules.demo.testdict.service.ITestdictService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 字典
 * @Author: jeecg-boot
 * @Date:   2025-06-11
 * @Version: V1.0
 */
@Service
public class TestdictServiceImpl extends ServiceImpl<TestdictMapper, Testdict> implements ITestdictService {

}
