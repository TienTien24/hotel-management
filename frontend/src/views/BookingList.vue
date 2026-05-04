<template>
  <div class="min-h-screen flex bg-gray-50 font-sans">
    <!-- Dynamic Sidebar based on user role -->
    <AdminSidebar v-if="isAdmin" />
    <StaffSidebar v-else />

    <!-- Main Content -->
    <main class="flex-1 p-10 overflow-y-auto">
      <header class="flex flex-col lg:flex-row lg:items-center lg:justify-between gap-6 mb-8">
        <div>
          <h2 class="text-3xl font-black tracking-tight text-slate-800">Quản lý đặt phòng</h2>
        </div>
        <button @click="openCreateModal" class="bg-[#004d26] hover:bg-[#003d1e] text-white px-6 py-3 rounded-xl font-black uppercase tracking-widest text-xs transition-all shadow-xl shadow-green-200/50 flex items-center gap-3">
          <i class="fas fa-plus"></i>
          Tạo booking mới
        </button>
      </header>

      <!-- Stats Quick View -->
      <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
        <div v-for="tab in tabs" :key="tab.value" 
          @click="filters.status = tab.value"
          :class="filters.status === tab.value ? 'bg-[#004d26] text-white shadow-lg shadow-green-100' : 'bg-white text-slate-500 hover:bg-slate-50'"
          class="bg-white rounded-[2rem] p-6 shadow-sm border border-slate-100 cursor-pointer transition-all"
        >
          <div class="flex items-center justify-between">
            <span class="text-[10px] font-black uppercase tracking-widest">{{ tab.label }}</span>
            <i :class="tab.icon" class="text-lg opacity-20"></i>
          </div>
          <div class="text-3xl font-black mt-2">{{ getBookingCount(tab.value) }}</div>
        </div>
      </div>

      <!-- Search and Filter Bar -->
      <div class="bg-white rounded-[2.5rem] shadow-sm border border-slate-100 overflow-hidden mb-8">
        <div class="p-8 border-b border-slate-50 bg-slate-50/50">
          <div class="flex flex-col lg:flex-row gap-4 w-full">
            <div class="relative w-full lg:w-80">
              <i class="fas fa-search absolute left-5 top-1/2 -translate-y-1/2 text-slate-300"></i>
              <input type="text" v-model="filters.customerName" placeholder="Tìm kiếm khách hàng..." class="w-full bg-white border-2 border-slate-100 rounded-xl pl-12 pr-5 py-3 outline-none focus:border-[#004d26] transition-all font-bold text-sm">
            </div>
            <div class="relative w-full lg:w-80">
              <i class="fas fa-door-open absolute left-5 top-1/2 -translate-y-1/2 text-slate-300"></i>
              <input type="text" v-model="filters.roomNumber" placeholder="Tìm theo phòng..." class="w-full bg-white border-2 border-slate-100 rounded-xl pl-12 pr-5 py-3 outline-none focus:border-[#004d26] transition-all font-bold text-sm">
            </div>
            <input type="date" v-model="filters.date" class="w-full lg:w-auto bg-white border-2 border-slate-100 rounded-xl px-5 py-3 outline-none focus:border-[#004d26] font-bold text-sm text-slate-600">
          </div>
        </div>
      </div>

      <!-- Bookings Table -->
      <div class="bg-white rounded-[2.5rem] shadow-sm border border-slate-100 overflow-hidden">
        <div v-if="loading" class="p-16 text-center text-slate-400 font-bold">Đang tải danh sách đặt phòng...</div>
        <div v-else-if="filteredBookings.length === 0" class="p-16 text-center text-slate-400 font-bold">Không có đặt phòng phù hợp.</div>
        <div v-else class="overflow-x-auto">
          <table class="w-full">
            <thead>
              <tr class="bg-slate-50/50 text-left">
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">ID</th>
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Khách hàng</th>
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Thông tin phòng</th>
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Check-in</th>
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Check-out</th>
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Trạng thái</th>
                <th class="px-8 py-5 text-center text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Thao tác</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-slate-50">
              <tr v-for="booking in filteredBookings" :key="booking.id" class="hover:bg-slate-50/50 transition-colors">
                <td class="px-8 py-6">
                  <div class="font-black text-slate-800">#{{ booking.id }}</div>
                </td>
                <td class="px-8 py-6">
                  <div class="text-sm font-black text-slate-800 uppercase tracking-tight">{{ booking.guestFullName || booking.customer?.fullName }}</div>
                  <div class="text-[10px] font-bold text-slate-400 mt-1 flex items-center gap-2">
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
                  <div class="text-sm font-bold text-slate-700">{{ formatDate(booking.checkInDate) }}</div>
                </td>
                <td class="px-8 py-6">
                  <div class="text-sm font-bold text-slate-700">{{ formatDate(booking.checkOutDate) }}</div>
                </td>
                <td class="px-8 py-6">
                  <span :class="getStatusClass(booking.status)" class="px-4 py-1.5 rounded-full text-[10px] font-black uppercase tracking-widest">
                    {{ formatStatus(booking.status) }}
                  </span>
                </td>
                <td class="px-8 py-6">
                  <div class="flex justify-center gap-2">
                    <button v-if="['PENDING', 'CONFIRMED'].includes(booking.status)" @click="openCheckInModal(booking)" class="w-10 h-10 rounded-xl bg-slate-50 text-slate-400 hover:bg-indigo-50 hover:text-indigo-600 transition-all flex items-center justify-center shadow-sm" title="Check-in">
                      <i class="fas fa-sign-in-alt"></i>
                    </button>
                    <button v-if="booking.status === 'CHECKED_IN'" @click="handleCheckOut(booking.id)" class="w-10 h-10 rounded-xl bg-slate-50 text-slate-400 hover:bg-emerald-50 hover:text-emerald-600 transition-all flex items-center justify-center shadow-sm" title="Check-out">
                      <i class="fas fa-sign-out-alt"></i>
                    </button>
                    <button v-if="booking.status === 'COMPLETED'" @click="openInvoiceModal(booking)" class="w-10 h-10 rounded-xl bg-slate-50 text-slate-400 hover:bg-blue-50 hover:text-blue-600 transition-all flex items-center justify-center shadow-sm" title="Xem hóa đơn">
                      <i class="fas fa-file-invoice"></i>
                    </button>
                    <button v-if="!['COMPLETED', 'CANCELLED'].includes(booking.status)" @click="openUpdateModal(booking)" class="w-10 h-10 rounded-xl bg-slate-50 text-slate-400 hover:bg-amber-50 hover:text-amber-600 transition-all flex items-center justify-center shadow-sm" title="Sửa / Đổi phòng">
                      <i class="fas fa-edit"></i>
                    </button>
                    <button v-if="!['COMPLETED', 'CANCELLED'].includes(booking.status)" @click="handleCancel(booking.id)" class="w-10 h-10 rounded-xl bg-slate-50 text-slate-400 hover:bg-rose-50 hover:text-rose-600 transition-all flex items-center justify-center shadow-sm" title="Hủy booking">
                      <i class="fas fa-times"></i>
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
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

          <div class="grid grid-cols-2 gap-8">
            <div class="space-y-3">
              <label class="block text-[10px] font-black uppercase tracking-[0.2em] text-slate-400 ml-1">Số người</label>
              <input v-model.number="form.numberOfGuests" type="number" min="1" required placeholder="Số khách..." class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold">
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
            <button @click="closeCheckInModal" class="w-12 h-12 flex items-center justify-center rounded-2xl bg-white/10 text-white hover:bg-rose-500 transition-all">
              <i class="fas fa-times text-xl"></i>
            </button>
          </div>
          <h3 class="text-4xl font-black uppercase tracking-tighter mb-2">Thủ tục Check-in</h3>
          <p class="text-indigo-100/60 font-bold">Lưu trữ hồ sơ khách hàng (CCCD/Passport)</p>
        </div>

        <form @submit.prevent="handleCheckIn" class="p-12 space-y-8">
          <div class="space-y-4 bg-slate-50 p-6 rounded-[2rem]">
            <label class="block text-[10px] font-black uppercase tracking-[0.2em] text-slate-400 ml-1">Xác nhận thông tin khách</label>
            <div class="space-y-2">
              <div class="flex justify-between items-center">
                <span class="text-sm font-bold text-slate-500">Tên khách</span>
                <span class="text-sm font-black text-slate-800">{{ selectedBooking?.guestFullName || selectedBooking?.customer?.fullName }}</span>
              </div>
              <div class="flex justify-between items-center">
                <span class="text-sm font-bold text-slate-500">Số người</span>
                <span class="text-sm font-black text-slate-800">{{ selectedBooking?.numberOfGuests }} người</span>
              </div>
              <div class="flex justify-between items-center">
                <span class="text-sm font-bold text-slate-500">Phòng</span>
                <span class="text-sm font-black text-slate-800">P.{{ selectedBooking?.room?.roomNumber }} - {{ selectedBooking?.room?.category }}</span>
              </div>
            </div>
          </div>

          <div class="space-y-3">
            <label class="block text-[10px] font-black uppercase tracking-[0.2em] text-slate-400 ml-1">Số CCCD / Passport <span class="text-rose-500">*</span></label>
            <input v-model="checkInForm.guestIdNumber" type="text" required placeholder="Nhập số định danh..." class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-indigo-600 transition-all font-bold">
          </div>

          <div class="space-y-3">
            <label class="block text-[10px] font-black uppercase tracking-[0.2em] text-slate-400 ml-1">Hình ảnh giấy tờ (Không bắt buộc)</label>
            <div class="space-y-4">
              <div class="w-full h-56 bg-slate-50 border-2 border-dashed border-slate-200 rounded-[2.5rem] flex flex-col items-center justify-center overflow-hidden group hover:border-indigo-600 transition-all relative">
                <video v-if="isCameraActive" ref="cameraRef" autoplay playsinline class="w-full h-full object-cover"></video>
                <template v-else-if="checkInForm.guestIdImageUrl">
                  <img :src="checkInForm.guestIdImageUrl" class="w-full h-full object-cover">
                  <button @click="clearImage" type="button" class="absolute top-4 right-4 w-10 h-10 bg-rose-500 text-white rounded-full flex items-center justify-center shadow-lg hover:bg-rose-600 transition-all">
                    <i class="fas fa-times"></i>
                  </button>
                </template>
                <template v-else>
                  <i class="fas fa-id-card text-4xl text-slate-200 group-hover:text-indigo-600 transition-all mb-4"></i>
                  <span class="text-[10px] font-black uppercase tracking-widest text-slate-400">Chọn cách thêm ảnh</span>
                </template>
              </div>
              
              <div class="flex gap-3">
                <input type="file" ref="fileInputRef" accept="image/*" @change="handleFileSelect" class="hidden">
                <button @click="openFilePicker" type="button" class="flex-1 bg-slate-100 hover:bg-slate-200 text-slate-700 py-4 rounded-2xl font-black uppercase tracking-widest text-xs transition-all flex items-center justify-center gap-2">
                  <i class="fas fa-upload"></i>
                  Upload ảnh
                </button>
                <button v-if="!isCameraActive" @click="startCamera" type="button" class="flex-1 bg-indigo-50 hover:bg-indigo-100 text-indigo-700 py-4 rounded-2xl font-black uppercase tracking-widest text-xs transition-all flex items-center justify-center gap-2">
                  <i class="fas fa-camera"></i>
                  Chụp ảnh
                </button>
                <button v-else @click="capturePhoto" type="button" class="flex-1 bg-emerald-500 hover:bg-emerald-600 text-white py-4 rounded-2xl font-black uppercase tracking-widest text-xs transition-all flex items-center justify-center gap-2">
                  <i class="fas fa-check"></i>
                  Chụp
                </button>
                <button v-if="isCameraActive" @click="stopCamera" type="button" class="w-14 bg-rose-50 hover:bg-rose-100 text-rose-700 py-4 rounded-2xl font-black uppercase tracking-widest text-xs transition-all flex items-center justify-center">
                  <i class="fas fa-times"></i>
                </button>
              </div>
            </div>
          </div>

          <button type="submit" class="w-full bg-indigo-600 hover:bg-indigo-700 text-white py-6 rounded-[2.5rem] font-black uppercase tracking-widest transition-all shadow-xl shadow-indigo-100 flex items-center justify-center gap-4">
            <i class="fas fa-user-check"></i>
            <span>Hoàn tất nhận phòng</span>
          </button>
        </form>
      </div>
    </div>

    <!-- Modal Hóa đơn -->
    <div v-if="showInvoiceModal" class="fixed inset-0 z-[100] flex items-center justify-center p-6 bg-slate-900/60 backdrop-blur-md">
      <div class="bg-white rounded-[3.5rem] max-w-2xl w-full overflow-hidden shadow-2xl animate-in fade-in zoom-in duration-300">
        <div class="bg-emerald-600 p-12 text-white relative">
          <div class="absolute top-12 right-12">
            <button @click="showInvoiceModal = false" class="w-12 h-12 flex items-center justify-center rounded-2xl bg-white/10 text-white hover:bg-rose-500 transition-all">
              <i class="fas fa-times text-xl"></i>
            </button>
          </div>
          <h3 class="text-4xl font-black uppercase tracking-tighter mb-2">Hóa đơn</h3>
          <p class="text-emerald-100/60 font-bold">Booking #{{ currentInvoice?.booking?.id }}</p>
        </div>

        <div class="p-12 space-y-8" v-if="currentInvoice">
          <div class="space-y-4 bg-slate-50 p-6 rounded-[2rem]">
            <h4 class="text-[11px] font-black uppercase tracking-[0.2em] text-slate-400">Thông tin khách hàng</h4>
            <div class="grid grid-cols-2 gap-4">
              <div>
                <p class="text-[10px] font-bold uppercase tracking-widest text-slate-400">Tên khách</p>
                <p class="text-sm font-black text-slate-800">{{ currentInvoice.booking?.guestFullName || currentInvoice.booking?.customer?.fullName }}</p>
              </div>
              <div>
                <p class="text-[10px] font-bold uppercase tracking-widest text-slate-400">Số điện thoại</p>
                <p class="text-sm font-black text-slate-800">{{ currentInvoice.booking?.guestPhone || currentInvoice.booking?.customer?.phone }}</p>
              </div>
            </div>
          </div>

          <div class="space-y-4 bg-slate-50 p-6 rounded-[2rem]">
            <h4 class="text-[11px] font-black uppercase tracking-[0.2em] text-slate-400">Thông tin phòng</h4>
            <div class="grid grid-cols-3 gap-4">
              <div>
                <p class="text-[10px] font-bold uppercase tracking-widest text-slate-400">Phòng</p>
                <p class="text-sm font-black text-slate-800">P.{{ currentInvoice.booking?.room?.roomNumber }}</p>
              </div>
              <div>
                <p class="text-[10px] font-bold uppercase tracking-widest text-slate-400">Ngày đến</p>
                <p class="text-sm font-black text-slate-800">{{ formatDate(currentInvoice.booking?.checkInDate) }}</p>
              </div>
              <div>
                <p class="text-[10px] font-bold uppercase tracking-widest text-slate-400">Ngày đi</p>
                <p class="text-sm font-black text-slate-800">{{ formatDate(currentInvoice.booking?.checkOutDate) }}</p>
              </div>
            </div>
          </div>

          <div class="space-y-4">
            <h4 class="text-[11px] font-black uppercase tracking-[0.2em] text-slate-400">Chi tiết thanh toán</h4>
            <div class="space-y-3">
              <div class="flex justify-between items-center py-3 border-b border-slate-100">
                <span class="text-sm font-bold text-slate-600">Phòng</span>
                <span class="text-sm font-black text-slate-800">{{ formatCurrency(currentInvoice.roomCharges) }}</span>
              </div>
              <div class="flex justify-between items-center py-3 border-b border-slate-100" v-if="currentInvoice.serviceCharges > 0">
                <span class="text-sm font-bold text-slate-600">Dịch vụ</span>
                <span class="text-sm font-black text-slate-800">{{ formatCurrency(currentInvoice.serviceCharges) }}</span>
              </div>
              <div class="flex justify-between items-center pt-3">
                <span class="text-lg font-black uppercase tracking-widest text-slate-800">Tổng cộng</span>
                <span class="text-2xl font-black text-emerald-600">{{ formatCurrency(currentInvoice.totalAmount) }}</span>
              </div>
            </div>
          </div>

          <div class="flex items-center justify-between bg-emerald-50 p-6 rounded-[2rem]">
            <div>
              <p class="text-[10px] font-bold uppercase tracking-widest text-emerald-600">Trạng thái thanh toán</p>
              <p class="text-sm font-black text-emerald-800">{{ currentInvoice.paymentStatus === 'PAID' ? 'Đã thanh toán' : 'Chưa thanh toán' }}</p>
            </div>
            <div class="text-right">
              <p class="text-[10px] font-bold uppercase tracking-widest text-slate-400">Ngày thanh toán</p>
              <p class="text-sm font-black text-slate-800">{{ currentInvoice.paymentDate ? formatDateTime(currentInvoice.paymentDate) : '--' }}</p>
            </div>
          </div>
          
          <button @click="printInvoice" class="w-full bg-slate-900 hover:bg-slate-800 text-white py-5 rounded-[2rem] font-black uppercase tracking-widest transition-all flex items-center justify-center gap-3">
            <i class="fas fa-print"></i>
            <span>In hóa đơn</span>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import axios from '../api/axios'
