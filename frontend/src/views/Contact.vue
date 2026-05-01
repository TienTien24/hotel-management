<template>
  <div class="min-h-screen bg-gray-50">
    <section class="bg-emerald-950 text-white relative overflow-hidden h-[600px] flex items-center justify-center">
      <div class="absolute inset-0 opacity-10">
        <img src="https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80" class="w-full h-full object-cover">
      </div>
      <div class="max-w-7xl mx-auto px-4 relative z-10 text-center">
        <h1 class="text-5xl md:text-7xl font-black uppercase tracking-tighter mb-6">Liên hệ <span class="text-emerald-400">Chúng tôi</span></h1>
        <p class="text-emerald-100 font-light tracking-[0.3em] uppercase text-sm">Chúng tôi luôn sẵn sàng lắng nghe và hỗ trợ bạn</p>
      </div>
    </section>
    <div class="max-w-7xl mx-auto px-4 py-12">

      <div class="grid grid-cols-1 lg:grid-cols-2 gap-12">
        <!-- Contact Info -->
        <div class="space-y-8">
          <div>
            <h2 class="text-2xl font-bold text-gray-800 mb-6">Thông tin liên hệ</h2>
            <p class="text-gray-600 leading-relaxed mb-8">
              Chúng tôi luôn sẵn sàng lắng nghe và hỗ trợ quý khách. Vui lòng liên hệ với chúng tôi qua các thông tin bên dưới hoặc gửi tin nhắn trực tiếp qua form.
            </p>
          </div>

          <div class="space-y-6">
            <div class="flex items-start space-x-4">
              <div class="bg-red-50 p-3 rounded-lg">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-[#E31E24]" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
                </svg>
              </div>
              <div>
                <h3 class="font-bold text-gray-800">Địa chỉ</h3>
                <p class="text-gray-600">170 An Dương Vương, phường Quy Nhơn Nam, tỉnh Gia Lai</p>
              </div>
            </div>

            <div class="flex items-start space-x-4">
              <div class="bg-red-50 p-3 rounded-lg">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-[#E31E24]" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z" />
                </svg>
              </div>
              <div>
                <h3 class="font-bold text-gray-800">Điện thoại</h3>
                <p class="text-gray-600">+84 123 456 789</p>
              </div>
            </div>

            <div class="flex items-start space-x-4">
              <div class="bg-red-50 p-3 rounded-lg">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-[#E31E24]" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
                </svg>
              </div>
              <div>
                <h3 class="font-bold text-gray-800">Email</h3>
                <p class="text-gray-600">contact@grandhotel.com</p>
              </div>
            </div>
          </div>
        </div>

        <!-- Contact Form -->
        <div class="bg-white rounded-2xl shadow-xl p-8 md:p-12">
          <div class="mb-8">
            <p class="text-gray-800 leading-relaxed font-medium">
              Để đóng góp ý kiến về chất lượng dịch vụ, cũng như có những yêu cầu, thắc mắc cần được giải đáp, xin vui lòng điền vào Form sau đây và gửi đến GRAND HOTEL
            </p>
          </div>

          <form @submit.prevent="handleSubmit" class="space-y-6">
            <div class="space-y-2">
              <label class="block text-sm font-bold text-gray-700">Họ và tên</label>
              <input 
                v-model="contactForm.fullName"
                type="text" 
                required
                placeholder="Nhập họ và tên của bạn"
                class="w-full px-4 py-3 border border-gray-200 rounded-lg focus:ring-2 focus:ring-emerald-500 focus:border-emerald-500 outline-none transition-all"
              >
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div class="space-y-2">
                <label class="block text-sm font-bold text-gray-700">Điện thoại</label>
                <input 
                  v-model="contactForm.phone"
                  type="tel" 
                  required
                  placeholder="Số điện thoại liên lạc"
                  class="w-full px-4 py-3 border border-gray-200 rounded-lg focus:ring-2 focus:ring-emerald-500 focus:border-emerald-500 outline-none transition-all"
                >
              </div>
              <div class="space-y-2">
                <label class="block text-sm font-bold text-gray-700">Email</label>
                <input 
                  v-model="contactForm.email"
                  type="email" 
                  required
                  placeholder="Địa chỉ email"
                  class="w-full px-4 py-3 border border-gray-200 rounded-lg focus:ring-2 focus:ring-emerald-500 focus:border-emerald-500 outline-none transition-all"
                >
              </div>
            </div>

            <div class="space-y-2">
              <label class="block text-sm font-bold text-gray-700">Địa chỉ</label>
              <input 
                v-model="contactForm.address"
                type="text" 
                placeholder="Địa chỉ của bạn (không bắt buộc)"
                class="w-full px-4 py-3 border border-gray-200 rounded-lg focus:ring-2 focus:ring-emerald-500 focus:border-emerald-500 outline-none transition-all"
              >
            </div>

            <div class="space-y-2">
              <label class="block text-sm font-bold text-gray-700">Ghi chú</label>
              <textarea 
                v-model="contactForm.note"
                rows="4" 
                placeholder="Nội dung tin nhắn hoặc yêu cầu của bạn..."
                class="w-full px-4 py-3 border border-gray-200 rounded-lg focus:ring-2 focus:ring-emerald-500 focus:border-emerald-500 outline-none transition-all resize-none"
              ></textarea>
            </div>

            <div class="pt-4">
              <button 
                type="submit"
                :disabled="isSubmitting"
                class="w-full md:w-auto bg-[#E31E24] text-white px-12 py-4 rounded-full font-bold uppercase tracking-widest hover:bg-red-700 transition-all shadow-lg disabled:opacity-50 disabled:cursor-not-allowed transform hover:-translate-y-1"
              >
                {{ isSubmitting ? 'ĐANG GỬI...' : 'GỬI ĐI' }}
              </button>
            </div>
          </form>

          <!-- Success Message -->
          <div v-if="submitSuccess" class="mt-6 p-4 bg-emerald-50 text-emerald-800 rounded-xl font-medium text-center animate-fade-in border border-emerald-100">
            Cảm ơn bạn! Thông tin của bạn đã được gửi đến ban quản lý Grand Hotel.
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from '../api/axios'

const contactForm = ref({
  fullName: '',
  phone: '',
  email: '',
  address: '',
  note: ''
})

const isSubmitting = ref(false)
const submitSuccess = ref(false)

const handleSubmit = async () => {
  isSubmitting.value = true
  try {
    // Gửi dữ liệu thực tế đến Backend
    await axios.post('/contact/submit', contactForm.value)
    
    submitSuccess.value = true
    // Reset form
    contactForm.value = {
      fullName: '',
      phone: '',
      email: '',
      address: '',
      note: ''
    }
    
    // Tự động ẩn thông báo sau 5 giây
    setTimeout(() => {
      submitSuccess.value = false
    }, 5000)
  } catch (error) {
    console.error('Lỗi khi gửi form liên hệ:', error)
    alert('Có lỗi xảy ra khi gửi thông tin. Vui lòng thử lại sau.')
  } finally {
    isSubmitting.value = false
  }
}

onMounted(() => {
  window.scrollTo(0, 0)
})
</script>

<style scoped>
.animate-fade-in {
  animation: fadeIn 0.5s ease-out forwards;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>
