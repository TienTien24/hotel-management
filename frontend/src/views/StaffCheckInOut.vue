<template>
  <div class="min-h-screen flex bg-[#f8faff] font-sans text-[#2d3748]">
    <StaffSidebar />

    <main class="flex-1 p-8 overflow-y-auto">
      <!-- Header -->
      <header class="flex items-center justify-between mb-8">
        <div>
          <h2 class="text-3xl font-bold text-[#1a202c]">Check-in / Check-out</h2>
        </div>
        
        <div class="flex items-center gap-4">
          <!-- Notification Bell -->
          <div class="relative group">
            <button @click="toggleNotifications" class="w-10 h-10 flex items-center justify-center rounded-full bg-white shadow-sm border border-gray-100 hover:bg-gray-50 transition-all relative">
              <i class="far fa-bell text-gray-500 text-lg"></i>
              <span v-if="unreadNotificationsCount > 0" class="absolute -top-1 -right-1 min-w-[1.25rem] h-5 bg-rose-500 text-white text-[10px] font-bold rounded-full flex items-center justify-center border-2 border-white px-1 animate-pulse">
                {{ unreadNotificationsCount }}
              </span>
            </button>
            
            <!-- Notification Dropdown -->
            <div v-if="showNotifications" class="absolute right-0 mt-3 w-80 bg-white rounded-2xl shadow-xl border border-gray-100 z-[60] overflow-hidden">
              <div class="p-4 border-b border-gray-50 flex items-center justify-between bg-gray-50 bg-opacity-50">
                <span class="font-bold text-sm text-gray-800">Thông báo</span>
                <button @click="markAllAsRead" class="text-[10px] font-bold text-blue-600 hover:underline">Đánh dấu đã đọc</button>
              </div>
              <div class="max-h-96 overflow-y-auto">
                <div v-for="(noti, index) in notifications" :key="index" 
                  class="p-4 border-b border-gray-50 hover:bg-gray-50 transition-all cursor-pointer flex gap-3"
                  :class="{'bg-blue-50': !noti.read}"
                >
                  <div class="w-8 h-8 rounded-full bg-blue-100 flex items-center justify-center shrink-0">
                    <span class="text-sm">🔔</span>
                  </div>
                  <div>
                    <p class="text-xs font-medium text-gray-800 leading-relaxed">{{ noti.message }}</p>
                    <span class="text-[10px] text-gray-400 mt-1 block">{{ noti.time }}</span>
                  </div>
                </div>
              </div>
              <div class="p-3 text-center border-t border-gray-50">
                <button class="text-[11px] font-bold text-gray-500 hover:text-gray-700">Xem tất cả thông báo</button>
              </div>
            </div>
          </div>
        </div>
      </header>

      <!-- Tabs -->
      <div class="flex items-center gap-12 mb-8 border-b border-gray-200 px-2">
        <button @click="activeTab = 'checkin'" 
          class="pb-4 text-base font-bold transition-all relative flex items-center gap-2"
          :class="activeTab === 'checkin' ? 'text-[#3182ce]' : 'text-gray-400 hover:text-gray-600'">
          <i class="far fa-calendar-check"></i>
          Check-in
          <div v-if="activeTab === 'checkin'" class="absolute bottom-0 left-0 w-full h-1 bg-[#3182ce] rounded-full"></div>
        </button>
        <button @click="activeTab = 'checkout'" 
          class="pb-4 text-base font-bold transition-all relative flex items-center gap-2"
          :class="activeTab === 'checkout' ? 'text-[#3182ce]' : 'text-gray-400 hover:text-gray-600'">
          <i class="fas fa-briefcase"></i>
          Check-out
          <div v-if="activeTab === 'checkout'" class="absolute bottom-0 left-0 w-full h-1 bg-[#3182ce] rounded-full"></div>
        </button>
      </div>

      <!-- Search and Filters -->
      <div class="flex flex-wrap gap-4 mb-8 items-center">
        <div class="relative flex-1 min-w-[300px]">
          <i class="fas fa-search absolute left-4 top-1/2 -translate-y-1/2 text-gray-400 text-sm"></i>
          <input type="text" v-model="filters.search" placeholder="Tìm mã đặt phòng, tên khách, SĐT..." 
            class="w-full bg-white border border-gray-200 rounded-xl pl-11 pr-4 py-3 outline-none focus:border-blue-400 transition-all text-sm font-medium shadow-sm">
        </div>
        
        <select v-model="filters.status" class="bg-white border border-gray-200 rounded-xl px-4 py-3 outline-none focus:border-blue-400 transition-all text-sm font-bold shadow-sm min-w-[150px]">
          <option value="">Tất cả trạng thái</option>
          <template v-if="activeTab === 'checkin'">
            <option value="CONFIRMED">Đã đặt (Chờ đến)</option>
            <option value="CHECKED_IN">Đã nhận phòng</option>
          </template>
          <template v-else>
            <option value="CHECKED_IN">Đang lưu trú</option>
            <option value="COMPLETED">Đã check-out</option>
          </template>
        </select>

        <select v-model="filters.roomType" class="bg-white border border-gray-200 rounded-xl px-4 py-3 outline-none focus:border-blue-400 transition-all text-sm font-bold shadow-sm min-w-[150px]">
          <option value="">Tất cả phòng</option>
          <option v-for="type in roomTypes" :key="type" :value="type">{{ type }}</option>
        </select>

        <div class="relative">
          <input type="date" v-model="filters.date" class="bg-white border border-gray-200 rounded-xl px-4 py-3 outline-none focus:border-blue-400 transition-all text-sm font-bold shadow-sm">
        </div>

        <button @click="fetchBookings" class="bg-[#3182ce] hover:bg-[#2b6cb0] text-white px-8 py-3 rounded-xl font-bold text-sm transition-all shadow-lg shadow-blue-100">
          Tìm kiếm
        </button>
      </div>

      <!-- Stats Cards -->
      <div v-if="activeTab === 'checkin'" class="grid grid-cols-1 md:grid-cols-5 gap-6 mb-8">
        <div class="bg-white p-6 rounded-2xl border border-gray-100 shadow-sm flex items-center gap-4">
          <div class="w-12 h-12 rounded-xl bg-blue-50 flex items-center justify-center text-blue-500">
            <i class="fas fa-user-clock text-xl"></i>
          </div>
          <div>
            <p class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Đã đặt</p>
            <p class="text-xs font-bold text-blue-600 mt-0.5">Chờ đến</p>
            <p class="text-2xl font-bold text-gray-800 mt-1">{{ stats.pendingCheckin }} <span class="text-xs font-medium text-gray-400">khách</span></p>
          </div>
        </div>
        <div class="bg-white p-6 rounded-2xl border border-gray-100 shadow-sm flex items-center gap-4">
          <div class="w-12 h-12 rounded-xl bg-emerald-50 flex items-center justify-center text-emerald-500">
            <i class="fas fa-user-check text-xl"></i>
          </div>
          <div>
            <p class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Đã nhận phòng</p>
            <p class="text-xs font-bold text-emerald-600 mt-0.5">(Trong ngày)</p>
            <p class="text-2xl font-bold text-gray-800 mt-1">{{ stats.checkedInToday }} <span class="text-xs font-medium text-gray-400">khách</span></p>
          </div>
        </div>
        <div class="bg-white p-6 rounded-2xl border border-gray-100 shadow-sm flex items-center gap-4">
          <div class="w-12 h-12 rounded-xl bg-amber-50 flex items-center justify-center text-amber-500">
            <i class="fas fa-sign-out-alt text-xl"></i>
          </div>
          <div>
            <p class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Sắp check-out</p>
            <p class="text-xs font-bold text-amber-600 mt-0.5">(Hôm nay)</p>
            <p class="text-2xl font-bold text-gray-800 mt-1">{{ stats.dueCheckout }} <span class="text-xs font-medium text-gray-400">khách</span></p>
          </div>
        </div>
        <div class="bg-white p-6 rounded-2xl border border-gray-100 shadow-sm flex items-center gap-4">
          <div class="w-12 h-12 rounded-xl bg-rose-50 flex items-center justify-center text-rose-500">
            <i class="fas fa-clock text-xl"></i>
          </div>
          <div>
            <p class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Quá hạn check-out</p>
            <p class="text-xs font-bold text-rose-600 mt-0.5">out</p>
            <p class="text-2xl font-bold text-gray-800 mt-1">{{ stats.overdueCheckout }} <span class="text-xs font-medium text-gray-400">khách</span></p>
          </div>
        </div>
        <div class="bg-white p-6 rounded-2xl border border-gray-100 shadow-sm flex items-center gap-4">
          <div class="w-12 h-12 rounded-xl bg-purple-50 flex items-center justify-center text-purple-500">
            <i class="fas fa-exclamation-triangle text-xl"></i>
          </div>
          <div>
            <p class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Đã check-out</p>
            <p class="text-xs font-bold text-purple-600 mt-0.5">(Hôm nay)</p>
            <p class="text-2xl font-bold text-gray-800 mt-1">{{ stats.checkedOutToday }} <span class="text-xs font-medium text-gray-400">khách</span></p>
          </div>
        </div>
      </div>

      <!-- Stats Cards Checkout Tab -->
      <div v-else class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
        <div class="bg-white p-6 rounded-2xl border border-gray-100 shadow-sm flex items-center gap-4">
          <div class="w-12 h-12 rounded-xl bg-blue-50 flex items-center justify-center text-blue-500">
            <i class="fas fa-user-clock text-xl"></i>
          </div>
          <div>
            <p class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Sắp check-out</p>
            <p class="text-xs font-bold text-blue-600 mt-0.5">(Hôm nay)</p>
            <p class="text-2xl font-bold text-gray-800 mt-1">{{ stats.dueCheckout }} <span class="text-xs font-medium text-gray-400">khách</span></p>
          </div>
        </div>
        <div class="bg-white p-6 rounded-2xl border border-gray-100 shadow-sm flex items-center gap-4">
          <div class="w-12 h-12 rounded-xl bg-emerald-50 flex items-center justify-center text-emerald-500">
            <i class="fas fa-user-check text-xl"></i>
          </div>
          <div>
            <p class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Đã check-out</p>
            <p class="text-xs font-bold text-emerald-600 mt-0.5">(Hôm nay)</p>
            <p class="text-2xl font-bold text-gray-800 mt-1">{{ stats.checkedOutToday }} <span class="text-xs font-medium text-gray-400">khách</span></p>
          </div>
        </div>
        <div class="bg-white p-6 rounded-2xl border border-gray-100 shadow-sm flex items-center gap-4">
          <div class="w-12 h-12 rounded-xl bg-amber-50 flex items-center justify-center text-amber-500">
            <i class="fas fa-clock text-xl"></i>
          </div>
          <div>
            <p class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Quá hạn check-out</p>
            <p class="text-xs font-bold text-amber-600 mt-0.5">(Quá giờ)</p>
            <p class="text-2xl font-bold text-gray-800 mt-1">{{ stats.overdueCheckout }} <span class="text-xs font-medium text-gray-400">khách</span></p>
          </div>
        </div>
        <div class="bg-white p-6 rounded-2xl border border-gray-100 shadow-sm flex items-center gap-4">
          <div class="w-12 h-12 rounded-xl bg-purple-50 flex items-center justify-center text-purple-500">
            <i class="fas fa-calendar-alt text-xl"></i>
          </div>
          <div>
            <p class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Đặt check-out</p>
            <p class="text-xs font-bold text-purple-600 mt-0.5">(Hôm nay)</p>
            <p class="text-2xl font-bold text-gray-800 mt-1">1 <span class="text-xs font-medium text-gray-400">khách</span></p>
          </div>
        </div>
      </div>

      <!-- Bookings Table -->
      <div class="bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden">
        <div class="overflow-x-auto">
          <table class="w-full text-left">
            <thead>
              <tr class="bg-gray-50 bg-opacity-50 border-b border-gray-100">
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider">Mã đặt phòng</th>
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider">Khách hàng</th>
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider">Phòng</th>
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider">Check-in</th>
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider">Check-out</th>
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider text-center">Trạng thái</th>
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider text-center">Thao tác</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-gray-50">
              <tr v-for="booking in filteredBookings" :key="booking.id" class="hover:bg-gray-50 hover:bg-opacity-30 transition-colors">
                <td class="px-6 py-5">
                  <span @click="openDetail(booking)" class="text-blue-600 font-bold cursor-pointer hover:underline">BK{{ String(booking.id).padStart(8, '0') }}</span>
                </td>
                <td class="px-6 py-5">
                  <div class="flex flex-col">
                    <span class="text-sm font-bold text-gray-800">{{ booking.guestFullName || booking.customer?.fullName }}</span>
                    <span class="text-[11px] text-gray-400 font-medium mt-0.5">{{ booking.guestPhone || booking.customer?.phone }}</span>
                  </div>
                </td>
                <td class="px-6 py-5">
                  <div class="flex flex-col">
                    <span class="text-sm font-bold text-gray-800">{{ booking.room?.roomNumber }}</span>
                    <span class="text-[10px] text-gray-400 font-medium">{{ booking.room?.category }}</span>
                  </div>
                </td>
                <td class="px-6 py-5">
                  <div class="flex flex-col">
                    <span class="text-sm font-medium text-gray-800">{{ formatDate(booking.checkInDate) }}</span>
                    <span class="text-[10px] text-gray-400 font-medium">14:00</span>
                  </div>
                </td>
                <td class="px-6 py-5">
                  <div class="flex flex-col">
                    <span class="text-sm font-medium text-gray-800">{{ formatDate(booking.checkOutDate) }}</span>
                    <span class="text-[10px] text-gray-400 font-medium">12:00</span>
                  </div>
                </td>
                <td class="px-6 py-5 text-center">
                  <span :class="getStatusStyles(booking.status)" class="px-3 py-1.5 rounded-lg text-[10px] font-bold inline-block min-w-[100px]">
                    {{ formatStatus(booking.status) }}
                  </span>
                </td>
                <td class="px-6 py-5">
                  <div class="flex items-center justify-center gap-2">
                    <button @click="openDetail(booking)" class="w-8 h-8 rounded-lg bg-blue-50 text-blue-500 hover:bg-blue-500 hover:text-white transition-all flex items-center justify-center border border-blue-100" title="Xem chi tiết">
                      <i class="far fa-eye text-xs"></i>
                    </button>
                    <button @click="openVoucher(booking)" class="w-8 h-8 rounded-lg bg-gray-50 text-gray-500 hover:bg-gray-800 hover:text-white transition-all flex items-center justify-center border border-gray-100" title="Phiếu đặt phòng">
                      <i class="far fa-file-alt text-xs"></i>
                    </button>
                    <button v-if="booking.status === 'CONFIRMED' || booking.status === 'PENDING'" @click="handleCheckIn(booking)" 
                      class="px-4 py-1.5 rounded-lg bg-indigo-600 text-white hover:bg-indigo-700 transition-all text-[10px] font-black uppercase tracking-widest shadow-md shadow-indigo-100">
                      Check-in
                    </button>
                    <button v-if="booking.status === 'CHECKED_IN'" @click="handleCheckOut(booking)" 
                      class="px-4 py-1.5 rounded-lg bg-emerald-50 text-emerald-600 hover:bg-emerald-600 hover:text-white transition-all text-[10px] font-bold border border-emerald-100">
                      Check-out
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <!-- Pagination -->
        <div class="p-6 border-t border-gray-50 flex items-center justify-between">
          <p class="text-xs text-gray-400 font-medium">Hiển thị 1 - {{ filteredBookings.length }} trong {{ filteredBookings.length }} kết quả</p>
          <div class="flex items-center gap-2">
            <button class="w-8 h-8 rounded-lg border border-gray-100 flex items-center justify-center text-gray-400 hover:bg-gray-50 transition-all">
              <i class="fas fa-chevron-left text-[10px]"></i>
            </button>
            <button v-for="p in [1, 2, 3, 4, 5]" :key="p" class="w-8 h-8 rounded-lg font-bold text-xs transition-all"
              :class="p === 1 ? 'bg-[#3182ce] text-white' : 'text-gray-400 hover:bg-gray-50'">
              {{ p }}
            </button>
            <button class="w-8 h-8 rounded-lg border border-gray-100 flex items-center justify-center text-gray-400 hover:bg-gray-50 transition-all">
              <i class="fas fa-chevron-right text-[10px]"></i>
            </button>
          </div>
        </div>
      </div>
    </main>

    <!-- Modal Check-in UI (Matching User's Image) -->
    <div v-if="showCheckInModal" class="fixed inset-0 z-[100] flex items-center justify-center p-6 bg-slate-900 bg-opacity-40 backdrop-blur-sm overflow-y-auto font-sans">
      <div class="bg-white rounded-[2.5rem] max-w-lg w-full shadow-2xl animate-in fade-in zoom-in duration-300 my-auto overflow-hidden border border-gray-100">
        <!-- Header (Blue/Indigo like the image) -->
        <div class="bg-indigo-600 p-8 text-white relative">
          <button @click="showCheckInModal = false" class="absolute top-6 right-6 w-10 h-10 flex items-center justify-center rounded-xl bg-white bg-opacity-10 hover:bg-white hover:bg-opacity-20 transition-all">
            <i class="fas fa-times"></i>
          </button>
          <h3 class="text-3xl font-black uppercase tracking-tight">Thủ tục Check-in</h3>
          <p class="text-white text-opacity-70 text-sm font-medium mt-1">Lưu trữ hồ sơ khách hàng (CCCD/Passport)</p>
        </div>

        <div class="p-8 space-y-8">
          <!-- Xác nhận thông tin khách -->
          <div class="bg-gray-50 rounded-3xl p-6 space-y-4">
            <h5 class="text-[10px] font-black text-gray-400 uppercase tracking-widest">Xác nhận thông tin khách</h5>
            <div class="space-y-3">
              <div class="flex justify-between items-center">
                <span class="text-sm font-medium text-gray-500">Tên khách</span>
                <span class="text-sm font-black text-gray-800">{{ selectedBooking?.guestFullName || selectedBooking?.customer?.fullName }}</span>
              </div>
              <div class="flex justify-between items-center">
                <span class="text-sm font-medium text-gray-500">Số người</span>
                <span class="text-sm font-black text-gray-800">2 người</span>
              </div>
              <div class="flex justify-between items-center">
                <span class="text-sm font-medium text-gray-500">Phòng</span>
                <span class="text-sm font-black text-gray-800">P.{{ selectedBooking?.room?.roomNumber }} - {{ selectedBooking?.room?.category }}</span>
              </div>
            </div>
          </div>

          <!-- Số CCCD / Passport -->
          <div class="space-y-3">
            <label class="text-[10px] font-black text-gray-400 uppercase tracking-widest ml-1">Số CCCD / Passport *</label>
            <input v-model="checkInForm.guestIdNumber" type="text" placeholder="Nhập số định danh..." 
              class="w-full bg-gray-50 border border-gray-100 rounded-2xl px-6 py-4 outline-none focus:border-indigo-400 transition-all text-sm font-bold shadow-sm">
          </div>

          <!-- Hình ảnh giấy tờ -->
          <div class="space-y-4">
            <h5 class="text-[10px] font-black text-gray-400 uppercase tracking-widest ml-1">Hình ảnh giấy tờ (Không bắt buộc)</h5>
            
            <div @click="triggerFileInput" class="border-2 border-dashed border-gray-100 rounded-[2rem] p-10 flex flex-col items-center justify-center gap-4 bg-gray-50 bg-opacity-50 group hover:border-indigo-200 transition-all cursor-pointer overflow-hidden relative">
              <img v-if="idCardPreview" :src="idCardPreview" class="absolute inset-0 w-full h-full object-cover">
              <div v-else class="flex flex-col items-center gap-4">
                <div class="w-16 h-16 bg-white rounded-2xl flex items-center justify-center text-gray-300 group-hover:text-indigo-400 shadow-sm transition-all">
                  <i class="far fa-id-card text-3xl"></i>
                </div>
                <span class="text-xs font-bold text-gray-400 group-hover:text-indigo-500 transition-all uppercase tracking-widest">Chọn cách thêm ảnh</span>
              </div>
            </div>

            <input type="file" ref="fileInput" @change="onFileChange" accept="image/*" class="hidden">

            <div class="grid grid-cols-2 gap-4">
              <button @click="triggerFileInput" type="button" class="flex items-center justify-center gap-3 py-4 bg-gray-50 hover:bg-gray-100 text-gray-600 rounded-2xl font-black text-[10px] uppercase tracking-widest transition-all">
                <i class="fas fa-upload text-sm"></i>
                Upload ảnh
              </button>
              <button @click="startCamera" type="button" class="flex items-center justify-center gap-3 py-4 bg-gray-50 hover:bg-gray-100 text-gray-600 rounded-2xl font-black text-[10px] uppercase tracking-widest transition-all">
                <i class="fas fa-camera text-sm"></i>
                Chụp ảnh
              </button>
            </div>
          </div>

          <!-- Camera Modal (Hidden by default) -->
          <div v-if="showCamera" class="fixed inset-0 z-[120] flex items-center justify-center bg-black bg-opacity-90 p-6">
            <div class="max-w-md w-full space-y-4">
              <video ref="video" autoplay playsinline class="w-full rounded-2xl shadow-2xl"></video>
              <div class="flex gap-4">
                <button @click="capturePhoto" class="flex-1 bg-white text-gray-900 py-4 rounded-xl font-bold">Chụp ảnh</button>
                <button @click="stopCamera" class="flex-1 bg-gray-800 text-white py-4 rounded-xl font-bold">Hủy</button>
              </div>
            </div>
          </div>

          <!-- Footer Action -->
          <button @click="submitCheckIn" class="w-full bg-indigo-600 hover:bg-indigo-700 text-white py-5 rounded-[1.5rem] font-black uppercase tracking-[0.2em] text-xs transition-all shadow-xl shadow-indigo-100 mt-4">
            Hoàn tất nhận phòng
          </button>
        </div>
      </div>
    </div>

    <!-- Modal Chi tiết đặt phòng -->
    <div v-if="showDetailModal" class="fixed inset-0 z-[100] flex items-center justify-center p-6 bg-slate-900 bg-opacity-40 backdrop-blur-sm overflow-y-auto font-sans">
      <div class="bg-white rounded-2xl max-w-md w-full shadow-2xl animate-in fade-in zoom-in duration-300 my-auto border border-gray-100 overflow-hidden">
        <div class="p-6 border-b border-gray-100 flex items-center justify-between sticky top-0 bg-white z-10">
          <div class="flex items-center gap-3">
            <h3 class="text-lg font-bold text-gray-800">Chi tiết đặt phòng</h3>
            <span :class="getStatusStyles(selectedBooking?.status)" class="px-2 py-0.5 rounded-md text-[9px] font-bold">
              {{ formatStatus(selectedBooking?.status) }}
            </span>
          </div>
          <button @click="showDetailModal = false" class="w-8 h-8 flex items-center justify-center rounded-lg hover:bg-gray-100 transition-all text-gray-400">
            <i class="fas fa-times"></i>
          </button>
        </div>

        <div class="p-8 space-y-8 max-h-[80vh] overflow-y-auto custom-scrollbar">
          <!-- Header Info -->
          <div class="flex items-center gap-5">
            <div class="w-14 h-14 bg-blue-50 rounded-xl flex items-center justify-center border border-blue-100 shadow-sm">
              <i class="far fa-calendar-alt text-blue-500 text-2xl"></i>
            </div>
            <div>
              <h4 class="text-xl font-bold text-blue-600 leading-none">BK{{ String(selectedBooking?.id).padStart(8, '0') }}</h4>
              <p class="text-[10px] text-gray-400 font-bold mt-2 uppercase tracking-wider">Ngày đặt: 20/05/2025 - 10:30 AM</p>
              <p class="text-[10px] text-gray-400 font-bold mt-1 uppercase tracking-wider">Lễ tân: {{ staffName }}</p>
            </div>
          </div>

          <!-- Thông tin khách hàng -->
          <div class="space-y-4">
            <div class="flex items-center justify-between">
              <h5 class="text-[11px] font-bold text-gray-400 uppercase tracking-widest border-l-4 border-blue-500 pl-3">Thông tin khách hàng</h5>
              <button class="text-blue-500 font-bold text-[10px] hover:underline px-3 py-1 bg-blue-50 rounded-lg">Sửa</button>
            </div>
            <div class="grid grid-cols-2 gap-x-4 gap-y-3 px-1">
              <div class="space-y-1">
                <span class="text-[9px] font-bold text-gray-400 uppercase">Họ tên</span>
                <p class="text-xs font-bold text-gray-800">{{ selectedBooking?.guestFullName || selectedBooking?.customer?.fullName }}</p>
              </div>
              <div class="space-y-1">
                <span class="text-[9px] font-bold text-gray-400 uppercase">SĐT</span>
                <p class="text-xs font-bold text-gray-800">{{ selectedBooking?.guestPhone || selectedBooking?.customer?.phone }}</p>
              </div>
              <div class="space-y-1">
                <span class="text-[9px] font-bold text-gray-400 uppercase">Email</span>
                <p class="text-xs font-bold text-gray-800">{{ selectedBooking?.guestEmail || selectedBooking?.customer?.email || '--' }}</p>
              </div>
              <div class="space-y-1">
                <span class="text-[9px] font-bold text-gray-400 uppercase">CMND/CCCD</span>
                <p class="text-xs font-bold text-gray-800">{{ selectedBooking?.guestIdNumber || '034567890123' }}</p>
              </div>
              <div class="space-y-1">
                <span class="text-[9px] font-bold text-gray-400 uppercase">Quốc tịch</span>
                <p class="text-xs font-bold text-gray-800">Việt Nam</p>
              </div>
              <div class="space-y-1">
                <span class="text-[9px] font-bold text-gray-400 uppercase">Địa chỉ</span>
                <p class="text-xs font-bold text-gray-800">123 Đường ABC, Quận 1, TP. HCM</p>
              </div>
              <div class="col-span-2 space-y-1">
                <span class="text-[9px] font-bold text-gray-400 uppercase">Ghi chú</span>
                <p class="text-xs font-bold text-gray-800">Khách VIP - Yêu cầu phòng yên tĩnh</p>
              </div>
            </div>
          </div>

          <!-- Thông tin phòng -->
          <div class="space-y-4">
            <h5 class="text-[11px] font-bold text-gray-400 uppercase tracking-widest border-l-4 border-blue-500 pl-3">Thông tin phòng</h5>
            <div class="space-y-3 px-1">
              <div class="flex justify-between items-center text-xs border-b border-gray-50 pb-2">
                <span class="text-gray-400 font-medium">Phòng</span>
                <span class="font-bold text-gray-800">{{ selectedBooking?.room?.roomNumber }} - {{ selectedBooking?.room?.category }}</span>
              </div>
              <div class="flex justify-between items-center text-xs border-b border-gray-50 pb-2">
                <span class="text-gray-400 font-medium">Loại giường</span>
                <span class="font-bold text-gray-800">1 giường đôi</span>
              </div>
              <div class="flex justify-between items-center text-xs border-b border-gray-50 pb-2">
                <span class="text-gray-400 font-medium">Số khách</span>
                <span class="font-bold text-gray-800">2 người lớn, 0 trẻ em</span>
              </div>
              <div class="flex justify-between items-center text-xs border-b border-gray-50 pb-2">
                <span class="text-gray-400 font-medium">Giá phòng / đêm</span>
                <span class="font-bold text-gray-800">1.200.000 đ</span>
              </div>
              <div class="flex justify-between items-center text-xs border-b border-gray-50 pb-2">
                <span class="text-gray-400 font-medium">Check-in</span>
                <span class="font-bold text-gray-800">{{ formatDate(selectedBooking?.checkInDate) }} (14:00)</span>
              </div>
              <div class="flex justify-between items-center text-xs border-b border-gray-50 pb-2">
                <span class="text-gray-400 font-medium">Check-out</span>
                <span class="font-bold text-gray-800">{{ formatDate(selectedBooking?.checkOutDate) }} (12:00)</span>
              </div>
              <div class="flex justify-between items-center text-xs">
                <span class="text-gray-800 font-bold uppercase tracking-wider">Tổng tiền phòng</span>
                <span class="font-black text-blue-600 text-sm">1.200.000 đ</span>
              </div>
            </div>
          </div>

          <!-- Trạng thái & Lưu trú -->
          <div class="space-y-4">
            <h5 class="text-[11px] font-bold text-gray-400 uppercase tracking-widest border-l-4 border-blue-500 pl-3">Trạng thái & Lưu trú</h5>
            <div class="space-y-3 px-1">
              <div class="flex justify-between items-center text-xs border-b border-gray-50 pb-2">
                <span class="text-gray-400 font-medium">Trạng thái</span>
                <span class="px-2 py-0.5 bg-emerald-50 text-emerald-600 rounded font-bold text-[10px]">Đã nhận phòng</span>
              </div>
              <div class="flex justify-between items-center text-xs border-b border-gray-50 pb-2">
                <span class="text-gray-400 font-medium">Nhận phòng lúc</span>
                <span class="font-bold text-gray-800">24/05/2025 - 14:10</span>
              </div>
              <div class="flex justify-between items-center text-xs">
                <span class="text-gray-400 font-medium">Số đêm</span>
                <span class="font-bold text-gray-800">1 đêm</span>
              </div>
            </div>
          </div>
        </div>

        <div class="p-6 border-t border-gray-100 bg-gray-50 bg-opacity-50 grid grid-cols-3 gap-3 sticky bottom-0 z-10">
          <button @click="openVoucher(selectedBooking)" class="flex flex-col items-center justify-center gap-1 bg-white border border-gray-200 py-2.5 rounded-xl hover:bg-blue-50 hover:border-blue-200 transition-all group">
            <i class="fas fa-print text-gray-400 group-hover:text-blue-500"></i>
            <span class="text-[9px] font-bold text-gray-500 group-hover:text-blue-600">In phiếu lưu trú</span>
          </button>
          <button class="flex flex-col items-center justify-center gap-1 bg-white border border-gray-200 py-2.5 rounded-xl hover:bg-blue-50 hover:border-blue-200 transition-all group">
            <i class="far fa-edit text-gray-400 group-hover:text-blue-500"></i>
            <span class="text-[9px] font-bold text-gray-500 group-hover:text-blue-600">Sửa thông tin</span>
          </button>
          <button @click="handleCheckOut(selectedBooking)" class="flex flex-col items-center justify-center gap-1 bg-rose-50 border border-rose-100 py-2.5 rounded-xl hover:bg-rose-500 transition-all group">
            <i class="fas fa-sign-out-alt text-rose-500 group-hover:text-white"></i>
            <span class="text-[9px] font-bold text-rose-600 group-hover:text-white uppercase">Check-out</span>
          </button>
        </div>
      </div>
    </div>

    <!-- Modal Phiếu đặt phòng (Booking Voucher) -->
    <div v-if="showVoucherModal" class="fixed inset-0 z-[110] flex items-center justify-center p-6 bg-slate-900 bg-opacity-40 backdrop-blur-sm overflow-y-auto font-sans">
      <div class="bg-white rounded-2xl max-w-md w-full shadow-2xl animate-in fade-in zoom-in duration-300 my-auto border border-gray-100 overflow-hidden">
        <div class="p-4 border-b border-gray-50 flex items-center justify-between">
          <h3 class="text-sm font-bold text-gray-800">Phiếu đặt phòng</h3>
          <button @click="showVoucherModal = false" class="w-6 h-6 flex items-center justify-center rounded-lg hover:bg-gray-100 transition-all text-gray-400">
            <i class="fas fa-times text-xs"></i>
          </button>
        </div>

        <div id="voucher-print-area" class="p-8 space-y-6 text-gray-800">
          <!-- Hotel Logo & Info -->
          <div class="flex items-center gap-4 pb-6 border-b border-dashed border-gray-200">
            <div class="w-12 h-12 bg-gray-800 rounded-xl flex items-center justify-center text-white shadow-lg">
              <i class="fas fa-hotel text-xl"></i>
            </div>
            <div>
              <h4 class="font-black text-sm uppercase tracking-tighter leading-none">Grand Hotel</h4>
              <p class="text-[9px] text-gray-500 font-medium mt-1.5">170 An Dương Vương, phường Quy Nhơn Nam, tỉnh Gia Lai</p>
              <p class="text-[9px] text-gray-500 font-medium leading-none">Hotline:+84 123 456 789 - Email: contact@grandhotel.com</p>
            </div>
          </div>

          <!-- Voucher Title -->
          <div class="text-center space-y-1 py-2">
            <h2 class="text-lg font-black uppercase tracking-[0.2em] text-gray-800">Phiếu đặt phòng</h2>
            <p class="text-blue-600 font-black text-xs">BK{{ String(selectedBooking?.id).padStart(8, '0') }}</p>
          </div>

          <!-- Customer Info -->
          <div class="space-y-3">
            <h5 class="text-[10px] font-black uppercase tracking-widest text-gray-400 border-b border-gray-50 pb-1.5">Thông tin khách hàng</h5>
            <div class="grid grid-cols-2 gap-y-2.5 text-xs">
              <div class="flex flex-col gap-0.5">
                <span class="text-[9px] text-gray-400 font-bold uppercase">Họ tên</span>
                <span class="font-bold">{{ selectedBooking?.guestFullName || selectedBooking?.customer?.fullName }}</span>
              </div>
              <div class="flex flex-col gap-0.5 text-right">
                <span class="text-[9px] text-gray-400 font-bold uppercase">SĐT</span>
                <span class="font-bold">{{ selectedBooking?.guestPhone || selectedBooking?.customer?.phone }}</span>
              </div>
              <div class="flex flex-col gap-0.5 col-span-2">
                <span class="text-[9px] text-gray-400 font-bold uppercase">Email</span>
                <span class="font-bold">{{ selectedBooking?.guestEmail || selectedBooking?.customer?.email || '--' }}</span>
              </div>
              <div class="flex flex-col gap-0.5">
                <span class="text-[9px] text-gray-400 font-bold uppercase">CMND/CCCD</span>
                <span class="font-bold">034567890123</span>
              </div>
              <div class="flex flex-col gap-0.5 text-right">
                <span class="text-[9px] text-gray-400 font-bold uppercase">Quốc tịch</span>
                <span class="font-bold">Việt Nam</span>
              </div>
              <div class="flex flex-col gap-0.5 col-span-2">
                <span class="text-[9px] text-gray-400 font-bold uppercase">Địa chỉ</span>
                <span class="font-bold leading-tight">123 Đường ABC, Quận 1, TP. HCM</span>
              </div>
            </div>
          </div>

          <!-- Booking Details -->
          <div class="space-y-3">
            <h5 class="text-[10px] font-black uppercase tracking-widest text-gray-400 border-b border-gray-50 pb-1.5">Thông tin đặt phòng</h5>
            <div class="grid grid-cols-2 gap-y-4 text-xs">
              <div class="flex flex-col gap-0.5">
                <span class="text-[9px] text-gray-400 font-bold uppercase">Phòng</span>
                <span class="font-bold">101 - Deluxe Room</span>
              </div>
              <div class="flex flex-col gap-0.5 text-right">
                <span class="text-[9px] text-gray-400 font-bold uppercase">Check-in</span>
                <span class="font-bold">24/05/2025 (14:00)</span>
              </div>
              <div class="flex flex-col gap-0.5">
                <span class="text-[9px] text-gray-400 font-bold uppercase">Loại giường</span>
                <span class="font-bold">1 giường đôi</span>
              </div>
              <div class="flex flex-col gap-0.5 text-right">
                <span class="text-[9px] text-gray-400 font-bold uppercase">Check-out</span>
                <span class="font-bold">25/05/2025 (12:00)</span>
              </div>
              <div class="flex flex-col gap-0.5">
                <span class="text-[9px] text-gray-400 font-bold uppercase">Số khách</span>
                <span class="font-bold">2 người lớn, 0 trẻ em</span>
              </div>
              <div class="flex flex-col gap-0.5 text-right">
                <span class="text-[9px] text-gray-400 font-bold uppercase">Số đêm</span>
                <span class="font-bold">1 đêm</span>
              </div>
              <div class="flex flex-col gap-0.5">
                <span class="text-[9px] text-gray-400 font-bold uppercase">Giá phòng / đêm</span>
                <span class="font-bold">1.200.000 đ</span>
              </div>
              <div class="flex flex-col gap-0.5 text-right">
                <span class="text-[9px] text-gray-400 font-bold uppercase">Tổng tiền</span>
                <span class="font-black text-gray-800">1.200.000 đ</span>
              </div>
            </div>
          </div>

          <!-- Payment Info -->
          <div class="bg-gray-50 rounded-2xl p-6 space-y-3">
            <h5 class="text-[10px] font-black uppercase tracking-widest text-gray-400">Thanh toán</h5>
            <div class="space-y-2.5 text-xs">
              <div class="flex justify-between items-center">
                <span class="text-gray-500 font-medium">Phương thức</span>
                <span class="font-bold">Tiền mặt</span>
              </div>
              <div class="flex justify-between items-center">
                <span class="text-gray-500 font-medium">Tiền cọc</span>
                <span class="font-bold">500.000 đ</span>
              </div>
              <div class="flex justify-between items-center">
                <span class="text-gray-500 font-medium">Thanh toán ngày</span>
                <span class="font-bold">20/05/2025 10:45 AM</span>
              </div>
              <div class="flex justify-between items-center pt-2.5 border-t border-gray-200">
                <span class="font-black uppercase tracking-widest">Tổng thanh toán</span>
                <span class="font-black text-blue-600 text-sm">1.200.000 đ</span>
              </div>
              <div class="flex justify-between items-center">
                <span class="text-gray-500 font-medium">Trạng thái</span>
                <span class="px-2 py-0.5 bg-emerald-100 text-emerald-700 rounded font-black text-[9px]">Đã thanh toán</span>
              </div>
            </div>
          </div>
        </div>

        <div class="p-6 border-t border-gray-100 flex gap-3">
          <button @click="printVoucher" class="flex-1 bg-blue-600 hover:bg-blue-700 text-white py-3 rounded-xl font-bold text-xs transition-all flex items-center justify-center gap-2">
            <i class="fas fa-print"></i>
            In phiếu
          </button>
          <button @click="downloadPDF" class="flex-1 bg-gray-100 hover:bg-gray-200 text-gray-600 py-3 rounded-xl font-bold text-xs transition-all flex items-center justify-center gap-2">
            <i class="fas fa-download"></i>
            Tải PDF
          </button>
          <button @click="showVoucherModal = false" class="bg-gray-100 hover:bg-gray-200 text-gray-600 px-6 py-3 rounded-xl font-bold text-xs transition-all">Đóng</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, nextTick } from 'vue'
