<template>
  <div style="padding-left: 5%">

    <el-card style="width: 100%;margin-top: 5%;margin-left: 5%">
      <span>回答列表</span>
      <el-radio-group style="margin-left: 20px" v-model="tableStatus" class="ml-4">
        <el-radio :label="2" size="large">待审批</el-radio>
        <el-radio :label="3" size="large">已审批</el-radio>
      </el-radio-group>
      <!--        {{children}}-->
      <el-table style="margin-top: 20px" :data="tableDataSource" border>
        <el-table-column fixed prop="writer.name" label="作答者" width="220"/>
        <el-table-column prop="task.name" label="任务标题" width="150"/>
        <el-table-column prop="task.totalScore" label="任务总分" width="150"/>
        <el-table-column label="任务状态" width="200">
          <template #default="scope">
                        <span>
                             <template v-if="scope.row.answerStatus===2">
                                未审批
                            </template>
                             <template v-if="scope.row.answerStatus===3">
                                已审批
                            </template>
                        </span>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="Operations" width="200">
          <template #default="scope">
            <template v-if="scope.row.answerStatus===2">
              <el-button link type="primary" size="small" @click="openDrawer(scope.row,false)">
                审批回答
              </el-button>
            </template>
            <template v-else>
              <span>不可查看</span>
            </template>
          </template>
        </el-table-column>
      </el-table>
    </el-card>


    <el-drawer v-model="drawer" direction="rtl" :before-close="handleCloseDrawer" :modal="true"
               :close-on-click-modal="false">
      <template #header>
        <div>
          <h2>标题：{{ currentAnswer.task.name }}</h2>
        </div>
      </template>
      <template #default>
        <div>
          <h3>题干：</h3>
          <p>
            {{currentAnswer.task.description}}
          </p>
        </div>

        <div>
          <hr>
          <p>
            {{ '答题者：' + currentAnswer.writer.name}}
          </p>
          <p>
            {{'总分： '+ currentAnswer.task.totalScore}}
          </p>
          <hr>
          <p v-html=" '具体作答：<br/>'+currentAnswer.answerContent"
             style="margin-top: 10px;margin-bottom: 15px">
          </p>
        </div>
      </template>
      <template #footer>
        <div style="flex: auto">
          <el-input
              type="textarea"
              :autosize="{ minRows: 2, maxRows: 4}"
              placeholder="评语"
              v-model="currentComment"
              style="margin-bottom: 20px">
          </el-input>
          <el-label style="margin-right: 10px">得分</el-label>
          <el-input-number v-model="currentScore" controls-position="right" :min="1"
                           :max="currentAnswer.task.totalScore" style="margin-right: 80px" @change="currentScoreChange"></el-input-number>
          <el-button @click="handleCloseDrawer">取消</el-button>
          <el-button type="primary" @click="submitEdit()" style="margin-left: 10px">提交</el-button>
        </div>
      </template>
    </el-drawer>
  </div>
</template>

<script setup>
import {onMounted, ref,watch} from "vue";
import store from "@/store";
import axios from "axios";
import {ElMessage,ElMessageBox} from "element-plus";
import '@wangeditor/editor/dist/css/style.css' // 引入 css
//import {Editor, Toolbar} from '@wangeditor/editor-for-vue'

const user = ref(store.state.loginUser)

const answerList = ref([])

const tableStatus = ref(2)//1表示展示的是待作答的页面，而2表示的是已经作答或者已经审批
const tableDataSource = ref([])

function getAnswersByVolunteerId() {
  axios.post('/answer/queryAnswerListByVolunteerId/',{volunteerId:user.value.id})
      .then(resp => {
        if (resp) {
          if (resp.data.success) {
            answerList.value = resp.data.content
            completeAnswerList()
            updateDataSource()
          } else {
            ElMessage({
              message: '查找志愿者审批回答列表失败：' + resp.data.message,
              type: 'error',
            })
          }
        }
      })
}



const currentAnswer = ref({task:{}})//当前选中
const drawEditable = ref(false)//抽屉中的内容是否可以编辑
const drawer = ref(false)//抽屉的可见性
const currentScore = ref(0)//当前分数
const currentComment = ref('')


function openDrawer(Answer, editAble) {
  drawEditable.value = editAble
  currentAnswer.value = Answer
  console.log(currentAnswer.value)
  if (!currentAnswer.value.answerContent){
    currentAnswer.value.answerContent = ''
  }
  drawer.value = true
}
function handleCloseDrawer() {
  drawer.value = false
  currentScore.value = 0
  currentComment.value = ''
  drawEditable.value = false
}



const currentScoreChange=(score)=>{
  currentScore.value = score
}

function submitEdit(){
  ElMessageBox.confirm(`确认要提交你的打分吗？`)
      .then(() => {
        axios.post('user/addScoreChild',{id:currentAnswer.value.writer.id,score:currentScore.value}).then(resp=>{
          const data = resp.data
          if(data.success){
            ElMessage({
              message:'分数添加成功',
              type:'success'
            })
          }else{
            ElMessage({
              message:'分数添加失败',
              type:'error'
            })
          }
        })
        axios.post('answer/evaluateAnswer',{id:currentAnswer.value.id,score:currentScore.value,comments:currentComment.value})
            .then(resp => {
              const data = resp.data
              if (data) {
                if (data.success) {
                  ElMessage({
                    message: resp.data.message,
                    type: '审批提交成功',
                  })
                } else {
                  ElMessage({
                    message: resp.data.message,
                    type: '审批提交失败',
                  })
                }
              }
            })
        handleCloseDrawer()
        location.reload()
      }).catch(() => {
    // catch error
  })
}

function completeAnswerList(){
  for(let answer of answerList.value){
    axios.post('task/getTaskByAnswer',answer).then(resp=>{
      if(resp){
        answer.task = resp.data.content[0]
      }else{
        ElMessage({
          message:'获取任务失败',
          type: 'error'
        })
      }
    })

    axios.post('user/getChildByAnswer',answer).then(resp=>{
      if(resp){
        answer.writer = resp.data.content[0]
      }else{
        ElMessage({
          message:'获取用户失败',
          type:'error'
        })
      }
    })
  }
}


watch(() => tableStatus.value, () => {
  updateDataSource()
})

function updateDataSource(){
  if (tableStatus.value === 2) {
    tableDataSource.value = []
    for (let a of answerList.value) {
      if (a.answerStatus === 2) {
        tableDataSource.value.push(Object.assign(a))
      }
    }
  } else {
    tableDataSource.value = []
    for (let a of answerList.value) {
      console.log(a.answerStatus)
      if (a.answerStatus === 3) {
        tableDataSource.value.push(Object.assign(a))
      }
    }
  }
}

onMounted(() => {
  getAnswersByVolunteerId()
})
</script>

<style scoped>

</style>