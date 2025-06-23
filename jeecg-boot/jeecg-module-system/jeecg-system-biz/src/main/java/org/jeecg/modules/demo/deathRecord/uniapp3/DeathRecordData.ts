import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '畜只 ID',
    align:"center",
    dataIndex: 'livestockId'
   },
   {
    title: '死亡日期',
    align:"center",
    dataIndex: 'deathDate',
   },
   {
    title: '死亡原因',
    align:"center",
    dataIndex: 'cause'
   },
   {
    title: '死亡地点',
    align:"center",
    dataIndex: 'location'
   },
   {
    title: '处理方式',
    align:"center",
    dataIndex: 'disposalMethod'
   },
   {
    title: '备注',
    align:"center",
    dataIndex: 'note'
   },
];