import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '销售记录 ID',
    align:"center",
    dataIndex: 'saleRecordId'
   },
   {
    title: '畜只 ID',
    align:"center",
    dataIndex: 'livestockId'
   },
   {
    title: '单价',
    align:"center",
    dataIndex: 'unitPrice'
   },
   {
    title: '数量（通常为 1）',
    align:"center",
    dataIndex: 'quantity'
   },
   {
    title: '单位（头 / 公斤）',
    align:"center",
    dataIndex: 'unit'
   },
   {
    title: '小计金额',
    align:"center",
    dataIndex: 'amount'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "销售记录 ID",
      field: 'saleRecordId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "畜只 ID",
      field: 'livestockId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "单价",
      field: 'unitPrice',
      component: 'InputNumber',
      //colProps: {span: 6},
 	},
	{
      label: "数量（通常为 1）",
      field: 'quantity',
      component: 'InputNumber',
      //colProps: {span: 6},
 	},
	{
      label: "单位（头 / 公斤）",
      field: 'unit',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "小计金额",
      field: 'amount',
      component: 'InputNumber',
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '销售记录 ID',
    field: 'saleRecordId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入销售记录 ID!'},
          ];
     },
  },
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
    label: '数量（通常为 1）',
    field: 'quantity',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入数量（通常为 1）!'},
          ];
     },
  },
  {
    label: '单位（头 / 公斤）',
    field: 'unit',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入单位（头 / 公斤）!'},
          ];
     },
  },
  {
    label: '小计金额',
    field: 'amount',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入小计金额!'},
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
  saleRecordId: {title: '销售记录 ID',order: 0,view: 'text', type: 'string',},
  livestockId: {title: '畜只 ID',order: 1,view: 'text', type: 'string',},
  unitPrice: {title: '单价',order: 2,view: 'number', type: 'number',},
  quantity: {title: '数量（通常为 1）',order: 3,view: 'number', type: 'number',},
  unit: {title: '单位（头 / 公斤）',order: 4,view: 'text', type: 'string',},
  amount: {title: '小计金额',order: 5,view: 'number', type: 'number',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}