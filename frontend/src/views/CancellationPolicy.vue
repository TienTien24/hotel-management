<template>
  <main class="policy-page min-h-screen bg-white font-sans">
    <!-- Hero Section -->
    <header class="relative h-[500px] flex items-center justify-center overflow-hidden">
      <div class="absolute inset-0 z-0">
        <img src="https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80" 
             alt="Policy Background" 
             class="w-full h-full object-cover">
        <div class="absolute inset-0 bg-black/60"></div>
      </div>
      <div class="max-w-7xl mx-auto px-6 relative z-10 text-center">
        <div class="flex items-center justify-center gap-3 mb-6 text-white/50 text-xs font-bold uppercase tracking-widest">
          <span>Trang chủ</span>
          <i class="fas fa-chevron-right text-[10px]"></i>
          <span>Chính sách</span>
          <i class="fas fa-chevron-right text-[10px]"></i>
          <span class="text-white">Chính sách hủy phòng</span>
        </div>
        <h1 class="text-7xl md:text-9xl font-black text-white uppercase tracking-tighter mb-4 leading-none">
          CHÍNH SÁCH<br>HỦY PHÒNG
        </h1>
        <div class="w-24 h-1 bg-amber-400 mx-auto mb-8"></div>
        <p class="text-2xl text-white/80 font-medium tracking-tight mb-2">
          Minh bạch – Linh hoạt – Hỗ trợ tận tâm
        </p>
        <p class="text-sm text-white/60 font-medium">Chúng tôi cam kết mang đến sự linh hoạt và tiện lợi nhất cho mọi khách hàng.</p>
      </div>
    </header>

    <!-- Info Bar -->
    <div class="max-w-7xl mx-auto px-6 -mt-16 relative z-20">
      <div class="bg-white rounded-[2.5rem] shadow-2xl shadow-emerald-900/10 p-10 border border-emerald-50 grid grid-cols-1 md:grid-cols-4 gap-8">
        <div class="flex items-center gap-5">
          <div class="w-14 h-14 bg-emerald-50 rounded-2xl flex items-center justify-center text-emerald-800 text-xl shadow-inner shrink-0">
            <i class="fas fa-calendar-check"></i>
          </div>
          <div>
            <h4 class="text-sm font-black text-slate-800 uppercase tracking-tight">Minh bạch</h4>
            <p class="text-[10px] text-gray-400 font-bold mt-1 uppercase">Chính sách rõ ràng, dễ hiểu</p>
          </div>
        </div>
        <div class="flex items-center gap-5">
          <div class="w-14 h-14 bg-emerald-50 rounded-2xl flex items-center justify-center text-emerald-800 text-xl shadow-inner shrink-0">
            <i class="fas fa-sync-alt"></i>
          </div>
          <div>
            <h4 class="text-sm font-black text-slate-800 uppercase tracking-tight">Linh hoạt</h4>
            <p class="text-[10px] text-gray-400 font-bold mt-1 uppercase">Tùy theo thời gian hủy và loại phòng</p>
          </div>
        </div>
        <div class="flex items-center gap-5">
          <div class="w-14 h-14 bg-emerald-50 rounded-2xl flex items-center justify-center text-emerald-800 text-xl shadow-inner shrink-0">
            <i class="fas fa-headset"></i>
          </div>
          <div>
            <h4 class="text-sm font-black text-slate-800 uppercase tracking-tight">Hỗ trợ 24/7</h4>
            <p class="text-[10px] text-gray-400 font-bold mt-1 uppercase">Đội ngũ chăm sóc khách hàng sẵn sàng</p>
          </div>
        </div>
        <div class="flex items-center gap-5">
          <div class="w-14 h-14 bg-emerald-50 rounded-2xl flex items-center justify-center text-emerald-800 text-xl shadow-inner shrink-0">
            <i class="fas fa-shield-alt"></i>
          </div>
          <div>
            <h4 class="text-sm font-black text-slate-800 uppercase tracking-tight">An tâm đặt phòng</h4>
            <p class="text-[10px] text-gray-400 font-bold mt-1 uppercase">Trải nghiệm kỳ nghỉ trọn vẹn</p>
          </div>
        </div>
      </div>
    </div>

    <!-- Main Content -->
    <section class="max-w-5xl mx-auto px-6 py-32">
      <!-- Section 1: Summary -->
      <div class="mb-24">
        <div class="flex items-center gap-6 mb-12">
          <div class="w-12 h-12 bg-[#004d26] text-white rounded-full flex items-center justify-center font-black text-xl shadow-xl shadow-green-900/20">1</div>
          <div>
            <h2 class="text-3xl font-black text-emerald-950 uppercase tracking-tighter">Tóm tắt chính sách</h2>
            <p class="text-xs text-gray-400 font-bold uppercase tracking-widest mt-1">Chính sách hủy phòng linh hoạt, đảm bảo quyền lợi tối đa cho khách hàng.</p>
          </div>
        </div>
        
        <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
          <div class="bg-white border border-gray-100 rounded-[2.5rem] p-10 text-center shadow-xl shadow-emerald-900/5 hover:shadow-2xl transition-all duration-500 group">
            <div class="w-24 h-24 bg-emerald-50 rounded-full flex flex-col items-center justify-center mx-auto mb-6 group-hover:scale-110 transition-transform duration-500 border-2 border-emerald-100">
              <span class="text-2xl font-black text-emerald-600">100%</span>
            </div>
            <h3 class="text-lg font-black text-emerald-950 uppercase tracking-tight mb-2">Hủy trước 24h</h3>
            <p class="text-xs text-gray-400 font-bold uppercase tracking-widest leading-relaxed">Hoàn 100% tiền phòng<br>(không tính phí dịch vụ)</p>
          </div>

          <div class="bg-white border border-gray-100 rounded-[2.5rem] p-10 text-center shadow-xl shadow-emerald-900/5 hover:shadow-2xl transition-all duration-500 group">
            <div class="w-24 h-24 bg-amber-50 rounded-full flex flex-col items-center justify-center mx-auto mb-6 group-hover:scale-110 transition-transform duration-500 border-2 border-amber-100">
              <span class="text-2xl font-black text-amber-600">50%</span>
            </div>
            <h3 class="text-lg font-black text-emerald-950 uppercase tracking-tight mb-2">Hủy trong 24h</h3>
            <p class="text-xs text-gray-400 font-bold uppercase tracking-widest leading-relaxed">Hoàn 50% tiền phòng<br>(không tính phí dịch vụ)</p>
          </div>

          <div class="bg-white border border-gray-100 rounded-[2.5rem] p-10 text-center shadow-xl shadow-emerald-900/5 hover:shadow-2xl transition-all duration-500 group border-rose-100 bg-rose-50/10">
            <div class="w-24 h-24 bg-rose-50 rounded-full flex flex-col items-center justify-center mx-auto mb-6 group-hover:scale-110 transition-transform duration-500 border-2 border-rose-100">
              <span class="text-2xl font-black text-rose-600">0%</span>
            </div>
            <h3 class="text-lg font-black text-emerald-950 uppercase tracking-tight mb-2">No-show/Check-in</h3>
            <p class="text-xs text-gray-400 font-bold uppercase tracking-widest leading-relaxed">Không hoàn tiền</p>
          </div>
        </div>
      </div>

      <!-- Section 2: Detailed Timeline -->
      <div class="mb-24">
        <div class="flex items-center gap-6 mb-12">
          <div class="w-12 h-12 bg-[#004d26] text-white rounded-full flex items-center justify-center font-black text-xl shadow-xl shadow-green-900/20">2</div>
          <div>
            <h2 class="text-3xl font-black text-emerald-950 uppercase tracking-tighter">Thời gian và phí hủy</h2>
            <div class="w-12 h-1 bg-[#004d26] mt-2"></div>
          </div>
        </div>

        <div class="space-y-8 relative before:absolute before:left-[2.75rem] before:top-8 before:bottom-8 before:w-0.5 before:bg-gray-100">
          <!-- Timeline Item 1 -->
          <div class="relative pl-24 group">
            <div class="absolute left-8 top-1/2 -translate-y-1/2 w-6 h-6 rounded-full bg-emerald-500 border-4 border-white shadow-lg z-10"></div>
            <div class="bg-white border border-gray-100 rounded-[2rem] p-8 shadow-xl shadow-emerald-900/5 group-hover:shadow-2xl transition-all duration-500 flex flex-col sm:flex-row items-center gap-8">
              <div class="w-16 h-16 bg-emerald-50 rounded-2xl flex items-center justify-center text-emerald-600 text-2xl shadow-inner shrink-0">
                <i class="fas fa-calendar-alt"></i>
              </div>
              <div class="flex-1">
                <div class="flex flex-wrap items-center gap-3 mb-2">
                  <h3 class="text-xl font-black text-emerald-950 uppercase tracking-tight">Hủy trước 24 giờ so với ngày Check-in</h3>
                  <span class="px-3 py-1 bg-emerald-100 text-emerald-700 rounded-full text-[9px] font-black uppercase tracking-widest">Hoàn 100%</span>
                </div>
                <p class="text-sm text-gray-500 font-medium leading-relaxed">Khách hàng được hoàn lại <strong class="text-emerald-600">100%</strong> số tiền đã đặt cọc hoặc thanh toán. Không áp dụng phí hủy phòng và không có điều khoản ẩn khác.</p>
              </div>
            </div>
          </div>

          <!-- Timeline Item 2 -->
          <div class="relative pl-24 group">
            <div class="absolute left-8 top-1/2 -translate-y-1/2 w-6 h-6 rounded-full bg-amber-500 border-4 border-white shadow-lg z-10"></div>
            <div class="bg-white border border-gray-100 rounded-[2rem] p-8 shadow-xl shadow-emerald-900/5 group-hover:shadow-2xl transition-all duration-500 flex flex-col sm:flex-row items-center gap-8">
              <div class="w-16 h-16 bg-amber-50 rounded-2xl flex items-center justify-center text-amber-600 text-2xl shadow-inner shrink-0">
                <i class="fas fa-history"></i>
              </div>
              <div class="flex-1">
                <div class="flex flex-wrap items-center gap-3 mb-2">
                  <h3 class="text-xl font-black text-emerald-950 uppercase tracking-tight">Hủy trong vòng 24 giờ trước ngày Check-in</h3>
                  <span class="px-3 py-1 bg-amber-100 text-amber-700 rounded-full text-[9px] font-black uppercase tracking-widest">Phí hủy 50%</span>
                </div>
                <p class="text-sm text-gray-500 font-medium leading-relaxed">Khách hàng sẽ được hoàn lại <strong class="text-amber-600">50%</strong> số tiền. Phí hủy phòng tương đương 50% giá trị đặt phòng đã thanh toán trước.</p>
              </div>
            </div>
          </div>

          <!-- Timeline Item 3 -->
          <div class="relative pl-24 group">
            <div class="absolute left-8 top-1/2 -translate-y-1/2 w-6 h-6 rounded-full bg-rose-500 border-4 border-white shadow-lg z-10"></div>
            <div class="bg-white border border-gray-100 rounded-[2rem] p-8 shadow-xl shadow-emerald-900/5 group-hover:shadow-2xl transition-all duration-500 flex flex-col sm:flex-row items-center gap-8">
              <div class="w-16 h-16 bg-rose-50 rounded-2xl flex items-center justify-center text-rose-600 text-2xl shadow-inner shrink-0">
                <i class="fas fa-times-circle"></i>
              </div>
              <div class="flex-1">
                <div class="flex flex-wrap items-center gap-3 mb-2">
                  <h3 class="text-xl font-black text-emerald-950 uppercase tracking-tight">Sau khi đã Check-in hoặc No-show</h3>
                  <span class="px-3 py-1 bg-rose-100 text-rose-700 rounded-full text-[9px] font-black uppercase tracking-widest">Không hoàn tiền</span>
                </div>
                <p class="text-sm text-gray-500 font-medium leading-relaxed">Không áp dụng hoàn tiền cho các trường hợp đã nhận phòng hoặc khách không đến mà không thông báo trước.</p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Section 3: How to -->
      <div class="mb-24">
        <div class="flex items-center gap-6 mb-12">
          <div class="w-12 h-12 bg-[#004d26] text-white rounded-full flex items-center justify-center font-black text-xl shadow-xl shadow-green-900/20">3</div>
          <div>
            <h2 class="text-3xl font-black text-emerald-950 uppercase tracking-tighter">Cách thức thực hiện</h2>
            <p class="text-xs text-gray-400 font-bold uppercase tracking-widest mt-1">Bạn có thể thực hiện hủy phòng trực tuyến thông qua mục <strong class="text-emerald-800">"Booking của tôi"</strong>.</p>
          </div>
        </div>

        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
          <div class="text-center group">
            <div class="w-16 h-16 bg-gray-50 rounded-2xl flex items-center justify-center mx-auto mb-6 text-emerald-800 text-xl border-2 border-gray-100 group-hover:bg-[#004d26] group-hover:text-white transition-all duration-500">
              <i class="fas fa-user-circle"></i>
            </div>
            <h4 class="text-sm font-black text-emerald-950 uppercase tracking-tight mb-2">Đăng nhập tài khoản</h4>
            <p class="text-[10px] text-gray-400 font-bold uppercase leading-relaxed">Truy cập tài khoản của bạn trên website.</p>
          </div>

          <div class="text-center group">
            <div class="w-16 h-16 bg-gray-50 rounded-2xl flex items-center justify-center mx-auto mb-6 text-emerald-800 text-xl border-2 border-gray-100 group-hover:bg-[#004d26] group-hover:text-white transition-all duration-500">
              <i class="fas fa-calendar-check"></i>
            </div>
            <h4 class="text-sm font-black text-emerald-950 uppercase tracking-tight mb-2">Chọn đặt phòng</h4>
            <p class="text-[10px] text-gray-400 font-bold uppercase leading-relaxed">Vào mục "Booking của tôi", chọn đơn muốn hủy.</p>
          </div>

          <div class="text-center group">
            <div class="w-16 h-16 bg-gray-50 rounded-2xl flex items-center justify-center mx-auto mb-6 text-emerald-800 text-xl border-2 border-gray-100 group-hover:bg-[#004d26] group-hover:text-white transition-all duration-500">
              <i class="fas fa-file-invoice"></i>
            </div>
            <h4 class="text-sm font-black text-emerald-950 uppercase tracking-tight mb-2">Xác nhận hủy</h4>
            <p class="text-[10px] text-gray-400 font-bold uppercase leading-relaxed">Kiểm tra thông tin và xác nhận hủy theo hướng dẫn.</p>
          </div>

          <div class="text-center group">
            <div class="w-16 h-16 bg-gray-50 rounded-2xl flex items-center justify-center mx-auto mb-6 text-emerald-800 text-xl border-2 border-gray-100 group-hover:bg-[#004d26] group-hover:text-white transition-all duration-500">
              <i class="fas fa-check-circle"></i>
            </div>
            <h4 class="text-sm font-black text-emerald-950 uppercase tracking-tight mb-2">Nhận xác nhận</h4>
            <p class="text-[10px] text-gray-400 font-bold uppercase leading-relaxed">Hệ thống sẽ gửi email xác nhận và thông tin hoàn tiền.</p>
          </div>
        </div>

        <!-- Contact Support -->
        <div class="mt-12 bg-gray-50 rounded-[2rem] p-8 border border-gray-100 flex flex-col md:flex-row items-center justify-center gap-12">
          <div class="flex items-center gap-4">
            <i class="fas fa-phone-alt text-emerald-800"></i>
            <div>
              <p class="text-[9px] font-black text-gray-400 uppercase tracking-widest">Hỗ trợ khách hàng</p>
              <p class="text-sm font-black text-emerald-950">Hotline: 0123 456 789</p>
            </div>
          </div>
          <div class="flex items-center gap-4 border-gray-200 md:border-l md:pl-12">
            <i class="fas fa-envelope text-emerald-800"></i>
            <div>
              <p class="text-[9px] font-black text-gray-400 uppercase tracking-widest">Email</p>
              <p class="text-sm font-black text-emerald-950">support@grandhotel.com</p>
            </div>
          </div>
          <div class="flex items-center gap-4 border-gray-200 md:border-l md:pl-12">
            <i class="fas fa-comments text-emerald-800"></i>
            <div>
              <p class="text-[9px] font-black text-gray-400 uppercase tracking-widest">Chat trực tuyến</p>
              <p class="text-sm font-black text-emerald-950">Live Chat 24/7</p>
            </div>
          </div>
        </div>
      </div>

      <!-- Section 4: Special Cases -->
      <div class="mb-24">
        <div class="flex items-center gap-6 mb-12">
          <div class="w-12 h-12 bg-[#004d26] text-white rounded-full flex items-center justify-center font-black text-xl shadow-xl shadow-green-900/20">4</div>
          <div>
            <h2 class="text-3xl font-black text-emerald-950 uppercase tracking-tighter">Trường hợp đặc biệt</h2>
            <p class="text-xs text-gray-400 font-bold uppercase tracking-widest mt-1">Chính sách hủy phòng linh hoạt, đảm bảo quyền lợi tối đa cho khách hàng.</p>
          </div>
        </div>

        <div class="space-y-4">
          <details class="group bg-white border border-gray-100 rounded-2xl overflow-hidden shadow-sm transition-all duration-300">
            <summary class="flex items-center justify-between p-6 cursor-pointer list-none">
              <div class="flex items-center gap-5">
                <div class="w-10 h-10 bg-gray-50 rounded-xl flex items-center justify-center text-gray-400 group-hover:bg-emerald-50 group-hover:text-emerald-600 transition-all duration-300 shadow-inner">
                  <i class="fas fa-shield-virus"></i>
                </div>
                <h4 class="text-sm font-black text-emerald-950 uppercase tracking-tight">Bất khả kháng (Force Majeure)</h4>
              </div>
              <i class="fas fa-chevron-down text-gray-300 group-open:rotate-180 transition-transform duration-300"></i>
            </summary>
            <div class="p-6 pt-0 border-t border-gray-50">
              <p class="text-xs text-gray-500 font-medium leading-relaxed">Trong trường hợp thiên tai, dịch bệnh, chiến tranh... khách sạn sẽ xem xét hoàn 100% hoặc cho phép đổi lịch mà không tính phí.</p>
            </div>
          </details>

          <details class="group bg-white border border-gray-100 rounded-2xl overflow-hidden shadow-sm transition-all duration-300">
            <summary class="flex items-center justify-between p-6 cursor-pointer list-none">
              <div class="flex items-center gap-5">
                <div class="w-10 h-10 bg-gray-50 rounded-xl flex items-center justify-center text-gray-400 group-hover:bg-emerald-50 group-hover:text-emerald-600 transition-all duration-300 shadow-inner">
                  <i class="fas fa-heartbeat"></i>
                </div>
                <h4 class="text-sm font-black text-emerald-950 uppercase tracking-tight">Y tế khẩn cấp</h4>
              </div>
              <i class="fas fa-chevron-down text-gray-300 group-open:rotate-180 transition-transform duration-300"></i>
            </summary>
            <div class="p-6 pt-0 border-t border-gray-50">
              <p class="text-xs text-gray-500 font-medium leading-relaxed">Chúng tôi luôn hỗ trợ tối đa cho các trường hợp y tế bất ngờ. Vui lòng liên hệ sớm để được hỗ trợ nhanh nhất.</p>
            </div>
          </details>
        </div>
      </div>

      <!-- CTA Section -->
      <div class="bg-[#004d26] rounded-[3rem] p-12 text-center shadow-2xl shadow-green-900/40 relative overflow-hidden group">
        <div class="absolute inset-0 bg-white/5 opacity-0 group-hover:opacity-100 transition-opacity duration-700"></div>
        <div class="relative z-10">
          <h2 class="text-4xl md:text-5xl font-black text-white uppercase tracking-tighter mb-4 leading-none">Sẵn sàng cho kỳ nghỉ tuyệt vời?</h2>
          <p class="text-lg text-white/60 font-medium mb-12">Đặt phòng ngay hôm nay để tận hưởng ưu đãi tốt nhất tại Grand Hotel.</p>
          <div class="flex flex-wrap justify-center gap-4">
            <router-link to="/rooms" class="px-10 py-5 bg-amber-400 text-emerald-950 rounded-2xl font-black uppercase tracking-widest text-xs hover:bg-white hover:shadow-2xl hover:shadow-white/20 transition-all duration-500">Đặt phòng ngay</router-link>
            <router-link to="/contact" class="px-10 py-5 bg-white/10 text-white border-2 border-white/20 rounded-2xl font-black uppercase tracking-widest text-xs hover:bg-white/20 transition-all duration-500">Liên hệ tư vấn</router-link>
          </div>
        </div>
      </div>
    </section>
  </main>
</template>

<script setup>
// Static page, no dynamic data needed
</script>

<style scoped>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css');

summary::-webkit-details-marker {
  display: none;
}
</style>