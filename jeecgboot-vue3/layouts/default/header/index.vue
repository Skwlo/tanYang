<template>
  <Header :class="getHeaderClass">
    <!-- left start -->
    <div :class="`${prefixCls}-left`">
      <!-- logo -->
      <AppLogo v-if="getShowHeaderLogo || getIsMobile" :class="`${prefixCls}-logo`" :theme="getHeaderTheme" :style="getLogoWidth" />
      <LayoutTrigger
        v-if="(getShowContent && getShowHeaderTrigger && !getSplit && !getIsMixSidebar) || getIsMobile"
        :theme="getHeaderTheme"
        :sider="false"
      />
      <LayoutBreadcrumb v-if="getShowContent && getShowBread" :theme="getHeaderTheme" />
      <!-- 欢迎语 -->
      <span v-if="getShowContent && getShowBreadTitle && !getIsMobile" :class="[prefixCls, `${prefixCls}--${getHeaderTheme}`,'headerIntroductionClass']"> {{t('layout.header.welcomeIn')}} {{ title }} </span>
    </div>
    <!-- left end -->

    <!-- menu start -->
    <div :class="`${prefixCls}-menu`" v-if="getShowTopMenu && !getIsMobile">
      <LayoutMenu :isHorizontal="true" :theme="getHeaderTheme" :splitType="getSplitType" :menuMode="getMenuMode" />
    </div>
    <!-- menu-end -->

    <!-- action  -->
    <div :class="`${prefixCls}-action`">
      <AppSearch :class="`${prefixCls}-action__item `" v-if="getShowSearch" />

      <!-- 喇叭按钮 -->
      <img
        src="/trumpet.png"
        alt="播放背景音乐"
        style="width: 32px; height: 32px; cursor: pointer; margin-left: 8px;"
        @click="toggleAudio"
      />
      <audio ref="audioRef" src="/cowvoice.mp3" loop></audio>

      <ErrorAction v-if="getUseErrorHandle" :class="`${prefixCls}-action__item error-action`" />

      <Notify v-if="getShowNotice" :class="`${prefixCls}-action__item notify-item`" />

      <FullScreen v-if="getShowFullScreen" :class="`${prefixCls}-action__item fullscreen-item`" />

      <LockScreen v-if="getUseLockPage" />

      <AppLocalePicker v-if="getShowLocalePicker" :reload="true" :showText="false" :class="`${prefixCls}-action__item`" />

      <!-- 新增牛牛管理按钮 -->
      <a-button
        type="primary"
        @click="goCowPage"
        class="cow-manage-btn"
        size="middle"
      >
        <template #icon>
          <span class="cow-icon">🐮</span>
        </template>
        🐮牛牛管理
      </a-button>

      <UserDropDown :theme="getHeaderTheme" />

      <SettingDrawer v-if="getShowSetting" :class="`${prefixCls}-action__item`" />
      <!-- ai助手
      <Aide></Aide> -->
    </div>
  </Header>
  <LoginSelect ref="loginSelectRef" @success="loginSelectOk"></LoginSelect>
</template>
<script lang="ts">
import { defineComponent, unref, computed, ref, onMounted, toRaw } from 'vue';
import { useGlobSetting } from '/@/hooks/setting';
import { propTypes } from '/@/utils/propTypes';

import { Layout } from 'ant-design-vue';
import { AppLogo } from '/@/components/Application';
import LayoutMenu from '../menu/index.vue';
import LayoutTrigger from '../trigger/index.vue';

import { AppSearch } from '/@/components/Application';

import { useHeaderSetting } from '/@/hooks/setting/useHeaderSetting';
import { useMenuSetting } from '/@/hooks/setting/useMenuSetting';
import { useRootSetting } from '/@/hooks/setting/useRootSetting';

import { MenuModeEnum, MenuSplitTyeEnum } from '/@/enums/menuEnum';
import { SettingButtonPositionEnum } from '/@/enums/appEnum';
import { AppLocalePicker } from '/@/components/Application';

