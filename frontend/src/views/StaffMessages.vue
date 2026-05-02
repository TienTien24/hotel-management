<template>
  <div class="min-h-screen flex bg-[#f8fafc] font-sans">
    <!-- Sidebar -->
    <aside class="w-72 bg-[#004d26] text-white min-h-screen flex flex-col shadow-2xl shrink-0 z-50">
      <div class="p-8 mb-4">
        <div class="flex items-center gap-3">
          <div class="w-12 h-12 bg-white/10 rounded-full flex items-center justify-center font-black text-2xl border border-white/20 text-white">H</div>
          <h1 class="text-2xl font-black uppercase tracking-tight text-white">Grand Hotel</h1>
        </div>
      </div>
      
      <nav class="flex-1 px-4 space-y-1">
        <router-link to="/staff-dashboard" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group hover:bg-white/10">
          <i class="fas fa-th-large text-xl opacity-70 group-hover:opacity-100"></i>
          <span class="font-bold text-lg tracking-tight">Tổng quan</span>
        </router-link>

        <router-link to="/staff-rooms" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group hover:bg-white/10">
          <i class="fas fa-door-open text-xl opacity-70 group-hover:opacity-100"></i>
          <span class="font-bold text-lg tracking-tight">Quản lý Phòng</span>
        </router-link>

        <router-link to="/bookings" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group hover:bg-white/10">
          <i class="fas fa-calendar-alt text-xl opacity-70 group-hover:opacity-100"></i>
          <span class="font-bold text-lg tracking-tight">Quản lý Đặt phòng</span>
        </router-link>

        <router-link to="/manage-services" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group hover:bg-white/10">
          <i class="fas fa-bell text-xl opacity-70 group-hover:opacity-100"></i>
          <span class="font-bold text-lg tracking-tight">Quản lý Dịch vụ</span>
        </router-link>

        <router-link to="/staff-messages" class="flex items-center gap-4 py-4 px-6 rounded-xl transition-all duration-300 group bg-white/10">
          <i class="fas fa-envelope text-xl opacity-100"></i>
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
          <h2 class="text-3xl font-black text-slate-800 uppercase tracking-tight">Phản hồi khách hàng</h2>
        </div>
      </header>

      <!-- Messages List -->
      <div class="bg-white rounded-[2.5rem] shadow-xl overflow-hidden border border-slate-50">
        <div class="p-8 border-b border-slate-50 flex justify-between items-center">
          <div class="flex gap-4">
            <button @click="filterStatus = ''" :class="!filterStatus ? 'bg-[#004d26] text-white' : 'bg-slate-50 text-slate-400'" class="px-6 py-2 rounded-xl text-[10px] font-black uppercase tracking-widest transition-all">Tất cả</button>
            <button @click="filterStatus = 'PENDING'" :class="filterStatus === 'PENDING' ? 'bg-amber-500 text-white' : 'bg-slate-50 text-slate-400'" class="px-6 py-2 rounded-xl text-[10px] font-black uppercase tracking-widest transition-all">Chưa đọc</button>
            <button @click="filterStatus = 'READ'" :class="filterStatus === 'READ' ? 'bg-blue-500 text-white' : 'bg-slate-50 text-slate-400'" class="px-6 py-2 rounded-xl text-[10px] font-black uppercase tracking-widest transition-all">Đã đọc</button>
            <button @click="filterStatus = 'REPLIED'" :class="filterStatus === 'REPLIED' ? 'bg-emerald-500 text-white' : 'bg-slate-50 text-slate-400'" class="px-6 py-2 rounded-xl text-[10px] font-black uppercase tracking-widest transition-all">Đã trả lời</button>
          </div>
          <button @click="fetchMessages" class="w-10 h-10 bg-slate-50 text-slate-400 rounded-xl flex items-center justify-center hover:bg-[#004d26] hover:text-white transition-all shadow-sm">
            <i class="fas fa-sync-alt"></i>
          </button>
        </div>

        <div class="overflow-x-auto">
          <table class="min-w-full">
            <thead>
              <tr class="bg-slate-50/50">
                <th class="px-8 py-4 text-left text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Khách hàng</th>
                <th class="px-8 py-4 text-left text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Nội dung liên hệ</th>
                <th class="px-8 py-4 text-left text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Thời gian</th>
                <th class="px-8 py-4 text-left text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Trạng thái</th>
                <th class="px-8 py-4 text-center text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Thao tác</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-slate-50">
              <tr v-for="msg in filteredMessages" :key="msg.id" class="hover:bg-slate-50/50 transition-colors" :class="{'bg-amber-50/30': msg.status === 'PENDING'}">
                <td class="px-8 py-6">
                  <div class="flex items-center gap-3">
                    <div class="w-10 h-10 rounded-full bg-slate-100 flex items-center justify-center text-slate-400 font-black">
                      {{ msg.fullName.charAt(0).toUpperCase() }}
                    </div>
                    <div>
                      <p class="text-sm font-black text-slate-800 uppercase tracking-tight">{{ msg.fullName }}</p>
                      <p class="text-[10px] font-bold text-slate-400">{{ msg.phone }} | {{ msg.email }}</p>
                    </div>
                  </div>
                </td>
                <td class="px-8 py-6">
                  <p class="text-xs font-medium text-slate-600 line-clamp-2 max-w-md">{{ msg.note }}</p>
                </td>
                <td class="px-8 py-6">
                  <p class="text-[10px] font-bold text-slate-500 uppercase tracking-tighter">{{ formatDateTime(msg.createdAt) }}</p>
                </td>
                <td class="px-8 py-6">
                  <span :class="getStatusClass(msg.status)" class="px-4 py-1.5 rounded-full text-[9px] font-black uppercase tracking-widest">
                    {{ formatStatus(msg.status) }}
                  </span>
                </td>
                <td class="px-8 py-6">
                  <div class="flex justify-center gap-2">
                    <button @click="openReplyModal(msg)" class="w-9 h-9 bg-white shadow-sm border border-slate-100 text-slate-400 rounded-xl flex items-center justify-center hover:bg-[#004d26] hover:text-white transition-all">
                      <i class="fas fa-reply text-[10px]"></i>
                    </button>
                    <button v-if="msg.status === 'PENDING'" @click="markAsRead(msg.id)" class="w-9 h-9 bg-white shadow-sm border border-slate-100 text-slate-400 rounded-xl flex items-center justify-center hover:bg-blue-500 hover:text-white transition-all">
                      <i class="fas fa-check text-[10px]"></i>
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
          
          <div v-if="filteredMessages.length === 0" class="p-32 text-center">
            <div class="w-20 h-20 bg-slate-50 rounded-full flex items-center justify-center mx-auto mb-6">
              <i class="fas fa-envelope-open text-2xl text-slate-200"></i>
            </div>
            <h4 class="text-lg font-black text-slate-300 uppercase tracking-widest">Không có tin nhắn nào</h4>
            <p class="text-sm text-slate-400 mt-2 font-medium">Hệ thống hiện tại chưa nhận được phản hồi mới</p>
          </div>
        </div>
      </div>
    </main>

    <!-- Reply Modal -->
    <div v-if="showReplyModal" class="fixed inset-0 z-[100] flex items-center justify-center p-6 bg-slate-900/60 backdrop-blur-md">
      <div class="bg-white rounded-[3.5rem] max-w-2xl w-full overflow-hidden shadow-2xl animate-in fade-in zoom-in duration-300">
        <div class="bg-[#004d26] p-10 text-white flex justify-between items-center">
          <div>
            <h3 class="text-3xl font-black uppercase tracking-tight">Trả lời khách hàng</h3>
            <p class="text-green-100/60 text-[10px] font-bold uppercase tracking-widest mt-1">Đang phản hồi cho: {{ selectedMessage?.fullName }}</p>
          </div>
          <button @click="showReplyModal = false" class="w-12 h-12 flex items-center justify-center rounded-2xl bg-white/10 text-white hover:bg-rose-500 transition-all">
            <i class="fas fa-times"></i>
          </button>
        </div>
        
        <div class="p-10 space-y-8">
          <div class="bg-slate-50 p-6 rounded-3xl border border-slate-100">
            <p class="text-[10px] font-black text-slate-400 uppercase tracking-widest mb-3">Nội dung khách gửi:</p>
            <p class="text-sm font-medium text-slate-700 italic">"{{ selectedMessage?.note }}"</p>
          </div>

          <div class="space-y-3">
            <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Nội dung phản hồi</label>
            <textarea v-model="replyContent" rows="5" placeholder="Nhập nội dung bạn muốn gửi tới khách hàng..." class="w-full bg-slate-50 border-2 border-slate-100 rounded-[2rem] px-6 py-5 outline-none focus:border-[#004d26] transition-all font-bold text-sm"></textarea>
          </div>

          <button @click="handleReply" class="w-full bg-[#004d26] hover:bg-[#003d1e] text-white py-6 rounded-[2.5rem] font-black uppercase tracking-widest transition-all shadow-xl shadow-green-100 flex items-center justify-center gap-4">
            <i class="fas fa-paper-plane"></i>
            <span>Gửi phản hồi qua Email</span>
          </button>
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
const messages = ref([])
const filterStatus = ref('')
const showReplyModal = ref(false)
const selectedMessage = ref(null)
const replyContent = ref('')

