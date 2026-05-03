<template>
  <nav 
    :class="[
      'fixed top-0 left-0 right-0 z-50 transition-all duration-300',
      isScrolled || !isHome ? 'bg-[#002b16] shadow-xl py-3' : 'bg-[#002b16]/90 backdrop-blur-md py-4'
    ]"
  >
    <div class="max-w-[1440px] mx-auto px-6 lg:px-12">
      <div class="flex justify-between items-center h-14">
        <!-- Logo -->
        <div class="flex-shrink-0 flex items-center">
          <router-link to="/" class="flex items-center space-x-3 group">
            <div class="w-10 h-10 bg-emerald-500/20 rounded-full flex items-center justify-center border border-emerald-500/30 group-hover:bg-emerald-500/40 transition-all">
              <span class="text-emerald-400 font-black text-xl">H</span>
            </div>
            <span class="text-xl font-black tracking-tighter uppercase text-white group-hover:text-emerald-400 transition-colors">
              Grand Hotel
            </span>
          </router-link>
        </div>

        <!-- Desktop Menu -->
        <div class="hidden xl:flex items-center space-x-8">
          <router-link 
            v-for="item in menuItems" 
            :key="item.path"
            :to="item.path" 
            :class="[
              'font-black uppercase text-[11px] tracking-[0.15em] transition-all duration-300 relative py-2',
              $route.path === item.path ? 'text-white after:content-[\'\'] after:absolute after:bottom-0 after:left-0 after:w-full after:h-0.5 after:bg-amber-400' : 'text-white/70 hover:text-emerald-400'
            ]"
          >
            {{ item.name }}
          </router-link>
        </div>

        <!-- Auth & Action -->
        <div class="flex items-center space-x-6">
          <template v-if="user">
            <div class="hidden lg:flex items-center gap-3">
              <div class="w-8 h-8 bg-white/10 rounded-full flex items-center justify-center border border-white/20">
                <i class="fas fa-user text-[10px] text-white"></i>
              </div>
              <span class="text-[11px] font-black text-white/70 uppercase tracking-widest">
                Xin chào, <span class="text-white">{{ user.username }}</span>
              </span>
            </div>
            <button 
              @click="handleLogout" 
              class="text-[10px] font-black uppercase tracking-[0.2em] text-white/50 hover:text-rose-400 transition-colors"
            >
              Đăng xuất
            </button>
          </template>
          
          <router-link 
            v-if="!user"
            to="/login" 
            class="text-[11px] font-black uppercase tracking-[0.2em] text-white hover:text-emerald-400 transition-colors"
          >
            Đăng nhập
          </router-link>

          <router-link 
            to="/rooms" 
            class="px-8 py-3 bg-[#004d26] text-white rounded-xl font-black text-[11px] uppercase tracking-[0.2em] hover:bg-emerald-500 transition-all shadow-lg shadow-emerald-950/20"
          >
            Đặt phòng
          </router-link>
        </div>

        <!-- Mobile menu button -->
        <div class="xl:hidden flex items-center ml-4">
          <button @click="isOpen = !isOpen" class="text-white hover:text-emerald-400">
            <svg class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path v-if="!isOpen" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
              <path v-else stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>
      </div>
    </div>

    <!-- Mobile Menu -->
    <div v-if="isOpen" class="xl:hidden bg-[#002b16] border-t border-white/5 py-6">
      <div class="px-6 space-y-4">
        <router-link 
          v-for="item in menuItems" 
          :key="item.path" 
          :to="item.path" 
          class="block text-white/70 font-black uppercase text-xs tracking-widest hover:text-emerald-400" 
          @click="isOpen = false"
        >
          {{ item.name }}
        </router-link>
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

const isHome = computed(() => route.path === '/')

const menuItems = computed(() => {
  const items = [
    { name: 'Trang chủ', path: '/' },
    { name: 'Phòng', path: '/rooms' },
    { name: 'Dịch vụ', path: '/services' },
    { name: 'Chính sách', path: '/cancellation-policy' },
    { name: 'Blog', path: '/blog' },
    { name: 'Liên hệ', path: '/contact' }
  ]
  
  if (user.value) {
    if (user.value.role === 'ADMIN') {
      items.push({ name: 'Dashboard', path: '/dashboard' })
    } else if (user.value.role === 'STAFF') {
      items.push({ name: 'Dashboard', path: '/staff-dashboard' })
    }
    
    if (user.value.role === 'CUSTOMER') {
      items.push({ name: 'Booking', path: '/my-bookings' })
    }
  }
  
  return items
})

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

watch(() => route.path, () => {
  checkUser()
  isOpen.value = false
})
</script>