<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name"/>
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" controls-position="right" min="0"/>
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
          -->
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career"/>
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
      </el-form-item>

      <!-- 讲师头像：TODO -->
      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">

          <!-- 头衔缩略图 -->
          <pan-thumb :image="teacher.avatar"/>
          <!-- 文件上传按钮 -->
          <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
          </el-button>

          <!--
      v-show：是否显示上传组件
      :key：类似于id，如果一个页面多个图片上传控件，可以做区分
      :url：后台上传的url地址
      @close：关闭上传组件
      @crop-upload-success：上传成功后的回调 -->
          <image-cropper
                         v-show="imagecropperShow"
                         :width="300"
                         :height="300"
                         :key="imagecropperKey"
                         :url="BASE_API+'/eduservice/oss/upload'"
                         field="file"
                         @close="close"
                         @crop-upload-success="cropSuccess"/>

      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import teacher from '@/api/teacher'

//上传头像组件
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

const defaultForm = {
  name: '',
  sort: 0,
  level: '',
  career: '',
  intro: '',
  avatar: ''//默认头像的地址
}

export default {
  //声明使用的额外的组件
    components:{ImageCropper,PanThumb},
    data() {
        return {//设置teacher对象初始值
            BASE_API: process.env.BASE_API,//结构api的地址
            imagecropperShow:false,// 是否显示上传组件
            imagecropperKey:0,// 上传组件id
            teacher: defaultForm
        }
    },
    watch: {
        $route(to, from) {
            console.log('watch $route')
            this.init()
        }
    },
    created () {
        this.init()
    },
    methods: {
      cropSuccess(data){//上传成功
          //获取到后台返回图片的地址，显示到页面中
          this.imagecropperShow=false
          this.teacher.avatar=data.imgurl
          // 上传成功后，重新打开上传组件时初始化组件，否则显示上一次的上传结果
          this.imagecropperKey = this.imagecropperKey + 1
      },
      //关闭上传组件
      close(){
        //点击X关闭弹框
        this.imagecropperShow=false
        // 上传失败后，重新打开上传组件时初始化组件，否则显示上一次的上传结果
        this.imagecropperKey = this.imagecropperKey + 1
      },
      init() {
            //在页面加载之前，判断路由里面是否有id值
            //如果有id值，调用方法根据id查询
            //从路由里面获取值
            if(this.$route.params && this.$route.params.id) {//修改数据回显
                const id = this.$route.params.id
                //调用方法根据id查询
                this.getTeacherById(id)
            } else {//添加
                //表单数据清空
                this.teacher = { ...defaultForm }
            }
        },
        //实现添加和修改使用同一个表单
        //预留做修改
        saveOrUpdate() {
            //判断点击保存，执行添加操作 还是修改操作
            if(!this.teacher.id) {
                //调用添加的方法
                this.saveTeacher()
            } else {
                this.updateTeacher()
            }
        },

        //根据id查询
        getTeacherById(id) {
            teacher.getTeacherId(id)
                .then(response => {
                    this.teacher = response.data.eduTeacher
                })
        },
        //添加讲师的方法
        saveTeacher() {
            //调用后台接口的方法实现添加
            teacher.saveTeacher(this.teacher)
                .then(() => {
                    //请求之后，添加之后，提示用户,
                    return this.$message({
                        type: 'success',
                        message: '添加成功!'
                    })
                }).then(() => {
                    //回到列表页面中，通过路由跳转回到列表页面中
                    this.$router.push({path: '/teacher'})
                })
                .catch(() => {
                    this.$message({
                        type: 'error',
                        message: '添加失败!'
                    })
                })
        },
        //修改讲师的方法
        updateTeacher() {
            teacher.updateTeacherId(this.teacher.id,this.teacher)
                .then(() => {
                    //请求之后，添加之后，提示用户,
                    return this.$message({
                        type: 'success',
                        message: '修改成功!'
                    })
                }).then(() => {
                    //路由跳转
                    this.$router.push({path: '/teacher'})
                }).catch(() => {
                    this.$message({
                        type: 'error',
                        message: '修改失败!'
                    })
                })
        }
    }
}

</script>
