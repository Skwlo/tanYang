import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '配种记录 ID',
    align:"center",
    dataIndex: 'breedingId'
   },
   {
    title: '产崽日期',
    align:"center",
    dataIndex: 'reproductionDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '产崽数量',
    align:"center",
    dataIndex: 'offspringCount_dictText'
   },
   {
    title: '活崽数量',
    align:"center",
    dataIndex: 'liveOffspringCount_dictText'
   },
   {
    title: '产崽状态',
    align:"center",
    dataIndex: 'status_dictText'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "配种记录 ID",
      field: 'breedingId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "产崽日期",
      field: 'reproductionDate',
      component: 'DatePicker',
      componentProps: {
        valueFormat: 'YYYY-MM-DD'
      },
      //colProps: {span: 6},
 	},
	// {
  //     label: "产崽数量",
  //     field: 'offspringCount',
  //     component: 'JSelectMultiple',
  //     componentProps:{
  //     },
  //     //colProps: {span: 6},
 	// },
	// {
  //     label: "活崽数量",
  //     field: 'liveOffspringCount',
  //     component: 'JSelectMultiple',
  //     componentProps:{
  //     },
  //     //colProps: {span: 6},
 	// },
	{
      label: "产崽状态",
      field: 'status',
      component: 'JDictSelectTag',
      componentProps: {
      dictCode: "huozaiStatue"
    },
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '配种记录 ID',
    field: 'breedingId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入配种记录 ID!'},
          ];
     },
  },
  {
    label: '产崽日期',
    field: 'reproductionDate',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入产崽日期!'},
          ];
     },
  },
  {
    label: '产崽数量',
    field: 'offspringCount',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"chanzanNumber"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入产崽数量!'},
          ];
     },
  },
  {
    label: '活崽数量',
    field: 'liveOffspringCount',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"huozaiNumber"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入活崽数量!'},
          ];
     },
  },
  {
    label: '产崽状态',
    field: 'status',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"huozaiStatue"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入产崽状态（正常 / 难产）!'},
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
  breedingId: {title: '配种记录 ID',order: 0,view: 'text', type: 'string',},
  reproductionDate: {title: '产崽日期',order: 1,view: 'date', type: 'string',},
  offspringCount: {title: '产崽数量',order: 2,view: 'number', type: 'number',dictCode: 'chanzanNumber',},
  liveOffspringCount: {title: '活崽数量',order: 3,view: 'number', type: 'number',dictCode: 'huozaiNumber',},
  status: {title: '产崽状态',order: 4,view: 'list', type: 'string',dictCode: 'huozaiStatue',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
