import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '销售日期',
    align:"center",
    dataIndex: 'saleDate',
   },
   {
    title: '客户名称',
    align:"center",
    dataIndex: 'customerName'
   },
   {
    title: '客户地址',
    align:"center",
    dataIndex: 'customerAddress'
   },
   {
    title: '客户电话',
    align:"center",
    dataIndex: 'customerPhone'
   },
   {
    title: '销售人员',
    align:"center",
    dataIndex: 'salesman'
   },
   {
    title: '销售总额',
    align:"center",
    dataIndex: 'totalAmount'
   },
   {
    title: '说明',
    align:"center",
    dataIndex: 'note'
   },
];