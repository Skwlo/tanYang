import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '幼畜 ID',
    align:"center",
    dataIndex: 'livestockId'
   },
   {
    title: '断奶日期',
    align:"center",
    dataIndex: 'weaningDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '断奶日龄',
    align:"center",
    dataIndex: 'weaningAge'
   },
   {
    title: '断奶重量 (kg)',
    align:"center",
    dataIndex: 'weaningWeight'
   },
   {
    title: '新棚栏 ID',
    align:"center",
    dataIndex: 'newShedPenId'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "幼畜 ID",
      field: 'livestockId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "断奶日期",
      field: 'weaningDate',
      component: 'DatePicker',
      componentProps: {
        valueFormat: 'YYYY-MM-DD'
      },
      //colProps: {span: 6},
 	},
	{
      label: "断奶日龄",
      field: 'weaningAge',
      component: 'InputNumber',
      //colProps: {span: 6},
 	},
	{
      label: "断奶重量 (kg)",
      field: 'weaningWeight',
      component: 'InputNumber',
      //colProps: {span: 6},
 	},
	{
      label: "新棚栏 ID",
      field: 'newShedPenId',
      component: 'Input',
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '幼畜 ID',
    field: 'livestockId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入幼畜 ID!'},
          ];
     },
  },
  {
    label: '断奶日期',
    field: 'weaningDate',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入断奶日期!'},
          ];
     },
  },
  {
    label: '断奶日龄',
    field: 'weaningAge',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入断奶日龄!'},
          ];
     },
  },
  {
    label: '断奶重量 (kg)',
    field: 'weaningWeight',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入断奶重量 (kg)!'},
          ];
     },
  },
  {
    label: '新棚栏 ID',
    field: 'newShedPenId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入新棚栏 ID!'},
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
  livestockId: {title: '幼畜 ID',order: 0,view: 'text', type: 'string',},
  weaningDate: {title: '断奶日期',order: 1,view: 'date', type: 'string',},
  weaningAge: {title: '断奶日龄',order: 2,view: 'number', type: 'number',},
  weaningWeight: {title: '断奶重量 (kg)',order: 3,view: 'number', type: 'number',},
  newShedPenId: {title: '新棚栏 ID',order: 4,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}