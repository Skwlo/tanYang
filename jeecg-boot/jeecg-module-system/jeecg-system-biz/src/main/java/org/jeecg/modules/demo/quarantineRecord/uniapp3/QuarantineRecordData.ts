import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '畜只 ID（可选，群体检疫时为空）',
    align:"center",
    dataIndex: 'livestockId'
   },
   {
    title: '棚栏 ID（群体检疫时使用）',
    align:"center",
    dataIndex: 'shedPenId'
   },
   {
    title: '检疫日期',
    align:"center",
    dataIndex: 'quarantineDate',
   },
   {
    title: '检疫项目',
    align:"center",
    dataIndex: 'item'
   },
   {
    title: '检疫结果',
    align:"center",
    dataIndex: 'result'
   },
   {
    title: '检疫报告文件路径',
    align:"center",
    dataIndex: 'reportPath'
   },
];