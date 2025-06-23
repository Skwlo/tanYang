import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '畜只 ID',
    align:"center",
    dataIndex: 'livestockId'
   },
   {
    title: '旧普通耳号',
    align:"center",
    dataIndex: 'oldCommonEarTag'
   },
   {
    title: '新普通耳号',
    align:"center",
    dataIndex: 'newCommonEarTag'
   },
   {
    title: '旧电子耳号',
    align:"center",
    dataIndex: 'oldElectronicEarTag'
   },
   {
    title: '新电子耳号',
    align:"center",
    dataIndex: 'newElectronicEarTag'
   },
   {
    title: '更换日期',
    align:"center",
    dataIndex: 'changeDate',
   },
   {
    title: '更换原因',
    align:"center",
    dataIndex: 'reason'
   },
];