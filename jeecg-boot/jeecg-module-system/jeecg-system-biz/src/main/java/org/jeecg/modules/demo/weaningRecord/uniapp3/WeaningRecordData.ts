import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '幼畜 ID',
    align:"center",
    dataIndex: 'livestockId'
   },
   {
    title: '断奶日期',
    align:"center",
    dataIndex: 'weaningDate',
   },
   {
    title: '断奶日龄',
    align:"center",
    dataIndex: 'weaningAge'
   },
   {
    title: '断奶重量 (kg)',
    align:"center",
    dataIndex: 'weaningWeight'
   },
   {
    title: '新棚栏 ID',
    align:"center",
    dataIndex: 'newShedPenId'
   },
];