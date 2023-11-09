<template>
  <div style="padding-left: 5%">
    <el-card class="box-card" style="width: 90%;margin-top: 5%;margin-left: 5%">
      <div class="clearfix">
        <span>任务列表</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="create">新建任务</el-button>
        <el-dialog
            v-model="dialogVisible"
            title="任务设计"
            width="70%"
            style="height: 460px"
            @close="closeDialog">
          <el-row>
            <el-col :span="4" class="lightgreen-box">
              <el-menu
                  default-active="1"
                  class="el-menu-vertical-demo"
                  @select="handleOpen">
                <el-menu-item index="1">
                  <i class="el-icon-setting"></i>
                  <span>简答类</span>
                </el-menu-item>
                <el-menu-item index="2">
                  <i class="el-icon-setting"></i>
                  <span>答卷类</span>
                </el-menu-item>
                <el-menu-item index="3">
                  <i class="el-icon-setting"></i>
                  <span>实践类</span>
                </el-menu-item>
                <el-menu-item index="4">
                  <i class="el-icon-setting"></i>
                  <span>互动类</span>
                </el-menu-item>
              </el-menu>
            </el-col>
            <el-col :span="20" class="orange-box">
              <el-form v-if="formType == 1" ref="form" :model="form" label-width="80px">
                <el-form-item label="任务标题">
                  <el-input v-model="task.name"></el-input>
                </el-form-item>
                <el-form-item label="任务内容">
                  <el-input type="textarea" v-model="task.description"></el-input>
                </el-form-item>
                <el-form-item label="设置时间">
                  <div>
                    <el-date-picker
                        v-model="task.startTime"
                        type="date"
                        placeholder="选择开始日期">
                    </el-date-picker>
                    <el-date-picker
                        v-model="task.endTime"
                        type="date"
                        placeholder="选择结束日期"
                        style="margin-left: 10px"
                    >
                    </el-date-picker>
                  </div>
                </el-form-item>
                <el-form-item label="任务难度">
                <el-radio-group v-model="task.difficulty">
                  <el-radio :label="1">简单</el-radio>
                  <el-radio :label="2">普通</el-radio>
                  <el-radio :label="3">困难</el-radio>
                </el-radio-group>
                </el-form-item>
                <el-form-item label-width="94px" label="任务优先级">
                  <el-select v-model="task.priority" placeholder="请选择优先级">
                    <el-option
                        v-for="item in options1"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="任务分值">
                  <el-input-number v-model="task.totalScore" @change="handleChange" :min="1" :max="100"
                                   label="描述文字"></el-input-number>
                </el-form-item>
                <el-form-item >
                  <el-button style="margin-left: 160px" type="primary" @click="onsubmit1">保存</el-button>
                  <el-button style="margin-left: 160px" @click="dialogVisible = false">取消</el-button>
                </el-form-item>
              </el-form>

              <el-form v-if="formType == 2" ref="form" :model="form" label-width="80px">
                <el-form-item label="任务标题">
                  <el-input></el-input>
                </el-form-item>
                <el-form-item label="任务内容">
                  <el-input
                      type="textarea"
                      :rows="2"
                      placeholder="请输入内容"
                      v-model="input1">
                  </el-input>
                </el-form-item>
                <el-form-item label="设置时间">
                  <div>
                    <el-date-picker
                        v-model="value1"
                        type="date"
                        placeholder="选择开始日期">
                    </el-date-picker>
                    <el-date-picker
                        v-model="value1"
                        type="date"
                        placeholder="选择结束日期"
                        style="margin-left: 10px"
                    >
                    </el-date-picker>
                  </div>
                </el-form-item>
                <el-form-item label="设置分数">
                  <el-input-number v-model="input2" @change="handled" :min="1" :max="10" label="任务分数"></el-input-number>
                  <el-button type="primary" style="margin-left: 80px" @click="designQuestion">设计问题</el-button>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="handled">保存</el-button>
                  <el-button @click="dialogVisible = false">取消</el-button>
                </el-form-item>
              </el-form>

              <el-form v-if="formType == 3" ref="form" :model="form" label-width="80px">
                <el-form-item label="任务标题">
                  <el-input></el-input>
                </el-form-item>
                <el-form-item label="任务内容">
                  <el-input
                      type="textarea"
                      :rows="2"
                      placeholder="请输入内容"
                      v-model="input3">
                  </el-input>
                </el-form-item>
                <el-form-item label="设置时间">
                  <div>
                    <el-date-picker
                        v-model="value1"
                        type="date"
                        placeholder="选择开始日期">
                    </el-date-picker>
                    <el-date-picker
                        v-model="value1"
                        type="date"
                        placeholder="选择结束日期"
                        style="margin-left: 10px"
                    >
                    </el-date-picker>
                  </div>
                </el-form-item>
                <el-form-item label="设置预算">
                  <el-input-number v-model="input4" @change="handled" :min="1" :max="10" label="任务分数"></el-input-number>
                </el-form-item>
                <el-form-item label="提交形式">
                  <el-radio-group v-model="input5">
                    <el-radio :label="3">文字</el-radio>
                    <el-radio :label="6">图片</el-radio>
                    <el-radio :label="9">文件</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="handled">保存</el-button>
                  <el-button @click="dialogVisible = false">取消</el-button>
                </el-form-item>
              </el-form>

              <el-form v-if="formType == 4" ref="form" :model="form" label-width="80px">
                <el-form-item label="任务标题">
                  <el-input></el-input>
                </el-form-item>
                <el-form-item label="任务内容">
                  <el-input
                      type="textarea"
                      :rows="2"
                      placeholder="请输入内容"
                      v-model="handled">
                  </el-input>
                </el-form-item>
                <el-form-item label="设置时间">
                  <div>
                    <el-date-picker
                        v-model="value1"
                        type="date"
                        placeholder="选择开始日期">
                    </el-date-picker>
                    <el-date-picker
                        v-model="value1"
                        type="date"
                        placeholder="选择结束日期"
                        style="margin-left: 10px"
                    >
                    </el-date-picker>
                  </div>
                </el-form-item>
                <el-form-item label="互动对象">

                  <el-dropdown split-button type="primary" @click="handleClick">
                    志愿者
                    <template v-slot:dropdown>
                      <el-dropdown-menu>
                        <el-dropdown-item>黄金糕</el-dropdown-item>
                        <el-dropdown-item>狮子头</el-dropdown-item>
                        <el-dropdown-item>螺蛳粉</el-dropdown-item>
                        <el-dropdown-item>双皮奶</el-dropdown-item>
                        <el-dropdown-item>蚵仔煎</el-dropdown-item>
                      </el-dropdown-menu>
                    </template>
                  </el-dropdown>

                  <el-dropdown split-button type="primary" @click="handleClick">
                    儿童
                      <template v-slot:dropdown>
                        <el-dropdown-menu>
                        <el-dropdown-item>黄金糕</el-dropdown-item>
                        <el-dropdown-item>狮子头</el-dropdown-item>
                        <el-dropdown-item>螺蛳粉</el-dropdown-item>
                        <el-dropdown-item>双皮奶</el-dropdown-item>
                        <el-dropdown-item>蚵仔煎</el-dropdown-item>
                        </el-dropdown-menu>
                      </template>
                  </el-dropdown>

                </el-form-item>
                <el-form-item label="提交形式">
                  <el-radio-group v-model="handled">
                    <el-radio :label="3">文字</el-radio>
                    <el-radio :label="6">图片</el-radio>
                    <el-radio :label="9">文件</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="handled">保存</el-button>
                  <el-button @click="dialogVisible = false">取消</el-button>
                </el-form-item>
              </el-form>
            </el-col>
          </el-row>
        </el-dialog>
      </div>
      <div style="width: 99%">
        <el-table :data="tableData" style="margin-top: 20px" border>
          <el-table-column prop="name" label="任务标题" width="150">
          </el-table-column>
          <el-table-column prop="type" label="任务类型" width="150">
          </el-table-column>
          <el-table-column prop="difficulty" label="任务难度" width="150">
          </el-table-column>
          <el-table-column prop="priority" label="任务优先级" width="150">
          </el-table-column>
          <el-table-column prop="totalScore" label="任务总分" width="150">
          </el-table-column>
          <el-table-column prop="startTime" label="开始时间" width="130">
          </el-table-column>
          <el-table-column prop="endTime" label="结束时间" width="130">
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="164">
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
        ref="multipleTable"
        :data="childrenList"
        @selection-change="selectionLineChangeHandle"
        style="margin-top: 20px" border>
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column prop="name" label="名字" width="234">
      </el-table-column>
      <el-table-column prop="password" label="所属志愿者" width="234">
      </el-table-column>
      <el-table-column prop="score" label="分数" width="234">
      </el-table-column>
    </el-table>
    <div style="text-align: center;margin-top: 20px">
      <el-button type="primary" @click="taskSend">确定</el-button>
      <el-button type="primary" @click="checkboxVisible=false">取消</el-button>
    </div>
  </el-dialog>


  <el-dialog
      v-model="designVisible"
      title="设计问题"
      width="80%"
      @close="closeDialog"
  >


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
const designVisible = ref(false)
const task = ref({})
const taskToRelease = ref([])
const childrenList = ref([])
const pickChildrenList = ref([])
const formType = ref(1)
const pagination = reactive({
  pageSize: 10,
  currentPage: 1,
  total: 0
})
const options1 = ref([{
  value: '高',
  label: '高'
}, {
  value: '中',
  label: '中'
}, {
  value: '低',
  label: '低'
}])

