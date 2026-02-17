<script setup>
import { onMounted, ref } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { useAccountStore } from '@/stores/account'

const accountName = ref('')
const authStore = useAuthStore()
const accountStore = useAccountStore()

// 1. 페이지 열릴 때 계좌 목록 조회
onMounted(() => {
  accountStore.fetchAccounts(authStore.user.userId)
})

// 2. 계좌 추가 — 필요한 ref + 함수
//    name만 입력받고, userId는 authStore에서, balance는 0으로 시작
//    추가 후 목록 다시 조회 (fetchAccounts)

// 3. 계좌 삭제 — id 받아서 delete 후 목록 다시 조회


async function handleAddAccount() {
  await accountStore.addAccount({
    userId: authStore.user.userId,
    name: accountName.value,
    balance: 0
  })
  await accountStore.fetchAccounts(authStore.user.userId)
  accountName.value = ''
}

async function handleDeleteAccount(id) {
  await accountStore.deleteAccount(id)
  await accountStore.fetchAccounts(authStore.user.userId)
}
</script>

<template>
  <h1>계좌 관리</h1>

  <!-- 계좌 추가 폼 -->
  <form @submit.prevent="handleAddAccount">
    <input v-model="accountName" placeholder="계좌명" required />
    <button type="submit">추가</button>
  </form>

  <!-- 계좌 목록 -->
  <ul>
    <li v-for="account in accountStore.accounts" :key="account.accountId">
      {{ account.name }} — {{ account.balance }}원
      <button @click="handleDeleteAccount(account.accountId)">삭제</button>
    </li>
  </ul>
</template>