<template>
    <div>
        <el-row style="min-height: 100vh">
            <el-col :span="2">
                <el-menu
                        style="min-height: 100vh"
                        default-active="2"
                        class="el-menu-vertical-demo"
                >
                    <el-menu-item index="1">
                        <!--                        <el-icon><User /></el-icon>-->
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>管理员</span>
                    </el-menu-item>
                    <el-menu-item index="2">
                        <el-icon>
                            <Files/>
                        </el-icon>
                        <router-link
                                :to="{
                                 path:'/admin/manageTask',
                                 query: {
                                     phone: user.phone
                                }
                            }">
                            任务管理
                        </router-link>
                    </el-menu-item>
                </el-menu>
            </el-col>
            <el-col :span="22">
                <router-view></router-view>
            </el-col>
        </el-row>
    </div>
</template>

<script setup>
import {onMounted, ref} from "vue";
import {useRoute} from "vue-router";
import axios from "axios";
import {ElMessage} from "element-plus";
import store from "@/store";

const route = useRoute()

const phone = ref(route.query.phone)

const user = ref({})


//通过phone查找登录用户详细信息
function getCurrentUser() {
    axios.get('/user/queryByPhone/' + phone.value)
        .then(resp => {
            if (resp) {
                if (resp.data.success) {
                    user.value = resp.data.content
                    store.commit('setLoginUser', user.value)
                } else {
                    ElMessage({
                        message: '获取用户失败：' + resp.data.message,
                        type: 'error',
                    })
                }
            }
        })
}

onMounted(() => {
    getCurrentUser()
})
</script>

<style scoped>
.router-link-active {
    text-decoration: none;
//color: #333;
}

a {
    text-decoration: none;
    color: #333;
}
</style>