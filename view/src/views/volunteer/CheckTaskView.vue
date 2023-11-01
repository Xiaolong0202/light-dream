<template>
  <div>
    <el-card class="box-card" style="width: 100%">
      <div class="clearfix">
        <span>任务列表</span>
        <el-button style="float: right; padding: 3px 0" type="text">新建任务</el-button>
      </div>
      <div>
        <el-table :data="tableData" style="width: 100%" max-height="250">
          <el-table-column fixed prop="date" label="截至日期" width="150">
          </el-table-column>
          <el-table-column prop="title" label="任务标题" width="120">
          </el-table-column>
          <el-table-column prop="id" label="任务id" width="120">
          </el-table-column>
          <el-table-column prop="type" label="任务类型" width="120">
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="200">
            <el-button type="success">发布</el-button>
            <el-button type="success">删除</el-button>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import store from "@/store";
const user = ref(store.state.loginUser)
const tableData = ref([])
onMounted(() => {
  console.log("hello")
  let temp = {
    date: '2016-05-07',
    title: '古诗默写',
    id: '001',
    type: '问答题',
  }
  tableData.value.push(temp)
})
</script>


<style>
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