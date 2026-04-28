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
      <header class="flex justify-between items-center mb-10">
        <h2 class="text-3xl font-bold text-gray-800">Quản lý Nhân viên</h2>
        <div class="flex items-center space-x-4">
          <span class="text-gray-600">Admin Panel</span>
        </div>
      </header>

      <div class="bg-white rounded-lg shadow-sm p-6">
        <div v-if="loading" class="text-center py-10">Đang tải...</div>
        <div v-else-if="staffList.length === 0" class="text-center py-10 text-gray-500">
          Chưa có nhân viên nào trong hệ thống.
        </div>
        <div v-else class="overflow-x-auto">
          <table class="w-full text-left">
            <thead>
              <tr class="border-b border-gray-100">
                <th class="py-3 px-4 font-bold text-gray-600 text-sm">Họ tên</th>
                <th class="py-3 px-4 font-bold text-gray-600 text-sm">Username</th>
                <th class="py-3 px-4 font-bold text-gray-600 text-sm">Email / SĐT</th>
                <th class="py-3 px-4 font-bold text-gray-600 text-sm">Trạng thái</th>
                <th class="py-3 px-4 font-bold text-gray-600 text-sm text-right">Thao tác</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="staff in staffList" :key="staff.id" class="border-b border-gray-50 hover:bg-gray-50 transition-colors">
                <td class="py-4 px-4 font-medium text-gray-800">{{ staff.fullName }}</td>
                <td class="py-4 px-4 text-sm text-gray-600">{{ staff.username }}</td>
                <td class="py-4 px-4 text-sm text-gray-600">
                  <div>{{ staff.email }}</div>
                  <div class="text-xs text-gray-400">{{ staff.phone }}</div>
                </td>
                <td class="py-4 px-4">
                  <span 
                    :class="[
                      'px-3 py-1 rounded-full text-[10px] font-bold uppercase tracking-widest',
                      staff.isLocked ? 'bg-red-100 text-red-700' : 'bg-emerald-100 text-emerald-700'
                    ]"
                  >
                    {{ staff.isLocked ? 'Đã khóa' : 'Hoạt động' }}
                  </span>
                </td>
                <td class="py-4 px-4 text-right">
                  <button 
                    @click="toggleLock(staff)"
                    :class="[
                      'px-4 py-2 rounded-md text-sm font-bold transition-colors',
                      staff.isLocked ? 'bg-emerald-600 hover:bg-emerald-700 text-white' : 'bg-red-600 hover:bg-red-700 text-white'
                    ]"
                  >
                    {{ staff.isLocked ? 'Mở khóa' : 'Khóa' }}
                  </button>
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
const router = useRouter()

const fetchStaff = async () => {
  try {
    loading.value = true
    // In a real app, you'd have an endpoint like /api/auth/users?role=STAFF
    // For now, let's get all users and filter
    const response = await axios.get('/auth/users')
    staffList.value = response.data.filter(user => user.role === 'STAFF')
  } catch (error) {
    console.error('Lỗi khi lấy danh sách nhân viên:', error)
  } finally {
    loading.value = false
  }
}

const toggleLock = async (staff) => {
  const action = staff.isLocked ? 'mở khóa' : 'khóa'
  if (confirm(`Bạn có chắc muốn ${action} tài khoản ${staff.username}?`)) {
    try {
      await axios.put(`/auth/user/${staff.id}/lock?lock=${!staff.isLocked}`)
      alert(`Đã ${action} thành công!`)
      fetchStaff()
    } catch (error) {
      console.error('Lỗi khi cập nhật trạng thái:', error)
      alert('Không thể cập nhật trạng thái tài khoản.')
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