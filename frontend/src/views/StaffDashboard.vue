<template>
  <div class="min-h-screen flex bg-gray-50 font-sans">
    <!-- Sidebar -->
    <aside class="w-72 bg-[#004d26] text-white min-h-screen flex flex-col shadow-2xl shrink-0">
      <div class="p-8 mb-4">
        <div class="flex items-center gap-3">
          <div class="w-12 h-12 bg-white/10 rounded-full flex items-center justify-center font-black text-2xl border border-white/20">H</div>
          <h1 class="text-2xl font-black uppercase tracking-tight">Grand Hotel</h1>
        </div>
      </div>
      
      <nav class="flex-1 px-4 space-y-1">
        <router-link to="/staff-dashboard" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group" :class="{'bg-white/10': $route.path === '/staff-dashboard', 'hover:bg-white/10': $route.path !== '/staff-dashboard'}">
          <i class="fas fa-th-large text-xl opacity-70 group-hover:opacity-100" :class="{'opacity-100': $route.path === '/staff-dashboard'}"></i>
          <span class="font-bold text-lg tracking-tight">Tổng quan</span>
        </router-link>

        <router-link to="/staff-rooms" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group" :class="{'bg-white/10': $route.path === '/staff-rooms', 'hover:bg-white/10': $route.path !== '/staff-rooms'}">
          <i class="fas fa-door-open text-xl opacity-70 group-hover:opacity-100" :class="{'opacity-100': $route.path === '/staff-rooms'}"></i>
          <span class="font-bold text-lg tracking-tight">Quản lý Phòng</span>
        </router-link>

        <router-link to="/bookings" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group" :class="{'bg-white/10': $route.path === '/bookings', 'hover:bg-white/10': $route.path !== '/bookings'}">
          <i class="fas fa-calendar-alt text-xl opacity-70 group-hover:opacity-100" :class="{'opacity-100': $route.path === '/bookings'}"></i>
          <span class="font-bold text-lg tracking-tight">Quản lý Đặt phòng</span>
        </router-link>

        <router-link to="/manage-services" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group" :class="{'bg-white/10': $route.path === '/manage-services', 'hover:bg-white/10': $route.path !== '/manage-services'}">
          <i class="fas fa-bell text-xl opacity-70 group-hover:opacity-100" :class="{'opacity-100': $route.path === '/manage-services'}"></i>
          <span class="font-bold text-lg tracking-tight">Quản lý Dịch vụ</span>
        </router-link>

        <router-link to="/staff-messages" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group" :class="{'bg-white/10': $route.path === '/staff-messages', 'hover:bg-white/10': $route.path !== '/staff-messages'}">
          <div class="relative">
            <i class="fas fa-envelope text-xl opacity-70 group-hover:opacity-100" :class="{'opacity-100': $route.path === '/staff-messages'}"></i>
            <span v-if="stats.pendingMessages > 0" class="absolute -top-2 -right-2 w-4 h-4 bg-rose-500 text-[8px] font-black rounded-full flex items-center justify-center border border-white/20">
              {{ stats.pendingMessages }}
            </span>
          </div>
          <span class="font-bold text-lg tracking-tight">Phản hồi khách hàng</span>
        </router-link>
      </nav>

      <div class="p-10 mt-auto">
        <button @click="logout" class="text-white/60 hover:text-white transition-colors font-black uppercase tracking-[0.2em] text-xs flex items-center gap-3">
          <i class="fas fa-sign-out-alt"></i>
          Đăng xuất
        </button>
      </div>
    </aside>

    <!-- Main Content -->
    <main class="flex-1 p-10 overflow-y-auto">
      <!-- Stats Grid -->
      <div class="grid grid-cols-1 md:grid-cols-4 gap-8 mb-12">
        <!-- Phòng Trống Card -->
        <div class="bg-white rounded-[2.5rem] p-8 shadow-xl shadow-gray-200 border border-gray-50 flex flex-col items-center text-center group transition-all duration-300 hover:-translate-y-2">
          <div class="w-16 h-16 bg-[#00a859] rounded-3xl flex items-center justify-center text-white shadow-xl shadow-green-100 mb-6">
            <i class="fas fa-bed text-2xl"></i>
          </div>
          <h3 class="text-[#00a859] font-black uppercase tracking-widest text-[10px] mb-2">Phòng Trống</h3>
          <p class="text-4xl font-black text-gray-900 mb-2">{{ stats.availableRooms || 0 }}</p>
          <p class="text-gray-400 font-bold uppercase tracking-widest text-[8px]">Số phòng trống</p>
        </div>

        <!-- Booking Mới Card -->
        <div class="bg-white rounded-[2.5rem] p-8 shadow-xl shadow-gray-200 border border-gray-50 flex flex-col items-center text-center group transition-all duration-300 hover:-translate-y-2">
          <div class="w-16 h-16 bg-[#f39200] rounded-3xl flex items-center justify-center text-white shadow-xl shadow-orange-100 mb-6">
            <i class="fas fa-calendar-check text-2xl"></i>
          </div>
          <h3 class="text-[#f39200] font-black uppercase tracking-widest text-[10px] mb-2">Booking Mới</h3>
          <p class="text-4xl font-black text-gray-900 mb-2">{{ stats.newBookings || 0 }}</p>
          <p class="text-gray-400 font-bold uppercase tracking-widest text-[8px]">Đặt phòng hôm nay</p>
        </div>

        <!-- Phòng Có Khách Card -->
        <div class="bg-white rounded-[2.5rem] p-8 shadow-xl shadow-gray-200 border border-gray-50 flex flex-col items-center text-center group transition-all duration-300 hover:-translate-y-2">
          <div class="w-16 h-16 bg-[#8e44ad] rounded-3xl flex items-center justify-center text-white shadow-xl shadow-purple-100 mb-6">
            <i class="fas fa-building text-2xl"></i>
          </div>
          <h3 class="text-[#8e44ad] font-black uppercase tracking-widest text-[10px] mb-2">Phòng Có Khách</h3>
          <p class="text-4xl font-black text-gray-900 mb-2">{{ stats.occupiedRooms || 0 }} / {{ stats.totalRooms || 64 }}</p>
          <p class="text-gray-400 font-bold uppercase tracking-widest text-[8px]">Phòng đang sử dụng</p>
        </div>

        <!-- Tin nhắn mới Card -->
        <div class="bg-white rounded-[2.5rem] p-8 shadow-xl shadow-gray-200 border border-gray-50 flex flex-col items-center text-center group transition-all duration-300 hover:-translate-y-2 cursor-pointer" @click="$router.push('/staff-messages')">
          <div class="w-16 h-16 bg-rose-500 rounded-3xl flex items-center justify-center text-white shadow-xl shadow-rose-100 mb-6 relative">
            <i class="fas fa-envelope text-2xl"></i>
            <span v-if="stats.pendingMessages > 0" class="absolute -top-1 -right-1 w-6 h-6 bg-white text-rose-500 text-[10px] font-black rounded-full flex items-center justify-center border-2 border-rose-500 shadow-sm">{{ stats.pendingMessages }}</span>
          </div>
          <h3 class="text-rose-500 font-black uppercase tracking-widest text-[10px] mb-2">Tin nhắn mới</h3>
          <p class="text-4xl font-black text-gray-900 mb-2">{{ stats.pendingMessages || 0 }}</p>
          <p class="text-gray-400 font-bold uppercase tracking-widest text-[8px]">Cần phản hồi</p>
        </div>
      </div>

      <!-- Sơ đồ phòng trực quan Section -->
      <div class="bg-white rounded-[3rem] shadow-2xl shadow-gray-200 overflow-hidden border border-gray-100">
        <div class="bg-[#004d26] text-white p-10 flex justify-between items-center">
          <div>
            <h3 class="text-3xl font-black uppercase tracking-tight">Sơ đồ phòng trực quan</h3>
            <p class="text-green-100/60 text-sm mt-2 font-medium">Tình trạng phòng thực tế</p>
          </div>
          <div class="flex gap-6">
            <div class="flex items-center gap-3">
              <span class="w-3 h-3 rounded-full bg-[#00a859]"></span>
              <span class="text-[11px] font-black uppercase tracking-widest text-green-50">Trống</span>
            </div>
            <div class="flex items-center gap-3">
              <span class="w-3 h-3 rounded-full bg-[#1d4ed8]"></span>
              <span class="text-[11px] font-black uppercase tracking-widest text-green-50">Đã đặt</span>
            </div>
            <div class="flex items-center gap-3">
              <span class="w-3 h-3 rounded-full bg-[#e74c3c]"></span>
              <span class="text-[11px] font-black uppercase tracking-widest text-green-50">Có khách</span>
            </div>
            <div class="flex items-center gap-3">
              <span class="w-3 h-3 rounded-full bg-[#f1c40f]"></span>
              <span class="text-[11px] font-black uppercase tracking-widest text-green-50">Đang dọn</span>
            </div>
            <div class="flex items-center gap-3">
              <span class="w-3 h-3 rounded-full bg-[#3498db]"></span>
              <span class="text-[11px] font-black uppercase tracking-widest text-green-50">Bảo trì</span>
            </div>
          </div>
        </div>
        
        <div class="p-10 bg-gray-50/30">
          <RoomMap ref="roomMap" @refresh-stats="fetchStats" :hide-header="true" />
        </div>
      </div>
    </main>

    <!-- Floating Notification -->
    <div v-if="showNotification" class="fixed bottom-10 right-10 bg-white rounded-[2rem] shadow-2xl p-6 border-l-8 border-rose-500 flex items-center gap-6 animate-bounce-in z-[100] cursor-pointer" @click="$router.push('/staff-messages')">
      <div class="w-14 h-14 bg-rose-50 rounded-2xl flex items-center justify-center text-rose-500">
        <i class="fas fa-envelope text-xl"></i>
      </div>
      <div>
        <h4 class="text-sm font-black text-gray-900 uppercase tracking-tight">Tin nhắn mới!</h4>
        <p class="text-xs text-gray-400 font-bold uppercase tracking-widest mt-1">Bạn có phản hồi mới từ khách hàng</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from '../api/axios'
