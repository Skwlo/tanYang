import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '畜只 ID',
    align:"center",
    dataIndex: 'livestockId'
   },
   {
    title: '变更日期',
    align:"center",
    dataIndex: 'changeDate',
   },
   {
    title: '原类型',
    align:"center",
    dataIndex: 'oldType_dictText'
   },
   {
    title: '新类型',
    align:"center",
    dataIndex: 'newType_dictText'
   },
   {
    title: '变更原因',
    align:"center",
    dataIndex: 'reason'
   },
];