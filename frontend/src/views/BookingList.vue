<template>
  <div class="min-h-screen flex bg-gray-50 font-sans">
    <!-- Sidebar -->
    <aside class="w-72 bg-[#004d26] text-white min-h-screen flex flex-col shadow-2xl shrink-0">
      <div class="p-8 mb-4">
        <div class="flex items-center gap-3">
          <div class="w-12 h-12 bg-white/10 rounded-full flex items-center justify-center font-black text-2xl border border-white/20">H</div>
          <h1 class="text-2xl font-black uppercase tracking-tight text-white">Grand Hotel</h1>
        </div>
      </div>
      
      <nav class="flex-1 px-4 space-y-1">
        <router-link to="/staff-dashboard" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group hover:bg-white/10">
          <i class="fas fa-th-large text-xl opacity-70 group-hover:opacity-100"></i>
          <span class="font-bold text-lg tracking-tight">Tổng quan</span>
        </router-link>

        <router-link to="/staff-rooms" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group hover:bg-white/10">
          <i class="fas fa-door-open text-xl opacity-70 group-hover:opacity-100"></i>
          <span class="font-bold text-lg tracking-tight">Quản lý Phòng</span>
        </router-link>

        <router-link to="/bookings" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group bg-white/10">
          <i class="fas fa-calendar-alt text-xl opacity-100"></i>
          <span class="font-bold text-lg tracking-tight">Quản lý Đặt phòng</span>
        </router-link>

        <router-link to="/manage-services" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group hover:bg-white/10">
          <i class="fas fa-bell text-xl opacity-70 group-hover:opacity-100"></i>
          <span class="font-bold text-lg tracking-tight">Quản lý Dịch vụ</span>
        </router-link>
      </nav>

      <div class="p-6 border-t border-white/10">
        <button @click="logout" class="flex items-center gap-4 py-4 px-6 w-full rounded-xl transition-all duration-300 hover:bg-white/10 group text-white uppercase tracking-widest font-black text-sm">
          <span>Đăng xuất</span>
        </button>
      </div>
    </aside>

    <!-- Main Content -->
    <main class="flex-1 p-10 overflow-y-auto">
      <header class="flex justify-between items-center mb-10">
        <div class="flex items-center gap-6">
          <div class="w-14 h-14 bg-[#004d26] text-white rounded-2xl flex items-center justify-center shadow-xl cursor-pointer hover:rotate-180 transition-all duration-500" @click="fetchBookings" title="Làm mới dữ liệu">
            <i class="fas fa-sync-alt text-2xl"></i>
          </div>
          <div>
            <h2 class="text-4xl font-black text-gray-900 uppercase tracking-tight mb-2">Quản lý đặt phòng</h2>
            <p class="text-gray-500 font-medium">Hệ thống quản lý lưu trú, check-in và hồ sơ khách hàng</p>
          </div>
        </div>
        <button @click="openCreateModal" class="bg-[#004d26] hover:bg-[#003d1e] text-white px-10 py-4 rounded-2xl font-black uppercase tracking-widest text-xs transition-all shadow-xl shadow-green-200 flex items-center gap-3">
          <i class="fas fa-plus"></i>
          Tạo booking mới
        </button>
      </header>

      <!-- Stats Quick View -->
      <div class="grid grid-cols-4 gap-6 mb-10">
        <div v-for="tab in tabs" :key="tab.value" 
          @click="filters.status = tab.value"
          :class="filters.status === tab.value ? 'bg-[#004d26] text-white shadow-lg shadow-green-100' : 'bg-white text-gray-500 hover:bg-gray-50'"
          class="p-6 rounded-[2rem] cursor-pointer transition-all border border-transparent"
        >
          <div class="flex items-center justify-between">
            <span class="text-[10px] font-black uppercase tracking-widest">{{ tab.label }}</span>
            <i :class="tab.icon" class="text-xl opacity-20"></i>
          </div>
          <div class="text-3xl font-black mt-2">{{ getBookingCount(tab.value) }}</div>
        </div>
      </div>

      <!-- Search and Filter Bar -->
      <div class="bg-white rounded-[2.5rem] shadow-xl p-8 mb-10 border border-gray-50">
        <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
          <div class="space-y-3">
            <label class="block text-[10px] font-black uppercase tracking-[0.2em] text-gray-400 ml-1">Tìm kiếm khách hàng</label>
            <div class="relative group">
              <i class="fas fa-search absolute left-6 top-1/2 -translate-y-1/2 text-gray-300 group-focus-within:text-[#004d26] transition-colors"></i>
              <input type="text" v-model="filters.customerName" placeholder="Nhập tên khách hàng..." class="w-full bg-gray-50 border-0 rounded-2xl pl-14 pr-6 py-4 outline-none focus:ring-2 focus:ring-[#004d26] text-sm font-bold transition-all">
            </div>
          </div>
          <div class="space-y-3">
            <label class="block text-[10px] font-black uppercase tracking-[0.2em] text-gray-400 ml-1">Tìm theo phòng</label>
            <div class="relative group">
              <i class="fas fa-door-open absolute left-6 top-1/2 -translate-y-1/2 text-gray-300 group-focus-within:text-[#004d26] transition-colors"></i>
              <input type="text" v-model="filters.roomNumber" placeholder="Nhập số phòng..." class="w-full bg-gray-50 border-0 rounded-2xl pl-14 pr-6 py-4 outline-none focus:ring-2 focus:ring-[#004d26] text-sm font-bold transition-all">
            </div>
          </div>
          <div class="space-y-3">
            <label class="block text-[10px] font-black uppercase tracking-[0.2em] text-gray-400 ml-1">Lọc theo ngày</label>
            <div class="relative group">
              <i class="fas fa-calendar-alt absolute left-6 top-1/2 -translate-y-1/2 text-gray-300 group-focus-within:text-[#004d26] transition-colors"></i>
              <input type="date" v-model="filters.date" class="w-full bg-gray-50 border-0 rounded-2xl pl-14 pr-6 py-4 outline-none focus:ring-2 focus:ring-[#004d26] text-sm font-bold transition-all">
            </div>
          </div>
        </div>
      </div>

      <!-- Bookings Table -->
      <div class="bg-white rounded-[3rem] shadow-2xl shadow-gray-200 overflow-hidden border border-gray-100">
        <div class="overflow-x-auto">
          <table class="min-w-full">
            <thead>
              <tr class="bg-[#004d26] text-white">
                <th class="px-8 py-6 text-left text-[10px] font-black uppercase tracking-[0.2em] opacity-60">ID</th>
                <th class="px-8 py-6 text-left text-[10px] font-black uppercase tracking-[0.2em] opacity-60">Khách hàng</th>
                <th class="px-8 py-6 text-left text-[10px] font-black uppercase tracking-[0.2em] opacity-60">Thông tin phòng</th>
                <th class="px-8 py-6 text-left text-[10px] font-black uppercase tracking-[0.2em] opacity-60">Check-in</th>
                <th class="px-8 py-6 text-left text-[10px] font-black uppercase tracking-[0.2em] opacity-60">Check-out</th>
                <th class="px-8 py-6 text-left text-[10px] font-black uppercase tracking-[0.2em] opacity-60">Trạng thái</th>
                <th class="px-8 py-6 text-center text-[10px] font-black uppercase tracking-[0.2em] opacity-60">Thao tác</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-gray-50">
              <tr v-for="booking in filteredBookings" :key="booking.id" class="hover:bg-gray-50/50 transition-all group">
                <td class="px-8 py-6 text-sm font-black text-gray-900">#{{ booking.id }}</td>
                <td class="px-8 py-6">
                  <div class="text-sm font-black text-gray-900 uppercase tracking-tight">{{ booking.guestFullName || booking.customer?.fullName }}</div>
                  <div class="text-[10px] font-bold text-gray-400 mt-1 flex items-center gap-2">
                    <i class="fas fa-phone-alt text-[8px]"></i>
                    {{ booking.guestPhone || booking.customer?.phone || 'N/A' }}
                  </div>
                </td>
                <td class="px-8 py-6">
                  <div class="flex items-center gap-3">
                    <div :class="getRoomStatusBgClass(booking.room?.status)" class="px-4 py-2 rounded-xl flex items-center gap-3 border shadow-sm transition-all">
                      <span class="text-sm font-black uppercase tracking-tight">P.{{ booking.room?.roomNumber }}</span>
                      <div class="w-px h-3 bg-black/10"></div>
                      <span class="text-[9px] font-black uppercase tracking-widest opacity-70">{{ booking.room?.category }}</span>
                    </div>
                  </div>
                </td>
                <td class="px-8 py-6">
                  <div class="text-sm font-bold text-gray-700">{{ formatDate(booking.checkInDate) }}</div>
                </td>
                <td class="px-8 py-6">
                  <div class="text-sm font-bold text-gray-700">{{ formatDate(booking.checkOutDate) }}</div>
                </td>
                <td class="px-8 py-6">
                  <span :class="getStatusClass(booking.status)" class="px-5 py-2 rounded-full text-[9px] font-black uppercase tracking-[0.1em] shadow-sm inline-block">
                    {{ formatStatus(booking.status) }}
                  </span>
                </td>
                <td class="px-8 py-6">
                  <div class="flex justify-center gap-3">
                    <button v-if="['PENDING', 'CONFIRMED'].includes(booking.status)" @click="openCheckInModal(booking)" class="w-11 h-11 bg-indigo-50 text-indigo-600 rounded-2xl flex items-center justify-center hover:bg-indigo-600 hover:text-white transition-all shadow-sm" title="Check-in">
                      <i class="fas fa-sign-in-alt"></i>
                    </button>
                    <button v-if="booking.status === 'CHECKED_IN'" @click="handleCheckOut(booking.id)" class="w-11 h-11 bg-emerald-50 text-emerald-600 rounded-2xl flex items-center justify-center hover:bg-emerald-600 hover:text-white transition-all shadow-sm" title="Check-out">
                      <i class="fas fa-sign-out-alt"></i>
                    </button>
                    <button v-if="!['COMPLETED', 'CANCELLED'].includes(booking.status)" @click="openUpdateModal(booking)" class="w-11 h-11 bg-amber-50 text-amber-600 rounded-2xl flex items-center justify-center hover:bg-amber-600 hover:text-white transition-all shadow-sm" title="Sửa / Đổi phòng">
                      <i class="fas fa-edit"></i>
                    </button>
                    <button v-if="!['COMPLETED', 'CANCELLED'].includes(booking.status)" @click="handleCancel(booking.id)" class="w-11 h-11 bg-rose-50 text-rose-600 rounded-2xl flex items-center justify-center hover:bg-rose-600 hover:text-white transition-all shadow-sm" title="Hủy booking">
                      <i class="fas fa-times"></i>
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
          
          <div v-if="filteredBookings.length === 0" class="p-32 text-center">
            <div class="w-24 h-24 bg-gray-50 rounded-full flex items-center justify-center mx-auto mb-8 border-2 border-dashed border-gray-100">
              <i class="fas fa-folder-open text-3xl text-gray-200"></i>
            </div>
            <h4 class="text-xl font-black text-gray-300 uppercase tracking-widest">Không có dữ liệu</h4>
            <p class="text-sm text-gray-400 mt-3 font-medium">Thử thay đổi bộ lọc hoặc từ khóa tìm kiếm</p>
          </div>
        </div>
      </div>
    </main>

    <!-- Modal Tạo/Cập nhật Booking -->
    <div v-if="showModal" class="fixed inset-0 z-[100] flex items-center justify-center p-6 bg-slate-900/60 backdrop-blur-md">
      <div class="bg-white rounded-[3.5rem] max-w-2xl w-full overflow-hidden shadow-2xl animate-in fade-in zoom-in duration-300">
        <div class="bg-[#004d26] p-12 text-white relative">
          <div class="absolute top-12 right-12">
            <button @click="closeModal" class="w-12 h-12 flex items-center justify-center rounded-2xl bg-white/10 text-white hover:bg-rose-500 transition-all">
              <i class="fas fa-times text-xl"></i>
            </button>
          </div>
          <h3 class="text-4xl font-black uppercase tracking-tighter mb-2">{{ isEditing ? 'Cập nhật Booking' : 'Tạo Booking mới' }}</h3>
          <p class="text-green-100/60 font-bold">Vui lòng điền chính xác thông tin đặt phòng</p>
        </div>

        <form @submit.prevent="handleSubmit" class="p-12 space-y-8">
          <div class="grid grid-cols-2 gap-8">
            <div class="space-y-3">
              <label class="block text-[10px] font-black uppercase tracking-[0.2em] text-slate-400 ml-1">Tên khách hàng</label>
              <input v-model="form.guestFullName" type="text" required placeholder="Họ và tên..." class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold">
            </div>
            <div class="space-y-3">
              <label class="block text-[10px] font-black uppercase tracking-[0.2em] text-slate-400 ml-1">SĐT / Email</label>
              <input v-model="form.guestContact" type="text" required placeholder="Liên hệ..." class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold">
            </div>
          </div>

          <div class="grid grid-cols-2 gap-8">
            <div class="space-y-3">
              <label class="block text-[10px] font-black uppercase tracking-[0.2em] text-slate-400 ml-1">Ngày nhận phòng</label>
              <input v-model="form.checkInDate" type="date" required class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold">
            </div>
            <div class="space-y-3">
              <label class="block text-[10px] font-black uppercase tracking-[0.2em] text-slate-400 ml-1">Ngày trả phòng</label>
              <input v-model="form.checkOutDate" type="date" required class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold">
            </div>
          </div>

          <div class="space-y-3">
            <label class="block text-[10px] font-black uppercase tracking-[0.2em] text-slate-400 ml-1">Chọn phòng (Số phòng / Loại phòng)</label>
            <select v-model="form.roomId" required class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold">
              <option value="">-- Chọn phòng còn trống --</option>
              <option v-for="room in availableRooms" :key="room.id" :value="room.id">
                Phòng {{ room.roomNumber }} - {{ room.category }} ({{ formatPrice(room.price) }}/đêm)
              </option>
            </select>
          </div>

          <button type="submit" class="w-full bg-[#004d26] hover:bg-[#003d1e] text-white py-6 rounded-[2.5rem] font-black uppercase tracking-widest transition-all shadow-xl shadow-green-100 flex items-center justify-center gap-4">
            <i class="fas fa-check-circle"></i>
            <span>{{ isEditing ? 'Lưu thay đổi' : 'Xác nhận Booked' }}</span>
          </button>
        </form>
      </div>
    </div>

    <!-- Modal Check-in (Lưu hồ sơ khách hàng) -->
    <div v-if="showCheckInModal" class="fixed inset-0 z-[100] flex items-center justify-center p-6 bg-slate-900/60 backdrop-blur-md">
      <div class="bg-white rounded-[3.5rem] max-w-xl w-full overflow-hidden shadow-2xl animate-in fade-in zoom-in duration-300">
        <div class="bg-indigo-600 p-12 text-white relative">
          <div class="absolute top-12 right-12">
            <button @click="showCheckInModal = false" class="w-12 h-12 flex items-center justify-center rounded-2xl bg-white/10 text-white hover:bg-rose-500 transition-all">
              <i class="fas fa-times text-xl"></i>
            </button>
          </div>
          <h3 class="text-4xl font-black uppercase tracking-tighter mb-2">Thủ tục Check-in</h3>
          <p class="text-indigo-100/60 font-bold">Lưu trữ hồ sơ khách hàng (CCCD/Passport)</p>
        </div>

        <form @submit.prevent="handleCheckIn" class="p-12 space-y-8">
          <div class="space-y-3">
            <label class="block text-[10px] font-black uppercase tracking-[0.2em] text-slate-400 ml-1">Số CCCD / Passport</label>
            <input v-model="checkInForm.guestIdNumber" type="text" required placeholder="Nhập số định danh..." class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-indigo-600 transition-all font-bold">
          </div>

          <div class="space-y-3">
            <label class="block text-[10px] font-black uppercase tracking-[0.2em] text-slate-400 ml-1">Hình ảnh giấy tờ (Upload/URL)</label>
            <div class="space-y-4">
              <div class="w-full h-56 bg-slate-50 border-2 border-dashed border-slate-200 rounded-[2.5rem] flex flex-col items-center justify-center overflow-hidden group hover:border-indigo-600 transition-all">
                <template v-if="!checkInForm.guestIdImageUrl">
                  <i class="fas fa-camera text-4xl text-slate-200 group-hover:text-indigo-600 transition-all mb-4"></i>
                  <span class="text-[10px] font-black uppercase tracking-widest text-slate-400">Chọn ảnh hoặc nhập link bên dưới</span>
                </template>
                <img v-else :src="checkInForm.guestIdImageUrl" class="w-full h-full object-cover">
              </div>
              <input v-model="checkInForm.guestIdImageUrl" type="text" placeholder="Dán URL hình ảnh hồ sơ..." class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-indigo-600 transition-all font-bold">
            </div>
          </div>

          <button type="submit" class="w-full bg-indigo-600 hover:bg-indigo-700 text-white py-6 rounded-[2.5rem] font-black uppercase tracking-widest transition-all shadow-xl shadow-indigo-100 flex items-center justify-center gap-4">
            <i class="fas fa-user-check"></i>
            <span>Hoàn tất nhận phòng</span>
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from '../api/axios'

