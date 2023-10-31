<template>
    <div>
        志愿者
        电话:{{phone}}
    </div>
</template>

<script setup>
import {ref} from "vue";
import {useRoute} from "vue-router";

const route = useRoute()

const phone = ref(route.query.phone)
</script>

<style scoped>

</style>