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
                  <span>答题类</span>
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
    <div>
      <button type="primary" @click="onAddQuestion(1)">添加单选</button>
      <button type="primary" @click="onAddQuestion(2)">添加多选</button>
      <button type="primary" @click="onAddQuestion(3)">添加填空</button>
      <button type="primary" @click="onAddQuestion(4)">添加矩阵</button>
      <button type="primary" @click="onAddQuestion(5)">添加量表</button>
    </div>
    <div id="problem">
    </div>
  </el-dialog>

</template>

<script setup>
import {onMounted, ref, reactive} from "vue";
import axios from "axios";
import {ElMessage, ElMessageBox} from "element-plus";
import store from "@/store";
//import $ from 'jquery'


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

// ---------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------

// let questionnaireTitle = '问卷标题'
// let questionnaireDescription = '问卷说明'
// const problem = []
//
// /**
//  * 添加问题
//  *
//  * @param {*} type 1：单选，2：多选，3：填空，4：矩阵，5：量表
//  */
// const onAddQuestion = (type) => {
//   let ele
//   switch (type) {
//     case 1:
//       ele = handleAddSingleChoice()
//       break;
//     case 2:
//       ele = handleAddMultipleChoice()
//       break;
//     case 3:
//       ele = handleAddFillBlanks()
//       break;
//     case 4:
//       ele = handleAddMatrix()
//       break;
//     case 5:
//       ele = handleAddGauge()
//       break;
//     default:
//       break;
//   }
//   $('#problem').append(ele)
//   problem.push({ problemName: '', mustAnswer: true, option: [{}] })
//
//   $(".question").hover(() => {
//     let problemIndex = $('.question:hover').attr('data-problemIndex')
//     let ele = `
//       <div class="operation">
//       <div class="button" onclick="handleMoveUp(${problemIndex})">上移</div>
//       <div class="button" onclick="handleMoveDown(${problemIndex})">下移</div>
//         <div class="button" onclick="handleEdit(${problemIndex})">编辑</div>
//         <div class="button" onclick="handleDelete(${problemIndex})">删除</div>
//       </div>
//     `
//     $('.question:hover').append(ele)
//     $(".question:hover").css('border', '1px solid #fdb553')
//   }, () => {
//     $('.question > .operation').remove()
//     $(".question").css('border', '1px solid #ffffff')
//   })
// }
//
// const onInput = (problemIndex, optionIndex, key) => {
//   if (optionIndex || optionIndex === 0)
//     problem[problemIndex].option[optionIndex][key] = $(`#question${problemIndex} #optionItem${optionIndex} #${key}`)[0].value
//   else
//     problem[problemIndex][key] = $(`#question${problemIndex} #${key}`)[0].value
// }
//
// const onMustAnswerClick = (problemIndex) => {
//   problem[problemIndex].mustAnswer = !problem[problemIndex].mustAnswer
//   if (problem[problemIndex].mustAnswer) $(`#question${problemIndex} #mustAnswer`).text('必答题')
//   else $(`#question${problemIndex} #mustAnswer`).text('非必答题')
// }
//
// const cancelEdit = (problemIndex) => {
//   $(`#question${problemIndex} .bottom`).css('display', 'none')
//   $(`#question${problemIndex} .bottom2`).css('display', 'block')
// }
//
// const handleMoveUp = (problemIndex) => {
//   if (problemIndex === 0) return
//   $(`#question${problemIndex - 1}`).before($(`#question${problemIndex}`))
//   let i = problem[problemIndex]
//   problem[problemIndex] = problem[problemIndex - 1]
//   problem[problemIndex - 1] = i
//   moveCommon()
// }
//
// const handleMoveDown = (problemIndex) => {
//   if (problemIndex === problem.length - 1) return
//   $(`#question${problemIndex + 1}`).after($(`#question${problemIndex}`))
//   let i = problem[problemIndex]
//   problem[problemIndex] = problem[problemIndex + 1]
//   problem[problemIndex + 1] = i
//   moveCommon()
// }
//
// const moveCommon = () => {
//   $('.question').map((index, item) => {
//     item.setAttribute('id', `question${index}`)
//     item.setAttribute('data-problemIndex', index)
//     let type = +$(`#question${index}`).attr('data-type')
//     let value;
//     value = $(`#question${index} #problemName`).attr('oninput').replace(/\(\d+,/g, `(${index},`)
//     $(`#question${index} #problemName`).attr('oninput', value)
//     $(`#question${index} #mustAnswer`).attr('onclick', `onMustAnswerClick(${index})`)
//     $(`#question${index} #cancelEdit`).attr('onclick', `cancelEdit(${index})`)
//     switch (type) {
//       case 1:
//         $(`#question${index} #chooseTerm`).map(((chooseTermIndex, chooseTermItem) => {
//           chooseTermItem.oninput = onInput.bind(this, index, chooseTermIndex, 'chooseTerm')
//         }))
//         $(`#question${index} .option-del`).map(((delIndex, delItem) => {
//           delItem.oninput = onInput.bind(this, index, delIndex, 'chooseTerm')
//         }))
//         $(`#question${index} .btn-add-option`).attr('onclick', `singleChoiceAddOption(${index})`)
//         $(`#question${index} #editFinish`).attr('onclick', `singleChoiceEditFinish(${index})`)
//         break;
//       case 2:
//         $(`#question${index} #chooseTerm`).map(((chooseTermIndex, chooseTermItem) => {
//           chooseTermItem.oninput = onInput.bind(this, index, chooseTermIndex, 'chooseTerm')
//         }))
//         $(`#question${index} .option-del`).map(((delIndex, delItem) => {
//           delItem.oninput = onInput.bind(this, index, delIndex, 'chooseTerm')
//         }))
//         $(`#question${index} .btn-add-option`).attr('onclick', `multipleChoiceAddOption(${index})`)
//         $(`#question${index} #editFinish`).attr('onclick', `multipleChoiceEditFinish(${index})`)
//         break;
//       case 3:
//         $(`#question${index} #editFinish`).attr('onclick', `fillBlanksEditFinish(${index})`)
//         break;
//       case 4:
//         $(`#question${index} #chooseTerm`).map(((chooseTermIndex, chooseTermItem) => {
//           chooseTermItem.oninput = onInput.bind(this, index, chooseTermIndex, 'chooseTerm')
//         }))
//         $(`#question${index} .option-del`).map(((delIndex, delItem) => {
//           delItem.oninput = onInput.bind(this, index, delIndex, 'chooseTerm')
//         }))
//         value = $(`#question${index} #leftTitle`).attr('oninput').replace(/\(\d+,/g, `(${index},`)
//         $(`#question${index} #leftTitle`).attr('oninput', value)
//         $(`#question${index} .btn-add-option`).attr('onclick', `matrixAddOption(${index})`)
//         $(`#question${index} #editFinish`).attr('onclick', `matrixEditFinish(${index})`)
//         break;
//       case 5:
//         $(`#question${index} #chooseTerm`).map(((chooseTermIndex, chooseTermItem) => {
//           chooseTermItem.oninput = onInput.bind(this, index, chooseTermIndex, 'chooseTerm')
//         }))
//         $(`#question${index} #fraction`).map(((fractionIndex, fractionItem) => {
//           fractionItem.oninput = onInput.bind(this, index, fractionIndex, 'chooseTerm')
//         }))
//         $(`#question${index} .option-del`).map(((delIndex, delItem) => {
//           delItem.oninput = onInput.bind(this, index, delIndex, 'chooseTerm')
//         }))
//         $(`#question${index} .btn-add-option`).attr('onclick', `gaugeAddOption(${index})`)
//         $(`#question${index} #editFinish`).attr('onclick', `gaugeEditFinish(${index})`)
//         break;
//       default:
//         break;
//     }
//   })
// }
//
// const handleEdit = (problemIndex) => {
//   $(`#question${problemIndex} .bottom`).css('display', 'block')
//   $(`#question${problemIndex} .bottom2`).css('display', 'none')
// }
//
// const handleDelete = (problemIndex) => {
//   $(`#question${problemIndex}`).remove()
//   problem.splice(problemIndex, 1)
// }
//
// const handleAddSingleChoice = () => {
//   let ele = `
//     <div class="question" id="question${problem.length}" data-type="1" data-problemIndex="${problem.length}">
//       <div class="top">
//         <span class="question-title" id="questionTitle">1.请编辑问题？</span>
//         <span class="must-answer" id="mustAnswer" onclick="onMustAnswerClick(${problem.length})">必答题</span>
//       </div>
//       <div class="bottom">
//         <textarea class="form-control textarea" id="problemName" placeholder="单选题目" rows="4" oninput="onInput(${problem.length}, ${undefined}, 'problemName')"></textarea>
//         <div class="option" id="option">
//           <div class="option-item" id="optionItem0">
//             <input type="text" class="form-control" id="chooseTerm" placeholder="选项【单选】" oninput="onInput(${problem.length}, 0, 'chooseTerm')" />
//             <span class="option-del" onclick="singleChoiceDelOption(${problem.length}, 0)">删除</span>
//           </div>
//         </div>
//         <div>
//           <button type="button" class="btn btn-link btn-add-option" onclick="singleChoiceAddOption(${problem.length})">添加选项</button>
//         </div>
//         <div class="btn-group">
//           <button type="button" id="cancelEdit" class="btn btn-default" onclick="cancelEdit(${problem.length})">取消编辑</button>
//           <button type="button" id="editFinish" class="btn btn-default" onclick="singleChoiceEditFinish(${problem.length})">完成编辑</button>
//         </div>
//       </div>
//       <div class="bottom2" style="display: none;">
//
//       </div>
//     </div>
//   `
//   return ele
// }
//
// const singleChoiceAddOption = (problemIndex) => {
//   $(`#question${problemIndex} #option`).append(`
//     <div class="option-item" id="optionItem${problem[problemIndex].option.length}">
//       <input type="text" class="form-control" id="chooseTerm" placeholder="选项【单选】" oninput="onInput(${problemIndex}, ${problem[problemIndex].option.length}, 'chooseTerm')" />
//       <span class="option-del" onclick="singleChoiceDelOption(${problemIndex}, ${problem[problemIndex].option.length})">删除</span>
//     </div>
//   `)
//   problem[problemIndex].option.push({})
// }
//
// const singleChoiceDelOption = (problemIndex, optionIndex) => {
//   $(`#question${problemIndex} .option-item`)[optionIndex].remove()
//   problem[problemIndex].option.splice(optionIndex, 1)
//   $(`#question${problemIndex} .option-del`).map((item, index) => {
//     index.onclick = singleChoiceDelOption.bind(this, problemIndex, item)
//   })
// }
//
// const singleChoiceEditFinish = (problemIndex) => {
//   $(`#question${problemIndex} .bottom`).css('display', 'none')
//   $(`#question${problemIndex} .bottom2`).css('display', 'inline')
//   $(`#question${problemIndex} #questionTitle`).text(`${problemIndex + 1}.${problem[problemIndex].problemName}`)
//   $(`#question${problemIndex} .bottom2`).html('')
//   problem[problemIndex].option.map(item => {
//     $(`#question${problemIndex} .bottom2`).append(`
//       <div style="display: flex; align-items: center;">
//         <label class="radio-inline">
//           <input type="radio">${item.chooseTerm ? item.chooseTerm : ''}
//         </label>
//       </div>
//     `)
//   })
// }
//
// const handleAddMultipleChoice = () => {
//   let ele = `
//     <div class="question" id="question${problem.length}" data-type="2" data-problemIndex="${problem.length}">
//       <div class="top">
//         <span class="question-title" id="questionTitle">1.请编辑问题？</span>
//         <span class="must-answer" id="mustAnswer" onclick="onMustAnswerClick(${problem.length})">必答题</span>
//       </div>
//       <div class="bottom">
//         <textarea class="form-control textarea" id="problemName" placeholder="多选题目" rows="4" oninput="onInput(${problem.length}, ${undefined}, 'problemName')"></textarea>
//         <div class="option" id="option">
//           <div class="option-item" id="optionItem0">
//             <input type="text" class="form-control" id="chooseTerm" placeholder="选项【多选】" oninput="onInput(${problem.length}, 0, 'chooseTerm')" />
//             <span class="option-del" onclick="multipleChoiceDelOption(${problem.length}, 0)">删除</span>
//           </div>
//         </div>
//         <div>
//           <button type="button" class="btn btn-link btn-add-option" onClick="multipleChoiceAddOption(${problem.length})">添加选项</button>
//         </div>
//         <div class="btn-group">
//           <button type="button" id="cancelEdit" class="btn btn-default" onclick="cancelEdit(${problem.length})">取消编辑</button>
//           <button type="button" id="editFinish" class="btn btn-default" onClick="multipleChoiceEditFinish(${problem.length})">完成编辑</button>
//         </div>
//       </div>
//       <div class="bottom2" style="display: none;">
//
//       </div>
//     </div>
//   `
//   return ele
// }
//
// const multipleChoiceAddOption = (problemIndex) => {
//   $(`#question${problemIndex} #option`).append(`
//     <div class="option-item" id="optionItem${problem[problemIndex].option.length}">
//       <input type="text" class="form-control" id="chooseTerm" placeholder="选项【多选】" oninput="onInput(${problemIndex}, ${problem[problemIndex].option.length}, 'chooseTerm')" />
//       <span class="option-del" onclick="multipleChoiceDelOption(${problemIndex}, ${problem[problemIndex].option.length})">删除</span>
//     </div>
//   `)
//   problem[problemIndex].option.push({})
// }
//
// const multipleChoiceDelOption = (problemIndex, optionIndex) => {
//   $(`#question${problemIndex} .option-item`)[optionIndex].remove()
//   problem[problemIndex].option.splice(optionIndex, 1)
//   $(`#question${problemIndex} .option-del`).map((item, index) => {
//     index.onclick = multipleChoiceDelOption.bind(this, problemIndex, item)
//   })
// }
//
// const multipleChoiceEditFinish = (problemIndex) => {
//   $(`#question${problemIndex} .bottom`).css('display', 'none')
//   $(`#question${problemIndex} .bottom2`).css('display', 'inline')
//   $(`#question${problemIndex} #questionTitle`).text(`${problemIndex + 1}.${problem[problemIndex].problemName}`)
//   $(`#question${problemIndex} .bottom2`).html('')
//   problem[problemIndex].option.map(item => {
//     $(`#question${problemIndex} .bottom2`).append(`
//       <div style="display: flex; align-items: center;">
//         <label class="checkbox-inline">
//           <input type="checkbox">${item.chooseTerm ? item.chooseTerm : ''}
//         </label>
//       </div>
//     `)
//   })
// }
//
// const handleAddFillBlanks = () => {
//   let ele = `
//     <div class="question" id="question${problem.length}" data-type="3" data-problemIndex="${problem.length}">
//       <div class="top">
//         <span class="question-title" id="questionTitle">1.请编辑问题？</span>
//         <span class="must-answer" id="mustAnswer" onclick="onMustAnswerClick(${problem.length})">必答题</span>
//       </div>
//       <div class="bottom">
//         <textarea class="form-control textarea" id="problemName" placeholder="请输入题目" rows="4" oninput="onInput(${problem.length}, ${undefined}, 'problemName')"></textarea>
//         <div class="btn-group">
//           <button type="button" id="cancelEdit" class="btn btn-default" onclick="cancelEdit(${problem.length})">取消编辑</button>
//           <button type="button" id="editFinish" class="btn btn-default" onClick="fillBlanksEditFinish(${problem.length})">完成编辑</button>
//         </div>
//       </div>
//       <div class="bottom2" style="display: none;">
//
//       </div>
//     </div>
//   `
//   return ele
// }
//
// const fillBlanksEditFinish = (problemIndex) => {
//   $(`#question${problemIndex} .bottom`).css('display', 'none')
//   $(`#question${problemIndex} .bottom2`).css('display', 'inline')
//   $(`#question${problemIndex} #questionTitle`).text(`${problemIndex + 1}.${problem[problemIndex].problemName}`)
//   $(`#question${problemIndex} .bottom2`).html(`
//     <div style="border: 1px solid #CCCCCC; width: 50%; height: 70px;"></div>
//   `)
// }
//
// const handleAddMatrix = () => {
//   let ele = `
//     <div class="question" id="question${problem.length}" data-type="4" data-problemIndex="${problem.length}">
//       <div class="top">
//         <span class="question-title" id="questionTitle">1.请编辑问题？</span>
//         <span class="must-answer" id="mustAnswer" onclick="onMustAnswerClick(${problem.length})">必答题</span>
//       </div>
//       <div class="bottom">
//         <textarea class="form-control textarea" id="problemName" placeholder="请编辑问题！" rows="4" oninput="onInput(${problem.length}, ${undefined}, 'problemName')"></textarea>
//         <div style="margin-bottom: 10px;">左标题</div>
//         <textarea class="form-control textarea" id="leftTitle" placeholder="例子：CCTV1,CCTV2,CCTV3" rows="4" oninput="onInput(${problem.length}, ${undefined}, 'leftTitle')"></textarea>
//         <div class="option" id="option">
//           <div class="option-item" id="optionItem0">
//             <input type="text" class="form-control" id="chooseTerm" placeholder="选项" oninput="onInput(${problem.length}, 0, 'chooseTerm')" />
//             <span class="option-del" onclick="matrixDelOption(${problem.length}, 0)">删除</span>
//           </div>
//         </div>
//         <div>
//           <button type="button" class="btn btn-link btn-add-option" onClick="matrixAddOption(${problem.length})">添加选项</button>
//         </div>
//         <div class="btn-group">
//           <button type="button" id="cancelEdit" class="btn btn-default" onclick="cancelEdit(${problem.length})">取消编辑</button>
//           <button type="button" id="editFinish" class="btn btn-default" onClick="matrixEditFinish(${problem.length})">完成编辑</button>
//         </div>
//       </div>
//       <div class="bottom2" style="display: none; padding-left: 80px;"></div>
//     </div>
//   `
//   return ele
// }
//
// const matrixAddOption = (problemIndex) => {
//   $(`#question${problemIndex} #option`).append(`
//     <div class="option-item" id="optionItem${problem[problemIndex].option.length}">
//       <input type="text" class="form-control" id="chooseTerm" placeholder="选项" oninput="onInput(${problemIndex}, ${problem[problemIndex].option.length}, 'chooseTerm')" />
//       <span class="option-del" onclick="matrixDelOption(${problemIndex}, ${problem[problemIndex].option.length})">删除</span>
//     </div>
//   `)
//   problem[problemIndex].option.push({})
// }
//
// const matrixDelOption = (problemIndex, optionIndex) => {
//   $(`#question${problemIndex} .option-item`)[optionIndex].remove()
//   problem[problemIndex].option.splice(optionIndex, 1)
//   $(`#question${problemIndex} .option-del`).map((item, index) => {
//     index.onclick = matrixDelOption.bind(this, problemIndex, item)
//   })
// }
//
// const matrixEditFinish = (problemIndex) => {
//   $(`#question${problemIndex} .bottom`).css('display', 'none')
//   $(`#question${problemIndex} .bottom2`).css('display', 'inline')
//   $(`#question${problemIndex} #questionTitle`).text(`${problemIndex + 1}.${problem[problemIndex].problemName}`)
//   $(`#question${problemIndex} .bottom2`).html('')
//   let trs = problem[problemIndex].leftTitle ? problem[problemIndex].leftTitle.split(',') : []
//   $(`#question${problemIndex} .bottom2`).append(`
//     <table class="table">
//       <thead>
//         <tr>
//           <th></th>
//         </tr>
//       </thead>
//       <tbody>
//
//       </tbody>
//     </table>
//   `)
//   trs.map((item, index) => {
//     $(`#question${problemIndex} .bottom2 tbody`).append(`
//       <tr class="tr${index}">
//         <td>${item}</td>
//       </tr>
//     `)
//     problem[problemIndex].option.map(() => {
//       $(`#question${problemIndex} .bottom2 tbody .tr${index}`).append(`
//         <td>
//           <input type="radio" name="radio${index}">
//         </td>
//       `)
//     })
//   })
//   problem[problemIndex].option.map(item => {
//     $(`#question${problemIndex} .bottom2 thead tr`).append(`
//       <th>${item.chooseTerm}</th>
//     `)
//   })
//
// }
//
// const handleAddGauge = () => {
//   let ele = `
//     <div class="question" id="question${problem.length}" data-type="5" data-problemIndex="${problem.length}">
//       <div class="top">
//         <span class="question-title" id="questionTitle">1.请编辑问题？</span>
//         <span class="must-answer" id="mustAnswer" onclick="onMustAnswerClick(${problem.length})">必答题</span>
//       </div>
//       <div class="bottom">
//         <textarea class="form-control textarea" id="problemName" placeholder="请编辑问题！" rows="4" oninput="onInput(${problem.length}, ${undefined}, 'problemName')"></textarea>
//         <div class="option" id="option">
//           <div style="display: flex; margin-bottom: 10px;">
//             <div style="width: calc(50% + 90px)">选项文字</div>
//             <div style="width: 140px;">分数</div>
//             <div>操作</div>
//           </div>
//           <div class="option-item" id="optionItem0">
//             <input type="text" class="form-control" id="chooseTerm" oninput="onInput(${problem.length}, 0, 'chooseTerm')" />
//             <input type="text" class="form-control" id="fraction" oninput="onInput(${problem.length}, 0, 'fraction')" style="width: 50px;" />
//             <span class="option-del" onclick="gaugeDelOption(${problem.length}, 0)">删除</span>
//           </div>
//         </div>
//         <div>
//           <button type="button" class="btn btn-link btn-add-option" onClick="gaugeAddOption(${problem.length})">添加选项</button>
//         </div>
//         <div class="btn-group">
//           <button type="button" id="cancelEdit" class="btn btn-default" onclick="cancelEdit(${problem.length})">取消编辑</button>
//           <button type="button" id="editFinish" class="btn btn-default" onClick="gaugeEditFinish(${problem.length})">完成编辑</button>
//         </div>
//       </div>
//       <div class="bottom2" style="display: none; align-items: center; justify-content: space-between;"></div>
//     </div>
//   `
//   return ele
// }
//
// const gaugeAddOption = (problemIndex) => {
//   $(`#question${problemIndex} #option`).append(`
//     <div class="option-item" id="optionItem${problem[problemIndex].option.length}">
//       <input type="text" class="form-control" id="chooseTerm" oninput="onInput(${problemIndex}, ${problem[problemIndex].option.length}, 'chooseTerm')" />
//       <input type="text" class="form-control" id="fraction" oninput="onInput(${problemIndex}, ${problem[problemIndex].option.length}, 'fraction')" style="width: 50px;" />
//       <span class="option-del" onclick="gaugeDelOption(${problemIndex}, ${problem[problemIndex].option.length})">删除</span>
//     </div>
//   `)
//   problem[problemIndex].option.push({})
// }
//
// const gaugeDelOption = (problemIndex, optionIndex) => {
//   $(`#question${problemIndex} .option-item`)[optionIndex].remove()
//   problem[problemIndex].option.splice(optionIndex, 1)
//   $(`#question${problemIndex} .option-del`).map((item, index) => {
//     index.onclick = gaugeDelOption.bind(this, problemIndex, item)
//   })
// }
//
// const gaugeEditFinish = (problemIndex) => {
//   $(`#question${problemIndex} .bottom`).css('display', 'none')
//   $(`#question${problemIndex} .bottom2`).css('display', 'flex')
//   $(`#question${problemIndex} #questionTitle`).text(`${problemIndex + 1}.${problem[problemIndex].problemName}`)
//   $(`#question${problemIndex} .bottom2`).html('')
//   $(`#question${problemIndex} .bottom2`).append(`
//     <div>${problem[problemIndex].option[0].chooseTerm}</div>
//   `)
//   problem[problemIndex].option.map(item => {
//     $(`#question${problemIndex} .bottom2`).append(`
//       <div>
//         <label class="radio-inline">
//           <input type="radio" name="fraction" />${item.fraction}
//         </label>
//       </div>
//     `)
//   })
//   $(`#question${problemIndex} .bottom2`).append(`
//     <div>${problem[problemIndex].option[problem[problemIndex].option.length - 1].chooseTerm}</div>
//   `)
// }
//
// const handleModifyTitle = () => {
//   $('#modifyTitleModal').modal('show')
//   $('#questionnaireTitle').val(questionnaireTitle)
//   $('#questionnaireDescription').val(questionnaireDescription)
// }
//
//
// const handleEditFinish = () => {
// }




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