import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '治疗方案 ID',
    align:"center",
    dataIndex: 'treatmentPlanId'
   },
   {
    title: '药物 ID',
    align:"center",
    dataIndex: 'materialId'
   },
   {
    title: '剂量',
    align:"center",
    dataIndex: 'dosage'
   },
   {
    title: '用法',
    align:"center",
    dataIndex: 'usag'
   },
];