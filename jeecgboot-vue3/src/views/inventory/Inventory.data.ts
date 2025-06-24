import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '当前库存数量',
    align:"center",
    dataIndex: 'currentQuantity'
   },
   {
    title: '最后入库日期',
    align:"center",
    dataIndex: 'lastInDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '最后出库日期',
    align:"center",
    dataIndex: 'lastOutDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '物资id',
    align:"center",
    dataIndex: 'materialId'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "当前库存数量",
      field: 'currentQuantity',
      component: 'InputNumber',
      //colProps: {span: 6},
 	},
	{
      label: "最后入库日期",
      field: 'lastInDate',
      component: 'DatePicker',
      componentProps: {
        valueFormat: 'YYYY-MM-DD'
      },
      //colProps: {span: 6},
 	},
	{
      label: "最后出库日期",
      field: 'lastOutDate',
      component: 'DatePicker',
      componentProps: {
        valueFormat: 'YYYY-MM-DD'
      },
      //colProps: {span: 6},
 	},
	{
      label: "物资id",
      field: 'materialId',
      component: 'Input',
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '当前库存数量',
    field: 'currentQuantity',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入当前库存数量!'},
          ];
     },
  },
  {
    label: '最后入库日期',
    field: 'lastInDate',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入最后入库日期!'},
          ];
     },
  },
  {
    label: '最后出库日期',
    field: 'lastOutDate',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入最后出库日期!'},
          ];
     },
  },
  {
    label: '物资id',
    field: 'materialId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入物资id!'},
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
  currentQuantity: {title: '当前库存数量',order: 0,view: 'number', type: 'number',},
  lastInDate: {title: '最后入库日期',order: 1,view: 'date', type: 'string',},
  lastOutDate: {title: '最后出库日期',order: 2,view: 'date', type: 'string',},
  materialId: {title: '物资id',order: 3,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}