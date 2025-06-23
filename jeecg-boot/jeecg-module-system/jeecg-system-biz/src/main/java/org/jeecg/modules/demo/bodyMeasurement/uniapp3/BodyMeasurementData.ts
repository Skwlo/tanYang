import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '畜只 ID',
    align:"center",
    dataIndex: 'livestockId'
   },
   {
    title: '测量日期',
    align:"center",
    dataIndex: 'measureDate',
   },
   {
    title: '测量阶段（羔羊期 / 育肥期等）',
    align:"center",
    dataIndex: 'measureStage_dictText'
   },
   {
    title: '月龄',
    align:"center",
    dataIndex: 'age'
   },
   {
    title: '体高 (cm)',
    align:"center",
    dataIndex: 'bodyHeight'
   },
   {
    title: '体重(kg)',
    align:"center",
    dataIndex: 'bodyWeight'
   },
   {
    title: '体长(cm)',
    align:"center",
    dataIndex: 'bodyLength'
   },
   {
    title: '胸围(cm)',
    align:"center",
    dataIndex: 'bust'
   },
   {
    title: '管围(cm)',
    align:"center",
    dataIndex: 'pipeCircumference'
   },
];