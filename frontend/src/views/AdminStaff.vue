<template>
  <div class="min-h-screen flex bg-[#f0f2f5] font-sans text-[#1e293b]">
    <AdminSidebar />

    <!-- Main Content -->
    <main class="flex-1 p-10 overflow-y-auto">
      <header class="flex justify-between items-center mb-10">
        <div>
          <h2 class="text-3xl font-black tracking-tight text-slate-800 uppercase">Quản lý nhân viên</h2>
        </div>
        <button @click="showCreateForm = true" class="bg-[#004d26] hover:bg-[#003d1e] text-white px-8 py-4 rounded-2xl font-black uppercase tracking-widest text-xs transition-all shadow-xl shadow-green-100 flex items-center gap-3">
          <i class="fas fa-user-plus"></i>
          Thêm nhân viên mới
        </button>
      </header>

      <!-- Staff List Table -->
      <div class="bg-white rounded-[2.5rem] shadow-sm border border-slate-100 overflow-hidden">
        <div class="p-8 border-b border-slate-50 flex justify-between items-center bg-slate-50/50">
          <div class="relative w-96">
            <i class="fas fa-search absolute left-5 top-1/2 -translate-y-1/2 text-slate-300"></i>
            <input type="text" v-model="searchQuery" placeholder="Tìm kiếm nhân viên..." class="w-full bg-white border-2 border-slate-100 rounded-xl pl-12 pr-5 py-3 outline-none focus:border-[#004d26] transition-all font-bold text-sm">
          </div>
          <div class="flex gap-4">
             <span class="px-4 py-2 bg-white rounded-xl border border-slate-100 text-[10px] font-black uppercase tracking-widest text-slate-400">Tổng cộng: {{ filteredStaff.length }}</span>
          </div>
        </div>

        <div class="overflow-x-auto">
          <table class="w-full">
            <thead>
              <tr class="bg-slate-50/50 text-left">
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Nhân viên</th>
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Username</th>
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Liên hệ</th>
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Vai trò</th>
                <th class="px-8 py-5 text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Trạng thái</th>
                <th class="px-8 py-5 text-center text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Thao tác</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-slate-50">
              <tr v-for="staff in filteredStaff" :key="staff.id" class="hover:bg-slate-50/50 transition-colors">
                <td class="px-8 py-6">
                  <div class="flex items-center gap-4">
                    <div class="w-12 h-12 rounded-2xl bg-slate-100 flex items-center justify-center font-black text-slate-400">
                      {{ staff.fullName?.charAt(0) || 'N' }}
                    </div>
                    <div>
                      <p class="font-black text-slate-800 uppercase tracking-tight">{{ staff.fullName }}</p>
                      <p class="text-[10px] font-bold text-slate-400 mt-1 uppercase">Mã NV: #{{ staff.id }}</p>
                    </div>
                  </div>
                </td>
                <td class="px-8 py-6">
                  <span class="text-sm font-bold text-slate-600 bg-slate-100 px-3 py-1 rounded-lg">{{ staff.username }}</span>
                </td>
                <td class="px-8 py-6">
                  <div class="space-y-1">
                    <p class="text-xs font-bold text-slate-600 flex items-center gap-2"><i class="fas fa-envelope text-slate-300 w-4"></i> {{ staff.email }}</p>
                    <p class="text-xs font-bold text-slate-600 flex items-center gap-2"><i class="fas fa-phone text-slate-300 w-4"></i> {{ staff.phone }}</p>
                  </div>
                </td>
                <td class="px-8 py-6">
                  <span :class="staff.role === 'ADMIN' ? 'bg-blue-50 text-blue-600' : 'bg-emerald-50 text-emerald-600'" class="px-4 py-1.5 rounded-full text-[9px] font-black uppercase tracking-widest border border-current opacity-80">
                    {{ staff.role }}
                  </span>
                </td>
                <td class="px-8 py-6">
                  <div class="flex items-center gap-2">
                    <span :class="staff.isLocked ? 'bg-rose-500' : 'bg-emerald-500'" class="w-2 h-2 rounded-full"></span>
                    <span class="text-[10px] font-black uppercase tracking-widest text-slate-500">{{ staff.isLocked ? 'Bị khóa' : 'Hoạt động' }}</span>
                  </div>
                </td>
                <td class="px-8 py-6">
                  <div class="flex justify-center gap-2">
                    <button @click="toggleLock(staff)" class="w-10 h-10 rounded-xl bg-slate-50 text-slate-400 hover:bg-rose-50 hover:text-rose-600 transition-all flex items-center justify-center shadow-sm">
                      <i :class="staff.isLocked ? 'fas fa-unlock' : 'fas fa-lock'"></i>
                    </button>
                    <button @click="deleteStaff(staff.id)" class="w-10 h-10 rounded-xl bg-slate-50 text-slate-400 hover:bg-rose-500 hover:text-white transition-all flex items-center justify-center shadow-sm">
                      <i class="fas fa-trash"></i>
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </main>

    <!-- Create Staff Modal -->
    <div v-if="showCreateForm" class="fixed inset-0 z-[100] flex items-center justify-center p-6 bg-slate-900/60 backdrop-blur-md">
      <div class="bg-white rounded-[3.5rem] max-w-xl w-full overflow-hidden shadow-2xl animate-in fade-in zoom-in duration-300">
        <div class="bg-[#004d26] p-10 text-white flex justify-between items-center">
          <div>
            <h3 class="text-3xl font-black uppercase tracking-tight">Thêm nhân viên</h3>
            <p class="text-green-100/60 text-[10px] font-bold uppercase tracking-widest mt-1">Khởi tạo tài khoản nhân sự mới</p>
          </div>
          <button @click="showCreateForm = false" class="w-12 h-12 flex items-center justify-center rounded-2xl bg-white/10 text-white hover:bg-rose-500 transition-all">
            <i class="fas fa-times"></i>
          </button>
        </div>
        
        <form @submit.prevent="handleCreateStaff" class="p-10 space-y-6">
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div class="space-y-2">
              <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Họ và tên</label>
              <input v-model="form.fullName" required type="text" placeholder="Nhập họ và tên..." class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold text-sm">
            </div>
            <div class="space-y-2">
              <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Username</label>
              <input v-model="form.username" required type="text" placeholder="Tên đăng nhập..." class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold text-sm">
            </div>
          </div>

          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div class="space-y-2">
              <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Mật khẩu</label>
              <input v-model="form.password" required type="password" placeholder="Tối thiểu 6 ký tự..." class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold text-sm">
            </div>
            <div class="space-y-2">
              <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Vai trò</label>
              <div class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 font-bold text-sm text-slate-500">
                Nhân viên (STAFF)
              </div>
            </div>
          </div>

          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div class="space-y-2">
              <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Email</label>
              <input v-model="form.email" required type="email" placeholder="email@hotel.com" class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold text-sm">
            </div>
            <div class="space-y-2">
              <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Điện thoại</label>
              <input v-model="form.phone" required type="tel" placeholder="0123 456 789" class="w-full bg-slate-50 border-2 border-slate-100 rounded-2xl px-6 py-4 outline-none focus:border-[#004d26] transition-all font-bold text-sm">
            </div>
          </div>

          <button type="submit" class="w-full bg-[#004d26] hover:bg-[#003d1e] text-white py-6 rounded-[2.5rem] font-black uppercase tracking-widest transition-all shadow-xl shadow-green-100 flex items-center justify-center gap-4 mt-4">
            <i class="fas fa-save"></i>
            <span>Lưu thông tin nhân viên</span>
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from '../api/axios'
import AdminSidebar from '../components/AdminSidebar.vue'

