<template>
  <div class="min-h-screen flex bg-[#f0f2f5] font-sans text-[#1e293b]">
    <AdminSidebar />

    <!-- Main Content -->
    <main class="flex-1 p-10 overflow-y-auto">
      <!-- Header -->
      <header class="flex justify-between items-center mb-10">
        <div>
          <h2 class="text-3xl font-black tracking-tight text-slate-800">Tổng quan hệ thống</h2>
        </div>
        <div class="flex items-center gap-6">
          <div class="flex items-center gap-3 bg-white px-6 py-3 rounded-2xl shadow-sm border border-slate-100">
            <i class="fas fa-calendar-alt text-blue-500"></i>
            <span class="font-bold text-sm">{{ currentDateRange }}</span>
          </div>
        </div>
      </header>

      <!-- Stats Grid -->
      <div class="grid grid-cols-1 md:grid-cols-4 gap-8 mb-10">
        <!-- Doanh thu Card -->
        <div class="bg-white rounded-[2rem] p-8 shadow-sm border border-slate-100 group hover:shadow-xl hover:shadow-blue-500/5 transition-all duration-500">
          <div class="flex items-center justify-between mb-6">
            <div class="w-14 h-14 bg-blue-50 text-blue-500 rounded-2xl flex items-center justify-center text-xl">
              <i class="fas fa-wallet"></i>
            </div>
            <div class="text-right">
              <span class="text-[10px] font-black text-emerald-500 bg-emerald-50 px-3 py-1 rounded-full uppercase tracking-widest">+12.5%</span>
            </div>
          </div>
          <h3 class="text-slate-400 font-black uppercase tracking-widest text-[10px] mb-2">Tổng doanh thu</h3>
          <p class="text-3xl font-black text-slate-800 tracking-tight">{{ formatCurrency(stats.totalRevenue) }}</p>
          <p class="text-[10px] text-slate-400 font-bold mt-2 uppercase tracking-widest">Tất cả thời gian</p>
        </div>

        <!-- Đặt phòng Card -->
        <div class="bg-white rounded-[2rem] p-8 shadow-sm border border-slate-100 group hover:shadow-xl hover:shadow-emerald-500/5 transition-all duration-500">
          <div class="flex items-center justify-between mb-6">
            <div class="w-14 h-14 bg-emerald-50 text-emerald-500 rounded-2xl flex items-center justify-center text-xl">
              <i class="fas fa-calendar-check"></i>
            </div>
            <div class="text-right">
              <span class="text-[10px] font-black text-emerald-500 bg-emerald-50 px-3 py-1 rounded-full uppercase tracking-widest">+8.3%</span>
            </div>
          </div>
          <h3 class="text-slate-400 font-black uppercase tracking-widest text-[10px] mb-2">Tổng đặt phòng</h3>
          <p class="text-3xl font-black text-slate-800 tracking-tight">{{ stats.newBookings || 0 }}</p>
          <p class="text-[10px] text-slate-400 font-bold mt-2 uppercase tracking-widest">Tổng số đơn hàng</p>
        </div>

        <!-- Tỷ lệ lấp đầy Card -->
        <div class="bg-white rounded-[2rem] p-8 shadow-sm border border-slate-100 group hover:shadow-xl hover:shadow-indigo-500/5 transition-all duration-500">
          <div class="flex items-center justify-between mb-6">
            <div class="w-14 h-14 bg-indigo-50 text-indigo-500 rounded-2xl flex items-center justify-center text-xl">
              <i class="fas fa-chart-line"></i>
            </div>
            <div class="text-right">
              <span class="text-[10px] font-black text-emerald-500 bg-emerald-50 px-3 py-1 rounded-full uppercase tracking-widest">+5.6%</span>
            </div>
          </div>
          <h3 class="text-slate-400 font-black uppercase tracking-widest text-[10px] mb-2">Tỷ lệ lấp đầy</h3>
          <p class="text-3xl font-black text-slate-800 tracking-tight">{{ stats.occupancyRate?.toFixed(1) || '0.0' }}%</p>
          <p class="text-[10px] text-slate-400 font-bold mt-2 uppercase tracking-widest">Hiệu suất sử dụng phòng</p>
        </div>

        <!-- Phòng đang ở Card -->
        <div class="bg-white rounded-[2rem] p-8 shadow-sm border border-slate-100 group hover:shadow-xl hover:shadow-orange-500/5 transition-all duration-500">
          <div class="flex items-center justify-between mb-6">
            <div class="w-14 h-14 bg-orange-50 text-orange-500 rounded-2xl flex items-center justify-center text-xl">
              <i class="fas fa-door-open"></i>
            </div>
            <div class="text-right">
              <span class="text-[10px] font-black text-emerald-500 bg-emerald-50 px-3 py-1 rounded-full uppercase tracking-widest">+10.2%</span>
            </div>
          </div>
          <h3 class="text-slate-400 font-black uppercase tracking-widest text-[10px] mb-2">Phòng đang ở</h3>
          <p class="text-3xl font-black text-slate-800 tracking-tight">{{ stats.occupiedRooms || 0 }}</p>
          <p class="text-[10px] text-slate-400 font-bold mt-2 uppercase tracking-widest">Khách đang lưu trú</p>
        </div>
      </div>

      <!-- Charts Row -->
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-8 mb-10">
        <!-- Revenue Chart -->
        <div class="lg:col-span-2 bg-white rounded-[2.5rem] p-10 shadow-sm border border-slate-100">
          <div class="flex justify-between items-center mb-8">
            <div>
              <h3 class="text-xl font-black text-slate-800 uppercase tracking-tight">Doanh thu theo ngày</h3>
              <p class="text-[10px] text-slate-400 font-bold uppercase tracking-widest mt-1">Thống kê 7 ngày gần nhất</p>
            </div>
            <select class="bg-slate-50 border-0 rounded-xl px-4 py-2 text-[10px] font-black uppercase tracking-widest outline-none">
              <option>7 ngày qua</option>
              <option>30 ngày qua</option>
            </select>
          </div>
          <div class="h-[350px]">
            <Line v-if="revenueChartData" :data="revenueChartData" :options="revenueChartOptions" />
          </div>
        </div>

        <!-- Room Status Pie Chart -->
        <div class="bg-white rounded-[2.5rem] p-10 shadow-sm border border-slate-100">
          <h3 class="text-xl font-black text-slate-800 uppercase tracking-tight mb-8 text-center">Tình trạng phòng</h3>
          <div class="h-[300px] flex items-center justify-center relative">
            <Pie v-if="roomChartData" :data="roomChartData" :options="roomChartOptions" />
          </div>
          <div class="mt-8 space-y-4">
            <div v-for="(val, key) in roomStatsLabels" :key="key" class="flex items-center justify-between px-4 py-3 bg-slate-50 rounded-2xl">
              <div class="flex items-center gap-3">
                <span class="w-3 h-3 rounded-full" :style="{ backgroundColor: roomChartData.datasets[0].backgroundColor[key] }"></span>
                <span class="text-[11px] font-black uppercase tracking-widest text-slate-500">{{ val }}</span>
              </div>
              <span class="text-sm font-black text-slate-800">{{ stats[key] || 0 }} phòng</span>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import axios from '../api/axios'
