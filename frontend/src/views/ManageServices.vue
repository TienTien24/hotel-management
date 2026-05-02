<template>
  <div class="min-h-screen flex bg-[#f8fafc] font-sans">
    <!-- Sidebar (Same as other staff pages) -->
    <aside class="w-72 bg-[#004d26] text-white min-h-screen flex flex-col shadow-2xl shrink-0 z-50">
      <div class="p-8 mb-4">
        <div class="flex items-center gap-3">
          <div class="w-12 h-12 bg-white/10 rounded-full flex items-center justify-center font-black text-2xl border border-white/20 text-white">H</div>
          <h1 class="text-2xl font-black uppercase tracking-tight text-white">Grand Hotel</h1>
        </div>
      </div>
      
      <nav class="flex-1 px-4 space-y-1">
        <router-link to="/staff-dashboard" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group" :class="{'bg-white/10': $route.path === '/staff-dashboard', 'hover:bg-white/10': $route.path !== '/staff-dashboard'}">
          <i class="fas fa-th-large text-xl opacity-70 group-hover:opacity-100" :class="{'opacity-100': $route.path === '/staff-dashboard'}"></i>
          <span class="font-bold text-lg tracking-tight">Tổng quan</span>
        </router-link>

        <router-link to="/staff-rooms" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group" :class="{'bg-white/10': $route.path === '/staff-rooms', 'hover:bg-white/10': $route.path !== '/staff-rooms'}">
          <i class="fas fa-door-open text-xl opacity-70 group-hover:opacity-100" :class="{'opacity-100': $route.path === '/staff-rooms'}"></i>
          <span class="font-bold text-lg tracking-tight">Quản lý Phòng</span>
        </router-link>

        <router-link to="/bookings" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group" :class="{'bg-white/10': $route.path === '/bookings', 'hover:bg-white/10': $route.path !== '/bookings'}">
          <i class="fas fa-calendar-alt text-xl opacity-70 group-hover:opacity-100" :class="{'opacity-100': $route.path === '/bookings'}"></i>
          <span class="font-bold text-lg tracking-tight">Quản lý Đặt phòng</span>
        </router-link>

        <router-link to="/manage-services" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group bg-white/10">
          <i class="fas fa-bell text-xl opacity-100"></i>
          <span class="font-bold text-lg tracking-tight">Quản lý Dịch vụ</span>
        </router-link>

        <router-link to="/staff-messages" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group hover:bg-white/10">
          <i class="fas fa-envelope text-xl opacity-70 group-hover:opacity-100"></i>
          <span class="font-bold text-lg tracking-tight">Phản hồi khách hàng</span>
        </router-link>
      </nav>

      <div class="p-10 mt-auto">
        <button @click="logout" class="text-white/60 hover:text-white transition-colors font-black uppercase tracking-[0.2em] text-xs flex items-center gap-3">
          <i class="fas fa-sign-out-alt"></i>
          Đăng xuất
        </button>
      </div>
    </aside>

    <!-- Main Content -->
    <main class="flex-1 p-8 overflow-y-auto">
      <header class="flex justify-between items-center mb-10">
        <div>
          <h2 class="text-3xl font-black text-slate-800 uppercase tracking-tight">Quản lý dịch vụ</h2>
        </div>
      </header>

      <!-- Stats Cards -->
      <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-10">
        <div class="bg-white p-6 rounded-[2rem] shadow-sm border border-slate-100 flex items-center gap-5">
          <div class="w-14 h-14 bg-blue-50 text-blue-600 rounded-2xl flex items-center justify-center text-xl">
            <i class="fas fa-clipboard-list"></i>
          </div>
          <div>
            <div class="flex items-center gap-2">
              <span class="text-2xl font-black text-slate-800">{{ stats.total }}</span>
            </div>
            <p class="text-[10px] font-black text-slate-400 uppercase tracking-widest">Tổng yêu cầu <span class="text-blue-500 lowercase ml-1">hôm nay</span></p>
          </div>
        </div>

        <div class="bg-white p-6 rounded-[2rem] shadow-sm border border-slate-100 flex items-center gap-5">
          <div class="w-14 h-14 bg-amber-50 text-amber-600 rounded-2xl flex items-center justify-center text-xl">
            <i class="fas fa-clock"></i>
          </div>
          <div>
            <div class="flex items-center gap-2">
              <span class="text-2xl font-black text-slate-800">{{ stats.processing }}</span>
            </div>
            <p class="text-[10px] font-black text-slate-400 uppercase tracking-widest">Đang xử lý <span class="text-amber-500 lowercase ml-1">hôm nay</span></p>
          </div>
        </div>

        <div class="bg-white p-6 rounded-[2rem] shadow-sm border border-slate-100 flex items-center gap-5">
          <div class="w-14 h-14 bg-emerald-50 text-emerald-600 rounded-2xl flex items-center justify-center text-xl">
            <i class="fas fa-check-circle"></i>
          </div>
          <div>
            <div class="flex items-center gap-2">
              <span class="text-2xl font-black text-slate-800">{{ stats.completed }}</span>
            </div>
            <p class="text-[10px] font-black text-slate-400 uppercase tracking-widest">Đã hoàn thành <span class="text-emerald-500 lowercase ml-1">hôm nay</span></p>
          </div>
        </div>

        <div class="bg-white p-6 rounded-[2rem] shadow-sm border border-slate-100 flex items-center gap-5">
          <div class="w-14 h-14 bg-indigo-50 text-indigo-600 rounded-2xl flex items-center justify-center text-xl">
            <i class="fas fa-dollar-sign"></i>
          </div>
          <div>
            <div class="flex items-center gap-2">
              <span class="text-2xl font-black text-slate-800">{{ formatCurrency(stats.revenue) }}</span>
            </div>
            <p class="text-[10px] font-black text-slate-400 uppercase tracking-widest">Doanh thu dịch vụ <span class="text-indigo-500 lowercase ml-1">hôm nay</span></p>
          </div>
        </div>
      </div>

      <div class="flex flex-col xl:flex-row gap-8">
        <!-- List Section -->
        <div class="flex-1 space-y-6">
          <!-- Filters -->
          <div class="bg-white p-4 rounded-[2rem] shadow-sm border border-slate-100 flex flex-wrap items-center gap-4">
            <div class="relative flex-1 min-w-[200px]">
              <i class="fas fa-search absolute left-4 top-1/2 -translate-y-1/2 text-slate-300"></i>
              <input type="text" v-model="filters.search" placeholder="Tìm kiếm (phòng, dịch vụ, ghi chú...)" class="w-full bg-slate-50 border-0 rounded-xl pl-10 pr-4 py-2.5 outline-none focus:ring-2 focus:ring-[#004d26] text-xs font-bold transition-all">
            </div>
            <select v-model="filters.status" class="bg-slate-50 border-0 rounded-xl px-4 py-2.5 outline-none focus:ring-2 focus:ring-[#004d26] text-xs font-bold text-slate-600">
              <option value="">Tất cả trạng thái</option>
              <option value="PENDING">Mới tạo (Pending)</option>
              <option value="IN_PROGRESS">Đang xử lý (In Progress)</option>
              <option value="COMPLETED">Đã hoàn tất (Completed)</option>
            </select>
            <select v-model="filters.type" class="bg-slate-50 border-0 rounded-xl px-4 py-2.5 outline-none focus:ring-2 focus:ring-[#004d26] text-xs font-bold text-slate-600">
              <option value="">Tất cả loại dịch vụ</option>
              <option v-for="s in services" :key="s.id" :value="s.id">{{ s.name }}</option>
            </select>
            <div class="relative">
              <input type="date" v-model="filters.date" class="bg-slate-50 border-0 rounded-xl px-4 py-2.5 outline-none focus:ring-2 focus:ring-[#004d26] text-xs font-bold text-slate-600">
            </div>
            <button @click="openCreateModal" class="bg-[#004d26] text-white px-6 py-2.5 rounded-xl text-xs font-black uppercase tracking-widest hover:bg-[#003d1e] transition-all flex items-center gap-2">
              <i class="fas fa-plus"></i>
              Thêm dịch vụ
            </button>
          </div>

          <!-- Table -->
          <div class="bg-white rounded-[2.5rem] shadow-xl overflow-hidden border border-slate-50">
            <table class="min-w-full">
              <thead>
                <tr class="bg-slate-50/50">
                  <th class="px-8 py-4 text-left text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Mã yêu cầu</th>
                  <th class="px-8 py-4 text-left text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Phòng</th>
                  <th class="px-8 py-4 text-left text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Dịch vụ</th>
                  <th class="px-8 py-4 text-left text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Số lượng</th>
                  <th class="px-8 py-4 text-left text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Thời gian yêu cầu</th>
                  <th class="px-8 py-4 text-left text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Trạng thái</th>
                  <th class="px-8 py-4 text-center text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Thao tác</th>
                </tr>
              </thead>
              <tbody class="divide-y divide-slate-50">
                <tr v-for="usage in filteredUsages" :key="usage.id" class="hover:bg-slate-50/50 transition-colors">
                  <td class="px-8 py-5 text-[11px] font-black text-slate-500 tracking-tighter uppercase">SV{{ String(usage.id).padStart(8, '0') }}</td>
                  <td class="px-8 py-5">
                    <span class="text-sm font-black text-slate-800">{{ usage.booking?.room?.roomNumber }}</span>
                  </td>
                  <td class="px-8 py-5">
                    <div class="flex items-center gap-3">
                      <div class="w-8 h-8 rounded-lg bg-blue-50 text-blue-600 flex items-center justify-center text-xs">
                        <i :class="getServiceIcon(usage.service?.name)"></i>
                      </div>
                      <div>
                        <p class="text-xs font-black text-slate-800">{{ usage.service?.name }}</p>
                        <p v-if="usage.note" class="text-[9px] font-bold text-slate-400 italic">"{{ usage.note }}"</p>
                      </div>
                    </div>
                  </td>
                  <td class="px-8 py-5 text-sm font-black text-slate-800">{{ usage.quantity }}</td>
                  <td class="px-8 py-5 text-[11px] font-bold text-slate-500 uppercase tracking-tighter">{{ formatDateTime(usage.usedDate) }}</td>
                  <td class="px-8 py-5">
                    <span :class="getStatusClass(usage.status)" class="px-3 py-1 rounded-lg text-[9px] font-black uppercase tracking-widest">
                      {{ formatStatus(usage.status) }}
                    </span>
                  </td>
                  <td class="px-8 py-5">
                    <div class="flex justify-center gap-2">
                      <button @click="openViewModal(usage)" class="w-8 h-8 rounded-lg bg-slate-50 text-slate-400 hover:bg-indigo-50 hover:text-indigo-600 transition-all flex items-center justify-center">
                        <i class="fas fa-eye text-[10px]"></i>
                      </button>
                      <button v-if="usage.status !== 'COMPLETED'" @click="openEditStatusModal(usage)" class="w-8 h-8 rounded-lg bg-slate-50 text-slate-400 hover:bg-amber-50 hover:text-amber-600 transition-all flex items-center justify-center">
                        <i class="fas fa-edit text-[10px]"></i>
                      </button>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
            <div v-if="filteredUsages.length === 0" class="p-20 text-center">
              <i class="fas fa-inbox text-4xl text-slate-100 mb-4"></i>
              <p class="text-xs font-black text-slate-300 uppercase tracking-[0.2em]">Không tìm thấy yêu cầu dịch vụ</p>
            </div>
          </div>
        </div>

        <!-- Create Panel (Right) -->
        <aside class="w-full xl:w-96">
          <div class="bg-white rounded-[2.5rem] shadow-xl border border-slate-50 overflow-hidden sticky top-8">
            <div class="bg-white p-8 border-b border-slate-50">
              <h3 class="text-xl font-black text-slate-800 uppercase tracking-tight">Thêm dịch vụ mới</h3>
            </div>
            <form @submit.prevent="handleCreateUsage" class="p-8 space-y-6">
              <div class="space-y-2">
                <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Phòng <span class="text-rose-500">*</span></label>
                <select v-model="form.bookingId" required class="w-full bg-slate-50 border-0 rounded-xl px-4 py-3 outline-none focus:ring-2 focus:ring-[#004d26] text-xs font-bold text-slate-800 transition-all">
                  <option value="">-- Chọn phòng đang có khách --</option>
                  <option v-for="b in occupiedBookings" :key="b.id" :value="b.id">
                    {{ b.room?.roomNumber }} - {{ b.guestFullName || b.customer?.fullName }}
                  </option>
                </select>
              </div>

              <div class="space-y-2">
                <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Loại dịch vụ <span class="text-rose-500">*</span></label>
                <select v-model="form.serviceId" required class="w-full bg-slate-50 border-0 rounded-xl px-4 py-3 outline-none focus:ring-2 focus:ring-[#004d26] text-xs font-bold text-slate-800 transition-all">
                  <option value="">-- Chọn loại dịch vụ --</option>
                  <option v-for="s in services" :key="s.id" :value="s.id">
                    {{ s.name }} ({{ formatCurrency(s.price) }})
                  </option>
                </select>
              </div>

              <div class="space-y-2">
                <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Số lượng <span class="text-rose-500">*</span></label>
                <div class="flex items-center gap-4 bg-slate-50 rounded-xl p-1">
                  <button type="button" @click="form.quantity > 1 && form.quantity--" class="w-10 h-10 rounded-lg bg-white shadow-sm flex items-center justify-center text-slate-400 hover:text-slate-800 transition-all">
                    <i class="fas fa-minus text-[10px]"></i>
                  </button>
                  <input type="number" v-model.number="form.quantity" min="1" class="flex-1 bg-transparent border-0 text-center text-sm font-black text-slate-800 outline-none">
                  <button type="button" @click="form.quantity++" class="w-10 h-10 rounded-lg bg-white shadow-sm flex items-center justify-center text-slate-400 hover:text-slate-800 transition-all">
                    <i class="fas fa-plus text-[10px]"></i>
                  </button>
                </div>
              </div>

              <div class="space-y-2">
                <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Ghi chú</label>
                <textarea v-model="form.note" rows="3" placeholder="Ví dụ: Đồ ăn không cay, giặt là gấp..." class="w-full bg-slate-50 border-0 rounded-xl px-4 py-3 outline-none focus:ring-2 focus:ring-[#004d26] text-xs font-bold text-slate-800 transition-all resize-none"></textarea>
              </div>

              <button type="submit" class="w-full bg-[#004d26] text-white py-4 rounded-2xl font-black uppercase tracking-[0.2em] text-xs hover:bg-[#003d1e] transition-all shadow-xl shadow-green-50 flex items-center justify-center gap-3">
                <i class="fas fa-plus-circle"></i>
                Tạo yêu cầu
              </button>
            </form>
          </div>
        </aside>
      </div>
    </main>

    <!-- Update Status Modal -->
    <div v-if="showStatusModal" class="fixed inset-0 z-[100] flex items-center justify-center p-6 bg-slate-900/60 backdrop-blur-md">
      <div class="bg-white rounded-[3.5rem] max-w-md w-full overflow-hidden shadow-2xl animate-in fade-in zoom-in duration-300">
        <div class="bg-[#004d26] p-10 text-white flex justify-between items-center">
          <div>
            <h3 class="text-2xl font-black uppercase tracking-tight">Cập nhật xử lý</h3>
            <p class="text-green-100/60 text-[10px] font-bold uppercase tracking-widest mt-1">Yêu cầu SV{{ String(selectedUsage?.id).padStart(8, '0') }}</p>
          </div>
          <button @click="showStatusModal = false" class="w-10 h-10 flex items-center justify-center rounded-xl bg-white/10 text-white hover:bg-rose-500 transition-all">
            <i class="fas fa-times"></i>
          </button>
        </div>
        <div class="p-10 space-y-6">
          <div class="grid grid-cols-1 gap-4">
            <button v-for="status in ['PENDING', 'IN_PROGRESS', 'COMPLETED']" :key="status" 
              @click="handleUpdateStatus(status)"
              :class="[
                selectedUsage?.status === status ? 'bg-[#004d26] text-white ring-4 ring-green-100' : 'bg-slate-50 text-slate-600 hover:bg-slate-100',
                'p-5 rounded-[1.5rem] flex items-center justify-between transition-all group'
              ]"
            >
              <div class="flex items-center gap-4">
                <div :class="[
                  selectedUsage?.status === status ? 'bg-white/20' : 'bg-white',
                  'w-10 h-10 rounded-xl flex items-center justify-center text-sm'
                ]">
                  <i :class="getStatusIcon(status)"></i>
                </div>
                <span class="text-[10px] font-black uppercase tracking-[0.2em]">{{ formatStatus(status) }}</span>
              </div>
              <i v-if="selectedUsage?.status === status" class="fas fa-check-circle"></i>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from '../api/axios'

