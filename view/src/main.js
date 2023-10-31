import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import axios from "axios";


createApp(App).use(store).use(router).use(ElementPlus).mount('#app')


// 设置请求的基础路径
axios.defaults.baseURL = 'http://127.0.0.1:81'