import axios from '../api/axios'
import StaffSidebar from '../components/StaffSidebar.vue'
import jsPDF from 'jspdf'
import 'jspdf-autotable'

const activeTab = ref('checkin')
const bookings = ref([])
const loading = ref(true)
const roomTypes = ref(['Deluxe Room', 'Superior Room', 'Suite Room', 'Standard Room'])
const showCheckInModal = ref(false)
const showDetailModal = ref(false)
const showVoucherModal = ref(false)
const selectedBooking = ref(null)

// Image & Camera Logic
const fileInput = ref(null)
const idCardPreview = ref(null)
const showCamera = ref(false)
const video = ref(null)
const stream = ref(null)

const triggerFileInput = () => {
  fileInput.value.click()
}

const onFileChange = (e) => {
  const file = e.target.files[0]
  if (file) {
    const reader = new FileReader()
    reader.onload = (event) => {
      idCardPreview.value = event.target.result
    }
    reader.readAsDataURL(file)
  }
}

const startCamera = async () => {
  try {
    showCamera.value = true
    await nextTick()
    stream.value = await navigator.mediaDevices.getUserMedia({ video: { facingMode: 'environment' } })
    if (video.value) {
      video.value.srcObject = stream.value
    }
  } catch (err) {
    console.error('Không thể mở camera:', err)
    alert('Không thể truy cập camera. Vui lòng kiểm tra quyền truy cập.')
    showCamera.value = false
  }
}

