<template>
  <div class="cow-manage-container">
    <div class="cow-manage-content">
      <h2 style="text-align: center; font-size: 28px; margin-bottom: 24px">🐮 牛牛管理可视化大屏</h2>
      
      <!-- 数据总览卡片 -->
      <a-row gutter="16" style="margin-bottom: 24px">
        <a-col :span="6">
          <a-card class="stat-card">
            <div style="font-size: 16px; color: #888">🐮 牛牛总数</div>
            <div style="font-size: 32px; color: #27ae60">{{ totalCount }}头</div>
          </a-card>
        </a-col>
        <a-col :span="6">
          <a-card class="stat-card">
            <div style="font-size: 16px; color: #888">✅ 正常状态</div>
            <div style="font-size: 32px; color: #1890ff">{{ normalCount }}头</div>
          </a-card>
        </a-col>
        <a-col :span="6">
          <a-card class="stat-card">
            <div style="font-size: 16px; color: #888">📅 今日新增</div>
            <div style="font-size: 32px; color: #faad14">{{ todayCount }}头</div>
          </a-card>
        </a-col>
        <a-col :span="6">
          <a-card class="stat-card">
            <div style="font-size: 16px; color: #888">⚠️ 健康预警</div>
            <div style="font-size: 32px; color: #f5222d">{{ warningCount }}头</div>
          </a-card>
        </a-col>
      </a-row>

      <!-- 图表区域 -->
      <a-row gutter="16" style="margin-bottom: 24px">
        <a-col :span="8">
          <a-card title="按类别统计" class="chart-card">
            <div style="height: 200px">
              <v-chart v-if="categoryStats.length > 0" :option="categoryOption" style="height: 100%" />
              <div v-else style="height: 100%; display: flex; align-items: center; justify-content: center; color: #bbb">暂无数据</div>
            </div>
          </a-card>
        </a-col>
        <a-col :span="8">
          <a-card title="按阶段统计" class="chart-card">
            <div style="height: 200px">
              <v-chart v-if="stageStats.length > 0" :option="stageOption" style="height: 100%" />
              <div v-else style="height: 100%; display: flex; align-items: center; justify-content: center; color: #bbb">暂无数据</div>
            </div>
          </a-card>
        </a-col>
        <a-col :span="8">
          <a-card title="按性别统计" class="chart-card">
            <div style="height: 200px">
              <v-chart v-if="genderStats.length > 0" :option="genderOption" style="height: 100%" />
              <div v-else style="height: 100%; display: flex; align-items: center; justify-content: center; color: #bbb">暂无数据</div>
            </div>
          </a-card>
        </a-col>
      </a-row>

      <!-- 最近记录 -->
      <a-card title="最近记录" style="margin-bottom: 24px" class="table-card">
        <a-table :columns="recentColumns" :data-source="recentRecords" :pagination="false" size="small">
          <template #bodyCell="{ column, record }">
            <template v-if="column.key === 'status'">
              <a-tag :color="getStatusColor(record.status)">{{ record.status }}</a-tag>
            </template>
            <template v-else-if="column.key === 'createTime'">
              {{ formatDate(record.createTime) }}
            </template>
          </template>
        </a-table>
      </a-card>

      <!-- 快捷入口 -->
      <div style="margin-top: 32px">
        <h3 style="font-size: 20px; margin-bottom: 16px">快捷入口</h3>
        <a-row gutter="12" justify="space-between">
          <a-col :span="3" v-for="item in quickLinks" :key="item.title">
            <a-card hoverable @click="goTo(item.route)" style="text-align: center; cursor: pointer" class="quick-link-card">
              <div style="font-size: 28px; margin-bottom: 8px">{{ item.icon }}</div>
              <div style="font-size: 14px; line-height: 1.2">{{ item.title }}</div>
            </a-card>
          </a-col>
        </a-row>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { ref, onMounted, computed } from 'vue';
  import { useRouter } from 'vue-router';
  import VChart from 'vue-echarts';
  import { use } from 'echarts/core';
  import { CanvasRenderer } from 'echarts/renderers';
  import { PieChart } from 'echarts/charts';
  import { TitleComponent, TooltipComponent, LegendComponent } from 'echarts/components';
  import { cowcowTotal } from '../../livestock/Livestock.api';

  // 注册 ECharts 组件
  use([CanvasRenderer, PieChart, TitleComponent, TooltipComponent, LegendComponent]);

  const router = useRouter();

  const quickLinks = [
    { title: '牛牛信息管理', icon: '🐮', route: '/livestock/livestockList' },
    { title: '饲养管理', icon: '🌱', route: '/feedingRecord/feedingRecordList' },
    { title: '健康管理', icon: '🩺', route: '/vaccinationRecord/vaccinationRecordList' },
    { title: '繁育管理', icon: '🐂', route: '/reproductionRecord/reproductionRecordList' },
    { title: '库存管理', icon: '📦', route: '/inventory/inventoryList' },
    { title: '销售管理', icon: '💰', route: '/saleRecord/saleRecordList' },
    { title: '场棚管理', icon: '🏠', route: '/shedPen/shedPenList' },
  ];

  function goTo(route: string) {
    router.push(route);
  }

  // 数据绑定
  const totalCount = ref(0);
  const normalCount = ref(0);
  const todayCount = ref(0);
  const warningCount = ref(0);
  const categoryStats = ref<Array<{ name: string; value: number }>>([]);
  const stageStats = ref<Array<{ name: string; value: number }>>([]);
  const genderStats = ref<Array<{ name: string; value: number }>>([]);
  const recentRecords = ref<any[]>([]);

  // 最近记录表格列定义
  const recentColumns = [
    { title: '耳号', dataIndex: 'commonEarTag', key: 'commonEarTag' },
    { title: '品种', dataIndex: 'breed', key: 'breed' },
    { title: '类别', dataIndex: 'category', key: 'category' },
    { title: '状态', dataIndex: 'status', key: 'status' },
    { title: '创建时间', dataIndex: 'createTime', key: 'createTime' },
  ];

  // 获取状态颜色
  function getStatusColor(status: string) {
    switch (status) {
      case '正常':
        return 'green';
      case '死亡':
        return 'red';
      case '淘汰':
        return 'orange';
      case '已售':
        return 'blue';
      default:
        return 'default';
    }
  }

  // 格式化日期
  function formatDate(dateStr: string) {
    if (!dateStr) return '';
    return new Date(dateStr).toLocaleString('zh-CN');
  }

  // 计算图表配置
  const categoryOption = computed(() => ({
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b}: {c}头 ({d}%)',
    },
    legend: {
      orient: 'vertical',
      left: 'left',
      bottom: 0,
    },
    series: [
      {
        name: '类别分布',
        type: 'pie',
        radius: '50%',
        data: categoryStats.value,
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)',
          },
        },
      },
    ],
  }));

  const stageOption = computed(() => ({
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b}: {c}头 ({d}%)',
    },
    legend: {
      orient: 'vertical',
      left: 'left',
      bottom: 0,
    },
    series: [
      {
        name: '阶段分布',
        type: 'pie',
        radius: '50%',
        data: stageStats.value,
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)',
          },
        },
      },
    ],
  }));

  const genderOption = computed(() => ({
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b}: {c}头 ({d}%)',
    },
    legend: {
      orient: 'vertical',
      left: 'left',
      bottom: 0,
    },
    series: [
      {
        name: '性别分布',
        type: 'pie',
        radius: '50%',
        data: genderStats.value,
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)',
          },
        },
      },
    ],
  }));

  // 页面挂载后获取后端统计
  onMounted(() => {
    console.log('mounted方法');
    cowcowTotal()
      .then((data) => {
        console.log('🐮 后端返回数据:', data);

        // 基础统计数据
        totalCount.value = data.totalCount || 0;
        normalCount.value = data.normalCount || 0;
        todayCount.value = data.todayCount || 0;
        warningCount.value = data.warningCount || 0;

        // 类别统计
        if (data.categoryStats) {
          categoryStats.value = Object.entries(data.categoryStats).map(([name, value]) => ({
            name,
            value: Number(value),
          }));
        }

        // 阶段统计
        if (data.stageStats) {
          stageStats.value = Object.entries(data.stageStats).map(([name, value]) => ({
            name,
            value: Number(value),
          }));
        }

        // 性别统计
        if (data.genderStats) {
          genderStats.value = Object.entries(data.genderStats).map(([name, value]) => ({
            name,
            value: Number(value),
          }));
        }

        // 最近记录
        if (data.recentRecords) {
          recentRecords.value = data.recentRecords;
        }

        console.log('🐮 处理后的数据:', {
          totalCount: totalCount.value,
          normalCount: normalCount.value,
          todayCount: todayCount.value,
          warningCount: warningCount.value,
          categoryStats: categoryStats.value,
          stageStats: stageStats.value,
          genderStats: genderStats.value,
          recentRecords: recentRecords.value,
        });
      })
      .catch((err) => {
        console.error('🐮 获取牛牛统计失败:', err);
      });
  });
