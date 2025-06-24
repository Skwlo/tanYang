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
    title: '淘汰日期',
    align:"center",
    dataIndex: 'eliminationDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '淘汰前类别',
    align:"center",
    dataIndex: 'beforeCategory_dictText'
   },
   {
    title: '淘汰原因',
    align:"center",
    dataIndex: 'eliminationReason'
   },
   {
    title: '说明',
    align:"center",
    dataIndex: 'note'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
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
    label: '淘汰日期',
    field: 'eliminationDate',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入淘汰日期!'},
          ];
     },
  },
  {
    label: '淘汰前类别',
    field: 'beforeCategory',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"leibie"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入淘汰前类别!'},
          ];
     },
  },
  {
    label: '淘汰原因',
    field: 'eliminationReason',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入淘汰原因!'},
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
  livestockId: {title: '畜只 ID',order: 0,view: 'text', type: 'string',},
  eliminationDate: {title: '淘汰日期',order: 1,view: 'date', type: 'string',},
  beforeCategory: {title: '淘汰前类别',order: 2,view: 'list', type: 'string',dictCode: 'leibie',},
  eliminationReason: {title: '淘汰原因',order: 3,view: 'text', type: 'string',},
  note: {title: '说明',order: 4,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}