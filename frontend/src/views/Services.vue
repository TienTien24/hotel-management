<template>
  <main class="services-page min-h-screen bg-white font-sans">
    <!-- Hero Section -->
    <header class="relative h-[500px] flex items-center justify-center overflow-hidden">
      <div class="absolute inset-0 z-0">
        <img src="https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80" 
             alt="Hero Background" 
             class="w-full h-full object-cover">
        <div class="absolute inset-0 bg-black/60"></div>
      </div>
      <div class="max-w-7xl mx-auto px-6 relative z-10">
        <h1 class="text-7xl md:text-9xl font-black text-white uppercase tracking-tighter mb-4 leading-none">
          DỊCH VỤ
        </h1>
        <p class="text-2xl text-white/80 font-medium tracking-tight">
          Trải nghiệm tiện nghi đẳng cấp
        </p>
      </div>
    </header>

    <!-- Info Bar -->
    <div class="max-w-7xl mx-auto px-6 -mt-16 relative z-20">
      <div class="bg-white rounded-[2.5rem] shadow-2xl shadow-emerald-900/10 p-10 border border-emerald-50 grid grid-cols-1 md:grid-cols-4 gap-8">
        <div class="flex items-center gap-5">
          <div class="w-14 h-14 bg-emerald-50 rounded-2xl flex items-center justify-center text-emerald-800 text-xl shadow-inner shrink-0">
            <i class="fas fa-star"></i>
          </div>
          <div>
            <h4 class="text-sm font-black text-slate-800 uppercase tracking-tight">Chất lượng 5 sao</h4>
            <p class="text-[10px] text-gray-400 font-bold mt-1 uppercase">Dịch vụ tiêu chuẩn quốc tế</p>
          </div>
        </div>
        <div class="flex items-center gap-5">
          <div class="w-14 h-14 bg-emerald-50 rounded-2xl flex items-center justify-center text-emerald-800 text-xl shadow-inner shrink-0">
            <i class="fas fa-th-large"></i>
          </div>
          <div>
            <h4 class="text-sm font-black text-slate-800 uppercase tracking-tight">Đa dạng tiện ích</h4>
            <p class="text-[10px] text-gray-400 font-bold mt-1 uppercase">Đáp ứng mọi nhu cầu</p>
          </div>
        </div>
        <div class="flex items-center gap-5">
          <div class="w-14 h-14 bg-emerald-50 rounded-2xl flex items-center justify-center text-emerald-800 text-xl shadow-inner shrink-0">
            <i class="fas fa-file-alt"></i>
          </div>
          <div>
            <h4 class="text-sm font-black text-slate-800 uppercase tracking-tight">Đặt dịch vụ dễ dàng</h4>
            <p class="text-[10px] text-gray-400 font-bold mt-1 uppercase">Nhanh chóng, tiện lợi</p>
          </div>
        </div>
        <div class="flex items-center gap-5">
          <div class="w-14 h-14 bg-emerald-50 rounded-2xl flex items-center justify-center text-emerald-800 text-xl shadow-inner shrink-0">
            <i class="fas fa-headset"></i>
          </div>
          <div>
            <h4 class="text-sm font-black text-slate-800 uppercase tracking-tight">Hỗ trợ 24/7</h4>
            <p class="text-[10px] text-gray-400 font-bold mt-1 uppercase">Luôn sẵn sàng phục vụ</p>
          </div>
        </div>
      </div>
    </div>

    <!-- Services Section -->
    <section class="max-w-7xl mx-auto px-6 py-32">
      <div class="text-center mb-24">
        <h2 class="text-4xl md:text-5xl font-black text-emerald-950 uppercase tracking-tighter mb-4">DANH SÁCH DỊCH VỤ</h2>
        <div class="w-24 h-1 bg-[#004d26] mx-auto mb-8"></div>
        <p class="text-gray-400 font-bold uppercase tracking-widest text-sm">Khám phá các tiện ích và dịch vụ đẳng cấp tại Grand Hotel</p>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-2 gap-12">
        <!-- Service Card 1 -->
        <div class="group bg-white rounded-[3rem] shadow-xl hover:shadow-2xl transition-all duration-500 overflow-hidden border border-gray-100 flex flex-col sm:flex-row">
          <div class="sm:w-2/5 h-64 sm:h-auto overflow-hidden">
            <img src="https://viphouse.vn/viphouse/images/2017/t7/thiet%20ke%20bar%20khach%20s%E1%BA%A1n/201408-w-best-rooftop-bars-in-nyc-hotel-hugo.jpg" class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-1000">
          </div>
          <div class="sm:w-3/5 p-10 flex flex-col justify-between">
            <div>
              <div class="flex items-center gap-4 mb-4">
                <div class="w-12 h-12 bg-emerald-50 rounded-2xl flex items-center justify-center text-emerald-800 text-lg shadow-inner">
                  <i class="fas fa-utensils"></i>
                </div>
                <h3 class="text-2xl font-black text-slate-800 uppercase tracking-tight">Nhà hàng & Bar</h3>
              </div>
              <p class="text-sm text-gray-500 leading-relaxed font-medium mb-6">Thưởng thức ẩm thực đa dạng từ Á đến Âu được chế biến bởi đội ngũ đầu bếp chuyên nghiệp.</p>
              <ul class="space-y-3 mb-8">
                <li class="flex items-center gap-3 text-xs font-bold text-gray-400 uppercase tracking-widest">
                  <i class="fas fa-check-circle text-emerald-500"></i> Thực đơn đa dạng
                </li>
                <li class="flex items-center gap-3 text-xs font-bold text-gray-400 uppercase tracking-widest">
                  <i class="fas fa-check-circle text-emerald-500"></i> Nguyên liệu tươi ngon
                </li>
                <li class="flex items-center gap-3 text-xs font-bold text-gray-400 uppercase tracking-widest">
                  <i class="fas fa-check-circle text-emerald-500"></i> Không gian sang trọng
                </li>
              </ul>
            </div>
          </div>
        </div>

        <!-- Service Card 2 -->
        <div class="group bg-white rounded-[3rem] shadow-xl hover:shadow-2xl transition-all duration-500 overflow-hidden border border-gray-100 flex flex-col sm:flex-row">
          <div class="sm:w-2/5 h-64 sm:h-auto overflow-hidden">
            <img src="https://vinapad.com/wp-content/uploads/2019/07/cac-goi-dich-vu-spa-1.jpg" class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-1000">
          </div>
          <div class="sm:w-3/5 p-10 flex flex-col justify-between">
            <div>
              <div class="flex items-center gap-4 mb-4">
                <div class="w-12 h-12 bg-emerald-50 rounded-2xl flex items-center justify-center text-emerald-800 text-lg shadow-inner">
                  <i class="fas fa-spa"></i>
                </div>
                <h3 class="text-2xl font-black text-slate-800 uppercase tracking-tight">Spa & Wellness</h3>
              </div>
              <p class="text-sm text-gray-500 leading-relaxed font-medium mb-6">Thư giãn và tái tạo năng lượng với các liệu trình spa chuyên nghiệp.</p>
              <ul class="space-y-3 mb-8">
                <li class="flex items-center gap-3 text-xs font-bold text-gray-400 uppercase tracking-widest">
                  <i class="fas fa-check-circle text-emerald-500"></i> Massage thư giãn
                </li>
                <li class="flex items-center gap-3 text-xs font-bold text-gray-400 uppercase tracking-widest">
                  <i class="fas fa-check-circle text-emerald-500"></i> Chăm sóc da chuyên sâu
                </li>
                <li class="flex items-center gap-3 text-xs font-bold text-gray-400 uppercase tracking-widest">
                  <i class="fas fa-check-circle text-emerald-500"></i> Phòng xông hơi & sauna
                </li>
              </ul>
            </div>
          </div>
        </div>

        <!-- Service Card 3 -->
        <div class="group bg-white rounded-[3rem] shadow-xl hover:shadow-2xl transition-all duration-500 overflow-hidden border border-gray-100 flex flex-col sm:flex-row">
          <div class="sm:w-2/5 h-64 sm:h-auto overflow-hidden">
            <img src="https://danang-shopping.com/wp-content/uploads/2017/05/khach-san-da-nang-co-ho-boi-dep-diamond-sea-hotel.jpg" class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-1000">
          </div>
          <div class="sm:w-3/5 p-10 flex flex-col justify-between">
            <div>
              <div class="flex items-center gap-4 mb-4">
                <div class="w-12 h-12 bg-emerald-50 rounded-2xl flex items-center justify-center text-emerald-800 text-lg shadow-inner">
                  <i class="fas fa-swimming-pool"></i>
                </div>
                <h3 class="text-2xl font-black text-slate-800 uppercase tracking-tight">Hồ bơi vô cực</h3>
              </div>
              <p class="text-sm text-gray-500 leading-relaxed font-medium mb-6">Hồ bơi vô cực view thành phố tuyệt đẹp, mang đến trải nghiệm thư giãn tuyệt vời.</p>
              <ul class="space-y-3 mb-8">
                <li class="flex items-center gap-3 text-xs font-bold text-gray-400 uppercase tracking-widest">
                  <i class="fas fa-check-circle text-emerald-500"></i> View panorama 360°
                </li>
                <li class="flex items-center gap-3 text-xs font-bold text-gray-400 uppercase tracking-widest">
                  <i class="fas fa-check-circle text-emerald-500"></i> Nước sạch 4 mùa
                </li>
                <li class="flex items-center gap-3 text-xs font-bold text-gray-400 uppercase tracking-widest">
                  <i class="fas fa-check-circle text-emerald-500"></i> Quầy bar bên hồ bơi
                </li>
              </ul>
            </div>
          </div>
        </div>

        <!-- Service Card 4 -->
        <div class="group bg-white rounded-[3rem] shadow-xl hover:shadow-2xl transition-all duration-500 overflow-hidden border border-gray-100 flex flex-col sm:flex-row">
          <div class="sm:w-2/5 h-64 sm:h-auto overflow-hidden">
            <img src="https://vinapad.com/wp-content/uploads/2019/04/phong-hop-khach-san-1.jpg" class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-1000">
          </div>
          <div class="sm:w-3/5 p-10 flex flex-col justify-between">
            <div>
              <div class="flex items-center gap-4 mb-4">
                <div class="w-12 h-12 bg-emerald-50 rounded-2xl flex items-center justify-center text-emerald-800 text-lg shadow-inner">
                  <i class="fas fa-users"></i>
                </div>
                <h3 class="text-2xl font-black text-slate-800 uppercase tracking-tight">Phòng họp & Sự kiện</h3>
              </div>
              <p class="text-sm text-gray-500 leading-relaxed font-medium mb-6">Không gian tổ chức sự kiện chuyên nghiệp, hiện đại với đầy đủ trang thiết bị.</p>
              <ul class="space-y-3 mb-8">
                <li class="flex items-center gap-3 text-xs font-bold text-gray-400 uppercase tracking-widest">
                  <i class="fas fa-check-circle text-emerald-500"></i> Sức chứa linh hoạt
                </li>
                <li class="flex items-center gap-3 text-xs font-bold text-gray-400 uppercase tracking-widest">
                  <i class="fas fa-check-circle text-emerald-500"></i> Thiết bị âm thanh ánh sáng
                </li>
                <li class="flex items-center gap-3 text-xs font-bold text-gray-400 uppercase tracking-widest">
                  <i class="fas fa-check-circle text-emerald-500"></i> Hỗ trợ kỹ thuật 24/7
                </li>
              </ul>
            </div>
          </div>
        </div>

        <!-- Service Card 5 -->
        <div class="group bg-white rounded-[3rem] shadow-xl hover:shadow-2xl transition-all duration-500 overflow-hidden border border-gray-100 flex flex-col sm:flex-row">
          <div class="sm:w-2/5 h-64 sm:h-auto overflow-hidden">
            <img src="https://vinapad.com/wp-content/uploads/2019/04/phong-hop-khach-san-1.jpg" class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-1000">
          </div>
          <div class="sm:w-3/5 p-8 flex flex-col justify-between">
            <div>
              <div class="flex items-center gap-4 mb-3">
                <div class="w-10 h-10 bg-[#004d26] rounded-full flex items-center justify-center text-white text-sm shadow-lg">
                  <i class="fas fa-users"></i>
                </div>
                <h3 class="text-2xl font-black text-slate-800 uppercase tracking-tight">Phòng họp & Sự kiện</h3>
              </div>
              <p class="text-sm text-gray-500 leading-relaxed font-medium mb-4">Không gian tổ chức sự kiện chuyên nghiệp, hiện đại với đầy đủ trang thiết bị.</p>
              <ul class="space-y-2 mb-6">
                <li class="flex items-center gap-3 text-xs font-bold text-gray-400 uppercase tracking-widest">
                  <i class="fas fa-check-circle text-emerald-500"></i> Sức chứa linh hoạt
                </li>
                <li class="flex items-center gap-3 text-xs font-bold text-gray-400 uppercase tracking-widest">
                  <i class="fas fa-check-circle text-emerald-500"></i> Thiết bị âm thanh ánh sáng
                </li>
                <li class="flex items-center gap-3 text-xs font-bold text-gray-400 uppercase tracking-widest">
                  <i class="fas fa-check-circle text-emerald-500"></i> Hỗ trợ kỹ thuật 24/7
                </li>
              </ul>
            </div>
          </div>
        </div>

        <!-- Service Card 6 -->
        <div class="group bg-white rounded-[3rem] shadow-xl hover:shadow-2xl transition-all duration-500 overflow-hidden border border-gray-100 flex flex-col sm:flex-row">
          <div class="sm:w-2/5 h-64 sm:h-auto overflow-hidden">
            <img src="https://vcdn1-kinhdoanh.vnecdn.net/2022/07/21/May-giat-S-Class-2-1658392102.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=Zp8T8T-w-w-w-w-w-w-w" class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-1000">
          </div>
          <div class="sm:w-3/5 p-8 flex flex-col justify-between">
            <div>
              <div class="flex items-center gap-4 mb-3">
                <div class="w-10 h-10 bg-[#004d26] rounded-full flex items-center justify-center text-white text-sm shadow-lg">
                  <i class="fas fa-tshirt"></i>
                </div>
                <h3 class="text-2xl font-black text-slate-800 uppercase tracking-tight">Giặt ủi</h3>
              </div>
              <p class="text-sm text-gray-500 leading-relaxed font-medium mb-4">Dịch vụ giặt ủi nhanh chóng, sạch sẽ với công nghệ hiện đại.</p>
              <ul class="space-y-2 mb-6">
                <li class="flex items-center gap-3 text-xs font-bold text-gray-400 uppercase tracking-widest">
                  <i class="fas fa-check-circle text-emerald-500"></i> Giặt ủi nhanh chóng
                </li>
                <li class="flex items-center gap-3 text-xs font-bold text-gray-400 uppercase tracking-widest">
                  <i class="fas fa-check-circle text-emerald-500"></i> Sấy khô, là ủi chuyên nghiệp
                </li>
                <li class="flex items-center gap-3 text-xs font-bold text-gray-400 uppercase tracking-widest">
                  <i class="fas fa-check-circle text-emerald-500"></i> Nhận & trả tại phòng
                </li>
                <li class="flex items-center gap-3 text-xs font-bold text-gray-400 uppercase tracking-widest">
                  <i class="fas fa-check-circle text-emerald-500"></i> Giá cả hợp lý
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </section>
  </main>
</template>

<script setup>
// No specific script needed for this static content page
</script>

<style scoped>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css');
/* Add any specific styles for the services page here */
</style>
