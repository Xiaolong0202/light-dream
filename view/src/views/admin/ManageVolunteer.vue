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
          <el-table-column prop="account" label="用户账号" width="200">
          </el-table-column>
          <el-table-column prop="password" label="用户密码" width="200">
          </el-table-column>
          <el-table-column prop="userType" label="用户类型" width="200">
          </el-table-column>
          <el-table-column prop="score" label="积分" width="150">
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="223">
            <template #default="scope">
              <el-button @click="bind(scope.row)" type="info" size="small">绑定</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
  </div>

  <el-dialog
      v-model="checkboxVisible"
      title="绑定儿童"
      width="50%"
      @close="closeDialog">
    <el-table
        ref = "multipleTable"
        :data="childrenList"
        @selection-change="selectionLineChangeHandle"
        style="margin-top: 20px" border>
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column prop="name" label="名字" width="234">
      </el-table-column>
      <el-table-column prop="password" label="已绑定志愿者" width="234">
      </el-table-column>
      <el-table-column prop="score" label="分数" width="234">
      </el-table-column>
    </el-table>
    <div style="text-align: center;margin-top: 20px">
      <el-button type="primary" @click="bindSend">确定</el-button>
      <el-button type="primary" @click="checkboxVisible=false">取消</el-button>
    </div>
  </el-dialog>

</template>

<script setup>

import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import {ElMessage, ElMessageBox} from "element-plus";

const tableData = ref([])
const dialogVisible = ref(false)
const userToBind = ref([])
const checkboxVisible = ref(false)
const childrenList = ref([])
const pickChildrenList = ref([])
const pagination = reactive({
  pageSize: 10,
  currentPage: 1,
  total: 0
})

/*对应志愿者绑定儿童框*/
const bind = (userBind) => {
  checkboxVisible.value = true
  userToBind.value = userBind
}

/*多选选中*/
const selectionLineChangeHandle=(val)=>{
  pickChildrenList.value = val
}

/*
* 志愿者绑定儿童*/
const bindSend=()=>{

  ElMessageBox.confirm(`确定要为该志愿者绑定这些儿童吗？`).then(()=>{

    console.log(pickChildrenList.value)

    let users = ""

    for(var i=0; i<pickChildrenList.value.length; i++){
      users = users + pickChildrenList.value[i].phone + ","
    }

    users = users.substring(0,users.length-1)

    axios.post('/user/bindVolunteerAndChild', {id:userToBind.value.id,users:users})
        .then(resp => {

          if (resp) {
            if (resp.data.success) {
              ElMessage({
                message: '绑定成功',
                type: 'success',
              })
              dialogVisible.value = false
              location.reload()
            } else {
              ElMessage({
                message: '绑定失败：' + resp.data.message,
                type: 'error',
              })
            }
          }

        })
    checkboxVisible.value=false

  })

}

/*
* 加载*/
onMounted(() => {
  axios.post('/user/queryUser',{userType:2}).then(resp => {
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
            account: users[i].phone,
            password: users[i].password,
            userType: userType,
            score:users[i].score
          }
          tableData.value.push(temp)
        }
      }
    }
  })

  axios.get(`/user/getAllChildren?pageSize=${pagination.pageSize}&currentPage=${pagination.currentPage}`)
      .then(resp => {
        const data = resp.data
        if (data) {
          if (data.success) {
            pagination.total = data.content.total
            childrenList.value = data.content.list
          } else {
            ElMessage({
              message: '获取儿童信息失败：' + resp.data.message,
              type: 'error',
            })
          }
        }
      })

  console.log(childrenList)

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

<style>

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

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