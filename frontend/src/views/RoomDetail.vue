<template>
  <div class="min-h-screen bg-gray-50 pb-20">
    <div v-if="room" class="max-w-7xl mx-auto px-4 pt-16">
      <nav class="flex mb-8 text-sm font-medium text-gray-500 uppercase tracking-widest">
        <router-link to="/" class="hover:text-emerald-800 transition-colors">Trang chủ</router-link>
        <span class="mx-3">/</span>
        <router-link to="/rooms" class="hover:text-emerald-800 transition-colors">Danh sách phòng</router-link>
        <span class="mx-3">/</span>
        <span class="text-emerald-800">Phòng {{ room.roomNumber }}</span>
      </nav>

      <div class="flex flex-col lg:flex-row gap-12">
        <div class="lg:w-7/12">
          <div class="rounded-3xl overflow-hidden shadow-2xl mb-6 aspect-video">
            <img :src="room.imageUrl || 'https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=1200&q=80'" class="w-full h-full object-cover">
          </div>
          <div class="grid grid-cols-3 gap-4">
            <div v-for="i in 3" :key="i" class="rounded-2xl overflow-hidden shadow-md aspect-video">
              <img :src="room.imageUrl || 'https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=400&q=80'" class="w-full h-full object-cover">
            </div>
          </div>
        </div>

        <div class="lg:w-5/12">
          <div class="bg-white rounded-3xl shadow-xl p-8 border border-gray-100 sticky top-8">
            <div class="flex justify-between items-start mb-6">
              <div>
                <div class="flex gap-2 mb-3">
                  <span class="bg-emerald-900 text-white px-4 py-1 rounded-full text-xs font-black uppercase tracking-widest inline-block">{{ room.category }}</span>
                  <span class="bg-emerald-100 text-emerald-800 px-4 py-1 rounded-full text-xs font-black uppercase tracking-widest inline-block">{{ room.type }}</span>
                </div>
                <h1 class="text-4xl font-black text-emerald-950 uppercase tracking-tighter">Phòng {{ room.roomNumber }}</h1>
              </div>
              <div class="text-right">
                <p class="text-3xl font-black text-emerald-800">{{ formatPrice(room.price) }}</p>
                <p class="text-gray-400 text-xs font-bold uppercase tracking-widest">mỗi đêm</p>
              </div>
            </div>

            <div class="space-y-4 text-gray-600 mb-8 border-b border-gray-100 pb-8">
              <p class="font-light leading-relaxed">
                <span class="font-black text-emerald-900">Mô tả chung:</span>
                {{ roomSpec.overview }}
              </p>
              <p class="font-light leading-relaxed">
                <span class="font-black text-emerald-900">Loại giường:</span>
                {{ roomSpec.bedType }}
              </p>
              <p class="font-light leading-relaxed">
                <span class="font-black text-emerald-900">Thiết kế:</span>
                {{ roomSpec.design }}
              </p>
              <p class="font-light leading-relaxed">
                <span class="font-black text-emerald-900">Phù hợp với:</span>
                {{ roomSpec.suitableFor }}
              </p>
            </div>

            <div class="grid grid-cols-2 gap-4 mb-10">
              <div class="bg-gray-50 rounded-2xl p-4">
                <div class="text-xs font-black text-gray-400 uppercase tracking-widest">Sức chứa</div>
                <div class="text-lg font-black text-emerald-900 mt-2">{{ roomSpec.capacityText }}</div>
              </div>
              <div class="bg-gray-50 rounded-2xl p-4">
                <div class="text-xs font-black text-gray-400 uppercase tracking-widest">Trạng thái</div>
                <div class="text-lg font-black mt-2" :class="getStatusColor(room.status)">{{ getStatusLabel(room.status) }}</div>
              </div>
            </div>

            <div class="mb-10">
              <h4 class="text-sm font-black text-emerald-900 uppercase tracking-widest mb-6">Tiện nghi phòng</h4>
              <div class="grid grid-cols-2 gap-y-4">
                <div v-for="amenity in roomSpec.amenities" :key="amenity" class="flex items-center space-x-3 text-gray-500">
                  <i class="fas fa-check-circle text-emerald-600"></i>
                  <span class="text-sm font-medium">{{ amenity }}</span>
                </div>
              </div>
            </div>

            <div v-if="canBook">
              <button @click="showBookingForm = true" class="w-full bg-emerald-800 text-white py-5 rounded-2xl font-black uppercase tracking-[0.2em] hover:bg-emerald-900 transition-all shadow-xl hover:shadow-emerald-100 transform hover:-translate-y-1 mb-4">
                Đặt phòng ngay
              </button>
            </div>
            <div v-else class="w-full bg-gray-100 text-gray-400 py-5 rounded-2xl font-black uppercase tracking-[0.2em] text-center">
              Phòng hiện chưa sẵn sàng
            </div>

            <p class="text-center text-[10px] text-gray-400 font-bold uppercase tracking-widest mt-6">
              Hỗ trợ COD, VNPay và Momo
            </p>
          </div>
        </div>
      </div>

      <div v-if="showBookingForm" class="fixed inset-0 bg-emerald-950/80 backdrop-blur-sm flex items-center justify-center p-4 z-50">
        <div class="bg-white rounded-[2rem] max-w-6xl w-full shadow-2xl transform transition-all max-h-[95vh] overflow-hidden flex flex-col">
          <!-- Modal Header -->
          <div class="p-8 pb-4 flex justify-between items-start">
            <div>
              <h3 class="text-3xl font-black text-emerald-950 uppercase tracking-tighter mb-2">BOOKING PHÒNG {{ room.roomNumber }}</h3>
              <div class="flex flex-wrap items-center gap-x-6 gap-y-2 text-xs font-bold text-gray-400 uppercase tracking-widest">
                <span class="text-emerald-800">{{ room.category }}</span>
                <div class="flex items-center gap-2">
                  <i class="fas fa-map-marker-alt text-emerald-600"></i>
                  <span>Phòng {{ room.roomNumber }}</span>
                </div>
                <div class="flex items-center gap-2">
                  <i class="fas fa-users text-emerald-600"></i>
                  <span>{{ roomSpec.capacityValue }} người</span>
                </div>
                <div class="flex items-center gap-2">
                  <i class="fas fa-expand text-emerald-600"></i>
                  <span>35m²</span>
                </div>
                <div class="flex items-center gap-2">
                  <i class="fas fa-bed text-emerald-600"></i>
                  <span>{{ roomSpec.bedType }}</span>
                </div>
                <div class="flex items-center gap-2">
                  <i class="fas fa-eye text-emerald-600"></i>
                  <span>Sea View</span>
                </div>
                <div class="flex items-center gap-1 bg-amber-50 text-amber-500 px-3 py-1 rounded-full border border-amber-100">
                  <i class="fas fa-star text-[10px]"></i>
                  <span>4.9 (128 đánh giá)</span>
                </div>
              </div>
            </div>
            <button @click="showBookingForm = false" class="text-gray-400 hover:text-emerald-800 transition-colors p-2">
              <i class="fas fa-times text-2xl"></i>
            </button>
          </div>

          <div class="flex-grow overflow-y-auto p-8 pt-4">
            <div class="flex flex-col lg:flex-row gap-8">
              <!-- Form Left Column -->
              <div class="lg:w-2/3 space-y-8">
                <form @submit.prevent="handleBooking" id="bookingForm" class="space-y-8">
                  <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div>
                      <label class="block text-[10px] font-black text-emerald-900 uppercase tracking-widest mb-3">Ngày check-in</label>
                      <div class="relative">
                        <i class="far fa-calendar-alt absolute left-5 top-1/2 -translate-y-1/2 text-gray-400"></i>
                        <input v-model="bookingData.checkInDate" type="date" class="w-full bg-gray-50 border border-gray-100 rounded-2xl pl-12 pr-6 py-4 focus:ring-2 focus:ring-emerald-800 outline-none font-bold text-sm text-slate-700" required>
                      </div>
                    </div>
                    <div>
                      <label class="block text-[10px] font-black text-emerald-900 uppercase tracking-widest mb-3">Ngày check-out</label>
                      <div class="relative">
                        <i class="far fa-calendar-check absolute left-5 top-1/2 -translate-y-1/2 text-gray-400"></i>
                        <input v-model="bookingData.checkOutDate" type="date" class="w-full bg-gray-50 border border-gray-100 rounded-2xl pl-12 pr-6 py-4 focus:ring-2 focus:ring-emerald-800 outline-none font-bold text-sm text-slate-700" required>
                      </div>
                    </div>
                  </div>

                  <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div>
                      <label class="block text-[10px] font-black text-emerald-900 uppercase tracking-widest mb-3">Họ tên khách</label>
                      <div class="relative">
                        <i class="far fa-user absolute left-5 top-1/2 -translate-y-1/2 text-gray-400"></i>
                        <input v-model="bookingData.guestFullName" type="text" :disabled="isLoggedIn" placeholder="Họ và tên của bạn" class="w-full border border-gray-100 rounded-2xl pl-12 pr-6 py-4 focus:ring-2 focus:ring-emerald-800 outline-none font-bold text-sm text-slate-700" :class="isLoggedIn ? 'bg-gray-100 cursor-not-allowed' : 'bg-gray-50'" required>
                      </div>
                    </div>
                    <div>
                      <label class="block text-[10px] font-black text-emerald-900 uppercase tracking-widest mb-3">Số điện thoại</label>
                      <div class="relative">
                        <i class="fas fa-phone-alt absolute left-5 top-1/2 -translate-y-1/2 text-gray-400"></i>
                        <input v-model="bookingData.guestPhone" type="tel" :disabled="isLoggedIn" placeholder="Số điện thoại liên lạc" class="w-full border border-gray-100 rounded-2xl pl-12 pr-6 py-4 focus:ring-2 focus:ring-emerald-800 outline-none font-bold text-sm text-slate-700" :class="isLoggedIn ? 'bg-gray-100 cursor-not-allowed' : 'bg-gray-50'" required>
                      </div>
                    </div>
                  </div>

                  <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div>
                      <label class="block text-[10px] font-black text-emerald-900 uppercase tracking-widest mb-3">Email</label>
                      <div class="relative">
                        <i class="far fa-envelope absolute left-5 top-1/2 -translate-y-1/2 text-gray-400"></i>
                        <input v-model="bookingData.guestEmail" type="email" :disabled="isLoggedIn" placeholder="Địa chỉ email nhận xác nhận" class="w-full border border-gray-100 rounded-2xl pl-12 pr-6 py-4 focus:ring-2 focus:ring-emerald-800 outline-none font-bold text-sm text-slate-700" :class="isLoggedIn ? 'bg-gray-100 cursor-not-allowed' : 'bg-gray-50'">
                      </div>
                    </div>
                    <div>
                      <label class="block text-[10px] font-black text-emerald-900 uppercase tracking-widest mb-3">Số người</label>
                      <div class="relative">
                        <i class="fas fa-user-friends absolute left-5 top-1/2 -translate-y-1/2 text-gray-400"></i>
                        <select v-model.number="bookingData.numberOfGuests" class="w-full bg-gray-50 border border-gray-100 rounded-2xl pl-12 pr-6 py-4 focus:ring-2 focus:ring-emerald-800 outline-none font-bold text-sm text-slate-700 appearance-none" required>
                          <option v-for="n in roomSpec.capacityValue || 4" :key="n" :value="n">{{ n }} người lớn</option>
                        </select>
                        <i class="fas fa-chevron-down absolute right-5 top-1/2 -translate-y-1/2 text-gray-400 pointer-events-none"></i>
                      </div>
                    </div>
                  </div>

                  <div>
                    <label class="block text-[10px] font-black text-emerald-900 uppercase tracking-widest mb-3">Địa chỉ</label>
                    <div class="relative">
                      <i class="fas fa-map-marker-alt absolute left-5 top-1/2 -translate-y-1/2 text-gray-400"></i>
                      <input v-model="bookingData.guestAddress" type="text" placeholder="Nhập địa chỉ của bạn (không bắt buộc)" class="w-full bg-gray-50 border border-gray-100 rounded-2xl pl-12 pr-6 py-4 focus:ring-2 focus:ring-emerald-800 outline-none font-bold text-sm text-slate-700">
                    </div>
                  </div>

                  <div>
                    <label class="block text-[10px] font-black text-emerald-900 uppercase tracking-widest mb-3">Yêu cầu đặc biệt (không bắt buộc)</label>
                    <div class="relative">
                      <i class="far fa-comment-alt absolute left-5 top-6 text-gray-400"></i>
                      <textarea v-model="bookingData.specialRequests" rows="3" placeholder="Ví dụ: Phòng tầng cao, không hút thuốc, trang trí sinh nhật..." class="w-full bg-gray-50 border border-gray-100 rounded-2xl pl-12 pr-6 py-4 focus:ring-2 focus:ring-emerald-800 outline-none font-bold text-sm text-slate-700 resize-none"></textarea>
                    </div>
                  </div>

                  <div>
                    <label class="block text-[10px] font-black text-emerald-900 uppercase tracking-widest mb-4">Phương thức thanh toán</label>
                    <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
                      <label 
                        v-for="method in paymentMethods" 
                        :key="method.value" 
                        class="relative flex items-center p-4 border-2 rounded-2xl cursor-pointer transition-all group"
                        :class="bookingData.paymentMethod === method.value ? 'border-emerald-800 bg-emerald-50' : 'border-gray-100 bg-white hover:border-emerald-200'"
                      >
                        <input v-model="bookingData.paymentMethod" :value="method.value" type="radio" class="sr-only">
                        <div class="w-10 h-10 rounded-xl flex items-center justify-center shrink-0 mr-4" :class="bookingData.paymentMethod === method.value ? 'bg-emerald-800 text-white' : 'bg-gray-50 text-emerald-800'">
                          <i :class="method.icon"></i>
                        </div>
                        <div class="flex-grow">
                          <p class="font-black text-[11px] uppercase tracking-widest">{{ method.label }}</p>
                          <p class="text-[9px] font-bold text-gray-400 uppercase mt-0.5">{{ method.desc }}</p>
                        </div>
                        <div v-if="bookingData.paymentMethod === method.value" class="absolute top-2 right-2">
                          <i class="fas fa-check-circle text-emerald-800 text-xs"></i>
                        </div>
                      </label>
                    </div>
                  </div>
                </form>
              </div>

              <!-- Sidebar Right Column -->
              <div class="lg:w-1/3 space-y-6">
                <!-- Room Info Box -->
                <div class="bg-white rounded-3xl border border-gray-100 overflow-hidden shadow-sm">
                  <div class="h-48 overflow-hidden relative">
                    <img :src="room.imageUrl || 'https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=800&q=80'" class="w-full h-full object-cover">
                    <div class="absolute bottom-4 right-4 bg-black/50 backdrop-blur-md text-white text-[10px] font-black px-3 py-1 rounded-full uppercase tracking-widest">
                      1/5
                    </div>
                  </div>
                  <div class="p-6">
                    <h4 class="text-[11px] font-black text-emerald-900 uppercase tracking-widest mb-4 italic">Thông tin phòng</h4>
                    <div class="space-y-3">
                      <div class="flex justify-between items-center text-sm">
                        <span class="text-gray-400 font-bold uppercase tracking-widest text-[10px]">Loại phòng</span>
                        <span class="font-black text-emerald-950 uppercase">{{ room.category }}</span>
                      </div>
                      <div class="flex justify-between items-center text-sm">
                        <span class="text-gray-400 font-bold uppercase tracking-widest text-[10px]">Phòng</span>
                        <span class="font-black text-emerald-950">{{ room.roomNumber }}</span>
                      </div>
                      <div class="flex justify-between items-center text-sm">
                        <span class="text-gray-400 font-bold uppercase tracking-widest text-[10px]">Sức chứa</span>
                        <span class="font-black text-emerald-950">{{ roomSpec.capacityValue }} người lớn</span>
                      </div>
                      <div class="flex justify-between items-center text-sm">
                        <span class="text-gray-400 font-bold uppercase tracking-widest text-[10px]">Diện tích</span>
                        <span class="font-black text-emerald-950">35m²</span>
                      </div>
                      <div class="flex justify-between items-center text-sm">
                        <span class="text-gray-400 font-bold uppercase tracking-widest text-[10px]">Giường</span>
                        <span class="font-black text-emerald-950">{{ roomSpec.bedType }}</span>
                      </div>
                      <div class="flex justify-between items-center text-sm">
                        <span class="text-gray-400 font-bold uppercase tracking-widest text-[10px]">Hướng view</span>
                        <span class="font-black text-emerald-950">Nhìn ra biển</span>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- Price Breakdown Box -->
                <div class="bg-white rounded-3xl border border-gray-100 p-6 shadow-sm">
                  <h4 class="text-[11px] font-black text-emerald-900 uppercase tracking-widest mb-4 italic">Chi tiết thanh toán</h4>
                  <div class="space-y-4">
                    <div class="flex justify-between items-center text-sm">
                      <span class="text-gray-500 font-bold">Giá phòng / đêm</span>
                      <span class="font-black text-emerald-950">{{ formatPrice(room.price) }}</span>
                    </div>
                    <div class="flex justify-between items-center text-sm">
                      <span class="text-gray-500 font-bold">Số đêm</span>
                      <span class="font-black text-emerald-950">{{ totalNights }} đêm</span>
                    </div>
                    <div class="pt-4 border-t border-gray-50 space-y-3">
                      <div class="flex justify-between items-center text-sm">
                        <span class="text-gray-500 font-bold">Tạm tính</span>
                        <span class="font-black text-emerald-950">{{ formatPrice(subtotal) }}</span>
                      </div>
                      <div class="flex justify-between items-center text-sm">
                        <span class="text-gray-500 font-bold">Thuế & phí dịch vụ (5%)</span>
                        <span class="font-black text-emerald-950">{{ formatPrice(taxAndServiceFee) }}</span>
                      </div>
                    </div>
                    <div class="pt-6 mt-6 border-t border-emerald-100">
                      <div class="flex justify-between items-end">
                        <span class="text-[11px] font-black text-emerald-900 uppercase tracking-widest">Tổng tiền</span>
                        <div class="text-right">
                          <p class="text-2xl font-black text-emerald-800 leading-none mb-2">{{ formatPrice(finalTotal) }}</p>
                          <p class="text-[9px] text-gray-400 font-bold uppercase tracking-widest">Đã bao gồm VAT và phí dịch vụ</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Policy Info -->
            <div class="grid grid-cols-1 md:grid-cols-3 gap-4 mt-8">
              <div class="bg-emerald-50/50 p-4 rounded-2xl flex items-start gap-3 border border-emerald-100/50">
                <i class="fas fa-history text-emerald-600 mt-1"></i>
                <div>
                  <p class="text-[10px] font-black text-emerald-900 uppercase tracking-widest mb-1">Miễn phí hủy phòng</p>
                  <p class="text-[9px] text-emerald-600/70 font-bold uppercase tracking-widest leading-relaxed">Hủy miễn phí trước 24h trước ngày nhận phòng</p>
                </div>
              </div>
              <div class="bg-emerald-50/50 p-4 rounded-2xl flex items-start gap-3 border border-emerald-100/50">
                <i class="fas fa-check-circle text-emerald-600 mt-1"></i>
                <div>
                  <p class="text-[10px] font-black text-emerald-900 uppercase tracking-widest mb-1">Xác nhận ngay</p>
                  <p class="text-[9px] text-emerald-600/70 font-bold uppercase tracking-widest leading-relaxed">Bạn sẽ nhận được xác nhận ngay sau khi đặt phòng</p>
                </div>
              </div>
              <div class="bg-emerald-50/50 p-4 rounded-2xl flex items-start gap-3 border border-emerald-100/50">
                <i class="fas fa-shield-alt text-emerald-600 mt-1"></i>
                <div>
                  <p class="text-[10px] font-black text-emerald-900 uppercase tracking-widest mb-1">Thanh toán an toàn</p>
                  <p class="text-[9px] text-emerald-600/70 font-bold uppercase tracking-widest leading-relaxed">Thông tin của bạn được bảo mật tuyệt đối</p>
                </div>
              </div>
            </div>

            <!-- Submit Button -->
            <div class="mt-10 space-y-4">
              <button 
                form="bookingForm"
                type="submit" 
                :disabled="loading" 
                class="w-full bg-[#004d26] text-white py-6 rounded-2xl font-black uppercase tracking-[0.2em] text-sm hover:bg-emerald-900 transition-all shadow-2xl shadow-green-900/30 flex items-center justify-center gap-4 group disabled:opacity-50"
              >
                <i class="fas fa-lock group-hover:scale-110 transition-transform"></i>
                {{ loading ? 'ĐANG XỬ LÝ...' : 'XÁC NHẬN BOOKING' }}
              </button>
              <p class="text-center text-[10px] text-gray-400 font-bold uppercase tracking-widest">
                <i class="fas fa-check-circle text-emerald-500 mr-2"></i>
                Bằng việc xác nhận, bạn đồng ý với <span class="text-emerald-800 underline cursor-pointer">Điều khoản & Chính sách</span> của chúng tôi.
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div v-else class="flex items-center justify-center min-h-screen">
      <div class="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-emerald-800"></div>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from '../api/axios'
