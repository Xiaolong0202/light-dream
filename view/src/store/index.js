import {createStore} from 'vuex'

function getUserFromStorage(){
    let item = window.sessionStorage.getItem('loginUser');
    return item && typeof(item) !== 'undefined' && item !== 'undefined'? JSON.parse(item):{}
}

export default createStore({
    state: {
        loginUser: getUserFromStorage()
    },
    getters: {},
    mutations: {
        setLoginUser(state, user) {
            window.sessionStorage.setItem('loginUser',JSON.stringify(user))
            state.loginUser = user
        }
    },
    actions: {},
    modules: {}
})
