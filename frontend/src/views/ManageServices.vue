<template>
  <div class="min-h-screen flex">
    <!-- Sidebar -->
    <aside class="w-64 bg-gray-800 text-white min-h-screen p-6 hidden md:block">
      <h1 class="text-2xl font-bold mb-10 text-center">Hotel Admin</h1>
      <nav>
        <router-link to="/dashboard" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700" :class="{'bg-gray-700': $route.path === '/dashboard'}">
          {{ user?.role === 'ADMIN' ? 'Báo cáo doanh thu' : 'Tổng quan' }}
        </router-link>
        <router-link to="/rooms" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700" :class="{'bg-gray-700': $route.path === '/rooms'}">Quản lý Phòng</router-link>
        <router-link to="/bookings" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700" :class="{'bg-gray-700': $route.path === '/bookings'}">Quản lý Đặt phòng</router-link>
        <router-link to="/manage-services" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700" :class="{'bg-gray-700': $route.path === '/manage-services'}">Quản lý Dịch vụ</router-link>
        <router-link v-if="user?.role === 'ADMIN'" to="/staff" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700" :class="{'bg-gray-700': $route.path === '/staff'}">Quản lý Nhân viên</router-link>
      </nav>
    </aside>

    <!-- Main content -->
    <main class="flex-1 p-8 bg-gray-100">
      <header class="mb-10">
        <div class="flex justify-between items-center mb-6">
          <h2 class="text-3xl font-bold text-gray-800">Quản lý Dịch vụ Khách sạn</h2>
          <div class="text-sm text-gray-500">
            <span v-if="services.length > 0">Hiển thị {{ services.length }} dịch vụ</span>
            <span v-else>Chưa có dịch vụ nào trong hệ thống</span>
          </div>
        </div>
        
        <!-- Nút thêm dịch vụ nổi bật -->
        <div class="flex justify-between items-center">
          <div class="text-sm text-gray-500">
            <span v-if="services.length > 0">Danh sách dịch vụ khách sạn</span>
            <span v-else>Bắt đầu thêm dịch vụ đầu tiên</span>
          </div>
          <button 
            @click="openAddModal" 
            class="bg-gradient-to-r from-emerald-600 to-emerald-700 hover:from-emerald-700 hover:to-emerald-800 text-white px-6 py-3 rounded-lg font-semibold shadow-lg hover:shadow-xl transform hover:scale-105 transition-all duration-200 flex items-center space-x-2"
          >
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"></path>
            </svg>
            <span>Thêm Dịch Vụ Mới</span>
          </button>
        </div>
      </header>

      <div class="bg-white rounded-xl shadow-lg overflow-hidden">
        <div v-if="services.length === 0" class="text-center py-16">
          <svg class="mx-auto h-16 w-16 text-gray-400 mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10"></path>
          </svg>
          <h3 class="text-lg font-medium text-gray-900 mb-2">Chưa có dịch vụ nào</h3>
          <p class="text-gray-500">Bắt đầu bằng cách thêm dịch vụ đầu tiên vào hệ thống.</p>
        </div>
        <div v-else class="overflow-x-auto">
          <table class="w-full">
            <thead class="bg-gray-50 border-b border-gray-200">
              <tr>
                <th class="py-4 px-6 text-left font-semibold text-gray-700 text-sm uppercase tracking-wider">Dịch vụ</th>
                <th class="py-4 px-6 text-left font-semibold text-gray-700 text-sm uppercase tracking-wider">Mô tả</th>
                <th class="py-4 px-6 text-left font-semibold text-gray-700 text-sm uppercase tracking-wider">Giá</th>
                <th class="py-4 px-6 text-right font-semibold text-gray-700 text-sm uppercase tracking-wider">Thao tác</th>
              </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
              <tr v-for="service in services" :key="service.id" class="hover:bg-gray-50 transition-colors">
                <td class="py-4 px-6">
                  <div class="flex items-center">
                    <div class="h-10 w-10 flex-shrink-0">
                      <div class="h-10 w-10 rounded-full bg-gradient-to-r from-emerald-500 to-emerald-600 flex items-center justify-center">
                        <span class="text-white font-semibold text-sm">{{ service.name.charAt(0).toUpperCase() }}</span>
                      </div>
                    </div>
                    <div class="ml-4">
                      <div class="text-sm font-medium text-gray-900">{{ service.name }}</div>
                      <div class="text-sm text-gray-500">Dịch vụ khách sạn</div>
                    </div>
                  </div>
                </td>
                <td class="py-4 px-6">
                  <div class="text-sm text-gray-900">{{ service.description || 'Không có mô tả' }}</div>
                </td>
                <td class="py-4 px-6">
                  <div class="flex items-center">
                    <span class="text-lg font-semibold text-emerald-600">${{ service.price }}</span>
                    <span class="ml-1 text-sm text-gray-500">USD</span>
                  </div>
                </td>
                <td class="py-4 px-6 text-right">
                  <div class="flex justify-end space-x-2">
                    <button 
                      @click="openEditModal(service)" 
                      class="inline-flex items-center px-3 py-2 border border-transparent text-sm leading-4 font-medium rounded-md text-blue-700 bg-blue-100 hover:bg-blue-200 transition-colors"
                    >
                      <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path>
                      </svg>
                      Sửa
                    </button>
                    <button 
                      @click="deleteService(service.id)" 
                      class="inline-flex items-center px-3 py-2 border border-transparent text-sm leading-4 font-medium rounded-md text-red-700 bg-red-100 hover:bg-red-200 transition-colors"
                    >
                      <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path>
                      </svg>
                      Xóa
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <!-- Service Modal -->
      <div v-if="showModal" class="fixed inset-0 bg-black bg-opacity-60 flex items-center justify-center z-50 p-4">
        <div class="bg-white rounded-xl shadow-2xl max-w-md w-full mx-4 transform transition-all">
          <div class="bg-gradient-to-r from-emerald-600 to-emerald-700 text-white p-6 rounded-t-xl">
            <h3 class="text-2xl font-bold flex items-center">
              <svg class="w-6 h-6 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10"></path>
              </svg>
              {{ isEditing ? 'Cập nhật Dịch vụ' : 'Thêm Dịch vụ Mới' }}
            </h3>
          </div>
          <div class="p-6">
            <form @submit.prevent="saveService">
              <div class="mb-4">
                <label class="block text-gray-700 text-sm font-bold mb-2" for="serviceName">
                  Tên dịch vụ *
                </label>
                <input
                  id="serviceName"
                  v-model="currentService.name"
                  type="text"
                  required
                  class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  placeholder="Nhập tên dịch vụ"
                />
              </div>

              <div class="mb-4">
                <label class="block text-gray-700 text-sm font-bold mb-2" for="serviceDescription">
                  Mô tả
                </label>
                <textarea
                  id="serviceDescription"
                  v-model="currentService.description"
                  rows="3"
                  class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  placeholder="Nhập mô tả dịch vụ"
                ></textarea>
              </div>

              <div class="mb-6">
                <label class="block text-gray-700 text-sm font-bold mb-2" for="servicePrice">
                  Giá (USD) *
                </label>
                <input
                  id="servicePrice"
                  v-model.number="currentService.price"
                  type="number"
                  required
                  min="0"
                  step="0.01"
                  class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  placeholder="Nhập giá dịch vụ"
                />
              </div>

              <div class="flex justify-end space-x-3 pt-4">
                <button
                  type="button"
                  @click="showModal = false"
                  class="px-6 py-3 border-2 border-gray-300 text-gray-700 rounded-lg hover:bg-gray-50 hover:border-gray-400 font-medium transition-all duration-200 flex items-center"
                >
                  <svg class="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
                  </svg>
                  Hủy
                </button>
                <button
                  type="submit"
                  class="px-6 py-3 bg-gradient-to-r from-emerald-600 to-emerald-700 text-white rounded-lg hover:from-emerald-700 hover:to-emerald-800 font-medium transition-all duration-200 flex items-center shadow-lg hover:shadow-xl"
                >
                  <svg class="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
                  </svg>
                  {{ isEditing ? 'Cập nhật Dịch vụ' : 'Thêm Dịch vụ' }}
                </button>
              </div>
            </form>
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