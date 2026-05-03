<template>
  <div class="min-h-screen flex bg-gray-50 font-sans">
    <!-- Sidebar -->
    <aside class="w-72 bg-[#004d26] text-white min-h-screen flex flex-col shadow-2xl shrink-0 z-50">
      <div class="p-8 border-b border-white/5">
        <div class="flex items-center gap-3">
          <div class="w-10 h-10 bg-white/10 rounded-lg flex items-center justify-center font-black text-xl border border-white/20">H</div>
          <div>
            <h1 class="text-lg font-black uppercase tracking-tight leading-none">Grand Hotel</h1>
          </div>
        </div>
      </div>
      
      <nav class="flex-1 px-4 mt-8 space-y-1">
        <router-link to="/staff-dashboard" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group" :class="$route.path === '/staff-dashboard' ? 'bg-white/10 text-white shadow-lg' : 'text-white/60 hover:bg-white/5 hover:text-white'">
          <i class="fas fa-chart-pie text-lg"></i>
          <span class="font-bold text-sm tracking-tight">Tổng quan</span>
        </router-link>

        <router-link to="/staff-rooms" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group" :class="$route.path === '/staff-rooms' ? 'bg-white/10 text-white shadow-lg' : 'text-white/60 hover:bg-white/5 hover:text-white'">
          <i class="fas fa-bed text-lg"></i>
          <span class="font-bold text-sm tracking-tight">Quản lý Phòng</span>
        </router-link>

        <router-link to="/bookings" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group" :class="$route.path === '/bookings' ? 'bg-white/10 text-white shadow-lg' : 'text-white/60 hover:bg-white/5 hover:text-white'">
          <i class="fas fa-calendar-check text-lg"></i>
          <span class="font-bold text-sm tracking-tight">Quản lý Đặt phòng</span>
        </router-link>

        <router-link to="/manage-services" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group" :class="$route.path === '/manage-services' ? 'bg-white/10 text-white shadow-lg' : 'text-white/60 hover:bg-white/5 hover:text-white'">
          <i class="fas fa-concierge-bell text-lg"></i>
          <span class="font-bold text-sm tracking-tight">Quản lý Dịch vụ</span>
        </router-link>

        <router-link to="/staff-messages" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group" :class="$route.path === '/staff-messages' ? 'bg-white/10 text-white shadow-lg' : 'text-white/60 hover:bg-white/5 hover:text-white'">
          <i class="fas fa-comment-alt text-lg"></i>
          <span class="font-bold text-sm tracking-tight">Phản hồi khách hàng</span>
        </router-link>
      </nav>

      <div class="p-8 border-t border-white/5">
        <button @click="logout" class="w-full py-4 px-6 bg-rose-500/10 hover:bg-rose-500 text-rose-500 hover:text-white rounded-xl transition-all duration-300 flex items-center justify-center gap-3 font-black uppercase tracking-widest text-[10px]">
          <i class="fas fa-sign-out-alt"></i>
          <span>Đăng xuất</span>
        </button>
      </div>
    </aside>

    <!-- Main Content -->
    <main class="flex-1 p-10 overflow-y-auto">
      <header class="flex flex-col lg:flex-row lg:items-center lg:justify-between gap-6 mb-8">
        <div>
          <h2 class="text-3xl font-black tracking-tight text-slate-800">Quản lý phòng</h2>
        </div>
      </header>

      <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
        <div class="bg-white rounded-[2rem] p-6 shadow-sm border border-slate-100">
          <p class="text-[10px] font-black text-slate-400 uppercase tracking-widest mb-2">Tổng số phòng</p>
          <p class="text-3xl font-black text-slate-800">{{ rooms.length }}</p>
        </div>
        <div class="bg-white rounded-[2rem] p-6 shadow-sm border border-slate-100">
          <p class="text-[10px] font-black text-slate-400 uppercase tracking-widest mb-2">Đang trống</p>
          <p class="text-3xl font-black text-emerald-600">{{ countByStatus('AVAILABLE') }}</p>
        </div>
        <div class="bg-white rounded-[2rem] p-6 shadow-sm border border-slate-100">
          <p class="text-[10px] font-black text-slate-400 uppercase tracking-widest mb-2">Đang sử dụng</p>
          <p class="text-3xl font-black text-blue-600">{{ countByStatus('OCCUPIED') + countByStatus('BOOKED') }}</p>
        </div>
        <div class="bg-white rounded-[2rem] p-6 shadow-sm border border-slate-100">
          <p class="text-[10px] font-black text-slate-400 uppercase tracking-widest mb-2">Bảo trì / dọn dẹp</p>
          <p class="text-3xl font-black text-rose-600">{{ countByStatus('MAINTENANCE') + countByStatus('CLEANING') }}</p>
        </div>
      </div>

      <div class="bg-white rounded-[2.5rem] shadow-sm border border-slate-100 overflow-hidden">
        <div class="p-8 border-b border-slate-50 bg-slate-50/50 flex flex-col xl:flex-row gap-4 xl:items-center xl:justify-between">
          <div class="flex flex-col lg:flex-row gap-4 w-full">
            <div class="relative w-full lg:w-80">
              <i class="fas fa-search absolute left-5 top-1/2 -translate-y-1/2 text-slate-300"></i>
              <input v-model="filters.search" type="text" placeholder="Tìm kiếm số phòng..." class="w-full bg-white border-2 border-slate-100 rounded-xl pl-12 pr-5 py-3 outline-none focus:border-[#004d26] transition-all font-bold text-sm">
            </div>
            <select v-model="filters.category" class="bg-white border-2 border-slate-100 rounded-xl px-4 py-3 outline-none focus:border-[#004d26] font-bold text-sm text-slate-600">
              <option value="">Tất cả loại phòng</option>
              <option value="Standard">Standard</option>
              <option value="Deluxe">Deluxe</option>
              <option value="Suite">Suite</option>
            </select>
            <select v-model="filters.status" class="bg-white border-2 border-slate-100 rounded-xl px-4 py-3 outline-none focus:border-[#004d26] font-bold text-sm text-slate-600">
              <option value="">Tất cả trạng thái</option>
              <option value="AVAILABLE">Đang trống</option>
              <option value="BOOKED">Đang đặt</option>
              <option value="OCCUPIED">Đã có khách</option>
              <option value="CLEANING">Đang dọn</option>
              <option value="MAINTENANCE">Bảo trì</option>
            </select>
          </div>
        </div>

        <div v-if="loading" class="p-16 text-center text-slate-400 font-bold">Đang tải danh sách phòng...</div>
        <div v-else-if="filteredRooms.length === 0" class="p-16 text-center text-slate-400 font-bold">Không có phòng phù hợp.</div>
        <div v-else class="overflow-x-auto">
          <table class="w-full">
            <thead>
              <tr class="bg-slate-50/50 text-left">
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Số phòng</th>
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Loại phòng</th>
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Kiểu phòng</th>
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Giá / đêm</th>
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Sức chứa</th>
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Trạng thái</th>
                <th class="px-8 py-5 text-center text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Thao tác</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-slate-50">
              <tr v-for="room in paginatedRooms" :key="room.id" class="hover:bg-slate-50/50 transition-colors">
                <td class="px-8 py-6">
                  <div class="font-black text-slate-800">{{ room.roomNumber }}</div>
                </td>
                <td class="px-8 py-6 text-sm font-bold text-slate-700">{{ room.category || '--' }}</td>
                <td class="px-8 py-6 text-sm font-bold text-slate-500">{{ room.type || '--' }}</td>
                <td class="px-8 py-6 text-sm font-black text-emerald-600">{{ formatPrice(room.price) }}</td>
                <td class="px-8 py-6 text-sm font-bold text-slate-500">{{ room.capacity || 0 }} người</td>
                <td class="px-8 py-6">
                  <span :class="getStatusClass(room.status)" class="px-4 py-1.5 rounded-full text-[10px] font-black uppercase tracking-widest">
                    {{ getStatusLabel(room.status) }}
                  </span>
                </td>
                <td class="px-8 py-6">
                  <div class="flex justify-center gap-2">
                    <button @click="openEditModal(room)" class="w-10 h-10 rounded-xl bg-slate-50 text-slate-400 hover:bg-blue-50 hover:text-blue-600 transition-all flex items-center justify-center shadow-sm">
                      <i class="fas fa-pen"></i>
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <!-- Pagination -->
        <div v-if="filteredRooms.length > 0" class="p-8 bg-slate-50/30 border-t border-slate-50 flex flex-col md:flex-row justify-between items-center gap-6">
          <p class="text-[11px] font-black text-slate-400 uppercase tracking-widest">
            Hiển thị <span class="text-slate-800">{{ startIndex + 1 }} - {{ Math.min(endIndex, filteredRooms.length) }}</span> trong <span class="text-slate-800">{{ filteredRooms.length }}</span> phòng
          </p>
          <div class="flex items-center gap-2">
            <button @click="currentPage--" :disabled="currentPage === 1" class="w-10 h-10 rounded-xl bg-white border-2 border-slate-100 text-slate-400 hover:border-[#004d26] hover:text-[#004d26] disabled:opacity-30 disabled:hover:border-slate-100 disabled:hover:text-slate-400 transition-all flex items-center justify-center">
              <i class="fas fa-chevron-left text-xs"></i>
            </button>
            
            <div class="flex items-center gap-2">
              <button v-for="page in totalPages" :key="page" @click="currentPage = page" :class="[currentPage === page ? 'bg-[#004d26] border-[#004d26] text-white' : 'bg-white border-slate-100 text-slate-500 hover:border-[#004d26]']" class="w-10 h-10 rounded-xl border-2 font-black text-xs transition-all">
                {{ page }}
              </button>
            </div>

            <button @click="currentPage++" :disabled="currentPage === totalPages" class="w-10 h-10 rounded-xl bg-white border-2 border-slate-100 text-slate-400 hover:border-[#004d26] hover:text-[#004d26] disabled:opacity-30 disabled:hover:border-slate-100 disabled:hover:text-slate-400 transition-all flex items-center justify-center">
              <i class="fas fa-chevron-right text-xs"></i>
            </button>
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
import { ref, computed, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import axios from '../api/axios'

const router = useRouter()
const rooms = ref([])
const loading = ref(true)
const showEditModal = ref(false)
const editingRoom = ref(null)

// Pagination state
const currentPage = ref(1)
const itemsPerPage = ref(10)

const filters = ref({
  search: '',
  category: '',
  status: ''
})

watch([() => filters.value.search, () => filters.value.category, () => filters.value.status], () => {
  currentPage.value = 1
})

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
    await fetchRooms() // Đảm bảo đợi dữ liệu mới nhất từ server
  } catch (error) {
    console.error('Lỗi khi cập nhật phòng:', error)
    alert('Không thể cập nhật thông tin phòng.')
  }
}

