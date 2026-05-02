<template>
  <div class="min-h-screen flex bg-[#f0f2f5] font-sans text-[#1e293b]">
    <AdminSidebar />

    <main class="flex-1 p-10 overflow-y-auto">
      <header class="flex justify-between items-center mb-10">
        <div>
          <h2 class="text-3xl font-black tracking-tight text-slate-800">Quản lý khách hàng</h2>
          <p class="text-sm text-slate-400 font-bold mt-2">Danh sách khách hàng lấy trực tiếp từ MySQL qua backend Spring Boot</p>
        </div>
      </header>

      <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
        <div class="bg-white rounded-[2rem] p-6 shadow-sm border border-slate-100">
          <p class="text-[10px] font-black text-slate-400 uppercase tracking-widest mb-2">Tổng khách hàng</p>
          <p class="text-3xl font-black text-slate-800">{{ customers.length }}</p>
        </div>
        <div class="bg-white rounded-[2rem] p-6 shadow-sm border border-slate-100">
          <p class="text-[10px] font-black text-slate-400 uppercase tracking-widest mb-2">Đang hoạt động</p>
          <p class="text-3xl font-black text-emerald-600">{{ activeCustomers }}</p>
        </div>
        <div class="bg-white rounded-[2rem] p-6 shadow-sm border border-slate-100">
          <p class="text-[10px] font-black text-slate-400 uppercase tracking-widest mb-2">Tổng booking</p>
          <p class="text-3xl font-black text-blue-600">{{ totalBookings }}</p>
        </div>
        <div class="bg-white rounded-[2rem] p-6 shadow-sm border border-slate-100">
          <p class="text-[10px] font-black text-slate-400 uppercase tracking-widest mb-2">Doanh thu từ khách</p>
          <p class="text-3xl font-black text-indigo-600">{{ formatCurrency(totalSpent) }}</p>
        </div>
      </div>

      <div class="bg-white rounded-[2.5rem] shadow-sm border border-slate-100 overflow-hidden">
        <div class="p-8 border-b border-slate-50 bg-slate-50/50 flex flex-col lg:flex-row gap-4 lg:items-center lg:justify-between">
          <div class="relative w-full lg:w-96">
            <i class="fas fa-search absolute left-5 top-1/2 -translate-y-1/2 text-slate-300"></i>
            <input v-model="searchQuery" type="text" placeholder="Tìm khách hàng theo tên, email, điện thoại..." class="w-full bg-white border-2 border-slate-100 rounded-xl pl-12 pr-5 py-3 outline-none focus:border-[#004d26] transition-all font-bold text-sm">
          </div>
          <div class="flex gap-3">
            <select v-model="statusFilter" class="bg-white border-2 border-slate-100 rounded-xl px-4 py-3 outline-none focus:border-[#004d26] font-bold text-sm text-slate-600">
              <option value="">Tất cả trạng thái</option>
              <option value="active">Hoạt động</option>
              <option value="locked">Đã khóa</option>
            </select>
          </div>
        </div>

        <div v-if="loading" class="p-16 text-center text-slate-400 font-bold">Đang tải dữ liệu khách hàng...</div>
        <div v-else-if="filteredCustomers.length === 0" class="p-16 text-center text-slate-400 font-bold">Không có khách hàng phù hợp</div>
        <div v-else class="overflow-x-auto">
          <table class="w-full">
            <thead>
              <tr class="bg-slate-50/50 text-left">
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Khách hàng</th>
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Liên hệ</th>
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Booking</th>
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Chi tiêu</th>
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Booking gần nhất</th>
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Trạng thái</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-slate-50">
              <tr v-for="customer in filteredCustomers" :key="customer.id" class="hover:bg-slate-50/50 transition-colors">
                <td class="px-8 py-6">
                  <div class="flex items-center gap-4">
                    <div class="w-12 h-12 rounded-2xl bg-slate-100 flex items-center justify-center font-black text-slate-500">
                      {{ customer.fullName?.charAt(0)?.toUpperCase() || 'K' }}
                    </div>
                    <div>
                      <p class="font-black text-slate-800">{{ customer.fullName || 'Chưa cập nhật' }}</p>
                      <p class="text-[10px] font-bold text-slate-400 uppercase tracking-widest">@{{ customer.username }}</p>
                    </div>
                  </div>
                </td>
                <td class="px-8 py-6">
                  <div class="space-y-1">
                    <p class="text-sm font-bold text-slate-700">{{ customer.email || '--' }}</p>
                    <p class="text-xs text-slate-400 font-bold">{{ customer.phone || '--' }}</p>
                  </div>
                </td>
                <td class="px-8 py-6">
                  <div class="space-y-1">
                    <p class="text-sm font-black text-slate-800">{{ customer.totalBookings }}</p>
                    <p class="text-[10px] font-bold text-slate-400 uppercase tracking-widest">
                      {{ customer.activeBookings }} đang hoạt động / {{ customer.completedBookings }} hoàn tất
                    </p>
                  </div>
                </td>
                <td class="px-8 py-6 text-sm font-black text-emerald-600">
                  {{ formatCurrency(customer.totalSpent) }}
                </td>
                <td class="px-8 py-6 text-sm font-bold text-slate-500">
                  {{ formatDateTime(customer.lastBookingDate) }}
                </td>
                <td class="px-8 py-6">
                  <span :class="customer.isLocked ? 'bg-rose-50 text-rose-600' : 'bg-emerald-50 text-emerald-600'" class="px-4 py-1.5 rounded-full text-[10px] font-black uppercase tracking-widest">
                    {{ customer.isLocked ? 'Ngưng hoạt động' : 'Hoạt động' }}
                  </span>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from '../api/axios'
