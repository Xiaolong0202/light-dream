import {createRouter, createWebHistory} from 'vue-router'
import LoginView from "@/views/LoginView.vue";
import ChildView from "@/views/ChildView.vue";
import VolunteerView from "@/views/VolunteerView.vue";
import MyChildrenView from "@/views/volunteer/MyChildrenView.vue";
import CheckTaskView from "@/views/volunteer/CheckTaskView.vue";
import AnwserTask from "@/views/child/AnswerTask.vue";
import ChildChat from "@/views/child/ChildChat.vue";
import ScoreMall from "@/views/child/ScoreMall.vue";
import VolunteerChatView from "@/views/volunteer/VolunteerChatView.vue";

const routes = [
    {
        path: '/',
        component: LoginView
    }, {
        path: '/child',
        component: ChildView,
        children: [{
            path: 'answerTask',
            component: AnwserTask
        }, {
            path: 'chat',
            component: ChildChat
        }, {
            path: 'mall',
            component: ScoreMall
        }]
    }, {
        path: '/volunteer',
        component: VolunteerView,
        children: [
            {
                path: 'children',
                component: MyChildrenView,
            }, {
                path: 'taskCheck',
                component: CheckTaskView
            },{
                path: 'volunteerChat',
                component: VolunteerChatView
            }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
