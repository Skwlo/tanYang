package org.jeecg.modules.demo.stockIn.service.impl;

import org.jeecg.modules.demo.stockIn.entity.StockIn;
import org.jeecg.modules.demo.stockIn.mapper.StockInMapper;
import org.jeecg.modules.demo.stockIn.service.IStockInService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.demo.inventory.entity.Inventory;
import org.jeecg.modules.demo.inventory.service.IInventoryService;
import java.math.BigDecimal;
/**
 * @Description: 入库记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Service
@Slf4j
public class StockInServiceImpl extends ServiceImpl<StockInMapper, StockIn> implements IStockInService {
    @Autowired
    private IInventoryService inventoryService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleStockIn(StockIn stockIn) throws Exception {
        save(stockIn);
        String materialId = stockIn.getMaterialId();
        BigDecimal quantity = stockIn.getQuantity();

        //查询库存记录
        Inventory inventory = inventoryService.lambdaQuery()
                .eq(Inventory::getMaterialId, materialId)
                .one();

        if (inventory != null) {
            //库存数量
            inventory.setCurrentQuantity(inventory.getCurrentQuantity().add(quantity));
            inventory.setLastInDate(stockIn.getInDate());
            inventoryService.updateById(inventory);
            log.info("更新库存成功: 物资ID={}, 新库存={}", materialId, inventory.getCurrentQuantity());
        } else {
            log.error("库存记录不存在，物资ID: {}", materialId);
            throw new Exception("库存记录不存在，无法完成入库操作");
        }
    }
}
