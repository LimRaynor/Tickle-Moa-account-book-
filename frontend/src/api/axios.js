// src/api/axios.js

import axios from 'axios'

// 1. 인스턴스 생성 — baseURL만 설정
const api = axios.create({
    baseURL: 'http://localhost:8080'
})

// 2. 나중에 JWT 추가할 자리 (지금은 비워둠)
// api.interceptors.request.use(config => {
//   const token = localStorage.getItem('token')
//   if (token) config.headers.Authorization = `Bearer ${token}`
//   return config
// })

// 3. export — 다른 파일에서 import api from '@/api/axios' 로 사용
export default api