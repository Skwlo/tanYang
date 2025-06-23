import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '配方 ID',
    align:"center",
    dataIndex: 'formulaId'
   },
   {
    title: '原料物资 ID',
    align:"center",
    dataIndex: 'materialId'
   },
   {
    title: '占比 (%)',
    align:"center",
    dataIndex: 'proportion'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "配方 ID",
      field: 'formulaId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "原料物资 ID",
      field: 'materialId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "占比 (%)",
      field: 'proportion',
      component: 'Input',
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '配方 ID',
    field: 'formulaId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入配方 ID!'},
          ];
     },
  },
  {
    label: '原料物资 ID',
    field: 'materialId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入原料物资 ID!'},
          ];
     },
  },
  {
    label: '占比 (%)',
    field: 'proportion',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入占比 (%)!'},
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
  formulaId: {title: '配方 ID',order: 0,view: 'text', type: 'string',},
  materialId: {title: '原料物资 ID',order: 1,view: 'text', type: 'string',},
  proportion: {title: '占比 (%)',order: 2,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}