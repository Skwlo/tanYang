import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '物资名称',
    align:"center",
    dataIndex: 'name'
   },
   {
    title: '物资类型（饲料 / 药品 / 疫苗）',
    align:"center",
    dataIndex: 'type_dictText'
   },
   {
    title: '规格含量',
    align:"center",
    dataIndex: 'specification'
   },
   {
    title: '计量单位',
    align:"center",
    dataIndex: 'unit'
   },
   {
    title: '	警戒数量',
    align:"center",
    dataIndex: 'alertQuantity'
   },
   {
    title: '是否是药品疫苗（1/0）',
    align:"center",
    dataIndex: 'isMedicine_dictText'
   },
   {
    title: '有效期天数（用于计算过期时间）',
    align:"center",
    dataIndex: 'expirationDays'
   },
   {
    title: '说明',
    align:"center",
    dataIndex: 'note'
   },
   {
    title: '物资 ID',
    align:"center",
    dataIndex: 'materialId'
   },
];