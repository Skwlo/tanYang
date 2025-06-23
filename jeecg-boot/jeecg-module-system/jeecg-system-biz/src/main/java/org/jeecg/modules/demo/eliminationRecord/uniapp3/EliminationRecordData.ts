import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '畜只 ID',
    align:"center",
    dataIndex: 'livestockId'
   },
   {
    title: '淘汰日期',
    align:"center",
    dataIndex: 'eliminationDate',
   },
   {
    title: '淘汰前类别',
    align:"center",
    dataIndex: 'beforeCategory_dictText'
   },
   {
    title: '淘汰原因',
    align:"center",
    dataIndex: 'eliminationReason'
   },
   {
    title: '说明',
    align:"center",
    dataIndex: 'note'
   },
];