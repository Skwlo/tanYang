import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '喂食日期',
    align:"center",
    dataIndex: 'feedDate',
   },
   {
    title: '喂食棚栏 ID',
    align:"center",
    dataIndex: 'shedPenId'
   },
   {
    title: '使用配方 ID',
    align:"center",
    dataIndex: 'formulaId'
   },
   {
    title: '投料类别（精料 / 粗料）',
    align:"center",
    dataIndex: 'feedType_dictText'
   },
   {
    title: '喂食数量 (kg)',
    align:"center",
    dataIndex: 'quantity'
   },
   {
    title: '操作人',
    align:"center",
    dataIndex: 'operator_dictText'
   },
];