const router = useRouter()
const bookings = ref([])
const availableRooms = ref([])
const showModal = ref(false)
const showCheckInModal = ref(false)
const isEditing = ref(false)
const selectedBooking = ref(null)

const filters = ref({
  customerName: '',
  roomNumber: '',
  date: '',
  status: ''
})

const tabs = [
  { label: 'Tất cả booking', value: '', icon: 'fas fa-list' },
  { label: 'Sắp nhận phòng (Booked)', value: 'CONFIRMED', icon: 'fas fa-calendar-check' },
  { label: 'Đang lưu trú', value: 'CHECKED_IN', icon: 'fas fa-user-clock' },
  { label: 'Đã hoàn tất', value: 'COMPLETED', icon: 'fas fa-check-double' }
]

const form = ref({
  guestFullName: '',
  guestContact: '',
  checkInDate: '',
  checkOutDate: '',
  roomId: ''
})

const checkInForm = ref({
  guestIdNumber: '',
  guestIdImageUrl: ''
})

const fetchBookings = async () => {
  try {
    const response = await axios.get('/bookings')
    bookings.value = response.data
  } catch (error) {
    console.error('Error fetching bookings:', error)
  }
}

const fetchRooms = async () => {
  try {
    const response = await axios.get('/rooms')
    availableRooms.value = response.data.filter(r => r.status === 'AVAILABLE')
  } catch (error) {
    console.error('Error fetching rooms:', error)
  }
}

