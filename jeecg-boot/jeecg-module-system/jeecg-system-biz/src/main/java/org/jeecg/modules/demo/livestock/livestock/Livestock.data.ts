import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '普通耳号',
    align:"center",
    dataIndex: 'commonEarTag'
   },
   {
    title: '电子耳号',
    align:"center",
    dataIndex: 'electronicEarTag'
   },
   {
    title: '品种（如滩羊）',
    align:"center",
    dataIndex: 'breed_dictText'
   },
   {
    title: '类别（种羊、肉羊等）',
    align:"center",
    dataIndex: 'category_dictText'
   },
   {
    title: '性别（M/F）',
    align:"center",
    dataIndex: 'gender_dictText'
   },
   {
    title: '出生日期',
    align:"center",
    dataIndex: 'birthDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '来源（采购 / 自繁）',
    align:"center",
    dataIndex: 'source_dictText'
   },
   {
    title: '状态（正常 / 死亡 / 淘汰 / 已售）',
    align:"center",
    dataIndex: 'status_dictText'
   },
   {
    title: '当前阶段（幼崽 / 育肥 / 成年）',
    align:"center",
    dataIndex: 'currentStage_dictText'
   },
   {
    title: '当前所在栅栏ID',
    align:"center",
    dataIndex: 'currentShedPenId'
   },
   {
    title: '父亲畜只 ID（可选）',
    align:"center",
    dataIndex: 'fatherId'
   },
   {
    title: '母亲畜只 ID（可选）',
    align:"center",
    dataIndex: 'motherId'
   },
   {
    title: '当前体重 (kg)（可选，可通过体尺测量更新）',
    align:"center",
    dataIndex: 'weight'
   },
   {
    title: '采购记录 ID（可选）',
    align:"center",
    dataIndex: 'purchaseInfoId'
   },
   {
    title: '畜只唯一标识（电子耳号）',
    align:"center",
    dataIndex: 'livestockId'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "普通耳号",
      field: 'commonEarTag',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "电子耳号",
      field: 'electronicEarTag',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "品种（如滩羊）",
      field: 'breed',
      component: 'JSelectMultiple',
      componentProps:{
      },
      //colProps: {span: 6},
 	},
	{
      label: "类别（种羊、肉羊等）",
      field: 'category',
      component: 'JSelectMultiple',
      componentProps:{
      },
      //colProps: {span: 6},
 	},
	{
      label: "性别（M/F）",
      field: 'gender',
      component: 'JSelectMultiple',
      componentProps:{
      },
      //colProps: {span: 6},
 	},
	{
      label: "出生日期",
      field: 'birthDate',
      component: 'DatePicker',
      componentProps: {
        valueFormat: 'YYYY-MM-DD'
      },
      //colProps: {span: 6},
 	},
	{
      label: "来源（采购 / 自繁）",
      field: 'source',
      component: 'JSelectMultiple',
      componentProps:{
      },
      //colProps: {span: 6},
 	},
	{
      label: "状态（正常 / 死亡 / 淘汰 / 已售）",
      field: 'status',
      component: 'JSelectMultiple',
      componentProps:{
      },
      //colProps: {span: 6},
 	},
	{
      label: "当前阶段（幼崽 / 育肥 / 成年）",
      field: 'currentStage',
      component: 'JSelectMultiple',
      componentProps:{
      },
      //colProps: {span: 6},
 	},
	{
      label: "当前所在栅栏ID",
      field: 'currentShedPenId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "父亲畜只 ID（可选）",
      field: 'fatherId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "母亲畜只 ID（可选）",
      field: 'motherId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "当前体重 (kg)（可选，可通过体尺测量更新）",
      field: 'weight',
      component: 'InputNumber',
      //colProps: {span: 6},
 	},
	{
      label: "采购记录 ID（可选）",
      field: 'purchaseInfoId',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "畜只唯一标识（电子耳号）",
      field: 'livestockId',
      component: 'Input',
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '普通耳号',
    field: 'commonEarTag',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入普通耳号!'},
          ];
     },
  },
  {
    label: '电子耳号',
    field: 'electronicEarTag',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入电子耳号!'},
          ];
     },
  },
  {
    label: '品种（如滩羊）',
    field: 'breed',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:""
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入品种（如滩羊）!'},
          ];
     },
  },
  {
    label: '类别（种羊、肉羊等）',
    field: 'category',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:""
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入类别（种羊、肉羊等）!'},
          ];
     },
  },
  {
    label: '性别（M/F）',
    field: 'gender',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:""
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入性别（M/F）!'},
          ];
     },
  },
  {
    label: '出生日期',
    field: 'birthDate',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入出生日期!'},
          ];
     },
  },
  {
    label: '来源（采购 / 自繁）',
    field: 'source',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:""
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入来源（采购 / 自繁）!'},
          ];
     },
  },
  {
    label: '状态（正常 / 死亡 / 淘汰 / 已售）',
    field: 'status',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:""
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入状态（正常 / 死亡 / 淘汰 / 已售）!'},
          ];
     },
  },
  {
    label: '当前阶段（幼崽 / 育肥 / 成年）',
    field: 'currentStage',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:""
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入当前阶段（幼崽 / 育肥 / 成年）!'},
          ];
     },
  },
  {
    label: '当前所在栅栏ID',
    field: 'currentShedPenId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入当前所在栅栏ID!'},
          ];
     },
  },
  {
    label: '父亲畜只 ID（可选）',
    field: 'fatherId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入父亲畜只 ID（可选）!'},
          ];
     },
  },
  {
    label: '母亲畜只 ID（可选）',
    field: 'motherId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入母亲畜只 ID（可选）!'},
          ];
     },
  },
  {
    label: '当前体重 (kg)（可选，可通过体尺测量更新）',
    field: 'weight',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入当前体重 (kg)（可选，可通过体尺测量更新）!'},
          ];
     },
  },
  {
    label: '采购记录 ID（可选）',
    field: 'purchaseInfoId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入采购记录 ID（可选）!'},
          ];
     },
  },
  {
    label: '畜只唯一标识（电子耳号）',
    field: 'livestockId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入畜只唯一标识（电子耳号）!'},
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
  commonEarTag: {title: '普通耳号',order: 0,view: 'text', type: 'string',},
  electronicEarTag: {title: '电子耳号',order: 1,view: 'text', type: 'string',},
  breed: {title: '品种（如滩羊）',order: 2,view: 'list', type: 'string',dictCode: '',},
  category: {title: '类别（种羊、肉羊等）',order: 3,view: 'list', type: 'string',dictCode: '',},
  gender: {title: '性别（M/F）',order: 4,view: 'list', type: 'string',dictCode: '',},
  birthDate: {title: '出生日期',order: 5,view: 'date', type: 'string',},
  source: {title: '来源（采购 / 自繁）',order: 6,view: 'list', type: 'string',dictCode: '',},
  status: {title: '状态（正常 / 死亡 / 淘汰 / 已售）',order: 7,view: 'list', type: 'string',dictCode: '',},
  currentStage: {title: '当前阶段（幼崽 / 育肥 / 成年）',order: 8,view: 'list', type: 'string',dictCode: '',},
  currentShedPenId: {title: '当前所在栅栏ID',order: 9,view: 'text', type: 'string',},
  fatherId: {title: '父亲畜只 ID（可选）',order: 10,view: 'text', type: 'string',},
  motherId: {title: '母亲畜只 ID（可选）',order: 11,view: 'text', type: 'string',},
  weight: {title: '当前体重 (kg)（可选，可通过体尺测量更新）',order: 12,view: 'number', type: 'number',},
  purchaseInfoId: {title: '采购记录 ID（可选）',order: 13,view: 'text', type: 'string',},
  livestockId: {title: '畜只唯一标识（电子耳号）',order: 14,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}