import { getRoomSpec } from '../utils/roomSpecs'

const route = useRoute()
const router = useRouter()
const room = ref(null)
const showBookingForm = ref(false)
const loading = ref(false)
const paymentMethods = [
  { value: 'COD', label: 'COD', icon: 'fas fa-money-bill-wave', desc: 'Thanh toán khi nhận phòng' },
  { value: 'VNPAY', label: 'VNPay', icon: 'fas fa-credit-card', desc: 'Thanh toán qua VNPay' },
  { value: 'MOMO', label: 'Momo', icon: 'fas fa-wallet', desc: 'Thanh toán qua MoMo' }
]

const bookingData = ref({
  checkInDate: '',
  checkOutDate: '',
  guestFullName: '',
  guestEmail: '',
  guestPhone: '',
  guestAddress: '',
  numberOfGuests: 1,
  specialRequests: '',
  paymentMethod: 'COD'
})

const subtotal = computed(() => {
  if (!room.value || totalNights.value <= 0) return 0
  return room.value.price * totalNights.value
})

const taxAndServiceFee = computed(() => {
  return subtotal.value * 0.05
})

const finalTotal = computed(() => {
  return subtotal.value + taxAndServiceFee.value
})

const canBook = computed(() => room.value && room.value.status === 'AVAILABLE')
const roomSpec = computed(() => getRoomSpec(room.value))
const isLoggedIn = computed(() => {
  const user = JSON.parse(localStorage.getItem('user') || 'null')
  return user !== null
})

