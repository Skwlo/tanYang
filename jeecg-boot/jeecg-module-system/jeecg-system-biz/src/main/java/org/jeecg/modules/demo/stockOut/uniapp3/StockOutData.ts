import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '物资 ID',
    align:"center",
    dataIndex: 'materialId'
   },
   {
    title: '出库日期',
    align:"center",
    dataIndex: 'outDate',
   },
   {
    title: '出库数量',
    align:"center",
    dataIndex: 'quantity'
   },
   {
    title: '用途（喂食 / 治疗）',
    align:"center",
    dataIndex: 'purpose_dictText'
   },
   {
    title: '操作人',
    align:"center",
    dataIndex: 'operator'
   },
   {
    title: '说明',
    align:"center",
    dataIndex: 'note'
   },
];