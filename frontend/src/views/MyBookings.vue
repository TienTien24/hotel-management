<template>
  <div class="min-h-screen bg-gray-50 pt-24 pb-20 font-sans">
    <div class="max-w-6xl mx-auto px-6">
      <!-- Header -->
      <header class="flex flex-col md:flex-row justify-between items-start md:items-center gap-8 mb-12">
        <div>
          <h2 class="text-4xl font-black text-emerald-950 uppercase tracking-tighter leading-none">Lịch sử đặt phòng</h2>
          <p class="text-sm text-gray-400 font-bold mt-2 uppercase tracking-widest">Xem và quản lý các đặt phòng của bạn</p>
        </div>
        <router-link to="/" class="group flex items-center gap-3 px-6 py-3 bg-white border-2 border-gray-100 rounded-2xl text-xs font-black text-gray-500 uppercase tracking-widest hover:border-emerald-800 hover:text-emerald-800 transition-all shadow-sm">
          <i class="fas fa-arrow-left text-[10px] group-hover:-translate-x-1 transition-transform"></i>
          Quay lại Trang chủ
        </router-link>
      </header>

      <!-- Loading State -->
      <div v-if="loading" class="flex flex-col items-center justify-center py-32 space-y-4">
        <div class="w-12 h-12 border-4 border-emerald-100 border-t-emerald-800 rounded-full animate-spin"></div>
        <p class="text-xs font-black text-gray-400 uppercase tracking-widest">Đang tải dữ liệu...</p>
      </div>

      <!-- Empty State -->
      <div v-else-if="!bookings.length" class="text-center py-32 bg-white rounded-[3rem] shadow-xl shadow-emerald-900/5 border-2 border-dashed border-gray-100">
        <div class="w-20 h-20 bg-emerald-50 rounded-full flex items-center justify-center mx-auto mb-6">
          <i class="fas fa-calendar-times text-3xl text-emerald-200"></i>
        </div>
        <p class="text-lg font-black text-emerald-950 uppercase tracking-tight">Bạn chưa có đặt phòng nào</p>
        <router-link to="/rooms" class="inline-flex items-center gap-3 mt-8 bg-[#004d26] text-white px-10 py-4 rounded-2xl font-black uppercase tracking-widest text-xs hover:bg-[#003d1e] shadow-xl shadow-green-900/20 transition-all">
          Đặt phòng ngay
          <i class="fas fa-arrow-right"></i>
        </router-link>
      </div>

      <!-- Bookings List -->
      <div v-else class="space-y-8">
        <div 
          v-for="booking in bookings" 
          :key="booking.id"
          class="bg-white rounded-[2.5rem] shadow-xl shadow-emerald-900/5 border border-gray-100 p-8 md:p-10 transition-all duration-500 hover:shadow-2xl hover:border-emerald-100 group"
        >
          <!-- Booking Header -->
          <div class="flex flex-col md:flex-row justify-between gap-8 mb-10 pb-8 border-b border-gray-50">
            <div class="flex items-start gap-6">
              <div class="w-14 h-14 bg-emerald-50 rounded-2xl flex items-center justify-center text-emerald-800 text-xl shadow-inner shrink-0">
                <i class="far fa-calendar-check"></i>
              </div>
              <div>
                <h3 class="text-2xl font-black text-emerald-950 uppercase tracking-tighter">Booking #{{ booking.id }}</h3>
                <p class="text-xs text-gray-400 font-bold mt-1 uppercase tracking-widest italic">Đặt ngày {{ formatDate(booking.createdAt) }}</p>
              </div>
            </div>
            <div class="flex items-center">
              <span :class="getStatusClass(booking.status)" class="px-6 py-2.5 text-[10px] font-black rounded-full uppercase tracking-[0.2em] shadow-sm">
                {{ formatStatus(booking.status) }}
              </span>
            </div>
          </div>

          <!-- Booking Details Grid -->
          <div class="grid grid-cols-1 md:grid-cols-3 gap-10 mb-10">
            <!-- Room Info -->
            <div class="space-y-4">
              <p class="text-[10px] font-black text-gray-400 uppercase tracking-widest">Phòng</p>
              <div>
                <p class="text-lg font-black text-emerald-950 uppercase tracking-tight">Phòng {{ booking.room?.roomNumber }} - {{ booking.room?.category }}</p>
                <p class="text-sm text-gray-500 font-medium mt-1">{{ booking.room?.type }}</p>
              </div>
            </div>

            <!-- Date Info -->
            <div class="space-y-4 border-gray-50 md:border-l md:pl-10">
              <p class="text-[10px] font-black text-gray-400 uppercase tracking-widest">Ngày ở</p>
              <div>
                <p class="text-lg font-black text-emerald-950 uppercase tracking-tight">{{ booking.checkInDate }}</p>
                <p class="text-sm text-gray-500 font-medium mt-1">đến {{ booking.checkOutDate }}</p>
              </div>
            </div>

            <!-- Payment Info -->
            <div class="space-y-4 border-gray-50 md:border-l md:pl-10">
              <p class="text-[10px] font-black text-gray-400 uppercase tracking-widest">Thanh toán</p>
              <div>
                <p class="text-2xl font-black text-emerald-800 leading-none">{{ formatPrice(booking.totalPrice) }}</p>
                <p class="text-[10px] text-gray-400 font-bold mt-2 uppercase tracking-widest">
                  {{ booking.paymentMethod }} - {{ booking.paymentStatus }}
                </p>
              </div>
            </div>
          </div>

          <!-- Actions -->
          <div v-if="booking.status === 'PENDING' || booking.status === 'CONFIRMED'" class="flex flex-wrap gap-4 pt-8 border-t border-gray-50">
            <button
              class="flex items-center gap-3 px-8 py-4 bg-emerald-50 text-emerald-800 rounded-2xl text-[11px] font-black uppercase tracking-widest hover:bg-emerald-800 hover:text-white transition-all shadow-sm"
            >
              <i class="fas fa-file-invoice"></i>
              Chi tiết đặt phòng
            </button>
            <button
              @click="cancelBooking(booking)"
              class="flex items-center gap-3 px-8 py-4 bg-rose-50 text-rose-600 rounded-2xl text-[11px] font-black uppercase tracking-widest hover:bg-rose-600 hover:text-white transition-all shadow-sm"
            >
              <i class="fas fa-times-circle"></i>
              Hủy đăng ký
            </button>
          </div>

          <!-- Review Section -->
          <div v-if="booking.reviewRating" class="mt-8 pt-8 border-t border-gray-50">
            <div class="bg-gray-50 rounded-[2rem] p-8 border border-gray-100 relative">
              <div class="flex items-center gap-4 mb-4">
                <p class="text-[10px] font-black text-gray-400 uppercase tracking-widest">Đánh giá của bạn:</p>
                <div class="flex text-amber-400 gap-1">
                  <i v-for="n in 5" :key="n" :class="n <= booking.reviewRating ? 'fas' : 'far'" class="fa-star text-[10px]"></i>
                </div>
              </div>
              <p v-if="booking.reviewComment" class="text-sm text-slate-700 font-bold italic leading-relaxed">"{{ booking.reviewComment }}"</p>
              <p class="text-[10px] text-gray-400 font-bold mt-4 uppercase tracking-widest italic">{{ formatDateTime(booking.reviewCreatedAt) }}</p>
              
              <!-- Quote Icon Decor -->
              <div class="absolute right-8 top-8 opacity-5 text-4xl text-emerald-950">
                <i class="fas fa-quote-right"></i>
              </div>
            </div>
          </div>

          <!-- Add Review Button -->
          <div v-if="booking.status === 'COMPLETED' && !booking.reviewRating" class="pt-8 border-t border-gray-50">
            <button
              @click="openReviewModal(booking)"
              class="flex items-center gap-3 px-10 py-4 bg-amber-400 text-emerald-950 rounded-2xl text-[11px] font-black uppercase tracking-widest hover:bg-emerald-950 hover:text-white transition-all shadow-xl shadow-amber-400/20"
            >
              <i class="fas fa-star"></i>
              Gửi đánh giá trải nghiệm
            </button>
          </div>
        </div>
      </div>

      <!-- Bottom Help Bar -->
      <div class="mt-16 bg-white rounded-[2.5rem] p-8 border border-gray-100 shadow-xl shadow-emerald-900/5 flex flex-col sm:flex-row items-center justify-center gap-8">
        <div class="flex items-center gap-4">
          <div class="w-10 h-10 bg-gray-50 rounded-full flex items-center justify-center text-gray-400">
            <i class="fas fa-info-circle"></i>
          </div>
          <div>
            <p class="text-sm font-black text-emerald-950 uppercase tracking-tight">Không tìm thấy đặt phòng?</p>
            <p class="text-[10px] text-gray-400 font-bold uppercase tracking-widest mt-1">Vui lòng liên hệ với chúng tôi để được hỗ trợ.</p>
          </div>
        </div>
        <router-link to="/contact" class="px-8 py-3 bg-[#004d26] text-white rounded-xl text-[10px] font-black uppercase tracking-widest hover:bg-[#003d1e] transition-all shadow-lg shadow-green-900/20">
          Liên hệ hỗ trợ
        </router-link>
      </div>

      <!-- Review Modal -->
      <div v-if="showReviewModal && selectedBooking" class="fixed inset-0 bg-emerald-950/80 backdrop-blur-sm flex items-center justify-center p-4 z-50">
        <div class="bg-white rounded-[3rem] shadow-2xl w-full max-w-lg p-10 md:p-12 relative overflow-hidden">
          <div class="flex justify-between items-center mb-10">
            <h3 class="text-3xl font-black text-emerald-950 uppercase tracking-tighter">Đánh giá kỳ nghỉ</h3>
            <button @click="closeReviewModal" class="text-gray-400 hover:text-emerald-800 transition-colors">
              <i class="fas fa-times text-2xl"></i>
            </button>
          </div>

          <div class="bg-gray-50 rounded-[2rem] p-6 mb-10 border border-gray-100">
            <p class="text-xs font-black text-gray-400 uppercase tracking-widest mb-2">Thông tin đặt phòng</p>
            <p class="text-lg font-black text-emerald-950 uppercase tracking-tight">{{ selectedBooking.room?.category }} - Phòng {{ selectedBooking.room?.roomNumber }}</p>
            <p class="text-xs text-gray-500 font-bold mt-1 uppercase tracking-widest italic">{{ selectedBooking.checkInDate }} - {{ selectedBooking.checkOutDate }}</p>
          </div>

          <div class="space-y-8">
            <div class="space-y-3">
              <label class="text-[10px] font-black text-slate-400 uppercase tracking-widest">Tiêu đề bài viết *</label>
              <input 
                v-model="reviewData.title" 
                type="text"
                placeholder="Ví dụ: Kỳ nghỉ tuyệt vời tại khách sạn..."
                class="w-full bg-gray-50 border-2 border-gray-50 rounded-2xl px-6 py-4 focus:bg-white focus:border-[#004d26] transition-all font-bold text-sm text-slate-700 outline-none"
              />
            </div>

            <div class="space-y-3">
              <label class="text-[10px] font-black text-slate-400 uppercase tracking-widest">Điểm đánh giá *</label>
              <div class="flex gap-3">
                <button 
                  v-for="n in 5" 
                  :key="n"
                  @click="reviewData.rating = n"
                  class="w-14 h-14 rounded-2xl border-2 transition-all flex items-center justify-center text-xl"
                  :class="n <= reviewData.rating ? 'border-amber-400 bg-amber-50 text-amber-400 shadow-lg shadow-amber-400/10' : 'border-gray-50 bg-gray-50 text-gray-200 hover:border-amber-200'"
                >
                  <i :class="n <= reviewData.rating ? 'fas' : 'far'" class="fa-star"></i>
                </button>
              </div>
            </div>

            <div class="space-y-3">
              <label class="text-[10px] font-black text-slate-400 uppercase tracking-widest">Nhận xét của bạn</label>
              <textarea 
                v-model="reviewData.comment" 
                rows="4"
                placeholder="Chia sẻ trải nghiệm chân thực của bạn..."
                class="w-full bg-gray-50 border-2 border-gray-50 rounded-2xl px-6 py-4 focus:bg-white focus:border-[#004d26] transition-all font-bold text-sm text-slate-700 outline-none resize-none"
              ></textarea>
            </div>

            <button 
              @click="submitReview"
              :disabled="submitting"
              class="w-full bg-[#004d26] text-white py-6 rounded-2xl font-black uppercase tracking-[0.2em] text-xs hover:bg-[#003d1e] shadow-2xl shadow-green-900/30 transition-all flex items-center justify-center gap-4 group disabled:opacity-50"
            >
              <i class="fas fa-paper-plane group-hover:translate-x-1 group-hover:-translate-y-1 transition-transform"></i>
              {{ submitting ? 'ĐANG GỬI...' : 'GỬI ĐÁNH GIÁ' }}
            </button>
          </div>
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
const reviewData = ref({ title: '', rating: 0, comment: '' })

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
  const d = new Date(date)
  const time = d.toLocaleTimeString('vi-VN', { hour: '2-digit', minute: '2-digit' })
  const day = d.toLocaleDateString('vi-VN', { day: '2-digit', month: '2-digit', year: 'numeric' })
  return `${time} ${day}`
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
    case 'PENDING': return 'bg-amber-50 text-amber-600'
    case 'CONFIRMED': return 'bg-blue-50 text-blue-600'
    case 'CHECKED_IN': return 'bg-purple-50 text-purple-600'
    case 'COMPLETED': return 'bg-emerald-50 text-emerald-600'
    case 'CANCELLED': return 'bg-rose-50 text-rose-600'
    default: return 'bg-gray-50 text-gray-400'
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
    bookings.value = response.data.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
  } catch (error) {
    console.error('Lỗi khi lấy danh sách đặt phòng:', error)
  } finally {
    loading.value = false
  }
}