const capturePhoto = () => {
  const canvas = document.createElement('canvas')
  canvas.width = video.value.videoWidth
  canvas.height = video.value.videoHeight
  const ctx = canvas.getContext('2d')
  ctx.drawImage(video.value, 0, 0)
  idCardPreview.value = canvas.toDataURL('image/jpeg')
  stopCamera()
}

const stopCamera = () => {
  if (stream.value) {
    stream.value.getTracks().forEach(track => track.stop())
  }
  showCamera.value = false
  stream.value = null
}

const staffName = computed(() => {
  const user = JSON.parse(localStorage.getItem('user') || '{}')
  return user.fullName || 'Nhân viên'
})

// Notifications Logic
const showNotifications = ref(false)
const notifications = ref([
  { message: '🔔 Có khách vừa đặt phòng mới', time: '5 phút trước', read: false },
  { message: '🔔 Có booking chờ xác nhận', time: '10 phút trước', read: false },
  { message: '🔔 Khách vừa hủy đặt phòng', time: '1 giờ trước', read: true },
  { message: '🔔 Đặt phòng sắp đến ngày check-in', time: '2 giờ trước', read: false },
  { message: '🔔 Khách sắp check-out hôm nay', time: '3 giờ trước', read: false },
  { message: '🔔 Phòng khách đặt hiện không khả dụng', time: '1 ngày trước', read: true },
  { message: '🔔 Khách chưa thanh toán tiền cọc', time: '1 ngày trước', read: false },
  { message: '🔔 Có yêu cầu đổi phòng / đổi ngày', time: '2 ngày trước', read: false },
  { message: '🔔 Booking quá hạn chưa check-in', time: '2 ngày trước', read: true },
  { message: '🔔 Có ghi chú đặc biệt từ khách VIP', time: '3 ngày trước', read: false }
])

