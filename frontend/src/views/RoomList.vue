<template>
  <div class="min-h-screen bg-gray-100 py-8">
    <div class="max-w-7xl mx-auto px-4">
      <header class="flex justify-between items-center mb-10">
        <h2 class="text-3xl font-bold text-gray-800">Quản lý Phòng</h2>
        <div class="flex space-x-4">
          <router-link to="/" class="text-blue-600 hover:underline">Quay lại Dashboard</router-link>
          <button v-if="isAdmin" @click="showAddModal = true" class="bg-blue-600 hover:bg-blue-700 text-white px-6 py-2 rounded-md transition duration-300">
            Thêm phòng mới
          </button>
        </div>
      </header>

      <!-- Room Cards Grid -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-8">
        <div v-for="room in rooms" :key="room.id" class="bg-white rounded-xl shadow-lg overflow-hidden transform transition duration-300 hover:scale-105 border border-gray-100">
          <div class="p-6">
            <div class="flex justify-between items-start mb-4">
              <h3 class="text-2xl font-bold text-gray-800">Phòng {{ room.roomNumber }}</h3>
              <span :class="getStatusClass(room.status)" class="px-3 py-1 text-xs font-bold rounded-full uppercase">
                {{ room.status }}
              </span>
            </div>
            <p class="text-gray-600 mb-2">Loại: <strong>{{ room.type }}</strong></p>
            <p class="text-2xl font-bold text-blue-600 mb-6">${{ room.price }}<span class="text-sm text-gray-400 font-normal"> / đêm</span></p>
            
            <div class="flex flex-col space-y-3">
              <button v-if="room.status === 'AVAILABLE'" @click="openBookingModal(room)" class="w-full bg-green-600 hover:bg-green-700 text-white font-bold py-2 rounded-md transition duration-300">
                Đặt ngay
              </button>
              <div v-if="isAdmin || isStaff" class="flex space-x-2">
                <button @click="editRoom(room)" class="flex-1 bg-yellow-500 hover:bg-yellow-600 text-white py-2 rounded-md transition duration-300">Sửa</button>
                <button v-if="isAdmin" @click="deleteRoom(room.id)" class="flex-1 bg-red-600 hover:bg-red-700 text-white py-2 rounded-md transition duration-300">Xóa</button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Add/Edit Modal -->
      <div v-if="showAddModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
        <div class="bg-white rounded-xl max-w-md w-full p-8 shadow-2xl">
          <h3 class="text-2xl font-bold mb-6 text-gray-800">{{ currentRoom.id ? 'Sửa thông tin phòng' : 'Thêm phòng mới' }}</h3>
          <form @submit.prevent="saveRoom">
            <div class="mb-4">
              <label class="block text-sm font-bold text-gray-700 mb-1">Số phòng</label>
              <input v-model="currentRoom.roomNumber" type="text" class="w-full border rounded-md px-4 py-2 focus:ring-2 focus:ring-blue-500 outline-none" required>
            </div>
            <div class="mb-4">
              <label class="block text-sm font-bold text-gray-700 mb-1">Loại phòng</label>
              <select v-model="currentRoom.type" class="w-full border rounded-md px-4 py-2 focus:ring-2 focus:ring-blue-500 outline-none">
                <option>Single</option>
                <option>Double</option>
                <option>Suite</option>
                <option>Deluxe</option>
              </select>
            </div>
            <div class="mb-4">
              <label class="block text-sm font-bold text-gray-700 mb-1">Giá phòng ($)</label>
              <input v-model="currentRoom.price" type="number" class="w-full border rounded-md px-4 py-2 focus:ring-2 focus:ring-blue-500 outline-none" required>
            </div>
            <div v-if="currentRoom.id" class="mb-6">
              <label class="block text-sm font-bold text-gray-700 mb-1">Trạng thái</label>
              <select v-model="currentRoom.status" class="w-full border rounded-md px-4 py-2 focus:ring-2 focus:ring-blue-500 outline-none">
                <option value="AVAILABLE">AVAILABLE</option>
                <option value="BOOKED">BOOKED</option>
                <option value="OCCUPIED">OCCUPIED</option>
                <option value="MAINTENANCE">MAINTENANCE</option>
              </select>
            </div>
            <div class="flex justify-end space-x-3 mt-8">
              <button @click="closeModal" type="button" class="px-6 py-2 border rounded-md hover:bg-gray-50 font-bold">Hủy</button>
              <button type="submit" class="px-6 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 font-bold">Lưu</button>
            </div>
          </form>
        </div>
      </div>

      <!-- Booking Modal -->
      <div v-if="showBookingModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
        <div class="bg-white rounded-xl max-w-md w-full p-8 shadow-2xl">
          <h3 class="text-2xl font-bold mb-6 text-gray-800">Đặt phòng {{ selectedRoom.roomNumber }}</h3>
          <form @submit.prevent="bookRoom">
            <div class="mb-4">
              <label class="block text-sm font-bold text-gray-700 mb-1">Ngày đến</label>
              <input v-model="bookingData.checkInDate" type="date" class="w-full border rounded-md px-4 py-2" required>
            </div>
            <div class="mb-6">
              <label class="block text-sm font-bold text-gray-700 mb-1">Ngày đi</label>
              <input v-model="bookingData.checkOutDate" type="date" class="w-full border rounded-md px-4 py-2" required>
            </div>
            <div class="flex justify-end space-x-3 mt-8">
              <button @click="showBookingModal = false" type="button" class="px-6 py-2 border rounded-md hover:bg-gray-50 font-bold">Hủy</button>
              <button type="submit" class="px-6 py-2 bg-green-600 text-white rounded-md hover:bg-green-700 font-bold">Xác nhận đặt</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import axios from '../api/axios'

