import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '销售记录 ID',
    align:"center",
    dataIndex: 'saleRecordId'
   },
   {
    title: '畜只 ID',
    align:"center",
    dataIndex: 'livestockId'
   },
   {
    title: '单价',
    align:"center",
    dataIndex: 'unitPrice'
   },
   {
    title: '数量（通常为 1）',
    align:"center",
    dataIndex: 'quantity'
   },
   {
    title: '单位（头 / 公斤）',
    align:"center",
    dataIndex: 'unit'
   },
   {
    title: '小计金额',
    align:"center",
    dataIndex: 'amount'
   },
];