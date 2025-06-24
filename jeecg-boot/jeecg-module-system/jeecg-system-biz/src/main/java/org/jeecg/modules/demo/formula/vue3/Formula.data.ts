import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '配方名称',
    align:"center",
    dataIndex: 'name'
   },
   {
    title: '适用阶段（幼崽 / 育肥 / 成年）',
    align:"center",
    dataIndex: 'applicableStage_dictText'
   },
   {
    title: '配方说明',
    align:"center",
    dataIndex: 'description'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "配方名称",
      field: 'name',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "适用阶段（幼崽 / 育肥 / 成年）",
      field: 'applicableStage',
      component: 'JSelectMultiple',
      componentProps:{
      },
      //colProps: {span: 6},
 	},
	{
      label: "配方说明",
      field: 'description',
      component: 'Input',
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '配方名称',
    field: 'name',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入配方名称!'},
          ];
     },
  },
  {
    label: '适用阶段（幼崽 / 育肥 / 成年）',
    field: 'applicableStage',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"peifang"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入适用阶段（幼崽 / 育肥 / 成年）!'},
          ];
     },
  },
  {
    label: '配方说明',
    field: 'description',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入配方说明!'},
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
  name: {title: '配方名称',order: 0,view: 'text', type: 'string',},
  applicableStage: {title: '适用阶段（幼崽 / 育肥 / 成年）',order: 1,view: 'list', type: 'string',dictCode: 'peifang',},
  description: {title: '配方说明',order: 2,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
