package org.jeecg.modules.demo.eliminationRecord.service.impl;

import org.jeecg.modules.demo.eliminationRecord.entity.EliminationRecord;
import org.jeecg.modules.demo.eliminationRecord.mapper.EliminationRecordMapper;
import org.jeecg.modules.demo.eliminationRecord.service.IEliminationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.jeecg.modules.demo.livestock.entity.Livestock;
import org.jeecg.modules.demo.livestock.service.impl.LivestockServiceImpl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: 淘汰记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Service
@Slf4j
public class EliminationRecordServiceImpl extends ServiceImpl<EliminationRecordMapper, EliminationRecord> implements IEliminationRecordService {
@Autowired
private LivestockServiceImpl livestockService;
@Override
@Transactional(rollbackFor = Exception.class)
    public void updstatus(EliminationRecord eliminationRecord) throws Exception{
    save(eliminationRecord);
    String livestockId = eliminationRecord.getLivestockId();
    Livestock livestock = livestockService.lambdaQuery()
            .eq(Livestock::getLivestockId,livestockId)
            .one();

    livestock.setStatus("淘汰");
    livestockService.updateById(livestock);
}

}
