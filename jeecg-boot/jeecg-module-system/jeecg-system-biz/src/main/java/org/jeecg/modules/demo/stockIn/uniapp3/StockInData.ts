import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '物资 ID',
    align:"center",
    dataIndex: 'materialId'
   },
   {
    title: '入库日期',
    align:"center",
    dataIndex: 'inDate',
   },
   {
    title: '入库数量',
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
    title: '经销商 ID',
    align:"center",
    dataIndex: 'dealerId'
   },
   {
    title: '运费',
    align:"center",
    dataIndex: 'freight'
   },
   {
    title: '装卸费',
    align:"center",
    dataIndex: 'handlingFee'
   },
   {
    title: '其他费用',
    align:"center",
    dataIndex: 'otherFee'
   },
   {
    title: '说明',
    align:"center",
    dataIndex: 'note'
   },
];