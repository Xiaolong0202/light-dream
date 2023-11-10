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
import AdminView from "@/views/AdminView.vue";
import ManageTask from "@/views/admin/ManageTask.vue";
import ManageUser from "@/views/admin/ManageUser.vue";
import ManageVolunteer from "@/views/admin/ManageVolunteer.vue";
import ManageChild from "@/views/admin/ManageChild.vue";
import ManageAnalyze from "@/views/admin/ManageAnalyze.vue";
import ChildrenStatics from "@/views/volunteer/ChildrenStatics.vue";

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
            },{
                path: 'childrenStatics',
                component: ChildrenStatics
            }
        ]
    },{
        path: '/admin',
        component: AdminView,
        children: [
            {
                path: 'manageTask',
                component: ManageTask
            },
            {
                path: 'manageUser',
                component: ManageUser
            },
            {
                path: 'manageVolunteer',
                component: ManageVolunteer
            },
            {
                path: 'manageChild',
                component: ManageChild
            },{
                path: 'manageAnalyze',
                component: ManageAnalyze
            }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
