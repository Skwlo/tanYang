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
    title: '旧普通耳号',
    align:"center",
    dataIndex: 'oldCommonEarTag'
   },
   {
    title: '新普通耳号',
    align:"center",
    dataIndex: 'newCommonEarTag'
   },
   {
    title: '旧电子耳号',
    align:"center",
    dataIndex: 'oldElectronicEarTag'
   },
   {
    title: '新电子耳号',
    align:"center",
    dataIndex: 'newElectronicEarTag'
   },
   {
    title: '更换日期',
    align:"center",
    dataIndex: 'changeDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '更换原因',
    align:"center",
    dataIndex: 'reason'
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
      label: "旧普通耳号",
      field: 'oldCommonEarTag',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "新普通耳号",
      field: 'newCommonEarTag',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "旧电子耳号",
      field: 'oldElectronicEarTag',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "新电子耳号",
      field: 'newElectronicEarTag',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "更换日期",
      field: 'changeDate',
      component: 'DatePicker',
      componentProps: {
        valueFormat: 'YYYY-MM-DD'
      },
      //colProps: {span: 6},
 	},
	{
      label: "更换原因",
      field: 'reason',
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
    label: '旧普通耳号',
    field: 'oldCommonEarTag',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入旧普通耳号!'},
          ];
     },
  },
  {
    label: '新普通耳号',
    field: 'newCommonEarTag',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入新普通耳号!'},
          ];
     },
  },
  {
    label: '旧电子耳号',
    field: 'oldElectronicEarTag',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入旧电子耳号!'},
          ];
     },
  },
  {
    label: '新电子耳号',
    field: 'newElectronicEarTag',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入新电子耳号!'},
          ];
     },
  },
  {
    label: '更换日期',
    field: 'changeDate',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入更换日期!'},
          ];
     },
  },
  {
    label: '更换原因',
    field: 'reason',
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
  livestockId: {title: '畜只 ID',order: 0,view: 'text', type: 'string',},
  oldCommonEarTag: {title: '旧普通耳号',order: 1,view: 'text', type: 'string',},
  newCommonEarTag: {title: '新普通耳号',order: 2,view: 'text', type: 'string',},
  oldElectronicEarTag: {title: '旧电子耳号',order: 3,view: 'text', type: 'string',},
  newElectronicEarTag: {title: '新电子耳号',order: 4,view: 'text', type: 'string',},
  changeDate: {title: '更换日期',order: 5,view: 'date', type: 'string',},
  reason: {title: '更换原因',order: 6,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}