const getBookingCount = (status) => {
  if (!status) return bookings.value.length
  return bookings.value.filter(b => b.status === status).length
}

const filteredBookings = computed(() => {
  return bookings.value.filter(b => {
    const matchName = !filters.value.customerName || 
      (b.guestFullName || b.customer?.fullName || '').toLowerCase().includes(filters.value.customerName.toLowerCase())
    const matchRoom = !filters.value.roomNumber || 
      (b.room?.roomNumber || '').toString().includes(filters.value.roomNumber)
    const matchDate = !filters.value.date || 
      b.checkInDate === filters.value.date || b.checkOutDate === filters.value.date
    const matchStatus = !filters.value.status || b.status === filters.value.status
    
    return matchName && matchRoom && matchDate && matchStatus
  })
})

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const d = new Date(dateStr)
  return d.toLocaleDateString('vi-VN')
}

const formatPrice = (price) => {
  return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price * 25000)
}

const getStatusClass = (status) => {
  switch (status) {
    case 'CONFIRMED': return 'bg-emerald-50 text-emerald-600 border-emerald-100'
    case 'CHECKED_IN': return 'bg-blue-50 text-blue-600 border-blue-100'
    case 'COMPLETED': return 'bg-gray-100 text-gray-400 border-gray-200'
    case 'CANCELLED': return 'bg-rose-50 text-rose-500 border-rose-100'
    default: return 'bg-amber-50 text-amber-600 border-amber-100'
  }
}

