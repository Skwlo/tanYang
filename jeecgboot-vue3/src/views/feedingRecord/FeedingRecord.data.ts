import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '喂食日期',
    align:"center",
    dataIndex: 'feedDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '喂食棚栏 ID',
    align:"center",
    dataIndex: 'shedPenId'
   },
   {
    title: '使用配方 ID',
    align:"center",
    dataIndex: 'formulaId'
   },
   {
    title: '投料类别（精料 / 粗料）',
    align:"center",
    dataIndex: 'feedType_dictText'
   },
   {
    title: '喂食数量 (kg)',
    align:"center",
    dataIndex: 'quantity'
   },
   {
    title: '操作人',
    align:"center",
    dataIndex: 'operator_dictText'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "喂食日期",
      field: 'feedDate',
      component: 'DatePicker',
      componentProps: {
        valueFormat: 'YYYY-MM-DD'
      },
      //colProps: {span: 6},
 	},
	{
      label: "喂食棚栏 ID",
      field: 'shedPenId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "使用配方 ID",
      field: 'formulaId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "投料类别（精料 / 粗料）",
      field: 'feedType',
      component: 'JSelectMultiple',
      componentProps:{
      dictCode:"touliao"
    },
      //colProps: {span: 6},
 	},
	{
      label: "喂食数量 (kg)",
      field: 'quantity',
      component: 'InputNumber',
      //colProps: {span: 6},
 	},
	{
      label: "操作人",
      field: 'operator',
      component: 'JSelectMultiple',
      componentProps:{
      dictCode:"caozuoren"
    },
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '喂食日期',
    field: 'feedDate',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入喂食日期!'},
          ];
     },
  },
  {
    label: '喂食棚栏 ID',
    field: 'shedPenId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入喂食棚栏 ID!'},
          ];
     },
  },
  {
    label: '使用配方 ID',
    field: 'formulaId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入使用配方 ID!'},
          ];
     },
  },
  {
    label: '投料类别（精料 / 粗料）',
    field: 'feedType',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"touliao"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入投料类别（精料 / 粗料）!'},
          ];
     },
  },
  {
    label: '喂食数量 (kg)',
    field: 'quantity',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入喂食数量 (kg)!'},
          ];
     },
  },
  {
    label: '操作人',
    field: 'operator',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"caozuoren"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入操作人!'},
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
  feedDate: {title: '喂食日期',order: 0,view: 'date', type: 'string',},
  shedPenId: {title: '喂食棚栏 ID',order: 1,view: 'text', type: 'string',},
  formulaId: {title: '使用配方 ID',order: 2,view: 'text', type: 'string',},
  feedType: {title: '投料类别（精料 / 粗料）',order: 3,view: 'list', type: 'string',dictCode: 'touliao',},
  quantity: {title: '喂食数量 (kg)',order: 4,view: 'number', type: 'number',},
  operator: {title: '操作人',order: 5,view: 'list', type: 'string',dictCode: 'caozuoren',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