const getStatusLabel = (status) => {
  switch (status) {
    case 'AVAILABLE': return 'Còn trống'
    case 'BOOKED': return 'Đã đặt'
    case 'OCCUPIED': return 'Đang ở'
    case 'MAINTENANCE': return 'Bảo trì'
    default: return 'Tạm hết'
  }
}

const getStatusColor = (status) => {
  switch (status) {
    case 'AVAILABLE': return 'text-emerald-700'
    case 'BOOKED': return 'text-blue-600'
    case 'OCCUPIED': return 'text-purple-600'
    case 'MAINTENANCE': return 'text-red-500'
    default: return 'text-gray-500'
  }
}

const totalNights = computed(() => {
  if (!bookingData.value.checkInDate || !bookingData.value.checkOutDate) return 0
  const start = new Date(bookingData.value.checkInDate)
  const end = new Date(bookingData.value.checkOutDate)
  const days = Math.ceil((end - start) / (1000 * 60 * 60 * 24))
  return days > 0 ? days : 0
})

const totalPrice = computed(() => {
  if (!room.value || totalNights.value <= 0) return '0 đ'
  return formatPrice(room.value.price * totalNights.value)
})

const formatPrice = (price) => {
  if (!price) return '0 đ'
  return `${new Intl.NumberFormat('vi-VN').format(price * 25000)} đ`
}

