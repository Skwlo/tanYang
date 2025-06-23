import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '物资 ID',
    align:"center",
    dataIndex: 'materialId'
   },
   {
    title: '出库日期',
    align:"center",
    dataIndex: 'outDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '出库数量',
    align:"center",
    dataIndex: 'quantity'
   },
   {
    title: '用途（喂食 / 治疗）',
    align:"center",
    dataIndex: 'purpose_dictText'
   },
   {
    title: '操作人',
    align:"center",
    dataIndex: 'operator'
   },
   {
    title: '说明',
    align:"center",
    dataIndex: 'note'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "物资 ID",
      field: 'materialId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "出库日期",
      field: 'outDate',
      component: 'DatePicker',
      componentProps: {
        valueFormat: 'YYYY-MM-DD'
      },
      //colProps: {span: 6},
 	},
	{
      label: "出库数量",
      field: 'quantity',
      component: 'InputNumber',
      //colProps: {span: 6},
 	},
	{
      label: "用途（喂食 / 治疗）",
      field: 'purpose',
      component: 'JSelectMultiple',
      componentProps:{
      },
      //colProps: {span: 6},
 	},
	{
      label: "操作人",
      field: 'operator',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "说明",
      field: 'note',
      component: 'Input',
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
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
  {
    label: '出库日期',
    field: 'outDate',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入出库日期!'},
          ];
     },
  },
  {
    label: '出库数量',
    field: 'quantity',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入出库数量!'},
          ];
     },
  },
  {
    label: '用途（喂食 / 治疗）',
    field: 'purpose',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:""
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入用途（喂食 / 治疗）!'},
          ];
     },
  },
  {
    label: '操作人',
    field: 'operator',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入操作人!'},
          ];
     },
  },
  {
    label: '说明',
    field: 'note',
    component: 'Input',
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
  materialId: {title: '物资 ID',order: 0,view: 'text', type: 'string',},
  outDate: {title: '出库日期',order: 1,view: 'date', type: 'string',},
  quantity: {title: '出库数量',order: 2,view: 'number', type: 'number',},
  purpose: {title: '用途（喂食 / 治疗）',order: 3,view: 'list', type: 'string',dictCode: '',},
  operator: {title: '操作人',order: 4,view: 'text', type: 'string',},
  note: {title: '说明',order: 5,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}