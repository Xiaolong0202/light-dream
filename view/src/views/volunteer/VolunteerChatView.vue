<template>
    <div>
        志愿者与儿童聊天
        登录信息：
        {{user}}
    </div>
</template>
<script setup>
import {onMounted, ref} from "vue";
import store from "@/store";

const user = ref(store.state.loginUser)

onMounted(()=>{

})
</script>

<style scoped>

</style>