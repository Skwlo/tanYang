package org.jeecg.modules.demo.deathRecord.service.impl;

import org.jeecg.modules.demo.deathRecord.entity.DeathRecord;
import org.jeecg.modules.demo.deathRecord.mapper.DeathRecordMapper;
import org.jeecg.modules.demo.deathRecord.service.IDeathRecordService;
import org.jeecg.modules.demo.saleRecordDetail.entity.SaleRecordDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.jeecg.modules.demo.livestock.entity.Livestock;
import org.jeecg.modules.demo.livestock.service.impl.LivestockServiceImpl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: 死亡记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Service
@Slf4j
public class DeathRecordServiceImpl extends ServiceImpl<DeathRecordMapper, DeathRecord> implements IDeathRecordService {
    @Autowired
    private LivestockServiceImpl livestockService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updstatus(DeathRecord deathRecord) throws Exception {
        save(deathRecord);
        String livestockId = deathRecord.getLivestockId();
        Livestock livestock = livestockService.lambdaQuery()
                .eq(Livestock::getLivestockId,livestockId)
                .one();

        livestock.setStatus("死亡");
        livestockService.updateById(livestock);

    }
}
