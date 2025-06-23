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
    title: '发病日期',
    align:"center",
    dataIndex: 'onsetDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '疾病类型',
    align:"center",
    dataIndex: 'diseaseType_dictText'
   },
   {
    title: '主要症状',
    align:"center",
    dataIndex: 'symptoms'
   },
   {
    title: '发病原因',
    align:"center",
    dataIndex: 'cause'
   },
   {
    title: '治疗方案 ID',
    align:"center",
    dataIndex: 'treatmentPlanId'
   },
   {
    title: '兽医师',
    align:"center",
    dataIndex: 'veterinarian'
   },
   {
    title: '是否治愈（1/0）	',
    align:"center",
    dataIndex: 'isCured_dictText'
   },
   {
    title: '治疗说明',
    align:"center",
    dataIndex: 'note'
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
      label: "发病日期",
      field: 'onsetDate',
      component: 'DatePicker',
      componentProps: {
        valueFormat: 'YYYY-MM-DD'
      },
      //colProps: {span: 6},
 	},
	{
      label: "疾病类型",
      field: 'diseaseType',
      component: 'JSelectMultiple',
      componentProps:{
      },
      //colProps: {span: 6},
 	},
	{
      label: "主要症状",
      field: 'symptoms',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "发病原因",
      field: 'cause',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "治疗方案 ID",
      field: 'treatmentPlanId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "兽医师",
      field: 'veterinarian',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "是否治愈（1/0）	",
      field: 'isCured',
      component: 'JSelectMultiple',
      componentProps:{
      },
      //colProps: {span: 6},
 	},
	{
      label: "治疗说明",
      field: 'note',
      component: 'Input',
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
    label: '发病日期',
    field: 'onsetDate',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入发病日期!'},
          ];
     },
  },
  {
    label: '疾病类型',
    field: 'diseaseType',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:""
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入疾病类型!'},
          ];
     },
  },
  {
    label: '主要症状',
    field: 'symptoms',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入主要症状!'},
          ];
     },
  },
  {
    label: '发病原因',
    field: 'cause',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入发病原因!'},
          ];
     },
  },
  {
    label: '治疗方案 ID',
    field: 'treatmentPlanId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入治疗方案 ID!'},
          ];
     },
  },
  {
    label: '兽医师',
    field: 'veterinarian',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入兽医师!'},
          ];
     },
  },
  {
    label: '是否治愈（1/0）	',
    field: 'isCured',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:""
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入是否治愈（1/0）	!'},
          ];
     },
  },
  {
    label: '治疗说明',
    field: 'note',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入治疗说明!'},
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
  onsetDate: {title: '发病日期',order: 1,view: 'date', type: 'string',},
  diseaseType: {title: '疾病类型',order: 2,view: 'list', type: 'string',dictCode: '',},
  symptoms: {title: '主要症状',order: 3,view: 'text', type: 'string',},
  cause: {title: '发病原因',order: 4,view: 'text', type: 'string',},
  treatmentPlanId: {title: '治疗方案 ID',order: 5,view: 'text', type: 'string',},
  veterinarian: {title: '兽医师',order: 6,view: 'text', type: 'string',},
  isCured: {title: '是否治愈（1/0）	',order: 7,view: 'list', type: 'string',dictCode: '',},
  note: {title: '治疗说明',order: 8,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}