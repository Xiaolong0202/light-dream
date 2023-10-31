import { createRouter, createWebHistory } from 'vue-router'
import LoginView from "@/views/LoginView.vue";
import ChildView from "@/views/ChildView.vue";
import VolunteerView from "@/views/VolunteerView.vue";

const routes = [
  {
    path: '/',
    component: LoginView
  },{
    path: '/child',
    component: ChildView
  },{
    path: '/volunteer',
    component: VolunteerView
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
