import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '配方 ID',
    align:"center",
    dataIndex: 'formulaId'
   },
   {
    title: '原料物资 ID',
    align:"center",
    dataIndex: 'materialId'
   },
   {
    title: '占比 (%)',
    align:"center",
    dataIndex: 'proportion'
   },
];