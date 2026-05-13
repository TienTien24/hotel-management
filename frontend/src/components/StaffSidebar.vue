<template>
  <aside class="w-72 bg-[#004d26] text-white min-h-screen flex flex-col shadow-2xl shrink-0 z-50">
    <div class="p-8 border-b border-white border-opacity-5">
      <div class="flex items-center gap-3">
        <div class="w-10 h-10 bg-white bg-opacity-10 rounded-lg flex items-center justify-center font-black text-xl border border-white border-opacity-20">H</div>
        <div>
          <h1 class="text-lg font-black uppercase tracking-tight leading-none">Grand Hotel</h1>
        </div>
      </div>
      <!-- Thông tin Lễ tân -->
      <div class="mt-6 flex items-center gap-3 p-3 bg-white bg-opacity-5 rounded-2xl border border-white border-opacity-10">
        <div class="w-10 h-10 rounded-full bg-slate-200 flex items-center justify-center overflow-hidden">
          <img v-if="user?.avatar" :src="user.avatar" class="w-full h-full object-cover">
          <i v-else class="fas fa-user text-slate-400"></i>
        </div>
        <div class="flex flex-col">
          <span class="text-[10px] font-black uppercase tracking-widest text-white text-opacity-40">Lễ tân</span>
          <span class="text-sm font-bold text-white leading-tight">{{ user?.fullName || 'Nhân viên' }}</span>
          <div class="flex items-center gap-1.5 mt-1">
            <div class="w-1.5 h-1.5 rounded-full bg-emerald-500"></div>
            <span class="text-[9px] font-bold text-emerald-500 uppercase tracking-widest">Online</span>
          </div>
        </div>
      </div>
    </div>
    
    <nav class="flex-1 px-4 mt-8 space-y-1">
      <router-link to="/staff-dashboard" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group" :class="$route.path === '/staff-dashboard' ? 'bg-white bg-opacity-10 text-white shadow-lg' : 'text-white text-opacity-60 hover:bg-white hover:bg-opacity-5 hover:text-white'">
        <i class="fas fa-chart-pie text-lg"></i>
        <span class="font-bold text-sm tracking-tight">Tổng quan</span>
      </router-link>

      <router-link to="/staff-rooms" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group" :class="$route.path === '/staff-rooms' ? 'bg-white bg-opacity-10 text-white shadow-lg' : 'text-white text-opacity-60 hover:bg-white hover:bg-opacity-5 hover:text-white'">
        <i class="fas fa-bed text-lg"></i>
        <span class="font-bold text-sm tracking-tight">Quản lý Phòng</span>
      </router-link>

      <router-link to="/bookings" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group" :class="$route.path === '/bookings' ? 'bg-white bg-opacity-10 text-white shadow-lg' : 'text-white text-opacity-60 hover:bg-white hover:bg-opacity-5 hover:text-white'">
        <i class="fas fa-calendar-check text-lg"></i>
        <span class="font-bold text-sm tracking-tight">Quản lý Đặt phòng</span>
      </router-link>

      <router-link to="/staff-check-in-out" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group" :class="$route.path === '/staff-check-in-out' ? 'bg-white bg-opacity-10 text-white shadow-lg' : 'text-white text-opacity-60 hover:bg-white hover:bg-opacity-5 hover:text-white'">
        <i class="fas fa-sign-in-alt text-lg"></i>
        <span class="font-bold text-sm tracking-tight">Check-in / Check-out</span>
      </router-link>

      <router-link to="/manage-services" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group" :class="$route.path === '/manage-services' ? 'bg-white bg-opacity-10 text-white shadow-lg' : 'text-white text-opacity-60 hover:bg-white hover:bg-opacity-5 hover:text-white'">
        <i class="fas fa-concierge-bell text-lg"></i>
        <span class="font-bold text-sm tracking-tight">Quản lý Dịch vụ</span>
      </router-link>

      <router-link to="/staff-messages" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group" :class="$route.path === '/staff-messages' ? 'bg-white bg-opacity-10 text-white shadow-lg' : 'text-white text-opacity-60 hover:bg-white hover:bg-opacity-5 hover:text-white'">
        <i class="fas fa-comment-alt text-lg"></i>
        <span class="font-bold text-sm tracking-tight">Phản hồi khách hàng</span>
      </router-link>
    </nav>

    <div class="p-8 border-t border-white border-opacity-5">
      <button @click="logout" class="w-full py-4 px-6 bg-rose-500 bg-opacity-10 hover:bg-rose-500 text-rose-500 hover:text-white rounded-xl transition-all duration-300 flex items-center justify-center gap-3 font-black uppercase tracking-widest text-[10px]">
        <i class="fas fa-sign-out-alt"></i>
        <span>Đăng xuất</span>
      </button>
    </div>
  </aside>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { ref, onMounted } from 'vue'

const router = useRouter()
const user = ref(null)

onMounted(() => {
  const userStr = localStorage.getItem('user')
  if (userStr) {
    user.value = JSON.parse(userStr)
  }
})

const logout = () => {
  localStorage.removeItem('user')
  router.push('/login')
}
</script>

<style scoped>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css');
</style>