import AdminSidebar from '../components/AdminSidebar.vue'
import StaffSidebar from '../components/StaffSidebar.vue'

const router = useRouter()
const isAdmin = computed(() => {
  const user = JSON.parse(localStorage.getItem('user') || '{}')
  return user.role === 'ADMIN'
})
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
  roomId: '',
  numberOfGuests: 1
})

const checkInForm = ref({
  guestIdNumber: '',
  guestIdImageUrl: ''
})
const isCameraActive = ref(false)
const cameraRef = ref(null)
const fileInputRef = ref(null)
let cameraStream = null
const showInvoiceModal = ref(false)
const currentInvoice = ref(null)

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
    roomId: '',
    numberOfGuests: 1
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
    roomId: booking.room?.id,
    numberOfGuests: booking.numberOfGuests || 1
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
    guestIdNumber: booking.guestIdNumber || '',
    guestIdImageUrl: booking.guestIdImageUrl || ''
  }
  showCheckInModal.value = true
}

const closeCheckInModal = () => {
  showCheckInModal.value = false
  stopCamera()
}

const startCamera = async () => {
  try {
    cameraStream = await navigator.mediaDevices.getUserMedia({ video: true })
    isCameraActive.value = true
    await nextTick()
    if (cameraRef.value) {
      cameraRef.value.srcObject = cameraStream
    }
  } catch (error) {
    console.error('Lỗi khi mở camera:', error)
    alert('Không thể mở camera. Vui lòng kiểm tra quyền truy cập.')
  }
}

