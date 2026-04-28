<template>
  <div class="min-h-screen flex">
    <!-- Sidebar -->
    <aside class="w-64 bg-gray-800 text-white min-h-screen p-6 hidden md:block">
      <h1 class="text-2xl font-bold mb-10 text-center">Hotel Admin</h1>
      <nav>
        <router-link to="/bookings" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700" :class="{'bg-gray-700': $route.path === '/bookings'}">Quản lý Đặt phòng</router-link>
        <router-link to="/manage-services" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700" :class="{'bg-gray-700': $route.path === '/manage-services'}">Quản lý Dịch vụ</router-link>
      </nav>
    </aside>

    <!-- Main content -->
    <main class="flex-1 p-8 bg-gray-100">
      <header class="flex justify-between items-center mb-10">
        <h2 class="text-3xl font-bold text-gray-800">Quản lý Dịch vụ Khách sạn</h2>
        <button @click="openAddModal" class="bg-emerald-600 hover:bg-emerald-700 text-white px-6 py-2 rounded-full font-bold shadow-lg transition-all flex items-center">
          <span class="mr-2">+</span> Thêm dịch vụ mới
        </button>
      </header>

      <div class="bg-white rounded-2xl shadow-sm overflow-hidden border border-gray-100">
        <table class="w-full text-left">
          <thead>
            <tr class="bg-gray-50 border-b border-gray-100">
              <th class="py-4 px-6 text-xs font-black text-gray-400 uppercase tracking-widest">Tên dịch vụ</th>
              <th class="py-4 px-6 text-xs font-black text-gray-400 uppercase tracking-widest">Mô tả</th>
              <th class="py-4 px-6 text-xs font-black text-gray-400 uppercase tracking-widest">Giá (USD)</th>
              <th class="py-4 px-6 text-xs font-black text-gray-400 uppercase tracking-widest text-right">Thao tác</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="service in services" :key="service.id" class="border-b border-gray-50 hover:bg-gray-50 transition-colors">
              <td class="py-4 px-6 font-bold text-gray-800">{{ service.name }}</td>
              <td class="py-4 px-6 text-sm text-gray-500">{{ service.description }}</td>
              <td class="py-4 px-6 font-black text-emerald-700">${{ service.price }}</td>
              <td class="py-4 px-6 text-right space-x-2">
                <button @click="openEditModal(service)" class="p-2 text-blue-600 hover:bg-blue-50 rounded-lg transition-colors">
                  Sửa
                </button>
                <button @click="deleteService(service.id)" class="p-2 text-red-600 hover:bg-red-50 rounded-lg transition-colors">
                  Xóa
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- Service Modal -->
      <div v-if="showModal" class="fixed inset-0 bg-black/50 backdrop-blur-sm flex items-center justify-center z-[100] p-4">
        <div class="bg-white rounded-[2rem] shadow-2xl w-full max-w-md overflow-hidden">
          <div class="bg-emerald-800 p-6 text-white">
            <h3 class="text-xl font-black uppercase tracking-widest">{{ isEditing ? 'Cập nhật dịch vụ' : 'Thêm dịch vụ mới' }}</h3>
          </div>
          <div class="p-8 space-y-4">
            <div>
              <label class="block text-xs font-black text-gray-400 uppercase tracking-widest mb-2">Tên dịch vụ</label>
              <input v-model="currentService.name" type="text" class="w-full bg-gray-50 border-0 rounded-2xl px-6 py-4 focus:ring-2 focus:ring-emerald-800 outline-none">
            </div>
            <div>
              <label class="block text-xs font-black text-gray-400 uppercase tracking-widest mb-2">Mô tả</label>
              <textarea v-model="currentService.description" class="w-full bg-gray-50 border-0 rounded-2xl px-6 py-4 focus:ring-2 focus:ring-emerald-800 outline-none h-24"></textarea>
            </div>
            <div>
              <label class="block text-xs font-black text-gray-400 uppercase tracking-widest mb-2">Giá (USD)</label>
              <input v-model.number="currentService.price" type="number" class="w-full bg-gray-50 border-0 rounded-2xl px-6 py-4 focus:ring-2 focus:ring-emerald-800 outline-none">
            </div>
          </div>
          <div class="p-8 pt-0 flex space-x-4">
            <button @click="showModal = false" class="flex-1 px-6 py-4 rounded-2xl font-black uppercase tracking-widest text-sm bg-gray-100 text-gray-500 hover:bg-gray-200 transition-all">
              Hủy
            </button>
            <button @click="saveService" class="flex-1 px-6 py-4 rounded-2xl font-black uppercase tracking-widest text-sm bg-emerald-800 text-white hover:bg-emerald-900 shadow-lg transition-all">
              {{ isEditing ? 'Cập nhật' : 'Lưu' }}
            </button>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from '../api/axios'

const services = ref([])
const showModal = ref(false)
const isEditing = ref(false)
const user = ref(null)
const router = useRouter()

const currentService = ref({
  id: null,
  name: '',
  description: '',
  price: 0
})

const fetchServices = async () => {
  try {
    const response = await axios.get('/hotel-services')
    services.value = response.data
  } catch (error) {
    console.error('Lỗi khi tải danh sách dịch vụ:', error)
  }
}

const openAddModal = () => {
  isEditing.value = false
  currentService.value = { id: null, name: '', description: '', price: 0 }
  showModal.value = true
}

const openEditModal = (service) => {
  isEditing.value = true
  currentService.value = { ...service }
  showModal.value = true
}

const saveService = async () => {
  try {
    if (isEditing.value) {
      await axios.put(`/hotel-services/${currentService.value.id}`, currentService.value)
      alert('Cập nhật dịch vụ thành công!')
    } else {
      await axios.post('/hotel-services', currentService.value)
      alert('Thêm dịch vụ thành công!')
    }
    showModal.value = false
    fetchServices()
  } catch (error) {
    console.error('Lỗi khi lưu dịch vụ:', error)
    alert('Không thể lưu dịch vụ. Vui lòng thử lại.')
  }
}

const deleteService = async (id) => {
  if (confirm('Bạn có chắc chắn muốn xóa dịch vụ này không?')) {
    try {
      await axios.delete(`/hotel-services/${id}`)
      alert('Đã xóa dịch vụ thành công!')
      fetchServices()
    } catch (error) {
      console.error('Lỗi khi xóa dịch vụ:', error)
      alert('Không thể xóa dịch vụ này.')
    }
  }
}

const logout = () => {
  localStorage.removeItem('user')
  router.push('/login')
}

onMounted(() => {
  const userData = localStorage.getItem('user')
  if (!userData) {
    router.push('/login')
    return
  }
  user.value = JSON.parse(userData)
  if (user.value.role !== 'ADMIN' && user.value.role !== 'STAFF') {
    router.push('/')
    return
  }
  fetchServices()
})
</script>