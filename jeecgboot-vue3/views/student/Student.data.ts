import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { rules } from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '姓名',
    align: "center",
    dataIndex: 'studentName'
  },
  {
    title: '学号',
    align: "center",
    dataIndex: 'studentNo'
  },
  {
    title: '性别',
    align: "center",
    dataIndex: 'studentSex_dictText'
  },
  {
    title: '班级',
    align: "center",
    dataIndex: 'studentClass',
  },
  {
    title: '学院',
    align: "center",
    dataIndex: 'studentAcademy',
  },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: '姓名',
    field: 'studentName',
    component: 'Input',
  },
  {
    label: '学号',
    field: 'studentNo',
    component: 'Input',
  },
  {
    label: '性别',
    field: 'studentSex',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'sex',
    },
  },
  {
    label: '班级',
    field: 'studentClass',
    component: 'Input',
  },
  {
    label: '学院',
    field: 'studentAcademy',
    component: 'Input',
  },
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '姓名',
    field: 'studentName',
    component: 'Input',
  },
  {
    label: '学号',
    field: 'studentNo',
    component: 'Input',
  },
  {
    label: '性别',
    field: 'studentSex',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: "sex"
    },
  },
  {
    label: '班级',
    field: 'studentClass',
    component: 'Input',
  },
  {
    label: '学院',
    field: 'studentAcademy',
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
  studentName: { title: '姓名', order: 0, view: 'text', type: 'string', },
  studentNo: { title: '学号', order: 1, view: 'text', type: 'string', },
  studentSex: { title: '性别', order: 2, view: 'list', type: 'string', dictCode: 'sex', },
  studentClass: { title: '班级', order: 3, view: 'text', type: 'string', },
  studentAcademy: { title: '学院', order: 4, view: 'text', type: 'string', },
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[] {
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
