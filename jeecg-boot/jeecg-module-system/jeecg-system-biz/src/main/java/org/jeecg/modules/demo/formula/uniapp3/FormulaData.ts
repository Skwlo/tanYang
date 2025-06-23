import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '配方名称',
    align:"center",
    dataIndex: 'name'
   },
   {
    title: '适用阶段（幼崽 / 育肥 / 成年）',
    align:"center",
    dataIndex: 'applicableStage_dictText'
   },
   {
    title: '配方说明',
    align:"center",
    dataIndex: 'description'
   },
];