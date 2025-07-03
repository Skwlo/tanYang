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
    title: '棚栏 ID',
    align:"center",
    dataIndex: 'shedPenId'
   },
   {
    title: '检疫日期',
    align:"center",
    dataIndex: 'quarantineDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '检疫项目',
    align:"center",
    dataIndex: 'item'
   },
   {
    title: '检疫结果',
    align:"center",
    dataIndex: 'result'
   },
   {
    title: '检疫报告文件路径',
    align:"center",
    dataIndex: 'reportPath'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "畜只 ID",
      field: 'livestockId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "棚栏 ID",
      field: 'shedPenId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "检疫日期",
      field: 'quarantineDate',
      component: 'DatePicker',
      componentProps: {
        valueFormat: 'YYYY-MM-DD'
      },
      //colProps: {span: 6},
 	},
	{
      label: "检疫项目",
      field: 'item',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "检疫结果",
      field: 'result',
      component: 'Input',
      //colProps: {span: 6},
 	},
	// {
  //     label: "检疫报告文件路径",
  //     field: 'reportPath',
  //     component: 'Input',
  //     //colProps: {span: 6},
 	// },
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '畜只 ID（可选，群体检疫时为空）',
    field: 'livestockId',
    component: 'Input',
  },
  {
    label: '棚栏 ID（群体检疫时使用）',
    field: 'shedPenId',
    component: 'Input',
  },
  {
    label: '检疫日期',
    field: 'quarantineDate',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入检疫日期!'},
          ];
     },
  },
  {
    label: '检疫项目',
    field: 'item',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入检疫项目!'},
          ];
     },
  },
  {
    label: '检疫结果',
    field: 'result',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入检疫结果!'},
          ];
     },
  },
  {
    label: '检疫报告文件路径',
    field: 'reportPath',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入检疫报告文件路径!'},
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
  livestockId: {title: '畜只 ID（可选，群体检疫时为空）',order: 0,view: 'text', type: 'string',},
  shedPenId: {title: '棚栏 ID（群体检疫时使用）',order: 1,view: 'text', type: 'string',},
  quarantineDate: {title: '检疫日期',order: 2,view: 'date', type: 'string',},
  item: {title: '检疫项目',order: 3,view: 'text', type: 'string',},
  result: {title: '检疫结果',order: 4,view: 'text', type: 'string',},
  reportPath: {title: '检疫报告文件路径',order: 5,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
