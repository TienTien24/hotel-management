<template>
  <footer id="contact" class="bg-emerald-950 text-white pt-16 pb-8">
    <div class="max-w-7xl mx-auto px-4 grid grid-cols-1 md:grid-cols-4 gap-12">
      <!-- Brand & Info -->
      <div class="col-span-1 md:col-span-1">
        <div class="flex items-center space-x-2 mb-6">
          <div class="w-10 h-10 bg-white rounded-full flex items-center justify-center">
            <span class="text-emerald-900 font-bold text-xl">H</span>
          </div>
          <span class="text-2xl font-black tracking-tighter uppercase">Grand Hotel</span>
        </div>
        <p class="text-emerald-100/60 leading-relaxed mb-8 font-light">
          Trải nghiệm sự sang trọng và đẳng cấp tại Grand Hotel. Chúng tôi cam kết mang đến cho bạn những giây phút nghỉ dưỡng tuyệt vời nhất.
        </p>
        <div class="flex space-x-4">
          <a href="#" class="w-10 h-10 bg-emerald-900/50 rounded-full flex items-center justify-center hover:bg-emerald-800 transition-colors">
            <i class="fab fa-facebook-f text-sm"></i>
          </a>
          <a href="#" class="w-10 h-10 bg-emerald-900/50 rounded-full flex items-center justify-center hover:bg-emerald-800 transition-colors">
            <i class="fab fa-instagram text-sm"></i>
          </a>
          <a href="#" class="w-10 h-10 bg-emerald-900/50 rounded-full flex items-center justify-center hover:bg-emerald-800 transition-colors">
            <i class="fab fa-twitter text-sm"></i>
          </a>
        </div>
      </div>

      <!-- Quick Links -->
      <div>
        <h4 class="text-lg font-bold uppercase tracking-widest mb-6 border-b border-emerald-800 pb-2 inline-block">Liên kết</h4>
        <ul class="space-y-4 text-emerald-100/60 font-light">
          <li><router-link to="/" class="hover:text-white transition-colors">Trang chủ</router-link></li>
          <li><a href="#about" class="hover:text-white transition-colors">Giới thiệu</a></li>
          <li><router-link to="/rooms" class="hover:text-white transition-colors">Loại Phòng</router-link></li>
          <li><a href="#services" class="hover:text-white transition-colors">Dịch vụ</a></li>
          <li><router-link to="/checkin" class="hover:text-white transition-colors">Check-in</router-link></li>
          <li><a href="#testimonials" class="hover:text-white transition-colors">Đánh giá</a></li>
          <li><router-link to="/contact" class="hover:text-white transition-colors">Liên hệ</router-link></li>
        </ul>
      </div>

      <!-- Services -->
      <div>
        <h4 class="text-lg font-bold uppercase tracking-widest mb-6 border-b border-emerald-800 pb-2 inline-block">Dịch vụ</h4>
        <ul class="space-y-4 text-emerald-100/60 font-light">
          <li>Nhà hàng & Bar</li>
          <li>Spa & Wellness</li>
          <li>Hồ bơi vô cực</li>
          <li>Đưa đón sân bay</li>
        </ul>
      </div>

      <!-- Contact Info -->
      <div>
        <h4 class="text-lg font-bold uppercase tracking-widest mb-6 border-b border-emerald-800 pb-2 inline-block">Liên hệ</h4>
        <ul class="space-y-4 text-emerald-100/60 font-light">
          <li class="flex items-start space-x-3">
            <i class="fas fa-map-marker-alt mt-1 text-emerald-500"></i>
            <span>123 Đường ABC, Quận 1, TP. Hồ Chí Minh</span>
          </li>
          <li class="flex items-center space-x-3">
            <i class="fas fa-phone-alt text-emerald-500"></i>
            <span>+84 123 456 789</span>
          </li>
          <li class="flex items-center space-x-3">
            <i class="fas fa-envelope text-emerald-500"></i>
            <span>contact@grandhotel.com</span>
          </li>
        </ul>
      </div>
    </div>

    <div class="max-w-7xl mx-auto px-4 mt-16 pt-8 border-t border-emerald-900/50 text-center text-emerald-100/40 text-sm font-light">
      <p>&copy; 2026 Grand Hotel. All rights reserved. Designed with ❤️</p>
    </div>
  </footer>
</template>

<script setup>
// Footer logic if any
</script>

<style scoped>
/* Footer styles */
</style>
