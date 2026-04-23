<template>
  <div class="min-h-screen bg-gray-100 py-8">
    <div class="max-w-7xl mx-auto px-4">
      <header class="flex flex-col md:flex-row justify-between md:items-center mb-10 gap-4">
        <div>
          <h2 class="text-3xl font-black text-emerald-950">{{ isCustomer ? 'Đặt phòng của tôi' : 'Quản lý Booking & Check-in' }}</h2>
          <p class="text-sm text-gray-500 mt-2">
            {{ isCustomer ? 'Theo dõi booking, thanh toán và đánh giá sau khi trả phòng' : 'Quản lý xác nhận booking, check-in, check-out và lịch sử khách đã ở' }}
          </p>
        </div>
        <router-link to="/" class="text-emerald-700 font-bold hover:underline">Quay lại Dashboard</router-link>
      </header>

      <template v-if="isCustomer">
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
          <div v-for="booking in bookings" :key="booking.id" class="bg-white rounded-[2rem] shadow-lg overflow-hidden border border-gray-100">
            <img :src="booking.room?.imageUrl || 'https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=800&q=80'" class="w-full h-56 object-cover">
            <div class="p-8">
              <div class="flex items-start justify-between gap-4 mb-5">
                <div>
                  <h3 class="text-2xl font-black text-emerald-950">Phòng {{ booking.room?.roomNumber }}</h3>
                  <p class="text-sm text-gray-500 mt-2">{{ booking.room?.category }} - {{ booking.room?.type }}</p>
                </div>
                <span :class="getStatusClass(booking.status)" class="px-4 py-2 rounded-full text-xs font-black uppercase tracking-widest">
                  {{ formatStatus(booking.status) }}
                </span>
              </div>

              <div class="grid grid-cols-2 gap-4 text-sm text-gray-600 mb-6">
                <div class="bg-gray-50 rounded-2xl p-4">
                  <div class="text-xs font-black uppercase tracking-widest text-gray-400">Check-in</div>
                  <div class="mt-2 font-bold">{{ booking.checkInDate }}</div>
                </div>
                <div class="bg-gray-50 rounded-2xl p-4">
                  <div class="text-xs font-black uppercase tracking-widest text-gray-400">Check-out</div>
                  <div class="mt-2 font-bold">{{ booking.checkOutDate }}</div>
                </div>
                <div class="bg-gray-50 rounded-2xl p-4">
                  <div class="text-xs font-black uppercase tracking-widest text-gray-400">Thanh toán</div>
                  <div class="mt-2 font-bold">{{ booking.paymentMethod }} / {{ booking.paymentStatus }}</div>
                </div>
                <div class="bg-gray-50 rounded-2xl p-4">
                  <div class="text-xs font-black uppercase tracking-widest text-gray-400">Tổng tiền</div>
                  <div class="mt-2 font-bold text-emerald-700">{{ formatPrice(booking.totalPrice) }}</div>
                </div>
              </div>

              <div class="text-sm text-gray-600 space-y-2 mb-6">
                <p><span class="font-bold text-gray-800">Khách đặt:</span> {{ booking.guestFullName || booking.customer?.fullName }}</p>
                <p><span class="font-bold text-gray-800">Số điện thoại:</span> {{ booking.guestPhone || booking.customer?.phone }}</p>
                <p><span class="font-bold text-gray-800">Số người:</span> {{ booking.numberOfGuests }}</p>
              </div>

              <div v-if="booking.reviewRating" class="bg-emerald-50 border border-emerald-100 rounded-2xl p-4 mb-5">
                <div class="flex items-center justify-between">
                  <span class="text-xs font-black uppercase tracking-widest text-emerald-700">Đánh giá của bạn</span>
                  <span class="font-black text-emerald-800">{{ booking.reviewRating }}/5</span>
                </div>
                <p class="text-sm text-gray-700 mt-3">{{ booking.reviewComment || 'Không có nhận xét thêm' }}</p>
              </div>

              <button
                v-if="booking.status === 'COMPLETED' && !booking.reviewRating"
                @click="openReviewModal(booking)"
                class="w-full bg-emerald-800 text-white py-4 rounded-2xl font-black uppercase tracking-widest text-xs hover:bg-emerald-900 transition-all"
              >
                Đánh giá sau lưu trú
              </button>
            </div>
          </div>
        </div>
      </template>

      <template v-else>
        <div class="bg-white rounded-[2rem] shadow-lg overflow-hidden mb-10">
          <div class="px-8 py-6 border-b border-gray-100">
            <h3 class="text-2xl font-black text-emerald-950">Danh sách booking hiện tại</h3>
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
                <tr v-for="booking in bookings" :key="booking.id">
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
                    <div class="flex gap-2">
                      <button v-if="booking.status === 'PENDING'" @click="confirmBooking(booking.id)" class="bg-blue-500 hover:bg-blue-600 text-white px-3 py-2 rounded-xl text-xs font-bold">Xác nhận</button>
                      <button v-if="booking.status === 'CONFIRMED'" @click="checkIn(booking.id)" class="bg-green-500 hover:bg-green-600 text-white px-3 py-2 rounded-xl text-xs font-bold">Check-in</button>
                      <button v-if="booking.status === 'CHECKED_IN'" @click="checkOut(booking.id)" class="bg-red-500 hover:bg-red-600 text-white px-3 py-2 rounded-xl text-xs font-bold">Check-out</button>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <div class="bg-white rounded-[2rem] shadow-lg overflow-hidden">
          <div class="px-8 py-6 border-b border-gray-100">
            <h3 class="text-2xl font-black text-emerald-950">Lịch sử check-in khách hàng</h3>
            <p class="text-sm text-gray-500 mt-2">Hiển thị các khách đã ở và đã trả phòng trước đó</p>
          </div>
          <div v-if="!completedCheckins.length" class="px-8 py-10 text-sm text-gray-500">Chưa có lịch sử check-in hoàn tất.</div>
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
      </template>

      <div v-if="showReviewModal && selectedBooking" class="fixed inset-0 bg-black/50 flex items-center justify-center p-4 z-50">
        <div class="bg-white rounded-[2rem] shadow-2xl w-full max-w-xl p-8">
          <div class="flex items-center justify-between mb-6">
            <h3 class="text-2xl font-black text-emerald-950">Đánh giá lưu trú</h3>
            <button @click="closeReviewModal" class="text-gray-400 hover:text-emerald-800">
              <i class="fas fa-times text-xl"></i>
            </button>
          </div>

          <form @submit.prevent="submitReview" class="space-y-5">
            <div>
              <label class="block text-xs font-black uppercase tracking-widest text-emerald-900 mb-3">Số sao</label>
              <select v-model.number="reviewForm.rating" class="w-full bg-gray-50 border-0 rounded-2xl px-5 py-4 outline-none focus:ring-2 focus:ring-emerald-800">
                <option :value="5">5 sao</option>
                <option :value="4">4 sao</option>
                <option :value="3">3 sao</option>
                <option :value="2">2 sao</option>
                <option :value="1">1 sao</option>
              </select>
            </div>
            <div>
              <label class="block text-xs font-black uppercase tracking-widest text-emerald-900 mb-3">Nhận xét</label>
              <textarea v-model="reviewForm.comment" rows="5" class="w-full bg-gray-50 border-0 rounded-2xl px-5 py-4 outline-none focus:ring-2 focus:ring-emerald-800"></textarea>
            </div>
            <button type="submit" class="w-full bg-emerald-800 text-white py-4 rounded-2xl font-black uppercase tracking-widest text-xs hover:bg-emerald-900 transition-all">
              Gửi đánh giá
            </button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import axios from '../api/axios'

