<template>
  <div class="livestock-bg">
    <!-- 顶部绿色渐变卡片 -->
    <div class="livestock-header">
      <div class="livestock-header-main">
        <div class="livestock-header-title-bg">
          <h2>
            <Icon icon="ant-design:home-filled" class="header-title-icon" />
            栏舍信息管理
          </h2>
          <div class="livestock-header-desc">管理所有栏舍的基础信息、状态与操作</div>
        </div>
      </div>
    </div>
    <!-- 卡片包裹表格 -->
    <a-card class="livestock-table-card">
      <BasicTable @register="registerTable" :rowSelection="rowSelection">
        <template #tableTitle>
          <div class="livestock-btn-group">
            <a-button type="primary" v-auth="'shedPen:shed_pen:add'" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增 </a-button>
            <a-button
              type="primary"
              style="background: #66bb6a; border: none"
              v-auth="'shedPen:shed_pen:exportXls'"
              preIcon="ant-design:export-outlined"
            >
              导出
            </a-button>
            <j-upload-button
              type="primary"
              style="background: #81c784; border: none"
              v-auth="'shedPen:shed_pen:importExcel'"
              preIcon="ant-design:import-outlined"
              >导入
            </j-upload-button>
            <a-dropdown v-if="selectedRowKeys.length > 0">
              <template #overlay>
                <a-menu>
                  <a-menu-item key="1" @click="batchHandleDelete">
                    <Icon icon="ant-design:delete-outlined" />
                    删除
                  </a-menu-item>
                </a-menu>
              </template>
              <a-button v-auth="'shedPen:shed_pen:deleteBatch'" style="background: #a5d6a7; border: none">
                批量操作
                <Icon icon="mdi:chevron-down" />
              </a-button>
            </a-dropdown>
            <super-query :config="superQueryConfig" @search="handleSuperQuery" />
          </div>
        </template>
        <template #action="{ record }">
          <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)" />
        </template>
      </BasicTable>
      <ShedPenModal @register="registerModal" @success="handleSuccess" />
    </a-card>
  </div>
</template>

<script lang="ts" name="shedPen-shedPen" setup>
  import { ref, reactive, computed, unref } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useModal } from '/@/components/Modal';
  import { useListPage } from '/@/hooks/system/useListPage';
  import ShedPenModal from './components/ShedPenModal.vue';
  import { columns, searchFormSchema, superQuerySchema } from './ShedPen.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl } from './ShedPen.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import { useUserStore } from '/@/store/modules/user';

  const queryParam = reactive<any>({});
  const checkedKeys = ref<Array<string | number>>([]);
  const userStore = useUserStore();
  //注册model
  const [registerModal, { openModal }] = useModal();
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: '场棚栏表',
      api: list,
      columns,
      canResize: false,
      formConfig: {
        //labelWidth: 120,
        schemas: searchFormSchema,
        autoSubmitOnEnter: true,
        showAdvancedButton: true,
        fieldMapToNumber: [],
        fieldMapToTime: [],
      },
      actionColumn: {
        width: 120,
        fixed: 'right',
      },
      beforeFetch: (params) => {
        return Object.assign(params, queryParam);
      },
    },
    exportConfig: {
      name: '场棚栏表',
      url: getExportUrl,
      params: queryParam,
    },
    importConfig: {
      url: getImportUrl,
      success: handleSuccess,
    },
  });

  const [registerTable, { reload }, { rowSelection, selectedRowKeys }] = tableContext;

  // 高级查询配置
  const superQueryConfig = reactive(superQuerySchema);

  /**
   * 高级查询事件
   */
  function handleSuperQuery(params) {
    Object.keys(params).map((k) => {
      queryParam[k] = params[k];
    });
    reload();
  }

  /**
   * 新增事件
   */
  function handleAdd() {
    openModal(true, {
      isUpdate: false,
      showFooter: true,
    });
  }

  /**
   * 编辑事件
   */
  function handleEdit(record: Recordable) {
    openModal(true, {
      record,
      isUpdate: true,
      showFooter: true,
    });
  }

  /**
   * 详情
   */
  function handleDetail(record: Recordable) {
    openModal(true, {
      record,
      isUpdate: true,
      showFooter: false,
    });
  }

  /**
   * 删除事件
   */
  async function handleDelete(record) {
    await deleteOne({ id: record.id }, handleSuccess);
  }

  /**
   * 批量删除事件
   */
  async function batchHandleDelete() {
    await batchDelete({ ids: selectedRowKeys.value }, handleSuccess);
  }

  /**
   * 成功回调
   */
  function handleSuccess() {
    (selectedRowKeys.value = []) && reload();
  }

  /**
   * 操作栏
   */
  function getTableAction(record) {
    return [
      {
        label: '编辑',
        onClick: handleEdit.bind(null, record),
        auth: 'shedPen:shed_pen:edit',
      },
    ];
  }

  /**
   * 下拉操作栏
   */
  function getDropDownAction(record) {
    return [
      {
        label: '详情',
        onClick: handleDetail.bind(null, record),
      },
      {
        label: '删除',
        popConfirm: {
          title: '是否确认删除',
          confirm: handleDelete.bind(null, record),
          placement: 'topLeft',
        },
        auth: 'shedPen:shed_pen:delete',
      },
    ];
  }
