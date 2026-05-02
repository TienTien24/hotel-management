<template>
  <aside class="w-72 bg-[#004d26] text-white min-h-screen flex flex-col shadow-2xl shrink-0 z-50">
    <div class="p-8 border-b border-white/5">
      <div class="flex items-center gap-3">
        <div class="w-10 h-10 bg-white/10 rounded-lg flex items-center justify-center font-black text-xl border border-white/20">H</div>
        <div>
          <h1 class="text-lg font-black uppercase tracking-tight leading-none">Grand Hotel</h1>
          <span class="text-[10px] text-green-300 font-bold uppercase tracking-[0.2em] mt-1 block">Quản trị hệ thống</span>
        </div>
      </div>
    </div>

    <nav class="flex-1 px-4 mt-8 space-y-1">
      <router-link
        v-for="item in items"
        :key="item.to"
        :to="item.to"
        class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group"
        :class="isActive(item.to) ? 'bg-white/10 text-white shadow-lg' : 'text-white/60 hover:bg-white/5 hover:text-white'"
      >
        <i :class="[item.icon, 'text-lg']"></i>
        <span class="font-bold text-sm tracking-tight">{{ item.label }}</span>
      </router-link>
    </nav>

    <div class="p-8 border-t border-white/5">
      <button
        @click="logout"
        class="w-full py-4 px-6 bg-rose-500/10 hover:bg-rose-500 text-rose-500 hover:text-white rounded-xl transition-all duration-300 flex items-center justify-center gap-3 font-black uppercase tracking-widest text-[10px]"
      >
        <i class="fas fa-sign-out-alt"></i>
        Đăng xuất
      </button>
    </div>
  </aside>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const items = [
  { to: '/admin-dashboard', label: 'Tổng quan', icon: 'fas fa-chart-pie' },
  { to: '/admin/staff', label: 'Quản lý nhân viên', icon: 'fas fa-users-cog' },
  { to: '/admin/rooms', label: 'Quản lý phòng', icon: 'fas fa-bed' },
  { to: '/admin/services', label: 'Quản lý dịch vụ', icon: 'fas fa-concierge-bell' },
  { to: '/admin/customers', label: 'Quản lý khách hàng', icon: 'fas fa-user-friends' },
  { to: '/admin/reports', label: 'Báo cáo doanh thu', icon: 'fas fa-file-invoice-dollar' },
  { to: '/admin/settings', label: 'Cài đặt hệ thống', icon: 'fas fa-cog' }
]

const isActive = (targetPath) => route.path === targetPath

const logout = () => {
  localStorage.removeItem('user')
  router.push('/login')
}
</script>

<style scoped>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css');
</style>
