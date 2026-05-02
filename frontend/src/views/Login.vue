<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-100 px-4">
    <div class="max-w-md w-full bg-white rounded-lg shadow-md p-8">
      <h2 class="text-3xl font-bold text-center text-gray-800 mb-8">Đăng nhập</h2>
      <form @submit.prevent="handleLogin">
        <div class="mb-4">
          <label class="block text-gray-700 text-sm font-bold mb-2">Tên đăng nhập</label>
          <input v-model="username" type="text" class="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" required>
        </div>
        <div class="mb-6">
          <label class="block text-gray-700 text-sm font-bold mb-2">Mật khẩu</label>
          <div class="relative">
            <input v-model="password" :type="showPassword ? 'text' : 'password'" class="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 pr-10" required>
            <button type="button" @click="showPassword = !showPassword" class="absolute inset-y-0 right-0 pr-3 flex items-center text-gray-500 hover:text-gray-700">
              <svg v-if="!showPassword" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5">
                <path stroke-linecap="round" stroke-linejoin="round" d="M2.036 12.322a1.012 1.012 0 010-.644C3.399 8.049 7.21 5 12 5c4.789 0 8.601 3.049 9.964 6.678.07.234.07.468 0 .702-1.363 3.629-5.175 6.678-9.964 6.678-4.79 0-8.601-3.049-9.964-6.678z" />
                <path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
              </svg>
              <svg v-else xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5">
                <path stroke-linecap="round" stroke-linejoin="round" d="M3.98 8.223A10.477 10.477 0 001.934 12C3.226 16.338 7.244 19.5 12 19.5c.993 0 1.953-.138 2.863-.395M6.228 6.228A10.45 10.45 0 0112 4.5c4.756 0 8.773 3.162 10.065 7.498a10.523 10.523 0 01-4.293 5.774M6.228 6.228L3 3m3.228 3.228l3.65 3.65m7.894 7.894L21 21m-3.228-3.228l-3.65-3.65m0 0a3 3 0 10-4.243-4.243m4.242 4.242L9.88 9.88" />
              </svg>
            </button>
          </div>
        </div>
        <button type="submit" class="w-full bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-md transition duration-300">
          Đăng nhập
        </button>
      </form>
      <p class="mt-4 text-center text-gray-600">
        Chưa có tài khoản? <router-link to="/register" class="text-blue-600 hover:underline">Đăng ký ngay</router-link>
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from '../api/axios'

const username = ref('')
const password = ref('')
const showPassword = ref(false)
const router = useRouter()

const handleLogin = async () => {
  try {
    console.log('Sending login request for:', username.value);
    const response = await axios.post('/auth/signin', {
      username: username.value,
      password: password.value
    })
    console.log('Login response:', response.data);
    localStorage.setItem('user', JSON.stringify(response.data))
    
    // Redirect based on role
    const userRole = response.data.role;
    if (userRole === 'ADMIN' || userRole === 'STAFF') {
      router.push('/staff-dashboard')
    } else {
      router.push('/')
    }
  } catch (error) {
    console.error('Login error:', error);
    const errorMsg = error.response?.data?.message || error.response?.data || 'Sai tên đăng nhập hoặc mật khẩu';
    alert('LỖI ĐĂNG NHẬP: ' + errorMsg)
  }
}
</script>
