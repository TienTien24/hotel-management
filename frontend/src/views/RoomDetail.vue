<template>
  <div class="min-h-screen bg-gray-50 pb-20">
    <div v-if="room" class="max-w-7xl mx-auto px-4 pt-8">
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
        <div class="bg-white rounded-[2rem] max-w-2xl w-full p-8 shadow-2xl transform transition-all max-h-[90vh] overflow-y-auto">
          <div class="flex justify-between items-center mb-8">
            <h3 class="text-3xl font-black text-emerald-950 uppercase tracking-tighter">Booking phòng {{ room.roomNumber }}</h3>
            <button @click="showBookingForm = false" class="text-gray-400 hover:text-emerald-800 transition-colors">
              <i class="fas fa-times text-2xl"></i>
            </button>
          </div>

          <form @submit.prevent="handleBooking" class="space-y-6">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <label class="block text-xs font-black text-emerald-900 uppercase tracking-widest mb-3">Ngày check-in</label>
                <input v-model="bookingData.checkInDate" type="date" class="w-full bg-gray-50 border-0 rounded-2xl px-6 py-4 focus:ring-2 focus:ring-emerald-800 outline-none" required>
              </div>
              <div>
                <label class="block text-xs font-black text-emerald-900 uppercase tracking-widest mb-3">Ngày check-out</label>
                <input v-model="bookingData.checkOutDate" type="date" class="w-full bg-gray-50 border-0 rounded-2xl px-6 py-4 focus:ring-2 focus:ring-emerald-800 outline-none" required>
              </div>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <label class="block text-xs font-black text-emerald-900 uppercase tracking-widest mb-3">Họ tên khách</label>
                <input v-model="bookingData.guestFullName" type="text" class="w-full bg-gray-50 border-0 rounded-2xl px-6 py-4 focus:ring-2 focus:ring-emerald-800 outline-none" required>
              </div>
              <div>
                <label class="block text-xs font-black text-emerald-900 uppercase tracking-widest mb-3">Số điện thoại</label>
                <input v-model="bookingData.guestPhone" type="text" class="w-full bg-gray-50 border-0 rounded-2xl px-6 py-4 focus:ring-2 focus:ring-emerald-800 outline-none" required>
              </div>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <label class="block text-xs font-black text-emerald-900 uppercase tracking-widest mb-3">Email</label>
                <input v-model="bookingData.guestEmail" type="email" class="w-full bg-gray-50 border-0 rounded-2xl px-6 py-4 focus:ring-2 focus:ring-emerald-800 outline-none">
              </div>
              <div>
                <label class="block text-xs font-black text-emerald-900 uppercase tracking-widest mb-3">Số người</label>
                <input v-model.number="bookingData.numberOfGuests" min="1" :max="roomSpec.capacityValue || 10" type="number" class="w-full bg-gray-50 border-0 rounded-2xl px-6 py-4 focus:ring-2 focus:ring-emerald-800 outline-none" required>
              </div>
            </div>

            <div>
              <label class="block text-xs font-black text-emerald-900 uppercase tracking-widest mb-3">Địa chỉ</label>
              <input v-model="bookingData.guestAddress" type="text" class="w-full bg-gray-50 border-0 rounded-2xl px-6 py-4 focus:ring-2 focus:ring-emerald-800 outline-none">
            </div>

            <div>
              <label class="block text-xs font-black text-emerald-900 uppercase tracking-widest mb-3">Phương thức thanh toán</label>
              <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
                <label v-for="method in paymentMethods" :key="method.value" class="border rounded-2xl px-5 py-4 cursor-pointer transition-all" :class="bookingData.paymentMethod === method.value ? 'border-emerald-800 bg-emerald-50' : 'border-gray-200 bg-white'">
                  <input v-model="bookingData.paymentMethod" :value="method.value" type="radio" class="mr-2">
                  <span class="font-bold text-sm text-gray-700">{{ method.label }}</span>
                </label>
              </div>
            </div>

            <div class="bg-emerald-50 p-6 rounded-2xl border border-emerald-100">
              <div class="flex justify-between items-center mb-2">
                <span class="text-sm font-bold text-emerald-900 uppercase tracking-widest">Số đêm</span>
                <span class="text-lg font-black text-emerald-800">{{ totalNights }}</span>
              </div>
              <div class="flex justify-between items-center">
                <span class="text-sm font-bold text-emerald-900 uppercase tracking-widest">Tổng tiền dự kiến</span>
                <span class="text-xl font-black text-emerald-800">{{ totalPrice }}</span>
              </div>
              <p class="text-[10px] text-emerald-600 font-bold uppercase tracking-widest italic mt-3">Online payment hiện được xử lý theo luồng xác nhận nội bộ</p>
            </div>

            <button type="submit" :disabled="loading" class="w-full bg-emerald-800 text-white py-5 rounded-2xl font-black uppercase tracking-[0.2em] hover:bg-emerald-900 transition-all shadow-xl disabled:opacity-50">
              {{ loading ? 'Đang xử lý...' : 'Xác nhận booking' }}
            </button>
          </form>
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
  { value: 'COD', label: 'COD' },
  { value: 'VNPAY', label: 'VNPay' },
  { value: 'MOMO', label: 'Momo' }
]

const bookingData = ref({
  checkInDate: '',
  checkOutDate: '',
  guestFullName: '',
  guestEmail: '',
  guestPhone: '',
  guestAddress: '',
  numberOfGuests: 1,
  paymentMethod: 'COD'
})

const canBook = computed(() => room.value && room.value.status === 'AVAILABLE')
const roomSpec = computed(() => getRoomSpec(room.value))

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
      paymentMethod: bookingData.value.paymentMethod
    })

    alert('Đặt phòng thành công!')
    showBookingForm.value = false
    router.push('/bookings')
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