const router = useRouter()
const services = ref([])
const usages = ref([])
const occupiedBookings = ref([])
const showStatusModal = ref(false)
const selectedUsage = ref(null)

const stats = ref({
  total: 0,
  processing: 0,
  completed: 0,
  revenue: 0
})

const filters = ref({
  search: '',
  status: '',
  type: '',
  date: new Date().toISOString().substr(0, 10)
})

const form = ref({
  bookingId: '',
  serviceId: '',
  quantity: 1,
  note: ''
})

const fetchServices = async () => {
  try {
    const response = await axios.get('/hotel-services')
    services.value = response.data
  } catch (error) {
    console.error('Lỗi tải dịch vụ:', error)
  }
}

const fetchUsages = async () => {
  try {
    const response = await axios.get('/hotel-services/usages')
    usages.value = response.data.sort((a, b) => b.id - a.id)
    updateStats()
  } catch (error) {
    console.error('Lỗi tải danh sách sử dụng:', error)
  }
}

const fetchOccupiedBookings = async () => {
  try {
    const response = await axios.get('/bookings')
    occupiedBookings.value = response.data.filter(b => b.status === 'CHECKED_IN')
  } catch (error) {
    console.error('Lỗi tải booking:', error)
  }
}

const updateStats = () => {
  const today = new Date().toISOString().substr(0, 10)
  const todayUsages = usages.value.filter(u => u.usedDate?.startsWith(today))
  
  stats.value = {
    total: todayUsages.length,
    processing: todayUsages.filter(u => u.status === 'IN_PROGRESS' || u.status === 'PENDING').length,
    completed: todayUsages.filter(u => u.status === 'COMPLETED').length,
    revenue: todayUsages.filter(u => u.status === 'COMPLETED').reduce((sum, u) => sum + (u.service?.price * u.quantity), 0)
  }
}