const cancelBooking = async (booking) => {
  const user = JSON.parse(localStorage.getItem('user'))
  if (!user) return

  const checkInDate = new Date(booking.checkInDate)
  const now = new Date()
  const diffTime = checkInDate - now
  const diffHours = diffTime / (1000 * 60 * 60)
  const refundPercentage = diffHours < 24 ? 50 : 100
  const cancellationFee = booking.totalPrice * (1 - refundPercentage / 100)

  let policyText = ''
  if (diffHours < 0) {
    alert('Không thể hủy booking sau ngày check-in.')
    return
  } else if (diffHours < 24) {
    policyText = `\n\n📋 Chính sách hủy phòng:\n- Hủy trong 24h trước check-in: Hoàn ${refundPercentage}%\n- Phí hủy: ${formatPrice(cancellationFee)}`
  } else {
    policyText = `\n\n📋 Chính sách hủy phòng:\n- Hủy trước 24h: Hoàn ${refundPercentage}%\n- Phí hủy: Không có`
  }

  const confirmMessage = `Bạn có chắc chắn muốn hủy đặt phòng #${booking.id} không?${policyText}`

  if (confirm(confirmMessage)) {
    try {
      const isAdmin = user.role === 'ADMIN'
      await axios.put(`/bookings/${booking.id}/cancel?userId=${user.id}&isAdmin=${isAdmin}`)
      alert('Đặt phòng đã được hủy thành công!')
      fetchBookings()
    } catch (error) {
      console.error('Lỗi khi hủy đặt phòng:', error)
      alert('Lỗi khi hủy đặt phòng: ' + (error.response?.data?.message || 'Vui lòng thử lại sau'))
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
    console.error('Lỗi khi gửi đánh giá:', error)
    alert('Có lỗi xảy ra khi gửi đánh giá. Vui lòng thử lại sau.')
  } finally {
    submitting.value = false
  }
}

onMounted(() => {
  fetchBookings()
  window.scrollTo(0, 0)
})
</script>

<style scoped>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css');

.animate-fade-in {
  animation: fadeIn 0.5s ease-out forwards;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>