</script>

<style lang="less" scoped>
  .livestock-bg {
    /* 移除全局背景 */
  }

  .livestock-header {
    position: relative;
    background-image: url('/changpeng1.png');
    background-repeat: no-repeat;
    background-position: center 67%;
    background-size: cover;
    padding: 24px 24px 20px 24px;
    border-radius: 12px 12px 0 0;
    box-shadow: 0 2px 8px #e8f5e9;
    overflow: hidden;
  }

  .livestock-header::after {
    content: '';
    position: absolute;
    inset: 0;
    border-radius: 12px 12px 0 0;
    background: linear-gradient(90deg, rgba(76, 175, 80, 0.18), rgba(255, 255, 255, 0.12));
    pointer-events: none;
  }

  .livestock-header-main {
    display: flex;
    align-items: center;
    justify-content: space-between;
    flex-wrap: wrap;
    gap: 16px;
    position: relative;
    z-index: 1;
  }

  .livestock-header-title-bg {
    display: inline-block;
    background: rgba(255, 255, 255, 0.7);
    border-radius: 12px;
    padding: 12px 28px 8px 28px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  }

  .header-title-icon {
    font-size: 28px;
    color: #43a047;
    vertical-align: middle;
    margin-right: 10px;
  }

  .livestock-header-desc {
    color: #256029;
    font-size: 15px;
    font-weight: 500;
    text-shadow:
      0 2px 8px rgba(76, 175, 80, 0.18),
      0 1px 2px #fff;
  }

  .livestock-table-card {
    background: url('/changpeng3.png') no-repeat center center;
    background-size: cover;
    /* background-color: rgba(255,255,255,0.85); */
    margin-top: 0;
    border-top-left-radius: 0;
    border-top-right-radius: 0;
    box-shadow: 0 2px 8px #e8f5e9;
  }

  .livestock-btn-group {
    display: flex;
    gap: 8px;
    flex-wrap: wrap;
    align-items: center;
    margin-bottom: 0;
  }

  :deep(.ant-table),
  :deep(.ant-table-container),
  :deep(.ant-table-content),
  :deep(.ant-table-body),
  :deep(.ant-table-thead),
  :deep(.ant-table-tbody) {
    background: transparent !important;
  }

  :deep(.ant-table-thead > tr > th) {
    background: rgba(208, 245, 200, 0.85) !important;
    color: #256029;
    font-weight: bold;
  }

  :deep(.ant-table-tbody > tr > td) {
    background: rgba(255, 255, 255, 0.6) !important;
    font-size: 15px;
    padding: 12px 8px;
  }

  :deep(.ant-btn-primary) {
    background: #43a047;
    border: none;
  }

  :deep(.ant-card-body) {
    background: transparent !important;
  }
</style>
