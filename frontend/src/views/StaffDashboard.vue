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
      </nav>

      <div class="p-6 border-t border-white/10">
        <button @click="logout" class="flex items-center gap-4 py-4 px-6 w-full rounded-xl transition-all duration-300 hover:bg-white/10 group text-white uppercase tracking-widest font-black text-sm">
          <span>Đăng xuất</span>
        </button>
      </div>
    </aside>

    <!-- Main Content -->
    <main class="flex-1 p-10 overflow-y-auto">
      <!-- Stats Grid -->
      <div class="grid grid-cols-1 md:grid-cols-3 gap-10 mb-12">
        <!-- Phòng Trống Card -->
        <div class="bg-white rounded-[2.5rem] p-10 shadow-xl shadow-gray-200 border border-gray-50 flex flex-col items-center text-center group transition-all duration-300 hover:-translate-y-2">
          <div class="w-20 h-20 bg-[#00a859] rounded-3xl flex items-center justify-center text-white shadow-xl shadow-green-100 mb-6">
            <i class="fas fa-bed text-3xl"></i>
          </div>
          <h3 class="text-[#00a859] font-black uppercase tracking-widest text-sm mb-4">Phòng Trống</h3>
          <p class="text-6xl font-black text-gray-900 mb-4">{{ stats.availableRooms || 0 }}</p>
          <p class="text-gray-400 font-bold uppercase tracking-widest text-[10px]">Số phòng trống</p>
        </div>

        <!-- Booking Mới Card -->
        <div class="bg-white rounded-[2.5rem] p-10 shadow-xl shadow-gray-200 border border-gray-50 flex flex-col items-center text-center group transition-all duration-300 hover:-translate-y-2">
          <div class="w-20 h-20 bg-[#f39200] rounded-3xl flex items-center justify-center text-white shadow-xl shadow-orange-100 mb-6">
            <i class="fas fa-calendar-check text-3xl"></i>
          </div>
          <h3 class="text-[#f39200] font-black uppercase tracking-widest text-sm mb-4">Booking Mới</h3>
          <p class="text-6xl font-black text-gray-900 mb-4">{{ stats.newBookings || 0 }}</p>
          <p class="text-gray-400 font-bold uppercase tracking-widest text-[10px]">Đặt phòng hôm nay</p>
        </div>

        <!-- Phòng Có Khách Card -->
        <div class="bg-white rounded-[2.5rem] p-10 shadow-xl shadow-gray-200 border border-gray-50 flex flex-col items-center text-center group transition-all duration-300 hover:-translate-y-2">
          <div class="w-20 h-20 bg-[#8e44ad] rounded-3xl flex items-center justify-center text-white shadow-xl shadow-purple-100 mb-6">
            <i class="fas fa-building text-3xl"></i>
          </div>
          <h3 class="text-[#8e44ad] font-black uppercase tracking-widest text-sm mb-4">Phòng Có Khách</h3>
          <p class="text-6xl font-black text-gray-900 mb-4">{{ stats.occupiedRooms || 0 }} / {{ stats.totalRooms || 64 }}</p>
          <p class="text-gray-400 font-bold uppercase tracking-widest text-[10px]">Phòng đang sử dụng</p>
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
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
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
  totalRooms: 64
})

const fetchStats = async () => {
  try {
    const response = await axios.get('/dashboard/stats')
    stats.value = response.data
    // Reload dữ liệu phòng trong RoomMap nếu có
    if (roomMap.value && typeof roomMap.value.fetchRooms === 'function') {
      roomMap.value.fetchRooms()
    }
  } catch (error) {
    console.error('Lỗi khi lấy thông tin thống kê:', error)
    // Nếu lỗi 403 hoặc 401 thì có thể do token hết hạn
    if (error.response?.status === 403 || error.response?.status === 401) {
      console.warn('Lỗi phân quyền, vui lòng đăng nhập lại')
    }
  }
}

const formatPrice = (price) => {
  if (!price) return '0 đ'
  return `${new Intl.NumberFormat('vi-VN').format(price * 25000)} đ`
}

const logout = () => {
  localStorage.removeItem('user')
  router.push('/login')
}

onMounted(() => {
  fetchStats()
  // Tự động cập nhật stats mỗi 10 giây để đồng bộ với hành động của khách/admin
  const interval = setInterval(fetchStats, 10000)
  return () => clearInterval(interval)
})
</script>

<style scoped>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css');

/* Tùy chỉnh thanh cuộn cho Main Content */
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