const fetchRooms = async () => {
  try {
    loading.value = true
    const response = await axios.get('/rooms')
    rooms.value = response.data.sort((a, b) => String(a.roomNumber).localeCompare(String(b.roomNumber)))
  } catch (error) {
    console.error('Lỗi khi lấy danh sách phòng:', error)
  } finally {
    loading.value = false
  }
}

const filteredRooms = computed(() => {
  const query = filters.value.search.trim().toLowerCase()
  return rooms.value.filter((room) => {
    const matchesSearch = !query || String(room.roomNumber).toLowerCase().includes(query)
    const matchesCategory = !filters.value.category || room.category === filters.value.category
    const matchesStatus = !filters.value.status || room.status === filters.value.status
    return matchesSearch && matchesCategory && matchesStatus
  })
})

const totalPages = computed(() => Math.ceil(filteredRooms.value.length / itemsPerPage.value))
const startIndex = computed(() => (currentPage.value - 1) * itemsPerPage.value)
const endIndex = computed(() => startIndex.value + itemsPerPage.value)
const paginatedRooms = computed(() => filteredRooms.value.slice(startIndex.value, endIndex.value))

const countByStatus = (status) => rooms.value.filter(room => room.status === status).length

const formatPrice = (price) => {
  if (!price) return '0 đ'
  return `${new Intl.NumberFormat('vi-VN').format(price * 25000)} đ`
}

const getStatusLabel = (status) => {
  switch (status) {
    case 'AVAILABLE': return 'Đang trống'
    case 'BOOKED': return 'Đang đặt'
    case 'OCCUPIED': return 'Đã có khách'
    case 'CLEANING': return 'Đang dọn'
    case 'MAINTENANCE': return 'Bảo trì'
    default: return status
  }
}

const getStatusClass = (status) => {
  switch (status) {
    case 'AVAILABLE': return 'bg-emerald-50 text-emerald-600'
    case 'BOOKED': return 'bg-amber-50 text-amber-600'
    case 'OCCUPIED': return 'bg-blue-50 text-blue-600'
    case 'CLEANING': return 'bg-purple-50 text-purple-600'
    case 'MAINTENANCE': return 'bg-rose-50 text-rose-600'
    default: return 'bg-slate-50 text-slate-600'
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
