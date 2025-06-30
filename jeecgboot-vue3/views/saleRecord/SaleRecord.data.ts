import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '销售日期',
    align:"center",
    dataIndex: 'saleDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '客户名称',
    align:"center",
    dataIndex: 'customerName'
   },
   {
    title: '客户地址',
    align:"center",
    dataIndex: 'customerAddress'
   },
   {
    title: '客户电话',
    align:"center",
    dataIndex: 'customerPhone'
   },
   {
    title: '销售人员',
    align:"center",
    dataIndex: 'salesman'
   },
   {
    title: '销售总额',
    align:"center",
    dataIndex: 'totalAmount'
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
      label: "销售日期",
      field: 'saleDate',
      component: 'DatePicker',
      componentProps: {
        valueFormat: 'YYYY-MM-DD'
      },
      //colProps: {span: 6},
 	},
	{
      label: "客户名称",
      field: 'customerName',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "客户地址",
      field: 'customerAddress',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "客户电话",
      field: 'customerPhone',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "销售人员",
      field: 'salesman',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "销售总额",
      field: 'totalAmount',
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
    label: '销售日期',
    field: 'saleDate',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入销售日期!'},
          ];
     },
  },
  {
    label: '客户名称',
    field: 'customerName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入客户名称!'},
          ];
     },
  },
  {
    label: '客户地址',
    field: 'customerAddress',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入客户地址!'},
          ];
     },
  },
  {
    label: '客户电话',
    field: 'customerPhone',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入客户电话!'},
          ];
     },
  },
  {
    label: '销售人员',
    field: 'salesman',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入销售人员!'},
          ];
     },
  },
  {
    label: '销售总额',
    field: 'totalAmount',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入销售总额!'},
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
  saleDate: {title: '销售日期',order: 0,view: 'date', type: 'string',},
  customerName: {title: '客户名称',order: 1,view: 'text', type: 'string',},
  customerAddress: {title: '客户地址',order: 2,view: 'text', type: 'string',},
  customerPhone: {title: '客户电话',order: 3,view: 'text', type: 'string',},
  salesman: {title: '销售人员',order: 4,view: 'text', type: 'string',},
  totalAmount: {title: '销售总额',order: 5,view: 'number', type: 'number',},
  note: {title: '说明',order: 6,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}