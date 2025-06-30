/*
 * @Author: kelemengqi 1565916105@qq.com
 * @Date: 2025-06-09 09:14:16
 * @LastEditors: kelemengqi 1565916105@qq.com
 * @LastEditTime: 2025-06-26 11:12:04
 * @FilePath: /JeecgBoot-master/jeecgboot-vue3/src/views/dashboard/Analysis/api.ts
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import { defHttp } from '/@/utils/http/axios';

enum Api {
  loginfo = '/sys/loginfo',
  visitInfo = '/sys/visitInfo',
}
/**
 * 日志统计信息
 * @param params
 */
export const getLoginfo = (params) => defHttp.get({ url: Api.loginfo, params }, { isTransformResponse: false });
/**
 * 访问量信息
 * @param params
 */
export const getVisitInfo = (params) => defHttp.get({ url: Api.visitInfo, params }, { isTransformResponse: false });
