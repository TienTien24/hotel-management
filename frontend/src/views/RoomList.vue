<template>
  <div class="min-h-screen bg-white font-sans">
    <!-- Hero Section -->
    <section class="relative h-[500px] flex items-center justify-center overflow-hidden">
      <div class="absolute inset-0 z-0">
        <img src="https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80" class="w-full h-full object-cover">
        <div class="absolute inset-0 bg-black/50"></div>
      </div>
      
      <div class="max-w-7xl mx-auto px-4 relative z-10 text-center">
        <div class="inline-flex items-center gap-2 px-4 py-2 bg-white/10 backdrop-blur-md rounded-full border border-white/20 mb-6">
          <i class="fas fa-calendar-alt text-amber-400 text-xs"></i>
          <span class="text-[10px] font-black uppercase tracking-[0.2em] text-white">Đặt phòng dễ dàng</span>
        </div>
        <h1 class="text-6xl md:text-8xl font-black text-white uppercase tracking-tighter mb-4 leading-none">
          BOOKING <span class="text-amber-400">ONLINE</span>
        </h1>
        <p class="text-lg text-white/80 font-medium max-w-2xl mx-auto leading-relaxed">
          Tìm phòng, xem giá và đặt phòng trực tuyến. Nhanh chóng – Tiện lợi – Giá tốt nhất.
        </p>
      </div>
    </section>

    <div class="max-w-7xl mx-auto px-4 -mt-24 relative z-20 pb-20">
      <!-- Search & Filter Bar -->
      <section class="bg-white rounded-[2.5rem] shadow-2xl shadow-emerald-900/10 p-8 md:p-10 mb-12 border border-emerald-50">
        <div class="flex flex-col lg:flex-row lg:items-center lg:justify-between gap-8 mb-10 pb-8 border-b border-gray-100">
          <div class="flex items-center gap-6">
            <div class="w-16 h-16 bg-emerald-50 rounded-[1.5rem] flex items-center justify-center text-emerald-800 text-2xl shadow-inner">
              <i class="fas fa-th-large"></i>
            </div>
            <div>
              <h2 class="text-3xl font-black text-emerald-950 uppercase tracking-tighter">Danh sách phòng khách sạn</h2>
              <p class="text-sm text-gray-400 font-bold mt-1">Hiển thị {{ rooms.length }} phòng của toàn khách sạn</p>
            </div>
          </div>

          <router-link v-if="user" to="/my-bookings" class="bg-[#004d26] hover:bg-[#003d1e] text-white px-8 py-4 rounded-2xl font-black uppercase tracking-widest text-xs transition-all shadow-xl shadow-green-900/20 flex items-center gap-3">
            <i class="fas fa-history"></i>
            Lịch sử đặt phòng
          </router-link>
        </div>

        <div class="flex flex-wrap gap-4">
          <button
            v-for="option in categoryOptions"
            :key="option.value"
            @click="selectCategory(option.value)"
            :class="[
              'px-8 py-4 rounded-2xl text-[11px] font-black uppercase tracking-widest transition-all flex items-center gap-3 border-2',
              activeCategory === option.value
                ? 'bg-[#004d26] text-white border-[#004d26] shadow-xl shadow-green-900/20'
                : 'bg-white text-gray-400 border-gray-100 hover:border-emerald-200 hover:text-emerald-800'
            ]"
          >
            <i :class="getCategoryIcon(option.value)" class="text-sm"></i>
            {{ option.label }}
          </button>
        </div>
      </section>

      <!-- Rooms Grid -->
      <div v-if="loading" class="flex flex-col items-center justify-center py-32 space-y-4">
        <div class="w-12 h-12 border-4 border-emerald-100 border-t-emerald-800 rounded-full animate-spin"></div>
        <p class="text-xs font-black text-gray-400 uppercase tracking-widest">Đang tìm phòng...</p>
      </div>

      <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-10">
        <div v-for="room in paginatedRooms" :key="room.id" class="group bg-white rounded-[2.5rem] overflow-hidden shadow-xl hover:shadow-2xl transition-all duration-500 border border-gray-100">
          <!-- Room Image -->
          <div class="relative h-72 overflow-hidden">
            <img :src="room.imageUrl || 'https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=800&q=80'" class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-1000">
            <div class="absolute top-6 right-6">
              <button class="w-10 h-10 bg-white/20 backdrop-blur-md rounded-full flex items-center justify-center text-white hover:bg-white hover:text-rose-500 transition-all">
                <i class="far fa-heart"></i>
              </button>
            </div>
            <router-link :to="'/rooms/' + room.id" class="absolute bottom-6 right-6 w-12 h-12 bg-[#004d26] text-white rounded-full flex items-center justify-center shadow-xl transform translate-y-20 opacity-0 group-hover:translate-y-0 group-hover:opacity-100 transition-all duration-500">
              <i class="fas fa-arrow-right"></i>
            </router-link>
          </div>

          <!-- Room Content -->
          <div class="p-8">
            <div class="mb-6">
              <h4 class="text-2xl font-black uppercase tracking-tighter text-slate-800 mb-2">Phòng {{ room.roomNumber }}</h4>
              <div class="flex gap-1 text-amber-400 text-[10px]">
                <i v-for="i in 5" :key="i" class="fas fa-star"></i>
              </div>
            </div>

            <div class="flex flex-wrap gap-2 mb-8 pb-8 border-b border-gray-50">
              <span class="bg-[#004d26] text-white px-4 py-1.5 rounded-full text-[10px] font-black uppercase tracking-widest shadow-lg shadow-green-900/10">{{ room.category }}</span>
              <span class="bg-gray-50 text-gray-500 px-4 py-1.5 rounded-full text-[10px] font-black uppercase tracking-widest border border-gray-100">{{ room.capacity }} người</span>
              <span class="bg-gray-50 text-gray-500 px-4 py-1.5 rounded-full text-[10px] font-black uppercase tracking-widest border border-gray-100">25m²</span>
            </div>

            <div class="grid grid-cols-2 gap-6 mb-8">
              <div>
                <p class="text-[10px] text-gray-400 font-black uppercase tracking-[0.2em] mb-2">Sức chứa</p>
                <div class="flex items-center gap-3">
                  <i class="fas fa-user-friends text-emerald-800"></i>
                  <span class="text-sm font-black text-slate-700">{{ room.capacity || 0 }} người</span>
                </div>
              </div>
              <div>
                <p class="text-[10px] text-gray-400 font-black uppercase tracking-[0.2em] mb-2">Trạng thái</p>
                <div class="flex items-center gap-3">
                  <i class="fas fa-circle text-[8px]" :class="room.status === 'AVAILABLE' ? 'text-emerald-500' : 'text-blue-500'"></i>
                  <span class="text-sm font-black text-slate-700">{{ getAvailabilityLabel(room) }}</span>
                </div>
              </div>
            </div>

            <div class="flex items-center justify-between">
              <div>
                <p class="text-3xl font-black text-emerald-950 leading-none tracking-tighter">{{ formatPrice(room.price) }}</p>
                <p class="text-[10px] text-gray-400 font-black uppercase tracking-widest mt-2">/ mỗi đêm</p>
              </div>
              <div class="flex gap-2">
                <button v-if="isAdmin || isStaff" @click="editRoom(room)" class="w-10 h-10 bg-slate-50 text-slate-400 rounded-xl flex items-center justify-center hover:bg-blue-50 hover:text-blue-600 transition-all">
                  <i class="fas fa-pen"></i>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Pagination -->
      <div v-if="!loading && rooms.length > itemsPerPage" class="mt-20 flex justify-center items-center gap-3">
        <button @click="currentPage--" :disabled="currentPage === 1" class="w-12 h-12 rounded-2xl bg-white border-2 border-gray-100 text-gray-400 hover:border-emerald-800 hover:text-emerald-800 disabled:opacity-30 transition-all flex items-center justify-center">
          <i class="fas fa-chevron-left text-sm"></i>
        </button>
        
        <div class="flex gap-2">
          <button v-for="page in totalPages" :key="page" @click="currentPage = page" :class="[currentPage === page ? 'bg-[#004d26] border-[#004d26] text-white shadow-xl shadow-green-900/20' : 'bg-white border-gray-100 text-gray-500 hover:border-emerald-800']" class="w-12 h-12 rounded-2xl border-2 font-black text-sm transition-all">
            {{ page }}
          </button>
        </div>

        <button @click="currentPage++" :disabled="currentPage === totalPages" class="w-12 h-12 rounded-2xl bg-white border-2 border-gray-100 text-gray-400 hover:border-emerald-800 hover:text-emerald-800 disabled:opacity-30 transition-all flex items-center justify-center">
          <i class="fas fa-chevron-right text-sm"></i>
        </button>
      </div>

      <div v-if="!loading && !rooms.length" class="text-center py-32 bg-gray-50 rounded-[3rem] border-2 border-dashed border-gray-200">
        <i class="fas fa-bed text-5xl text-gray-200 mb-6"></i>
        <p class="text-xs font-black text-gray-400 uppercase tracking-widest">Hiện chưa có dữ liệu phòng phù hợp.</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from '../api/axios'