const unreadNotificationsCount = computed(() => notifications.value.filter(n => !n.read).length)

const toggleNotifications = () => {
  showNotifications.value = !showNotifications.value
}

const markAllAsRead = () => {
  notifications.value.forEach(n => n.read = true)
}

const checkInForm = ref({
  checkInDate: '',
  checkInTime: '14:00',
  guestIdNumber: '',
  nationality: 'Việt Nam',
  address: '',
  note: '',
  paymentMethod: 'Tiền mặt',
  depositAmount: 500000,
  internalNote: ''
})

const filters = ref({
  search: '',
  status: '',
  roomType: '',
  date: new Date().toISOString().substr(0, 10)
})

const stats = ref({
  pendingCheckin: 0,
  checkedInToday: 0,
  dueCheckout: 0,
  overdueCheckout: 0,
  checkedOutToday: 0
})

const fetchBookings = async () => {
  loading.value = true
  try {
    const response = await axios.get('/bookings')
    bookings.value = response.data
    calculateStats()
  } catch (error) {
    console.error('Error fetching bookings:', error)
  } finally {
    loading.value = false
  }
}

const calculateStats = () => {
  const today = new Date().toISOString().substr(0, 10)
  stats.value.pendingCheckin = bookings.value.filter(b => b.status === 'CONFIRMED').length
  stats.value.checkedInToday = bookings.value.filter(b => b.status === 'CHECKED_IN' && b.checkInDate === today).length
  stats.value.dueCheckout = bookings.value.filter(b => b.status === 'CHECKED_IN' && b.checkOutDate === today).length
  stats.value.checkedOutToday = bookings.value.filter(b => b.status === 'COMPLETED' && b.checkOutDate === today).length
  
  const now = new Date()
  stats.value.overdueCheckout = bookings.value.filter(b => 
    b.status === 'CHECKED_IN' && 
    new Date(b.checkOutDate) < now && 
    b.checkOutDate !== today
  ).length
}