import AdminSidebar from '../components/AdminSidebar.vue'

const router = useRouter()
const customers = ref([])
const loading = ref(false)
const searchQuery = ref('')
const statusFilter = ref('')

const fetchCustomers = async () => {
  try {
    loading.value = true
    const response = await axios.get('/admin/customers')
    customers.value = response.data
  } catch (error) {
    console.error('Lỗi tải khách hàng:', error)
    alert('Không thể tải danh sách khách hàng.')
  } finally {
    loading.value = false
  }
}

const filteredCustomers = computed(() => {
  const query = searchQuery.value.trim().toLowerCase()

  return customers.value.filter((customer) => {
    const matchesQuery = !query
      || customer.fullName?.toLowerCase().includes(query)
      || customer.email?.toLowerCase().includes(query)
      || customer.phone?.toLowerCase().includes(query)
      || customer.username?.toLowerCase().includes(query)

    const matchesStatus = !statusFilter.value
      || (statusFilter.value === 'active' && !customer.isLocked)
      || (statusFilter.value === 'locked' && customer.isLocked)

    return matchesQuery && matchesStatus
  })
})

const activeCustomers = computed(() => customers.value.filter(customer => !customer.isLocked).length)
const totalBookings = computed(() => customers.value.reduce((sum, customer) => sum + (customer.totalBookings || 0), 0))
const totalSpent = computed(() => customers.value.reduce((sum, customer) => sum + (customer.totalSpent || 0), 0))

const formatCurrency = (amount) => {
  if (!amount) return '0 đ'
  return `${new Intl.NumberFormat('vi-VN').format(amount * 25000)} đ`
}

const formatDateTime = (value) => {
  if (!value) return '--'
  const date = new Date(value)
  if (Number.isNaN(date.getTime())) return '--'
  return `${date.toLocaleDateString('vi-VN')} ${date.toLocaleTimeString('vi-VN', { hour: '2-digit', minute: '2-digit' })}`
}

const logout = () => {
  localStorage.removeItem('user')
  router.push('/login')
}

onMounted(() => {
  fetchCustomers()
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
