<template>
    <view>
        <!--标题和返回-->
		<cu-custom :bgColor="NavBarColor" isBack :backRouterName="backRouteName">
			<block slot="backText">返回</block>
			<block slot="content">体尺测量记录表</block>
		</cu-custom>
		 <!--表单区域-->
		<view>
			<form>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">畜只 ID：</text></view>
                  <input  placeholder="请输入畜只 ID" v-model="model.livestockId"/>
                </view>
              </view>
              <my-date label="测量日期：" fields="day" v-model="model.measureDate" placeholder="请输入测量日期"></my-date>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">测量阶段（羔羊期 / 育肥期等）：</text></view>
                  <input  placeholder="请输入测量阶段（羔羊期 / 育肥期等）" v-model="model.measureStage"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">月龄：</text></view>
                  <input type="number" placeholder="请输入月龄" v-model="model.age"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">体高 (cm)：</text></view>
                  <input type="number" placeholder="请输入体高 (cm)" v-model="model.bodyHeight"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">体重(kg)：</text></view>
                  <input type="number" placeholder="请输入体重(kg)" v-model="model.bodyWeight"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">体长(cm)：</text></view>
                  <input type="number" placeholder="请输入体长(cm)" v-model="model.bodyLength"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">胸围(cm)：</text></view>
                  <input type="number" placeholder="请输入胸围(cm)" v-model="model.bust"/>
                </view>
              </view>
              <view class="cu-form-group">
                <view class="flex align-center">
                  <view class="title"><text space="ensp">管围(cm)：</text></view>
                  <input type="number" placeholder="请输入管围(cm)" v-model="model.pipeCircumference"/>
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
        name: "BodyMeasurementForm",
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
                  queryById: "/bodyMeasurement/bodyMeasurement/queryById",
                  add: "/bodyMeasurement/bodyMeasurement/add",
                  edit: "/bodyMeasurement/bodyMeasurement/edit",
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
