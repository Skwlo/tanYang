import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '采购日期',
    align:"center",
    dataIndex: 'purchaseDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '采购人',
    align:"center",
    dataIndex: 'seller'
   },
   {
    title: '采购数量',
    align:"center",
    dataIndex: 'quantity'
   },
   {
    title: '单价',
    align:"center",
    dataIndex: 'unitPrice'
   },
   {
    title: '总价',
    align:"center",
    dataIndex: 'totalPrice'
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
      label: "采购日期",
      field: 'purchaseDate',
      component: 'DatePicker',
      componentProps: {
        valueFormat: 'YYYY-MM-DD'
      },
      //colProps: {span: 6},
 	},
	{
      label: "采购人",
      field: 'seller',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "采购数量",
      field: 'quantity',
      component: 'InputNumber',
      //colProps: {span: 6},
 	},
	{
      label: "单价",
      field: 'unitPrice',
      component: 'InputNumber',
      //colProps: {span: 6},
 	},
	{
      label: "总价",
      field: 'totalPrice',
      component: 'InputNumber',
      //colProps: {span: 6},
 	},
	{
      label: "备注",
      field: 'note',
      component: 'Input',
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '采购日期',
    field: 'purchaseDate',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入采购日期!'},
          ];
     },
  },
  {
    label: '采购人',
    field: 'seller',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入采购人!'},
          ];
     },
  },
  {
    label: '采购数量',
    field: 'quantity',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入采购数量!'},
          ];
     },
  },
  {
    label: '单价',
    field: 'unitPrice',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入单价!'},
          ];
     },
  },
  {
    label: '总价',
    field: 'totalPrice',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入总价!'},
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
  purchaseDate: {title: '采购日期',order: 0,view: 'date', type: 'string',},
  seller: {title: '采购人',order: 1,view: 'text', type: 'string',},
  quantity: {title: '采购数量',order: 2,view: 'number', type: 'number',},
  unitPrice: {title: '单价',order: 3,view: 'number', type: 'number',},
  totalPrice: {title: '总价',order: 4,view: 'number', type: 'number',},
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