const fetchMessages = async () => {
  try {
    const response = await axios.get('/contact/messages')
    messages.value = response.data.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
  } catch (error) {
    console.error('Lỗi tải tin nhắn:', error)
  }
}

const filteredMessages = computed(() => {
  if (!filterStatus.value) return messages.value
  return messages.value.filter(m => m.status === filterStatus.value)
})

const openReplyModal = (msg) => {
  selectedMessage.value = msg
  replyContent.value = msg.replyContent || ''
  showReplyModal.value = true
}

const handleReply = async () => {
  if (!replyContent.value.trim()) return
  try {
    await axios.post(`/contact/messages/${selectedMessage.value.id}/reply`, {
      replyContent: replyContent.value
    })
    showReplyModal.value = false
    fetchMessages()
    alert('Đã gửi phản hồi thành công!')
  } catch (error) {
    alert('Lỗi khi gửi phản hồi')
  }
}

const markAsRead = async (id) => {
  try {
    await axios.put(`/contact/messages/${id}/status`, null, {
      params: { status: 'READ' }
    })
    fetchMessages()
  } catch (error) {
    console.error('Lỗi cập nhật trạng thái')
  }
}

const formatDateTime = (dateStr) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return date.toLocaleDateString('vi-VN') + ' ' + date.toLocaleTimeString('vi-VN', { hour: '2-digit', minute: '2-digit' })
}

const getStatusClass = (status) => {
  switch (status) {
    case 'PENDING': return 'bg-amber-50 text-amber-600'
    case 'READ': return 'bg-blue-50 text-blue-600'
    case 'REPLIED': return 'bg-emerald-50 text-emerald-600'
    default: return 'bg-slate-50 text-slate-400'
  }
}

const formatStatus = (status) => {
  switch (status) {
    case 'PENDING': return 'Chưa đọc'
    case 'READ': return 'Đã đọc'
    case 'REPLIED': return 'Đã trả lời'
    default: return status
  }
}

const logout = () => {
  localStorage.removeItem('user')
  router.push('/login')
}

onMounted(() => {
  fetchMessages()
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
</style>