const filteredBookings = computed(() => {
  return bookings.value.filter(b => {
    if (activeTab.value === 'checkin') {
      if (b.status === 'COMPLETED' || b.status === 'CANCELLED') return false
    } else {
      if (b.status === 'CONFIRMED' || b.status === 'PENDING' || b.status === 'CANCELLED') return false
    }

    const searchStr = filters.value.search.toLowerCase()
    const matchSearch = !filters.value.search || 
      (b.guestFullName || b.customer?.fullName || '').toLowerCase().includes(searchStr) ||
      (b.guestPhone || b.customer?.phone || '').includes(searchStr) ||
      String(b.id).includes(searchStr)
    
    const matchStatus = !filters.value.status || b.status === filters.value.status
    const matchRoomType = !filters.value.roomType || b.room?.category === filters.value.roomType
    
    return matchSearch && matchStatus && matchRoomType
  })
})

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const d = new Date(dateStr)
  return d.toLocaleDateString('vi-VN')
}

const formatStatus = (status) => {
  switch (status) {
    case 'CONFIRMED': return 'Đã đặt (Chờ đến)'
    case 'CHECKED_IN': return 'Đã nhận phòng'
    case 'COMPLETED': return 'Đã trả phòng'
    case 'CANCELLED': return 'Đã hủy'
    default: return status
  }
}

