<route lang="json5" type="page">
{
layout: 'default',
style: {
navigationStyle: 'custom',
navigationBarTitleText: '畜只表',
},
}
</route>

<template>
  <PageLayout :navTitle="navTitle" :backRouteName="backRouteName">
    <scroll-view class="scrollArea" scroll-y>
      <view class="form-container">
        <wd-form ref="form" :model="myFormData">
          <wd-cell-group border>
          <view class="{ 'mt-14px': 0 == 0 }">
              <wd-input
                  label-width="100px"
                  v-model="myFormData['commonEarTag']"
                  :label="get4Label('普通耳号')"
                  name='commonEarTag'
                  prop='commonEarTag'
                  placeholder="请选择普通耳号"
                  :rules="[
                                  { required: true, message: '请输入普通耳号!'},
                  ]"
                  clearable
              />
        </view>
          <view class="{ 'mt-14px': 1 == 0 }">
              <wd-input
                  label-width="100px"
                  v-model="myFormData['electronicEarTag']"
                  :label="get4Label('电子耳号')"
                  name='electronicEarTag'
                  prop='electronicEarTag'
                  placeholder="请选择电子耳号"
                  :rules="[
                                  { required: true, message: '请输入电子耳号!'},
                  ]"
                  clearable
              />
        </view>
          <view class="{ 'mt-14px': 0 == 0 }">
              <online-select
                :label="get4Label('品种（如滩羊）')"
                 labelWidth="100px"
                 type="list"
                 name='breed'
                 dict=""
                v-model="myFormData['breed']"
              ></online-select>
        </view>
          <view class="{ 'mt-14px': 1 == 0 }">
              <online-select
                :label="get4Label('类别（种羊、肉羊等）')"
                 labelWidth="100px"
                 type="list"
                 name='category'
                 dict=""
                v-model="myFormData['category']"
              ></online-select>
        </view>
          <view class="{ 'mt-14px': 0 == 0 }">
              <online-select
                :label="get4Label('性别（M/F）')"
                 labelWidth="100px"
                 type="list"
                 name='gender'
                 dict=""
                v-model="myFormData['gender']"
              ></online-select>
        </view>
          <view class="{ 'mt-14px': 1 == 0 }">
              <online-date
                  :label="get4Label('出生日期')"
                  labelWidth="100px"
                  type="date"
                  name='birthDate'
                  v-model:value="myFormData['birthDate']"
              ></online-date>
        </view>
          <view class="{ 'mt-14px': 0 == 0 }">
              <online-select
                :label="get4Label('来源（采购 / 自繁）')"
                 labelWidth="100px"
                 type="list"
                 name='source'
                 dict=""
                v-model="myFormData['source']"
              ></online-select>
        </view>
          <view class="{ 'mt-14px': 1 == 0 }">
              <online-select
                :label="get4Label('状态（正常 / 死亡 / 淘汰 / 已售）')"
                 labelWidth="100px"
                 type="list"
                 name='status'
                 dict=""
                v-model="myFormData['status']"
              ></online-select>
        </view>
          <view class="{ 'mt-14px': 0 == 0 }">
              <online-select
                :label="get4Label('当前阶段（幼崽 / 育肥 / 成年）')"
                 labelWidth="100px"
                 type="list"
                 name='currentStage'
                 dict=""
                v-model="myFormData['currentStage']"
              ></online-select>
        </view>
          <view class="{ 'mt-14px': 1 == 0 }">
              <wd-input
                  label-width="100px"
                  v-model="myFormData['currentShedPenId']"
                  :label="get4Label('当前所在栅栏ID')"
                  name='currentShedPenId'
                  prop='currentShedPenId'
                  placeholder="请选择当前所在栅栏ID"
                  :rules="[
                                  { required: true, message: '请输入当前所在栅栏ID!'},
                  ]"
                  clearable
              />
        </view>
          <view class="{ 'mt-14px': 0 == 0 }">
              <wd-input
                  label-width="100px"
                  v-model="myFormData['fatherId']"
                  :label="get4Label('父亲畜只 ID（可选）')"
                  name='fatherId'
                  prop='fatherId'
                  placeholder="请选择父亲畜只 ID（可选）"
                  :rules="[
                                  { required: true, message: '请输入父亲畜只 ID（可选）!'},
                  ]"
                  clearable
              />
        </view>
          <view class="{ 'mt-14px': 1 == 0 }">
              <wd-input
                  label-width="100px"
                  v-model="myFormData['motherId']"
                  :label="get4Label('母亲畜只 ID（可选）')"
                  name='motherId'
                  prop='motherId'
                  placeholder="请选择母亲畜只 ID（可选）"
                  :rules="[
                                  { required: true, message: '请输入母亲畜只 ID（可选）!'},
                  ]"
                  clearable
              />
        </view>
          <view class="{ 'mt-14px': 0 == 0 }">
               <wd-input
                   label-width="100px"
                   v-model="myFormData['weight']"
                   :label="get4Label('当前体重 (kg)（可选，可通过体尺测量更新）')"
                   name='weight'
                   prop='weight'
                   placeholder="请选择当前体重 (kg)（可选，可通过体尺测量更新）"
                   inputMode="numeric"
                   :rules="[
                             { required: true, message: '请输入当前体重 (kg)（可选，可通过体尺测量更新）!'},
                   ]"
                   clearable
              />
        </view>
          <view class="{ 'mt-14px': 1 == 0 }">
              <wd-input
                  label-width="100px"
                  v-model="myFormData['purchaseInfoId']"
                  :label="get4Label('采购记录 ID（可选）')"
                  name='purchaseInfoId'
                  prop='purchaseInfoId'
                  placeholder="请选择采购记录 ID（可选）"
                  :rules="[
                                  { required: true, message: '请输入采购记录 ID（可选）!'},
                  ]"
                  clearable
              />
        </view>
          <view class="{ 'mt-14px': 0 == 0 }">
              <wd-input
                  label-width="100px"
                  v-model="myFormData['livestockId']"
                  :label="get4Label('畜只唯一标识（电子耳号）')"
                  name='livestockId'
                  prop='livestockId'
                  placeholder="请选择畜只唯一标识（电子耳号）"
                  :rules="[
                                  { required: true, message: '请输入畜只唯一标识（电子耳号）!'},
                  ]"
                  clearable
              />
        </view>
          </wd-cell-group>
        </wd-form>
      </view>
    </scroll-view>
    <view class="footer">
      <wd-button :disabled="loading" block :loading="loading" @click="handleSubmit">提交</wd-button>
    </view>
  </PageLayout>
