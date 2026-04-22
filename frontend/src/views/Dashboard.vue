<template>
  <div class="min-h-screen flex">
    <!-- Sidebar -->
    <aside class="w-64 bg-gray-800 text-white min-h-screen p-6 hidden md:block">
      <h1 class="text-2xl font-bold mb-10 text-center">Hotel Admin</h1>
      <nav>
        <router-link to="/" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700" :class="{'bg-gray-700': $route.path === '/'}">Dashboard</router-link>
        <router-link to="/rooms" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700" :class="{'bg-gray-700': $route.path === '/rooms'}">Quản lý Phòng</router-link>
        <router-link to="/bookings" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700" :class="{'bg-gray-700': $route.path === '/bookings'}">Quản lý Đặt phòng</router-link>
      </nav>
      <div class="mt-auto pt-10">
        <button @click="logout" class="w-full bg-red-600 hover:bg-red-700 py-2 rounded-md">Đăng xuất</button>
      </div>
    </aside>

    <!-- Main content -->
    <main class="flex-1 p-8 bg-gray-100">
      <header class="flex justify-between items-center mb-10">
        <h2 class="text-3xl font-bold text-gray-800">Tổng quan hệ thống</h2>
        <div class="flex items-center space-x-4">
          <span class="text-gray-600">Xin chào, <strong>{{ user?.username }}</strong> ({{ user?.role }})</span>
        </div>
      </header>

      <!-- Stats Grid -->
      <div v-if="user?.role === 'ADMIN'" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-10">
        <div class="bg-white p-6 rounded-lg shadow-sm border-l-4 border-blue-500">
          <p class="text-sm text-gray-500 uppercase font-bold">Doanh thu tổng</p>
          <p class="text-2xl font-bold text-gray-800">${{ stats.totalRevenue || 0 }}</p>
        </div>
        <div class="bg-white p-6 rounded-lg shadow-sm border-l-4 border-green-500">
          <p class="text-sm text-gray-500 uppercase font-bold">Tỷ lệ lấp đầy</p>
          <p class="text-2xl font-bold text-gray-800">{{ stats.occupancyRate?.toFixed(1) || 0 }}%</p>
        </div>
        <div class="bg-white p-6 rounded-lg shadow-sm border-l-4 border-yellow-500">
          <p class="text-sm text-gray-500 uppercase font-bold">Booking mới</p>
          <p class="text-2xl font-bold text-gray-800">{{ stats.newBookings || 0 }}</p>
        </div>
        <div class="bg-white p-6 rounded-lg shadow-sm border-l-4 border-purple-500">
          <p class="text-sm text-gray-500 uppercase font-bold">Phòng đang sử dụng</p>
          <p class="text-2xl font-bold text-gray-800">{{ stats.occupiedRooms || 0 }} / {{ stats.totalRooms || 0 }}</p>
        </div>
      </div>

      <!-- Customer Messages Section (Admin & Staff) -->
      <div v-if="user?.role === 'ADMIN' || user?.role === 'STAFF'" class="bg-white rounded-lg shadow-sm p-6 mb-10">
        <h3 class="text-xl font-bold text-gray-800 mb-6 flex items-center">
          <span class="mr-2">📩</span> Phản hồi khách hàng
        </h3>
        
        <div v-if="messages.length === 0" class="text-center py-10 text-gray-500">
          Chưa có phản hồi nào từ khách hàng.
        </div>
        
        <div v-else class="overflow-x-auto">
          <table class="w-full text-left">
            <thead>
              <tr class="border-b border-gray-100">
                <th class="py-3 px-4 font-bold text-gray-600 text-sm">Ngày</th>
                <th class="py-3 px-4 font-bold text-gray-600 text-sm">Khách hàng</th>
                <th class="py-3 px-4 font-bold text-gray-600 text-sm">Liên hệ</th>
                <th class="py-3 px-4 font-bold text-gray-600 text-sm">Nội dung / Ghi chú</th>
                <th class="py-3 px-4 font-bold text-gray-600 text-sm">Trạng thái</th>
                <th class="py-3 px-4 font-bold text-gray-600 text-sm text-right">Thao tác</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(msg, index) in messages" :key="index" class="border-b border-gray-50 hover:bg-gray-50 transition-colors">
                <td class="py-4 px-4 text-sm text-gray-500">{{ new Date(msg.createdAt).toLocaleDateString('vi-VN') }}</td>
                <td class="py-4 px-4 font-medium text-gray-800">{{ msg.fullName }}</td>
                <td class="py-4 px-4 text-sm text-gray-600">
                  <div>{{ msg.email }}</div>
                  <div class="text-xs text-gray-400">{{ msg.phone }}</div>
                </td>
                <td class="py-4 px-4 text-sm text-gray-600 max-w-xs truncate" :title="msg.note">
                  {{ msg.note || 'Không có ghi chú' }}
                </td>
                <td class="py-4 px-4">
                  <span 
                    :class="[
                      'px-3 py-1 rounded-full text-[10px] font-bold uppercase tracking-widest',
                      msg.status === 'PENDING' ? 'bg-amber-100 text-amber-700' : 
                      msg.status === 'READ' ? 'bg-blue-100 text-blue-700' : 'bg-emerald-100 text-emerald-700'
                    ]"
                  >
                    {{ msg.status === 'PENDING' ? 'Mới' : msg.status === 'READ' ? 'Đã đọc' : 'Đã phản hồi' }}
                  </span>
                </td>
                <td class="py-4 px-4 text-right">
                  <div class="flex items-center justify-end space-x-2">
                    <button 
                      v-if="msg.status === 'PENDING'"
                      @click="updateMessageStatus(msg.id, 'READ')"
                      class="p-1.5 bg-blue-50 text-blue-600 rounded-md hover:bg-blue-100 transition-colors"
                      title="Đánh dấu đã đọc"
                    >
                      <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                      </svg>
                    </button>
                    <button 
                      v-if="msg.status !== 'REPLIED'"
                      @click="updateMessageStatus(msg.id, 'REPLIED')"
                      class="p-1.5 bg-emerald-50 text-emerald-600 rounded-md hover:bg-emerald-100 transition-colors"
                      title="Đã phản hồi"
                    >
                      <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 10h10a8 8 0 018 8v2M3 10l6 6m-6-6l6-6" />
                      </svg>
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <div v-if="user?.role !== 'ADMIN' && user?.role !== 'STAFF'" class="bg-white p-8 rounded-lg shadow-md">
        <h3 class="text-xl font-bold mb-4 text-center">Chào mừng bạn đến với Hệ thống Quản lý Khách sạn!</h3>
        <p class="text-gray-600 text-center">Vui lòng sử dụng menu để thực hiện các chức năng.</p>
        <div class="mt-8 flex justify-center space-x-4">
          <router-link to="/rooms" class="bg-blue-600 text-white px-6 py-2 rounded-md">Xem phòng ngay</router-link>
          <router-link to="/bookings" class="bg-gray-600 text-white px-6 py-2 rounded-md">Lịch sử đặt phòng</router-link>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from '../api/axios'

const user = ref(null)
const stats = ref({})
const messages = ref([])
const router = useRouter()

const logout = () => {
  localStorage.removeItem('user')
  router.push('/login')
}

const fetchMessages = async () => {
  try {
    const response = await axios.get('/contact/messages')
    messages.value = response.data
  } catch (error) {
    console.error('Lỗi khi lấy tin nhắn liên hệ:', error)
  }
}

const updateMessageStatus = async (id, status) => {
  try {
    await axios.put(`/contact/messages/${id}/status?status=${status}`)
    // Cập nhật lại danh sách sau khi đổi trạng thái
    await fetchMessages()
  } catch (error) {
    console.error('Lỗi khi cập nhật trạng thái:', error)
    alert('Không thể cập nhật trạng thái tin nhắn.')
  }
}

const fetchStats = async () => {
  if (user.value?.role === 'ADMIN') {
    try {
      const response = await axios.get('/dashboard/stats')
      stats.value = response.data
    } catch (error) {
      console.error('Lỗi khi lấy thông tin thống kê:', error)
    }
  }
}

onMounted(() => {
  user.value = JSON.parse(localStorage.getItem('user'))
  fetchStats()
  fetchMessages()
})
</script>