const router = useRouter()
const staffList = ref([])
const searchQuery = ref('')
const showCreateForm = ref(false)

const form = ref({
  fullName: '',
  username: '',
  password: '',
  email: '',
  phone: ''
})

const fetchStaff = async () => {
  try {
    const response = await axios.get('/staff/list')
    staffList.value = response.data
  } catch (error) {
    console.error('Lỗi tải danh sách nhân viên:', error)
    alert('Không thể tải danh sách nhân viên.')
  }
}

const filteredStaff = computed(() => {
  if (!searchQuery.value) return staffList.value
  const query = searchQuery.value.toLowerCase()
  return staffList.value.filter(s => 
    s.fullName.toLowerCase().includes(query) || 
    s.username.toLowerCase().includes(query) ||
    s.email.toLowerCase().includes(query)
  )
})

const handleCreateStaff = async () => {
  try {
    await axios.post('/staff/create', form.value)
    alert('Thêm nhân viên thành công!')
    showCreateForm.value = false
    form.value = { fullName: '', username: '', password: '', email: '', phone: '' }
    await fetchStaff()
  } catch (error) {
    alert(error.response?.data || 'Không thể tạo nhân viên')
  }
}

const toggleLock = async (staff) => {
  try {
    await axios.put(`/staff/${staff.id}/lock`, null, {
      params: { lock: !staff.isLocked }
    })
    await fetchStaff()
  } catch (error) {
    alert('Không thể thay đổi trạng thái khóa')
  }
}

const deleteStaff = async (id) => {
  if (!confirm('Bạn có chắc chắn muốn xóa nhân viên này?')) return
  try {
    await axios.delete(`/staff/${id}`)
    await fetchStaff()
  } catch (error) {
    alert('Không thể xóa nhân viên này')
  }
}

const logout = () => {
  localStorage.removeItem('user')
  router.push('/login')
}

onMounted(() => {
  fetchStaff()
})
</script>

<style scoped>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css');

main::-webkit-scrollbar { width: 6px; }
main::-webkit-scrollbar-track { background: transparent; }
main::-webkit-scrollbar-thumb { background: #cbd5e1; border-radius: 10px; }

.animate-in { animation: animate-in 0.3s ease-out; }
@keyframes animate-in {
  from { opacity: 0; transform: scale(0.98); }
  to { opacity: 1; transform: scale(1); }
}

select {
  appearance: none;
  background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 24 24' stroke='%2394a3b8'%3E%3Cpath stroke-linecap='round' stroke-linejoin='round' stroke-width='2' d='M19 9l-7 7-7-7'%3E%3C/path%3E%3C/svg%3E");
  background-repeat: no-repeat;
  background-position: right 1.5rem center;
  background-size: 1rem;
}
</style>
