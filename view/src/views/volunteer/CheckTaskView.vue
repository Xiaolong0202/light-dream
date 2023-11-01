<template>
  <div>
    <el-card class="box-card" style="width: 100%">
      <div class="clearfix">
        <span>任务列表</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="create">新建任务</el-button>
        <el-dialog
            v-model="dialogVisible"
            title="任务设计"
            width="50%"
            @close="closeDialog">
          <el-form ref="form" :model="form" label-width="80px">
            <el-form-item label="任务标题">
              <el-input v-model="task.name"></el-input>
            </el-form-item>
            <el-form-item label="任务内容">
              <el-input type="textarea" v-model="task.description"></el-input>
            </el-form-item>
            <el-form-item label="截止日期">
              <div class="block">
                <el-date-picker
                    v-model="task.deadline"
                    type="date"
                    placeholder="选择日期">
                </el-date-picker>
              </div>
            </el-form-item>
            <el-form-item label="分值">
              <el-input-number v-model="task.totalScore" @change="handleChange" :min="1" :max="100" label="描述文字"></el-input-number>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onsubmit">保存</el-button>
              <el-button @click="dialogVisible = false">取消</el-button>
            </el-form-item>
          </el-form>
        </el-dialog>
      </div>
      <div>
        <el-table :data="tableData" style="width: 100%" max-height="250">
          <el-table-column fixed prop="date" label="截至日期" width="150">
          </el-table-column>
          <el-table-column prop="title" label="任务标题" width="120">
          </el-table-column>
          <el-table-column prop="mark" label="任务总分" width="120">
          </el-table-column>
          <el-table-column prop="type" label="任务类型" width="120">
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="200">
            <el-button type="success" @click="release">发布</el-button>
            <el-button type="success" @click="remove">删除</el-button>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";
import store from "@/store";
const user = ref(store.state.loginUser)
const tableData = ref([])
const dialogVisible = ref(false)
const task = ref({})

const create=()=>{
  console.log("create")
  // eslint-disable-next-line no-undef
  dialogVisible.value = true
}
const release=()=>{

}
const remove=()=>{

}
const onsubmit=()=>{
  task.value.volunteerId = user.value.id
  task.value.type = 0
  console.log(task.value)
  axios.post('/task/addTask', task.value).then(resp => {
    console.log(resp)
    if (resp) {
      if (resp.data.success) {
        ElMessage({
          message: '注册成功',
          type: 'success',
        })
        dialogVisible.value = false
      } else {
        ElMessage({
          message: '注册失败：' + resp.data.message,
          type: 'error',
        })
      }
    }
  })

}


onMounted(() => {
  console.log("hello")
  let temp = {
    date: '2016-05-07',
    title: '古诗默写',
    mark: '50',
    type: '问答题',
  }
  tableData.value.push(temp)

  let temp1 = {
    date: '2016-05-02',
    title: '名言默写',
    mark: '50',
    type: '问答题',
  }

  tableData.value.push(temp1)
})
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