const filteredUsages = computed(() => {
  return usages.value.filter(u => {
    const matchSearch = !filters.value.search || 
      (u.booking?.room?.roomNumber?.toString().includes(filters.value.search)) ||
      (u.service?.name?.toLowerCase().includes(filters.value.search.toLowerCase())) ||
      (u.note?.toLowerCase().includes(filters.value.search.toLowerCase()))
    
    const matchStatus = !filters.value.status || u.status === filters.value.status
    const matchType = !filters.value.type || u.service?.id === filters.value.type
    const matchDate = !filters.value.date || u.usedDate?.startsWith(filters.value.date)
    
    return matchSearch && matchStatus && matchType && matchDate
  })
})

const handleCreateUsage = async () => {
  try {
    await axios.post('/hotel-services/add-to-booking', null, {
      params: {
        bookingId: form.value.bookingId,
        serviceId: form.value.serviceId,
        quantity: form.value.quantity,
        note: form.value.note
      }
    })
    form.value = { bookingId: '', serviceId: '', quantity: 1, note: '' }
    fetchUsages()
    alert('Đã tạo yêu cầu dịch vụ!')
  } catch (error) {
    alert('Lỗi: ' + (error.response?.data?.message || 'Không thể tạo yêu cầu'))
  }
}

const openEditStatusModal = (usage) => {
  selectedUsage.value = usage
  showStatusModal.value = true
}

