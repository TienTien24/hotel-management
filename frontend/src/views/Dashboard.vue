<template>
  <div class="min-h-screen flex">
    <!-- Sidebar -->
    <aside class="w-64 bg-gray-800 text-white min-h-screen p-6 hidden md:block">
      <h1 class="text-2xl font-bold mb-10 text-center">Hotel Admin</h1>
      <nav>
        <router-link to="/dashboard" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700" :class="{'bg-gray-700': $route.path === '/dashboard'}">
          {{ user?.role === 'ADMIN' ? 'Báo cáo doanh thu' : 'Tổng quan' }}
        </router-link>
        <router-link to="/rooms" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700" :class="{'bg-gray-700': $route.path === '/rooms'}">Quản lý Phòng</router-link>
        <router-link to="/bookings" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700" :class="{'bg-gray-700': $route.path === '/bookings'}">Quản lý Đặt phòng</router-link>
        <router-link to="/manage-services" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700" :class="{'bg-gray-700': $route.path === '/manage-services'}">Quản lý Dịch vụ</router-link>
        <router-link v-if="user?.role === 'ADMIN'" to="/staff" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700" :class="{'bg-gray-700': $route.path === '/staff'}">Quản lý Nhân viên</router-link>
      </nav>
    </aside>

    <!-- Main content -->
    <main class="flex-1 p-8 bg-gray-100">
      <header class="mb-10">
        <div class="flex justify-between items-center mb-6">
          <div>
            <h1 class="text-4xl font-bold text-gray-900 mb-2">
              {{ user?.role === 'ADMIN' ? 'Báo cáo Doanh thu' : 'Tổng quan Hệ thống' }}
            </h1>
            <p class="text-gray-600 text-lg">Chào mừng trở lại, <span class="font-semibold text-blue-600">{{ user?.username }}</span></p>
          </div>
        </div>
        
        <!-- Date and time display -->
        <div class="flex items-center justify-between bg-white rounded-xl shadow-sm p-4 border border-gray-100">
          <div class="flex items-center space-x-2 text-gray-600">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
            </svg>
            <span class="font-medium">{{ new Date().toLocaleDateString('vi-VN', { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' }) }}</span>
          </div>
          <div class="flex items-center space-x-2 text-gray-600">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path>
            </svg>
            <span class="font-medium">{{ new Date().toLocaleTimeString('vi-VN') }}</span>
          </div>
        </div>
      </header>

      <!-- Stats Grid -->
      <div v-if="user?.role === 'ADMIN'" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-10">
        <div class="bg-white rounded-xl shadow-lg hover:shadow-xl transition-all duration-300 transform hover:-translate-y-1 overflow-hidden">
          <div class="bg-gradient-to-r from-blue-500 to-blue-600 p-4">
            <div class="flex items-center justify-between">
              <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
              </svg>
              <span class="text-white text-sm font-medium">Doanh thu</span>
            </div>
          </div>
          <div class="p-6">
            <p class="text-3xl font-bold text-gray-900">${{ formatNumber(stats.totalRevenue || 0) }}</p>
            <p class="text-sm text-gray-500 mt-1">Tổng doanh thu</p>
          </div>
        </div>

        <div class="bg-white rounded-xl shadow-lg hover:shadow-xl transition-all duration-300 transform hover:-translate-y-1 overflow-hidden">
          <div class="bg-gradient-to-r from-green-500 to-green-600 p-4">
            <div class="flex items-center justify-between">
              <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"></path>
              </svg>
              <span class="text-white text-sm font-medium">Lấp đầy</span>
            </div>
          </div>
          <div class="p-6">
            <p class="text-3xl font-bold text-gray-900">{{ stats.occupancyRate?.toFixed(1) || 0 }}%</p>
            <p class="text-sm text-gray-500 mt-1">Tỷ lệ lấp đầy</p>
          </div>
        </div>

        <div class="bg-white rounded-xl shadow-lg hover:shadow-xl transition-all duration-300 transform hover:-translate-y-1 overflow-hidden">
          <div class="bg-gradient-to-r from-amber-500 to-amber-600 p-4">
            <div class="flex items-center justify-between">
              <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
              </svg>
              <span class="text-white text-sm font-medium">Booking</span>
            </div>
          </div>
          <div class="p-6">
            <p class="text-3xl font-bold text-gray-900">{{ stats.newBookings || 0 }}</p>
            <p class="text-sm text-gray-500 mt-1">Đặt phòng mới</p>
          </div>
        </div>

        <div class="bg-white rounded-xl shadow-lg hover:shadow-xl transition-all duration-300 transform hover:-translate-y-1 overflow-hidden">
          <div class="bg-gradient-to-r from-purple-500 to-purple-600 p-4">
            <div class="flex items-center justify-between">
              <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"></path>
              </svg>
              <span class="text-white text-sm font-medium">Phòng</span>
            </div>
          </div>
          <div class="p-6">
            <p class="text-3xl font-bold text-gray-900">{{ stats.occupiedRooms || 0 }} / {{ stats.totalRooms || 0 }}</p>
            <p class="text-sm text-gray-500 mt-1">Phòng đang sử dụng</p>
          </div>
        </div>
      </div>

      <!-- Revenue Report Section (Admin only) -->
      <div v-if="user?.role === 'ADMIN'" class="bg-white rounded-xl shadow-lg overflow-hidden mb-10">
        <div class="bg-gradient-to-r from-blue-600 to-blue-700 text-white p-6">
          <h3 class="text-2xl font-bold flex items-center">
            <svg class="w-6 h-6 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"></path>
            </svg>
            Báo cáo Doanh thu
          </h3>
          <p class="text-blue-100 mt-1">Phân tích doanh thu theo khoảng thời gian</p>
        </div>
        
        <div class="p-6">
          <div class="bg-gray-50 rounded-xl p-6 mb-6">
            <div class="grid grid-cols-1 md:grid-cols-4 gap-4">
              <div>
                <label class="block text-sm font-semibold text-gray-700 mb-2">Từ ngày</label>
                <input v-model="reportParams.fromDate" type="date" class="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
              </div>
              <div>
                <label class="block text-sm font-semibold text-gray-700 mb-2">Đến ngày</label>
                <input v-model="reportParams.toDate" type="date" class="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
              </div>
              <div class="md:col-span-2 flex items-end">
                <button @click="fetchRevenueReport" class="bg-gradient-to-r from-blue-600 to-blue-700 hover:from-blue-700 hover:to-blue-800 text-white px-6 py-3 rounded-lg font-semibold transition-all duration-200 shadow-lg hover:shadow-xl flex items-center">
                  <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"></path>
                  </svg>
                  Xem báo cáo
                </button>
              </div>
            </div>
          </div>

        <div v-if="revenueReport" class="mt-8">
          <div class="grid grid-cols-1 md:grid-cols-3 gap-6 bg-blue-50 p-6 rounded-xl border border-blue-100 mb-8">
            <div>
              <p class="text-sm text-blue-600 font-bold uppercase mb-1">Doanh thu trong kỳ</p>
              <p class="text-3xl font-black text-blue-900">{{ formatPrice(revenueReport.totalRevenue || 0) }}</p>
            </div>
            <div>
              <p class="text-sm text-blue-600 font-bold uppercase mb-1">Tổng số booking</p>
              <p class="text-3xl font-black text-blue-900">{{ revenueReport.totalBookings || 0 }}</p>
            </div>
            <div>
              <p class="text-sm text-blue-600 font-bold uppercase mb-1">Số hóa đơn đã xuất</p>
              <p class="text-3xl font-black text-blue-900">{{ revenueReport.invoiceCount || 0 }}</p>
            </div>
          </div>

          <div class="bg-white border border-gray-100 rounded-xl overflow-hidden">
            <h4 class="px-6 py-4 bg-gray-50 text-sm font-black text-gray-600 uppercase tracking-widest border-b border-gray-100">Chi tiết theo ngày</h4>
            <div class="overflow-x-auto">
              <table class="w-full text-left">
                <thead>
                  <tr class="border-b border-gray-100">
                    <th class="py-3 px-6 text-xs font-black text-gray-400 uppercase tracking-widest">Ngày</th>
                    <th class="py-3 px-6 text-xs font-black text-gray-400 uppercase tracking-widest">Số booking</th>
                    <th class="py-3 px-6 text-xs font-black text-gray-400 uppercase tracking-widest text-right">Doanh thu</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="day in revenueReport.details" :key="day.date" class="border-b border-gray-50 hover:bg-gray-50 transition-colors">
                    <td class="py-4 px-6 text-sm font-bold text-gray-700">{{ formatDate(day.date) }}</td>
                    <td class="py-4 px-6 text-sm text-gray-600">{{ day.bookings }}</td>
                    <td class="py-4 px-6 text-sm font-black text-emerald-700 text-right">{{ formatPrice(day.revenue) }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        </div>
      </div>

      <!-- Customer Messages Section (Admin & Staff) -->
      <div v-if="user?.role === 'ADMIN' || user?.role === 'STAFF'" class="bg-white rounded-xl shadow-lg overflow-hidden mb-10">
        <div class="bg-gradient-to-r from-purple-600 to-purple-700 text-white p-6">
          <h3 class="text-2xl font-bold flex items-center">
            <svg class="w-6 h-6 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"></path>
            </svg>
            Phản hồi Khách hàng
          </h3>
          <p class="text-purple-100 mt-1">Quản lý tin nhắn và yêu cầu từ khách hàng</p>
        </div>
        
        <div class="p-6">
          <div v-if="messages.length === 0" class="text-center py-16">
            <svg class="mx-auto h-16 w-16 text-gray-400 mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-2.586a1 1 0 00-.707.293l-2.414 2.414a1 1 0 01-.707.293h-3.172a1 1 0 01-.707-.293l-2.414-2.414A1 1 0 006.586 13H4"></path>
            </svg>
            <h3 class="text-lg font-medium text-gray-900 mb-2">Chưa có phản hồi nào</h3>
            <p class="text-gray-500">Chưa có tin nhắn từ khách hàng.</p>
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
      </div>

      <div v-if="user?.role !== 'ADMIN' && user?.role !== 'STAFF'" class="bg-white rounded-xl shadow-lg overflow-hidden">
        <div class="bg-gradient-to-r from-indigo-600 to-indigo-700 text-white p-8 text-center">
          <svg class="w-16 h-16 mx-auto mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"></path>
          </svg>
          <h3 class="text-3xl font-bold mb-2">Chào mừng đến với Khách sạn!</h3>
          <p class="text-indigo-100 text-lg">Hệ thống Quản lý Khách sạn hiện đại</p>
        </div>
        <div class="p-8">
          <p class="text-gray-600 text-center mb-8 text-lg">Khám phá các tiện ích và đặt phòng dễ dàng</p>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <router-link to="/rooms" class="group bg-gradient-to-r from-blue-50 to-blue-100 p-6 rounded-xl hover:shadow-lg transition-all duration-300 border border-blue-200">
              <div class="flex items-center mb-4">
                <svg class="w-8 h-8 text-blue-600 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4"></path>
                </svg>
                <h4 class="text-lg font-semibold text-gray-800">Xem phòng</h4>
              </div>
              <p class="text-gray-600">Khám phá danh sách phòng và tiện ích</p>
            </router-link>
            <router-link to="/bookings" class="group bg-gradient-to-r from-purple-50 to-purple-100 p-6 rounded-xl hover:shadow-lg transition-all duration-300 border border-purple-200">
              <div class="flex items-center mb-4">
                <svg class="w-8 h-8 text-purple-600 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"></path>
                </svg>
                <h4 class="text-lg font-semibold text-gray-800">Lịch sử đặt phòng</h4>
              </div>
              <p class="text-gray-600">Xem các đặt phòng của bạn</p>
            </router-link>
          </div>
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

const reportParams = ref({
  fromDate: new Date(new Date().setDate(new Date().getDate() - 30)).toISOString().split('T')[0],
  toDate: new Date().toISOString().split('T')[0]
})
const revenueReport = ref(null)

const fetchRevenueReport = async () => {
  if (user.value?.role === 'ADMIN') {
    try {
      const response = await axios.get(`/dashboard/reports/revenue?fromDate=${reportParams.value.fromDate}&toDate=${reportParams.value.toDate}`)
      revenueReport.value = response.data
    } catch (error) {
      console.error('Lỗi khi lấy báo cáo doanh thu:', error)
      alert('Không thể lấy báo cáo doanh thu.')
    }
  }
}

const formatPrice = (price) => {
  if (!price) return '0 đ'
  return `${new Intl.NumberFormat('vi-VN').format(price * 25000)} đ`
}

const formatNumber = (num) => {
  if (!num) return '0'
  return new Intl.NumberFormat('vi-VN').format(num)
}

const formatDate = (date) => {
  if (!date) return '--'
  return new Date(date).toLocaleDateString('vi-VN')
}

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
  if (user.value?.role === 'ADMIN') {
    fetchRevenueReport()
  }
})
</script>