function handleOpen(key) {
  formType.value = key
}

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
const selectionLineChangeHandle = (val) => {
  pickChildrenList.value = val
}

/*
* 任务发送*/
const taskSend = () => {
  ElMessageBox.confirm(`确认将任务发送到所选儿童吗？`).then(() => {

    console.log(task.value)//要发送的任务
    console.log(pickChildrenList.value)//所选择的儿童列表

    let users = ""

    for (var i = 0; i < pickChildrenList.value.length; i++) {
      users = users + pickChildrenList.value[i].id + ","
    }

    users = users.substring(0, users.length - 1)
    console.log(users)

    axios.post('/task/releaseTask', {id: taskToRelease.value.id, users: users})
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
    checkboxVisible.value = false
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
const onsubmit1 = () => {
  task.value.adminId = user.value.id
  task.value.type = 1
  task.value.isdelete = 0

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


const designQuestion=()=>{
    designVisible.value = true
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
            taskType = '简答类任务'
          } else if(tasks[i].type == 2){
            taskType = '答卷类任务'
          } else if(tasks[i].type == 3){
            taskType = '实践类任务'
          } else if(tasks[i].type == 4){
            taskType = '互动类任务'
          }

          let temp = {
            id: tasks[i].id,
            name: tasks[i].name,
            totalScore: tasks[i].totalScore,
            type: taskType,
            difficulty:tasks[i].difficulty,
            priority:tasks[i].priority,
            startTime:tasks[i].startTime.substring(0,10),
            endTime:tasks[i].endTime.substring(0,10)
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
.el-dropdown {
  vertical-align: top;
}
.el-dropdown + .el-dropdown {
  margin-left: 15px;
}
.el-icon-arrow-down {
  font-size: 12px;
}

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