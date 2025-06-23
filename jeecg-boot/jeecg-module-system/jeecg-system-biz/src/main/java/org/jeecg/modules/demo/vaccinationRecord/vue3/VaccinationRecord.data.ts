import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '畜只 ID（可选，群体免疫时为空）',
    align:"center",
    dataIndex: 'livestockId'
   },
   {
    title: '疫苗 ID',
    align:"center",
    dataIndex: 'vaccineId'
   },
   {
    title: '免疫日期',
    align:"center",
    dataIndex: 'vaccinationDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '使用剂量',
    align:"center",
    dataIndex: 'dosage'
   },
   {
    title: '注射部位',
    align:"center",
    dataIndex: 'injectionSite'
   },
   {
    title: '合格指标',
    align:"center",
    dataIndex: 'standard'
   },
   {
    title: '免疫范围（个体 / 群体）',
    align:"center",
    dataIndex: 'scope_dictText'
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
      label: "畜只 ID（可选，群体免疫时为空）",
      field: 'livestockId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "疫苗 ID",
      field: 'vaccineId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "免疫日期",
      field: 'vaccinationDate',
      component: 'DatePicker',
      componentProps: {
        valueFormat: 'YYYY-MM-DD'
      },
      //colProps: {span: 6},
 	},
	{
      label: "使用剂量",
      field: 'dosage',
      component: 'InputNumber',
      //colProps: {span: 6},
 	},
	{
      label: "注射部位",
      field: 'injectionSite',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "合格指标",
      field: 'standard',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "免疫范围（个体 / 群体）",
      field: 'scope',
      component: 'JSelectMultiple',
      componentProps:{
      },
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
    label: '畜只 ID（可选，群体免疫时为空）',
    field: 'livestockId',
    component: 'Input',
  },
  {
    label: '疫苗 ID',
    field: 'vaccineId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入疫苗 ID!'},
          ];
     },
  },
  {
    label: '免疫日期',
    field: 'vaccinationDate',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入免疫日期!'},
          ];
     },
  },
  {
    label: '使用剂量',
    field: 'dosage',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入使用剂量!'},
          ];
     },
  },
  {
    label: '注射部位',
    field: 'injectionSite',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入注射部位!'},
          ];
     },
  },
  {
    label: '合格指标',
    field: 'standard',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入合格指标!'},
          ];
     },
  },
  {
    label: '免疫范围（个体 / 群体）',
    field: 'scope',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:""
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入免疫范围（个体 / 群体）!'},
          ];
     },
  },
  {
    label: '说明',
    field: 'note',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入说明!'},
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
  livestockId: {title: '畜只 ID（可选，群体免疫时为空）',order: 0,view: 'text', type: 'string',},
  vaccineId: {title: '疫苗 ID',order: 1,view: 'text', type: 'string',},
  vaccinationDate: {title: '免疫日期',order: 2,view: 'date', type: 'string',},
  dosage: {title: '使用剂量',order: 3,view: 'number', type: 'number',},
  injectionSite: {title: '注射部位',order: 4,view: 'text', type: 'string',},
  standard: {title: '合格指标',order: 5,view: 'text', type: 'string',},
  scope: {title: '免疫范围（个体 / 群体）',order: 6,view: 'list', type: 'string',dictCode: '',},
  note: {title: '说明',order: 7,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}