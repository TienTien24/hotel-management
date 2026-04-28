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

      <template v-else-if="isAdmin || isStaff">
        <div class="bg-white rounded-[2rem] shadow-lg p-6 mb-8 border border-gray-100">
          <h3 class="text-xl font-black text-emerald-950 mb-4 uppercase tracking-tight">Bộ lọc tìm kiếm</h3>
          <div class="grid grid-cols-1 md:grid-cols-4 gap-4">
            <div>
              <label class="block text-xs font-black uppercase tracking-widest text-gray-500 mb-2">Ngày Check-in</label>
              <input type="date" v-model="filters.checkInDate" class="w-full bg-gray-50 border-0 rounded-2xl px-4 py-3 outline-none focus:ring-2 focus:ring-emerald-800 text-sm font-bold">
            </div>
            <div>
              <label class="block text-xs font-black uppercase tracking-widest text-gray-500 mb-2">Ngày Check-out</label>
              <input type="date" v-model="filters.checkOutDate" class="w-full bg-gray-50 border-0 rounded-2xl px-4 py-3 outline-none focus:ring-2 focus:ring-emerald-800 text-sm font-bold">
            </div>
            <div>
              <label class="block text-xs font-black uppercase tracking-widest text-gray-500 mb-2">Khách hàng</label>
              <input type="text" v-model="filters.customerName" placeholder="Tìm theo tên..." class="w-full bg-gray-50 border-0 rounded-2xl px-4 py-3 outline-none focus:ring-2 focus:ring-emerald-800 text-sm font-bold">
            </div>
            <div>
              <label class="block text-xs font-black uppercase tracking-widest text-gray-500 mb-2">Trạng thái</label>
              <select v-model="filters.status" class="w-full bg-gray-50 border-0 rounded-2xl px-4 py-3 outline-none focus:ring-2 focus:ring-emerald-800 text-sm font-bold">
                <option value="">Tất cả trạng thái</option>
                <option value="PENDING">Chờ xác nhận</option>
                <option value="CONFIRMED">Đã xác nhận</option>
                <option value="CHECKED_IN">Đã check-in</option>
                <option value="COMPLETED">Đã trả phòng</option>
                <option value="CANCELLED">Đã hủy</option>
              </select>
            </div>
          </div>
          <div class="mt-4 flex gap-3">
            <button @click="applyFilters" class="bg-emerald-800 text-white px-8 py-3 rounded-2xl font-black uppercase tracking-widest text-xs hover:bg-emerald-900 transition-all shadow-lg">Lọc kết quả</button>
            <button @click="clearFilters" class="bg-gray-100 text-gray-500 px-8 py-3 rounded-2xl font-black uppercase tracking-widest text-xs hover:bg-gray-200 transition-all">Xóa bộ lọc</button>
          </div>
        </div>

        <div class="bg-white rounded-[2rem] shadow-lg overflow-hidden mb-10 border border-gray-100">
          <div class="px-8 py-6 border-b border-gray-100 flex justify-between items-center">
            <h3 class="text-2xl font-black text-emerald-950 uppercase tracking-tight">Danh sách booking</h3>
            <span class="bg-emerald-50 text-emerald-700 px-4 py-1.5 rounded-full text-xs font-black uppercase tracking-widest">
              {{ filteredBookings.length }} kết quả
            </span>
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
                  <th class="px-6 py-4 text-left text-xs font-black text-gray-500 uppercase tracking-wider text-center">Thao tác</th>
                </tr>
              </thead>
              <tbody class="bg-white divide-y divide-gray-200">
                <tr v-for="booking in filteredBookings" :key="booking.id" class="hover:bg-gray-50 transition-colors">
                  <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900 font-bold">#{{ booking.id }}</td>
                  <td class="px-6 py-4 whitespace-nowrap">
                    <div class="text-sm text-gray-900 font-black uppercase tracking-tight">{{ booking.guestFullName || booking.customer?.fullName }}</div>
                    <div class="text-xs text-gray-500 font-bold mt-1">{{ booking.guestPhone || booking.customer?.phone }}</div>
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap">
                    <div class="text-sm text-emerald-900 font-black uppercase tracking-tight">Phòng {{ booking.room?.roomNumber }}</div>
                    <div class="text-[10px] text-gray-400 font-black uppercase tracking-widest mt-1">{{ booking.room?.category }}</div>
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap">
                    <div class="text-xs text-gray-900 font-black">{{ booking.checkInDate }}</div>
                    <div class="text-[10px] text-gray-400 font-bold">đến {{ booking.checkOutDate }}</div>
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap">
                    <div class="text-xs font-black text-emerald-800 uppercase tracking-widest">{{ booking.paymentMethod }}</div>
                    <div class="text-[10px] font-black uppercase tracking-widest mt-1" :class="booking.paymentStatus === 'PAID' ? 'text-green-500' : 'text-red-400'">
                      {{ booking.paymentStatus }}
                    </div>
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap">
                    <span :class="getStatusClass(booking.status)" class="px-3 py-1.5 text-[10px] font-black rounded-full uppercase tracking-widest">
                      {{ formatStatus(booking.status) }}
                    </span>
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                    <div class="flex flex-wrap justify-center gap-2">
                      <button v-if="booking.status === 'PENDING'" @click="openPaymentModal(booking)" class="bg-amber-500 hover:bg-amber-600 text-white px-4 py-2 rounded-xl text-[10px] font-black uppercase tracking-widest shadow-sm">Thanh toán</button>
                      <button v-if="booking.status === 'PENDING'" @click="confirmBooking(booking.id)" class="bg-emerald-600 hover:bg-emerald-700 text-white px-4 py-2 rounded-xl text-[10px] font-black uppercase tracking-widest shadow-sm">Xác nhận</button>
                      <button v-if="booking.status === 'PENDING' || booking.status === 'CONFIRMED'" @click="checkIn(booking.id)" class="bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded-xl text-[10px] font-black uppercase tracking-widest shadow-sm">Check-in</button>
                      
                      <button v-if="booking.status === 'CHECKED_IN'" @click="openServiceModal(booking)" class="bg-sky-500 hover:bg-sky-600 text-white px-4 py-2 rounded-xl text-[10px] font-black uppercase tracking-widest shadow-sm">Dịch vụ</button>
                      <button v-if="booking.status === 'CHECKED_IN'" @click="viewInvoice(booking.id)" class="bg-gray-100 text-gray-700 hover:bg-gray-200 px-4 py-2 rounded-xl text-[10px] font-black uppercase tracking-widest shadow-sm">Tạm tính</button>
                      <button v-if="booking.status === 'CHECKED_IN'" @click="checkOut(booking.id)" class="bg-rose-500 hover:bg-rose-600 text-white px-4 py-2 rounded-xl text-[10px] font-black uppercase tracking-widest shadow-sm">Check-out</button>
                      
                      <button v-if="booking.status === 'COMPLETED'" @click="viewInvoice(booking.id)" class="bg-emerald-100 text-emerald-700 hover:bg-emerald-200 px-4 py-2 rounded-xl text-[10px] font-black uppercase tracking-widest shadow-sm">Hóa đơn</button>
                      <button v-if="['PENDING', 'CONFIRMED', 'CHECKED_IN'].includes(booking.status) && (isAdmin || isStaff || booking.status !== 'CHECKED_IN')" @click="cancelBooking(booking.id)" class="bg-white border border-red-100 text-red-500 hover:bg-red-50 px-4 py-2 rounded-xl text-[10px] font-black uppercase tracking-widest shadow-sm">Hủy</button>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
            <div v-if="filteredBookings.length === 0" class="px-8 py-12 text-center">
              <div class="text-gray-400 mb-2 font-black uppercase tracking-widest text-xs">Không tìm thấy dữ liệu</div>
              <p class="text-sm text-gray-400">Vui lòng điều chỉnh lại bộ lọc tìm kiếm.</p>
            </div>
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
                  <th class="px-6 py-4 text-left text-xs font-black text-gray-500 uppercase tracking-wider">Thao tác</th>
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
                  <td class="px-6 py-4 text-sm text-gray-900">
                    <div v-if="booking.reviewRating" class="flex items-center text-amber-500 font-bold">
                      <i class="fas fa-star mr-1"></i> {{ booking.reviewRating }}/5
                    </div>
                    <span v-else class="text-gray-400 italic">Chưa đánh giá</span>
                  </td>
                  <td class="px-6 py-4 text-sm font-medium">
                    <button @click="viewInvoice(booking.id)" class="text-purple-600 hover:text-purple-900 font-bold">Xem hóa đơn</button>
                  </td>
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

      <!-- Invoice Modal -->
      <div v-if="showInvoiceModal && selectedInvoice" class="fixed inset-0 bg-black/50 flex items-center justify-center p-4 z-50">
        <div class="bg-white rounded-[2rem] shadow-2xl w-full max-w-md p-8 border-t-8 border-emerald-800">
          <div class="flex justify-between items-start mb-8">
            <div>
              <h3 class="text-3xl font-black text-emerald-950 uppercase tracking-tighter">Hóa đơn</h3>
              <p class="text-xs font-bold text-gray-400 mt-1 uppercase tracking-widest">Mã hóa đơn: #{{ selectedInvoice.id }}</p>
            </div>
            <button @click="showInvoiceModal = false" class="text-gray-400 hover:text-emerald-800">
              <i class="fas fa-times text-xl"></i>
            </button>
          </div>

          <div class="space-y-6 mb-10">
            <div class="flex justify-between border-b border-gray-100 pb-3">
              <span class="text-xs font-black text-gray-400 uppercase tracking-widest">Mã đặt phòng</span>
              <span class="font-bold text-emerald-950">#{{ selectedInvoice.booking?.id }}</span>
            </div>
            <div class="flex justify-between border-b border-gray-100 pb-3">
              <span class="text-xs font-black text-gray-400 uppercase tracking-widest">Ngày thanh toán</span>
              <span class="font-bold text-emerald-950">{{ formatDateTime(selectedInvoice.paymentDate) }}</span>
            </div>
            <div class="flex justify-between border-b border-gray-100 pb-3">
              <span class="text-xs font-black text-gray-400 uppercase tracking-widest">Trạng thái</span>
              <span class="px-3 py-1 bg-emerald-100 text-emerald-700 rounded-full text-[10px] font-black uppercase tracking-widest">
                {{ selectedInvoice.paymentStatus }}
              </span>
            </div>
            <div class="space-y-3 pt-2">
              <div class="flex justify-between text-sm">
                <span class="text-gray-500">Tiền phòng</span>
                <span class="font-semibold text-gray-800">{{ formatPrice(selectedInvoice.roomCharges) }}</span>
              </div>
              <div class="flex justify-between text-sm">
                <span class="text-gray-500">Phí dịch vụ</span>
                <span class="font-semibold text-gray-800">{{ formatPrice(selectedInvoice.serviceCharges) }}</span>
              </div>
              
              <!-- Service breakdown -->
              <div v-if="selectedUsages.length > 0" class="pl-4 border-l-2 border-gray-100 space-y-1">
                <div v-for="usage in selectedUsages" :key="usage.id" class="flex justify-between text-[10px] text-gray-400">
                  <span>{{ usage.service.name }} (x{{ usage.quantity }})</span>
                  <span>{{ formatPrice(usage.service.price * usage.quantity) }}</span>
                </div>
              </div>

              <div class="flex justify-between items-center pt-4 border-t-2 border-emerald-50">
                <span class="text-lg font-black text-emerald-950 uppercase tracking-widest">Tổng cộng</span>
                <span class="text-2xl font-black text-emerald-800">{{ formatPrice(selectedInvoice.totalAmount) }}</span>
              </div>
            </div>
          </div>

          <button @click="window.print()" class="w-full bg-emerald-800 text-white py-4 rounded-2xl font-black uppercase tracking-widest text-xs hover:bg-emerald-900 transition-all flex items-center justify-center gap-2">
            <i class="fas fa-print"></i> In hóa đơn
          </button>
        </div>
      </div>

      <!-- Service Selection Modal -->
      <div v-if="showServiceModal" class="fixed inset-0 bg-black/50 flex items-center justify-center p-4 z-50">
        <div class="bg-white rounded-[2rem] shadow-2xl w-full max-w-md p-8">
          <div class="flex items-center justify-between mb-6">
            <h3 class="text-2xl font-black text-emerald-950">Thêm dịch vụ - #{{ selectedBooking?.id }}</h3>
            <button @click="showServiceModal = false" class="text-gray-400 hover:text-emerald-800">
              <i class="fas fa-times text-xl"></i>
            </button>
          </div>

          <form @submit.prevent="addService" class="space-y-5">
            <div>
              <label class="block text-xs font-black uppercase tracking-widest text-emerald-900 mb-3">Chọn dịch vụ</label>
              <select v-model="serviceForm.serviceId" class="w-full bg-gray-50 border-0 rounded-2xl px-5 py-4 outline-none focus:ring-2 focus:ring-emerald-800">
                <option v-for="service in availableServices" :key="service.id" :value="service.id">
                  {{ service.name }} - {{ formatPrice(service.price) }}
                </option>
              </select>
            </div>
            <div>
              <label class="block text-xs font-black uppercase tracking-widest text-emerald-900 mb-3">Số lượng</label>
              <input v-model.number="serviceForm.quantity" type="number" min="1" class="w-full bg-gray-50 border-0 rounded-2xl px-5 py-4 outline-none focus:ring-2 focus:ring-emerald-800">
            </div>
            <button type="submit" class="w-full bg-amber-500 text-white py-4 rounded-2xl font-black uppercase tracking-widest text-xs hover:bg-amber-600 transition-all">
              Thêm vào hóa đơn
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
const showInvoiceModal = ref(false)
const selectedInvoice = ref(null)
const selectedUsages = ref([])
const showServiceModal = ref(false)
const availableServices = ref([])
const showPaymentModal = ref(false)
const selectedBooking = ref(null)