const getStatusStyles = (status) => {
  switch (status) {
    case 'CONFIRMED': return 'bg-blue-50 text-blue-600 border border-blue-100'
    case 'CHECKED_IN': return 'bg-emerald-50 text-emerald-600 border border-emerald-100'
    case 'COMPLETED': return 'bg-gray-50 text-gray-500 border border-gray-100'
    case 'CANCELLED': return 'bg-rose-50 text-rose-500 border border-rose-100'
    default: return 'bg-amber-50 text-amber-600 border border-amber-100'
  }
}

const handleCheckIn = (booking) => {
  selectedBooking.value = booking
  idCardPreview.value = null
  checkInForm.value = {
    checkInDate: booking.checkInDate,
    checkInTime: '14:00',
    guestIdNumber: booking.guestIdNumber || '',
    nationality: 'Việt Nam',
    address: booking.guestAddress || '',
    note: booking.note || '',
    paymentMethod: 'Tiền mặt',
    depositAmount: 500000,
    internalNote: ''
  }
  showCheckInModal.value = true
}

const openDetail = (booking) => {
  selectedBooking.value = booking
  showDetailModal.value = true
}

const openVoucher = (booking) => {
  selectedBooking.value = booking
  showVoucherModal.value = true
}

const submitCheckIn = async () => {
  try {
    const payload = {
      guestIdNumber: checkInForm.value.guestIdNumber,
      guestIdImageUrl: idCardPreview.value // Truyền thêm ảnh CCCD nếu có
    }
    await axios.put(`/bookings/${selectedBooking.value.id}/check-in`, payload)
    showCheckInModal.value = false
    fetchBookings()
    alert('Khách đã nhận phòng thành công!')
  } catch (error) {
    const errorMsg = error.response?.data?.message || error.message || 'Check-in thất bại'
    alert('Lỗi: ' + errorMsg)
  }
}