const getRoomStatusBgClass = (status) => {
  switch (status) {
    case 'AVAILABLE': return 'bg-green-50 text-green-700 border-green-100';
    case 'OCCUPIED': return 'bg-rose-50 text-rose-700 border-rose-100';
    case 'CLEANING': return 'bg-amber-50 text-amber-700 border-amber-100';
    case 'MAINTENANCE': return 'bg-gray-50 text-gray-700 border-gray-100';
    case 'BOOKED': return 'bg-blue-50 text-blue-700 border-blue-100';
    default: return 'bg-white border-gray-100 text-gray-900';
  }
}

const formatStatus = (status) => {
  switch (status) {
    case 'PENDING': return 'Chờ xác nhận'
    case 'CONFIRMED': return 'Booked'
    case 'CHECKED_IN': return 'Checked-in'
    case 'COMPLETED': return 'Completed'
    case 'CANCELLED': return 'Cancelled'
    default: return status
  }
}

const openCreateModal = () => {
  isEditing.value = false
  form.value = {
    guestFullName: '',
    guestContact: '',
    checkInDate: '',
    checkOutDate: '',
    roomId: ''
  }
  showModal.value = true
  fetchRooms()
}

const openUpdateModal = (booking) => {
  isEditing.value = true
  selectedBooking.value = booking
  form.value = {
    guestFullName: booking.guestFullName || booking.customer?.fullName,
    guestContact: booking.guestPhone || booking.customer?.phone || booking.guestEmail || booking.customer?.email,
    checkInDate: booking.checkInDate,
    checkOutDate: booking.checkOutDate,
    roomId: booking.room?.id
  }
  showModal.value = true
  fetchRooms()
  if (!availableRooms.value.find(r => r.id === booking.room?.id)) {
    availableRooms.value.push(booking.room)
  }
}

