<template>
  <section class="relative z-20 -mt-24 pb-16">
    <div class="max-w-7xl mx-auto px-4">
      <div class="bg-white rounded-[2rem] shadow-2xl shadow-emerald-900/10 p-6 border border-emerald-50">
        <div class="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-6 gap-3">
          <!-- Check-in -->
          <div class="relative group border border-gray-100 rounded-2xl p-3 hover:border-emerald-800 transition-all">
            <label class="block text-[9px] font-black text-gray-400 uppercase tracking-widest mb-1 ml-9">Ngày check-in</label>
            <div class="flex items-center gap-3">
              <i class="fas fa-calendar-alt text-emerald-800 text-lg"></i>
              <input 
                type="date" 
                v-model="searchData.checkIn"
                class="w-full bg-transparent outline-none font-bold text-xs text-slate-700"
              >
            </div>
          </div>

          <!-- Check-out -->
          <div class="relative group border border-gray-100 rounded-2xl p-3 hover:border-emerald-800 transition-all">
            <label class="block text-[9px] font-black text-gray-400 uppercase tracking-widest mb-1 ml-9">Ngày check-out</label>
            <div class="flex items-center gap-3">
              <i class="fas fa-calendar-check text-emerald-800 text-lg"></i>
              <input 
                type="date" 
                v-model="searchData.checkOut"
                class="w-full bg-transparent outline-none font-bold text-xs text-slate-700"
              >
            </div>
          </div>

          <!-- Guests -->
          <div class="relative group border border-gray-100 rounded-2xl p-3 hover:border-emerald-800 transition-all">
            <label class="block text-[9px] font-black text-gray-400 uppercase tracking-widest mb-1 ml-9">Số người</label>
            <div class="flex items-center gap-3">
              <i class="fas fa-user-friends text-emerald-800 text-lg"></i>
              <select 
                v-model="searchData.capacity"
                class="w-full bg-transparent outline-none font-bold text-xs text-slate-700 appearance-none cursor-pointer"
              >
                <option :value="1">1 người lớn</option>
                <option :value="2">2 người lớn, 1 trẻ em</option>
                <option :value="3">3 người lớn</option>
                <option :value="4">4 người lớn</option>
              </select>
              <i class="fas fa-chevron-down text-gray-300 text-[10px] pointer-events-none ml-auto"></i>
            </div>
          </div>

          <!-- Number of Rooms -->
          <div class="relative group border border-gray-100 rounded-2xl p-3 hover:border-emerald-800 transition-all">
            <label class="block text-[9px] font-black text-gray-400 uppercase tracking-widest mb-1 ml-9">Số phòng</label>
            <div class="flex items-center gap-3">
              <i class="fas fa-bed text-emerald-800 text-lg"></i>
              <select 
                v-model="searchData.numRooms"
                class="w-full bg-transparent outline-none font-bold text-xs text-slate-700 appearance-none cursor-pointer"
              >
                <option :value="1">1 phòng</option>
                <option :value="2">2 phòng</option>
                <option :value="3">3 phòng</option>
                <option :value="4">4 phòng</option>
              </select>
              <i class="fas fa-chevron-down text-gray-300 text-[10px] pointer-events-none ml-auto"></i>
            </div>
          </div>

          <!-- Room Type -->
          <div class="relative group border border-gray-100 rounded-2xl p-3 hover:border-emerald-800 transition-all">
            <label class="block text-[9px] font-black text-gray-400 uppercase tracking-widest mb-1 ml-9">Loại phòng</label>
            <div class="flex items-center gap-3">
              <i class="fas fa-hotel text-emerald-800 text-lg"></i>
              <select 
                v-model="searchData.category"
                class="w-full bg-transparent outline-none font-bold text-xs text-slate-700 appearance-none cursor-pointer"
              >
                <option value="All">Tất cả</option>
                <option value="Standard">Standard</option>
                <option value="Deluxe">Deluxe</option>
                <option value="Suite">Suite</option>
              </select>
              <i class="fas fa-chevron-down text-gray-300 text-[10px] pointer-events-none ml-auto"></i>
            </div>
          </div>

          <!-- Search Button -->
          <button 
            @click="handleSearch"
            class="w-full bg-[#004d26] text-white rounded-2xl font-black uppercase tracking-[0.1em] text-[12px] hover:bg-emerald-900 transition-all shadow-xl shadow-green-900/20 flex items-center justify-center gap-3 py-4 md:py-0"
          >
            <i class="fas fa-search"></i>
            Tìm phòng
          </button>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const searchData = reactive({
  checkIn: '',
  checkOut: '',
  capacity: 2,
  numRooms: 1,
  category: 'All'
})

const handleSearch = () => {
  const query = {}
  if (searchData.category !== 'All') {
    query.category = searchData.category
  }
  if (searchData.capacity > 1) {
    query.capacity = searchData.capacity
  }
  if (searchData.checkIn) {
    query.checkIn = searchData.checkIn
  }
  if (searchData.checkOut) {
    query.checkOut = searchData.checkOut
  }
  if (searchData.numRooms > 1) {
    query.numRooms = searchData.numRooms
  }

  router.push({
    path: '/rooms',
    query
  })
}
</script>

<style scoped>
/* Add any component specific styles here if needed */
</style>