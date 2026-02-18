<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import api from '@/api/axios'

const email = ref('')
const password = ref('')
const router = useRouter()
const authStore = useAuthStore()

async function handleLogin() {
  try {
    const res = await api.post('/api/auth/login', {
      email: email.value,
      password: password.value
    })
    authStore.login(res.data)
    alert(res.data.name + '님 환영합니다!')
    router.push('/')
  } catch (e) {
    alert('로그인 실패')
  }
}
</script>

<template>
  <div class="auth-wrapper">
    <div class="card auth-card">
      <h1>로그인</h1>
      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label>이메일</label>
          <input v-model="email" type="email" required />
        </div>
        <div class="form-group">
          <label>비밀번호</label>
          <input v-model="password" type="password" required />
        </div>
        <button type="submit">로그인하기</button>
      </form>
    </div>
  </div>
</template>

<style scoped>
.auth-wrapper {
  display: flex;
  justify-content: center;
  padding-top: 40px;
}

.auth-card {
  width: 100%;
  max-width: 400px;
}

.auth-card button {
  width: 100%;
  margin-top: 8px;
  padding: 12px;
}
</style>
