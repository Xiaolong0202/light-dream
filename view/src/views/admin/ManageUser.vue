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
              <el-button @click="modify(scope.row)" type="text" size="small">查看/修改</el-button>
              <el-button @click="bind(scope.row)" type="text" size="small">绑定</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
  </div>


  <el-dialog
      v-model="checkboxVisible1"
      title="用户信息"
      width="30%"
      @close="closeDialog">

    <el-form :model="userToModify" :disabled="disEditable" label-width="80px" style="margin-right: 20px" >
      <el-form-item label="手机号">
        <el-input v-model="userToModify.phone"/>
      </el-form-item>
      <el-form-item label="姓名">
        <el-input v-model="userToModify.name"/>
      </el-form-item>
      <el-form-item label="密码">
        <el-input show-password v-model="userToModify.password"/>
      </el-form-item>
    </el-form>

    <div style="text-align: center;margin-top: 20px">
      <el-button v-if="disEditable" type="primary" @click="modifyConsent">修改</el-button>
      <el-button v-if="!disEditable" type="primary" @click="modifySend">保存</el-button>
      <el-button type="primary" @click="checkboxVisible1=false">取消</el-button>
    </div>
  </el-dialog>

  <el-dialog
      v-model="checkboxVisible2"
      title="绑定儿童"
      width="50%"
      @close="closeDialog">

    在这里显示儿童列表

    <div style="text-align: center;margin-top: 20px">
      <el-button type="primary" @click="bindSend">确定</el-button>
      <el-button type="primary" @click="checkboxVisible2=false">取消</el-button>
    </div>
  </el-dialog>


</template>

<script setup>
import {onMounted, ref} from "vue";
import {ElMessage} from "element-plus";
import axios from "axios";

const tableData = ref([])
const userToModify = ref({})
const userToBind = ref({})
const checkboxVisible1 = ref(false)
const checkboxVisible2 = ref(false)
const disEditable = ref(true)



/*对应修改填写框*/
const modify = (userModify) => {
  checkboxVisible1.value = true
  userToModify.value = userModify
}
const modifyConsent=()=>{
  disEditable.value = false
}
const modifySend=()=>{
  console.log(userToModify.value)
  axios.post('user/modifyUser',userToModify).then(resp=>{
    if(resp){
      if(resp.data.success){
        userToModify.value = ref({})
        disEditable.value = true
        checkboxVisible1.value = false
        ElMessage({
          message: '修改success：' + resp.data.message,
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


/*对应志愿者绑定儿童框*/
const bind = (userBind) => {
  checkboxVisible2.value = true
  userToBind.value = userBind
}

onMounted(() => {
  axios.post('/user/queryAllUser').then(resp => {
    console.log(resp)
    if (resp) {
      if (resp.data.success) {
        console.log(resp.data.content)
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
            score:users[i].score
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