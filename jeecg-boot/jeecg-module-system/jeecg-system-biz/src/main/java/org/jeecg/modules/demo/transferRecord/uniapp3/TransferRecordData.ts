import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '畜只 ID',
    align:"center",
    dataIndex: 'transferRecord'
   },
   {
    title: '原棚栏 ID',
    align:"center",
    dataIndex: 'fromShedPenId'
   },
   {
    title: '目标棚栏 ID',
    align:"center",
    dataIndex: 'toShedPenId'
   },
   {
    title: '转群日期',
    align:"center",
    dataIndex: 'transferDate',
   },
   {
    title: '转群原因',
    align:"center",
    dataIndex: 'reason'
   },
];