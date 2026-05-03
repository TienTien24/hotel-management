<template>
  <main class="contact-page min-h-screen bg-white font-sans">
    <!-- Hero Section -->
    <header class="relative h-[500px] flex items-center justify-center overflow-hidden">
      <div class="absolute inset-0 z-0">
        <img src="https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80" 
             alt="Contact Hero" 
             class="w-full h-full object-cover">
        <div class="absolute inset-0 bg-black/60"></div>
      </div>
      <div class="max-w-7xl mx-auto px-6 relative z-10 text-center">
        <p class="text-amber-400 font-bold uppercase tracking-[0.3em] text-xs mb-4 italic">Kết nối với chúng tôi</p>
        <h1 class="text-7xl md:text-9xl font-black text-white uppercase tracking-tighter mb-4 leading-none">
          LIÊN HỆ <span class="text-emerald-400">CHÚNG TÔI</span>
        </h1>
        <div class="w-24 h-1 bg-amber-400 mx-auto mb-8"></div>
        <p class="text-lg text-white/80 font-medium tracking-tight uppercase tracking-[0.1em]">
          Chúng tôi luôn sẵn sàng lắng nghe và hỗ trợ bạn
        </p>
      </div>
    </header>

    <div class="max-w-7xl mx-auto px-6 py-32">
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-20">
        <!-- Contact Info Column -->
        <div class="space-y-12">
          <div class="flex items-center gap-4 mb-8">
            <div class="w-12 h-12 bg-emerald-50 rounded-2xl flex items-center justify-center text-emerald-800 text-xl shadow-inner">
              <i class="fas fa-id-card"></i>
            </div>
            <h2 class="text-3xl font-black text-slate-800 uppercase tracking-tighter">Thông tin liên hệ</h2>
          </div>
          
          <p class="text-sm text-gray-500 font-medium leading-relaxed max-w-md">
            Chúng tôi luôn sẵn sàng lắng nghe và hỗ trợ quý khách. Vui lòng liên hệ với chúng tôi qua các thông tin bên dưới hoặc gửi tin nhắn trực tiếp qua form.
          </p>

          <div class="space-y-6">
            <!-- Address -->
            <div class="bg-gray-50 rounded-[2rem] p-8 border border-gray-100 flex items-center gap-8 group hover:bg-white hover:shadow-2xl hover:shadow-emerald-900/5 transition-all duration-500">
              <div class="w-16 h-16 bg-white rounded-2xl flex items-center justify-center text-emerald-800 text-2xl shadow-sm border border-gray-100 shrink-0 group-hover:bg-[#004d26] group-hover:text-white transition-all">
                <i class="fas fa-map-marker-alt"></i>
              </div>
              <div>
                <p class="text-[10px] font-black text-gray-400 uppercase tracking-widest mb-1">Địa chỉ</p>
                <p class="text-sm font-black text-slate-800">170 An Dương Vương, phường Quy Nhơn Nam, tỉnh Gia Lai</p>
              </div>
            </div>

            <!-- Phone -->
            <div class="bg-gray-50 rounded-[2rem] p-8 border border-gray-100 flex items-center gap-8 group hover:bg-white hover:shadow-2xl hover:shadow-emerald-900/5 transition-all duration-500">
              <div class="w-16 h-16 bg-white rounded-2xl flex items-center justify-center text-emerald-800 text-2xl shadow-sm border border-gray-100 shrink-0 group-hover:bg-[#004d26] group-hover:text-white transition-all">
                <i class="fas fa-phone-alt"></i>
              </div>
              <div>
                <p class="text-[10px] font-black text-gray-400 uppercase tracking-widest mb-1">Điện thoại</p>
                <p class="text-sm font-black text-slate-800">+84 123 456 789</p>
              </div>
            </div>

            <!-- Email -->
            <div class="bg-gray-50 rounded-[2rem] p-8 border border-gray-100 flex items-center gap-8 group hover:bg-white hover:shadow-2xl hover:shadow-emerald-900/5 transition-all duration-500">
              <div class="w-16 h-16 bg-white rounded-2xl flex items-center justify-center text-emerald-800 text-2xl shadow-sm border border-gray-100 shrink-0 group-hover:bg-[#004d26] group-hover:text-white transition-all">
                <i class="fas fa-envelope"></i>
              </div>
              <div>
                <p class="text-[10px] font-black text-gray-400 uppercase tracking-widest mb-1">Email</p>
                <p class="text-sm font-black text-slate-800">contact@grandhotel.com</p>
              </div>
            </div>
          </div>

          <!-- 24/7 Support -->
          <div class="bg-emerald-50 rounded-[2rem] p-8 border border-emerald-100 flex items-center gap-8">
            <div class="relative">
              <div class="w-16 h-16 bg-emerald-600 rounded-full flex items-center justify-center text-white text-2xl shadow-lg">
                <i class="fas fa-headset"></i>
              </div>
              <div class="absolute -right-2 -bottom-2 w-8 h-8 bg-white rounded-full flex items-center justify-center text-emerald-600 text-[10px] font-black border-2 border-emerald-600">24/7</div>
            </div>
            <div>
              <h4 class="text-lg font-black text-emerald-900 uppercase tracking-tight">Hỗ trợ 24/7</h4>
              <p class="text-xs text-emerald-700/70 font-bold uppercase tracking-widest mt-1">Đội ngũ chăm sóc khách hàng luôn sẵn sàng hỗ trợ bạn mọi lúc.</p>
            </div>
          </div>
        </div>

        <!-- Contact Form Column -->
        <div class="bg-white rounded-[3rem] shadow-2xl shadow-emerald-900/10 p-10 md:p-16 border border-emerald-50 relative">
          <div class="flex items-center gap-6 mb-12 pb-8 border-b border-gray-50">
            <div class="w-14 h-14 bg-[#004d26] text-white rounded-2xl flex items-center justify-center text-xl shadow-xl shadow-green-900/20">
              <i class="fas fa-paper-plane"></i>
            </div>
            <div>
              <h3 class="text-2xl font-black text-slate-800 uppercase tracking-tighter">Gửi yêu cầu cho chúng tôi</h3>
              <p class="text-xs text-gray-400 font-bold mt-1 uppercase tracking-widest leading-relaxed">Để đóng góp ý kiến về chất lượng dịch vụ, vui lòng điền vào Form sau đây và gửi đến <span class="text-[#004d26]">GRAND HOTEL</span></p>
            </div>
          </div>

          <form @submit.prevent="handleSubmit" class="space-y-8">
            <!-- Full Name -->
            <div class="space-y-3">
              <label class="text-[10px] font-black text-slate-400 uppercase tracking-widest flex items-center gap-2">Họ và tên <span class="text-rose-500">*</span></label>
              <div class="relative group">
                <i class="fas fa-user absolute left-6 top-1/2 -translate-y-1/2 text-gray-300 group-focus-within:text-[#004d26] transition-colors"></i>
                <input v-model="contactForm.fullName" type="text" required placeholder="Nhập họ và tên của bạn" class="w-full bg-gray-50 border-2 border-gray-50 rounded-2xl pl-16 pr-6 py-5 outline-none focus:bg-white focus:border-[#004d26] transition-all font-bold text-sm text-slate-700">
              </div>
            </div>

            <!-- Phone & Email -->
            <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
              <div class="space-y-3">
                <label class="text-[10px] font-black text-slate-400 uppercase tracking-widest flex items-center gap-2">Điện thoại <span class="text-rose-500">*</span></label>
                <div class="relative group">
                  <i class="fas fa-phone-alt absolute left-6 top-1/2 -translate-y-1/2 text-gray-300 group-focus-within:text-[#004d26] transition-colors"></i>
                  <input v-model="contactForm.phone" type="tel" required placeholder="Số điện thoại liên lạc" class="w-full bg-gray-50 border-2 border-gray-50 rounded-2xl pl-16 pr-6 py-5 outline-none focus:bg-white focus:border-[#004d26] transition-all font-bold text-sm text-slate-700">
                </div>
              </div>
              <div class="space-y-3">
                <label class="text-[10px] font-black text-slate-400 uppercase tracking-widest flex items-center gap-2">Email <span class="text-rose-500">*</span></label>
                <div class="relative group">
                  <i class="fas fa-envelope absolute left-6 top-1/2 -translate-y-1/2 text-gray-300 group-focus-within:text-[#004d26] transition-colors"></i>
                  <input v-model="contactForm.email" type="email" required placeholder="Địa chỉ email" class="w-full bg-gray-50 border-2 border-gray-50 rounded-2xl pl-16 pr-6 py-5 outline-none focus:bg-white focus:border-[#004d26] transition-all font-bold text-sm text-slate-700">
                </div>
              </div>
            </div>

            <!-- Address -->
            <div class="space-y-3">
              <label class="text-[10px] font-black text-slate-400 uppercase tracking-widest">Địa chỉ</label>
              <div class="relative group">
                <i class="fas fa-map-marker-alt absolute left-6 top-1/2 -translate-y-1/2 text-gray-300 group-focus-within:text-[#004d26] transition-colors"></i>
                <input v-model="contactForm.address" type="text" placeholder="Địa chỉ của bạn (không bắt buộc)" class="w-full bg-gray-50 border-2 border-gray-50 rounded-2xl pl-16 pr-6 py-5 outline-none focus:bg-white focus:border-[#004d26] transition-all font-bold text-sm text-slate-700">
              </div>
            </div>

            <!-- Note -->
            <div class="space-y-3">
              <label class="text-[10px] font-black text-slate-400 uppercase tracking-widest">Ghi chú</label>
              <div class="relative group">
                <i class="fas fa-comment-alt absolute left-6 top-6 text-gray-300 group-focus-within:text-[#004d26] transition-colors"></i>
                <textarea v-model="contactForm.note" rows="4" placeholder="Nội dung tin nhắn hoặc yêu cầu của bạn..." class="w-full bg-gray-50 border-2 border-gray-50 rounded-2xl pl-16 pr-6 py-5 outline-none focus:bg-white focus:border-[#004d26] transition-all font-bold text-sm text-slate-700 resize-none"></textarea>
              </div>
            </div>

            <!-- Submit Button -->
            <button 
              type="submit" 
              :disabled="isSubmitting"
              class="w-full bg-[#004d26] text-white py-6 rounded-2xl font-black uppercase tracking-[0.2em] text-xs hover:bg-[#003d1e] shadow-2xl shadow-green-900/30 transition-all flex items-center justify-center gap-4 group disabled:opacity-50"
            >
              <i class="fas fa-paper-plane group-hover:translate-x-1 group-hover:-translate-y-1 transition-transform"></i>
              {{ isSubmitting ? 'ĐANG GỬI...' : 'GỬI ĐI' }}
            </button>
          </form>

          <!-- Success Message -->
          <div v-if="submitSuccess" class="mt-8 p-6 bg-emerald-50 text-emerald-800 rounded-2xl font-black uppercase tracking-widest text-[10px] text-center border-2 border-emerald-100 animate-fade-in">
            Cảm ơn bạn! Thông tin của bạn đã được gửi thành công.
          </div>
        </div>
      </div>

      <!-- Bottom Features -->
      <div class="grid grid-cols-1 md:grid-cols-3 gap-8 mt-20">
        <div class="flex items-center gap-6 p-8 bg-gray-50 rounded-[2rem] border border-gray-100 group hover:bg-white hover:shadow-2xl hover:shadow-emerald-900/5 transition-all">
          <div class="w-12 h-12 bg-white rounded-xl flex items-center justify-center text-emerald-600 text-xl shadow-sm border border-gray-100 group-hover:bg-emerald-600 group-hover:text-white transition-all">
            <i class="fas fa-user-shield"></i>
          </div>
          <div>
            <h4 class="text-sm font-black text-slate-800 uppercase tracking-tight">Bảo mật thông tin</h4>
            <p class="text-[10px] text-gray-400 font-bold uppercase tracking-widest mt-1">Cam kết bảo mật tuyệt đối</p>
          </div>
        </div>
        <div class="flex items-center gap-6 p-8 bg-gray-50 rounded-[2rem] border border-gray-100 group hover:bg-white hover:shadow-2xl hover:shadow-emerald-900/5 transition-all">
          <div class="w-12 h-12 bg-white rounded-xl flex items-center justify-center text-emerald-600 text-xl shadow-sm border border-gray-100 group-hover:bg-emerald-600 group-hover:text-white transition-all">
            <i class="fas fa-bolt"></i>
          </div>
          <div>
            <h4 class="text-sm font-black text-slate-800 uppercase tracking-tight">Phản hồi nhanh chóng</h4>
            <p class="text-[10px] text-gray-400 font-bold uppercase tracking-widest mt-1">Hỗ trợ trong thời gian sớm nhất</p>
          </div>
        </div>
        <div class="flex items-center gap-6 p-8 bg-gray-50 rounded-[2rem] border border-gray-100 group hover:bg-white hover:shadow-2xl hover:shadow-emerald-900/5 transition-all">
          <div class="w-12 h-12 bg-white rounded-xl flex items-center justify-center text-emerald-600 text-xl shadow-sm border border-gray-100 group-hover:bg-emerald-600 group-hover:text-white transition-all">
            <i class="fas fa-heart"></i>
          </div>
          <div>
            <h4 class="text-sm font-black text-slate-800 uppercase tracking-tight">Hỗ trợ tận tâm</h4>
            <p class="text-[10px] text-gray-400 font-bold uppercase tracking-widest mt-1">Đội ngũ chuyên nghiệp</p>
          </div>
        </div>
      </div>
    </div>
  </main>
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
    await axios.post('/contact/submit', contactForm.value)
    submitSuccess.value = true
    contactForm.value = {
      fullName: '',
      phone: '',
      email: '',
      address: '',
      note: ''
    }
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
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css');

.animate-fade-in {
  animation: fadeIn 0.5s ease-out forwards;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>