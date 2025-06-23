import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '疾病类型',
    align:"center",
    dataIndex: 'diseaseType_dictText'
   },
   {
    title: '主要症状',
    align:"center",
    dataIndex: 'mainSymptoms'
   },
   {
    title: '发病原因',
    align:"center",
    dataIndex: 'cause'
   },
   {
    title: '药物治疗种类',
    align:"center",
    dataIndex: 'medicationType_dictText'
   },
   {
    title: '使用方式',
    align:"center",
    dataIndex: 'usageMethod_dictText'
   },
   {
    title: '治疗说明	',
    align:"center",
    dataIndex: 'note'
   },
];