const route = useRoute()
const router = useRouter()
const rooms = ref([])
const loading = ref(false)
const isAdmin = ref(false)
const isStaff = ref(false)
const user = ref(null)

// Modal state
const showAddModal = ref(false)
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

// Pagination
const currentPage = ref(1)
const itemsPerPage = 6

const activeCategory = ref(route.query.category || 'All')

const categoryOptions = [
  { label: 'XEM TẤT CẢ', value: 'All' },
  { label: 'STANDARD', value: 'Standard' },
  { label: 'DELUXE', value: 'Deluxe' },
  { label: 'SUITE', value: 'Suite' }
]

const getCategoryIcon = (value) => {
  switch (value) {
    case 'All': return 'fas fa-border-all'
    case 'Standard': return 'fas fa-hotel'
    case 'Deluxe': return 'fas fa-star'
    case 'Suite': return 'fas fa-crown'
    default: return 'fas fa-bed'
  }
}

const totalPages = computed(() => Math.ceil(rooms.value.length / itemsPerPage))
const paginatedRooms = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage
  return rooms.value.slice(start, start + itemsPerPage)
})

const fetchRooms = async () => {
  loading.value = true
  try {
    const params = {
      category: activeCategory.value === 'All' ? undefined : activeCategory.value,
      numberOfGuests: route.query.capacity || undefined,
      checkInDate: route.query.checkIn || undefined,
      checkOutDate: route.query.checkOut || undefined
    }
    const response = await axios.get('/rooms/search', { params })
    rooms.value = response.data
    currentPage.value = 1
  } catch (error) {
    console.error('Lỗi khi tải danh sách phòng:', error)
    rooms.value = []
  } finally {
    loading.value = false
  }
}

