import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '普通耳号',
    align:"center",
    dataIndex: 'commonEarTag'
   },
   {
    title: '电子耳号',
    align:"center",
    dataIndex: 'electronicEarTag'
   },
   {
    title: '品种（如滩羊）',
    align:"center",
    dataIndex: 'breed_dictText'
   },
   {
    title: '类别（种羊、肉羊等）',
    align:"center",
    dataIndex: 'category_dictText'
   },
   {
    title: '性别（M/F）',
    align:"center",
    dataIndex: 'gender_dictText'
   },
   {
    title: '出生日期',
    align:"center",
    dataIndex: 'birthDate',
   },
   {
    title: '来源（采购 / 自繁）',
    align:"center",
    dataIndex: 'source_dictText'
   },
   {
    title: '状态（正常 / 死亡 / 淘汰 / 已售）',
    align:"center",
    dataIndex: 'status_dictText'
   },
   {
    title: '当前阶段（幼崽 / 育肥 / 成年）',
    align:"center",
    dataIndex: 'currentStage_dictText'
   },
   {
    title: '当前所在栅栏ID',
    align:"center",
    dataIndex: 'currentShedPenId'
   },
   {
    title: '父亲畜只 ID（可选）',
    align:"center",
    dataIndex: 'fatherId'
   },
   {
    title: '母亲畜只 ID（可选）',
    align:"center",
    dataIndex: 'motherId'
   },
   {
    title: '当前体重 (kg)（可选，可通过体尺测量更新）',
    align:"center",
    dataIndex: 'weight'
   },
   {
    title: '采购记录 ID（可选）',
    align:"center",
    dataIndex: 'purchaseInfoId'
   },
   {
    title: '畜只唯一标识（电子耳号）',
    align:"center",
    dataIndex: 'livestockId'
   },
];