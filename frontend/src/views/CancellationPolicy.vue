<template>
  <div class="min-h-screen bg-gray-100 pb-10">
    <section class="bg-emerald-950 text-white relative overflow-hidden h-[400px] flex items-center justify-center">
      <div class="absolute inset-0 opacity-10">
        <img src="https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80" class="w-full h-full object-cover">
      </div>
      <div class="max-w-7xl mx-auto px-4 relative z-10 text-center">
        <h1 class="text-4xl md:text-6xl font-black uppercase tracking-tighter mb-6">Chính sách <span class="text-emerald-400">Hủy phòng</span></h1>
        <p class="text-emerald-100 font-light tracking-[0.3em] uppercase text-sm">Thông tin minh bạch về việc hoàn tiền và thay đổi đặt phòng</p>
      </div>
    </section>

    <div class="max-w-4xl mx-auto px-4 -mt-20 relative z-20">
      <div class="bg-white rounded-[2rem] shadow-2xl p-8 md:p-12 space-y-10">
        <section>
          <h2 class="text-2xl font-black text-emerald-950 uppercase tracking-widest mb-6 border-l-4 border-emerald-500 pl-4">1. Quy định chung</h2>
          <p class="text-gray-700 leading-relaxed mb-4">
            Tại Grand Hotel, chúng tôi hiểu rằng kế hoạch của bạn có thể thay đổi. Chính sách hủy phòng của chúng tôi được thiết kế để cân bằng giữa sự linh hoạt cho khách hàng và nhu cầu quản lý vận hành của khách sạn.
          </p>
        </section>

        <section class="bg-emerald-50 rounded-3xl p-8 border border-emerald-100">
          <h2 class="text-2xl font-black text-emerald-950 uppercase tracking-widest mb-6">2. Thời gian và Phí hủy</h2>
          <div class="space-y-6">
            <div class="flex items-start gap-4">
              <div class="w-12 h-12 bg-emerald-800 text-white rounded-full flex items-center justify-center flex-shrink-0 font-bold">1</div>
              <div>
                <h3 class="font-bold text-lg text-emerald-900">Hủy trước 24 giờ so với ngày Check-in</h3>
                <p class="text-gray-600">Khách hàng được hoàn lại <strong>100%</strong> số tiền đã đặt cọc hoặc thanh toán. Không áp dụng phí hủy phòng.</p>
              </div>
            </div>
            <div class="flex items-start gap-4">
              <div class="w-12 h-12 bg-amber-500 text-white rounded-full flex items-center justify-center flex-shrink-0 font-bold">2</div>
              <div>
                <h3 class="font-bold text-lg text-amber-900">Hủy trong vòng 24 giờ trước ngày Check-in</h3>
                <p class="text-gray-600">Khách hàng sẽ được hoàn lại <strong>90%</strong> số tiền. Phí hủy phòng tương đương 10% giá trị đặt phòng.</p>
              </div>
            </div>
            <div class="flex items-start gap-4">
              <div class="w-12 h-12 bg-red-600 text-white rounded-full flex items-center justify-center flex-shrink-0 font-bold">3</div>
              <div>
                <h3 class="font-bold text-lg text-red-900">Sau khi đã Check-in hoặc No-show</h3>
                <p class="text-gray-600">Không áp dụng hoàn tiền cho các trường hợp đã nhận phòng hoặc khách không đến mà không có thông báo trước.</p>
              </div>
            </div>
          </div>
        </section>

        <section>
          <h2 class="text-2xl font-black text-emerald-950 uppercase tracking-widest mb-6 border-l-4 border-emerald-500 pl-4">3. Cách thức thực hiện</h2>
          <p class="text-gray-700 leading-relaxed mb-4">
            Bạn có thể thực hiện hủy phòng trực tuyến thông qua mục <strong>"Booking của tôi"</strong> trong tài khoản của bạn. Hệ thống sẽ tự động tính toán số tiền hoàn lại dựa trên thời điểm bạn thực hiện thao tác.
          </p>
          <p class="text-gray-700 leading-relaxed">
            Nếu cần hỗ trợ thêm, vui lòng liên hệ bộ phận chăm sóc khách hàng qua hotline: <span class="font-bold text-emerald-800">0123-456-789</span> hoặc email: <span class="font-bold text-emerald-800">support@grandhotel.com</span>.
          </p>
        </section>

        <div class="pt-10 border-t border-gray-100 flex justify-center">
          <router-link to="/rooms" class="bg-emerald-800 text-white px-10 py-4 rounded-full font-black uppercase tracking-widest text-sm hover:bg-emerald-900 shadow-xl transition-all">
            Tiếp tục đặt phòng
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
// Static policy page
</script>