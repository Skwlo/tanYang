package org.jeecg.modules.demo.saleRecordDetail.service.impl;

import org.jeecg.modules.demo.inventory.service.IInventoryService;
import org.jeecg.modules.demo.livestock.entity.Livestock;
import org.jeecg.modules.demo.livestock.service.impl.LivestockServiceImpl;
import org.jeecg.modules.demo.saleRecordDetail.entity.SaleRecordDetail;
import org.jeecg.modules.demo.saleRecordDetail.mapper.SaleRecordDetailMapper;
import org.jeecg.modules.demo.saleRecordDetail.service.ISaleRecordDetailService;
import org.jeecg.modules.demo.stockIn.entity.StockIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.jeecg.modules.demo.livestock.entity.Livestock;
import org.jeecg.modules.demo.livestock.service.ILivestockService;

/**
 * @Description: 销售明细表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Service
@Slf4j
public class SaleRecordDetailServiceImpl extends ServiceImpl<SaleRecordDetailMapper, SaleRecordDetail> implements ISaleRecordDetailService {
    @Autowired
    private LivestockServiceImpl livestockService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeStatus(SaleRecordDetail saleRecordDetail) throws Exception {
        save(saleRecordDetail);
        String livestockId = saleRecordDetail.getLivestockId();
        Livestock livestock = livestockService.lambdaQuery()
                .eq(Livestock::getLivestockId,livestockId)
                .one();

        livestock.setStatus("已售");
        livestockService.updateById(livestock);

    }
}
