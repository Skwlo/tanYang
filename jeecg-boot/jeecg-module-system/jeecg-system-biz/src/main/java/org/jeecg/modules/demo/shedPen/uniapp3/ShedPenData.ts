import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '名称',
    align:"center",
    dataIndex: 'name'
   },
   {
    title: '类型（场 / 棚 / 栏）',
    align:"center",
    dataIndex: 'type_dictText'
   },
   {
    title: '父级 ID（场无父级，棚父级为场，栏父级为棚）',
    align:"center",
    dataIndex: 'parentId'
   },
   {
    title: '容量（可容纳畜只数量）',
    align:"center",
    dataIndex: 'capacity'
   },
   {
    title: '说明',
    align:"center",
    dataIndex: 'note'
   },
   {
    title: '棚栏 ID',
    align:"center",
    dataIndex: 'shedPenId'
   },
];