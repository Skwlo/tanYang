package org.jeecg.modules.demo.livestock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.demo.livestock.entity.Livestock;
import org.jeecg.modules.demo.livestock.mapper.LivestockMapper;
import org.jeecg.modules.demo.livestock.service.ILivestockService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 畜只表
 * @Author: jeecg-boot
 * @Date:   2025-06-17
 * @Version: V1.0
 */
@Service
public class LivestockServiceImpl extends ServiceImpl<LivestockMapper, Livestock> implements ILivestockService {
    @Override
    public Livestock getByLivestockId(String livestockId) {
        QueryWrapper<Livestock> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("livestock_id", livestockId); // 对应数据库字段名
        return this.getOne(queryWrapper);
    }
}
