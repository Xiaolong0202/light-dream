<template>
    <div style="">

        <el-card style="width: 90%;margin-top: 5%;margin-left: 5%">
            <span>负责的儿童</span>
            <el-button style="margin-left: 20px" @click="dialogVisible=true">绑定儿童</el-button>
            <!--        {{children}}-->
            <el-table style="margin-top: 20px" :data="children"  border>
                <el-table-column fixed prop="id" label="儿童ID" width="200"/>
                <el-table-column  prop="name" label="姓名" width="180"/>
                <el-table-column prop="phone" label="电话号码" width="250"/>
                <el-table-column prop="score" label="积分数" width="300"/>
                <el-table-column fixed="right" label="Operations" width="200">
                    <template #default="scope">
                        <el-button link type="primary" size="small" @click="disAbleChild(scope.row.id,scope.row.name)"
                        >解除绑定
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style=" display: grid;place-items: center;">
                <el-pagination
                    :page-sizes="[10, 20, 30, 40]"
                    :page-size="pagination.pageSize"
                    :pager-count="pagination.currentPage"
                    layout="prev, pager, next"
                    :total="pagination.total"
                />
            </div>
        </el-card>

        <!--对话框-->
        <el-dialog
            v-model="dialogVisible"
            title="输入儿童的电话号码"
            width="30%"
            :before-close="handleClose"
        >
            <el-input v-model="bindChildPhone" placeholder="电话号码" />
            <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="bindChild">
          确认
        </el-button>
      </span>
            </template>
        </el-dialog>
    </div>
</template>
<script setup>
import {onMounted, reactive, ref} from "vue";
import store from "@/store";
import axios from "axios";
import {ElMessage, ElMessageBox} from "element-plus";

const user = ref(store.state.loginUser)//当前登录的用户

const children = ref([])//被管理的孩子

const dialogVisible = ref(false)

const pagination = reactive({
    pageSize: 10,
    currentPage: 1,
    total: 0
})

const bindChildPhone = ref('')

//窗体关闭的回调函数
const handleClose = ()=>{
    bindChildPhone.value = null
}

const bindChild = ()=>{
    ElMessageBox.confirm(`确认要与电话号码为${bindChildPhone.value}的孩子绑定吗？`)
        .then(() => {
            axios.put(`/user/bindChild?volunteerId=${user.value.id}&childPhone=${bindChildPhone.value}`)
                .then(resp => {
                    const data = resp.data
                    if (data) {
                        if (data.success) {
                            ElMessage({
                                message: resp.data.message,
                                type: 'success',
                            })
                            dialogVisible.value = false
                            getChildrenData()

                        } else {
                            ElMessage({
                                message: resp.data.message,
                                type: 'error',
                            })
                        }
                    }
                })
        })
        .catch(() => {
            // catch error
        })

}


const getChildrenData = () => {
    axios.get(`/user/queryChildren/${user.value.id}?pageSize=${pagination.pageSize}&currentPage=${pagination.currentPage}`)
        .then(resp => {
            const data = resp.data
            console.log(data)
            if (data) {
                if (data.success) {
                    pagination.total = data.content.total
                    children.value = data.content.list
                } else {
                    ElMessage({
                        message: '获取儿童信息失败：' + resp.data.message,
                        type: 'error',
                    })
                }
            }
        })
}

const disAbleChild = (childId, childName) => {
    ElMessageBox.confirm(`确认要与孩子${childName}解绑吗？`)
        .then(() => {
            axios.put(`/user/disBindChild?childId=${childId}`)
                .then(resp => {
                    const data = resp.data
                    if (data) {
                        if (data.success) {
                            ElMessage({
                                message: resp.data.message,
                                type: 'success',
                            })
                            getChildrenData()

                        } else {
                            ElMessage({
                                message: resp.data.message,
                                type: 'error',
                            })
                        }
                    }
                })
        })
        .catch(() => {
            // catch error
        })
}

onMounted(() => {
    console.log("hello")
    getChildrenData()
})
</script>

<style scoped>

</style>