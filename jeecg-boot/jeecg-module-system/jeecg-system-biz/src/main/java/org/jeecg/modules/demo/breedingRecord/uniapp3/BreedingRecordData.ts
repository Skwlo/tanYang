import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '公畜 ID',
    align:"center",
    dataIndex: 'maleLivestockId'
   },
   {
    title: '母畜 ID',
    align:"center",
    dataIndex: 'femaleLivestockId'
   },
   {
    title: '配种日期',
    align:"center",
    dataIndex: 'breedingDate',
   },
   {
    title: '预产日期',
    align:"center",
    dataIndex: 'expectedDeliveryDate',
   },
   {
    title: '配种方式（自然 / 人工）',
    align:"center",
    dataIndex: 'method_dictText'
   },
   {
    title: '配种状态（成功 / 失败）',
    align:"center",
    dataIndex: 'status_dictText'
   },
   {
    title: '是否繁育（1/0）',
    align:"center",
    dataIndex: 'isBreeding_dictText'
   },
];