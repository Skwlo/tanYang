<template>
    <view>
        <!--标题和返回-->
		<cu-custom :bgColor="NavBarColor" isBack :backRouterName="backRouteName">
			<block slot="backText">返回</block>
			<block slot="content">畜只表</block>
		</cu-custom>
		 <!--表单区域-->
		<view>
			<form>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">普通耳号：</text></view>
                  <input  placeholder="请输入普通耳号" v-model="model.commonEarTag"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">电子耳号：</text></view>
                  <input  placeholder="请输入电子耳号" v-model="model.electronicEarTag"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">品种（如滩羊）：</text></view>
                  <input  placeholder="请输入品种（如滩羊）" v-model="model.breed"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">类别（种羊、肉羊等）：</text></view>
                  <input  placeholder="请输入类别（种羊、肉羊等）" v-model="model.category"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">性别（M/F）：</text></view>
                  <input  placeholder="请输入性别（M/F）" v-model="model.gender"/>
                </view>
              </view>
              <my-date label="出生日期：" fields="day" v-model="model.birthDate" placeholder="请输入出生日期"></my-date>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">来源（采购 / 自繁）：</text></view>
                  <input  placeholder="请输入来源（采购 / 自繁）" v-model="model.source"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">状态（正常 / 死亡 / 淘汰 / 已售）：</text></view>
                  <input  placeholder="请输入状态（正常 / 死亡 / 淘汰 / 已售）" v-model="model.status"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">当前阶段（幼崽 / 育肥 / 成年）：</text></view>
                  <input  placeholder="请输入当前阶段（幼崽 / 育肥 / 成年）" v-model="model.currentStage"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">当前所在栅栏ID：</text></view>
                  <input  placeholder="请输入当前所在栅栏ID" v-model="model.currentShedPenId"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">父亲畜只 ID（可选）：</text></view>
                  <input  placeholder="请输入父亲畜只 ID（可选）" v-model="model.fatherId"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">母亲畜只 ID（可选）：</text></view>
                  <input  placeholder="请输入母亲畜只 ID（可选）" v-model="model.motherId"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">当前体重 (kg)（可选，可通过体尺测量更新）：</text></view>
                  <input type="number" placeholder="请输入当前体重 (kg)（可选，可通过体尺测量更新）" v-model="model.weight"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">采购记录 ID（可选）：</text></view>
                  <input  placeholder="请输入采购记录 ID（可选）" v-model="model.purchaseInfoId"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">畜只唯一标识（电子耳号）：</text></view>
                  <input  placeholder="请输入畜只唯一标识（电子耳号）" v-model="model.livestockId"/>
                </view>
              </view>
				<view class="padding">
					<button class="cu-btn block bg-blue margin-tb-sm lg" @click="onSubmit">
						<text v-if="loading" class="cuIcon-loading2 cuIconfont-spin"></text>提交
					</button>
				</view>
			</form>
		</view>
    </view>
</template>

<script>
    import myDate from '@/components/my-componets/my-date.vue'

    export default {
        name: "LivestockForm",
        components:{ myDate },
        props:{
          formData:{
              type:Object,
              default:()=>{},
              required:false
          }
        },
        data(){
            return {
				CustomBar: this.CustomBar,
				NavBarColor: this.NavBarColor,
				loading:false,
                model: {},
                backRouteName:'index',
                url: {
                  queryById: "/livestock/livestock/queryById",
                  add: "/livestock/livestock/add",
                  edit: "/livestock/livestock/edit",
                },
            }
        },
        created(){
             this.initFormData();
        },
        methods:{
           initFormData(){
               if(this.formData){
                    let dataId = this.formData.dataId;
                    this.$http.get(this.url.queryById,{params:{id:dataId}}).then((res)=>{
                        if(res.data.success){
                            console.log("表单数据",res);
                            this.model = res.data.result;
                        }
                    })
                }
            },
            onSubmit() {
                let myForm = {...this.model};
                this.loading = true;
                let url = myForm.id?this.url.edit:this.url.add;
				this.$http.post(url,myForm).then(res=>{
				   console.log("res",res)
				   this.loading = false
				   this.$Router.push({name:this.backRouteName})
				}).catch(()=>{
					this.loading = false
				});
            }
        }
    }
</script>
