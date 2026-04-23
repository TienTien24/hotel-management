<template>
  <nav 
    :class="[
      'fixed top-0 left-0 right-0 z-50 transition-all duration-300',
      isScrolled || !isHome ? 'bg-white shadow-md py-4' : 'bg-transparent py-6'
    ]"
  >
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex justify-between items-center">
        <!-- Logo -->
        <div class="flex-shrink-0 flex items-center">
          <router-link to="/" class="flex items-center space-x-2">
            <div class="w-10 h-10 bg-emerald-800 rounded-full flex items-center justify-center shadow-lg">
              <span class="text-white font-bold text-xl">H</span>
            </div>
            <span 
              :class="[
                'text-2xl font-black tracking-tighter uppercase transition-colors duration-300',
                isScrolled || !isHome ? 'text-emerald-900' : 'text-white'
              ]"
            >
              Grand Hotel
            </span>
          </router-link>
        </div>

        <!-- Desktop Menu -->
        <div class="hidden md:flex items-center space-x-8">
          <router-link 
            v-for="item in menuItems" 
            :key="item.path"
            :to="item.path" 
            :class="[
              'font-bold uppercase text-sm tracking-widest transition-all duration-300 hover:text-emerald-500',
              isScrolled || !isHome ? 'text-gray-700' : 'text-white drop-shadow-md'
            ]"
          >
            {{ item.name }}
          </router-link>
        </div>

        <!-- Auth Button -->
        <div class="flex items-center space-x-4">
          <template v-if="!user">
            <router-link 
              to="/login" 
              :class="[
                'px-6 py-2.5 rounded-full font-bold text-sm uppercase tracking-widest transition-all duration-300 shadow-lg',
                isScrolled || !isHome ? 'bg-emerald-800 text-white hover:bg-emerald-900' : 'bg-white text-emerald-900 hover:bg-emerald-50'
              ]"
            >
              Đăng nhập
            </router-link>
          </template>
          <template v-else>
            <div class="flex items-center space-x-4">
              <span 
                :class="[
                  'hidden lg:block text-sm font-medium transition-colors duration-300',
                  isScrolled || !isHome ? 'text-gray-500' : 'text-emerald-50'
                ]"
              >
                Xin chào, <span :class="isScrolled || !isHome ? 'text-emerald-800 font-bold' : 'text-white font-black text-base'">{{ user.username }}</span>
              </span>
              <button 
                @click="handleLogout" 
                :class="[
                  'font-bold uppercase text-sm tracking-widest transition-all duration-300 hover:text-red-500',
                  isScrolled || !isHome ? 'text-gray-700' : 'text-white drop-shadow-md'
                ]"
              >
                Đăng xuất
              </button>
            </div>
          </template>
        </div>

        <!-- Mobile menu button -->
        <div class="md:hidden flex items-center">
          <button @click="isOpen = !isOpen" class="text-gray-700 hover:text-emerald-700">
            <svg class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path v-if="!isOpen" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
              <path v-else stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>
      </div>
    </div>

    <!-- Mobile Menu -->
    <div v-if="isOpen" class="md:hidden bg-white border-t border-gray-100 pb-4">
      <div class="px-2 pt-2 pb-3 space-y-1">
        <router-link v-for="item in menuItems" :key="item.path" :to="item.path" class="block px-3 py-2 text-gray-700 font-bold uppercase text-sm" @click="isOpen = false">
          {{ item.name }}
        </router-link>
        <template v-if="!user">
          <router-link to="/login" class="block px-3 py-2 text-emerald-800 font-bold uppercase text-sm">Đăng nhập</router-link>
        </template>
        <template v-else>
          <button @click="handleLogout" class="block w-full text-left px-3 py-2 text-red-600 font-bold uppercase text-sm">Đăng xuất</button>
        </template>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { ref, onMounted, onUnmounted, watch, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()
const isOpen = ref(false)
const user = ref(null)
const isScrolled = ref(false)

const isHome = computed(() => route.path === '/' || route.path === '/about')

const menuItems = [
  { name: 'Trang chủ', path: '/' },
  { name: 'Giới thiệu', path: '/about' },
  { name: 'Loại Phòng', path: '/rooms' },
  { name: 'Check-in', path: '/checkin' },
  { name: 'Dịch vụ', path: '/#services' },
  { name: 'Liên hệ', path: '/contact' }
]

const handleScroll = () => {
  isScrolled.value = window.scrollY > 50
}

const checkUser = () => {
  const userData = localStorage.getItem('user')
  user.value = userData ? JSON.parse(userData) : null
}

const handleLogout = () => {
  localStorage.removeItem('user')
  user.value = null
  router.push('/login')
}

onMounted(() => {
  checkUser()
  window.addEventListener('scroll', handleScroll)
})

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll)
})

// Update user state when route changes (e.g., after login)
watch(() => route.path, () => {
  checkUser()
  isOpen.value = false
})
</script>
