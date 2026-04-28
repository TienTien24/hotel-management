<template>
  <main class="services-page min-h-screen bg-gradient-to-b from-gray-50 to-gray-100">
    <!-- Hero Section -->
    <header class="hero-section bg-emerald-950 text-white relative overflow-hidden min-h-[80vh] flex items-center justify-center">
      <div class="hero-background absolute inset-0 opacity-20">
        <img src="https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80" 
             alt="Hero Background" 
             class="w-full h-full object-cover">
      </div>
      <div class="hero-content max-w-7xl mx-auto px-6 relative z-10 text-center py-20">
        <h1 class="hero-title text-5xl md:text-7xl font-black uppercase tracking-tighter mb-6">
          Dịch vụ <span class="text-emerald-400">Đẳng cấp</span>
        </h1>
        <p class="hero-subtitle text-emerald-100 font-light tracking-[0.3em] uppercase text-sm mb-8">
          Khám phá các tiện nghi và dịch vụ độc quyền của chúng tôi
        </p>
        <div class="hero-description max-w-3xl mx-auto text-emerald-200 text-lg leading-relaxed">
          Chúng tôi không chỉ cung cấp chỗ nghỉ, chúng tôi mang đến trải nghiệm.
        </div>
      </div>
    </header>

    <!-- Services Container -->
    <section class="services-container max-w-7xl mx-auto px-6 py-20">
      <div class="services-grid space-y-24">
        
        <!-- Service Card 1: Nhà hàng & Bar -->
        <article class="service-card bg-white rounded-[3rem] shadow-2xl overflow-hidden hover:shadow-3xl transition-all duration-300">
          <div class="service-content flex flex-col lg:flex-row items-stretch">
            <div class="service-image lg:w-1/2 order-1">
              <img src="https://viphouse.vn/viphouse/images/2017/t7/thiet%20ke%20bar%20khach%20s%E1%BA%A1n/201408-w-best-rooftop-bars-in-nyc-hotel-hugo.jpg" 
                   alt="Nhà hàng & Bar" 
                   class="w-full h-[400px] lg:h-[600px] object-cover">
            </div>
            <div class="service-details lg:w-1/2 order-2 p-10 lg:p-12 flex flex-col justify-center">
              <div class="service-header mb-6">
                <h3 class="service-title text-3xl lg:text-4xl font-bold text-emerald-950 mb-3">
                  Nhà hàng & Bar
                </h3>
                <div class="service-badge bg-emerald-100 text-emerald-700 px-4 py-2 rounded-full text-sm font-semibold w-fit">
                  Ẩm thực tinh hoa
                </div>
              </div>
              <p class="service-description text-gray-700 text-lg leading-relaxed mb-8">
                Trải nghiệm ẩm thực đẳng cấp thế giới tại nhà hàng và bar sang trọng của chúng tôi. 
                Từ món ăn địa phương tinh túy đến ẩm thực quốc tế, mỗi bữa ăn là một hành trình khám phá hương vị độc đáo.
              </p>
              <ul class="service-features space-y-3 text-gray-600">
                <li class="flex items-center">
                  <span class="feature-icon text-emerald-500 mr-3">▸</span>
                  Nhà hàng à la carte với đầu bếp chuyên nghiệp
                </li>
                <li class="flex items-center">
                  <span class="feature-icon text-emerald-500 mr-3">▸</span>
                  Bar trên tầng thượng với tầm nhìn panorama
                </li>
                <li class="flex items-center">
                  <span class="feature-icon text-emerald-500 mr-3">▸</span>
                  Buffet sáng quốc tế phong phú
                </li>
                <li class="flex items-center">
                  <span class="feature-icon text-emerald-500 mr-3">▸</span>
                  Rượu vang và cocktail cao cấp
                </li>
              </ul>
            </div>
          </div>
        </article>

        <!-- Service Card 2: Spa & Wellness -->
        <article class="service-card bg-white rounded-[3rem] shadow-2xl overflow-hidden hover:shadow-3xl transition-all duration-300">
          <div class="service-content flex flex-col lg:flex-row-reverse items-stretch">
            <div class="service-image lg:w-1/2 order-2 lg:order-1">
              <img src="https://vinapad.com/wp-content/uploads/2019/07/cac-goi-dich-vu-spa-1.jpg" 
                   alt="Spa & Wellness" 
                   class="w-full h-[400px] lg:h-[600px] object-cover">
            </div>
            <div class="service-details lg:w-1/2 order-1 lg:order-2 p-10 lg:p-12 flex flex-col justify-center">
              <div class="service-header mb-6">
                <h3 class="service-title text-3xl lg:text-4xl font-bold text-emerald-950 mb-3">
                  Spa & Wellness
                </h3>
                <div class="service-badge bg-purple-100 text-purple-700 px-4 py-2 rounded-full text-sm font-semibold w-fit">
                  Thư giãn & Tái tạo
                </div>
              </div>
              <p class="service-description text-gray-700 text-lg leading-relaxed mb-8">
                Thư giãn và tái tạo năng lượng tại spa sang trọng của chúng tôi. 
                Với các liệu pháp trị liệu chuyên nghiệp và không gian yên tĩnh, chúng tôi mang đến trải nghiệm thư giãn tuyệt đối cho cả thể chất và tinh thần.
              </p>
              <ul class="service-features space-y-3 text-gray-600">
                <li class="flex items-center">
                  <span class="feature-icon text-purple-500 mr-3">▸</span>
                  Mát-xa trị liệu toàn thân và đá nóng
                </li>
                <li class="flex items-center">
                  <span class="feature-icon text-purple-500 mr-3">▸</span>
                  Chăm sóc da mặt chuyên sâu
                </li>
                <li class="flex items-center">
                  <span class="feature-icon text-purple-500 mr-3">▸</span>
                  Phòng xông hơi và sauna
                </li>
                <li class="flex items-center">
                  <span class="feature-icon text-purple-500 mr-3">▸</span>
                  Liệu pháp aromatherapy tự nhiên
                </li>
              </ul>
            </div>
          </div>
        </article>

        <!-- Service Card 3: Hồ bơi vô cực -->
        <article class="service-card bg-white rounded-[3rem] shadow-2xl overflow-hidden hover:shadow-3xl transition-all duration-300">
          <div class="service-content flex flex-col lg:flex-row items-stretch">
            <div class="service-image lg:w-1/2 order-1">
              <img src="https://danang-shopping.com/wp-content/uploads/2017/05/khach-san-da-nang-co-ho-boi-dep-diamond-sea-hotel.jpg" 
                   alt="Hồ bơi vô cực" 
                   class="w-full h-[400px] lg:h-[600px] object-cover">
            </div>
            <div class="service-details lg:w-1/2 order-2 p-10 lg:p-12 flex flex-col justify-center">
              <div class="service-header mb-6">
                <h3 class="service-title text-3xl lg:text-4xl font-bold text-emerald-950 mb-3">
                  Hồ bơi vô cực
                </h3>
                <div class="service-badge bg-blue-100 text-blue-700 px-4 py-2 rounded-full text-sm font-semibold w-fit">
                  Tiện nghi đẳng cấp
                </div>
              </div>
              <p class="service-description text-gray-700 text-lg leading-relaxed mb-8">
                Tận hưởng trải nghiệm bơi lội đỉnh cao tại hồ bơi vô cực của chúng tôi. 
                Với tầm nhìn bao quát thành phố và thiết kế hiện đại, đây là nơi lý tưởng để thư giãn và tận hưởng không gian sang trọng.
              </p>
              <ul class="service-features space-y-3 text-gray-600">
                <li class="flex items-center">
                  <span class="feature-icon text-blue-500 mr-3">▸</span>
                  Hồ bơi vô cực với tầm nhìn panorama
                </li>
                <li class="flex items-center">
                  <span class="feature-icon text-blue-500 mr-3">▸</span>
                  Quầy bar phục vụ tại hồ bơi
                </li>
                <li class="flex items-center">
                  <span class="feature-icon text-blue-500 mr-3">▸</span>
                  Ghế nghỉ dưỡng và ô dù
                </li>
                <li class="flex items-center">
                  <span class="feature-icon text-blue-500 mr-3">▸</span>
                  Nước nóng và jacuzzi
                </li>
              </ul>
            </div>
          </div>
        </article>

        <!-- Service Card 4: Đưa đón sân bay -->
        <article class="service-card bg-white rounded-[3rem] shadow-2xl overflow-hidden hover:shadow-3xl transition-all duration-300">
          <div class="service-content flex flex-col lg:flex-row-reverse items-stretch">
            <div class="service-image lg:w-1/2 order-2 lg:order-1">
              <img src="https://xedulichngochieu.com/images/img-ckfinder/images/dich-vu-xe-dua-don-tai-san-bay-o-hai-phong.jpg" 
                   alt="Đưa đón sân bay" 
                   class="w-full h-[400px] lg:h-[600px] object-cover">
            </div>
            <div class="service-details lg:w-1/2 order-1 lg:order-2 p-10 lg:p-12 flex flex-col justify-center">
              <div class="service-header mb-6">
                <h3 class="service-title text-3xl lg:text-4xl font-bold text-emerald-950 mb-3">
                  Đưa đón sân bay
                </h3>
                <div class="service-badge bg-amber-100 text-amber-700 px-4 py-2 rounded-full text-sm font-semibold w-fit">
                  Tiện nghi & Thuận tiện
                </div>
              </div>
              <p class="service-description text-gray-700 text-lg leading-relaxed mb-8">
                Dịch vụ đưa đón sân bay chuyên nghiệp và tiện lợi. 
                Đội ngũ lái xe thân thiện sẽ đón bạn tại sân bay và đưa về khách hàng một cách an toàn, nhanh chóng và thoải mái.
              </p>
              <ul class="service-features space-y-3 text-gray-600">
                <li class="flex items-center">
                  <span class="feature-icon text-amber-500 mr-3">▸</span>
                  Xe sang trọng hiện đại
                </li>
                <li class="flex items-center">
                  <span class="feature-icon text-amber-500 mr-3">▸</span>
                  Lái xe chuyên nghiệp, nói được tiếng Anh
                </li>
                <li class="flex items-center">
                  <span class="feature-icon text-amber-500 mr-3">▸</span>
                  Hỗ trợ hành lý 24/7
                </li>
                <li class="flex items-center">
                  <span class="feature-icon text-amber-500 mr-3">▸</span>
                  Theo dõi chuyến bay trực tuyến
                </li>
              </ul>
            </div>
          </div>
        </article>
      </div>
    </section>
  </main>
</template>

<script setup>
// No specific script needed for this static content page
</script>

<style scoped>
/* Add any specific styles for the services page here */
</style>
