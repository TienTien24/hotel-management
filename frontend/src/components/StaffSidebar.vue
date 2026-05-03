<template>
  <aside class="w-72 bg-[#004d26] text-white min-h-screen flex flex-col shadow-2xl shrink-0 z-50">
    <div class="p-8 border-b border-white/5">
      <div class="flex items-center gap-3">
        <div class="w-10 h-10 bg-white/10 rounded-lg flex items-center justify-center font-black text-xl border border-white/20">H</div>
        <div>
          <h1 class="text-lg font-black uppercase tracking-tight leading-none">Grand Hotel</h1>
        </div>
      </div>
    </div>
    
    <nav class="flex-1 px-4 mt-8 space-y-1">
      <router-link to="/staff-dashboard" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group" :class="$route.path === '/staff-dashboard' ? 'bg-white/10 text-white shadow-lg' : 'text-white/60 hover:bg-white/5 hover:text-white'">
        <i class="fas fa-chart-pie text-lg"></i>
        <span class="font-bold text-sm tracking-tight">Tổng quan</span>
      </router-link>

      <router-link to="/staff-rooms" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group" :class="$route.path === '/staff-rooms' ? 'bg-white/10 text-white shadow-lg' : 'text-white/60 hover:bg-white/5 hover:text-white'">
        <i class="fas fa-bed text-lg"></i>
        <span class="font-bold text-sm tracking-tight">Quản lý Phòng</span>
      </router-link>

      <router-link to="/bookings" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group" :class="$route.path === '/bookings' ? 'bg-white/10 text-white shadow-lg' : 'text-white/60 hover:bg-white/5 hover:text-white'">
        <i class="fas fa-calendar-check text-lg"></i>
        <span class="font-bold text-sm tracking-tight">Quản lý Đặt phòng</span>
      </router-link>

      <router-link to="/manage-services" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group" :class="$route.path === '/manage-services' ? 'bg-white/10 text-white shadow-lg' : 'text-white/60 hover:bg-white/5 hover:text-white'">
        <i class="fas fa-concierge-bell text-lg"></i>
        <span class="font-bold text-sm tracking-tight">Quản lý Dịch vụ</span>
      </router-link>

      <router-link to="/staff-messages" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group" :class="$route.path === '/staff-messages' ? 'bg-white/10 text-white shadow-lg' : 'text-white/60 hover:bg-white/5 hover:text-white'">
        <i class="fas fa-comment-alt text-lg"></i>
        <span class="font-bold text-sm tracking-tight">Phản hồi khách hàng</span>
      </router-link>
    </nav>

    <div class="p-8 border-t border-white/5">
      <button @click="logout" class="w-full py-4 px-6 bg-rose-500/10 hover:bg-rose-500 text-rose-500 hover:text-white rounded-xl transition-all duration-300 flex items-center justify-center gap-3 font-black uppercase tracking-widest text-[10px]">
        <i class="fas fa-sign-out-alt"></i>
        <span>Đăng xuất</span>
      </button>
    </div>
  </aside>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const logout = () => {
  localStorage.removeItem('user')
  router.push('/login')
}
</script>

<style scoped>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css');
</style>
