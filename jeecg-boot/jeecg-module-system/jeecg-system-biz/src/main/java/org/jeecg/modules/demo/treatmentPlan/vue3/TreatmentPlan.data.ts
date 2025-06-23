import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '疾病类型',
    align:"center",
    dataIndex: 'diseaseType_dictText'
   },
   {
    title: '主要症状',
    align:"center",
    dataIndex: 'mainSymptoms'
   },
   {
    title: '发病原因',
    align:"center",
    dataIndex: 'cause'
   },
   {
    title: '药物治疗种类',
    align:"center",
    dataIndex: 'medicationType_dictText'
   },
   {
    title: '使用方式',
    align:"center",
    dataIndex: 'usageMethod_dictText'
   },
   {
    title: '治疗说明	',
    align:"center",
    dataIndex: 'note'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
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
      field: 'mainSymptoms',
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
      label: "药物治疗种类",
      field: 'medicationType',
      component: 'JSelectMultiple',
      componentProps:{
      },
      //colProps: {span: 6},
 	},
	{
      label: "使用方式",
      field: 'usageMethod',
      component: 'JSelectMultiple',
      componentProps:{
      },
      //colProps: {span: 6},
 	},
	{
      label: "治疗说明	",
      field: 'note',
      component: 'Input',
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
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
    field: 'mainSymptoms',
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
    label: '药物治疗种类',
    field: 'medicationType',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:""
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入药物治疗种类!'},
          ];
     },
  },
  {
    label: '使用方式',
    field: 'usageMethod',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:""
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入使用方式!'},
          ];
     },
  },
  {
    label: '治疗说明	',
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
  diseaseType: {title: '疾病类型',order: 0,view: 'list', type: 'string',dictCode: '',},
  mainSymptoms: {title: '主要症状',order: 1,view: 'text', type: 'string',},
  cause: {title: '发病原因',order: 2,view: 'text', type: 'string',},
  medicationType: {title: '药物治疗种类',order: 3,view: 'list', type: 'string',dictCode: '',},
  usageMethod: {title: '使用方式',order: 4,view: 'list', type: 'string',dictCode: '',},
  note: {title: '治疗说明	',order: 5,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}