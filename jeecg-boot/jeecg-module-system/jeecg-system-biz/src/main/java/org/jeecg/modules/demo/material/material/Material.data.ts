import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
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
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "物资名称",
      field: 'name',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "物资类型（饲料 / 药品 / 疫苗）",
      field: 'type',
      component: 'JSelectMultiple',
      componentProps:{
      },
      //colProps: {span: 6},
 	},
	{
      label: "规格含量",
      field: 'specification',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "计量单位",
      field: 'unit',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "	警戒数量",
      field: 'alertQuantity',
      component: 'InputNumber',
      //colProps: {span: 6},
 	},
	{
      label: "是否是药品疫苗（1/0）",
      field: 'isMedicine',
      component: 'JSelectMultiple',
      componentProps:{
      },
      //colProps: {span: 6},
 	},
	{
      label: "有效期天数（用于计算过期时间）",
      field: 'expirationDays',
      component: 'InputNumber',
      //colProps: {span: 6},
 	},
	{
      label: "说明",
      field: 'note',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "物资 ID",
      field: 'materialId',
      component: 'Input',
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '物资名称',
    field: 'name',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入物资名称!'},
          ];
     },
  },
  {
    label: '物资类型（饲料 / 药品 / 疫苗）',
    field: 'type',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:""
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入物资类型（饲料 / 药品 / 疫苗）!'},
          ];
     },
  },
  {
    label: '规格含量',
    field: 'specification',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入规格含量!'},
          ];
     },
  },
  {
    label: '计量单位',
    field: 'unit',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入计量单位!'},
          ];
     },
  },
  {
    label: '	警戒数量',
    field: 'alertQuantity',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入	警戒数量!'},
          ];
     },
  },
  {
    label: '是否是药品疫苗（1/0）',
    field: 'isMedicine',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:""
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入是否是药品疫苗（1/0）!'},
          ];
     },
  },
  {
    label: '有效期天数（用于计算过期时间）',
    field: 'expirationDays',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入有效期天数（用于计算过期时间）!'},
          ];
     },
  },
  {
    label: '说明',
    field: 'note',
    component: 'Input',
  },
  {
    label: '物资 ID',
    field: 'materialId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入物资 ID!'},
          ];
     },
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];

// 高级查询数据
export const superQuerySchema = {
  name: {title: '物资名称',order: 0,view: 'text', type: 'string',},
  type: {title: '物资类型（饲料 / 药品 / 疫苗）',order: 1,view: 'list', type: 'string',dictCode: '',},
  specification: {title: '规格含量',order: 2,view: 'text', type: 'string',},
  unit: {title: '计量单位',order: 3,view: 'text', type: 'string',},
  alertQuantity: {title: '	警戒数量',order: 4,view: 'number', type: 'number',},
  isMedicine: {title: '是否是药品疫苗（1/0）',order: 5,view: 'list', type: 'string',dictCode: '',},
  expirationDays: {title: '有效期天数（用于计算过期时间）',order: 6,view: 'number', type: 'number',},
  note: {title: '说明',order: 7,view: 'text', type: 'string',},
  materialId: {title: '物资 ID',order: 8,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}