const filters = ref({
  checkInDate: '',
  checkOutDate: '',
  customerName: '',
  status: ''
})

const filteredBookings = computed(() => {
  if (isCustomer.value) return bookings.value
  
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

const applyFilters = () => {}

const clearFilters = () => {
  filters.value = { checkInDate: '', checkOutDate: '', customerName: '', status: '' }
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
    alert(error.response?.data || 'Lỗi khi tạo thanh toán VNPay')
  }
}

const payWithMomo = async (bookingId) => {
  try {
    const response = await axios.post(`/payments/momo/${bookingId}`)
    window.location.href = response.data.payUrl
  } catch (error) {
    alert(error.response?.data || 'Lỗi khi tạo thanh toán Momo')
  }
}

const payWithCod = async (bookingId) => {
  try {
    await axios.put(`/bookings/${bookingId}/confirm`)
    alert('Xác nhận thanh toán COD thành công!')
    closePaymentModal()
    await refreshAll()
  } catch (error) {
    alert(error.response?.data || 'Lỗi khi xác nhận thanh toán COD')
  }
}

const serviceForm = ref({
  serviceId: null,
  quantity: 1
})

const fetchServices = async () => {
  try {
    const response = await axios.get('/hotel-services')
    availableServices.value = response.data
    if (availableServices.value.length > 0) {
      serviceForm.value.serviceId = availableServices.value[0].id
    }
  } catch (error) {
    console.error('Lỗi khi lấy danh sách dịch vụ:', error)
  }
}

const openServiceModal = (booking) => {
  selectedBooking.value = booking
  serviceForm.value.quantity = 1
  showServiceModal.value = true
}

const addService = async () => {
  try {
    await axios.post('/hotel-services/add-to-booking', null, {
      params: {
        bookingId: selectedBooking.value.id,
        serviceId: serviceForm.value.serviceId,
        quantity: serviceForm.value.quantity
      }
    })
    alert('Đã thêm dịch vụ thành công!')
    showServiceModal.value = false
    await refreshAll()
  } catch (error) {
    console.error('Lỗi khi thêm dịch vụ:', error)
    alert('Không thể thêm dịch vụ. Vui lòng thử lại.')
  }
}

const viewInvoice = async (bookingId) => {
  try {
    const [invoiceRes, usagesRes] = await Promise.all([
      axios.get(`/invoices/booking/${bookingId}`),
      axios.get(`/hotel-services/booking/${bookingId}`)
    ])
    selectedInvoice.value = invoiceRes.data
    selectedUsages.value = usagesRes.data
    showInvoiceModal.value = true
  } catch (error) {
    console.error('Lỗi khi lấy hóa đơn:', error)
    alert('Hóa đơn chưa được tạo hoặc không tìm thấy.')
  }
}

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
    alert('Xác nhận booking thành công!')
    await refreshAll()
  } catch (error) {
    console.error('Lỗi xác nhận:', error)
    const serverMsg = error.response?.data
    const message = typeof serverMsg === 'string' ? serverMsg : (serverMsg?.message || error.message || 'Lỗi không xác định')
    alert('Lỗi khi xác nhận booking: ' + message)
  }
}

