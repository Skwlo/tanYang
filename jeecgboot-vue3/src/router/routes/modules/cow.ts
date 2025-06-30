import { AppRouteRecordRaw } from '/@/router/types';

const CowManageRoute: AppRouteRecordRaw = {
  path: '/cow',
  name: 'CowManage',
  component: () => import('/@/views/CowManage.vue'),
  meta: {
    title: '牛牛管理',
    affix: false,
  },
};

export default CowManageRoute; 