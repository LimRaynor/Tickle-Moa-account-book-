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

// 2. 계좌 추가
async function handleAddAccount() {
  await accountStore.addAccount({
    // handleAddAccount 계좌생성세는 요청을 store에 전달하는것
    userId: authStore.user.userId, // 로그인 확인값이 null 이라 에러 (아마 토큰발급문제인듯)
    name: accountName.value,
    balance: 0
  })
  await accountStore.fetchAccounts(authStore.user.userId)
  accountName.value = ''
}

// 3. 계좌 삭제
async function handleDeleteAccount(id) {
  await accountStore.deleteAccount(id)
  await accountStore.fetchAccounts(authStore.user.userId)
}
</script>

<template>
  <div class="card">
    <h1>계좌 관리</h1>
    <form class="add-form" @submit.prevent="handleAddAccount">
      <input v-model="accountName" placeholder="계좌명을 입력하세요" required />
      <button type="submit">추가</button>
    </form>
  </div>

  <div class="card list-card">
    <h2>계좌 목록</h2>
    <ul class="item-list">
      <li v-for="account in accountStore.accounts" :key="account.accountId">
        <div class="item-info">
          <span class="item-name">{{ account.name }}</span>
          <span class="item-amount">{{ account.balance.toLocaleString() }}원</span>
        </div>
        <button class="btn-delete" @click="handleDeleteAccount(account.accountId)">삭제</button>
      </li>
    </ul>
    <p v-if="accountStore.accounts.length === 0" class="empty">등록된 계좌가 없습니다.</p>
  </div>
</template>

<style scoped>
.card + .card {
  margin-top: 20px;
}

.add-form {
  display: flex;
  gap: 12px;
}

.add-form input {
  flex: 1;
}

h2 {
  font-weight: 600;
  font-size: 16px;
  margin-bottom: 16px;
  color: var(--color-dark);
}

.item-list {
  list-style: none;
  padding: 0;
}

.item-list li {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 14px 0;
  border-bottom: 1px solid var(--color-border);
}

.item-list li:last-child {
  border-bottom: none;
}

.item-info {
  display: flex;
  gap: 16px;
  align-items: center;
}

.item-name {
  font-weight: 600;
}

.item-amount {
  color: var(--color-primary);
  font-weight: 600;
}

.empty {
  text-align: center;
  color: var(--color-text-muted);
  padding: 24px 0;
}
</style>
