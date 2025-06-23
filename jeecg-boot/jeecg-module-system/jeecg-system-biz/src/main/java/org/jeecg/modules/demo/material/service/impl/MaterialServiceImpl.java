package org.jeecg.modules.demo.material.service.impl;

import org.jeecg.modules.demo.material.entity.Material;
import org.jeecg.modules.demo.material.mapper.MaterialMapper;
import org.jeecg.modules.demo.material.service.IMaterialService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 物资表
 * @Author: jeecg-boot
 * @Date:   2025-06-17
 * @Version: V1.0
 */
@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements IMaterialService {

}
