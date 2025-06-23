import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '采购日期',
    align:"center",
    dataIndex: 'purchaseDate',
   },
   {
    title: '采购人',
    align:"center",
    dataIndex: 'seller'
   },
   {
    title: '采购数量',
    align:"center",
    dataIndex: 'quantity'
   },
   {
    title: '单价',
    align:"center",
    dataIndex: 'unitPrice'
   },
   {
    title: '总价',
    align:"center",
    dataIndex: 'totalPrice'
   },
   {
    title: '备注',
    align:"center",
    dataIndex: 'note'
   },
];