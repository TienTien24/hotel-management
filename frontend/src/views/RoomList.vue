<template>
  <div class="min-h-screen bg-white">
    <section class="bg-emerald-950 py-20 text-white relative overflow-hidden">
      <div class="absolute inset-0 opacity-10">
        <img src="https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80" class="w-full h-full object-cover">
      </div>
      <div class="max-w-7xl mx-auto px-4 relative z-10 text-center">
        <h1 class="text-5xl md:text-7xl font-black uppercase tracking-tighter mb-6">Booking <span class="text-emerald-400">Online</span></h1>
        <p class="text-emerald-100 font-light tracking-[0.3em] uppercase text-sm">Tìm phòng, xem giá và đặt phòng trực tuyến</p>
      </div>
    </section>

    <div class="max-w-7xl mx-auto px-4 py-16">
      <section class="bg-white border border-emerald-100 rounded-[2rem] shadow-sm p-6 md:p-8 mb-10">
        <div class="flex flex-col lg:flex-row lg:items-start lg:justify-between gap-6">
          <div class="flex items-start gap-4">
            <router-link to="/" class="bg-emerald-50 text-emerald-800 w-14 h-14 rounded-2xl flex items-center justify-center hover:bg-emerald-100 transition-colors shrink-0">
              <i class="fas fa-arrow-left"></i>
            </router-link>
            <div>
              <h2 class="text-2xl md:text-3xl font-black text-emerald-950 uppercase tracking-tighter">{{ pageTitle }}</h2>
              <p class="text-sm text-gray-500 mt-2">{{ loading ? 'Đang tải dữ liệu...' : resultText }}</p>
            </div>
          </div>

          <button v-if="isAdmin" @click="openAddModal" class="bg-emerald-800 hover:bg-emerald-900 text-white px-8 py-4 rounded-2xl font-black uppercase tracking-widest text-xs transition shadow-xl transform hover:-translate-y-1">
            <i class="fas fa-plus mr-2"></i> Thêm phòng mới
          </button>
        </div>

        <div class="flex flex-wrap gap-3 mt-8">
          <button
            v-for="option in categoryOptions"
            :key="option.value"
            @click="selectCategory(option.value)"
            :class="[
              'px-5 py-3 rounded-full text-sm font-black uppercase tracking-wider transition-all border',
              activeCategory === option.value
                ? 'bg-emerald-900 text-white border-emerald-900 shadow-lg'
                : 'bg-white text-emerald-900 border-emerald-200 hover:bg-emerald-50'
            ]"
          >
            {{ option.label }}
          </button>
        </div>
      </section>

      <div v-if="loading" class="text-center py-20 text-gray-400 font-bold uppercase tracking-widest text-xs">
        Đang tìm phòng...
      </div>

      <div v-else class="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-8">
        <div v-for="room in rooms" :key="room.id" class="group bg-white rounded-[2rem] overflow-hidden shadow-lg hover:shadow-2xl transition-all duration-500 border border-emerald-100/70">
          <div class="relative h-56 overflow-hidden">
            <img :src="room.imageUrl || 'https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=800&q=80'" class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-700">
            <div class="absolute inset-0 bg-gradient-to-t from-emerald-950/25 via-transparent to-transparent"></div>
          </div>

          <div class="p-6">
            <div class="flex items-start justify-between gap-4 mb-4">
              <div>
                <h4 class="text-2xl font-black uppercase tracking-tighter text-emerald-950">Phòng {{ room.roomNumber }}</h4>
                <div class="flex text-yellow-400 text-[10px] mt-2">
                  <i v-for="i in 5" :key="i" class="fas fa-star"></i>
                </div>
              </div>

              <router-link :to="'/rooms/' + room.id" class="w-12 h-12 rounded-full bg-emerald-900 text-white flex items-center justify-center hover:bg-emerald-800 transition-colors shadow-lg shrink-0">
                <i class="fas fa-arrow-right"></i>
              </router-link>
            </div>

            <div class="flex flex-wrap gap-2 mb-5">
              <span class="bg-emerald-900 text-white px-3 py-1.5 rounded-full text-[10px] font-black uppercase tracking-widest">{{ room.category }}</span>
              <span class="bg-emerald-50 text-emerald-800 px-3 py-1.5 rounded-full text-[10px] font-black uppercase tracking-widest">{{ room.type }}</span>
              <span class="bg-white border border-emerald-100 text-gray-400 px-3 py-1.5 rounded-full text-[10px] font-black uppercase tracking-widest">{{ getFloorLabel(room) }}</span>
            </div>

            <div class="grid grid-cols-2 gap-4 mb-6">
              <div class="bg-gray-50 rounded-2xl px-4 py-3">
                <div class="text-[10px] text-gray-400 font-black uppercase tracking-widest">Sức chứa</div>
                <div class="text-emerald-900 text-base font-black mt-1">{{ room.capacity || 0 }} người</div>
              </div>
              <div class="bg-gray-50 rounded-2xl px-4 py-3">
                <div class="text-[10px] text-gray-400 font-black uppercase tracking-widest">Trạng thái</div>
                <div :class="getAvailabilityTextClass(room)" class="text-base font-black mt-1">{{ getAvailabilityLabel(room) }}</div>
              </div>
            </div>

            <div class="flex items-end justify-between gap-4">
              <div>
                <p class="text-3xl font-black text-emerald-900 leading-none">{{ formatPrice(room.price) }}</p>
                <p class="text-[10px] text-gray-400 font-bold uppercase tracking-widest mt-2">mỗi đêm</p>
              </div>

              <div class="flex space-x-2">
                <button v-if="isAdmin || isStaff" @click="editRoom(room)" class="w-10 h-10 bg-yellow-50 text-yellow-600 rounded-xl flex items-center justify-center hover:bg-yellow-100 transition-all">
                  <i class="fas fa-edit"></i>
                </button>
                <button v-if="isAdmin" @click="deleteRoom(room.id)" class="w-10 h-10 bg-red-50 text-red-600 rounded-xl flex items-center justify-center hover:bg-red-100 transition-all">
                  <i class="fas fa-trash"></i>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div v-if="!loading && !rooms.length" class="text-center py-20">
        <p class="text-gray-400 font-bold uppercase tracking-widest text-xs">Hiện chưa có dữ liệu phòng để hiển thị.</p>
      </div>

      <div v-if="showAddModal" class="fixed inset-0 bg-emerald-950/80 backdrop-blur-sm flex items-center justify-center p-4 z-50">
        <div class="bg-white rounded-[2.5rem] max-w-lg w-full p-10 shadow-2xl overflow-hidden">
          <div class="flex justify-between items-center mb-10">
            <h3 class="text-3xl font-black text-emerald-950 uppercase tracking-tighter">{{ currentRoom.id ? 'Sửa thông tin' : 'Thêm phòng mới' }}</h3>
            <button @click="closeModal" class="text-gray-400 hover:text-emerald-800 transition-colors">
              <i class="fas fa-times text-2xl"></i>
            </button>
          </div>

          <form @submit.prevent="saveRoom" class="space-y-6">
            <div class="grid grid-cols-2 gap-6">
              <div>
                <label class="block text-xs font-black text-emerald-900 uppercase tracking-widest mb-3">Số phòng</label>
                <input v-model="currentRoom.roomNumber" type="text" class="w-full bg-gray-50 border-0 rounded-2xl px-6 py-4 focus:ring-2 focus:ring-emerald-800 outline-none transition-all" required>
              </div>
              <div>
                <label class="block text-xs font-black text-emerald-900 uppercase tracking-widest mb-3">Hạng phòng</label>
                <select v-model="currentRoom.category" class="w-full bg-gray-50 border-0 rounded-2xl px-6 py-4 focus:ring-2 focus:ring-emerald-800 outline-none transition-all">
                  <option>Standard</option>
                  <option>Deluxe</option>
                  <option>Suite</option>
                </select>
              </div>
            </div>

            <div class="grid grid-cols-2 gap-6">
              <div>
                <label class="block text-xs font-black text-emerald-900 uppercase tracking-widest mb-3">Loại phòng</label>
                <select v-model="currentRoom.type" class="w-full bg-gray-50 border-0 rounded-2xl px-6 py-4 focus:ring-2 focus:ring-emerald-800 outline-none transition-all">
                  <template v-if="currentRoom.category === 'Standard'">
                    <option>Single</option>
                    <option>Double</option>
                    <option>Twin</option>
                  </template>
                  <template v-else-if="currentRoom.category === 'Deluxe'">
                    <option>Double</option>
                    <option>Twin</option>
                    <option>Ocean View</option>
                    <option>City View</option>
                  </template>
                  <template v-else>
                    <option>Junior</option>
                    <option>Executive</option>
                    <option>Family</option>
                    <option>Presidential</option>
                  </template>
                </select>
              </div>
              <div>
                <label class="block text-xs font-black text-emerald-900 uppercase tracking-widest mb-3">Giá phòng ($)</label>
                <input v-model.number="currentRoom.price" type="number" class="w-full bg-gray-50 border-0 rounded-2xl px-6 py-4 focus:ring-2 focus:ring-emerald-800 outline-none transition-all" required>
              </div>
            </div>

            <div class="grid grid-cols-2 gap-6">
              <div>
                <label class="block text-xs font-black text-emerald-900 uppercase tracking-widest mb-3">Sức chứa</label>
                <input v-model.number="currentRoom.capacity" type="number" min="1" class="w-full bg-gray-50 border-0 rounded-2xl px-6 py-4 focus:ring-2 focus:ring-emerald-800 outline-none transition-all" required>
              </div>
              <div v-if="currentRoom.id">
                <label class="block text-xs font-black text-emerald-900 uppercase tracking-widest mb-3">Trạng thái</label>
                <select v-model="currentRoom.status" class="w-full bg-gray-50 border-0 rounded-2xl px-6 py-4 focus:ring-2 focus:ring-emerald-800 outline-none transition-all">
                  <option value="AVAILABLE">AVAILABLE</option>
                  <option value="BOOKED">BOOKED</option>
                  <option value="OCCUPIED">OCCUPIED</option>
                  <option value="MAINTENANCE">MAINTENANCE</option>
                </select>
              </div>
            </div>

            <div>
              <label class="block text-xs font-black text-emerald-900 uppercase tracking-widest mb-3">Link hình ảnh</label>
              <input v-model="currentRoom.imageUrl" type="text" class="w-full bg-gray-50 border-0 rounded-2xl px-6 py-4 focus:ring-2 focus:ring-emerald-800 outline-none transition-all">
            </div>

            <div>
              <label class="block text-xs font-black text-emerald-900 uppercase tracking-widest mb-3">Mô tả</label>
              <textarea v-model="currentRoom.description" class="w-full bg-gray-50 border-0 rounded-2xl px-6 py-4 focus:ring-2 focus:ring-emerald-800 outline-none transition-all h-32"></textarea>
            </div>

            <button type="submit" class="w-full bg-emerald-800 text-white py-5 rounded-2xl font-black uppercase tracking-[0.2em] hover:bg-emerald-900 transition-all shadow-xl transform hover:-translate-y-1 mt-6">
              Lưu thông tin
            </button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from '../api/axios'

