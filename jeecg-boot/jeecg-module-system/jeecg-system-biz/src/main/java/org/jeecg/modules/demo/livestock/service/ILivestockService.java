package org.jeecg.modules.demo.livestock.service;

import org.jeecg.modules.demo.livestock.entity.Livestock;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 畜只表
 * @Author: jeecg-boot
 * @Date:   2025-06-17
 * @Version: V1.0
 */
public interface ILivestockService extends IService<Livestock> {

    // 添加根据livestockId查询的方法
    Livestock getByLivestockId(String livestockId);
}