import { UserDropDown, LayoutBreadcrumb, FullScreen, Notify, ErrorAction, LockScreen } from './components';
import { useAppInject } from '/@/hooks/web/useAppInject';
import { useDesign } from '/@/hooks/web/useDesign';

import { createAsyncComponent } from '/@/utils/factory/createAsyncComponent';
import { useLocale } from '/@/locales/useLocale';

import LoginSelect from '/@/views/sys/login/LoginSelect.vue';
import { useUserStore } from '/@/store/modules/user';
import { useI18n } from '/@/hooks/web/useI18n';
import Aide from "@/views/dashboard/ai/components/aide/index.vue"
import { useRouter } from 'vue-router';

const { t } = useI18n();

export default defineComponent({
  name: 'LayoutHeader',
  components: {
    Header: Layout.Header,
    AppLogo,
    LayoutTrigger,
    LayoutBreadcrumb,
    LayoutMenu,
    UserDropDown,
    AppLocalePicker,
    FullScreen,
    Notify,
    AppSearch,
    ErrorAction,
    LockScreen,
    LoginSelect,
    SettingDrawer: createAsyncComponent(() => import('/@/layouts/default/setting/index.vue'), {
      loading: true,
    }),
    Aide
  },
  props: {
    fixed: propTypes.bool,
  },
  setup(props) {
    const { prefixCls } = useDesign('layout-header');
    const userStore = useUserStore();
    const { getShowTopMenu, getShowHeaderTrigger, getSplit, getIsMixMode, getMenuWidth, getIsMixSidebar } = useMenuSetting();
    const { getUseErrorHandle, getShowSettingButton, getSettingButtonPosition } = useRootSetting();
    const { title } = useGlobSetting();
    const router = useRouter();

    const {
      getHeaderTheme,
      getShowFullScreen,
      getShowNotice,
      getShowContent,
      getShowBread,
      getShowHeaderLogo,
      getShowHeader,
      getShowSearch,
      getUseLockPage,
      getShowBreadTitle,
    } = useHeaderSetting();

    const { getShowLocalePicker } = useLocale();

    const { getIsMobile } = useAppInject();

    const getHeaderClass = computed(() => {
      const theme = unref(getHeaderTheme);
      return [
        prefixCls,
        {
          [`${prefixCls}--fixed`]: props.fixed,
          [`${prefixCls}--mobile`]: unref(getIsMobile),
          [`${prefixCls}--${theme}`]: theme,
        },
      ];
    });

    const getShowSetting = computed(() => {
      if (!unref(getShowSettingButton)) {
        return false;
      }
      const settingButtonPosition = unref(getSettingButtonPosition);

      if (settingButtonPosition === SettingButtonPositionEnum.AUTO) {
        return unref(getShowHeader);
      }
      return settingButtonPosition === SettingButtonPositionEnum.HEADER;
    });

    const getLogoWidth = computed(() => {
      if (!unref(getIsMixMode) || unref(getIsMobile)) {
        return {};
      }
      const width = unref(getMenuWidth) < 180 ? 180 : unref(getMenuWidth);
      return { width: `${width}px` };
    });

    const getSplitType = computed(() => {
      return unref(getSplit) ? MenuSplitTyeEnum.TOP : MenuSplitTyeEnum.NONE;
    });

    const getMenuMode = computed(() => {
      return unref(getSplit) ? MenuModeEnum.HORIZONTAL : null;
    });

    /**
     * 首页多租户部门弹窗逻辑
     */
    const loginSelectRef = ref();

    function showLoginSelect() {
      //update-begin---author:liusq  Date:20220101  for：判断登录进来是否需要弹窗选择租户----
      //判断是否是登陆进来
      const loginInfo = toRaw(userStore.getLoginInfo) || {};
      if (!!loginInfo.isLogin) {
        loginSelectRef.value.show(loginInfo);
      }
      //update-end---author:liusq  Date:20220101  for：判断登录进来是否需要弹窗选择租户----
    }

    function loginSelectOk() {
      console.log('成功。。。。。');
    }

    // 新增牛牛管理跳转方法
    function goCowPage() {
      router.push('/cow');
    }

    const audioRef = ref(null);
    const isPlaying = ref(false);
    function toggleAudio() {
      if (audioRef.value) {
        if (isPlaying.value) {
          audioRef.value.pause();
          isPlaying.value = false;
        } else {
          audioRef.value.play();
          isPlaying.value = true;
        }
      }
    }

    onMounted(() => {
      showLoginSelect();
    });

    return {
      prefixCls,
      getHeaderClass,
      getShowHeaderLogo,
      getHeaderTheme,
      getShowHeaderTrigger,
      getIsMobile,
      getShowBreadTitle,
      getShowBread,
      getShowContent,
      getSplitType,
      getSplit,
      getMenuMode,
      getShowTopMenu,
      getShowLocalePicker,
      getShowFullScreen,
      getShowNotice,
      getUseErrorHandle,
      getLogoWidth,
      getIsMixSidebar,
      getShowSettingButton,
      getShowSetting,
      getShowSearch,
      getUseLockPage,
      loginSelectOk,
      loginSelectRef,
      title,
      t,
      goCowPage,
      audioRef,
      isPlaying,
      toggleAudio
    };
  },
});
</script>
<style lang="less">
@import './index.less';
//update-begin---author:scott ---date:2022-09-30  for：默认隐藏顶部菜单面包屑-----------
//顶部欢迎语展示样式
@prefix-cls: ~'@{namespace}-layout-header';

