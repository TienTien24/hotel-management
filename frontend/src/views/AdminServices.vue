<template>
  <div class="min-h-screen flex bg-[#f0f2f5] font-sans text-[#1e293b]">
    <AdminSidebar />

    <main class="flex-1 p-10 overflow-y-auto">
      <header class="flex justify-between items-center mb-10">
        <div>
          <h2 class="text-3xl font-black tracking-tight text-slate-800">Quản lý dịch vụ</h2>
        </div>
        <button @click="openCreateModal" class="bg-[#004d26] hover:bg-[#003d1e] text-white px-8 py-4 rounded-2xl font-black uppercase tracking-widest text-xs transition-all shadow-xl shadow-green-100 flex items-center gap-3">
          <i class="fas fa-plus"></i>
          Thêm dịch vụ
        </button>
      </header>

      <div class="bg-white rounded-[2.5rem] shadow-sm border border-slate-100 overflow-hidden">
        <div class="p-8 border-b border-slate-50 bg-slate-50/50">
          <div class="relative w-full lg:w-96">
            <i class="fas fa-search absolute left-5 top-1/2 -translate-y-1/2 text-slate-300"></i>
            <input v-model="searchQuery" type="text" placeholder="Tìm dịch vụ theo tên hoặc mô tả..." class="w-full bg-white border-2 border-slate-100 rounded-xl pl-12 pr-5 py-3 outline-none focus:border-[#004d26] transition-all font-bold text-sm">
          </div>
        </div>

        <div v-if="loading" class="p-16 text-center text-slate-400 font-bold">Đang tải dịch vụ...</div>
        <div v-else-if="filteredServices.length === 0" class="p-16 text-center text-slate-400 font-bold">Không có dịch vụ nào</div>
        <div v-else class="overflow-x-auto">
          <table class="w-full">
            <thead>
              <tr class="bg-slate-50/50 text-left">
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Mã DV</th>
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Tên dịch vụ</th>
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Mô tả</th>
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Giá</th>
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Trạng thái</th>
                <th class="px-8 py-5 text-center text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Thao tác</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-slate-50">
              <tr v-for="service in paginatedServices" :key="service.id" class="hover:bg-slate-50/50 transition-colors">
                <td class="px-8 py-6 text-sm font-black text-slate-500">DV{{ String(service.id).padStart(3, '0') }}</td>
                <td class="px-8 py-6">
                  <p class="font-black text-slate-800">{{ service.name }}</p>
                </td>
                <td class="px-8 py-6 text-sm font-bold text-slate-500">{{ service.description || 'Không có mô tả' }}</td>
                <td class="px-8 py-6 text-sm font-black text-emerald-600">{{ formatCurrency(service.price) }}</td>
                <td class="px-8 py-6">
                  <span class="px-4 py-1.5 rounded-full text-[10px] font-black uppercase tracking-widest bg-emerald-50 text-emerald-600">
                    Đang cung cấp
                  </span>
                </td>
                <td class="px-8 py-6">
                  <div class="flex justify-center gap-2">
                    <button @click="openEditModal(service)" class="w-10 h-10 rounded-xl bg-slate-50 text-slate-400 hover:bg-blue-50 hover:text-blue-600 transition-all flex items-center justify-center shadow-sm">
                      <i class="fas fa-pen"></i>
                    </button>
                    <button @click="deleteService(service)" class="w-10 h-10 rounded-xl bg-slate-50 text-slate-400 hover:bg-rose-500 hover:text-white transition-all flex items-center justify-center shadow-sm">
                      <i class="fas fa-trash"></i>
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <!-- Pagination -->
        <div v-if="filteredServices.length > 0" class="p-8 bg-slate-50/30 border-t border-slate-50 flex flex-col md:flex-row justify-between items-center gap-6">
          <p class="text-[11px] font-black text-slate-400 uppercase tracking-widest">
            Hiển thị <span class="text-slate-800">{{ startIndex + 1 }} - {{ Math.min(endIndex, filteredServices.length) }}</span> trong <span class="text-slate-800">{{ filteredServices.length }}</span> dịch vụ
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

      <div v-if="showModal" class="fixed inset-0 z-[100] flex items-center justify-center p-6 bg-slate-900/60 backdrop-blur-md">
        <div class="bg-white rounded-[3rem] max-w-xl w-full overflow-hidden shadow-2xl">
          <div class="bg-[#004d26] p-8 text-white flex justify-between items-center">
            <div>
              <h3 class="text-2xl font-black uppercase tracking-tight">{{ editingId ? 'Cập nhật dịch vụ' : 'Thêm dịch vụ mới' }}</h3>
              <p class="text-green-100/60 text-[10px] font-bold uppercase tracking-widest mt-1">Liên kết trực tiếp với bảng `hotel_service`</p>
            </div>
            <button @click="closeModal" class="w-10 h-10 flex items-center justify-center rounded-xl bg-white/10 text-white hover:bg-rose-500 transition-all">
              <i class="fas fa-times"></i>
            </button>
          </div>

          <form @submit.prevent="submitForm" class="p-8 space-y-5">
            <div class="space-y-2">
              <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Tên dịch vụ</label>
              <input v-model="form.name" required type="text" class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold text-sm">
            </div>
            <div class="space-y-2">
              <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Mô tả</label>
              <textarea v-model="form.description" rows="4" class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold text-sm resize-none"></textarea>
            </div>
            <div class="space-y-2">
              <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Giá</label>
              <input v-model.number="form.price" required min="0" step="0.01" type="number" class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold text-sm">
            </div>
            <button type="submit" :disabled="submitting" class="w-full bg-[#004d26] hover:bg-[#003d1e] disabled:opacity-50 text-white py-4 rounded-[2rem] font-black uppercase tracking-widest transition-all shadow-xl shadow-green-100">
              {{ submitting ? 'Đang lưu...' : (editingId ? 'Cập nhật dịch vụ' : 'Tạo dịch vụ') }}
            </button>
          </form>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { computed, onMounted, ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import axios from '../api/axios'
import AdminSidebar from '../components/AdminSidebar.vue'

const router = useRouter()
const services = ref([])
const loading = ref(false)
const submitting = ref(false)
const searchQuery = ref('')
const showModal = ref(false)
const editingId = ref(null)

// Pagination state
const currentPage = ref(1)
const itemsPerPage = ref(10)

watch(searchQuery, () => {
  currentPage.value = 1
})

const defaultForm = () => ({
  name: '',
  description: '',
  price: 0
})

const form = ref(defaultForm())

const fetchServices = async () => {
  try {
    loading.value = true
    const response = await axios.get('/hotel-services')
    services.value = response.data
  } catch (error) {
    console.error('Lỗi tải dịch vụ:', error)
    alert('Không thể tải danh sách dịch vụ.')
  } finally {
    loading.value = false
  }
}

const filteredServices = computed(() => {
  const query = searchQuery.value.trim().toLowerCase()
  if (!query) return services.value

  return services.value.filter(service =>
    service.name?.toLowerCase().includes(query)
    || service.description?.toLowerCase().includes(query)
  )
})

const totalPages = computed(() => Math.ceil(filteredServices.value.length / itemsPerPage.value))
const startIndex = computed(() => (currentPage.value - 1) * itemsPerPage.value)
const endIndex = computed(() => startIndex.value + itemsPerPage.value)
const paginatedServices = computed(() => filteredServices.value.slice(startIndex.value, endIndex.value))

const openCreateModal = () => {
  editingId.value = null
  form.value = defaultForm()
  showModal.value = true
}

const openEditModal = (service) => {
  editingId.value = service.id
  form.value = {
    name: service.name || '',
    description: service.description || '',
    price: service.price || 0
  }
  showModal.value = true
}

const closeModal = () => {
  showModal.value = false
  editingId.value = null
  form.value = defaultForm()
}

const submitForm = async () => {
  try {
    submitting.value = true
    if (editingId.value) {
      await axios.put(`/hotel-services/${editingId.value}`, form.value)
      alert('Cập nhật dịch vụ thành công!')
    } else {
      await axios.post('/hotel-services', form.value)
      alert('Thêm dịch vụ thành công!')
    }
    closeModal()
    await fetchServices()
  } catch (error) {
    console.error('Lỗi lưu dịch vụ:', error)
    alert('Không thể lưu dịch vụ.')
  } finally {
    submitting.value = false
  }
}

const deleteService = async (service) => {
  if (!confirm(`Bạn có chắc muốn xóa dịch vụ "${service.name}"?`)) return

  try {
    await axios.delete(`/hotel-services/${service.id}`)
    await fetchServices()
    alert('Đã xóa dịch vụ.')
  } catch (error) {
    console.error('Lỗi xóa dịch vụ:', error)
    alert('Không thể xóa dịch vụ.')
  }
}

const formatCurrency = (amount) => {
  if (amount == null) return '0 đ'
  return `${new Intl.NumberFormat('vi-VN').format(amount * 25000)} đ`
}

const logout = () => {
  localStorage.removeItem('user')
  router.push('/login')
}

onMounted(() => {
  fetchServices()
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
