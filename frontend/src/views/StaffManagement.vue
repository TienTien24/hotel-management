<template>
  <div class="min-h-screen flex">
    <!-- Sidebar -->
    <aside class="w-64 bg-gray-800 text-white min-h-screen p-6 hidden md:block">
      <h1 class="text-2xl font-bold mb-10 text-center">Hotel Admin</h1>
      <nav>
        <router-link to="/dashboard" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700" :class="{'bg-gray-700': $route.path === '/dashboard'}">Báo cáo doanh thu</router-link>
        <router-link to="/rooms" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700" :class="{'bg-gray-700': $route.path === '/rooms'}">Quản lý Phòng</router-link>
        <router-link to="/bookings" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700" :class="{'bg-gray-700': $route.path === '/bookings'}">Quản lý Đặt phòng</router-link>
        <router-link to="/manage-services" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700" :class="{'bg-gray-700': $route.path === '/manage-services'}">Quản lý Dịch vụ</router-link>
        <router-link to="/staff" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700" :class="{'bg-gray-700': $route.path === '/staff'}">Quản lý Nhân viên</router-link>
      </nav>
    </aside>

    <!-- Main content -->
    <main class="flex-1 p-8 bg-gray-100">
      <header class="mb-10">
        <div class="flex justify-between items-center mb-6">
          <h2 class="text-3xl font-bold text-gray-800">Quản lý Nhân viên</h2>
          <span class="text-gray-600">Admin Panel</span>
        </div>
        
        <!-- Nút thêm nhân viên nổi bật -->
        <div class="flex justify-between items-center">
          <div class="text-sm text-gray-500">
            <span v-if="staffList.length > 0">Hiển thị {{ staffList.length }} nhân viên</span>
            <span v-else>Chưa có nhân viên nào trong hệ thống</span>
          </div>
          <button 
            @click="showCreateForm = true"
            class="bg-gradient-to-r from-blue-600 to-blue-700 hover:from-blue-700 hover:to-blue-800 text-white px-6 py-3 rounded-lg font-semibold shadow-lg hover:shadow-xl transform hover:scale-105 transition-all duration-200 flex items-center space-x-2"
          >
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"></path>
            </svg>
            <span>Thêm Nhân viên Mới</span>
          </button>
        </div>
      </header>

      <!-- Create Staff Form Modal -->
      <div v-if="showCreateForm" class="fixed inset-0 bg-black bg-opacity-60 flex items-center justify-center z-50 p-4">
        <div class="bg-white rounded-xl shadow-2xl max-w-md w-full mx-4 transform transition-all">
          <div class="bg-gradient-to-r from-blue-600 to-blue-700 text-white p-6 rounded-t-xl">
            <h3 class="text-2xl font-bold flex items-center">
              <svg class="w-6 h-6 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path>
              </svg>
              Tạo Nhân viên Mới
            </h3>
          </div>
          <div class="p-6">
          
          <form @submit.prevent="createStaff">
            <div class="mb-4">
              <label class="block text-gray-700 text-sm font-bold mb-2" for="fullName">
                Họ và tên *
              </label>
              <input
                id="fullName"
                v-model="newStaff.fullName"
                type="text"
                required
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Nhập họ và tên"
              />
            </div>

            <div class="mb-4">
              <label class="block text-gray-700 text-sm font-bold mb-2" for="username">
                Username *
              </label>
              <input
                id="username"
                v-model="newStaff.username"
                type="text"
                required
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Nhập username"
              />
            </div>

            <div class="mb-4">
              <label class="block text-gray-700 text-sm font-bold mb-2" for="password">
                Mật khẩu *
              </label>
              <input
                id="password"
                v-model="newStaff.password"
                type="password"
                required
                minlength="6"
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Nhập mật khẩu (tối thiểu 6 ký tự)"
              />
            </div>

            <div class="mb-4">
              <label class="block text-gray-700 text-sm font-bold mb-2" for="email">
                Email *
              </label>
              <input
                id="email"
                v-model="newStaff.email"
                type="email"
                required
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Nhập email"
              />
            </div>

            <div class="mb-6">
              <label class="block text-gray-700 text-sm font-bold mb-2" for="phone">
                Số điện thoại *
              </label>
              <input
                id="phone"
                v-model="newStaff.phone"
                type="tel"
                required
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Nhập số điện thoại"
              />
            </div>

            <div class="flex justify-end space-x-3 pt-4">
              <button
                type="button"
                @click="showCreateForm = false; resetForm()"
                class="px-6 py-3 border-2 border-gray-300 text-gray-700 rounded-lg hover:bg-gray-50 hover:border-gray-400 font-medium transition-all duration-200 flex items-center"
              >
                <svg class="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
                </svg>
                Hủy
              </button>
              <button
                type="submit"
                :disabled="creating"
                class="px-6 py-3 bg-gradient-to-r from-blue-600 to-blue-700 text-white rounded-lg hover:from-blue-700 hover:to-blue-800 font-medium transition-all duration-200 disabled:opacity-50 disabled:cursor-not-allowed flex items-center shadow-lg hover:shadow-xl"
              >
                <svg v-if="creating" class="w-4 h-4 mr-2 animate-spin" fill="none" viewBox="0 0 24 24">
                  <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                  <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                </svg>
                <svg v-else class="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
                </svg>
                {{ creating ? 'Đang tạo...' : 'Tạo Nhân viên' }}
              </button>
            </div>
          </form>
          </div>
        </div>
      </div>

      <div class="bg-white rounded-xl shadow-lg overflow-hidden">
        <div v-if="loading" class="text-center py-16">
          <div class="inline-flex items-center">
            <svg class="animate-spin h-8 w-8 text-blue-600 mr-3" viewBox="0 0 24 24">
              <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
              <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
            </svg>
            <span class="text-gray-600 font-medium">Đang tải danh sách nhân viên...</span>
          </div>
        </div>
        <div v-else-if="staffList.length === 0" class="text-center py-16">
          <svg class="mx-auto h-16 w-16 text-gray-400 mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z"></path>
          </svg>
          <h3 class="text-lg font-medium text-gray-900 mb-2">Chưa có nhân viên nào</h3>
          <p class="text-gray-500">Bắt đầu bằng cách thêm nhân viên đầu tiên vào hệ thống.</p>
        </div>
        <div v-else class="overflow-x-auto">
          <table class="w-full">
            <thead class="bg-gray-50 border-b border-gray-200">
              <tr>
                <th class="py-4 px-6 text-left font-semibold text-gray-700 text-sm uppercase tracking-wider">Nhân viên</th>
                <th class="py-4 px-6 text-left font-semibold text-gray-700 text-sm uppercase tracking-wider">Thông tin liên hệ</th>
                <th class="py-4 px-6 text-left font-semibold text-gray-700 text-sm uppercase tracking-wider">Trạng thái</th>
                <th class="py-4 px-6 text-right font-semibold text-gray-700 text-sm uppercase tracking-wider">Thao tác</th>
              </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
              <tr v-for="staff in staffList" :key="staff.id" class="hover:bg-gray-50 transition-colors">
                <td class="py-4 px-6">
                  <div class="flex items-center">
                    <div class="h-10 w-10 flex-shrink-0">
                      <div class="h-10 w-10 rounded-full bg-gradient-to-r from-blue-500 to-blue-600 flex items-center justify-center">
                        <span class="text-white font-semibold text-sm">{{ staff.fullName.charAt(0).toUpperCase() }}</span>
                      </div>
                    </div>
                    <div class="ml-4">
                      <div class="text-sm font-medium text-gray-900">{{ staff.fullName }}</div>
                      <div class="text-sm text-gray-500">@{{ staff.username }}</div>
                    </div>
                  </div>
                </td>
                <td class="py-4 px-6">
                  <div class="text-sm text-gray-900">{{ staff.email }}</div>
                  <div class="text-sm text-gray-500">{{ staff.phone }}</div>
                </td>
                <td class="py-4 px-6">
                  <span 
                    :class="[
                      'inline-flex px-3 py-1 text-xs font-semibold rounded-full',
                      staff.isLocked 
                        ? 'bg-red-100 text-red-800' 
                        : 'bg-green-100 text-green-800'
                    ]"
                  >
                    <span class="flex items-center">
                      <span 
                        :class="[
                          'w-2 h-2 mr-1.5 rounded-full',
                          staff.isLocked ? 'bg-red-400' : 'bg-green-400'
                        ]"
                      ></span>
                      {{ staff.isLocked ? 'Đã khóa' : 'Hoạt động' }}
                    </span>
                  </span>
                </td>
                <td class="py-4 px-6 text-right">
                  <div class="flex justify-end space-x-2">
                    <button 
                      @click="toggleLock(staff)"
                      :class="[
                        'inline-flex items-center px-3 py-2 border border-transparent text-sm leading-4 font-medium rounded-md transition-colors',
                        staff.isLocked 
                          ? 'text-green-700 bg-green-100 hover:bg-green-200' 
                          : 'text-red-700 bg-red-100 hover:bg-red-200'
                      ]"
                    >
                      <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path v-if="staff.isLocked" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 11V7a4 4 0 118 0m-4 8v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2z"></path>
                        <path v-else stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"></path>
                      </svg>
                      {{ staff.isLocked ? 'Mở khóa' : 'Khóa' }}
                    </button>
                    <button 
                      @click="deleteStaff(staff)"
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
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from '../api/axios'

