import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', () => {
/*    const user = ref(null) // 초기화부분에 토큰없이 진행, 항상 유저부분에 null로 진행해버림 */
    const user = ref(JSON.parse(localStorage.getItem('user'))||null) // null or 토큰 주입 -> user 객체 복원
    // user  정보가 localstorage에 저장되지않아서 새로고침시 null 되버림

    const token = ref(localStorage.getItem('token') || null)

    function login(tokenResponse) {
        token.value = tokenResponse.token
        user.value = {
            userId: tokenResponse.userId,
            name: tokenResponse.name,
            email: tokenResponse.email,
            role: tokenResponse.role
        }
        localStorage.setItem('token', tokenResponse.token)
        localStorage.setItem('user', JSON.stringify(user.value)) 
        // 유저 객체저장(로그인시 받아온user정보)값을 localstorage에 저장하는것

    }

    function logout() {
        user.value = null // 유저 정보 초기화
/*        const user = ref(JSON.parse(localStorage.getItem('user'))||null)
        // user 객체 복원 or 안되면 null 대입
        logout에서는 user객체를 지워버려야하는데 복원해버림
        */
        token.value = null
        localStorage.removeItem('token') // 토큰 지속시간을 0으로해서 자동으로 지워지게
        localStorage.removeItem('user') //  로그아웃시 저장받은정보 값을 localstorage에서 제거하는것
    }

    function setUser(userData) {
        user.value = userData
    }

    return { user, token, login, logout, setUser }
})
