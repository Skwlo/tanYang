import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '课程名字',
    align:"center",
    dataIndex: 'courseName'
   },
   {
    title: '课程代码',
    align:"center",
    dataIndex: 'courseCode'
   },
   {
    title: '任课老师',
    align:"center",
    dataIndex: 'courseClass'
   },
   {
    title: '上课地点',
    align:"center",
    dataIndex: 'coursePlace'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "课程名字",
      field: 'courseName',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "课程代码",
      field: 'courseCode',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "任课老师",
      field: 'courseClass',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "上课地点",
      field: 'coursePlace',
      component: 'Input',
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '课程名字',
    field: 'courseName',
    component: 'Input',
  },
  {
    label: '课程代码',
    field: 'courseCode',
    component: 'Input',
  },
  {
    label: '任课老师',
    field: 'courseClass',
    component: 'Input',
  },
  {
    label: '上课地点',
    field: 'coursePlace',
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
  courseName: {title: '课程名字',order: 0,view: 'text', type: 'string',},
  courseCode: {title: '课程代码',order: 1,view: 'text', type: 'string',},
  courseClass: {title: '任课老师',order: 2,view: 'text', type: 'string',},
  coursePlace: {title: '上课地点',order: 3,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}