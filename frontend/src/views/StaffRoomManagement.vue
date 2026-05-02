<template>
  <div class="min-h-screen flex bg-gray-50 font-sans">
    <!-- Sidebar -->
    <aside class="w-72 bg-[#004d26] text-white min-h-screen flex flex-col shadow-2xl shrink-0">
      <div class="p-8 mb-4">
        <div class="flex items-center gap-3">
          <div class="w-12 h-12 bg-white/10 rounded-full flex items-center justify-center font-black text-2xl border border-white/20">H</div>
          <h1 class="text-2xl font-black uppercase tracking-tight">Grand Hotel</h1>
        </div>
      </div>
      
      <nav class="flex-1 px-4 space-y-1">
        <router-link to="/staff-dashboard" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group hover:bg-white/10">
          <i class="fas fa-th-large text-xl opacity-70 group-hover:opacity-100"></i>
          <span class="font-bold text-lg tracking-tight">Tổng quan</span>
        </router-link>

        <router-link to="/staff-rooms" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group" :class="{'bg-white/10': $route.path === '/staff-rooms', 'hover:bg-white/10': $route.path !== '/staff-rooms'}">
          <i class="fas fa-door-open text-xl opacity-70 group-hover:opacity-100" :class="{'opacity-100': $route.path === '/staff-rooms'}"></i>
          <span class="font-bold text-lg tracking-tight">Quản lý Phòng</span>
        </router-link>

        <router-link to="/bookings" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group hover:bg-white/10">
          <i class="fas fa-calendar-alt text-xl opacity-70 group-hover:opacity-100"></i>
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
      <header class="mb-10">
        <h2 class="text-3xl font-black text-gray-900 uppercase tracking-tight mb-2">Danh sách phòng khách sạn</h2>
        <p class="text-gray-500 font-medium">Hiển thị {{ filteredRooms.length }} phòng của toàn khách sạn</p>
      </header>

      <!-- Filter Tabs -->
      <div class="flex gap-4 mb-10">
        <button 
          @click="currentFilter = 'ALL'" 
          :class="currentFilter === 'ALL' ? 'bg-[#005c32] text-white' : 'bg-white text-gray-600 border border-gray-200 hover:bg-gray-50'"
          class="px-8 py-3 rounded-full font-black uppercase tracking-widest text-[11px] transition-all"
        >
          Xem tất cả
        </button>
        <button 
          v-for="cat in ['STANDARD', 'DELUXE', 'SUITE']" 
          :key="cat"
          @click="currentFilter = cat" 
          :class="currentFilter === cat ? 'bg-[#005c32] text-white' : 'bg-white text-gray-600 border border-gray-200 hover:bg-gray-50'"
          class="px-8 py-3 rounded-full font-black uppercase tracking-widest text-[11px] transition-all"
        >
          {{ cat }}
        </button>
      </div>

      <!-- Rooms Grid -->
      <div v-if="loading" class="flex justify-center py-20">
        <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-[#005c32]"></div>
      </div>

      <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        <div 
          v-for="room in filteredRooms" 
          :key="room.id"
          class="bg-white rounded-[2.5rem] shadow-xl shadow-gray-200 overflow-hidden border border-gray-50 group transition-all duration-500 hover:-translate-y-2"
        >
          <!-- Room Image -->
          <div class="relative h-64 overflow-hidden">
            <img :src="room.imageUrl" :alt="room.roomNumber" class="w-full h-full object-cover transition-transform duration-700 group-hover:scale-110">
            <div class="absolute top-6 right-6">
              <button class="w-12 h-12 bg-[#005c32] text-white rounded-full flex items-center justify-center shadow-lg transform transition-transform group-hover:rotate-45">
                <i class="fas fa-arrow-right"></i>
              </button>
            </div>
          </div>

          <!-- Room Content -->
          <div class="p-8">
            <div class="flex justify-between items-start mb-4">
              <div>
                <h3 class="text-2xl font-black text-gray-900 uppercase tracking-tight mb-2">Phòng {{ room.roomNumber }}</h3>
                <div class="flex gap-1 text-[#f39200] text-xs">
                  <i v-for="i in 5" :key="i" class="fas fa-star"></i>
                </div>
              </div>
              <button @click="openEditModal(room)" class="p-2 bg-amber-50 text-amber-600 rounded-lg hover:bg-amber-100 transition-colors shadow-sm">
                <i class="fas fa-edit"></i>
              </button>
            </div>

            <!-- Tags -->
            <div class="flex flex-wrap gap-2 mb-8">
              <span class="px-4 py-1.5 bg-[#005c32] text-white rounded-full text-[10px] font-black uppercase tracking-widest">{{ room.category }}</span>
              <span class="px-4 py-1.5 bg-green-50 text-[#005c32] rounded-full text-[10px] font-black uppercase tracking-widest">{{ room.type }}</span>
              <span class="px-4 py-1.5 bg-gray-50 text-gray-400 rounded-full text-[10px] font-black uppercase tracking-widest">Tầng {{ room.roomNumber.charAt(0) }}</span>
            </div>

            <div class="flex justify-between items-end border-t border-gray-50 pt-6">
              <div class="space-y-4">
                <div>
                  <p class="text-[10px] text-gray-400 font-bold uppercase tracking-widest mb-1">Sức chứa</p>
                  <p class="font-black text-gray-900">{{ room.capacity }} người</p>
                </div>
                <div>
                  <p class="text-2xl font-black text-[#005c32]">{{ formatPrice(room.price) }}</p>
                  <p class="text-[10px] text-gray-400 font-bold uppercase tracking-widest mt-1">Mỗi đêm</p>
                </div>
              </div>
              <div class="text-right">
                <p class="text-[10px] text-gray-400 font-bold uppercase tracking-widest mb-1">Trạng thái</p>
                <p :class="getStatusClass(room.status)" class="font-black">{{ getStatusLabel(room.status) }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>

    <!-- Edit Room Modal -->
    <div v-if="showEditModal && editingRoom" class="fixed inset-0 z-[100] flex items-center justify-center p-6 bg-slate-900/60 backdrop-blur-md">
      <div class="bg-white rounded-[3rem] max-w-2xl w-full p-12 shadow-2xl transform transition-all animate-in fade-in zoom-in duration-300">
        <div class="flex justify-between items-start mb-10">
          <div>
            <h3 class="text-4xl font-black text-slate-900 uppercase tracking-tighter">Chỉnh sửa phòng</h3>
            <p class="text-lg text-emerald-600 font-bold mt-2">Phòng {{ editingRoom.roomNumber }}</p>
          </div>
          <button @click="closeEditModal" class="w-12 h-12 flex items-center justify-center rounded-2xl bg-slate-100 text-slate-400 hover:bg-rose-500 hover:text-white transition-all">
            <i class="fas fa-times text-xl"></i>
          </button>
        </div>

        <form @submit.prevent="updateRoom" class="space-y-6">
          <div class="grid grid-cols-2 gap-6">
            <div>
              <label class="block text-[10px] font-black uppercase tracking-[0.2em] text-slate-400 mb-2">Loại phòng</label>
              <select v-model="editingRoom.category" class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold">
                <option value="Standard">Standard</option>
                <option value="Deluxe">Deluxe</option>
                <option value="Suite">Suite</option>
              </select>
            </div>
            <div>
              <label class="block text-[10px] font-black uppercase tracking-[0.2em] text-slate-400 mb-2">Kiểu phòng</label>
              <input v-model="editingRoom.type" type="text" class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold">
            </div>
          </div>

          <div class="grid grid-cols-2 gap-6">
            <div>
              <label class="block text-[10px] font-black uppercase tracking-[0.2em] text-slate-400 mb-2">Giá phòng ($)</label>
              <input v-model.number="editingRoom.price" type="number" class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold">
            </div>
            <div>
              <label class="block text-[10px] font-black uppercase tracking-[0.2em] text-slate-400 mb-2">Sức chứa</label>
              <input v-model.number="editingRoom.capacity" type="number" class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold">
            </div>
          </div>

          <div>
            <label class="block text-[10px] font-black uppercase tracking-[0.2em] text-slate-400 mb-2">Trạng thái</label>
            <select v-model="editingRoom.status" class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold">
              <option value="AVAILABLE">Đã dọn xong - Còn trống</option>
              <option value="OCCUPIED">Có khách lưu trú</option>
              <option value="CLEANING">Đang dọn dẹp</option>
              <option value="MAINTENANCE">Đang bảo trì</option>
            </select>
          </div>

          <button 
            type="submit"
            class="w-full bg-[#004d26] hover:bg-[#003d1e] text-white py-6 rounded-[2rem] font-black uppercase tracking-widest transition-all shadow-xl shadow-green-200/50 flex items-center justify-center gap-3 mt-4"
          >
            <i class="fas fa-save"></i>
            <span>Lưu thay đổi</span>
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
const rooms = ref([])
const loading = ref(true)
const currentFilter = ref('ALL')
const showEditModal = ref(false)
const editingRoom = ref(null)

const openEditModal = (room) => {
  editingRoom.value = { ...room }
  showEditModal.value = true
}

const closeEditModal = () => {
  showEditModal.value = false
  editingRoom.value = null
}

const updateRoom = async () => {
  try {
    await axios.put(`/rooms/${editingRoom.value.id}`, editingRoom.value)
    alert('Cập nhật thông tin phòng thành công!')
    closeEditModal()
    fetchRooms()
  } catch (error) {
    console.error('Lỗi khi cập nhật phòng:', error)
    alert('Không thể cập nhật thông tin phòng.')
  }
}

const fetchRooms = async () => {
  try {
    loading.value = true
    const response = await axios.get('/rooms')
    rooms.value = response.data.sort((a, b) => a.roomNumber.localeCompare(b.roomNumber))
  } catch (error) {
    console.error('Lỗi khi lấy danh sách phòng:', error)
  } finally {
    loading.value = false
  }
}

const filteredRooms = computed(() => {
  if (currentFilter.value === 'ALL') return rooms.value
  return rooms.value.filter(r => r.category.toUpperCase() === currentFilter.value)
})

const formatPrice = (price) => {
  if (!price) return '0 đ'
  return `${new Intl.NumberFormat('vi-VN').format(price * 25000)} đ`
}

const getStatusLabel = (status) => {
  switch (status) {
    case 'AVAILABLE': return 'Còn trống'
    case 'OCCUPIED': return 'Có khách'
    case 'CLEANING': return 'Đang dọn'
    case 'MAINTENANCE': return 'Bảo trì'
    case 'BOOKED': return 'Đã đặt'
    default: return status
  }
}

const getStatusClass = (status) => {
  switch (status) {
    case 'AVAILABLE': return 'text-[#005c32]'
    case 'OCCUPIED': return 'text-rose-600'
    case 'CLEANING': return 'text-amber-600'
    case 'MAINTENANCE': return 'text-slate-400'
    case 'BOOKED': return 'text-blue-600'
    default: return 'text-gray-900'
  }
}

const logout = () => {
  localStorage.removeItem('user')
  router.push('/login')
}

onMounted(() => {
  fetchRooms()
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
  background: #005c32;
  border-radius: 10px;
}
main::-webkit-scrollbar-thumb:hover {
  background: #004d26;
}
</style>
