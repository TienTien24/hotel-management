<template>
  <div class="min-h-screen bg-gray-100 py-8">
    <div class="max-w-7xl mx-auto px-4">
      <header class="flex flex-col md:flex-row justify-between md:items-center mb-10 gap-4">
        <div>
          <h2 class="text-3xl font-black text-emerald-950">Quản lý Check-in</h2>
          <p class="text-sm text-gray-500 mt-2">Xác nhận booking, check-in, check-out và lịch sử khách đã ở</p>
        </div>
        <router-link to="/dashboard" class="text-emerald-700 font-bold hover:underline">Quay lại Dashboard</router-link>
      </header>

      <div class="bg-white rounded-[2rem] shadow-lg p-6 mb-8">
        <h3 class="text-xl font-black text-emerald-950 mb-4">Bộ lọc</h3>
        <div class="grid grid-cols-1 md:grid-cols-4 gap-4">
          <div>
            <label class="block text-xs font-black uppercase tracking-widest text-gray-500 mb-2">Ngày Check-in</label>
            <input type="date" v-model="filters.checkInDate" class="w-full bg-gray-50 border-0 rounded-2xl px-4 py-3 outline-none focus:ring-2 focus:ring-emerald-800">
          </div>
          <div>
            <label class="block text-xs font-black uppercase tracking-widest text-gray-500 mb-2">Ngày Check-out</label>
            <input type="date" v-model="filters.checkOutDate" class="w-full bg-gray-50 border-0 rounded-2xl px-4 py-3 outline-none focus:ring-2 focus:ring-emerald-800">
          </div>
          <div>
            <label class="block text-xs font-black uppercase tracking-widest text-gray-500 mb-2">Khách hàng</label>
            <input type="text" v-model="filters.customerName" placeholder="Tìm theo tên..." class="w-full bg-gray-50 border-0 rounded-2xl px-4 py-3 outline-none focus:ring-2 focus:ring-emerald-800">
          </div>
          <div>
            <label class="block text-xs font-black uppercase tracking-widest text-gray-500 mb-2">Trạng thái</label>
            <select v-model="filters.status" class="w-full bg-gray-50 border-0 rounded-2xl px-4 py-3 outline-none focus:ring-2 focus:ring-emerald-800">
              <option value="">Tất cả</option>
              <option value="PENDING">Chờ xác nhận</option>
              <option value="CONFIRMED">Đã xác nhận</option>
              <option value="CHECKED_IN">Đã check-in</option>
              <option value="COMPLETED">Đã trả phòng</option>
              <option value="CANCELLED">Đã hủy</option>
            </select>
          </div>
        </div>
        <div class="mt-4 flex gap-3">
          <button @click="applyFilters" class="bg-emerald-800 text-white px-6 py-3 rounded-2xl font-bold text-sm hover:bg-emerald-900 transition-all">Áp dụng</button>
          <button @click="clearFilters" class="bg-gray-200 text-gray-700 px-6 py-3 rounded-2xl font-bold text-sm hover:bg-gray-300 transition-all">Xóa lọc</button>
        </div>
      </div>

      <div class="bg-white rounded-[2rem] shadow-lg overflow-hidden mb-10">
        <div class="px-8 py-6 border-b border-gray-100">
          <h3 class="text-2xl font-black text-emerald-950">Danh sách booking</h3>
        </div>
        <div class="overflow-x-auto">
          <table class="min-w-full divide-y divide-gray-200">
            <thead class="bg-gray-50">
              <tr>
                <th class="px-6 py-4 text-left text-xs font-black text-gray-500 uppercase tracking-wider">Mã</th>
                <th class="px-6 py-4 text-left text-xs font-black text-gray-500 uppercase tracking-wider">Khách hàng</th>
                <th class="px-6 py-4 text-left text-xs font-black text-gray-500 uppercase tracking-wider">Phòng</th>
                <th class="px-6 py-4 text-left text-xs font-black text-gray-500 uppercase tracking-wider">Lưu trú</th>
                <th class="px-6 py-4 text-left text-xs font-black text-gray-500 uppercase tracking-wider">Thanh toán</th>
                <th class="px-6 py-4 text-left text-xs font-black text-gray-500 uppercase tracking-wider">Trạng thái</th>
                <th class="px-6 py-4 text-left text-xs font-black text-gray-500 uppercase tracking-wider">Thao tác</th>
              </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
              <tr v-for="booking in filteredBookings" :key="booking.id">
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900 font-bold">#{{ booking.id }}</td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="text-sm text-gray-900 font-semibold">{{ booking.guestFullName || booking.customer?.fullName }}</div>
                  <div class="text-xs text-gray-500">{{ booking.guestPhone || booking.customer?.phone }}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">Phòng {{ booking.room?.roomNumber }} - {{ booking.room?.category }}</td>
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                  {{ booking.checkInDate }} - {{ booking.checkOutDate }}
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                  <div class="font-semibold">{{ booking.paymentMethod }}</div>
                  <div class="text-xs text-gray-500">{{ booking.paymentStatus }}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <span :class="getStatusClass(booking.status)" class="px-3 py-2 text-xs font-black rounded-full uppercase tracking-widest">
                    {{ formatStatus(booking.status) }}
                  </span>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                  <div class="flex flex-wrap gap-2">
                    <button v-if="booking.status === 'PENDING'" @click="openPaymentModal(booking)" class="bg-blue-500 hover:bg-blue-600 text-white px-3 py-2 rounded-xl text-xs font-bold">Thanh toán</button>
                    <button v-if="booking.status === 'PENDING'" @click="confirmBooking(booking.id)" class="bg-green-500 hover:bg-green-600 text-white px-3 py-2 rounded-xl text-xs font-bold">Xác nhận</button>
                    <button v-if="booking.status === 'CONFIRMED'" @click="checkIn(booking.id)" class="bg-purple-500 hover:bg-purple-600 text-white px-3 py-2 rounded-xl text-xs font-bold">Check-in</button>
                    <button v-if="booking.status === 'CHECKED_IN'" @click="checkOut(booking.id)" class="bg-red-500 hover:bg-red-600 text-white px-3 py-2 rounded-xl text-xs font-bold">Check-out</button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
          <div v-if="filteredBookings.length === 0" class="px-8 py-10 text-center text-sm text-gray-500">Không có booking nào phù hợp với bộ lọc.</div>
        </div>
      </div>

      <div class="bg-white rounded-[2rem] shadow-lg overflow-hidden">
        <div class="px-8 py-6 border-b border-gray-100">
          <h3 class="text-2xl font-black text-emerald-950">Lịch sử check-in khách hàng</h3>
          <p class="text-sm text-gray-500 mt-2">Hiển thị các khách đã ở và đã trả phòng trước đó</p>
        </div>
        <div v-if="!completedCheckins.length" class="px-8 py-10 text-sm text-gray-500 text-center">Chưa có lịch sử check-in hoàn tất.</div>
        <div v-else class="overflow-x-auto">
          <table class="min-w-full divide-y divide-gray-200">
            <thead class="bg-gray-50">
              <tr>
                <th class="px-6 py-4 text-left text-xs font-black text-gray-500 uppercase tracking-wider">Khách</th>
                <th class="px-6 py-4 text-left text-xs font-black text-gray-500 uppercase tracking-wider">Phòng</th>
                <th class="px-6 py-4 text-left text-xs font-black text-gray-500 uppercase tracking-wider">Check-in</th>
                <th class="px-6 py-4 text-left text-xs font-black text-gray-500 uppercase tracking-wider">Check-out</th>
                <th class="px-6 py-4 text-left text-xs font-black text-gray-500 uppercase tracking-wider">Đánh giá</th>
              </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
              <tr v-for="booking in completedCheckins" :key="booking.id">
                <td class="px-6 py-4">
                  <div class="text-sm font-semibold text-gray-900">{{ booking.guestFullName || booking.customer?.fullName }}</div>
                  <div class="text-xs text-gray-500">{{ booking.guestPhone || booking.customer?.phone }}</div>
                </td>
                <td class="px-6 py-4 text-sm text-gray-900">Phòng {{ booking.room?.roomNumber }} - {{ booking.room?.category }}</td>
                <td class="px-6 py-4 text-sm text-gray-900">{{ formatDateTime(booking.checkedInAt) }}</td>
                <td class="px-6 py-4 text-sm text-gray-900">{{ formatDateTime(booking.checkedOutAt) }}</td>
                <td class="px-6 py-4 text-sm text-gray-900">{{ booking.reviewRating ? `${booking.reviewRating}/5` : 'Chưa đánh giá' }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <div v-if="showPaymentModal && selectedBooking" class="fixed inset-0 bg-black/50 flex items-center justify-center p-4 z-50">
        <div class="bg-white rounded-[2rem] shadow-2xl w-full max-w-lg p-8">
          <div class="flex items-center justify-between mb-6">
            <h3 class="text-2xl font-black text-emerald-950">Thanh toán Booking #{{ selectedBooking.id }}</h3>
            <button @click="closePaymentModal" class="text-gray-400 hover:text-emerald-800">
              <i class="fas fa-times text-xl"></i>
            </button>
          </div>

          <div class="bg-gray-50 rounded-2xl p-4 mb-6">
            <div class="flex justify-between mb-2">
              <span class="text-gray-500">Phòng:</span>
              <span class="font-bold">{{ selectedBooking.room?.roomNumber }}</span>
            </div>
            <div class="flex justify-between mb-2">
              <span class="text-gray-500">Khách hàng:</span>
              <span class="font-bold">{{ selectedBooking.guestFullName || selectedBooking.customer?.fullName }}</span>
            </div>
            <div class="flex justify-between">
              <span class="text-gray-500">Tổng tiền:</span>
              <span class="font-bold text-emerald-700">{{ formatPrice(selectedBooking.totalPrice) }}</span>
            </div>
          </div>

          <div class="space-y-3">
            <button @click="payWithVnpay(selectedBooking.id)" class="w-full bg-red-600 hover:bg-red-700 text-white py-4 rounded-2xl font-bold uppercase tracking-widest text-sm flex items-center justify-center gap-3 transition-all">
              <img src="https://images.vnpayment.vn/merchant/logo/merchant-logo.png" alt="VNPay" class="h-6 object-contain">
              Thanh toán qua VNPay
            </button>
            <button @click="payWithMomo(selectedBooking.id)" class="w-full bg-pink-600 hover:bg-pink-700 text-white py-4 rounded-2xl font-bold uppercase tracking-widest text-sm flex items-center justify-center gap-3 transition-all">
              <span class="text-xl font-black">M</span>
              Thanh toán qua Momo
            </button>
            <button @click="payWithCod(selectedBooking.id)" class="w-full bg-gray-600 hover:bg-gray-700 text-white py-4 rounded-2xl font-bold uppercase tracking-widest text-sm transition-all">
              Thanh toán COD (Tiền mặt)
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import axios from '../api/axios'

const route = useRoute()
const bookings = ref([])
const completedCheckins = ref([])
const showPaymentModal = ref(false)
const selectedBooking = ref(null)

const filters = ref({
  checkInDate: '',
  checkOutDate: '',
  customerName: '',
  status: ''
})

const formatPrice = (price) => {
  if (!price) return '0 đ'
  return `${new Intl.NumberFormat('vi-VN').format(price * 25000)} đ`
}

const formatStatus = (status) => {
  switch (status) {
    case 'PENDING': return 'Chờ xác nhận'
    case 'CONFIRMED': return 'Đã xác nhận'
    case 'CHECKED_IN': return 'Đã check-in'
    case 'COMPLETED': return 'Đã trả phòng'
    case 'CANCELLED': return 'Đã hủy'
    default: return status
  }
}

const getStatusClass = (status) => {
  switch (status) {
    case 'PENDING': return 'bg-yellow-100 text-yellow-800'
    case 'CONFIRMED': return 'bg-blue-100 text-blue-800'
    case 'CHECKED_IN': return 'bg-purple-100 text-purple-800'
    case 'COMPLETED': return 'bg-green-100 text-green-800'
    case 'CANCELLED': return 'bg-red-100 text-red-800'
    default: return 'bg-gray-100 text-gray-800'
  }
}

const formatDateTime = (value) => {
  if (!value) return '--'
  return new Date(value).toLocaleString('vi-VN')
}

const filteredBookings = computed(() => {
  return bookings.value.filter(booking => {
    if (filters.value.status && booking.status !== filters.value.status) return false
    if (filters.value.customerName) {
      const name = (booking.guestFullName || booking.customer?.fullName || '').toLowerCase()
      if (!name.includes(filters.value.customerName.toLowerCase())) return false
    }
    if (filters.value.checkInDate && booking.checkInDate !== filters.value.checkInDate) return false
    if (filters.value.checkOutDate && booking.checkOutDate !== filters.value.checkOutDate) return false
    return true
  })
})

const fetchBookings = async () => {
  try {
    const response = await axios.get('/bookings')
    bookings.value = response.data
  } catch (error) {
    console.error('Lỗi khi lấy danh sách đặt phòng:', error)
  }
}

const fetchCompletedCheckins = async () => {
  try {
    const response = await axios.get('/bookings/completed-checkins')
    completedCheckins.value = response.data
  } catch (error) {
    console.error('Lỗi khi lấy lịch sử check-in:', error)
  }
}

const refreshAll = async () => {
  await fetchBookings()
  await fetchCompletedCheckins()
}

const applyFilters = () => {}

const clearFilters = () => {
  filters.value = { checkInDate: '', checkOutDate: '', customerName: '', status: '' }
}

const confirmBooking = async (id) => {
  try {
    await axios.put(`/bookings/${id}/confirm`)
    await refreshAll()
  } catch (error) {
    alert('Lỗi khi xác nhận booking')
  }
}

const checkIn = async (id) => {
  try {
    await axios.put(`/bookings/${id}/check-in`)
    alert('Check-in thành công!')
    await refreshAll()
  } catch (error) {
    alert('Lỗi khi check-in')
  }
}

const checkOut = async (id) => {
  try {
    await axios.put(`/invoices/booking/${id}/check-out`)
    alert('Check-out thành công!')
    await refreshAll()
  } catch (error) {
    alert('Lỗi khi check-out')
  }
}

const openPaymentModal = (booking) => {
  selectedBooking.value = booking
  showPaymentModal.value = true
}

const closePaymentModal = () => {
  showPaymentModal.value = false
  selectedBooking.value = null
}

const payWithVnpay = async (bookingId) => {
  try {
    const response = await axios.post(`/payments/vnpay/${bookingId}`)
    window.location.href = response.data.paymentUrl
  } catch (error) {
    alert('Lỗi khi tạo thanh toán VNPay')
  }
}

const payWithMomo = async (bookingId) => {
  try {
    const response = await axios.post(`/payments/momo/${bookingId}`)
    window.location.href = response.data.payUrl
  } catch (error) {
    alert('Lỗi khi tạo thanh toán Momo')
  }
}

const payWithCod = async (bookingId) => {
  try {
    await axios.put(`/bookings/${bookingId}/confirm`)
    alert('Xác nhận thanh toán COD thành công!')
    closePaymentModal()
    await refreshAll()
  } catch (error) {
    alert('Lỗi khi xác nhận thanh toán COD')
  }
}

onMounted(() => {
  refreshAll()

  const paymentStatus = route.query.payment
  if (paymentStatus === 'success') {
    alert('Thanh toán thành công!')
  } else if (paymentStatus === 'failed') {
    alert('Thanh toán thất bại. Vui lòng thử lại.')
  }
})
</script>