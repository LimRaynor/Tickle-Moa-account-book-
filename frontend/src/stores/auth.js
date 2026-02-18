import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', () => {
    const user = ref(null)
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
    }

    function logout() {
        user.value = null
        token.value = null
        localStorage.removeItem('token')
    }

    function setUser(userData) {
        user.value = userData
    }

    return { user, token, login, logout, setUser }
})
