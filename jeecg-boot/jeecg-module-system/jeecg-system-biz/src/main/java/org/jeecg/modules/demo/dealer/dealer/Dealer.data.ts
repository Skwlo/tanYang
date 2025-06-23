import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '经销商名称',
    align:"center",
    dataIndex: 'name'
   },
   {
    title: '销售类型（饲料 / 药品 / 畜只）',
    align:"center",
    dataIndex: 'type_dictText'
   },
   {
    title: '地址',
    align:"center",
    dataIndex: 'address'
   },
   {
    title: '联系人',
    align:"center",
    dataIndex: 'contactPerson'
   },
   {
    title: '电话',
    align:"center",
    dataIndex: 'phone'
   },
   {
    title: '说明',
    align:"center",
    dataIndex: 'note'
   },
   {
    title: '经销商 ID',
    align:"center",
    dataIndex: 'dealerId'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "经销商名称",
      field: 'name',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "销售类型（饲料 / 药品 / 畜只）",
      field: 'type',
      component: 'JSelectMultiple',
      componentProps:{
      },
      //colProps: {span: 6},
 	},
	{
      label: "地址",
      field: 'address',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "联系人",
      field: 'contactPerson',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "电话",
      field: 'phone',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "说明",
      field: 'note',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "经销商 ID",
      field: 'dealerId',
      component: 'Input',
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '经销商名称',
    field: 'name',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入经销商名称!'},
          ];
     },
  },
  {
    label: '销售类型（饲料 / 药品 / 畜只）',
    field: 'type',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:""
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入销售类型（饲料 / 药品 / 畜只）!'},
          ];
     },
  },
  {
    label: '地址',
    field: 'address',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入地址!'},
          ];
     },
  },
  {
    label: '联系人',
    field: 'contactPerson',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入联系人!'},
          ];
     },
  },
  {
    label: '电话',
    field: 'phone',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入电话!'},
          ];
     },
  },
  {
    label: '说明',
    field: 'note',
    component: 'Input',
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
  name: {title: '经销商名称',order: 0,view: 'text', type: 'string',},
  type: {title: '销售类型（饲料 / 药品 / 畜只）',order: 1,view: 'list', type: 'string',dictCode: '',},
  address: {title: '地址',order: 2,view: 'text', type: 'string',},
  contactPerson: {title: '联系人',order: 3,view: 'text', type: 'string',},
  phone: {title: '电话',order: 4,view: 'text', type: 'string',},
  note: {title: '说明',order: 5,view: 'text', type: 'string',},
  dealerId: {title: '经销商 ID',order: 6,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}