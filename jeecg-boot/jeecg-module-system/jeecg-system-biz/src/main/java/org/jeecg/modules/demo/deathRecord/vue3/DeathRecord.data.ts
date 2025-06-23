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
    title: '死亡日期',
    align:"center",
    dataIndex: 'deathDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '死亡原因',
    align:"center",
    dataIndex: 'cause'
   },
   {
    title: '死亡地点',
    align:"center",
    dataIndex: 'location'
   },
   {
    title: '处理方式',
    align:"center",
    dataIndex: 'disposalMethod'
   },
   {
    title: '备注',
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
      label: "死亡日期",
      field: 'deathDate',
      component: 'DatePicker',
      componentProps: {
        valueFormat: 'YYYY-MM-DD'
      },
      //colProps: {span: 6},
 	},
	{
      label: "死亡原因",
      field: 'cause',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "死亡地点",
      field: 'location',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "处理方式",
      field: 'disposalMethod',
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
    label: '死亡日期',
    field: 'deathDate',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入死亡日期!'},
          ];
     },
  },
  {
    label: '死亡原因',
    field: 'cause',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入死亡原因!'},
          ];
     },
  },
  {
    label: '死亡地点',
    field: 'location',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入死亡地点!'},
          ];
     },
  },
  {
    label: '处理方式',
    field: 'disposalMethod',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入处理方式!'},
          ];
     },
  },
  {
    label: '备注',
    field: 'note',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入备注!'},
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
  deathDate: {title: '死亡日期',order: 1,view: 'date', type: 'string',},
  cause: {title: '死亡原因',order: 2,view: 'text', type: 'string',},
  location: {title: '死亡地点',order: 3,view: 'text', type: 'string',},
  disposalMethod: {title: '处理方式',order: 4,view: 'text', type: 'string',},
  note: {title: '备注',order: 5,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}