const stopCamera = () => {
  if (cameraStream) {
    cameraStream.getTracks().forEach(track => track.stop())
    cameraStream = null
  }
  isCameraActive.value = false
}

const capturePhoto = () => {
  if (!cameraRef.value) return
  
  const canvas = document.createElement('canvas')
  canvas.width = cameraRef.value.videoWidth
  canvas.height = cameraRef.value.videoHeight
  const ctx = canvas.getContext('2d')
  ctx.drawImage(cameraRef.value, 0, 0)
  checkInForm.value.guestIdImageUrl = canvas.toDataURL('image/jpeg')
  stopCamera()
}

const openFilePicker = () => {
  fileInputRef.value?.click()
}

const handleFileSelect = (event) => {
  const file = event.target.files[0]
  if (file) {
    const reader = new FileReader()
    reader.onload = (e) => {
      checkInForm.value.guestIdImageUrl = e.target.result
    }
    reader.readAsDataURL(file)
  }
}

const clearImage = () => {
  checkInForm.value.guestIdImageUrl = ''
}

const handleCheckIn = async () => {
  try {
    await axios.put(`/bookings/${selectedBooking.value.id}/check-in`, checkInForm.value)
    showCheckInModal.value = false
    stopCamera()
    fetchBookings()
    alert('Khách đã nhận phòng thành công!')
  } catch (error) {
    alert('Lỗi: ' + (error.response?.data?.message || 'Check-in thất bại'))
  }
}

