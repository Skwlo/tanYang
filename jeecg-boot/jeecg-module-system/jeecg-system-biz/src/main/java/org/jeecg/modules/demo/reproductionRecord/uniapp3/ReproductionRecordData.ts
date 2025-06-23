import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '配种记录 ID',
    align:"center",
    dataIndex: 'breedingId'
   },
   {
    title: '产崽日期',
    align:"center",
    dataIndex: 'reproductionDate',
   },
   {
    title: '产崽数量',
    align:"center",
    dataIndex: 'offspringCount_dictText'
   },
   {
    title: '活崽数量',
    align:"center",
    dataIndex: 'liveOffspringCount_dictText'
   },
   {
    title: '产崽状态（正常 / 难产）',
    align:"center",
    dataIndex: 'status_dictText'
   },
];