</template>

<script lang="ts" setup>
import { onLoad } from '@dcloudio/uni-app'
import { http } from '@/utils/http'
import { useToast } from 'wot-design-uni'
import { useRouter } from '@/plugin/uni-mini-router'
import { ref, onMounted, computed,reactive } from 'vue'
import OnlineImage from '@/components/online/view/online-image.vue'
import OnlineFile from '@/components/online/view/online-file.vue'
import OnlineFileCustom from '@/components/online/view/online-file-custom.vue'
import OnlineSelect from '@/components/online/view/online-select.vue'
import OnlineTime from '@/components/online/view/online-time.vue'
import OnlineDate from '@/components/online/view/online-date.vue'
import OnlineRadio from '@/components/online/view/online-radio.vue'
import OnlineCheckbox from '@/components/online/view/online-checkbox.vue'
import OnlineMulti from '@/components/online/view/online-multi.vue'
import OnlinePopupLinkRecord from '@/components/online/view/online-popup-link-record.vue'
import SelectDept from '@/components/SelectDept/SelectDept.vue'
import SelectUser from '@/components/SelectUser/SelectUser.vue'
defineOptions({
  name: 'LivestockForm',
  options: {
    styleIsolation: 'shared',
  },
})
const toast = useToast()
const router = useRouter()
const form = ref(null)
// 定义响应式数据
const myFormData = reactive({})
const loading = ref(false)
const navTitle = ref('新增')
const dataId = ref('')
const backRouteName = ref('LivestockList')
// 定义 initForm 方法
const initForm = (item) => {
  console.log('initForm item', item)
  if(item?.dataId){
    dataId.value = item.dataId;
    navTitle.value = item.dataId?'编辑':'新增';
    initData();
  }
}
// 初始化数据
const initData = () => {
  http.get("/livestock/livestock/queryById",{id:dataId.value}).then((res) => {
    if (res.success) {
      let obj = res.result
      Object.assign(myFormData, { ...obj })
    }else{
      toast.error(res?.message || '表单加载失败！')
    }
  })
}
const handleSuccess = () => {
  uni.$emit('refreshList');
  router.back()
}
// 提交表单
const handleSubmit = () => {
  let url = dataId.value?'/livestock/livestock/edit':'/livestock/livestock/add';
  form.value
    .validate()
    .then(({ valid, errors }) => {
      if (valid) {
        loading.value = true;
        http.post(url,myFormData).then((res) => {
          loading.value = false;
          if (res.success) {
            toast.success('保存成功');
            handleSuccess()
          }else{
            toast.error(res?.message || '表单保存失败！')
          }
        })
      }
    })
    .catch((error) => {
      console.log(error, 'error')
      loading.value = false;
    })
}
// 标题
const get4Label = computed(() => {
  return (label) => {
    return label && label.length > 4 ? label.substring(0, 4) : label;
  }
})

// 标题
const getFormSchema = computed(() => {
  return (dictTable,dictCode,dictText) => {
    return {
      dictCode,
      dictTable,
      dictText
    };
  }
})
/**
 * 获取日期控件的扩展类型
 * @param picker
 * @returns {string}
 */
const getDateExtendType = (picker: string) => {
    let mapField = {
      month: 'year-month',
      year: 'year',
      quarter: 'quarter',
      week: 'week',
      day: 'date',
    }
    return picker && mapField[picker]
      ? mapField[picker]
      : 'date'
}
//设置pop返回值
const setFieldsValue = (data) => {
   Object.assign(myFormData, {...data })
}
// onLoad 生命周期钩子
onLoad((option) => {
  initForm(option)
})
</script>

<style lang="scss" scoped>
.footer {
  width: 100%;
  padding: 10px 20px;
  padding-bottom: calc(constant(safe-area-inset-bottom) + 10px);
  padding-bottom: calc(env(safe-area-inset-bottom) + 10px);
}
</style>
