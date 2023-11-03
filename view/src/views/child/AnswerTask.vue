<template>
    <div style="padding-left: 5%">

        <el-card style="width: 100%;margin-top: 5%;margin-left: 5%">
            <span v-if="tableStatus === 1">待做的任务列表</span>
            <span v-else-if="tableStatus === 2">已上交的任务列表</span>

            <!--            <el-button style="margin-left: 20px" @click="dialogVisible=true">绑定儿童</el-button>-->
            <el-radio-group style="margin-left: 20px" v-model="tableStatus" class="ml-4">
                <el-radio :label="1" size="large">待完成</el-radio>
                <el-radio :label="2" size="large">已完成</el-radio>
            </el-radio-group>
            <!--        {{children}}-->
            <el-table style="margin-top: 20px" :data="tableDataSource" border>
                <el-table-column fixed prop="task.name" label="任务名" width="220"/>
                <el-table-column prop="task.type" label="任务类型" width="150"/>
                <el-table-column prop="task.deadline" label="截止日期" width="150"/>
                <el-table-column prop="task.totalScore" label="任务总分" width="150"/>
                <el-table-column label="得分" width="150">
                    <template #default="scope">
                        <span>
                             <template v-if="scope.row.answerStatus===3">
                                scope.row.score
                            </template>
                            <template v-else>
                                ~
                            </template>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column label="任务状态" width="200">
                    <template #default="scope">
                        <span>
                            <template v-if="scope.row.answerStatus===1">
                                未作答
                            </template>
                             <template v-if="scope.row.answerStatus===2">
                                已作答，未审批
                            </template>
                             <template v-if="scope.row.answerStatus===3">
                                已审批
                            </template>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column fixed="right" label="Operations" width="200">
                    <template #default="scope">
                        <template v-if="scope.row.answerStatus===1">
                            <el-button link type="primary" size="small" @click="openDrawer(scope.row,true)">
                                编辑作答内容
                            </el-button>
                        </template>
                        <template v-else>
                            <el-button link type="primary" size="small" @click="openDrawer(scope.row,false)">
                                查看答题记录
                            </el-button>
                        </template>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>


        <el-drawer v-model="drawer" direction="rtl" :before-close="handleCloseDrawer" :modal="true"
                   :close-on-click-modal="false">
            <template #header>
                <h2>标题：{{ currentAnswer.task.name }}</h2>
            </template>
            <template #default>
                <div>
                    <p>
                        {{ '任务描述：' + currentAnswer.task.description }}
                    </p>
                    <p v-if="currentAnswer.answerStatus > 1" v-html=" '作答内容：<br/>'+currentAnswer.answerContent"
                       style="margin-top: 50px;margin-bottom: 50px">

                    </p>
                    <div v-else style="border: 1px solid #ccc">
                        <Toolbar
                                style="border-bottom: 1px solid #ccc"
                                :editor="editorRef"
                                :defaultConfig="toolbarConfig"
                                mode="default"
                        />
                        <Editor
                                style="height: 500px; overflow-y: hidden;"
                                v-model="currentAnswer.answerContent"
                                :defaultConfig="editorConfig"
                                mode="default"
                                @onCreated="handleCreated"
                        />
                    </div>
                </div>
            </template>
            <template #footer>
                <div style="flex: auto">
                    <el-button @click="handleCloseDrawer">cancel</el-button>
                    <el-button :disabled="!drawEditable" type="primary" @click="saveEdit()">保存草稿</el-button>
                    <el-button :disabled="!drawEditable" type="primary" @click="submitEdit()">提交</el-button>
                </div>
            </template>
        </el-drawer>
    </div>
</template>

<script setup>
import {onBeforeUnmount, onMounted, ref, shallowRef, watch} from "vue";
import store from "@/store";
import axios from "axios";
import {ElMessage, ElMessageBox} from "element-plus";
import '@wangeditor/editor/dist/css/style.css' // 引入 css
import {Editor, Toolbar} from '@wangeditor/editor-for-vue'

const user = ref(store.state.loginUser)

const answers = ref([])

const tableStatus = ref(1)//1表示展示的是待作答的页面，而2表示的是已经作答或者已经审批

const tableDataSource = ref([])
//wang editor配置
// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef()
const toolbarConfig = {}
const editorConfig = {placeholder: '请输入内容...'}

// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
    const editor = editorRef.value
    if (editor == null) return
    editor.destroy()
})

const handleCreated = (editor) => {
    editorRef.value = editor // 记录 editor 实例，重要！
}
//wang editor配置

function getAnswersOfTheChild() {
    axios.get(`/answer/getList/${user.value.id}`)
        .then(resp => {
            if (resp) {
                if (resp.data.success) {
                    answers.value = resp.data.content
                    console.log(answers.value)
                    //模拟变化,触发监听事件
                    updateDataSource()
                } else {
                    ElMessage({
                        message: '查找儿童任务回答列表失败：' + resp.data.message,
                        type: 'error',
                    })
                }
            }
        })
}

const currentAnswer = ref({task: {}})//当前选中
const drawEditable = ref(false)//抽屉中的内容是否可以编辑
const drawer = ref(false)//抽屉的可见性
/**
 * 关闭抽屉的回调
 */
function handleCloseDrawer() {
    // editorRef.value.setHtml('')
    if (editorRef.value){
        editorRef.value.clear()
        console.log(editorRef.value)
    }
    currentAnswer.value = {task: {}}
    drawEditable.value = false
    drawer.value = false
}

function openDrawer(Answer, editAble) {
    drawEditable.value = editAble
    currentAnswer.value = Object.assign(Answer)
    drawer.value = true
}

/**
 * 保存孩子对任务的修改
 */
function saveEdit(){
    ElMessageBox.confirm(`确认要保存你的任务草稿吗？`)
        .then(() => {
            axios.put('/answer/edit',currentAnswer.value)
                .then(resp => {
                    const data = resp.data
                    if (data) {
                        if (data.success) {
                            ElMessage({
                                message: resp.data.message,
                                type: 'success',
                            })
                            getAnswersOfTheChild()
                        } else {
                            ElMessage({
                                message: resp.data.message,
                                type: 'error',
                            })
                        }
                    }
                })
        }).catch(() => {
        // catch error
    })
}

function submitEdit(){
    ElMessageBox.confirm(`确认要提交你的任务吗？`)
        .then(() => {
            axios.put('/answer/child-submit',currentAnswer.value)
                .then(resp => {
                    const data = resp.data
                    if (data) {
                        if (data.success) {
                            ElMessage({
                                message: resp.data.message,
                                type: 'success',
                            })
                            getAnswersOfTheChild()
                            drawer.value = false
                        } else {
                            ElMessage({
                                message: resp.data.message,
                                type: 'error',
                            })
                        }
                    }
                })
        }).catch(() => {
        // catch error
    })
}

watch(() => tableStatus.value, () => {
    updateDataSource()
})
function updateDataSource(){
    if (tableStatus.value === 1) {
        tableDataSource.value = []
        for (let a of answers.value) {
            if (a.answerStatus == 1) {
                tableDataSource.value.push(Object.assign(a))
            }
        }
    } else {
        tableDataSource.value = []
        for (let a of answers.value) {
            if (a.answerStatus != 1) {
                tableDataSource.value.push(Object.assign(a))
            }
        }
    }
}
onMounted(() => {
    getAnswersOfTheChild()
})
</script>

<style scoped>

</style>