const handleUpdateStatus = async (status) => {
  try {
    await axios.put(`/hotel-services/usages/${selectedUsage.value.id}/status`, null, {
      params: { status }
    })
    showStatusModal.value = false
    fetchUsages()
  } catch (error) {
    alert('Lỗi: ' + (error.response?.data?.message || 'Không thể cập nhật'))
  }
}

const formatCurrency = (amount) => {
  return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(amount * 25000)
}

const formatDateTime = (dateTimeStr) => {
  if (!dateTimeStr) return ''
  const date = new Date(dateTimeStr)
  return date.toLocaleDateString('vi-VN') + ' ' + date.toLocaleTimeString('vi-VN', { hour: '2-digit', minute: '2-digit' })
}

const getStatusClass = (status) => {
  switch (status) {
    case 'PENDING': return 'bg-blue-50 text-blue-600'
    case 'IN_PROGRESS': return 'bg-amber-50 text-amber-600'
    case 'COMPLETED': return 'bg-emerald-50 text-emerald-600'
    default: return 'bg-slate-50 text-slate-400'
  }
}

const formatStatus = (status) => {
  switch (status) {
    case 'PENDING': return 'Mới tạo'
    case 'IN_PROGRESS': return 'Đang xử lý'
    case 'COMPLETED': return 'Đã xong'
    default: return status
  }
}

