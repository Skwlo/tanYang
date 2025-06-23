package org.jeecg.modules.demo.eliminationRecord.service;

import org.jeecg.modules.demo.eliminationRecord.entity.EliminationRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 淘汰记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
public interface IEliminationRecordService extends IService<EliminationRecord> {
    void updstatus(EliminationRecord eliminationRecord) throws Exception;
}
