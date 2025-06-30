/*
 * @Author: kelemengqi 1565916105@qq.com
 * @Date: 2025-06-09 09:22:21
 * @LastEditors: kelemengqi 1565916105@qq.com
 * @LastEditTime: 2025-06-26 09:49:49
 * @FilePath: /JeecgBoot-master/jeecgboot-vue3/src/router/router.ts
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
/*
 * 路由实例存储文件，请勿轻易添加其他代码，防止出现 HMR 或其他问题
 */
import type {Router, RouterHistory} from 'vue-router';
import {createRouter as createVueRouter, createWebHistory, RouterOptions} from 'vue-router';

export let router: Router = null as unknown as Router;

export function setRouter(r: Router) {
  router = r
}

let webHistory: Nullable<RouterHistory> = null;

/**
 * 创建路由
 * @param options 参数
 */
export function createRouter(options: Partial<RouterOptions>) {
  webHistory = createWebHistory(import.meta.env.VITE_PUBLIC_PATH);
  // app router
  let router = createVueRouter({
    history: webHistory,
    routes: [],
    ...options,
  });

  setRouter(router)

  return router
}

// 销毁路由
export function destroyRouter() {
  setRouter(null as unknown as Router);
  if (webHistory) {
    webHistory.destroy();
  }
  webHistory = null
}