import AdminSidebar from '../components/AdminSidebar.vue'
import { Line, Pie } from 'vue-chartjs'
import { Chart as ChartJS, Title, Tooltip, Legend, LineElement, PointElement, LinearScale, CategoryScale, ArcElement } from 'chart.js'

ChartJS.register(Title, Tooltip, Legend, LineElement, PointElement, LinearScale, CategoryScale, ArcElement)

const router = useRouter()
const user = ref(JSON.parse(localStorage.getItem('user') || 'null'))
const stats = ref({})
const revenueDetails = ref([])
const revenueReport = ref(null)

const currentDateRange = computed(() => {
  const now = new Date()
  const sevenDaysAgo = new Date()
  sevenDaysAgo.setDate(now.getDate() - 7)
  return `${sevenDaysAgo.toLocaleDateString('vi-VN')} - ${now.toLocaleDateString('vi-VN')}`
})

const fetchStats = async () => {
  try {
    const res = await axios.get('/dashboard/stats')
    stats.value = res.data
  } catch (error) {
    console.error('Error fetching admin stats:', error)
  }
}

const fetchRevenueReport = async () => {
  try {
    const today = new Date().toISOString().split('T')[0]
    const sevenDaysAgo = new Date()
    sevenDaysAgo.setDate(sevenDaysAgo.getDate() - 7)
    const fromDate = sevenDaysAgo.toISOString().split('T')[0]
    
    const res = await axios.get('/dashboard/reports/revenue', {
      params: { fromDate, toDate: today }
    })
    revenueReport.value = res.data
    revenueDetails.value = res.data.details
  } catch (error) {
    console.error('Error fetching revenue report:', error)
  }
}

