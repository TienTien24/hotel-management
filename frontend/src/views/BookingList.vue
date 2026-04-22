<template>
  <div class="min-h-screen bg-gray-100 py-8">
    <div class="max-w-7xl mx-auto px-4">
      <header class="flex justify-between items-center mb-10">
        <h2 class="text-3xl font-bold text-gray-800">Quản lý Đặt phòng</h2>
        <router-link to="/" class="text-blue-600 hover:underline">Quay lại Dashboard</router-link>
      </header>

      <div class="bg-white rounded-xl shadow-md overflow-hidden">
        <table class="min-w-full divide-y divide-gray-200">
          <thead class="bg-gray-50">
            <tr>
              <th class="px-6 py-3 text-left text-xs font-bold text-gray-500 uppercase tracking-wider">Mã đặt phòng</th>
              <th class="px-6 py-3 text-left text-xs font-bold text-gray-500 uppercase tracking-wider">Khách hàng</th>
              <th class="px-6 py-3 text-left text-xs font-bold text-gray-500 uppercase tracking-wider">Phòng</th>
              <th class="px-6 py-3 text-left text-xs font-bold text-gray-500 uppercase tracking-wider">Ngày đến - Ngày đi</th>
              <th class="px-6 py-3 text-left text-xs font-bold text-gray-500 uppercase tracking-wider">Tổng tiền</th>
              <th class="px-6 py-3 text-left text-xs font-bold text-gray-500 uppercase tracking-wider">Trạng thái</th>
              <th class="px-6 py-3 text-left text-xs font-bold text-gray-500 uppercase tracking-wider">Thao tác</th>
            </tr>
          </thead>
          <tbody class="bg-white divide-y divide-gray-200">
            <tr v-for="booking in bookings" :key="booking.id">
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900 font-bold">#{{ booking.id }}</td>
              <td class="px-6 py-4 whitespace-nowrap">
                <div class="text-sm text-gray-900">{{ booking.customer?.fullName }}</div>
                <div class="text-xs text-gray-500">{{ booking.customer?.phone }}</div>
              </td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">Phòng {{ booking.room?.roomNumber }}</td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                {{ booking.checkInDate }} - {{ booking.checkOutDate }}
              </td>
              <td class="px-6 py-4 whitespace-nowrap text-sm font-bold text-blue-600">${{ booking.totalPrice }}</td>
              <td class="px-6 py-4 whitespace-nowrap">
                <span :class="getStatusClass(booking.status)" class="px-3 py-1 text-xs font-bold rounded-full uppercase">
                  {{ booking.status }}
                </span>
              </td>
              <td class="px-6 py-4 whitespace-nowrap text-sm font-medium flex space-x-2">
                <button v-if="booking.status === 'PENDING' && (isAdmin || isStaff)" @click="confirmBooking(booking.id)" class="bg-blue-500 hover:bg-blue-600 text-white px-3 py-1 rounded">Xác nhận</button>
                <button v-if="booking.status === 'CONFIRMED' && (isAdmin || isStaff)" @click="checkIn(booking.id)" class="bg-green-500 hover:bg-green-600 text-white px-3 py-1 rounded">Check-in</button>
                <button v-if="booking.status === 'CONFIRMED' && booking.room?.status === 'OCCUPIED' && (isAdmin || isStaff)" @click="checkOut(booking.id)" class="bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded">Check-out</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import axios from '../api/axios'

const bookings = ref([])
const user = ref(JSON.parse(localStorage.getItem('user')))

const isAdmin = computed(() => user.value?.role === 'ADMIN')
const isStaff = computed(() => user.value?.role === 'STAFF')

const fetchBookings = async () => {
  try {
    let url = '/bookings'
    if (user.value.role === 'CUSTOMER') {
      url = `/bookings/customer/${user.value.id}`
    }
    const response = await axios.get(url)
    bookings.value = response.data
  } catch (error) {
    console.error('Lỗi khi lấy danh sách đặt phòng:', error)
  }
}

const confirmBooking = async (id) => {
  try {
    await axios.put(`/bookings/${id}/confirm`)
    fetchBookings()
  } catch (error) {
    alert('Lỗi khi xác nhận: ' + error.message)
  }
}

const checkIn = async (id) => {
  try {
    await axios.put(`/bookings/${id}/check-in`)
    fetchBookings()
  } catch (error) {
    alert('Lỗi khi check-in: ' + error.message)
  }
}

const checkOut = async (id) => {
  try {
    await axios.put(`/invoices/booking/${id}/check-out`)
    alert('Check-out thành công!')
    fetchBookings()
  } catch (error) {
    alert('Lỗi khi check-out: ' + error.message)
  }
}

const getStatusClass = (status) => {
  switch (status) {
    case 'PENDING': return 'bg-yellow-100 text-yellow-800'
    case 'CONFIRMED': return 'bg-blue-100 text-blue-800'
    case 'COMPLETED': return 'bg-green-100 text-green-800'
    case 'CANCELLED': return 'bg-red-100 text-red-800'
    default: return 'bg-gray-100 text-gray-800'
  }
}

onMounted(() => {
  fetchBookings()
})
</script>
