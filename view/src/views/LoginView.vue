<template>
    <img class="bgone" src="@/img/1.jpg"/>
    <img class="pic" src="@/img/a.png"/>

    <div class="table">
        <div class="wel">登录界面</div>
        <div class="wel1">login interface</div>
        <div class="user">
            <div id="yonghu" style=""><img src="@/img/yhm.png"/></div>
            <input type="text" name="" placeholder="手机号" v-model="user.phone"/>
        </div>
        <div class="password">
            <div id="yonghu"><img src="@/img/mm.png"/></div>
            <input type="password" name="密码" placeholder="输入密码" v-model="user.password"/>
        </div>
        <input class="btn" type="button" name="登录" value="登录" @click="login"/>
        <input class="regbtn" type="button" name="注册" value="注册" @click="register"/>
    </div>
    <el-dialog
            v-model="dialogVisible"
            title="注册信息填写"
            width="50%"
            @close="closeDialog"
    >
        <el-form :model="user" label-width="120px">
            <el-form-item label="手机号">
                <el-input v-model="user.phone"/>
            </el-form-item>
            <el-form-item label="姓名">
                <el-input v-model="user.name"/>
            </el-form-item>
            <el-form-item label="密码">
                <el-input show-password v-model="user.password"/>
            </el-form-item>
            <el-form-item label="身份">
                <el-select v-model="user.userType" placeholder="请选择你的身份">
                    <el-option label="儿童" value="1"/>
                    <el-option label="志愿者" value="2"/>
                </el-select>
            </el-form-item>
        </el-form>
        <template #footer>
            <el-divider/>
            <span class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="registerToBackEnd">
          确认注册
        </el-button>
      </span>
        </template>
    </el-dialog>
</template>

<script setup>
import {ref} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";

const router = useRouter()
const user = ref({})
const dialogVisible = ref(false)
const login = () => {
    // console.log(user)
    axios.post('/user/login', user.value).then(resp => {
        console.log(resp)
        if (resp) {
            if (resp.data.success) {
                ElMessage({
                    message: '登录成功',
                    type: 'success',
                })
                user.value.userType = resp.data.content
                if (user.value.userType === 1) {
                    //为儿童
                    router.push({
                        path: '/child',
                        query: {
                            phone: user.value.phone
                        }
                    })
                } else if (user.value.userType === 2) {
                    //为儿童
                    router.push({
                        path: '/volunteer',
                        query: {
                            phone: user.value.phone
                        }
                    })
                }
            } else {
                ElMessage({
                    message: '登录失败：' + resp.data.message,
                    type: 'error',
                })
            }
        }
    })

}

const register = () => {
    dialogVisible.value = true
    user.value = {}
}
const closeDialog = () => {
    user.value = {}
}
const registerToBackEnd = () => {
    axios.post('/user/register', user.value).then(resp => {
        console.log(resp)
        if (resp) {
            if (resp.data.success) {
                ElMessage({
                    message: '注册成功',
                    type: 'success',
                })
                dialogVisible.value = false
            } else {
                ElMessage({
                    message: '注册失败：' + resp.data.message,
                    type: 'error',
                })
            }
        }
    })
}
</script>

<style scoped>
* {
    font-family: "微软雅黑";
    font-size: 16px;
    border: 0;
    padding: 0;
    margin: 0;
    color: #666;
    box-sizing: border-box;
    -moz-box-sizing: border-box;
    -webkit-box-sizing: border-box;
}

.bgone {
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    right: 0;
    left: 0;
    bottom: 0;
    margin: auto;
}

.pic {
    width: 492px;
    height: auto;
    position: absolute;
    top: 173px;
    right: 453px;
    left: 0;
    bottom: 0;
    margin: auto;
    z-index: 1;
}

.table {
    background-color: #FFFFFF;
    width: 960px;
    height: 465px;
    position: absolute;
    top: 20%;
    right: 0;
    left: 0;
    bottom: 0;
    margin: auto;
}

.password {
    position: absolute;
    top: 10.8rem;
    right: 4rem;
    display: flex;
}

.btn {
    position: absolute;
    top: 16.5rem;
    right: 3.9rem;
    border: none;
    color: #fff;
    width: 373px;
    text-align: center;
    background-color: #1592ef;
    text-indent: 0rem;
    border-radius: 10px;
    text-shadow: 2px 2px 1px rgba(0, 0, 0, 0.2);
    box-shadow: 2px 2px 1px rgba(0, 0, 0, 0.2);
    font-size: 20px;
    height: 50px;
}

.regbtn {
    position: absolute;
    top: 21rem;
    right: 3.9rem;
    border: none;
    color: #111;
    width: 373px;
    text-align: center;

    text-indent: 0rem;
    border-radius: 10px;
    text-shadow: 2px 2px 1px rgba(0, 0, 0, 0.2);
    box-shadow: 2px 2px 1px rgba(0, 0, 0, 0.2);
    font-size: 20px;
    height: 50px;
}

.wel {
    width: 960px;
    height: 35px;
    color: #fff;
    font-size: 30px;
    position: absolute;
    top: -7rem;
    text-align: center;


}

.wel1 {
    width: 960px;
    height: 35px;
    color: #fff;
    font-size: 12.38px;
    position: absolute;
    top: -4rem;
    text-align: center;

}

input {
    height: 55px;
    width: 373px;
    text-indent: 55px;
    outline: none;
    background: #eef6fd;
    border-bottom: 2px #d6e7fa solid;
}

.password input {
    border: 0;
}

.user {
    position: absolute;
    top: 5.8rem;
    right: 4rem;
    display: flex;
}

#yonghu img {
    height: 30px;
    position: absolute;
    left: 10px;
    top: 13px;
}
</style>