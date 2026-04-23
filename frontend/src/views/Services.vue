<template>
  <div class="min-h-screen bg-gray-100 pb-10">
    <section class="bg-emerald-950 text-white relative overflow-hidden h-[600px] flex items-center justify-center">
      <div class="absolute inset-0 opacity-10">
        <img src="https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80" class="w-full h-full object-cover">
      </div>
      <div class="max-w-7xl mx-auto px-4 relative z-10 text-center">
        <h1 class="text-5xl md:text-7xl font-black uppercase tracking-tighter mb-6">Dịch vụ <span class="text-emerald-400">Đẳng cấp</span></h1>
        <p class="text-emerald-100 font-light tracking-[0.3em] uppercase text-sm">Khám phá các tiện nghi và dịch vụ độc quyền của chúng tôi</p>
      </div>
    </section>
    <div class="max-w-6xl mx-auto px-4">
      <div class="space-y-16">
        <!-- Service Section 1: Spa & Wellness -->
        <div class="flex flex-col md:flex-row items-center gap-8 bg-white rounded-[2rem] shadow-lg p-8">
          <div class="md:w-1/2">
            <img src="https://images.unsplash.com/photo-1522335789239-5e92911771a6?auto=format&fit=crop&w=800&q=80" alt="Spa & Wellness" class="rounded-2xl shadow-md w-full h-[600px] object-cover">
          </div>
          <div class="md:w-1/2">
            <h3 class="text-2xl font-bold text-emerald-950 mb-4">Spa & Chăm sóc sức khỏe</h3>
            <p class="text-gray-700 mb-4">
              Thư giãn và phục hồi năng lượng tại spa sang trọng của chúng tôi. Với các liệu pháp mát-xa chuyên nghiệp, phòng xông hơi và hồ bơi nước nóng, chúng tôi mang đến trải nghiệm thư giãn tuyệt đối.
            </p>
            <ul class="list-disc list-inside text-gray-600">
              <li>Mát-xa toàn thân và trị liệu đá nóng</li>
              <li>Chăm sóc da mặt và cơ thể</li>
              <li>Phòng xông hơi khô và ướt</li>
              <li>Hồ bơi trong nhà và ngoài trời</li>
            </ul>
          </div>
        </div>

        <!-- Service Section 2: Fine Dining -->
        <div class="flex flex-col md:flex-row-reverse items-center gap-8 bg-white rounded-[2rem] shadow-lg p-8">
          <div class="md:w-1/2">
            <img src="https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?auto=format&fit=crop&w=800&q=80" alt="Fine Dining" class="rounded-2xl shadow-md w-full h-[600px] object-cover">
          </div>
          <div class="md:w-1/2">
            <h3 class="text-2xl font-bold text-emerald-950 mb-4">Ẩm thực tinh hoa</h3>
            <p class="text-gray-700 mb-4">
              Trải nghiệm ẩm thực đẳng cấp thế giới tại các nhà hàng của chúng tôi. Từ các món ăn địa phương truyền thống đến ẩm thực quốc tế, mỗi bữa ăn là một hành trình khám phá hương vị.
            </p>
            <ul class="list-disc list-inside text-gray-600">
              <li>Nhà hàng hải sản tươi sống</li>
              <li>Quán bar trên tầng thượng với tầm nhìn tuyệt đẹp</li>
              <li>Dịch vụ phòng 24/7</li>
              <li>Tiệc buffet sáng phong phú</li>
            </ul>
          </div>
        </div>

        <!-- Service Section 3: Fitness Center -->
        <div class="flex flex-col md:flex-row items-center gap-8 bg-white rounded-[2rem] shadow-lg p-8">
          <div class="md:w-1/2">
            <img src="https://images.unsplash.com/photo-1534438747741-0000f5886279?auto=format&fit=crop&w=800&q=80" alt="Fitness Center" class="rounded-2xl shadow-md w-full h-[600px] object-cover">
          </div>
          <div class="md:w-1/2">
            <h3 class="text-2xl font-bold text-emerald-950 mb-4">Trung tâm thể dục</h3>
            <p class="text-gray-700 mb-4">
              Duy trì thói quen tập luyện của bạn tại trung tâm thể dục hiện đại của chúng tôi. Được trang bị đầy đủ các thiết bị tiên tiến và huấn luyện viên cá nhân theo yêu cầu.
            </p>
            <ul class="list-disc list-inside text-gray-600">
              <li>Máy chạy bộ, xe đạp tập, máy tập tạ</li>
              <li>Lớp học yoga và pilates</li>
              <li>Huấn luyện viên cá nhân</li>
              <li>Nước uống và khăn tắm miễn phí</li>
            </ul>
          </div>
        </div>

        <!-- Service Section 4: Conference & Events -->
        <div class="flex flex-col md:flex-row-reverse items-center gap-8 bg-white rounded-[2rem] shadow-lg p-8">
          <div class="md:w-1/2">
            <img src="https://images.unsplash.com/photo-1556761175-5973dc0f32e7?auto=format&fit=crop&w=800&q=80" alt="Conference & Events" class="rounded-2xl shadow-md w-full h-[600px] object-cover">
          </div>
          <div class="md:w-1/2">
            <h3 class="text-2xl font-bold text-emerald-950 mb-4">Hội nghị & Sự kiện</h3>
            <p class="text-gray-700 mb-4">
              Grand Hotel là địa điểm lý tưởng cho các cuộc họp, hội nghị và sự kiện đặc biệt. Với không gian linh hoạt và đội ngũ tổ chức chuyên nghiệp, chúng tôi đảm bảo mọi sự kiện đều thành công.
            </p>
            <ul class="list-disc list-inside text-gray-600">
              <li>Phòng họp đa chức năng</li>
              <li>Thiết bị nghe nhìn hiện đại</li>
              <li>Dịch vụ phục vụ ăn uống</li>
              <li>Hỗ trợ kỹ thuật chuyên nghiệp</li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
// No specific script needed for this static content page
</script>

<style scoped>
/* Add any specific styles for the services page here */
</style>
