// src/stores/account.js
import { ref } from 'vue'
import { defineStore } from 'pinia'
import api from '@/api/axios'

export const useAccountStore = defineStore('account', () => {
    const accounts = ref([])

    // 계좌 목록 조회
    async function fetchAccounts(userId) {
        const res = await api.get('/api/accounts', { params: { userId } })
        accounts.value = res.data
    }

    // 계좌 추가
    async function addAccount(account) {
        await api.post('/api/accounts', account)
        // post 방식으로 해당 url요청하면 응답이 올때까지 기다리는것 index.js에서 주소확인 문제없음

    }

    // 계좌 삭제
    async function deleteAccount(id) {
        await api.delete(`/api/accounts/${id}`)
    }

    return { accounts, fetchAccounts, addAccount, deleteAccount }
})