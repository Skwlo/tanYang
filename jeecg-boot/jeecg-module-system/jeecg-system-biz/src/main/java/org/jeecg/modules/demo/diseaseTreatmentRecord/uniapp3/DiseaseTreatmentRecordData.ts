import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '畜只 ID',
    align:"center",
    dataIndex: 'livestockId'
   },
   {
    title: '发病日期',
    align:"center",
    dataIndex: 'onsetDate',
   },
   {
    title: '疾病类型',
    align:"center",
    dataIndex: 'diseaseType_dictText'
   },
   {
    title: '主要症状',
    align:"center",
    dataIndex: 'symptoms'
   },
   {
    title: '发病原因',
    align:"center",
    dataIndex: 'cause'
   },
   {
    title: '治疗方案 ID',
    align:"center",
    dataIndex: 'treatmentPlanId'
   },
   {
    title: '兽医师',
    align:"center",
    dataIndex: 'veterinarian'
   },
   {
    title: '是否治愈（1/0）	',
    align:"center",
    dataIndex: 'isCured_dictText'
   },
   {
    title: '治疗说明',
    align:"center",
    dataIndex: 'note'
   },
];