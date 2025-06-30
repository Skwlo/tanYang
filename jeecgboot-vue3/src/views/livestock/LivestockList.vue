<template>
  <div class="livestock-bg">
    <!-- 顶部绿色渐变卡片 -->
    <div class="livestock-header">
      <div class="livestock-header-main">
        <div class="livestock-header-title-bg">
          <h2>🐮 绿色牧场 · 牛牛信息管理</h2>
          <div class="livestock-header-desc">管理所有牛牛的基础信息、状态与操作</div>
        </div>
      </div>
    </div>
    <!-- 卡片包裹表格 -->
    <a-card class="livestock-table-card">
      <BasicTable @register="registerTable" :rowSelection="rowSelection">
        <template #tableTitle>
          <div class="livestock-btn-group">
            <a-button type="primary" v-auth="'livestock:livestock:add'" @click="handleAdd" preIcon="ant-design:plus-outlined">新增</a-button>
            <a-button
              type="primary"
              style="background: #66bb6a; border: none"
              v-auth="'livestock:livestock:exportXls'"
              preIcon="ant-design:export-outlined"
            >
              导出
            </a-button>
            <j-upload-button
              type="primary"
              style="background: #81c784; border: none"
              v-auth="'livestock:livestock:importExcel'"
              preIcon="ant-design:import-outlined"
            >
              导入
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
              <a-button v-auth="'livestock:livestock:deleteBatch'" style="background: #a5d6a7; border: none">
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
    </a-card>
    <LivestockModal @register="registerModal" @success="handleSuccess" />
  </div>
</template>

<script lang="ts" name="livestock-livestock" setup>
  import { ref, reactive, onMounted } from 'vue';
  import { BasicTable, TableAction } from '/@/components/Table';
  import { useModal } from '/@/components/Modal';
  import { useListPage } from '/@/hooks/system/useListPage';
  import LivestockModal from './components/LivestockModal.vue';
  import { columns, searchFormSchema, superQuerySchema } from './Livestock.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl } from './Livestock.api';
  // import { useUserStore } from '/@/store/modules/user';
  const queryParam = reactive<any>({});
  // const userStore = useUserStore();
  //注册model
  const [registerModal, { openModal }] = useModal();
  //注册table数据
  const { tableContext } = useListPage({
      tableProps: {
           title: 'livestock',
           api: list,
           columns,
           canResize: false,
           formConfig: {
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
            name: 'livestock',
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

  // 统计数据（可用后端接口替换）
  const totalCow = ref(0);
  const todayAdd = ref(0);
  onMounted(() => {
    // 这里可以用 axios 请求后端统计接口
    totalCow.value = 128;
    todayAdd.value = 3;
  });

  function handleSuperQuery(params) {
    Object.keys(params).map((k) => {
      queryParam[k] = params[k];
    });
    reload();
  }
  function handleAdd() {
     openModal(true, {
       isUpdate: false,
       showFooter: true,
     });
  }
  function handleEdit(record: Recordable) {
     openModal(true, {
       record,
       isUpdate: true,
       showFooter: true,
     });
   }
  function handleDetail(record: Recordable) {
     openModal(true, {
       record,
       isUpdate: true,
       showFooter: false,
     });
   }
  async function handleDelete(record) {
     await deleteOne({ id: record.id }, handleSuccess);
   }
  async function batchHandleDelete() {
     await batchDelete({ ids: selectedRowKeys.value }, handleSuccess);
   }
  function handleSuccess() {
      (selectedRowKeys.value = []) && reload();
   }
  function getTableAction(record) {
       return [
         {
           label: '编辑',
           onClick: handleEdit.bind(null, record),
           auth: 'livestock:livestock:edit',
         },
       ];
   }
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
           auth: 'livestock:livestock:delete',
         },
       ];
   }
</script>

<style lang="less" scoped>
.livestock-bg {
  /* 移除全局背景 */
}
.livestock-header {
  background-image: url('/introduce.png');
  background-repeat: no-repeat;
  background-position: center 25%;
  background-size: cover;
  padding: 24px 24px 20px 24px;
  border-radius: 12px 12px 0 0;
  box-shadow: 0 2px 8px #e8f5e9;
}
.livestock-header-main {
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-wrap: wrap;
  gap: 16px;
}
.livestock-header-desc {
  color: #388e3c;
  font-size: 15px;
}
.livestock-table-card {
  background: url('@/assets/images/bgbackground.png') no-repeat center center;
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
  background: rgba(208,245,200,0.85) !important;
  color: #256029;
  font-weight: bold;
}
:deep(.ant-table-tbody > tr > td) {
  background: rgba(255,255,255,0.6) !important;
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
.livestock-header-title-bg {
  display: inline-block;
  background: rgba(255,255,255,0.7);
  border-radius: 12px;
  padding: 12px 28px 8px 28px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
}
</style>
