<template>
    <div>
        孩子
        电话:{{ user }}
    </div>
</template>

<script setup>
import {onMounted, ref} from "vue";
import {useRoute} from "vue-router";
import axios from "axios";
import {ElMessage} from "element-plus";

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

</style>