const getStatusIcon = (status) => {
  switch (status) {
    case 'PENDING': return 'fas fa-plus-circle'
    case 'IN_PROGRESS': return 'fas fa-spinner fa-spin'
    case 'COMPLETED': return 'fas fa-check-circle'
    default: return 'fas fa-info-circle'
  }
}

const getServiceIcon = (name) => {
  const n = name?.toLowerCase() || ''
  if (n.includes('ăn') || n.includes('food')) return 'fas fa-utensils'
  if (n.includes('giặt') || n.includes('laundry')) return 'fas fa-tshirt'
  if (n.includes('dọn') || n.includes('cleaning')) return 'fas fa-broom'
  if (n.includes('đưa đón') || n.includes('shuttle')) return 'fas fa-car'
  if (n.includes('spa') || n.includes('massage')) return 'fas fa-spa'
  if (n.includes('nước') || n.includes('water')) return 'fas fa-tint'
  return 'fas fa-concierge-bell'
}

const logout = () => {
  localStorage.removeItem('user')
  router.push('/login')
}

onMounted(() => {
  fetchServices()
  fetchUsages()
  fetchOccupiedBookings()
})
</script>

<style scoped>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css');

main::-webkit-scrollbar { width: 6px; }
main::-webkit-scrollbar-track { background: transparent; }
main::-webkit-scrollbar-thumb { background: #e2e8f0; border-radius: 10px; }

.animate-in { animation: animate-in 0.3s ease-out; }
@keyframes animate-in {
  from { opacity: 0; transform: scale(0.98); }
  to { opacity: 1; transform: scale(1); }
}

select {
  appearance: none;
  background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 24 24' stroke='%2394a3b8'%3E%3Cpath stroke-linecap='round' stroke-linejoin='round' stroke-width='2' d='M19 9l-7 7-7-7'%3E%3C/path%3E%3C/svg%3E");
  background-repeat: no-repeat;
  background-position: right 1rem center;
  background-size: 1rem;
}
</style>
