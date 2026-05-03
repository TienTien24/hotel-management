<template>
  <div class="min-h-screen flex bg-[#f0f2f5] font-sans text-[#1e293b]">
    <AdminSidebar />

    <main class="flex-1 p-10 overflow-y-auto">
      <header class="flex flex-col xl:flex-row xl:items-end xl:justify-between gap-6 mb-8">
        <div>
          <h2 class="text-3xl font-black tracking-tight text-slate-800">Báo cáo doanh thu</h2>
        </div>

        <div class="bg-white rounded-[2rem] shadow-sm border border-slate-100 p-5 flex flex-col md:flex-row gap-4">
          <input v-model="filters.fromDate" type="date" class="bg-slate-50 border-0 rounded-xl px-4 py-3 outline-none font-bold text-sm">
          <input v-model="filters.toDate" type="date" class="bg-slate-50 border-0 rounded-xl px-4 py-3 outline-none font-bold text-sm">
          <button @click="fetchReport" class="bg-[#004d26] hover:bg-[#003d1e] text-white px-6 py-3 rounded-xl font-black uppercase tracking-widest text-xs transition-all">
            Xem báo cáo
          </button>
        </div>
      </header>

      <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
        <div class="bg-white rounded-[2rem] p-6 shadow-sm border border-slate-100">
          <p class="text-[10px] font-black text-slate-400 uppercase tracking-widest mb-2">Tổng doanh thu</p>
          <p class="text-3xl font-black text-slate-800">{{ formatCurrency(report.totalRevenue) }}</p>
        </div>
        <div class="bg-white rounded-[2rem] p-6 shadow-sm border border-slate-100">
          <p class="text-[10px] font-black text-slate-400 uppercase tracking-widest mb-2">Doanh thu phòng</p>
          <p class="text-3xl font-black text-blue-600">{{ formatCurrency(report.totalRoomRevenue) }}</p>
        </div>
        <div class="bg-white rounded-[2rem] p-6 shadow-sm border border-slate-100">
          <p class="text-[10px] font-black text-slate-400 uppercase tracking-widest mb-2">Doanh thu dịch vụ</p>
          <p class="text-3xl font-black text-emerald-600">{{ formatCurrency(report.totalServiceRevenue) }}</p>
        </div>
        <div class="bg-white rounded-[2rem] p-6 shadow-sm border border-slate-100">
          <p class="text-[10px] font-black text-slate-400 uppercase tracking-widest mb-2">Tổng booking</p>
          <p class="text-3xl font-black text-indigo-600">{{ report.totalBookings || 0 }}</p>
        </div>
      </div>

      <div class="grid grid-cols-1 xl:grid-cols-3 gap-8 mb-8">
        <div class="xl:col-span-2 bg-white rounded-[2.5rem] p-8 shadow-sm border border-slate-100">
          <div class="flex items-center justify-between mb-6">
            <div>
              <h3 class="text-xl font-black text-slate-800">Doanh thu theo ngày</h3>
              <p class="text-[10px] text-slate-400 font-bold uppercase tracking-widest mt-1">Dựa trên hóa đơn đã thanh toán</p>
            </div>
          </div>
          <div class="h-[340px]">
            <Line v-if="dailyRevenueChartData" :data="dailyRevenueChartData" :options="lineOptions" />
            <div v-else class="h-full flex items-center justify-center text-slate-400 font-bold">Chưa có dữ liệu doanh thu trong khoảng thời gian này</div>
          </div>
        </div>

        <div class="bg-white rounded-[2.5rem] p-8 shadow-sm border border-slate-100">
          <h3 class="text-xl font-black text-slate-800 text-center mb-6">Doanh thu theo loại phòng</h3>
          <div class="h-[300px] flex items-center justify-center">
            <Doughnut v-if="roomRevenueChartData" :data="roomRevenueChartData" :options="doughnutOptions" />
            <div v-else class="text-slate-400 font-bold text-center">Chưa có dữ liệu loại phòng</div>
          </div>
        </div>
      </div>

      <div class="grid grid-cols-1 xl:grid-cols-2 gap-8">
        <div class="bg-white rounded-[2.5rem] p-8 shadow-sm border border-slate-100">
          <h3 class="text-xl font-black text-slate-800 mb-6">Doanh thu theo dịch vụ</h3>
          <div v-if="serviceRows.length" class="space-y-4">
            <div v-for="service in serviceRows" :key="service.name" class="flex items-center justify-between bg-slate-50 rounded-2xl px-5 py-4">
              <div>
                <p class="font-black text-slate-800">{{ service.name }}</p>
                <p class="text-[10px] font-bold uppercase tracking-widest text-slate-400">Dịch vụ hoàn tất trong kỳ</p>
              </div>
              <p class="text-sm font-black text-emerald-600">{{ formatCurrency(service.revenue) }}</p>
            </div>
          </div>
          <div v-else class="text-slate-400 font-bold">Chưa có dữ liệu doanh thu dịch vụ.</div>
        </div>

        <div class="bg-white rounded-[2.5rem] p-8 shadow-sm border border-slate-100">
          <h3 class="text-xl font-black text-slate-800 mb-6">Chi tiết theo ngày</h3>
          <div v-if="report.details?.length" class="overflow-x-auto">
            <table class="w-full">
              <thead>
                <tr class="border-b border-slate-100">
                  <th class="py-3 text-left text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Ngày</th>
                  <th class="py-3 text-left text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Booking</th>
                  <th class="py-3 text-right text-[10px] font-black text-slate-400 uppercase tracking-[0.2em]">Doanh thu</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="detail in report.details" :key="detail.date" class="border-b border-slate-50">
                  <td class="py-4 text-sm font-bold text-slate-700">{{ formatDate(detail.date) }}</td>
                  <td class="py-4 text-sm text-slate-500">{{ detail.bookings }}</td>
                  <td class="py-4 text-sm font-black text-slate-800 text-right">{{ formatCurrency(detail.revenue) }}</td>
                </tr>
              </tbody>
            </table>
          </div>
          <div v-else class="text-slate-400 font-bold">Chưa có dữ liệu chi tiết.</div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from '../api/axios'
