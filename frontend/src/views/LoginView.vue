<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import api from '@/api/axios'

// 입력값을 담을 변수 생성
const email = ref('')
const password = ref('')

// 페이지 이동할때 쓰는 상수
const router = useRouter()

async function handleLogin() {
  try {
    // const = 상수
    // 응답이 올때까지 url로 요청하고 기다린다
    const res = await api.post('/api/auth/login', {

      email: email.value,
      password: password.value
    })
    // 응답이 정상적으로 오면 출력
    console.log('로그인 유저:', res.data)
    alert(res.data.name + '님 환영합니다!')

    // 메인페이지로 이동 (router.push 가 이동시킨다 url 로)
    router.push('/')
  } catch (e) {
    alert('로그인 실패')
  }
}
</script>

<template>
  <h1>로그인</h1>
  <form @submit.prevent="handleLogin">
    <!-- email, password 입력 + 버튼 -->
    <div>
    <label>이메일</label>
    <input v-model="email" type="email" required />
    <!--    입력하면  email 변수에 동기화 -->
    </div>
    <div>
      <label>비밀번호</label>
      <input v-model="password" type="password" required />
      <!--    입력하면  password 변수에 동기화 -->
    </div>
    <button type="submit">로그인하기</button>
  </form>
</template>

<style scoped>

</style>