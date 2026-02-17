<script setup>
import { ref } from 'vue'
import api from '@/api/axios'

// 1. 폼 데이터를 ref로 선언
const name = ref('')
const email = ref('')
const password = ref('')

// 2. 가입 함수


async function handleSignup() {
  // 비동기 함수로 응답올때까지 기다림
  try {
    await api.post('/api/auth/signup', {
      // 백엔드로 요청하면 응답올때까지 기다림
      name: name.value,
      email: email.value,
      password: password.value
    })
    alert('회원가입 성공!')
    // 문제없이 응답받았을경우
  } catch (e) {
    alert('회원가입 실패')
    // 하나라도 에러가나서 응답에문제가 생겼을 경우
  }
}
</script>

<template>
<h1> 회원 가입 페이지~ </h1>
  <form @submit.prevent="handleSignup">
<!--형식은 @리스너가 새로고침 액션감지하면 새로고침을 방어하고 handleSignup을 실행-->
    <div>
      <label>이름</label>
      <input v-model="name" type="text" required />
<!--    입력하면  name변수에 동기화 -->
    </div>
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
    <button type="submit">가입</button>
<!--   폼 안에 데이터들을 모아서 백엔드에 제출하는 버튼 -->
  </form>
</template>

<style scoped>

</style>