const handleCheckOut = async (id) => {
  if (!confirm('Xác nhận trả phòng (Check-out) cho khách?')) return
  try {
    const invoice = await axios.put(`/invoices/booking/${id}/check-out`)
    currentInvoice.value = invoice.data
    fetchBookings()
    showInvoiceModal.value = true
  } catch (error) {
    alert('Lỗi: ' + (error.response?.data?.message || 'Check-out thất bại'))
  }
}

const openInvoiceModal = async (booking) => {
  try {
    const response = await axios.get(`/invoices/booking/${booking.id}`)
    currentInvoice.value = response.data
    showInvoiceModal.value = true
  } catch (error) {
    alert('Lỗi: ' + (error.response?.data?.message || 'Không tìm thấy hóa đơn'))
  }
}

const formatCurrency = (amount) => {
  if (!amount) return '0 đ'
  return new Intl.NumberFormat('vi-VN').format(amount * 25000) + ' đ'
}

const formatDateTime = (dateStr) => {
  if (!dateStr) return ''
  const d = new Date(dateStr)
  return d.toLocaleString('vi-VN')
}

const printInvoice = () => {
  const printContent = document.querySelector('#invoice-content')?.innerHTML
  if (!printContent) return
  
  const printWindow = window.open('', '', 'width=800,height=1000')
  if (!printWindow) return
  
  printWindow.document.write(`
    <!DOCTYPE html>
    <html>
    <head>
      <title>Hóa đơn - Grand Hotel</title>
      <style>
        body {
          font-family: Arial, sans-serif;
          padding: 40px;
          max-width: 800px;
          margin: 0 auto;
        }
        .invoice-header {
          text-align: center;
          margin-bottom: 30px;
          border-bottom: 2px solid #004d26;
          padding-bottom: 20px;
        }
        .invoice-header h1 {
          color: #004d26;
          margin: 0;
        }
        .invoice-section {
          margin: 20px 0;
          padding: 15px;
          background: #f8fafc;
          border-radius: 8px;
        }
        .invoice-section h4 {
          color: #64748b;
          font-size: 12px;
          text-transform: uppercase;
          letter-spacing: 0.1em;
          margin: 0 0 15px 0;
        }
        .grid-2 {
          display: grid;
          grid-template-columns: 1fr 1fr;
          gap: 20px;
        }
        .grid-3 {
          display: grid;
          grid-template-columns: 1fr 1fr 1fr;
          gap: 20px;
        }
        .item {
          margin-bottom: 10px;
        }
        .item-label {
          font-size: 12px;
          color: #64748b;
          text-transform: uppercase;
          letter-spacing: 0.1em;
        }
        .item-value {
          font-size: 14px;
          font-weight: bold;
          color: #1e293b;
          margin-top: 4px;
        }
        .total-section {
          margin-top: 30px;
        }
        .total-row {
          display: flex;
          justify-content: space-between;
          padding: 10px 0;
          border-bottom: 1px solid #e2e8f0;
        }
        .total-row:last-child {
          border-bottom: none;
          padding-top: 20px;
        }
        .total-label {
          font-size: 14px;
          color: #475569;
          font-weight: bold;
        }
        .total-value {
          font-size: 14px;
          color: #1e293b;
          font-weight: bold;
        }
        .grand-total .total-label {
          font-size: 18px;
          text-transform: uppercase;
          letter-spacing: 0.1em;
        }
        .grand-total .total-value {
          font-size: 24px;
          color: #059669;
        }
        .status-section {
          display: flex;
          justify-content: space-between;
          background: #d1fae5;
          padding: 20px;
          border-radius: 8px;
          margin-top: 20px;
        }
        .status-label {
          font-size: 12px;
          text-transform: uppercase;
          letter-spacing: 0.1em;
          color: #059669;
        }
        .status-value {
          font-size: 14px;
          font-weight: bold;
          color: #065f46;
          margin-top: 4px;
        }
        @media print {
          body {
            padding: 0;
          }
        }
      </style>
    </head>
    <body>
      <div class="invoice-header">
        <h1>GRAND HOTEL</h1>
        <p>HÓA ĐƠN</p>
        <p>Booking #${currentInvoice.value?.booking?.id || ''}</p>
      </div>
      
      <div class="invoice-section">
        <h4>Thông tin khách hàng</h4>
        <div class="grid-2">
          <div class="item">
            <div class="item-label">Tên khách</div>
            <div class="item-value">${currentInvoice.value?.booking?.guestFullName || currentInvoice.value?.booking?.customer?.fullName || ''}</div>
          </div>
          <div class="item">
            <div class="item-label">Số điện thoại</div>
            <div class="item-value">${currentInvoice.value?.booking?.guestPhone || currentInvoice.value?.booking?.customer?.phone || ''}</div>
          </div>
        </div>
      </div>
      
      <div class="invoice-section">
        <h4>Thông tin phòng</h4>
        <div class="grid-3">
          <div class="item">
            <div class="item-label">Phòng</div>
            <div class="item-value">P.${currentInvoice.value?.booking?.room?.roomNumber || ''}</div>
          </div>
          <div class="item">
            <div class="item-label">Ngày đến</div>
            <div class="item-value">${formatDate(currentInvoice.value?.booking?.checkInDate)}</div>
          </div>
          <div class="item">
            <div class="item-label">Ngày đi</div>
            <div class="item-value">${formatDate(currentInvoice.value?.booking?.checkOutDate)}</div>
          </div>
        </div>
      </div>
      
      <div class="total-section">
        <div class="total-row">
          <span class="total-label">Phòng</span>
          <span class="total-value">${formatCurrency(currentInvoice.value?.roomCharges)}</span>
        </div>
        ${(currentInvoice.value?.serviceCharges || 0) > 0 ? `
        <div class="total-row">
          <span class="total-label">Dịch vụ</span>
          <span class="total-value">${formatCurrency(currentInvoice.value?.serviceCharges)}</span>
        </div>
        ` : ''}
        <div class="total-row grand-total">
          <span class="total-label">Tổng cộng</span>
          <span class="total-value">${formatCurrency(currentInvoice.value?.totalAmount)}</span>
        </div>
      </div>
      
      <div class="status-section">
        <div>
          <div class="status-label">Trạng thái thanh toán</div>
          <div class="status-value">${currentInvoice.value?.paymentStatus === 'PAID' ? 'Đã thanh toán' : 'Chưa thanh toán'}</div>
        </div>
        <div style="text-align: right;">
          <div class="status-label">Ngày thanh toán</div>
          <div class="status-value">${currentInvoice.value?.paymentDate ? formatDateTime(currentInvoice.value.paymentDate) : '--'}</div>
        </div>
      </div>
    </body>
    </html>
  `)
  
  printWindow.document.close()
  printWindow.focus()
  
  setTimeout(() => {
    printWindow.print()
  }, 500)
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