const selectCategory = (val) => {
  if (val === 'All') {
    router.push('/rooms')
  } else {
    router.push({ path: '/rooms', query: { category: val } })
  }
}

const checkUser = () => {
  const userData = localStorage.getItem('user')
  if (!userData) {
    user.value = null
    return
  }
  const parsedUser = JSON.parse(userData)
  user.value = parsedUser
  isAdmin.value = parsedUser.role === 'ADMIN'
  isStaff.value = parsedUser.role === 'STAFF'
}

const formatPrice = (price) => {
  if (!price) return '0 đ'
  return `${new Intl.NumberFormat('vi-VN').format(price * 25000)} đ`
}

const getAvailabilityLabel = (room) => {
  switch (room.status) {
    case 'AVAILABLE': return 'Còn trống'
    case 'BOOKED': return 'Đã đặt'
    case 'OCCUPIED': return 'Đang ở'
    case 'MAINTENANCE': return 'Bảo trì'
    default: return 'Hết'
  }
}

const editRoom = (room) => {
  currentRoom.value = { ...room }
  showAddModal.value = true
}

onMounted(() => {
  checkUser()
  fetchRooms()
})

watch(
  () => route.query.category,
  (newCat) => {
    activeCategory.value = newCat || 'All'
    fetchRooms()
  }
)
</script>

<style scoped>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css');
</style>