const staffList = ref([])
const loading = ref(true)
const showCreateForm = ref(false)
const creating = ref(false)
const router = useRouter()

const newStaff = ref({
  fullName: '',
  username: '',
  password: '',
  email: '',
  phone: ''
})

const fetchStaff = async () => {
  try {
    loading.value = true
    // Use the new staff endpoint to get only staff members
    const response = await axios.get('/staff/list')
    staffList.value = response.data
  } catch (error) {
    console.error('Lỗi khi lấy danh sách nhân viên:', error)
    // Fallback to old method if new endpoint doesn't work
    try {
      const fallbackResponse = await axios.get('/auth/users')
      staffList.value = fallbackResponse.data.filter(user => user.role === 'STAFF')
    } catch (fallbackError) {
      console.error('Lỗi khi lấy danh sách nhân viên (fallback):', fallbackError)
    }
  } finally {
    loading.value = false
  }
}

const createStaff = async () => {
  try {
    creating.value = true
    await axios.post('/staff/create', newStaff.value)
    alert('Tạo nhân viên thành công!')
    showCreateForm.value = false
    resetForm()
    fetchStaff()
  } catch (error) {
    console.error('Lỗi khi tạo nhân viên:', error)
    alert(error.response?.data || 'Không thể tạo nhân viên. Vui lòng thử lại.')
  } finally {
    creating.value = false
  }
}