const user = ref(JSON.parse(localStorage.getItem('user') || 'null'))
const bookings = ref([])
const completedCheckins = ref([])
const showReviewModal = ref(false)
const selectedBooking = ref(null)
const reviewForm = ref({
  rating: 5,
  comment: ''
})

const isAdmin = computed(() => user.value?.role === 'ADMIN')
const isStaff = computed(() => user.value?.role === 'STAFF')
const isCustomer = computed(() => user.value?.role === 'CUSTOMER')

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

const fetchBookings = async () => {
  try {
    const url = isCustomer.value ? `/bookings/customer/${user.value.id}` : '/bookings'
    const response = await axios.get(url)
    bookings.value = response.data
  } catch (error) {
    console.error('Lỗi khi lấy danh sách đặt phòng:', error)
  }
}

const fetchCompletedCheckins = async () => {
  if (isCustomer.value) return
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

const openReviewModal = (booking) => {
  selectedBooking.value = booking
  reviewForm.value = { rating: 5, comment: '' }
  showReviewModal.value = true
}

const closeReviewModal = () => {
  showReviewModal.value = false
  selectedBooking.value = null
}

const submitReview = async () => {
  try {
    await axios.post(`/bookings/${selectedBooking.value.id}/review`, reviewForm.value, {
      params: { customerId: user.value.id }
    })
    alert('Gửi đánh giá thành công!')
    closeReviewModal()
    await refreshAll()
  } catch (error) {
    alert('Không thể gửi đánh giá')
  }
}

onMounted(() => {
  refreshAll()
})
</script>