const route = useRoute()
const router = useRouter()
const rooms = ref([])
const loading = ref(false)
const showAddModal = ref(false)
const isAdmin = ref(false)
const isStaff = ref(false)
const categoryOptions = [
  { label: 'Xem tất cả', value: 'All' },
  { label: 'Standard', value: 'Standard' },
  { label: 'Deluxe', value: 'Deluxe' },
  { label: 'Suite', value: 'Suite' }
]

const currentRoom = ref({
  id: null,
  roomNumber: '',
  category: 'Standard',
  type: 'Single',
  capacity: 2,
  price: 0,
  status: 'AVAILABLE',
  description: '',
  imageUrl: ''
})

const fetchRooms = async () => {
  loading.value = true
  try {
    const params = {
      category: route.query.category || undefined
    }
    const response = await axios.get('/rooms/search', { params })
    rooms.value = response.data
  } catch (error) {
    console.error('Lỗi khi tải danh sách phòng:', error)
    rooms.value = []
  } finally {
    loading.value = false
  }
}

const checkUser = () => {
  const userData = localStorage.getItem('user')
  if (!userData) return
  const user = JSON.parse(userData)
  isAdmin.value = user.role === 'ADMIN'
  isStaff.value = user.role === 'STAFF'
}

const formatPrice = (price) => {
  if (!price) return '0 đ'
  return `${new Intl.NumberFormat('vi-VN').format(price * 25000)} đ`
}

