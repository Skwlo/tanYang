package org.jeecg.modules.demo.stockOut.service;

import org.jeecg.modules.demo.stockOut.entity.StockOut;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 出库记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
public interface IStockOutService extends IService<StockOut> {
    void handleStockOut(StockOut stockOut) throws Exception;
}
