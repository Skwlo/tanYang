package org.jeecg.modules.demo.vaccinationRecord.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.demo.inventory.entity.Inventory;
import org.jeecg.modules.demo.inventory.service.IInventoryService;
import org.jeecg.modules.demo.stockOut.entity.StockOut;
import org.jeecg.modules.demo.stockOut.service.IStockOutService;
import org.jeecg.modules.demo.vaccinationRecord.entity.VaccinationRecord;
import org.jeecg.modules.demo.vaccinationRecord.mapper.VaccinationRecordMapper;
import org.jeecg.modules.demo.vaccinationRecord.service.IVaccinationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

/**
 * @Description: 免疫记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
@Service
public class VaccinationRecordServiceImpl extends ServiceImpl<VaccinationRecordMapper, VaccinationRecord> implements IVaccinationRecordService {

    @Autowired
    private IInventoryService inventoryService;

    @Autowired
    private IStockOutService stockOutService;

//    @Override
    @Transactional
    public void saveVaccinationAndDeductStock(VaccinationRecord vaccinationRecord, String username) {
        /* 查询库存表 inventory 信息
         * 如果库存表不存在该 material_id 的库存信息 则返回库存信息不存在
         * 如果请求数量大于库存数量 则返回库存不足
         */
//        QueryWrapper<Inventory> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("material_id", vaccinationRecord.getVaccineId());
//        Inventory inventory = inventoryService.getOne(queryWrapper);
//
//        if (inventory == null) {
//            throw new JeecgBootException("库存信息不存在！");
//        }
//
//        // 获取使用剂量
//
//        int usageQuantity = vaccinationRecord.getDosage().intValue();
//
//        if (inventory.getCurrentQuantity().compareTo(BigDecimal.valueOf(usageQuantity)) < 0) {
//            throw new JeecgBootException("库存不足，无法完成免疫操作！");
//        }
//
//        // 保存免疫记录
//        this.save(vaccinationRecord);
//
//        // 减少库存表中对应物品的数量
//        inventory.setCurrentQuantity(inventory.getCurrentQuantity().subtract(BigDecimal.valueOf(usageQuantity)));
//        inventory.setLastOutDate(vaccinationRecord.getVaccinationDate());
//        inventoryService.updateById(inventory);
//
//        // 往 stock_out 出库表中添加记录
//        StockOut stockOut = new StockOut();
//        stockOut.setMaterialId(vaccinationRecord.getVaccineId());
//        stockOut.setQuantity(vaccinationRecord.getDosage());
//        stockOut.setOutDate(vaccinationRecord.getVaccinationDate());
//        stockOut.setPurpose("免疫");
//        stockOut.setOperator(username);
//        stockOut.setNote("使用免疫出库记录");
//        stockOutService.save(stockOut);
    }
}