const rooms = ref([])
const showAddModal = ref(false)
const showBookingModal = ref(false)
const user = ref(JSON.parse(localStorage.getItem('user')))

const isAdmin = computed(() => user.value?.role === 'ADMIN')
const isStaff = computed(() => user.value?.role === 'STAFF')

const currentRoom = ref({
  roomNumber: '',
  type: 'Single',
  price: 0,
  status: 'AVAILABLE'
})

const selectedRoom = ref(null)
const bookingData = ref({
  checkInDate: '',
  checkOutDate: ''
})

const fetchRooms = async () => {
  try {
    const response = await axios.get('/rooms')
    rooms.value = response.data
  } catch (error) {
    console.error('Lỗi khi lấy danh sách phòng:', error)
  }
}

const saveRoom = async () => {
  try {
    if (currentRoom.value.id) {
      await axios.put(`/rooms/${currentRoom.value.id}`, currentRoom.value)
    } else {
      await axios.post('/rooms', currentRoom.value)
    }
    closeModal()
    fetchRooms()
  } catch (error) {
    alert('Lỗi khi lưu phòng: ' + error.message)
  }
}

const editRoom = (room) => {
  currentRoom.value = { ...room }
  showAddModal.value = true
}

const deleteRoom = async (id) => {
  if (confirm('Bạn có chắc chắn muốn xóa phòng này?')) {
    try {
      await axios.delete(`/rooms/${id}`)
      fetchRooms()
    } catch (error) {
      alert('Lỗi khi xóa phòng: ' + error.message)
    }
  }
}

const openBookingModal = (room) => {
  selectedRoom.value = room
  showBookingModal.value = true
}

const bookRoom = async () => {
  try {
    await axios.post('/bookings', {
      room: { id: selectedRoom.value.id },
      customer: { id: user.value.id },
      checkInDate: bookingData.value.checkInDate,
      checkOutDate: bookingData.value.checkOutDate
    })
    alert('Đặt phòng thành công! Chờ xác nhận.')
    showBookingModal.value = false
    fetchRooms()
  } catch (error) {
    alert('Lỗi khi đặt phòng: ' + (error.response?.data?.message || error.message))
  }
}

const closeModal = () => {
  showAddModal.value = false
  currentRoom.value = { roomNumber: '', type: 'Single', price: 0, status: 'AVAILABLE' }
}

const getStatusClass = (status) => {
  switch (status) {
    case 'AVAILABLE': return 'bg-green-100 text-green-800'
    case 'BOOKED': return 'bg-blue-100 text-blue-800'
    case 'OCCUPIED': return 'bg-red-100 text-red-800'
    case 'MAINTENANCE': return 'bg-yellow-100 text-yellow-800'
    default: return 'bg-gray-100 text-gray-800'
  }
}

onMounted(() => {
  fetchRooms()
})
</script>
