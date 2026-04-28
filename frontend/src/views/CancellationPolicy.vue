<template>
  <main class="policy-page min-h-screen bg-gradient-to-b from-gray-50 to-gray-100">
    <!-- Hero Section -->
    <header class="hero-section bg-emerald-950 text-white relative overflow-hidden min-h-[70vh] flex items-center justify-center">
      <div class="hero-background absolute inset-0 opacity-20">
        <img src="https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80" 
             alt="Policy Background" 
             class="w-full h-full object-cover">
      </div>
      <div class="hero-content max-w-7xl mx-auto px-6 relative z-10 text-center py-20">
        <div class="hero-icon mb-6">
          <div class="w-20 h-20 bg-emerald-400/20 rounded-full flex items-center justify-center mx-auto">
            <svg class="w-10 h-10 text-emerald-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"></path>
            </svg>
          </div>
        </div>
        <h1 class="hero-title text-4xl md:text-6xl lg:text-7xl font-black uppercase tracking-tighter mb-6">
          Chính sách <span class="text-emerald-400">Hủy phòng</span>
        </h1>
        <p class="hero-subtitle text-emerald-100 font-light tracking-[0.3em] uppercase text-sm mb-8">
          Thông tin minh bạch về việc hoàn tiền và thay đổi đặt phòng
        </p>
        <div class="hero-description max-w-3xl mx-auto text-emerald-200 text-lg leading-relaxed">
          Chúng tôi cam kết mang đến sự linh hoạt và tiện lợi cho khách hàng với các điều khoản rõ ràng, minh bạch.
        </div>
      </div>
    </header>

    <!-- Policy Content -->
    <section class="policy-content max-w-5xl mx-auto px-6 py-20">
      <div class="policy-container bg-white rounded-[3rem] shadow-2xl overflow-hidden">
        
        <!-- Quick Summary -->
        <div class="quick-summary bg-gradient-to-r from-emerald-50 to-teal-50 p-8 lg:p-12 border-b border-emerald-100">
          <div class="summary-header text-center mb-8">
            <h2 class="summary-title text-2xl lg:text-3xl font-bold text-emerald-950 mb-4">
              Tóm tắt chính sách
            </h2>
            <p class="summary-subtitle text-gray-600 max-w-2xl mx-auto">
              Chính sách hủy phòng linh hoạt, đảm bảo quyền lợi tối đa cho khách hàng
            </p>
          </div>
          <div class="summary-grid grid grid-cols-1 md:grid-cols-3 gap-6">
            <div class="summary-card bg-white rounded-2xl p-6 text-center shadow-lg hover:shadow-xl transition-all">
              <div class="summary-icon w-16 h-16 bg-emerald-100 rounded-full flex items-center justify-center mx-auto mb-4">
                <span class="text-2xl font-bold text-emerald-600">100%</span>
              </div>
              <h3 class="summary-card-title font-bold text-emerald-900 mb-2">Hủy trước 24h</h3>
              <p class="summary-card-text text-sm text-gray-600">Hoàn lại toàn bộ số tiền</p>
            </div>
            <div class="summary-card bg-white rounded-2xl p-6 text-center shadow-lg hover:shadow-xl transition-all">
              <div class="summary-icon w-16 h-16 bg-amber-100 rounded-full flex items-center justify-center mx-auto mb-4">
                <span class="text-2xl font-bold text-amber-600">50%</span>
              </div>
              <h3 class="summary-card-title font-bold text-amber-900 mb-2">Hủy trong 24h</h3>
              <p class="summary-card-text text-sm text-gray-600">Hoàn lại 90% số tiền</p>
            </div>
            <div class="summary-card bg-white rounded-2xl p-6 text-center shadow-lg hover:shadow-xl transition-all">
              <div class="summary-icon w-16 h-16 bg-red-100 rounded-full flex items-center justify-center mx-auto mb-4">
                <span class="text-2xl font-bold text-red-600">0%</span>
              </div>
              <h3 class="summary-card-title font-bold text-red-900 mb-2">No-show/Check-in</h3>
              <p class="summary-card-text text-sm text-gray-600">Không hoàn tiền</p>
            </div>
          </div>
        </div>

        <!-- Detailed Policy -->
        <div class="policy-details p-8 lg:p-12 space-y-12">
          
          <!-- General Rules -->
          <article class="policy-section">
            <div class="section-header mb-8">
              <div class="section-number w-12 h-12 bg-emerald-500 text-white rounded-full flex items-center justify-center font-bold text-lg mb-4">
                1
              </div>
              <h2 class="section-title text-2xl lg:text-3xl font-bold text-emerald-950 mb-4">
                Quy định chung
              </h2>
              <div class="section-divider w-20 h-1 bg-emerald-200 rounded-full"></div>
            </div>
            <div class="section-content space-y-6">
              <p class="text-gray-700 text-lg leading-relaxed">
                Tại Grand Hotel, chúng tôi hiểu rằng kế hoạch của bạn có thể thay đổi. Chính sách hủy phòng của chúng tôi được thiết kế để cân bằng giữa sự linh hoạt cho khách hàng và nhu cầu quản lý vận hành của khách sạn.
              </p>
              <div class="policy-highlights bg-gray-50 rounded-2xl p-6 border border-gray-200">
                <h4 class="font-bold text-emerald-900 mb-4 flex items-center">
                  <svg class="w-5 h-5 mr-2 text-emerald-500" fill="currentColor" viewBox="0 0 20 20">
                    <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"></path>
                  </svg>
                  Các nguyên tắc chính
                </h4>
                <ul class="space-y-2 text-gray-600">
                  <li class="flex items-start">
                    <span class="text-emerald-500 mr-2 mt-1">•</span>
                    <span>Thời gian tính theo giờ địa phương của khách sạn</span>
                  </li>
                  <li class="flex items-start">
                    <span class="text-emerald-500 mr-2 mt-1">•</span>
                    <span>Phí hủy được tính trên tổng giá trị đặt phòng</span>
                  </li>
                  <li class="flex items-start">
                    <span class="text-emerald-500 mr-2 mt-1">•</span>
                    <span>Số tiền hoàn lại sẽ được xử lý trong 3-5 ngày làm việc</span>
                  </li>
                </ul>
              </div>
            </div>
          </article>

          <!-- Cancellation Timeline -->
          <article class="policy-section bg-gradient-to-r from-emerald-50 to-teal-50 rounded-3xl p-8 lg:p-12 border border-emerald-100">
            <div class="section-header mb-8">
              <div class="section-number w-12 h-12 bg-emerald-600 text-white rounded-full flex items-center justify-center font-bold text-lg mb-4">
                2
              </div>
              <h2 class="section-title text-2xl lg:text-3xl font-bold text-emerald-950 mb-4">
                Thời gian và Phí hủy
              </h2>
              <div class="section-divider w-20 h-1 bg-emerald-300 rounded-full"></div>
            </div>
            <div class="timeline-content space-y-8">
              <!-- Timeline Item 1 -->
              <div class="timeline-item">
                <div class="timeline-card bg-white rounded-2xl p-6 shadow-lg hover:shadow-xl transition-all">
                  <div class="timeline-header flex items-start gap-4 mb-4">
                    <div class="timeline-badge w-14 h-14 bg-emerald-500 text-white rounded-full flex items-center justify-center flex-shrink-0">
                      <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                      </svg>
                    </div>
                    <div class="timeline-content flex-1">
                      <h3 class="timeline-title font-bold text-xl text-emerald-900 mb-2">
                        Hủy trước 24 giờ so với ngày Check-in
                      </h3>
                      <div class="timeline-badge-text inline-block bg-emerald-100 text-emerald-700 px-3 py-1 rounded-full text-sm font-semibold mb-3">
                        Ưu đãi tốt nhất
                      </div>
                      <p class="timeline-description text-gray-600 leading-relaxed">
                        Khách hàng được hoàn lại <strong class="text-emerald-600">100%</strong> số tiền đã đặt cọc hoặc thanh toán. 
                        Không áp dụng phí hủy phòng và không có điều khoản nào khác.
                      </p>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Timeline Item 2 -->
              <div class="timeline-item">
                <div class="timeline-card bg-white rounded-2xl p-6 shadow-lg hover:shadow-xl transition-all">
                  <div class="timeline-header flex items-start gap-4 mb-4">
                    <div class="timeline-badge w-14 h-14 bg-amber-500 text-white rounded-full flex items-center justify-center flex-shrink-0">
                      <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                      </svg>
                    </div>
                    <div class="timeline-content flex-1">
                      <h3 class="timeline-title font-bold text-xl text-amber-900 mb-2">
                        Hủy trong vòng 24 giờ trước ngày Check-in
                      </h3>
                      <div class="timeline-badge-text inline-block bg-amber-100 text-amber-700 px-3 py-1 rounded-full text-sm font-semibold mb-3">
                        Phí hủy 10%
                      </div>
                      <p class="timeline-description text-gray-600 leading-relaxed">
                        Khách hàng sẽ được hoàn lại <strong class="text-amber-600">50%</strong> số tiền. 
                        Phí hủy phòng tương đương 10% giá trị đặt phòng để bù đắp chi phí chuẩn bị phòng.
                      </p>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Timeline Item 3 -->
              <div class="timeline-item">
                <div class="timeline-card bg-white rounded-2xl p-6 shadow-lg hover:shadow-xl transition-all">
                  <div class="timeline-header flex items-start gap-4 mb-4">
                    <div class="timeline-badge w-14 h-14 bg-red-500 text-white rounded-full flex items-center justify-center flex-shrink-0">
                      <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                      </svg>
                    </div>
                    <div class="timeline-content flex-1">
                      <h3 class="timeline-title font-bold text-xl text-red-900 mb-2">
                        Sau khi đã Check-in hoặc No-show
                      </h3>
                      <div class="timeline-badge-text inline-block bg-red-100 text-red-700 px-3 py-1 rounded-full text-sm font-semibold mb-3">
                        Không hoàn tiền
                      </div>
                      <p class="timeline-description text-gray-600 leading-relaxed">
                        Không áp dụng hoàn tiền cho các trường hợp đã nhận phòng hoặc khách không đến (no-show) 
                        mà không có thông báo trước.
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </article>

          <!-- How to Cancel -->
          <article class="policy-section">
            <div class="section-header mb-8">
              <div class="section-number w-12 h-12 bg-emerald-500 text-white rounded-full flex items-center justify-center font-bold text-lg mb-4">
                3
              </div>
              <h2 class="section-title text-2xl lg:text-3xl font-bold text-emerald-950 mb-4">
                Cách thức thực hiện
              </h2>
              <div class="section-divider w-20 h-1 bg-emerald-200 rounded-full"></div>
            </div>
            <div class="section-content space-y-6">
              <p class="text-gray-700 text-lg leading-relaxed">
                Bạn có thể thực hiện hủy phòng trực tuyến thông qua mục <strong class="text-emerald-600">"Booking của tôi"</strong> 
                trong tài khoản của bạn. Hệ thống sẽ tự động tính toán số tiền hoàn lại dựa trên thời điểm bạn thực hiện thao tác.
              </p>
              
              <div class="cancellation-steps grid grid-cols-1 md:grid-cols-2 gap-6">
                <div class="step-card bg-emerald-50 rounded-2xl p-6 border border-emerald-100">
                  <div class="step-header flex items-center mb-4">
                    <div class="step-number w-8 h-8 bg-emerald-500 text-white rounded-full flex items-center justify-center font-bold mr-3">
                      1
                    </div>
                    <h4 class="step-title font-bold text-emerald-900">Đăng nhập tài khoản</h4>
                  </div>
                  <p class="step-description text-gray-600 text-sm">
                    Truy cập vào tài khoản của bạn trên website hoặc ứng dụng di động
                  </p>
                </div>
                
                <div class="step-card bg-emerald-50 rounded-2xl p-6 border border-emerald-100">
                  <div class="step-header flex items-center mb-4">
                    <div class="step-number w-8 h-8 bg-emerald-500 text-white rounded-full flex items-center justify-center font-bold mr-3">
                      2
                    </div>
                    <h4 class="step-title font-bold text-emerald-900">Tìm booking cần hủy</h4>
                  </div>
                  <p class="step-description text-gray-600 text-sm">
                    Chọn booking bạn muốn hủy từ danh sách các đặt phòng của bạn
                  </p>
                </div>
                
                <div class="step-card bg-emerald-50 rounded-2xl p-6 border border-emerald-100">
                  <div class="step-header flex items-center mb-4">
                    <div class="step-number w-8 h-8 bg-emerald-500 text-white rounded-full flex items-center justify-center font-bold mr-3">
                      3
                    </div>
                    <h4 class="step-title font-bold text-emerald-900">Xác nhận hủy phòng</h4>
                  </div>
                  <p class="step-description text-gray-600 text-sm">
                    Nhấn nút "Hủy phòng" và xác nhận thao tác
                  </p>
                </div>
                
                <div class="step-card bg-emerald-50 rounded-2xl p-6 border border-emerald-100">
                  <div class="step-header flex items-center mb-4">
                    <div class="step-number w-8 h-8 bg-emerald-500 text-white rounded-full flex items-center justify-center font-bold mr-3">
                      4
                    </div>
                    <h4 class="step-title font-bold text-emerald-900">Nhận xác nhận</h4>
                  </div>
                  <p class="step-description text-gray-600 text-sm">
                    Hệ thống sẽ gửi email xác nhận và thông tin số tiền hoàn lại
                  </p>
                </div>
              </div>
              
              <div class="contact-info bg-gradient-to-r from-blue-50 to-indigo-50 rounded-2xl p-6 border border-blue-100">
                <h4 class="font-bold text-blue-900 mb-4 flex items-center">
                  <svg class="w-5 h-5 mr-2 text-blue-500" fill="currentColor" viewBox="0 0 20 20">
                    <path d="M2 3a1 1 0 011-1h2.153a1 1 0 01.986.836l.74 4.435a1 1 0 01-.54 1.06l-1.548.773a11.037 11.037 0 006.105 6.105l.774-1.548a1 1 0 011.059-.54l4.435.74a1 1 0 01.836.986V17a1 1 0 01-1 1h-2C7.82 18 2 12.18 2 5V3z"></path>
                  </svg>
                  Hỗ trợ khách hàng
                </h4>
                <div class="contact-details grid grid-cols-1 md:grid-cols-2 gap-4 text-gray-600">
                  <div class="contact-item flex items-center">
                    <span class="contact-label font-semibold mr-2">Hotline:</span>
                    <span class="contact-value text-blue-600 font-bold">0123-456-789</span>
                  </div>
                  <div class="contact-item flex items-center">
                    <span class="contact-label font-semibold mr-2">Email:</span>
                    <span class="contact-value text-blue-600 font-bold">support@grandhotel.com</span>
                  </div>
                  <div class="contact-item flex items-center">
                    <span class="contact-label font-semibold mr-2">Thời gian:</span>
                    <span class="contact-value">24/7</span>
                  </div>
                  <div class="contact-item flex items-center">
                    <span class="contact-label font-semibold mr-2">Chat:</span>
                    <span class="contact-value text-blue-600 font-bold">Live Chat</span>
                  </div>
                </div>
              </div>
            </div>
          </article>

          <!-- Special Cases -->
          <article class="policy-section bg-amber-50 rounded-3xl p-8 lg:p-12 border border-amber-100">
            <div class="section-header mb-8">
              <div class="section-number w-12 h-12 bg-amber-500 text-white rounded-full flex items-center justify-center font-bold text-lg mb-4">
                4
              </div>
              <h2 class="section-title text-2xl lg:text-3xl font-bold text-amber-900 mb-4">
                Trường hợp đặc biệt
              </h2>
              <div class="section-divider w-20 h-1 bg-amber-300 rounded-full"></div>
            </div>
            <div class="section-content space-y-6">
              <div class="special-cases grid grid-cols-1 md:grid-cols-2 gap-6">
                <div class="case-card bg-white rounded-2xl p-6 shadow-md">
                  <h4 class="case-title font-bold text-amber-900 mb-3 flex items-center">
                    <svg class="w-5 h-5 mr-2 text-amber-500" fill="currentColor" viewBox="0 0 20 20">
                      <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z" clip-rule="evenodd"></path>
                    </svg>
                    Bắt buộc (Force Majeure)
                  </h4>
                  <p class="case-description text-gray-600 text-sm">
                    Trong trường hợp thiên tai, chiến tranh, dịch bệnh... chúng tôi sẽ xem xét hoàn tiền 100% 
                    hoặc cho phép đổi lịch mà không tính phí.
                  </p>
                </div>
                
                <div class="case-card bg-white rounded-2xl p-6 shadow-md">
                  <h4 class="case-title font-bold text-amber-900 mb-3 flex items-center">
                    <svg class="w-5 h-5 mr-2 text-amber-500" fill="currentColor" viewBox="0 0 20 20">
                      <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z" clip-rule="evenodd"></path>
                    </svg>
                    Y tế khẩn cấp
                  </h4>
                  <p class="case-description text-gray-600 text-sm">
                    Cung cấp giấy tờ y tế hợp lệ, chúng tôi sẽ hỗ trợ đổi lịch hoặc hoàn tiền theo chính sách linh hoạt.
                  </p>
                </div>
              </div>
            </div>
          </article>
        </div>

        <!-- CTA Section -->
        <div class="cta-section bg-gradient-to-r from-emerald-600 to-teal-600 p-8 lg:p-12 text-center">
          <h2 class="cta-title text-2xl lg:text-3xl font-bold text-white mb-4">
            Sẵn sàng đặt phòng?
          </h2>
          <p class="cta-subtitle text-emerald-100 mb-8 max-w-2xl mx-auto">
            Khám phá các loại phòng sang trọng của chúng tôi và tận hưởng dịch vụ đẳng cấp quốc tế
          </p>
          <div class="cta-buttons flex flex-col sm:flex-row gap-4 justify-center">
            <router-link to="/rooms" class="cta-button bg-white text-emerald-700 px-8 py-4 rounded-full font-bold uppercase tracking-wider hover:bg-gray-100 shadow-xl transition-all">
              Xem phòng
            </router-link>
            <router-link to="/contact" class="cta-button-secondary bg-transparent border-2 border-white text-white px-8 py-4 rounded-full font-bold uppercase tracking-wider hover:bg-white hover:text-emerald-700 transition-all">
              Liên hệ tư vấn
            </router-link>
          </div>
        </div>
      </div>
    </section>
  </main>
</template>

<script setup>
// Static policy page
</script>