// Chart Configurations
const revenueChartData = computed(() => {
  if (!revenueDetails.value.length) return null
  return {
    labels: revenueDetails.value.map(d => new Date(d.date).toLocaleDateString('vi-VN', { day: '2-digit', month: '2-digit' })),
    datasets: [{
      label: 'Doanh thu (VND)',
      data: revenueDetails.value.map(d => d.revenue * 25000),
      borderColor: '#3b82f6',
      backgroundColor: 'rgba(59, 130, 246, 0.1)',
      fill: true,
      tension: 0.4,
      pointRadius: 6,
      pointBackgroundColor: '#fff',
      pointBorderColor: '#3b82f6',
      pointBorderWidth: 2
    }]
  }
})

const revenueChartOptions = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: { display: false },
    tooltip: {
      backgroundColor: '#1e293b',
      titleFont: { size: 12, weight: 'bold' },
      bodyFont: { size: 12 },
      padding: 12,
      cornerRadius: 12,
      displayColors: false
    }
  },
  scales: {
    y: {
      beginAtZero: true,
      grid: { color: 'rgba(0,0,0,0.05)', drawBorder: false },
      ticks: {
        font: { size: 10, weight: 'bold' },
        callback: (value) => value.toLocaleString('vi-VN') + ' đ'
      }
    },
    x: {
      grid: { display: false },
      ticks: { font: { size: 10, weight: 'bold' } }
    }
  }
}

const roomStatsLabels = {
  occupiedRooms: 'Đang ở',
  bookedRooms: 'Đã đặt',
  availableRooms: 'Phòng trống',
  maintenanceRooms: 'Bảo trì'
}

const roomChartData = computed(() => {
  return {
    labels: Object.values(roomStatsLabels),
    datasets: [{
      data: [
        stats.value.occupiedRooms || 0,
        stats.value.bookedRooms || 0,
        stats.value.availableRooms || 0,
        stats.value.maintenanceRooms || 0
      ],
      backgroundColor: ['#3b82f6', '#10b981', '#f59e0b', '#ef4444'],
      borderWidth: 0,
      hoverOffset: 20
    }]
  }
})

const roomChartOptions = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: { display: false },
    tooltip: {
      backgroundColor: '#1e293b',
      padding: 12,
      cornerRadius: 12,
      titleFont: { size: 12, weight: 'bold' },
      bodyFont: { size: 12 }
    }
  },
  cutout: '70%'
}

const formatCurrency = (amount) => {
  if (!amount) return '0 đ'
  return new Intl.NumberFormat('vi-VN').format(amount * 25000) + ' đ'
}

const logout = () => {
  localStorage.removeItem('user')
  router.push('/login')
}

onMounted(() => {
  fetchStats()
  fetchRevenueReport()
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
