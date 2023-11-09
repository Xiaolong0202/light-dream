<template>
  <div style="padding-left: 5%">
    <el-card class="box-card" style="width: 90%;margin-top: 5%;margin-left: 5%">
      <div class="clearfix">
        <span>用户列表</span>

      </div>
      <div style="width: 99%">
        <el-table :data="tableData" style="margin-top: 20px" border>
          <el-table-column fixed prop="name" label="用户姓名" width="200">
          </el-table-column>
          <el-table-column prop="phone" label="用户账号" width="200">
          </el-table-column>
          <el-table-column prop="password" label="用户密码" width="200">
          </el-table-column>
          <el-table-column prop="userType" label="用户类型" width="200">
          </el-table-column>
          <el-table-column prop="score" label="积分" width="150">
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="223">
            <template #default="scope">
              <el-button @click="modify(scope.row)" type="info" size="small">查看/修改</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
  </div>

  <el-dialog
      v-model="checkboxVisible"
      title="用户信息"
      width="30%"
      @close="closeDialog">

    <el-form :model="userToModify" :disabled="disEditable" label-width="80px" style="margin-right: 20px" >
      <el-form-item label="姓名">
        <el-input v-model="userToModify.name"/>
      </el-form-item>
      <el-form-item label="账号">
        <el-input v-model="userToModify.phone"/>
      </el-form-item>
      <el-form-item label="密码">
        <el-input show-password v-model="userToModify.password"/>
      </el-form-item>
    </el-form>

    <div style="text-align: center;margin-top: 20px">
      <el-button v-if="disEditable" type="primary" @click="modifyConsent">修改</el-button>
      <el-button v-if="!disEditable" type="primary" @click="modifySend">保存</el-button>
      <el-button type="primary" @click="checkboxVisible=false">取消</el-button>
    </div>
  </el-dialog>



</template>

<script setup>
import {onMounted, ref} from "vue";
import {ElMessage} from "element-plus";
import axios from "axios";

const tableData = ref([])
const userToModify = ref([])
const checkboxVisible = ref(false)
const disEditable = ref(true)

/*对应修改填写框*/
const modify = (userModify) => {
  checkboxVisible.value = true
  userToModify.value = userModify
}
const modifyConsent=()=>{
  disEditable.value = false
}
const modifySend=()=>{
  if(userToModify.value.userType == "儿童"){
    userToModify.value.userType = 1
  }else if(userToModify.value.userType == "志愿者"){
    userToModify.value.userType = 2
  }else{
    userToModify.value.userType = 3
  }

  axios.post('user/modifyUser',userToModify.value).then(resp=>{
    if(resp){
      if(resp.data.success){
        userToModify.value = ref({})
        disEditable.value = true
        checkboxVisible.value = false
        ElMessage({
          message: '修改成功：' + resp.data.message,
        })
      }else{
        ElMessage({
          message: '修改失败：' + resp.data.message,
          type: 'error',
        })
      }
    }
  })
}

/*
* 加载*/
onMounted(() => {
  axios.post('/user/queryAllUser').then(resp => {
    console.log(resp)
    if (resp) {
      if (resp.data.success) {

        let users = resp.data.content;

        for (var i = 0; i < users.length; i++) {
          let userType = ''

          if (users[i].userType == 1) {
            userType = '儿童'
          } else if(users[i].userType == 2){
            userType = '志愿者'
          } else{
            userType = '管理者'
          }
          let temp = {
            id:users[i].id,
            name:users[i].name,
            phone: users[i].phone,
            password: users[i].password,
            userType: userType,
            score:users[i].score,
            volunteerId:users[i].volunteerId
          }
          tableData.value.push(temp)
        }
      }
    }
  })

})

/*
* 解决ERROR ResizeObserver loop completed with undelivered notifications.
问题的*/
const debounce = (fn, delay) => {

  let timer = null;

  return function () {

    let context = this;

    let args = arguments;

    clearTimeout(timer);

    timer = setTimeout(function () {

      fn.apply(context, args);

    }, delay);

  }

}

/*
*解决ERROR ResizeObserver loop completed with undelivered notifications.
问题的 */
const _ResizeObserver = window.ResizeObserver;

window.ResizeObserver = class ResizeObserver extends _ResizeObserver {

  constructor(callback) {

    callback = debounce(callback, 16);

    super(callback);

  }

}


</script>

<style scoped>

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

.box-card {
  width: 480px;
}

</style>