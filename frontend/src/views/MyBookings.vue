<template>
  <div class="min-h-screen bg-gray-100 pt-24 pb-10">
    <div class="max-w-6xl mx-auto px-4">
      <header class="flex justify-between items-center mb-10">
        <div>
          <h2 class="text-3xl font-black text-emerald-950">Lịch sử đặt phòng</h2>
          <p class="text-sm text-gray-500 mt-2">Xem và quản lý các đặt phòng của bạn</p>
        </div>
        <router-link to="/" class="text-emerald-700 font-bold hover:underline whitespace-nowrap">Quay lại Trang chủ</router-link>
      </header>

      <div v-if="loading" class="text-center py-20 text-gray-400 font-bold uppercase tracking-widest text-xs">
        Đang tải dữ liệu...
      </div>

      <div v-else-if="!bookings.length" class="bg-white rounded-[2rem] shadow-lg p-10 text-center">
        <p class="text-gray-500 text-lg">Bạn chưa có đặt phòng nào.</p>
        <router-link to="/rooms" class="inline-block mt-4 bg-emerald-800 text-white px-6 py-3 rounded-xl font-bold hover:bg-emerald-900">
          Đặt phòng ngay
        </router-link>
      </div>

      <div v-else class="space-y-6">
        <div 
          v-for="booking in bookings" 
          :key="booking.id"
          class="bg-white rounded-[2rem] shadow-lg p-6"
        >
          <div class="flex flex-col md:flex-row justify-between gap-4 mb-4">
            <div>
              <h3 class="text-xl font-black text-emerald-950">Booking #{{ booking.id }}</h3>
              <p class="text-sm text-gray-500 mt-1">Đặt ngày {{ formatDate(booking.createdAt) }}</p>
            </div>
            <span :class="getStatusClass(booking.status)" class="px-4 py-2 text-xs font-black rounded-full uppercase tracking-widest self-start md:self-auto">
              {{ formatStatus(booking.status) }}
            </span>
          </div>

          <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-6">
            <div>
              <p class="text-xs font-black text-gray-500 uppercase tracking-widest mb-1">Phòng</p>
              <p class="font-bold text-emerald-900">Phòng {{ booking.room?.roomNumber }} - {{ booking.room?.category }}</p>
              <p class="text-sm text-gray-500">{{ booking.room?.type }}</p>
            </div>
            <div>
              <p class="text-xs font-black text-gray-500 uppercase tracking-widest mb-1">Ngày ở</p>
              <p class="font-bold text-emerald-900">{{ booking.checkInDate }}</p>
              <p class="text-sm text-gray-500">đến {{ booking.checkOutDate }}</p>
            </div>
            <div>
              <p class="text-xs font-black text-gray-500 uppercase tracking-widest mb-1">Thanh toán</p>
              <p class="font-bold text-emerald-900">{{ formatPrice(booking.totalPrice) }}</p>
              <p class="text-sm text-gray-500">{{ booking.paymentMethod }} - {{ booking.paymentStatus }}</p>
            </div>
          </div>

          <div v-if="booking.status === 'COMPLETED' && !booking.reviewRating" class="border-t border-gray-100 pt-4">
            <button
              @click="openReviewModal(booking)"
              class="bg-emerald-800 hover:bg-emerald-900 text-white px-6 py-3 rounded-xl font-bold text-sm transition-all"
            >
              <i class="fas fa-star mr-2"></i> Đánh giá
            </button>
          </div>

          <!-- New section for Cancel and Edit buttons -->
          <div v-if="booking.status === 'PENDING' || booking.status === 'CONFIRMED'" class="border-t border-gray-100 pt-4 flex gap-2">
            <button
              v-if="booking.status === 'PENDING'"
              @click="editBooking(booking)"
              class="bg-blue-600 hover:bg-blue-700 text-white px-6 py-3 rounded-xl font-bold text-sm transition-all"
            >
              <i class="fas fa-edit mr-2"></i> Chỉnh sửa
            </button>
            <button
              @click="cancelBooking(booking)"
              class="bg-red-600 hover:bg-red-700 text-white px-6 py-3 rounded-xl font-bold text-sm transition-all"
            >
              <i class="fas fa-times-circle mr-2"></i> Hủy đăng ký
            </button>
          </div>

          <div v-if="booking.reviewRating" class="border-t border-gray-100 pt-4">
            <div class="flex items-center gap-2 mb-2">
              <p class="text-xs font-black text-gray-500 uppercase tracking-widest">Đánh giá của bạn:</p>
              <div class="flex text-yellow-400">
                <i v-for="n in 5" :key="n" :class="n <= booking.reviewRating ? 'fas' : 'far'" class="fa-star text-sm"></i>
              </div>
            </div>
            <p v-if="booking.reviewComment" class="text-gray-600 italic">"{{ booking.reviewComment }}"</p>
            <p class="text-xs text-gray-400 mt-1">{{ formatDateTime(booking.reviewCreatedAt) }}</p>
          </div>
        </div>
      </div>

      <div v-if="showReviewModal && selectedBooking" class="fixed inset-0 bg-black/50 flex items-center justify-center p-4 z-50">
        <div class="bg-white rounded-[2rem] shadow-2xl w-full max-w-md p-8">
          <div class="flex justify-between items-center mb-6">
            <h3 class="text-2xl font-black text-emerald-950">Đánh giá Booking #{{ selectedBooking.id }}</h3>
            <button @click="closeReviewModal" class="text-gray-400 hover:text-emerald-800">
              <i class="fas fa-times text-xl"></i>
            </button>
          </div>

          <div class="bg-gray-50 rounded-2xl p-4 mb-6">
            <p class="font-bold">{{ selectedBooking.room?.category }} - Phòng {{ selectedBooking.room?.roomNumber }}</p>
            <p class="text-sm text-gray-500">{{ selectedBooking.checkInDate }} - {{ selectedBooking.checkOutDate }}</p>
          </div>

          <div class="mb-6">
            <label class="block text-xs font-black text-gray-500 uppercase tracking-widest mb-3">Tiêu đề bài viết *</label>
            <input 
              v-model="reviewData.title" 
              type="text"
              placeholder="Ví dụ: Kỳ nghỉ tuyệt vời tại khách sạn..."
              class="w-full bg-gray-50 border-0 rounded-2xl px-4 py-3 focus:ring-2 focus:ring-emerald-800 outline-none"
            />
          </div>

          <div class="mb-6">
            <label class="block text-xs font-black text-gray-500 uppercase tracking-widest mb-3">Điểm đánh giá *</label>
            <div class="flex gap-2">
              <button 
                v-for="n in 5" 
                :key="n"
                @click="reviewData.rating = n"
                class="w-12 h-12 rounded-xl border-2 transition-all flex items-center justify-center text-xl"
                :class="n <= reviewData.rating ? 'border-yellow-400 bg-yellow-50 text-yellow-400' : 'border-gray-200 text-gray-300 hover:border-yellow-300'"
              >
                <i :class="n <= reviewData.rating ? 'fas' : 'far'" class="fa-star"></i>
              </button>
            </div>
          </div>

          <div class="mb-6">
            <label class="block text-xs font-black text-gray-500 uppercase tracking-widest mb-3">Nhận xét của bạn</label>
            <textarea 
              v-model="reviewData.comment" 
              rows="4"
              placeholder="Chia sẻ trải nghiệm của bạn..."
              class="w-full bg-gray-50 border-0 rounded-2xl px-4 py-3 focus:ring-2 focus:ring-emerald-800 outline-none"
            ></textarea>
          </div>

          <button 
            @click="submitReview"
            :disabled="submitting"
            class="w-full bg-emerald-800 hover:bg-emerald-900 disabled:bg-gray-400 text-white py-4 rounded-2xl font-bold uppercase tracking-widest text-sm transition-all"
          >
            {{ submitting ? 'Đang gửi...' : 'Gửi đánh giá' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from '../api/axios'

const bookings = ref([])
const loading = ref(true)
const showReviewModal = ref(false)
const selectedBooking = ref(null)
const submitting = ref(false)
const reviewData = ref({ rating: 0, comment: '' })

const formatPrice = (price) => {
  if (!price) return '0 đ'
  return `${new Intl.NumberFormat('vi-VN').format(price * 25000)} đ`
}

const formatDate = (date) => {
  if (!date) return '--'
  return new Date(date).toLocaleDateString('vi-VN')
}

const formatDateTime = (date) => {
  if (!date) return '--'
  return new Date(date).toLocaleString('vi-VN')
}

const formatStatus = (status) => {
  switch (status) {
    case 'PENDING': return 'Chờ xác nhận'
    case 'CONFIRMED': return 'Đã xác nhận'
    case 'CHECKED_IN': return 'Đang ở'
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

const fetchBookings = async () => {
  try {
    const user = JSON.parse(localStorage.getItem('user'))
    if (!user) {
      loading.value = false
      return
    }
    const response = await axios.get(`/bookings/customer/${user.id}`)
    bookings.value = response.data
  } catch (error) {
    console.error('Lỗi khi lấy danh sách đặt phòng:', error)
  } finally {
    loading.value = false
  }
}

const editBooking = (booking) => {
  alert(`Chỉnh sửa đặt phòng #${booking.id}`)
  // TODO: Implement actual edit functionality, e.g., navigate to an edit page
}

const cancelBooking = async (booking) => {
  if (confirm(`Bạn có chắc chắn muốn hủy đặt phòng #${booking.id} không?`)) {
    try {
      // Trying PUT instead of POST as it's more consistent with other status updates in this project
      await axios.put(`/bookings/${booking.id}/cancel`)
      alert('Đặt phòng đã được hủy thành công!')
      fetchBookings() // Refresh the bookings list
    } catch (error) {
      console.error('Lỗi khi hủy đặt phòng:', error)
      // Fallback: try POST if PUT fails, or just show error
      try {
        await axios.post(`/bookings/${booking.id}/cancel`)
        alert('Đặt phòng đã được hủy thành công!')
        fetchBookings()
      } catch (postError) {
        alert('Lỗi khi hủy đặt phòng: ' + (postError.response?.data?.message || 'Vui lòng thử lại sau'))
      }
    }
  }
}



const openReviewModal = (booking) => {
  selectedBooking.value = booking
  reviewData.value = { title: '', rating: 0, comment: '' }
  showReviewModal.value = true
}

const closeReviewModal = () => {
  showReviewModal.value = false
  selectedBooking.value = null
}

const submitReview = async () => {
  if (!reviewData.value.title) {
    alert('Vui lòng nhập tiêu đề bài viết')
    return
  }
  if (reviewData.value.rating === 0) {
    alert('Vui lòng chọn điểm đánh giá')
    return
  }

  try {
    submitting.value = true
    const user = JSON.parse(localStorage.getItem('user'))
    await axios.post(`/bookings/${selectedBooking.value.id}/review?customerId=${user.id}`, {
      title: reviewData.value.title,
      rating: reviewData.value.rating,
      comment: reviewData.value.comment
    })
    alert('Cảm ơn bạn đã đánh giá!')
    closeReviewModal()
    await fetchBookings()
  } catch (error) {
    alert('Lỗi khi gửi đánh giá: ' + (error.response?.data?.message || 'Vui lòng thử lại'))
  } finally {
    submitting.value = false
  }
}

onMounted(() => {
  fetchBookings()
})
</script>