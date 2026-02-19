<script setup>
import { RouterLink, RouterView, useRouter } from 'vue-router'
import  { useAuthStore } from  './stores/auth.js'


//  객체생성
const router = useRouter()
const authStore = useAuthStore();

const handleLogout = () => {

  try {
    authStore.logout()
    router.push('/')

  }  catch (error){
    console.log("로그아웃 실패")
  }

}
</script>


<template>
  <header class="nav-header">
    <div class="nav-inner">
      <span class="nav-logo">Tickle Moa</span>
      <nav>
        <RouterLink to="/">Home</RouterLink>
        <RouterLink to="/about">About</RouterLink>
        <RouterLink to="/signup">회원가입</RouterLink>
        <RouterLink to="/login">로그인</RouterLink>
        <RouterLink to="/accounts">계좌</RouterLink>
        <RouterLink to="/transactions">거래내역</RouterLink>
      </nav>
      <button class="logout-btn" @click="handleLogout">로그아웃</button>
    </div>
  </header>

  <main class="main-content">
    <RouterView/>
  </main>
</template>

<style scoped>
.nav-header {
  background: var(--color-dark);
  position: sticky;
  top: 0;
  z-index: 100;
}

.nav-inner {
  max-width: 900px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  padding: 0 24px;
  height: 56px;
}

.nav-logo {
  color: var(--color-primary);
  font-weight: 700;
  font-size: 18px;
  margin-right: 32px;
  white-space: nowrap;
}

nav {
  display: flex;
  gap: 4px;
}

nav a {
  color: rgba(255, 255, 255, 0.7);
  padding: 8px 14px;
  border-radius: var(--radius);
  font-size: 14px;
  transition: all 0.3s;
}

nav a:hover {
  color: var(--color-white);
  background: rgba(255, 255, 255, 0.1);
}

nav a.router-link-exact-active {
  color: var(--color-white);
  background: var(--color-primary);
}

.logout {
  margin-left: auto;
  color: rgba(255, 255, 255, 0.7);
  background: none;
  border: none;
  padding: 8px 14px;
  border-radius: var(--radius);
  font-size: 14px;
  cursor: pointer;
  transition: all 0.3s;
  white-space: nowrap;
}

.logout:hover {
  color: var(--color-white);
  background: rgba(255, 255, 255, 0.1);
}

.main-content {
  max-width: 900px;
  margin: 0 auto;
  padding: 32px 24px;
}
</style>
