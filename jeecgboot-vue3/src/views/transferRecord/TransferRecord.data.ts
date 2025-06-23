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
    dataIndex: 'transferRecord'
   },
   {
    title: '原棚栏 ID',
    align:"center",
    dataIndex: 'fromShedPenId'
   },
   {
    title: '目标棚栏 ID',
    align:"center",
    dataIndex: 'toShedPenId'
   },
   {
    title: '转群日期',
    align:"center",
    dataIndex: 'transferDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '转群原因',
    align:"center",
    dataIndex: 'reason'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "畜只 ID",
      field: 'transferRecord',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "原棚栏 ID",
      field: 'fromShedPenId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "目标棚栏 ID",
      field: 'toShedPenId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "转群日期",
      field: 'transferDate',
      component: 'DatePicker',
      componentProps: {
        valueFormat: 'YYYY-MM-DD'
      },
      //colProps: {span: 6},
 	},
	{
      label: "转群原因",
      field: 'reason',
      component: 'Input',
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '畜只 ID',
    field: 'transferRecord',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入畜只 ID!'},
          ];
     },
  },
  {
    label: '原棚栏 ID',
    field: 'fromShedPenId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入原棚栏 ID!'},
          ];
     },
  },
  {
    label: '目标棚栏 ID',
    field: 'toShedPenId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入目标棚栏 ID!'},
          ];
     },
  },
  {
    label: '转群日期',
    field: 'transferDate',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入转群日期!'},
          ];
     },
  },
  {
    label: '转群原因',
    field: 'reason',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入转群原因!'},
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
  transferRecord: {title: '畜只 ID',order: 0,view: 'text', type: 'string',},
  fromShedPenId: {title: '原棚栏 ID',order: 1,view: 'text', type: 'string',},
  toShedPenId: {title: '目标棚栏 ID',order: 2,view: 'text', type: 'string',},
  transferDate: {title: '转群日期',order: 3,view: 'date', type: 'string',},
  reason: {title: '转群原因',order: 4,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}