const resetForm = () => {
  newStaff.value = {
    fullName: '',
    username: '',
    password: '',
    email: '',
    phone: ''
  }
}

const toggleLock = async (staff) => {
  const action = staff.isLocked ? 'mở khóa' : 'khóa'
  if (confirm(`Bạn có chắc muốn ${action} tài khoản ${staff.username}?`)) {
    try {
      await axios.put(`/staff/${staff.id}/lock?lock=${!staff.isLocked}`)
      alert(`Đã ${action} thành công!`)
      fetchStaff()
    } catch (error) {
      console.error('Lỗi khi cập nhật trạng thái:', error)
      alert('Không thể cập nhật trạng thái tài khoản.')
    }
  }
}

const deleteStaff = async (staff) => {
  if (confirm(`Bạn có chắc muốn xóa nhân viên ${staff.fullName}? Hành động này không thể hoàn tác.`)) {
    try {
      await axios.delete(`/staff/${staff.id}`)
      alert('Đã xóa nhân viên thành công!')
      fetchStaff()
    } catch (error) {
      console.error('Lỗi khi xóa nhân viên:', error)
      alert('Không thể xóa nhân viên.')
    }
  }
}

const logout = () => {
  localStorage.removeItem('user')
  router.push('/login')
}

onMounted(() => {
  const user = JSON.parse(localStorage.getItem('user'))
  if (!user || user.role !== 'ADMIN') {
    router.push('/dashboard')
    return
  }
  fetchStaff()
})
</script>