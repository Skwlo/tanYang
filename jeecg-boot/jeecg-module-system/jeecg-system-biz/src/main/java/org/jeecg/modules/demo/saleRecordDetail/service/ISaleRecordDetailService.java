package org.jeecg.modules.demo.saleRecordDetail.service;

import org.jeecg.modules.demo.saleRecordDetail.entity.SaleRecordDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.demo.stockIn.entity.StockIn;

/**
 * @Description: 销售明细表
 * @Author: jeecg-boot
 * @Date:   2025-06-13
 * @Version: V1.0
 */
public interface ISaleRecordDetailService extends IService<SaleRecordDetail> {
    void changeStatus(SaleRecordDetail saleRecordDetail) throws Exception;
}
