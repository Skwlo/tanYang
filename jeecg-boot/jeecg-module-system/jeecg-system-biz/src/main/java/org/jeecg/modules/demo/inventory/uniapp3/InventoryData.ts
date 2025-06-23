import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '当前库存数量',
    align:"center",
    dataIndex: 'currentQuantity'
   },
   {
    title: '最后入库日期',
    align:"center",
    dataIndex: 'lastInDate',
   },
   {
    title: '最后出库日期',
    align:"center",
    dataIndex: 'lastOutDate',
   },
   {
    title: '物资id',
    align:"center",
    dataIndex: 'materialId'
   },
];