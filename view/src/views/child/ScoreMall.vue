<template>
    <div>
        积分商城
        {{user}}
    </div>
</template>

<script setup>
import {ref} from "vue";
import store from "@/store";

const user = ref(store.state.loginUser)
</script>

<style scoped>

</style>