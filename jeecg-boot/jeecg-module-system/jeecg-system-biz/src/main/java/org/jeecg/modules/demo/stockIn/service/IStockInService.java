package org.jeecg.modules.demo.stockIn.service;

import org.jeecg.modules.demo.stockIn.entity.StockIn;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 入库记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
public interface IStockInService extends IService<StockIn> {

    void handleStockIn(StockIn stockIn) throws Exception;
}
