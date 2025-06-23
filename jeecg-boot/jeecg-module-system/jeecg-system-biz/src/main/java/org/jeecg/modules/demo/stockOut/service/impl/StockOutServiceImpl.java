package org.jeecg.modules.demo.stockOut.service.impl;

import org.jeecg.modules.demo.stockOut.entity.StockOut;
import org.jeecg.modules.demo.stockOut.mapper.StockOutMapper;
import org.jeecg.modules.demo.stockOut.service.IStockOutService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.demo.inventory.entity.Inventory;
import org.jeecg.modules.demo.inventory.service.IInventoryService;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 出库记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Service
@Slf4j
public class StockOutServiceImpl extends ServiceImpl<StockOutMapper, StockOut> implements IStockOutService {
    @Autowired
    private IInventoryService inventoryService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleStockOut(StockOut stockOut) throws Exception {
        if (stockOut.getQuantity() == null || stockOut.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("出库数量必须为正数");
        }
        //插入出库记录
        save(stockOut);
        //获取ID和数量
        String materialId = stockOut.getMaterialId();
        BigDecimal quantity = stockOut.getQuantity();
        Inventory inventory = inventoryService.lambdaQuery()
                .eq(Inventory::getMaterialId, materialId)
                .one();

        if (inventory != null) {
            //检查库存是否充足
            if (inventory.getCurrentQuantity().compareTo(quantity) < 0) {
                throw new Exception("库存不足，无法完成出库");
            }

            inventory.setCurrentQuantity(inventory.getCurrentQuantity().subtract(quantity));
            inventory.setLastOutDate(stockOut.getOutDate() != null ? stockOut.getOutDate() : new Date());
            //保存
            inventoryService.updateById(inventory);
            log.info("更新库存成功: 物资ID={}, 新库存={}", materialId, inventory.getCurrentQuantity());
        } else {
            log.error("库存记录不存在，物资ID: {}", materialId);
            throw new Exception("库存记录不存在，无法完成出库操作");
        }
    }
}