</script>

<style lang="less" scoped>
.cow-manage-container {
  min-height: 100vh;
  background-image: url('/leftbackground.png');
  background-repeat: no-repeat;
  background-position: center center;
  background-size: cover;
  background-attachment: fixed;
  padding: 32px;
}

.cow-manage-content {
  background: rgba(255, 255, 255, 0.9);
  border-radius: 16px;
  padding: 32px;
  backdrop-filter: blur(10px);
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

.stat-card {
  background: rgba(255, 255, 255, 0.95) !important;
  border: 1px solid rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(10px);
  transition: all 0.3s ease;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
  }
}

.chart-card {
  background: rgba(255, 255, 255, 0.95) !important;
  border: 1px solid rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(10px);
}

.table-card {
  background: rgba(255, 255, 255, 0.95) !important;
  border: 1px solid rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(10px);
}

.quick-link-card {
  background: rgba(255, 255, 255, 0.95) !important;
  border: 1px solid rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(10px);
  transition: all 0.3s ease;
  padding: 16px 8px;
  min-height: 100px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  &:hover {
    transform: translateY(-3px);
    box-shadow: 0 12px 30px rgba(0, 0, 0, 0.2);
    background: rgba(255, 255, 255, 0.98) !important;
  }
}

:deep(.ant-card-body) {
  background: transparent !important;
}

:deep(.ant-table) {
  background: rgba(255, 255, 255, 0.8) !important;
}

:deep(.ant-table-thead > tr > th) {
  background: rgba(255, 255, 255, 0.9) !important;
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
}

:deep(.ant-table-tbody > tr > td) {
  background: rgba(255, 255, 255, 0.7) !important;
  border-bottom: 1px solid rgba(0, 0, 0, 0.05);
}

:deep(.ant-table-tbody > tr:hover > td) {
  background: rgba(255, 255, 255, 0.9) !important;
}
</style>
