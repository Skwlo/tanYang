import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '姓名',
    align:"center",
    dataIndex: 'studentName'
   },
   {
    title: '学号',
    align:"center",
    dataIndex: 'studentNo'
   },
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
    title: '考试名字',
    align:"center",
    dataIndex: 'examName'
   },
   {
    title: '考试时间',
    align:"center",
    dataIndex: 'examTime'
   },
   {
    title: '学生成绩',
    align:"center",
    dataIndex: 'studentScore'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "姓名",
      field: 'studentName',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "学号",
      field: 'studentNo',
      component: 'Input',
      //colProps: {span: 6},
 	},
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
      label: "考试名字",
      field: 'examName',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "考试时间",
      field: 'examTime',
      component: 'DatePicker',
      componentProps: {
         showTime:true,
         valueFormat: 'YYYY-MM-DD HH:mm:ss'
       },
      //colProps: {span: 6},
 	},
	{
      label: "学生成绩",
      field: 'studentScore',
      component: 'Input',
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '学号',
    field: 'studentNo',
    component: 'Input',
  },
  {
    label: '课程代码',
    field: 'courseCode',
    component: 'Input',
  },
  {
    label: '考试名字',
    field: 'examName',
    component: 'Input',
  },
  {
    label: '考试时间',
    field: 'examTime',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
  },
  {
    label: '学生成绩',
    field: 'studentScore',
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
  studentName: {title: '姓名',order: 0,view: 'text', type: 'string',},
  studentNo: {title: '学号',order: 1,view: 'text', type: 'string',},
  courseName: {title: '课程名字',order: 2,view: 'text', type: 'string',},
  courseCode: {title: '课程代码',order: 3,view: 'text', type: 'string',},
  examName: {title: '考试名字',order: 4,view: 'text', type: 'string',},
  examTime: {title: '考试时间',order: 5,view: 'datetime', type: 'string',},
  studentScore: {title: '学生成绩',order: 6,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}