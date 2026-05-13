<template>
  <div class="min-h-screen bg-gray-50 pt-24 pb-20 font-sans">
    <div class="max-w-6xl mx-auto px-6">
      <!-- Header -->
      <header class="flex flex-col md:flex-row justify-between items-start md:items-center gap-8 mb-12">
        <div>
          <h2 class="text-4xl font-black text-emerald-950 uppercase tracking-tighter leading-none">Tài khoản của bạn</h2>
          <p class="text-sm text-gray-400 font-bold mt-2 uppercase tracking-widest">Quản lý thông tin cá nhân và đặt phòng</p>
        </div>
        <router-link to="/" class="group flex items-center gap-3 px-6 py-3 bg-white border-2 border-gray-100 rounded-2xl text-xs font-black text-gray-500 uppercase tracking-widest hover:border-emerald-800 hover:text-emerald-800 transition-all shadow-sm">
          <i class="fas fa-arrow-left text-[10px] group-hover:-translate-x-1 transition-transform"></i>
          Quay lại Trang chủ
        </router-link>
      </header>

      <!-- Tabs -->
      <div class="flex gap-4 mb-10">
        <button 
          @click="activeTab = 'profile'" 
          :class="[
            'px-8 py-4 rounded-2xl font-black uppercase tracking-[0.2em] text-xs transition-all shadow-lg',
            activeTab === 'profile' 
              ? 'bg-[#004d26] text-white shadow-green-900/20' 
              : 'bg-white text-gray-400 hover:text-emerald-800 hover:border-emerald-100 border-2 border-gray-100'
          ]"
        >
          <i class="fas fa-user mr-2"></i>
          Quản lý tài khoản
        </button>
        <button 
          @click="activeTab = 'bookings'" 
          :class="[
            'px-8 py-4 rounded-2xl font-black uppercase tracking-[0.2em] text-xs transition-all shadow-lg',
            activeTab === 'bookings' 
              ? 'bg-[#004d26] text-white shadow-green-900/20' 
              : 'bg-white text-gray-400 hover:text-emerald-800 hover:border-emerald-100 border-2 border-gray-100'
          ]"
        >
          <i class="fas fa-calendar mr-2"></i>
          Đặt phòng của tôi
        </button>
      </div>

      <!-- Profile Tab Content -->
      <div v-if="activeTab === 'profile'">
        <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
          <div class="lg:col-span-2 space-y-8">
            <div class="bg-white rounded-[2.5rem] shadow-xl shadow-emerald-900/5 border border-gray-100 p-8 md:p-10">
              <h3 class="text-2xl font-black text-emerald-950 uppercase tracking-tighter mb-8">Thông tin cá nhân</h3>
              
              <form @submit.prevent="handleUpdateProfile" class="space-y-6">
                <div class="flex items-center gap-8 mb-8">
                  <div class="relative group">
                    <div class="w-32 h-32 bg-emerald-50 rounded-full flex items-center justify-center text-emerald-800 text-5xl shadow-inner shrink-0 overflow-hidden">
                      <img v-if="editProfile.avatar" :src="editProfile.avatar" alt="Avatar" class="w-full h-full object-cover" />
                      <img v-else-if="profile.avatar" :src="profile.avatar" alt="Avatar" class="w-full h-full object-cover" />
                      <i v-else class="fas fa-user"></i>
                    </div>
                    <label class="absolute inset-0 bg-black/40 rounded-full flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity cursor-pointer">
                      <div class="flex flex-col items-center text-white">
                        <i class="fas fa-camera text-2xl mb-1"></i>
                        <span class="text-[10px] font-black uppercase tracking-widest">Thay đổi</span>
                      </div>
                      <input 
                        type="file"
                        accept="image/*"
                        capture="environment"
                        @change="handleAvatarUpload"
                        class="hidden"
                      />
                    </label>
                  </div>
                  <div class="flex-1">
                    <p class="text-[10px] font-black text-gray-400 uppercase tracking-widest">Ảnh đại diện</p>
                  </div>
                </div>

                <div class="space-y-3">
                  <label class="text-[10px] font-black text-gray-400 uppercase tracking-widest">Họ và tên</label>
                  <input 
                    v-model="editProfile.fullName" 
                    type="text"
                    class="w-full bg-gray-50 border-2 border-gray-50 rounded-2xl px-6 py-4 focus:bg-white focus:border-[#004d26] transition-all font-bold text-sm text-slate-700 outline-none"
                  />
                </div>

                <div class="space-y-3">
                  <label class="text-[10px] font-black text-gray-400 uppercase tracking-widest">Email</label>
                  <input 
                    :value="profile.email" 
                    type="email"
                    disabled
                    class="w-full bg-gray-100 border-2 border-gray-100 rounded-2xl px-6 py-4 font-bold text-sm text-gray-400 outline-none cursor-not-allowed"
                  />
                  <p class="text-[10px] text-gray-400 font-bold uppercase tracking-widest">Email không thể thay đổi</p>
                </div>

                <div class="space-y-3">
                  <label class="text-[10px] font-black text-gray-400 uppercase tracking-widest">Số điện thoại</label>
                  <input 
                    v-model="editProfile.phone" 
                    type="tel"
                    class="w-full bg-gray-50 border-2 border-gray-50 rounded-2xl px-6 py-4 focus:bg-white focus:border-[#004d26] transition-all font-bold text-sm text-slate-700 outline-none"
                  />
                </div>

                <button 
                  type="submit"
                  :disabled="updatingProfile"
                  class="w-full bg-[#004d26] text-white py-6 rounded-2xl font-black uppercase tracking-[0.2em] text-xs hover:bg-[#003d1e] shadow-2xl shadow-green-900/30 transition-all flex items-center justify-center gap-4 group disabled:opacity-50"
                >
                  <i class="fas fa-save group-hover:scale-110 transition-transform"></i>
                  {{ updatingProfile ? 'ĐANG CẬP NHẬT...' : 'CẬP NHẬT THÔNG TIN' }}
                </button>
              </form>
            </div>
          </div>

          <div class="space-y-8">
            <div class="bg-white rounded-[2.5rem] shadow-xl shadow-emerald-900/5 border border-gray-100 p-8 md:p-10">
              <h3 class="text-2xl font-black text-emerald-950 uppercase tracking-tighter mb-8">Đổi mật khẩu</h3>
              
              <form @submit.prevent="handleChangePassword" class="space-y-6">
                <div class="space-y-3">
                  <label class="text-[10px] font-black text-gray-400 uppercase tracking-widest">Mật khẩu hiện tại</label>
                  <input 
                    v-model="passwordData.currentPassword" 
                    type="password"
                    class="w-full bg-gray-50 border-2 border-gray-50 rounded-2xl px-6 py-4 focus:bg-white focus:border-[#004d26] transition-all font-bold text-sm text-slate-700 outline-none"
                  />
                </div>

                <div class="space-y-3">
                  <label class="text-[10px] font-black text-gray-400 uppercase tracking-widest">Mật khẩu mới</label>
                  <input 
                    v-model="passwordData.newPassword" 
                    type="password"
                    minlength="6"
                    class="w-full bg-gray-50 border-2 border-gray-50 rounded-2xl px-6 py-4 focus:bg-white focus:border-[#004d26] transition-all font-bold text-sm text-slate-700 outline-none"
                  />
                  <p class="text-[10px] text-gray-400 font-bold uppercase tracking-widest">Tối thiểu 6 ký tự</p>
                </div>

                <div class="space-y-3">
                  <label class="text-[10px] font-black text-gray-400 uppercase tracking-widest">Xác nhận mật khẩu</label>
                  <input 
                    v-model="passwordData.confirmPassword" 
                    type="password"
                    class="w-full bg-gray-50 border-2 border-gray-50 rounded-2xl px-6 py-4 focus:bg-white focus:border-[#004d26] transition-all font-bold text-sm text-slate-700 outline-none"
                  />
                </div>

                <button 
                  type="submit"
                  :disabled="changingPassword"
                  class="w-full bg-emerald-800 text-white py-6 rounded-2xl font-black uppercase tracking-[0.2em] text-xs hover:bg-emerald-900 shadow-xl shadow-emerald-800/20 transition-all flex items-center justify-center gap-4 group disabled:opacity-50"
                >
                  <i class="fas fa-key group-hover:rotate-12 transition-transform"></i>
                  {{ changingPassword ? 'ĐANG ĐỔI MẬT KHẨU...' : 'ĐỔI MẬT KHẨU' }}
                </button>
              </form>
            </div>

            <div class="bg-emerald-50 rounded-[2.5rem] p-8 border border-emerald-100">
              <div class="flex items-center gap-4 mb-4">
                <div class="w-10 h-10 bg-emerald-100 rounded-full flex items-center justify-center text-emerald-800">
                  <i class="fas fa-shield-alt"></i>
                </div>
                <div>
                  <p class="text-sm font-black text-emerald-950 uppercase tracking-tight">Bảo mật tài khoản</p>
                  <p class="text-[10px] text-emerald-600 font-bold uppercase tracking-widest mt-1">Luôn giữ mật khẩu an toàn</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Bookings Tab Content -->
      <div v-if="activeTab === 'bookings'">
        <div v-if="bookingsLoading" class="flex flex-col items-center justify-center py-32 space-y-4">
          <div class="w-12 h-12 border-4 border-emerald-100 border-t-emerald-800 rounded-full animate-spin"></div>
          <p class="text-xs font-black text-gray-400 uppercase tracking-widest">Đang tải dữ liệu...</p>
        </div>

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

        <div v-else class="space-y-8">
          <div 
            v-for="booking in bookings" 
            :key="booking.id"
            class="bg-white rounded-[2.5rem] shadow-xl shadow-emerald-900/5 border border-gray-100 p-8 md:p-10 transition-all duration-500 hover:shadow-2xl hover:border-emerald-100 group"
          >
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

            <div class="grid grid-cols-1 md:grid-cols-3 gap-10 mb-10">
              <div class="space-y-4">
                <p class="text-[10px] font-black text-gray-400 uppercase tracking-widest">Phòng</p>
                <div>
                  <p class="text-lg font-black text-emerald-950 uppercase tracking-tight">Phòng {{ booking.room?.roomNumber }} - {{ booking.room?.category }}</p>
                  <p class="text-sm text-gray-500 font-medium mt-1">{{ booking.room?.type }}</p>
                </div>
              </div>

              <div class="space-y-4 border-gray-50 md:border-l md:pl-10">
                <p class="text-[10px] font-black text-gray-400 uppercase tracking-widest">Ngày ở</p>
                <div>
                  <p class="text-lg font-black text-emerald-950 uppercase tracking-tight">{{ booking.checkInDate }}</p>
                  <p class="text-sm text-gray-500 font-medium mt-1">đến {{ booking.checkOutDate }}</p>
                </div>
              </div>

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

            <div v-if="booking.status === 'PENDING' || booking.status === 'CONFIRMED'" class="flex flex-wrap gap-4 pt-8 border-t border-gray-50">
              <button class="flex items-center gap-3 px-8 py-4 bg-emerald-50 text-emerald-800 rounded-2xl text-[11px] font-black uppercase tracking-widest hover:bg-emerald-800 hover:text-white transition-all shadow-sm">
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
                
                <div class="absolute right-8 top-8 opacity-5 text-4xl text-emerald-950">
                  <i class="fas fa-quote-right"></i>
                </div>
              </div>
            </div>

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
              :disabled="submittingReview"
              class="w-full bg-[#004d26] text-white py-6 rounded-2xl font-black uppercase tracking-[0.2em] text-xs hover:bg-[#003d1e] shadow-2xl shadow-green-900/30 transition-all flex items-center justify-center gap-4 group disabled:opacity-50"
            >
              <i class="fas fa-paper-plane group-hover:translate-x-1 group-hover:-translate-y-1 transition-transform"></i>
              {{ submittingReview ? 'ĐANG GỬI...' : 'GỬI ĐÁNH GIÁ' }}
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