const getAvailabilityLabel = (room) => {
  return room.status === 'MAINTENANCE' || room.status === 'OCCUPIED' ? 'Hết' : 'Còn'
}

const getAvailabilityClass = (room) => {
  return room.status === 'MAINTENANCE' || room.status === 'OCCUPIED' ? 'bg-red-500' : 'bg-emerald-500'
}

const getAvailabilityTextClass = (room) => {
  return room.status === 'MAINTENANCE' || room.status === 'OCCUPIED' ? 'text-red-500' : 'text-emerald-700'
}

const getCurrentCategory = () => route.query.category || 'All'

const getFloorLabel = (room) => {
  const floor = String(room.roomNumber || '').trim().charAt(0)
  return floor ? `Tầng ${floor}` : 'Tầng ?'
}

const activeCategory = ref(getCurrentCategory())

const pageTitle = ref('Danh sách phòng khách sạn')
const resultText = ref('')

const updatePageContent = () => {
  const category = getCurrentCategory()
  activeCategory.value = category

  if (category === 'All') {
    pageTitle.value = 'Danh sách phòng khách sạn'
    resultText.value = `Hiển thị ${rooms.value.length} phòng của toàn khách sạn`
    return
  }

  pageTitle.value = `Danh sách phòng ${category}`
  resultText.value = `Hiển thị ${rooms.value.length} phòng thuộc hạng ${category}`
}

