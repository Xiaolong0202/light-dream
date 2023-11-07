<template>
    <div>
        <el-card style="width: 90%;margin-top: 5%;margin-left: 5%;min-height: 80vh;min-width: 80vw">
            <el-page-header @back="goBack">
                <template #content>
                    <span class="text-large font-600 mr-3"> 儿童任务分析 </span>
                </template>
            </el-page-header>
            <br>
            <div ref="scoreNum" :style="{ width: '900px', height: '300px' }"></div>
            <div ref="taskNum" :style="{ width: '900px', height: '300px' }"></div>
        </el-card>
    </div>
</template>

<script setup>
import {onMounted, ref} from "vue";
import store from "@/store";
import {useRouter} from "vue-router";
import axios from "axios";
import {ElMessage} from "element-plus";

import * as echarts from 'echarts/core';
import {BarChart} from 'echarts/charts';
import {
    TitleComponent,
    ToolboxComponent,
    TooltipComponent,
    GridComponent,
    LegendComponent
} from 'echarts/components';
import { LineChart } from 'echarts/charts';
import { UniversalTransition } from 'echarts/features';
import { CanvasRenderer } from 'echarts/renderers';


const scoreNum = ref()
const taskNum = ref()

function initCharts1() {
    echarts.use([GridComponent, BarChart, CanvasRenderer,  TitleComponent, ToolboxComponent, TooltipComponent, LegendComponent, LineChart, UniversalTransition]);

    let myChart = echarts.init(scoreNum.value);
    myChart.showLoading()

    let option = {
        title: {
            text: '积分统计'
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            data: []
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                data: [],
                type: 'bar'
            }
        ]
    };
    // 从chidren获取score
    for (let child of children.value) {
        option.xAxis.data.push(child.name)
        option.series[0].data.push(child.score)
    }
    
    option && myChart.setOption(option);
    myChart.hideLoading()
}

function initCharts2() {
    echarts.use([GridComponent, BarChart, CanvasRenderer]);

    let myChart = echarts.init(taskNum.value);

    let option = {
        title: {
            text: '一周内任务完成量的统计'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['Email', 'Union Ads', 'Video Ads', 'Direct', 'Search Engine']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
            type: 'value'
        },
        series: [
        ]
    }

    // 从chidren获取score
    for (let child of children.value) {
        option.legend.data.push(child.name)
        let d = []
        for (let i = 0; i < 7; i++) {
            d.push(getRandomNumber(0,8))
        }
        option.series.push( {
            name: child.name,
            type: 'line',
            stack: 'Total',
            data: d
        })
    }
    option && myChart.setOption(option);

}

///以上是echarts 的内容

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
                    //加载图表数据
                    initCharts1()
                    initCharts2()
                } else {
                    ElMessage({
                        message: '获取儿童信息失败：' + resp.data.message,
                        type: 'error',
                    })
                }
            }
        })
}


function getRandomNumber(min, max) {
    // 计算范围内的随机数
    let random = Math.random() * (max - min + 1) + min;
    // 向下取整得到整数
    let randomNumber = Math.floor(random);
    // 返回随机数
    return randomNumber;
}

onMounted(() => {
    getChildrenData()
})


</script>

<style scoped>

</style>