<template>
  <div class="min-h-screen flex bg-[#f0f2f5] font-sans text-[#1e293b]">
    <AdminSidebar />

    <main class="flex-1 p-10 overflow-y-auto">
      <header class="mb-8">
        <h2 class="text-3xl font-black tracking-tight text-slate-800">Cài đặt hệ thống</h2>
      </header>

      <div class="bg-white rounded-[2.5rem] shadow-sm border border-slate-100 overflow-hidden">
        <div class="grid grid-cols-1 xl:grid-cols-[280px_1fr] min-h-[620px]">
          <aside class="border-r border-slate-100 bg-slate-50/60 p-6">
            <button
              v-for="tab in tabs"
              :key="tab.value"
              @click="activeTab = tab.value"
              class="w-full text-left flex items-center gap-3 px-4 py-4 rounded-2xl transition-all duration-300 mb-3"
              :class="activeTab === tab.value ? 'bg-blue-50 text-blue-600 shadow-sm' : 'text-slate-500 hover:bg-white'"
            >
              <i :class="tab.icon"></i>
              <span class="font-black text-sm tracking-tight">{{ tab.label }}</span>
            </button>
          </aside>

          <section class="p-8 md:p-10">
            <div v-if="loading" class="text-slate-400 font-bold">Đang tải cài đặt...</div>

            <form v-else @submit.prevent="saveSettings" class="space-y-8">
              <template v-if="activeTab === 'hotel-info'">
                <div>
                  <h3 class="text-xl font-black text-slate-800 mb-1">Thông tin khách sạn</h3>
                </div>

                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                  <div class="space-y-2">
                    <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Tên khách sạn</label>
                    <input v-model="form.hotelName" type="text" class="w-full bg-slate-50 border border-slate-200 rounded-xl px-4 py-3 outline-none focus:ring-2 focus:ring-blue-500">
                  </div>
                  <div class="space-y-2">
                    <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Số điện thoại</label>
                    <input v-model="form.phone" type="text" class="w-full bg-slate-50 border border-slate-200 rounded-xl px-4 py-3 outline-none focus:ring-2 focus:ring-blue-500">
                  </div>
                </div>

                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                  <div class="space-y-2">
                    <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Địa chỉ</label>
                    <input v-model="form.address" type="text" class="w-full bg-slate-50 border border-slate-200 rounded-xl px-4 py-3 outline-none focus:ring-2 focus:ring-blue-500">
                  </div>
                  <div class="space-y-2">
                    <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Email</label>
                    <input v-model="form.email" type="email" class="w-full bg-slate-50 border border-slate-200 rounded-xl px-4 py-3 outline-none focus:ring-2 focus:ring-blue-500">
                  </div>
                </div>

                <div class="grid grid-cols-1 md:grid-cols-[1fr_220px] gap-6 items-start">
                  <div class="space-y-2">
                    <label class="block text-[10px] font-black text-slate-400 uppercase tracking-widest ml-1">Logo khách sạn</label>
                    <input type="file" accept="image/*" @change="handleLogoUpload" class="w-full bg-slate-50 border border-slate-200 rounded-xl px-4 py-3 outline-none">
                    <input v-model="form.logoUrl" type="text" placeholder="Hoặc nhập URL logo..." class="w-full bg-slate-50 border border-slate-200 rounded-xl px-4 py-3 outline-none focus:ring-2 focus:ring-blue-500">
                  </div>
                  <div class="bg-slate-50 border border-slate-200 rounded-2xl p-6 flex items-center justify-center min-h-[170px]">
                    <img v-if="form.logoUrl" :src="form.logoUrl" alt="Logo preview" class="max-h-28 object-contain">
                    <div v-else class="text-center text-slate-400">
                      <i class="fas fa-image text-3xl mb-3"></i>
                      <p class="text-xs font-black uppercase tracking-widest">Chưa có logo</p>
                    </div>
                  </div>
                </div>
              </template>

              <template v-else>
                <div class="rounded-[2rem] bg-slate-50 border border-slate-100 p-8">
                  <h3 class="text-xl font-black text-slate-800 mb-3">{{ currentTabLabel }}</h3>
                </div>
              </template>

              <div class="flex justify-end">
                <button type="submit" :disabled="saving" class="bg-blue-600 hover:bg-blue-700 disabled:opacity-50 text-white px-8 py-3 rounded-xl font-black text-sm transition-all shadow-lg">
                  {{ saving ? 'Đang lưu...' : 'Lưu thay đổi' }}
                </button>
              </div>
            </form>
          </section>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import axios from '../api/axios'
import AdminSidebar from '../components/AdminSidebar.vue'

const tabs = [
  { value: 'hotel-info', label: 'Thông tin khách sạn', icon: 'fas fa-info-circle' },
  { value: 'general', label: 'Cài đặt chung', icon: 'fas fa-cog' },
  { value: 'email', label: 'Email & thông báo', icon: 'fas fa-envelope' },
  { value: 'payment', label: 'Phương thức thanh toán', icon: 'fas fa-credit-card' },
  { value: 'backup', label: 'Sao lưu dữ liệu', icon: 'fas fa-database' }
]

const activeTab = ref('hotel-info')
const loading = ref(false)
const saving = ref(false)
const form = ref({
  hotelName: '',
  address: '',
  phone: '',
  email: '',
  logoUrl: ''
})

const currentTabLabel = computed(() => tabs.find(tab => tab.value === activeTab.value)?.label || '')

const fetchSettings = async () => {
  try {
    loading.value = true
    const response = await axios.get('/admin/settings')
    form.value = {
      hotelName: response.data.hotelName || '',
      address: response.data.address || '',
      phone: response.data.phone || '',
      email: response.data.email || '',
      logoUrl: response.data.logoUrl || ''
    }
  } catch (error) {
    console.error('Lỗi tải cài đặt:', error)
    alert('Không thể tải cài đặt hệ thống.')
  } finally {
    loading.value = false
  }
}

const saveSettings = async () => {
  try {
    saving.value = true
    await axios.put('/admin/settings', form.value)
    alert('Đã lưu cài đặt hệ thống!')
  } catch (error) {
    console.error('Lỗi lưu cài đặt:', error)
    alert('Không thể lưu cài đặt hệ thống.')
  } finally {
    saving.value = false
  }
}

const handleLogoUpload = (event) => {
  const file = event.target.files?.[0]
  if (!file) return

  const reader = new FileReader()
  reader.onload = () => {
    form.value.logoUrl = String(reader.result || '')
  }
  reader.readAsDataURL(file)
}

onMounted(() => {
  fetchSettings()
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
