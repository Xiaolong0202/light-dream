import { createRouter, createWebHistory } from 'vue-router'
import LoginView from "@/views/LoginView.vue";
import ChildView from "@/views/ChildView.vue";
import VolunteerView from "@/views/VolunteerView.vue";
import MyChildrenView from "@/views/volunteer/MyChildrenView.vue";
import CheckTaskView from "@/views/volunteer/CheckTaskView.vue";

const routes = [
  {
    path: '/',
    component: LoginView
  },{
    path: '/child',
    component: ChildView
  },{
    path: '/volunteer',
    component: VolunteerView,
    children: [
      {
        path: 'children',
        component: MyChildrenView,
      },
      {
        path: 'taskCheck',
        component: CheckTaskView
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