.ant-layout .@{prefix-cls} {
  display: flex;
  padding: 0 8px;
  // update-begin--author:liaozhiyang---date:20240407---for：【QQYUN-8762】顶栏高度
  height: @header-height;
  // update-end--author:liaozhiyang---date:20240407---for：【QQYUN-8762】顶栏高度
  align-items: center;

  .headerIntroductionClass {
    margin-right: 4px;
    margin-bottom: 2px;
    border-bottom: 0px;
    border-left: 0px;
  }

  &--light {
    .headerIntroductionClass {
      color: #000;
    }
  }

  &--dark {
    .headerIntroductionClass {
      color: rgba(255, 255, 255, 1);
    }
    .anticon, .truncate {
      color: rgba(255, 255, 255, 1);
    }
  }
  //update-end---author:scott ---date::2022-09-30  for：默认隐藏顶部菜单面包屑--------------
}

// 牛牛管理按钮样式
.cow-manage-btn {
  margin-left: 8px;
  background: linear-gradient(135deg, #27ae60 0%, #2ecc71 100%);
  border: none;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(39, 174, 96, 0.3);
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  position: relative;
  overflow: hidden;
  font-weight: 600;
  letter-spacing: 0.5px;

  &::before {
    content: '';
    position: absolute;
    top: 0;
    left: -100%;
    width: 100%;
    height: 100%;
    background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.2), transparent);
    transition: left 0.5s;
  }

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 20px rgba(39, 174, 96, 0.4);
    background: linear-gradient(135deg, #2ecc71 0%, #27ae60 100%);

    &::before {
      left: 100%;
    }

    .cow-icon {
      transform: scale(1.2) rotate(5deg);
    }
  }

  &:active {
    transform: translateY(0);
    box-shadow: 0 2px 8px rgba(39, 174, 96, 0.3);
  }

  .cow-icon {
    display: inline-block;
    font-size: 16px;
    margin-right: 4px;
    transition: all 0.3s ease;
    animation: cowBounce 2s infinite;
  }

  @keyframes cowBounce {
    0%, 20%, 50%, 80%, 100% {
      transform: translateY(0);
    }
    40% {
      transform: translateY(-3px);
    }
    60% {
      transform: translateY(-2px);
    }
  }
}

// 深色主题下的按钮样式
.@{prefix-cls}--dark {
  .cow-manage-btn {
    background: linear-gradient(135deg, #27ae60 0%, #2ecc71 100%);
    color: white;

    &:hover {
      background: linear-gradient(135deg, #2ecc71 0%, #27ae60 100%);
      color: white;
    }
  }
}
</style>