const checkIn = async (id) => {
  if (!id) {
    alert('Lỗi: ID booking không hợp lệ')
    return
  }
  try {
    console.log('Bắt đầu check-in cho ID:', id)
    const response = await axios.put(`/bookings/${id}/check-in`)
    console.log('Kết quả check-in:', response.data)
    alert('Check-in thành công!')
    await refreshAll()
  } catch (error) {
    console.error('Lỗi check-in chi tiết:', error)
    const serverData = error.response?.data
    let message = 'Lỗi không xác định'
    
    if (serverData) {
      if (typeof serverData === 'string') {
        message = serverData
      } else if (serverData.message) {
        message = serverData.message
      }
    } else {
      message = error.message
    }
    
    alert('Lỗi khi check-in: ' + message)
  }
}

const checkOut = async (id) => {
  try {
    await axios.put(`/invoices/booking/${id}/check-out`)
    alert('Check-out thành công!')
    await refreshAll()
  } catch (error) {
    console.error('Lỗi check-out:', error)
    const serverMsg = error.response?.data
    const message = typeof serverMsg === 'string' ? serverMsg : (serverMsg?.message || error.message || 'Lỗi không xác định')
    alert('Lỗi khi check-out: ' + message)
  }
}

const cancelBooking = async (id) => {
  if (!confirm('Bạn có chắc chắn muốn hủy đặt phòng này?')) return
  try {
    await axios.put(`/bookings/${id}/cancel`, null, {
      params: {
        userId: user.value.id,
        isAdmin: !isCustomer.value
      }
    })
    alert('Hủy đặt phòng thành công!')
    await refreshAll()
  } catch (error) {
    alert(error.response?.data || 'Lỗi khi hủy đặt phòng')
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
  fetchServices()
})
</script>
