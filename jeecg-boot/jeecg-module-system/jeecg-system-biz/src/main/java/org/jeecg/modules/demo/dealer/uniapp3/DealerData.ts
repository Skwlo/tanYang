import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '经销商名称',
    align:"center",
    dataIndex: 'name'
   },
   {
    title: '销售类型（饲料 / 药品 / 畜只）',
    align:"center",
    dataIndex: 'type_dictText'
   },
   {
    title: '地址',
    align:"center",
    dataIndex: 'address'
   },
   {
    title: '联系人',
    align:"center",
    dataIndex: 'contactPerson'
   },
   {
    title: '电话',
    align:"center",
    dataIndex: 'phone'
   },
   {
    title: '说明',
    align:"center",
    dataIndex: 'note'
   },
   {
    title: '经销商 ID',
    align:"center",
    dataIndex: 'dealerId'
   },
];