const fetchRoom = async () => {
  try {
    const response = await axios.get(`/rooms/${route.params.id}`)
    room.value = response.data
  } catch (error) {
    console.error('Error fetching room:', error)
    alert('Không tìm thấy thông tin phòng!')
    router.push('/rooms')
  }
}

const fillCustomerInfo = () => {
  const user = JSON.parse(localStorage.getItem('user') || 'null')
  if (!user) return
  bookingData.value.guestFullName = user.fullName || ''
  bookingData.value.guestEmail = user.email || ''
  bookingData.value.guestPhone = user.phone || ''
}

const handleBooking = async () => {
  const user = JSON.parse(localStorage.getItem('user') || 'null')
  if (!user) {
    alert('Vui lòng đăng nhập để đặt phòng!')
    router.push('/login')
    return
  }

  if (totalNights.value <= 0) {
    alert('Ngày check-out phải sau ngày check-in')
    return
  }

  loading.value = true
  try {
    await axios.post('/bookings', {
      roomId: room.value.id,
      userId: user.id,
      checkInDate: bookingData.value.checkInDate,
      checkOutDate: bookingData.value.checkOutDate,
      guestFullName: bookingData.value.guestFullName,
      guestEmail: bookingData.value.guestEmail,
      guestPhone: bookingData.value.guestPhone,
      guestAddress: bookingData.value.guestAddress,
      numberOfGuests: bookingData.value.numberOfGuests,
      specialRequests: bookingData.value.specialRequests,
      paymentMethod: bookingData.value.paymentMethod
    })

    alert('Đặt phòng thành công!')
    showBookingForm.value = false
    router.push({ path: '/profile', query: { tab: 'bookings' } })
  } catch (error) {
    console.error('Booking failed:', error)
    alert(error.response?.data?.message || 'Đặt phòng thất bại, vui lòng kiểm tra lại thông tin')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchRoom()
  fillCustomerInfo()
})
</script>
