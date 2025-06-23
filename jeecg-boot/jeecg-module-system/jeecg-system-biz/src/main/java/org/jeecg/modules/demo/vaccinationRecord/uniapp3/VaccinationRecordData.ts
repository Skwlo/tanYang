import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '畜只 ID（可选，群体免疫时为空）',
    align:"center",
    dataIndex: 'livestockId'
   },
   {
    title: '疫苗 ID',
    align:"center",
    dataIndex: 'vaccineId'
   },
   {
    title: '免疫日期',
    align:"center",
    dataIndex: 'vaccinationDate',
   },
   {
    title: '使用剂量',
    align:"center",
    dataIndex: 'dosage'
   },
   {
    title: '注射部位',
    align:"center",
    dataIndex: 'injectionSite'
   },
   {
    title: '合格指标',
    align:"center",
    dataIndex: 'standard'
   },
   {
    title: '免疫范围（个体 / 群体）',
    align:"center",
    dataIndex: 'scope_dictText'
   },
   {
    title: '说明',
    align:"center",
    dataIndex: 'note'
   },
];