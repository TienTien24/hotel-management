<template>
  <div :class="{'bg-white rounded-[2rem] shadow-lg overflow-hidden border border-gray-100': !hideHeader}">
    <div v-if="!hideHeader" class="bg-gradient-to-r from-emerald-800 to-emerald-900 text-white p-6 flex justify-between items-center">
      <div>
        <h3 class="text-2xl font-black uppercase tracking-tight">Sơ đồ phòng trực quan</h3>
        <p class="text-emerald-100 text-sm mt-1">Theo dõi tình trạng phòng thực tế</p>
      </div>
      <div class="flex gap-4">
        <div class="flex items-center gap-2">
          <span class="w-3 h-3 rounded-full bg-green-500"></span>
          <span class="text-xs font-bold uppercase tracking-widest text-emerald-100">Trống</span>
        </div>
        <div class="flex items-center gap-2">
          <span class="w-3 h-3 rounded-full bg-red-500"></span>
          <span class="text-xs font-bold uppercase tracking-widest text-emerald-100">Có khách</span>
        </div>
        <div class="flex items-center gap-2">
          <span class="w-3 h-3 rounded-full bg-yellow-400"></span>
          <span class="text-xs font-bold uppercase tracking-widest text-emerald-100">Đang dọn</span>
        </div>
        <div class="flex items-center gap-2">
          <span class="w-3 h-3 rounded-full bg-gray-400"></span>
          <span class="text-xs font-bold uppercase tracking-widest text-emerald-100">Bảo trì</span>
        </div>
      </div>
    </div>

    <div :class="{'p-8': !hideHeader}">
      <div v-if="loading" class="text-center py-12">
        <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-emerald-800 mx-auto"></div>
        <p class="mt-4 text-gray-500 font-bold uppercase tracking-widest text-xs">Đang tải sơ đồ phòng...</p>
      </div>

      <div v-else class="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-6 gap-6">
        <div 
          v-for="room in rooms" 
          :key="room.id"
          class="relative group cursor-pointer"
          @click="selectRoom(room)"
        >
          <div 
            :class="[
              'h-40 rounded-3xl flex flex-col items-center justify-between p-6 transition-all duration-300 transform group-hover:-translate-y-1 group-hover:shadow-xl border-2',
              getRoomBgClass(room.status)
            ]"
          >
            <div class="text-center">
              <span class="text-3xl font-black block leading-none mb-1">{{ room.roomNumber }}</span>
              <span class="text-[11px] font-black uppercase tracking-widest opacity-60">{{ room.category }}</span>
            </div>
            
            <div class="flex items-center gap-2 mt-4 pt-4 border-t border-black/5 w-full justify-center">
              <i :class="getRoomIcon(room.status)" class="text-xs"></i>
              <span class="text-[10px] font-black uppercase tracking-widest">{{ getStatusLabel(room.status) }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Room Status Update Modal -->
    <div v-if="selectedRoom" class="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/60 backdrop-blur-sm">
      <div class="bg-white rounded-[2.5rem] max-w-md w-full p-8 shadow-2xl transform transition-all animate-in fade-in zoom-in duration-300">
        <div class="flex justify-between items-start mb-6">
          <div>
            <h3 class="text-3xl font-black text-emerald-950 uppercase tracking-tighter">Phòng {{ selectedRoom.roomNumber }}</h3>
            <p class="text-sm text-gray-500 font-bold mt-1">{{ selectedRoom.category }} - {{ selectedRoom.type }}</p>
          </div>
          <button @click="selectedRoom = null" class="text-gray-400 hover:text-gray-600 transition-colors">
            <i class="fas fa-times text-xl"></i>
          </button>
        </div>

        <div class="space-y-4 mb-8">
          <div class="bg-gray-50 rounded-2xl p-4 flex items-center justify-between">
            <span class="text-xs font-black uppercase tracking-widest text-gray-400">Trạng thái hiện tại</span>
            <span :class="getStatusTextClass(selectedRoom.status)" class="font-black uppercase tracking-widest text-sm">
              {{ getStatusLabel(selectedRoom.status) }}
            </span>
          </div>
        </div>

        <div class="grid grid-cols-1 gap-3">
          <button 
            v-if="selectedRoom.status === 'CLEANING'"
            @click="updateStatus('AVAILABLE')"
            class="w-full bg-green-500 hover:bg-green-600 text-white py-4 rounded-2xl font-black uppercase tracking-widest text-xs transition-all shadow-lg shadow-green-200"
          >
            Đã dọn xong (Sẵn sàng)
          </button>
          <button 
            v-if="selectedRoom.status === 'AVAILABLE'"
            @click="updateStatus('CLEANING')"
            class="w-full bg-yellow-400 hover:bg-yellow-500 text-white py-4 rounded-2xl font-black uppercase tracking-widest text-xs transition-all shadow-lg shadow-yellow-200"
          >
            Đang dọn dẹp
          </button>
          <button 
            v-if="selectedRoom.status !== 'MAINTENANCE' && selectedRoom.status !== 'OCCUPIED'"
            @click="updateStatus('MAINTENANCE')"
            class="w-full bg-gray-400 hover:bg-gray-500 text-white py-4 rounded-2xl font-black uppercase tracking-widest text-xs transition-all shadow-lg shadow-gray-200"
          >
            Đưa vào bảo trì
          </button>
          <button 
            v-if="selectedRoom.status === 'MAINTENANCE'"
            @click="updateStatus('AVAILABLE')"
            class="w-full bg-green-500 hover:bg-green-600 text-white py-4 rounded-2xl font-black uppercase tracking-widest text-xs transition-all shadow-lg shadow-green-200"
          >
            Kết thúc bảo trì
          </button>
        </div>
        
        <p v-if="selectedRoom.status === 'OCCUPIED'" class="text-center text-xs text-rose-500 font-bold uppercase tracking-widest mt-4">
          Phòng đang có khách, vui lòng check-out trước khi đổi trạng thái.
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import axios from '../api/axios';

export default {
  props: {
    hideHeader: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      rooms: [],
      loading: true,
      selectedRoom: null
    };
  },
  mounted() {
    this.fetchRooms();
  },
  methods: {
    async fetchRooms() {
      try {
        this.loading = true;
        const response = await axios.get('/rooms');
        this.rooms = response.data.sort((a, b) => a.roomNumber.localeCompare(b.roomNumber));
      } catch (error) {
        console.error('Error fetching rooms:', error);
      } finally {
        this.loading = false;
      }
    },
    selectRoom(room) {
      this.selectedRoom = { ...room };
    },
    async updateStatus(newStatus) {
      try {
        await axios.put(`/rooms/${this.selectedRoom.id}/status?status=${newStatus}`);
        this.selectedRoom = null;
        this.fetchRooms();
        this.$emit('refresh-stats');
      } catch (error) {
        alert(error.response?.data?.message || 'Không thể cập nhật trạng thái');
      }
    },
    getRoomBgClass(status) {
      switch (status) {
        case 'AVAILABLE': return 'bg-[#f0fdf4] border-[#bbf7d0] text-[#15803d]';
        case 'OCCUPIED': return 'bg-[#fff1f2] border-[#fecdd3] text-[#be123c]';
        case 'CLEANING': return 'bg-[#fefce8] border-[#fef08a] text-[#a16207]';
        case 'MAINTENANCE': return 'bg-[#f8fafc] border-[#e2e8f0] text-[#64748b]';
        case 'BOOKED': return 'bg-[#eff6ff] border-[#bfdbfe] text-[#1d4ed8]';
        default: return 'bg-white border-gray-100 text-gray-900';
      }
    },
    getStatusLabel(status) {
      switch (status) {
        case 'AVAILABLE': return 'Trống';
        case 'OCCUPIED': return 'Có khách';
        case 'CLEANING': return 'Đang dọn';
        case 'MAINTENANCE': return 'Bảo trì';
        case 'BOOKED': return 'Đã đặt';
        default: return status;
      }
    },
    getRoomIcon(status) {
      switch (status) {
        case 'AVAILABLE': return 'fas fa-check-circle';
        case 'OCCUPIED': return 'fas fa-user-check';
        case 'CLEANING': return 'fas fa-broom';
        case 'MAINTENANCE': return 'fas fa-tools';
        case 'BOOKED': return 'fas fa-calendar-check';
        default: return '';
      }
    },
    getStatusTextClass(status) {
      switch (status) {
        case 'AVAILABLE': return 'text-green-600';
        case 'OCCUPIED': return 'text-red-600';
        case 'CLEANING': return 'text-yellow-600';
        case 'MAINTENANCE': return 'text-gray-600';
        case 'BOOKED': return 'text-blue-600';
        default: return '';
      }
    }
  }
};
</script>
