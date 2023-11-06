<template>
  <div style="padding-left: 5%">
    <el-card class="box-card" style="width: 90%;margin-top: 5%;margin-left: 5%">
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
              <el-input-number v-model="task.totalScore" @change="handleChange" :min="1" :max="100"
                               label="描述文字"></el-input-number>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onsubmit">保存</el-button>
              <el-button @click="dialogVisible = false">取消</el-button>
            </el-form-item>
          </el-form>
        </el-dialog>
      </div>
      <div style="width: 99%">
        <el-table :data="tableData" style="margin-top: 20px" border>
          <el-table-column fixed prop="deadline" label="截至日期" width="300">
          </el-table-column>
          <el-table-column prop="name" label="任务标题" width="200">
          </el-table-column>
          <el-table-column prop="totalScore" label="任务总分" width="200">
          </el-table-column>
          <el-table-column prop="type" label="任务类型" width="200">
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="200">
            <template #default="scope">
              <el-button @click="release(scope.row)" type="text" size="small">发布</el-button>
              <el-button @click="remove(scope.row)" type="text" size="small">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
  </div>

  <el-dialog
      v-model="checkboxVisible"
      title="选定儿童"
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
      <el-table-column fixed prop="id" label="id" width="300">
      </el-table-column>
      <el-table-column prop="name" label="名字" width="200">
      </el-table-column>
      <el-table-column prop="phone" label="任电话务总分" width="200">
      </el-table-column>
    </el-table>
    <div style="text-align: center;margin-top: 20px">
      <el-button type="primary" @click="taskSend">确定</el-button>
      <el-button type="primary" @click="checkboxVisible=false">取消</el-button>
    </div>
  </el-dialog>


</template>

<script setup>
import {onMounted, ref, reactive} from "vue";
import axios from "axios";
import {ElMessage, ElMessageBox} from "element-plus";
import store from "@/store";


const user = ref(store.state.loginUser)
const tableData = ref([])
const dialogVisible = ref(false)
const checkboxVisible = ref(false)
const task = ref({})
const taskToRelease = ref([])
const childrenList = ref([])
const pickChildrenList = ref([])
const pagination = reactive({
  pageSize: 10,
  currentPage: 1,
  total: 0
})

/*弹出任务创建*/
const create = () => {
  console.log("create")
  dialogVisible.value = true
}

/*对应任务弹出多选框*/
const release = (taskRelease) => {
  checkboxVisible.value = true
  taskToRelease.value = taskRelease
}

/*多选选中*/
const selectionLineChangeHandle=(val)=>{
  pickChildrenList.value = val
}

/*
* 任务发送*/
const taskSend=()=>{
  ElMessageBox.confirm(`确认将任务发送到所选儿童吗？`).then(()=>{

    console.log(task.value)//要发送的任务
    console.log(pickChildrenList.value)//所选择的儿童列表

    let users = ""

    for(var i=0; i<pickChildrenList.value.length; i++){
      users = users + pickChildrenList.value[i].id + ","
    }

    users = users.substring(0,users.length-1)
    console.log(users)

    axios.post('/task/releaseTask', {id:taskToRelease.value.id,users:users})
        .then(resp => {
      console.log(resp)
      if (resp) {
        if (resp.data.success) {
          ElMessage({
            message: '发布成功',
            type: 'success',
          })
          dialogVisible.value = false
          location.reload()
        } else {
          ElMessage({
            message: '发布失败：' + resp.data.message,
            type: 'error',
          })
        }
      }
    })
    checkboxVisible.value=false
  })
}

/*
* 任务删除*/
const remove = (taskRemove) => {
  ElMessageBox.confirm(`确认要删除任务${taskRemove.name}吗？`)
      .then(() => {
        if (taskRemove.type == "问答题") {
          taskRemove.type = 1;
        } else {
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
      }).catch(() => {
  })
}

/*
* 任务提交*/
const onsubmit = () => {
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

/*
* 加载*/
onMounted(() => {
  axios.post('/task/queryTaskList', user.value).then(resp => {
    console.log(resp)
    if (resp) {
      if (resp.data.success) {

        let tasks = resp.data.content;

        for (var i = 0; i < tasks.length; i++) {
          let taskType = ''

          if (tasks[i].type == 1) {
            taskType = '问答题'
          } else {
            taskType = '论述题'
          }

          let temp = {
            id: tasks[i].id,
            deadline: tasks[i].deadline.substring(0, 10),
            name: tasks[i].name,
            totalScore: tasks[i].totalScore,
            type: taskType,
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