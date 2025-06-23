package org.jeecg.modules.demo.deathRecord.service;

import org.jeecg.modules.demo.deathRecord.entity.DeathRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 死亡记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
public interface IDeathRecordService extends IService<DeathRecord> {
    void updstatus(DeathRecord deathRecord) throws Exception;
}
