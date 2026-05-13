<template>
  <div class="min-h-screen flex bg-gray-50 font-sans">
    <!-- Sidebar -->
    <StaffSidebar />

    <!-- Main Content -->
    <main class="flex-1 p-10 overflow-y-auto">
      <header class="flex flex-col lg:flex-row lg:items-center lg:justify-between gap-6 mb-8">
        <div>
          <h2 class="text-3xl font-black tracking-tight text-slate-800">Tổng quan</h2>
        </div>
      </header>

      <!-- Stats Grid -->
      <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
        <div class="bg-white rounded-[2rem] p-6 shadow-sm border border-slate-100">
          <p class="text-[10px] font-black text-slate-400 uppercase tracking-widest mb-2">Phòng trống</p>
          <p class="text-3xl font-black text-emerald-600">{{ stats.availableRooms || 0 }}</p>
        </div>
        <div class="bg-white rounded-[2rem] p-6 shadow-sm border border-slate-100">
          <p class="text-[10px] font-black text-slate-400 uppercase tracking-widest mb-2">Booking mới</p>
          <p class="text-3xl font-black text-amber-600">{{ stats.newBookings || 0 }}</p>
        </div>
        <div class="bg-white rounded-[2rem] p-6 shadow-sm border border-slate-100">
          <p class="text-[10px] font-black text-slate-400 uppercase tracking-widest mb-2">Phòng có khách</p>
          <p class="text-3xl font-black text-blue-600">{{ stats.occupiedRooms || 0 }}</p>
        </div>
        <div class="bg-white rounded-[2rem] p-6 shadow-sm border border-slate-100">
          <p class="text-[10px] font-black text-slate-400 uppercase tracking-widest mb-2">Tin nhắn mới</p>
          <p class="text-3xl font-black text-rose-600">{{ stats.pendingMessages || 0 }}</p>
        </div>
      </div>

      <!-- Sơ đồ phòng trực quan Section -->
      <div class="bg-white rounded-[2.5rem] shadow-sm border border-slate-100 overflow-hidden">
        <div class="p-8 border-b border-slate-50 bg-gray-50 bg-opacity-50">
          <div class="flex flex-col lg:flex-row lg:items-center lg:justify-between gap-4">
            <div>
              <h3 class="text-2xl font-black text-slate-800">Sơ đồ phòng trực quan</h3>
              <p class="text-slate-400 text-sm mt-1">Tình trạng phòng thực tế</p>
            </div>
            <div class="flex gap-4 flex-wrap">
              <div class="flex items-center gap-2">
                <span class="w-3 h-3 rounded-full bg-emerald-600"></span>
                <span class="text-[10px] font-black text-slate-400 uppercase tracking-widest">Trống</span>
              </div>
              <div class="flex items-center gap-2">
                <span class="w-3 h-3 rounded-full bg-blue-600"></span>
                <span class="text-[10px] font-black text-slate-400 uppercase tracking-widest">Đã đặt</span>
              </div>
              <div class="flex items-center gap-2">
                <span class="w-3 h-3 rounded-full bg-rose-600"></span>
                <span class="text-[10px] font-black text-slate-400 uppercase tracking-widest">Có khách</span>
              </div>
              <div class="flex items-center gap-2">
                <span class="w-3 h-3 rounded-full bg-amber-600"></span>
                <span class="text-[10px] font-black text-slate-400 uppercase tracking-widest">Đang dọn</span>
              </div>
              <div class="flex items-center gap-2">
                <span class="w-3 h-3 rounded-full bg-slate-600"></span>
                <span class="text-[10px] font-black text-slate-400 uppercase tracking-widest">Bảo trì</span>
              </div>
            </div>
          </div>
        </div>
        
        <div class="p-8">
          <RoomMap ref="roomMap" @refresh-stats="fetchStats" :hide-header="true" />
        </div>
      </div>
    </main>

  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from '../api/axios'
import RoomMap from '../components/RoomMap.vue'
import StaffSidebar from '../components/StaffSidebar.vue'

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
  width: 8px;
}
main::-webkit-scrollbar-track {
  background: #f1f1f1;
}
main::-webkit-scrollbar-thumb {
  background: #005c32;
  border-radius: 10px;
}
main::-webkit-scrollbar-thumb:hover {
  background: #004d26;
}
</style>