const selectCategory = (category) => {
  if (category === 'All') {
    router.push('/rooms')
    return
  }

  router.push({ path: '/rooms', query: { category } })
}

const openAddModal = () => {
  currentRoom.value = {
    id: null,
    roomNumber: '',
    category: 'Standard',
    type: 'Single',
    capacity: 2,
    price: 0,
    status: 'AVAILABLE',
    description: '',
    imageUrl: ''
  }
  showAddModal.value = true
}

const closeModal = () => {
  showAddModal.value = false
}

const editRoom = (room) => {
  currentRoom.value = { ...room }
  showAddModal.value = true
}

const saveRoom = async () => {
  try {
    if (currentRoom.value.id) {
      await axios.put(`/rooms/${currentRoom.value.id}`, currentRoom.value)
    } else {
      await axios.post('/rooms', currentRoom.value)
    }
    await fetchRooms()
    closeModal()
  } catch (error) {
    console.error('Lỗi khi lưu thông tin phòng:', error)
    alert('Lỗi khi lưu thông tin phòng!')
  }
}

const deleteRoom = async (id) => {
  if (!confirm('Bạn có chắc chắn muốn xóa phòng này?')) return
  try {
    await axios.delete(`/rooms/${id}`)
    await fetchRooms()
  } catch (error) {
    console.error('Lỗi khi xóa phòng:', error)
    alert('Lỗi khi xóa phòng!')
  }
}

onMounted(() => {
  checkUser()
})

watch(
  () => route.query.category,
  async () => {
    await fetchRooms()
    updatePageContent()
  },
  { immediate: true }
)

watch(
  rooms,
  () => {
    updatePageContent()
  },
  { deep: true }
)
</script>
