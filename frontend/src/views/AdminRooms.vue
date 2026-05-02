<template>
  <div class="min-h-screen flex bg-[#f0f2f5] font-sans text-[#1e293b]">
    <AdminSidebar />

    <main class="flex-1 p-10 overflow-y-auto">
      <header class="flex flex-col lg:flex-row lg:items-center lg:justify-between gap-6 mb-8">
        <div>
          <h2 class="text-3xl font-black tracking-tight text-slate-800">Quản lý phòng</h2>
          <p class="text-sm text-slate-400 font-bold mt-2">Quản lý danh sách phòng, trạng thái và thông tin hiển thị</p>
        </div>
        <button @click="openCreateModal" class="bg-[#004d26] hover:bg-[#003d1e] text-white px-8 py-4 rounded-2xl font-black uppercase tracking-widest text-xs transition-all shadow-xl shadow-green-100 flex items-center gap-3">
          <i class="fas fa-plus"></i>
          Thêm phòng
        </button>
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
              <tr v-for="room in filteredRooms" :key="room.id" class="hover:bg-slate-50/50 transition-colors">
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
                    <button @click="deleteRoom(room)" class="w-10 h-10 rounded-xl bg-slate-50 text-slate-400 hover:bg-rose-500 hover:text-white transition-all flex items-center justify-center shadow-sm">
                      <i class="fas fa-trash"></i>
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <div v-if="showModal" class="fixed inset-0 z-[100] flex items-center justify-center p-6 bg-slate-900/60 backdrop-blur-md">
        <div class="bg-white rounded-[3rem] max-w-2xl w-full overflow-hidden shadow-2xl">
          <div class="bg-[#004d26] p-8 text-white flex justify-between items-center">
            <div>
              <h3 class="text-2xl font-black uppercase tracking-tight">{{ editingId ? 'Cập nhật phòng' : 'Thêm phòng mới' }}</h3>
              <p class="text-green-100/60 text-[10px] font-bold uppercase tracking-widest mt-1">Quản lý trực tiếp bảng `room`</p>
            </div>
            <button @click="closeModal" class="w-10 h-10 flex items-center justify-center rounded-xl bg-white/10 text-white hover:bg-rose-500 transition-all">
              <i class="fas fa-times"></i>
            </button>
          </div>

          <form @submit.prevent="submitForm" class="p-8 space-y-5">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-5">
              <div class="space-y-2">
                <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Số phòng</label>
                <input v-model="form.roomNumber" required type="text" class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold text-sm">
              </div>
              <div class="space-y-2">
                <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Kiểu phòng</label>
                <input v-model="form.type" required type="text" class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold text-sm">
              </div>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-3 gap-5">
              <div class="space-y-2">
                <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Loại phòng</label>
                <select v-model="form.category" class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold text-sm">
                  <option value="Standard">Standard</option>
                  <option value="Deluxe">Deluxe</option>
                  <option value="Suite">Suite</option>
                </select>
              </div>
              <div class="space-y-2">
                <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Giá</label>
                <input v-model.number="form.price" required min="0" step="0.01" type="number" class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold text-sm">
              </div>
              <div class="space-y-2">
                <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Sức chứa</label>
                <input v-model.number="form.capacity" required min="1" type="number" class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold text-sm">
              </div>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-5">
              <div class="space-y-2">
                <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Trạng thái</label>
                <select v-model="form.status" class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold text-sm">
                  <option value="AVAILABLE">Đang trống</option>
                  <option value="BOOKED">Đang đặt</option>
                  <option value="OCCUPIED">Đã có khách</option>
                  <option value="CLEANING">Đang dọn</option>
                  <option value="MAINTENANCE">Bảo trì</option>
                </select>
              </div>
              <div class="space-y-2">
                <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Ảnh URL</label>
                <input v-model="form.imageUrl" type="text" class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold text-sm">
              </div>
            </div>

            <div class="space-y-2">
              <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Mô tả</label>
              <textarea v-model="form.description" rows="4" class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold text-sm resize-none"></textarea>
            </div>

            <button type="submit" :disabled="submitting" class="w-full bg-[#004d26] hover:bg-[#003d1e] disabled:opacity-50 text-white py-4 rounded-[2rem] font-black uppercase tracking-widest transition-all shadow-xl shadow-green-100">
              {{ submitting ? 'Đang lưu...' : (editingId ? 'Cập nhật phòng' : 'Tạo phòng') }}
            </button>
          </form>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import axios from '../api/axios'
import AdminSidebar from '../components/AdminSidebar.vue'

const loading = ref(false)
const submitting = ref(false)
const rooms = ref([])
const showModal = ref(false)
const editingId = ref(null)

const filters = ref({
  search: '',
  category: '',
  status: ''
})

const createDefaultForm = () => ({
  roomNumber: '',
  category: 'Standard',
  type: 'Standard Room',
  capacity: 2,
  price: 0,
  description: '',
  imageUrl: '',
  status: 'AVAILABLE'
})

const form = ref(createDefaultForm())

const fetchRooms = async () => {
  try {
    loading.value = true
    const response = await axios.get('/rooms')
    rooms.value = [...response.data].sort((a, b) => String(a.roomNumber).localeCompare(String(b.roomNumber)))
  } catch (error) {
    console.error('Lỗi tải phòng:', error)
    alert('Không thể tải danh sách phòng.')
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

const openCreateModal = () => {
  editingId.value = null
  form.value = createDefaultForm()
  showModal.value = true
}

const openEditModal = (room) => {
  editingId.value = room.id
  form.value = {
    roomNumber: room.roomNumber || '',
    category: room.category || 'Standard',
    type: room.type || '',
    capacity: room.capacity || 2,
    price: room.price || 0,
    description: room.description || '',
    imageUrl: room.imageUrl || '',
    status: room.status || 'AVAILABLE'
  }
  showModal.value = true
}

const closeModal = () => {
  showModal.value = false
  editingId.value = null
}

const submitForm = async () => {
  try {
    submitting.value = true
    if (editingId.value) {
      await axios.put(`/rooms/${editingId.value}`, form.value)
      alert('Cập nhật phòng thành công!')
    } else {
      await axios.post('/rooms', form.value)
      alert('Thêm phòng thành công!')
    }
    closeModal()
    await fetchRooms()
  } catch (error) {
    console.error('Lỗi lưu phòng:', error)
    alert('Không thể lưu thông tin phòng.')
  } finally {
    submitting.value = false
  }
}

const deleteRoom = async (room) => {
  if (!confirm(`Bạn có chắc muốn xóa phòng ${room.roomNumber}?`)) return

  try {
    await axios.delete(`/rooms/${room.id}`)
    await fetchRooms()
    alert('Đã xóa phòng.')
  } catch (error) {
    console.error('Lỗi xóa phòng:', error)
    alert('Không thể xóa phòng.')
  }
}

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
    case 'CLEANING': return 'bg-slate-100 text-slate-600'
    case 'MAINTENANCE': return 'bg-rose-50 text-rose-600'
    default: return 'bg-slate-50 text-slate-400'
  }
}

onMounted(() => {
  fetchRooms()
})
</script>

<style scoped>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css');

main::-webkit-scrollbar {
  width: 6px;
}

main::-webkit-scrollbar-track {
  background: transparent;
}

main::-webkit-scrollbar-thumb {
  background: #cbd5e1;
  border-radius: 10px;
}
</style>
