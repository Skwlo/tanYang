import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '物资 ID',
    align:"center",
    dataIndex: 'materialId'
   },
   {
    title: '入库日期',
    align:"center",
    dataIndex: 'inDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '入库数量',
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
    title: '经销商 ID',
    align:"center",
    dataIndex: 'dealerId'
   },
   {
    title: '运费',
    align:"center",
    dataIndex: 'freight'
   },
   {
    title: '装卸费',
    align:"center",
    dataIndex: 'handlingFee'
   },
   {
    title: '其他费用',
    align:"center",
    dataIndex: 'otherFee'
   },
   {
    title: '说明',
    align:"center",
    dataIndex: 'note'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "物资 ID",
      field: 'materialId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "入库日期",
      field: 'inDate',
      component: 'DatePicker',
      componentProps: {
        valueFormat: 'YYYY-MM-DD'
      },
      //colProps: {span: 6},
 	},
	{
      label: "入库数量",
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
      label: "经销商 ID",
      field: 'dealerId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "运费",
      field: 'freight',
      component: 'InputNumber',
      //colProps: {span: 6},
 	},
	{
      label: "装卸费",
      field: 'handlingFee',
      component: 'InputNumber',
      //colProps: {span: 6},
 	},
	{
      label: "其他费用",
      field: 'otherFee',
      component: 'InputNumber',
      //colProps: {span: 6},
 	},
	{
      label: "说明",
      field: 'note',
      component: 'Input',
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '物资 ID',
    field: 'materialId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入物资 ID!'},
          ];
     },
  },
  {
    label: '入库日期',
    field: 'inDate',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入入库日期!'},
          ];
     },
  },
  {
    label: '入库数量',
    field: 'quantity',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入入库数量!'},
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
    label: '经销商 ID',
    field: 'dealerId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入经销商 ID!'},
          ];
     },
  },
  {
    label: '运费',
    field: 'freight',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入运费!'},
          ];
     },
  },
  {
    label: '装卸费',
    field: 'handlingFee',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入装卸费!'},
          ];
     },
  },
  {
    label: '其他费用',
    field: 'otherFee',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入其他费用!'},
          ];
     },
  },
  {
    label: '说明',
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
  materialId: {title: '物资 ID',order: 0,view: 'text', type: 'string',},
  inDate: {title: '入库日期',order: 1,view: 'date', type: 'string',},
  quantity: {title: '入库数量',order: 2,view: 'number', type: 'number',},
  unitPrice: {title: '单价',order: 3,view: 'number', type: 'number',},
  totalPrice: {title: '总价',order: 4,view: 'number', type: 'number',},
  dealerId: {title: '经销商 ID',order: 5,view: 'text', type: 'string',},
  freight: {title: '运费',order: 6,view: 'number', type: 'number',},
  handlingFee: {title: '装卸费',order: 7,view: 'number', type: 'number',},
  otherFee: {title: '其他费用',order: 8,view: 'number', type: 'number',},
  note: {title: '说明',order: 9,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}