const closeModal = () => {
  showModal.value = false
  selectedBooking.value = null
}

const handleSubmit = async () => {
  try {
    const user = JSON.parse(localStorage.getItem('user'))
    const payload = {
      ...form.value,
      userId: user.id,
      guestPhone: form.value.guestContact.includes('@') ? '' : form.value.guestContact,
      guestEmail: form.value.guestContact.includes('@') ? form.value.guestContact : ''
    }

    if (isEditing.value) {
      await axios.put(`/bookings/${selectedBooking.value.id}/update`, payload)
      if (form.value.roomId !== selectedBooking.value.room?.id) {
        await axios.put(`/bookings/${selectedBooking.value.id}/change-room?newRoomId=${form.value.roomId}`)
      }
    } else {
      await axios.post('/bookings', payload)
    }
    
    showModal.value = false
    fetchBookings()
    alert(isEditing.value ? 'Cập nhật thành công!' : 'Đã tạo booking thành công!')
  } catch (error) {
    alert('Lỗi: ' + (error.response?.data?.message || 'Không thể thực hiện'))
  }
}

const openCheckInModal = (booking) => {
  selectedBooking.value = booking
  checkInForm.value = {
    guestIdNumber: '',
    guestIdImageUrl: ''
  }
  showCheckInModal.value = true
}

