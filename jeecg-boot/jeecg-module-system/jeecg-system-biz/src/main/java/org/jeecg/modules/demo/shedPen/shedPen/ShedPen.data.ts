import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '名称',
    align:"center",
    dataIndex: 'name'
   },
   {
    title: '类型（场 / 棚 / 栏）',
    align:"center",
    dataIndex: 'type_dictText'
   },
   {
    title: '父级 ID（场无父级，棚父级为场，栏父级为棚）',
    align:"center",
    dataIndex: 'parentId'
   },
   {
    title: '容量（可容纳畜只数量）',
    align:"center",
    dataIndex: 'capacity'
   },
   {
    title: '说明',
    align:"center",
    dataIndex: 'note'
   },
   {
    title: '棚栏 ID',
    align:"center",
    dataIndex: 'shedPenId'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "名称",
      field: 'name',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "类型（场 / 棚 / 栏）",
      field: 'type',
      component: 'JSelectMultiple',
      componentProps:{
      },
      //colProps: {span: 6},
 	},
	{
      label: "父级 ID（场无父级，棚父级为场，栏父级为棚）",
      field: 'parentId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "容量（可容纳畜只数量）",
      field: 'capacity',
      component: 'InputNumber',
      //colProps: {span: 6},
 	},
	{
      label: "说明",
      field: 'note',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "棚栏 ID",
      field: 'shedPenId',
      component: 'Input',
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '名称',
    field: 'name',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入名称!'},
          ];
     },
  },
  {
    label: '类型（场 / 棚 / 栏）',
    field: 'type',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:""
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入类型（场 / 棚 / 栏）!'},
          ];
     },
  },
  {
    label: '父级 ID（场无父级，棚父级为场，栏父级为棚）',
    field: 'parentId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入父级 ID（场无父级，棚父级为场，栏父级为棚）!'},
          ];
     },
  },
  {
    label: '容量（可容纳畜只数量）',
    field: 'capacity',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入容量（可容纳畜只数量）!'},
          ];
     },
  },
  {
    label: '说明',
    field: 'note',
    component: 'Input',
  },
  {
    label: '棚栏 ID',
    field: 'shedPenId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入棚栏 ID!'},
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
  name: {title: '名称',order: 0,view: 'text', type: 'string',},
  type: {title: '类型（场 / 棚 / 栏）',order: 1,view: 'list', type: 'string',dictCode: '',},
  parentId: {title: '父级 ID（场无父级，棚父级为场，栏父级为棚）',order: 2,view: 'text', type: 'string',},
  capacity: {title: '容量（可容纳畜只数量）',order: 3,view: 'number', type: 'number',},
  note: {title: '说明',order: 4,view: 'text', type: 'string',},
  shedPenId: {title: '棚栏 ID',order: 5,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}