import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '公畜 ID',
    align:"center",
    dataIndex: 'maleLivestockId'
   },
   {
    title: '母畜 ID',
    align:"center",
    dataIndex: 'femaleLivestockId'
   },
   {
    title: '配种日期',
    align:"center",
    dataIndex: 'breedingDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '预产日期',
    align:"center",
    dataIndex: 'expectedDeliveryDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '配种方式',
    align:"center",
    dataIndex: 'method_dictText'
   },
   {
     title: '配种状态',
     align: 'center',
     dataIndex: 'status',
     customRender: ({ record }) => {
       return record.status_dictText || record.status;
     },
   },
   {
    title: '是否繁育',
    align:"center",
    dataIndex: 'isBreeding_dictText'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "公畜 ID",
      field: 'maleLivestockId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "母畜 ID",
      field: 'femaleLivestockId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "配种日期",
      field: 'breedingDate',
      component: 'DatePicker',
      componentProps: {
        valueFormat: 'YYYY-MM-DD'
      },
      //colProps: {span: 6},
 	},
	{
      label: "预产日期",
      field: 'expectedDeliveryDate',
      component: 'DatePicker',
      componentProps: {
        valueFormat: 'YYYY-MM-DD'
      },
      //colProps: {span: 6},
 	},
	{
      label: "配种方式",
      field: 'method',
      component: 'JSelectMultiple',
      componentProps: {
      dictCode: "peizhong-way"
    },
      //colProps: {span: 6},
 	},
  {
      label: "配种状态",
      field: 'status',
      component: 'JDictSelectTag',
      componentProps: {
        dictCode: "Peizhong-Zhuangtai"
      },
  },
	{
      label: "是否繁育",
      field: 'isBreeding',
      component: 'JDictSelectTag',
      componentProps:{
      dictCode:"isBreeding"
    },
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '公畜 ID',
    field: 'maleLivestockId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入公畜 ID!'},
          ];
     },
  },
  {
    label: '母畜 ID',
    field: 'femaleLivestockId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入母畜 ID!'},
          ];
     },
  },
  {
    label: '配种日期',
    field: 'breedingDate',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入配种日期!'},
          ];
     },
  },
  {
    label: '预产日期',
    field: 'expectedDeliveryDate',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入预产日期!'},
          ];
     },
  },
  {
    label: '配种方式',
    field: 'method',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"peizhong-way"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入配种方式（自然 / 人工）!'},
          ];
     },
  },
  {
    label: '配种状态',
    field: 'status',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"Peizhong-Zhuangtai"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入配种状态（成功 / 失败）!'},
          ];
     },
  },
  {
    label: '是否繁育（1/0）',
    field: 'isBreeding',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"isBreeding"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入是否繁育（1/0）!'},
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
  maleLivestockId: {title: '公畜 ID',order: 0,view: 'text', type: 'string',},
  femaleLivestockId: {title: '母畜 ID',order: 1,view: 'text', type: 'string',},
  breedingDate: {title: '配种日期',order: 2,view: 'date', type: 'string',},
  expectedDeliveryDate: {title: '预产日期',order: 3,view: 'date', type: 'string',},
  method: {title: '配种方式（自然 / 人工）',order: 4,view: 'list', type: 'string',dictCode: 'peizhong-way',},
  status: {title: '配种状态（成功 / 失败）',order: 5,view: 'list', type: 'string',dictCode: 'Peizhong-Zhuangtai',},
  isBreeding: {title: '是否繁育（1/0）',order: 6,view: 'list', type: 'string',dictCode: 'isBreeding',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