const activeTab = ref('profile')

const loading = ref(true)
const bookingsLoading = ref(true)
const updatingProfile = ref(false)
const changingPassword = ref(false)
const profile = ref({})
const editProfile = ref({})
const passwordData = ref({
  currentPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const bookings = ref([])
const showReviewModal = ref(false)
const selectedBooking = ref(null)
const submittingReview = ref(false)
const reviewData = ref({ title: '', rating: 0, comment: '' })

const fetchProfile = async () => {
  try {
    console.log('Fetching profile...')
    const response = await axios.get('/auth/profile')
    console.log('Profile response:', response.data)
    profile.value = response.data
    editProfile.value = {
      fullName: response.data.fullName || '',
      phone: response.data.phone || '',
      avatar: response.data.avatar || ''
    }
  } catch (error) {
    console.error('Lỗi khi lấy thông tin hồ sơ:', error)
    console.error('Error details:', error.response?.data)
  } finally {
    loading.value = false
  }
}

const handleAvatarUpload = (event) => {
  const file = event.target.files[0]
  if (file) {
    const reader = new FileReader()
    reader.onload = (e) => {
      editProfile.value.avatar = e.target.result
    }
    reader.readAsDataURL(file)
  }
}

const handleUpdateProfile = async () => {
  try {
    updatingProfile.value = true
    const response = await axios.put('/auth/profile', editProfile.value)
    profile.value = response.data
    alert('Cập nhật thông tin thành công!')
  } catch (error) {
    console.error('Lỗi khi cập nhật hồ sơ:', error)
    let errorMessage = 'Vui lòng thử lại sau'
    if (error.response?.data) {
      if (typeof error.response.data === 'string') {
        errorMessage = error.response.data
      } else if (error.response.data.message) {
        errorMessage = error.response.data.message
      }
    }
    alert('Lỗi khi cập nhật thông tin: ' + errorMessage)
  } finally {
    updatingProfile.value = false
  }
}

const handleChangePassword = async () => {
  if (!passwordData.value.currentPassword) {
    alert('Vui lòng nhập mật khẩu hiện tại')
    return
  }
  if (!passwordData.value.newPassword || passwordData.value.newPassword.length < 6) {
    alert('Mật khẩu mới phải có ít nhất 6 ký tự')
    return
  }
  if (passwordData.value.newPassword !== passwordData.value.confirmPassword) {
    alert('Mật khẩu xác nhận không khớp')
    return
  }

  try {
    changingPassword.value = true
    await axios.put('/auth/change-password', passwordData.value)
    alert('Đổi mật khẩu thành công!')
    passwordData.value = {
      currentPassword: '',
      newPassword: '',
      confirmPassword: ''
    }
  } catch (error) {
    console.error('Lỗi khi đổi mật khẩu:', error)
    let errorMessage = 'Vui lòng thử lại sau'
    if (error.response?.data) {
      if (typeof error.response.data === 'string') {
        errorMessage = error.response.data
      } else if (error.response.data.message) {
        errorMessage = error.response.data.message
      }
    }
    alert('Lỗi khi đổi mật khẩu: ' + errorMessage)
  } finally {
    changingPassword.value = false
  }
}

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
      bookingsLoading.value = false
      return
    }
    const response = await axios.get(`/bookings/customer/${user.id}`)
    bookings.value = response.data.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
  } catch (error) {
    console.error('Lỗi khi lấy danh sách đặt phòng:', error)
  } finally {
    bookingsLoading.value = false
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
    submittingReview.value = true
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
    submittingReview.value = false
  }
}

onMounted(() => {
  fetchProfile()
  fetchBookings()
  window.scrollTo(0, 0)
})
</script>

<style scoped>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css');
</style>
