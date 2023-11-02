<template>
  <div>
      <span>{{answers}}</span>
  </div>
</template>

<script setup>
import {onMounted, ref} from "vue";
  import store from "@/store";
  import axios from "axios";
  import {ElMessage} from "element-plus";

  const user = ref(store.state.loginUser)

  const answers = ref([])

  function getAnswersOfTheChild(){
      axios.get(`/answer/getList/${user.value.id}`)
          .then(resp=>{
              if (resp){
                  if (resp.data.success){
                      answers.value = resp.data.content
                      console.log(answers.value)
                  }else {
                      ElMessage({
                          message: '查找儿童信息失败：' + resp.data.message,
                          type: 'error',
                      })
                  }
              }
          })
  }

  onMounted(()=>{
      console.log(112)
      getAnswersOfTheChild()
  })
</script>

<style scoped>

</style>