import AdminSidebar from '../components/AdminSidebar.vue'
import { Line, Doughnut } from 'vue-chartjs'
import { ArcElement, CategoryScale, Chart as ChartJS, Legend, LineElement, LinearScale, PointElement, Title, Tooltip } from 'chart.js'

ChartJS.register(Title, Tooltip, Legend, LineElement, PointElement, LinearScale, CategoryScale, ArcElement)

const router = useRouter()
const today = new Date().toISOString().split('T')[0]
const thirtyDaysAgo = new Date(new Date().setDate(new Date().getDate() - 30)).toISOString().split('T')[0]

const filters = ref({
  fromDate: thirtyDaysAgo,
  toDate: today
})

const report = ref({
  totalRevenue: 0,
  totalRoomRevenue: 0,
  totalServiceRevenue: 0,
  totalBookings: 0,
  details: [],
  roomTypeRevenue: {},
  serviceRevenue: {}
})

const fetchReport = async () => {
  try {
    const response = await axios.get('/dashboard/reports/revenue', {
      params: {
        fromDate: filters.value.fromDate,
        toDate: filters.value.toDate
      }
    })
    report.value = response.data
  } catch (error) {
    console.error('Lỗi tải báo cáo:', error)
    alert('Không thể tải báo cáo doanh thu.')
  }
}

const serviceRows = computed(() => Object.entries(report.value.serviceRevenue || {}).map(([name, revenue]) => ({ name, revenue })))

const dailyRevenueChartData = computed(() => {
  if (!report.value.details?.length) return null

  return {
    labels: report.value.details.map(item => formatDate(item.date)),
    datasets: [
      {
        label: 'Doanh thu',
        data: report.value.details.map(item => item.revenue * 25000),
        borderColor: '#3b82f6',
        backgroundColor: 'rgba(59, 130, 246, 0.12)',
        fill: true,
        tension: 0.35
      }
    ]
  }
})

const roomRevenueChartData = computed(() => {
  const entries = Object.entries(report.value.roomTypeRevenue || {})
  if (!entries.length) return null

  return {
    labels: entries.map(([label]) => label),
    datasets: [
      {
        data: entries.map(([, value]) => value * 25000),
        backgroundColor: ['#3b82f6', '#10b981', '#8b5cf6', '#f59e0b', '#ef4444'],
        borderWidth: 0
      }
    ]
  }
})

const lineOptions = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: { display: false }
  },
  scales: {
    y: {
      beginAtZero: true,
      ticks: {
        callback: (value) => `${new Intl.NumberFormat('vi-VN').format(value)} đ`
      }
    }
  }
}

const doughnutOptions = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: {
      position: 'bottom'
    }
  },
  cutout: '68%'
}

const formatCurrency = (amount) => {
  if (!amount) return '0 đ'
  return `${new Intl.NumberFormat('vi-VN').format(amount * 25000)} đ`
}

const formatDate = (value) => {
  if (!value) return '--'
  return new Date(value).toLocaleDateString('vi-VN')
}

const logout = () => {
  localStorage.removeItem('user')
  router.push('/login')
}

onMounted(() => {
  fetchReport()
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