import RoomMap from '../components/RoomMap.vue'

const router = useRouter()
const roomMap = ref(null)
const user = ref(JSON.parse(localStorage.getItem('user') || 'null'))
const stats = ref({
  availableRooms: 64,
  newBookings: 0,
  occupiedRooms: 0,
  cleaningRooms: 0,
  maintenanceRooms: 0,
  totalRooms: 64,
  pendingMessages: 0
})

const fetchStats = async () => {
  try {
    const response = await axios.get('/dashboard/stats')
    const newStats = response.data
    
    // Thông báo nếu có tin nhắn mới
    if (newStats.pendingMessages > stats.value.pendingMessages) {
      showNotification.value = true
      setTimeout(() => { showNotification.value = false }, 5000)
    }
    
    stats.value = newStats
  } catch (error) {
    console.error('Error fetching dashboard stats:', error)
  }
}

const showNotification = ref(false)
let statsInterval = null

const logout = () => {
  localStorage.removeItem('user')
  router.push('/login')
}

onMounted(() => {
  fetchStats()
  // Tự động cập nhật thống kê mỗi 30 giây
  statsInterval = setInterval(fetchStats, 30000)
})

onUnmounted(() => {
  if (statsInterval) clearInterval(statsInterval)
})
</script>

<style scoped>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css');

main::-webkit-scrollbar {
  width: 6px;
}
main::-webkit-scrollbar-track {
  background: transparent;
}
main::-webkit-scrollbar-thumb {
  background: #e2e8f0;
  border-radius: 10px;
}

.animate-bounce-in {
  animation: bounceIn 0.6s cubic-bezier(0.68, -0.55, 0.265, 1.55) forwards;
}

@keyframes bounceIn {
  0% { opacity: 0; transform: translateY(100px) scale(0.3); }
  60% { opacity: 1; transform: translateY(-20px) scale(1.05); }
  100% { transform: translateY(0) scale(1); }
}
</style>