const handleCheckIn = async () => {
  try {
    await axios.put(`/bookings/${selectedBooking.value.id}/check-in`, checkInForm.value)
    showCheckInModal.value = false
    fetchBookings()
    alert('Khách đã nhận phòng thành công!')
  } catch (error) {
    alert('Lỗi: ' + (error.response?.data?.message || 'Check-in thất bại'))
  }
}

const handleCheckOut = async (id) => {
  if (!confirm('Xác nhận trả phòng (Check-out) cho khách?')) return
  try {
    await axios.put(`/bookings/${id}/check-out`)
    fetchBookings()
    alert('Đã hoàn tất trả phòng. Trạng thái phòng: Đang dọn dẹp.')
  } catch (error) {
    alert('Lỗi: ' + (error.response?.data?.message || 'Check-out thất bại'))
  }
}

const handleCancel = async (id) => {
  if (!confirm('Bạn có chắc chắn muốn hủy đặt phòng này?')) return
  try {
    const user = JSON.parse(localStorage.getItem('user'))
    await axios.put(`/bookings/${id}/cancel?userId=${user.id}&isAdmin=true`)
    fetchBookings()
    alert('Đã hủy booking.')
  } catch (error) {
    alert('Lỗi: ' + (error.response?.data?.message || 'Hủy thất bại'))
  }
}

const logout = () => {
  localStorage.removeItem('user')
  router.push('/login')
}

onMounted(() => {
  fetchBookings()
})
</script>

<style scoped>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css');

main::-webkit-scrollbar {
  width: 8px;
}
main::-webkit-scrollbar-track {
  background: #f1f1f1;
}
main::-webkit-scrollbar-thumb {
  background: #004d26;
  border-radius: 10px;
}

.animate-in {
  animation: animate-in 0.3s ease-out;
}

@keyframes animate-in {
  from {
    opacity: 0;
    transform: scale(0.98);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}
</style>
