<template>
  <div style="padding-left: 5%">
    <el-card class="box-card" style="width: 100%;margin-top: 5%;margin-left: 5%">
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
        <div style="width: 99%">
          <el-table :data="tableData"  style="margin-top: 20px" border>
            <el-table-column fixed prop="deadline" label="截至日期" width="220">
            </el-table-column>
            <el-table-column prop="name" label="任务标题" width="180">
            </el-table-column>
            <el-table-column prop="totalScore" label="任务总分" width="500">
            </el-table-column>
            <el-table-column prop="type" label="任务类型" width="300">
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="200">
              <template #default="scope">
                <el-button @click="release()" type="text" size="small">发布</el-button>
                <el-button @click="remove(scope.row)" type="text" size="small">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>


    </el-card>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import axios from "axios";
import {ElMessage, ElMessageBox} from "element-plus";
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
const remove=(taskRemove)=>{
    ElMessageBox.confirm(`确认要删除任务${taskRemove.name}吗？`)
        .then(() => {
            if(taskRemove.type=="问答题"){
                taskRemove.type = 1;
            }else{
                taskRemove.type = 0;
            }
            console.log(taskRemove)
            axios.post('/task/deleteTaskById', taskRemove).then(resp => {
                console.log(resp)
                if (resp) {
                    if (resp.data.success) {
                        ElMessage({
                            message: '删除成功',
                            type: 'success',
                        })
                        dialogVisible.value = false
                        location.reload()
                    } else {
                        ElMessage({
                            message: '删除失败：' + resp.data.message,
                            type: 'error',
                        })
                    }
                }
            })
        }).catch(()=>{})

}
const onsubmit=()=>{
  task.value.volunteerId = user.value.id
  task.value.type = 0
  task.value.isdelete = 0
  console.log(task.value)
  axios.post('/task/addTask', task.value).then(resp => {
    console.log(resp)
    if (resp) {
      if (resp.data.success) {
        ElMessage({
          message: '添加成功',
          type: 'success',
        })
        dialogVisible.value = false
        location.reload()
      } else {
        ElMessage({
          message: '添加失败：' + resp.data.message,
          type: 'error',
        })
      }
    }
  })
}


// 解决ERROR ResizeObserver loop completed with undelivered notifications.

//问题的

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

// 解决ERROR ResizeObserver loop completed with undelivered notifications.

//问题的

const _ResizeObserver = window.ResizeObserver;

window.ResizeObserver = class ResizeObserver extends _ResizeObserver {

  constructor(callback) {

    callback = debounce(callback, 16);

    super(callback);

  }

}





onMounted(() => {
  console.log("hello")

  axios.post('/task/queryTaskList', user.value).then(resp => {
    console.log(resp)
    if (resp) {
      if (resp.data.success) {

        let tasks = resp.data.content;

        for (var i = 0; i < tasks.length; i++) {
          let taskType = ''

          if(tasks[i].type == 1){
            taskType = '问答题'
          }else {
            taskType = '论述题'
          }

          let temp = {
            id:tasks[i].id,
            deadline: tasks[i].deadline.substring(0,10),
            name: tasks[i].name,
            totalScore: tasks[i].totalScore,
            type: taskType,
          }
          tableData.value.push(temp)
        }
      }
    }

  })
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