const handleCheckOut = async (booking) => {
  if (!confirm(`Xác nhận check-out cho phòng ${booking.room?.roomNumber}?`)) return
  try {
    await axios.put(`/invoices/booking/${booking.id}/check-out`)
    fetchBookings()
    alert('Check-out thành công!')
  } catch (error) {
    console.error('Check-out failed:', error)
    alert('Lỗi check-out')
  }
}

const printVoucher = () => {
  window.print()
}

const downloadPDF = () => {
  const doc = new jsPDF()
  const booking = selectedBooking.value
  
  // Set font
  doc.setFontSize(20)
  doc.text('PHIEU DAT PHONG', 105, 20, { align: 'center' })
  
  doc.setFontSize(12)
  doc.text(`Ma dat phong: BK${String(booking?.id).padStart(8, '0')}`, 20, 40)
  doc.text(`Khach hang: ${booking?.guestFullName || booking?.customer?.fullName}`, 20, 50)
  doc.text(`So dien thoai: ${booking?.guestPhone || booking?.customer?.phone}`, 20, 60)
  doc.text(`Phong: ${booking?.room?.roomNumber} - ${booking?.room?.category}`, 20, 70)
  doc.text(`Check-in: ${formatDate(booking?.checkInDate)} (14:00)`, 20, 80)
  doc.text(`Check-out: ${formatDate(booking?.checkOutDate)} (12:00)`, 20, 90)
  doc.text(`Tong tien: ${new Intl.NumberFormat('vi-VN').format(booking?.totalPrice)} d`, 20, 100)
  
  doc.save(`Voucher_BK${String(booking?.id).padStart(8, '0')}.pdf`)
}

onMounted(() => {
  fetchBookings()
})
</script>

<style scoped>
.custom-scrollbar::-webkit-scrollbar {
  width: 4px;
}
.custom-scrollbar::-webkit-scrollbar-track {
  background: transparent;
}
.custom-scrollbar::-webkit-scrollbar-thumb {
  background: #e2e8f0;
  border-radius: 10px;
}

.animate-in {
  animation: animate-in 0.2s ease-out;
}

@keyframes animate-in {
  from {
    opacity: 0;
    transform: scale(0.98);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}

@media print {
  body * {
    visibility: hidden;
  }
  #voucher-print-area, #voucher-print-area * {
    visibility: visible;
  }
  #voucher-print-area {
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
  }
}
</style>
