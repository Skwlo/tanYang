import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '治疗方案 ID',
    align:"center",
    dataIndex: 'treatmentPlanId'
   },
   {
    title: '药物 ID',
    align:"center",
    dataIndex: 'materialId'
   },
   {
    title: '剂量',
    align:"center",
    dataIndex: 'dosage'
   },
   {
    title: '用法',
    align:"center",
    dataIndex: 'usag'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "治疗方案 ID",
      field: 'treatmentPlanId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "药物 ID",
      field: 'materialId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "剂量",
      field: 'dosage',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "用法",
      field: 'usag',
      component: 'Input',
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
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
    label: '药物 ID',
    field: 'materialId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入药物 ID!'},
          ];
     },
  },
  {
    label: '剂量',
    field: 'dosage',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入剂量!'},
          ];
     },
  },
  {
    label: '用法',
    field: 'usag',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入用法!'},
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
  treatmentPlanId: {title: '治疗方案 ID',order: 0,view: 'text', type: 'string',},
  materialId: {title: '药物 ID',order: 1,view: 'text', type: 'string',},
  dosage: {title: '剂量',order: 2,view: 'text', type: 'string',},
  usag: {title: '用法',order: 3,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}