import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '畜只 ID',
    align:"center",
    dataIndex: 'livestockId'
   },
   {
    title: '测量日期',
    align:"center",
    dataIndex: 'measureDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
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
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "畜只 ID",
      field: 'livestockId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "测量日期",
      field: 'measureDate',
      component: 'DatePicker',
      componentProps: {
        valueFormat: 'YYYY-MM-DD'
      },
      //colProps: {span: 6},
 	},
	{
      label: "测量阶段（羔羊期 / 育肥期等）",
      field: 'measureStage',
      component: 'JSelectMultiple',
      componentProps:{
      },
      //colProps: {span: 6},
 	},
	{
      label: "月龄",
      field: 'age',
      component: 'InputNumber',
      //colProps: {span: 6},
 	},
	{
      label: "体高 (cm)",
      field: 'bodyHeight',
      component: 'InputNumber',
      //colProps: {span: 6},
 	},
	{
      label: "体重(kg)",
      field: 'bodyWeight',
      component: 'InputNumber',
      //colProps: {span: 6},
 	},
	{
      label: "体长(cm)",
      field: 'bodyLength',
      component: 'InputNumber',
      //colProps: {span: 6},
 	},
	{
      label: "胸围(cm)",
      field: 'bust',
      component: 'InputNumber',
      //colProps: {span: 6},
 	},
	{
      label: "管围(cm)",
      field: 'pipeCircumference',
      component: 'InputNumber',
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '畜只 ID',
    field: 'livestockId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入畜只 ID!'},
          ];
     },
  },
  {
    label: '测量日期',
    field: 'measureDate',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入测量日期!'},
          ];
     },
  },
  {
    label: '测量阶段（羔羊期 / 育肥期等）',
    field: 'measureStage',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:""
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入测量阶段（羔羊期 / 育肥期等）!'},
          ];
     },
  },
  {
    label: '月龄',
    field: 'age',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入月龄!'},
          ];
     },
  },
  {
    label: '体高 (cm)',
    field: 'bodyHeight',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入体高 (cm)!'},
          ];
     },
  },
  {
    label: '体重(kg)',
    field: 'bodyWeight',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入体重(kg)!'},
          ];
     },
  },
  {
    label: '体长(cm)',
    field: 'bodyLength',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入体长(cm)!'},
          ];
     },
  },
  {
    label: '胸围(cm)',
    field: 'bust',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入胸围(cm)!'},
          ];
     },
  },
  {
    label: '管围(cm)',
    field: 'pipeCircumference',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入管围(cm)!'},
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
  livestockId: {title: '畜只 ID',order: 0,view: 'text', type: 'string',},
  measureDate: {title: '测量日期',order: 1,view: 'date', type: 'string',},
  measureStage: {title: '测量阶段（羔羊期 / 育肥期等）',order: 2,view: 'list', type: 'string',dictCode: '',},
  age: {title: '月龄',order: 3,view: 'number', type: 'number',},
  bodyHeight: {title: '体高 (cm)',order: 4,view: 'number', type: 'number',},
  bodyWeight: {title: '体重(kg)',order: 5,view: 'number', type: 'number',},
  bodyLength: {title: '体长(cm)',order: 6,view: 'number', type: 'number',},
  bust: {title: '胸围(cm)',order: 7,view: 'number', type: 'number',},
  pipeCircumference: {title: '管围(cm)',order: 8,view: 'number', type: 'number',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}