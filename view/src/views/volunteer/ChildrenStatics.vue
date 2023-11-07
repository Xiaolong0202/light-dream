<template>
    <div>
        <el-card style="width: 90%;margin-top: 5%;margin-left: 5%;min-height: 50vh;min-width: 50vw">
        <el-page-header @back="goBack">
            <template #content>
                <span class="text-large font-600 mr-3"> 儿童任务分析 </span>
            </template>
        </el-page-header>
        </el-card>
    </div>
</template>

<script setup>
import {onMounted, ref} from "vue";
import store from "@/store";
import {useRouter} from "vue-router";
import axios from "axios";
import {ElMessage} from "element-plus";

const router = useRouter()
const children = ref([])//被管理的孩子
const user = ref(store.state.loginUser)//当前登录的用户

function goBack() {
    router.push({
        path: '/volunteer/children',
        query: {
            phone: user.value.phone
        }
    })
}
const getChildrenData = () => {
    axios.get(`/user/queryChildren/${user.value.id}?pageSize=${10000}&currentPage=${1}`)
        .then(resp => {
            const data = resp.data
            console.log(data)
            if (data) {
                if (data.success) {
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

onMounted(()=>{
    getChildrenData()
})
</script>

<style scoped>

</style>