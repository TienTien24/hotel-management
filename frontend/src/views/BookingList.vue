<template>
  <div class="min-h-screen flex bg-[#f8faff] font-sans text-[#2d3748]">
    <!-- Sidebar -->
    <AdminSidebar v-if="isAdmin" />
    <StaffSidebar v-else />

    <!-- Main Content -->
    <main class="flex-1 p-8 overflow-y-auto">
      <!-- Header Section -->
      <header class="flex items-center justify-between mb-8">
        <h2 class="text-2xl font-bold text-[#1a202c]">Quản lý đặt phòng</h2>
        
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

          <!-- Create Booking Button -->
          <button @click="openCreateModal" class="bg-[#3182ce] hover:bg-[#2b6cb0] text-white px-5 py-2.5 rounded-xl font-bold text-sm transition-all shadow-lg shadow-blue-100 flex items-center gap-2">
            <i class="fas fa-plus text-xs"></i>
            Tạo đặt phòng
          </button>
        </div>
      </header>

      <!-- Tabs Navigation -->
      <div class="flex items-center gap-8 mb-6 border-b border-gray-200 px-2">
        <button v-for="tab in tabItems" :key="tab.value"
          @click="filters.status = tab.value"
          class="pb-4 text-sm font-bold transition-all relative"
          :class="filters.status === tab.value ? 'text-[#3182ce]' : 'text-gray-400 hover:text-gray-600'"
        >
          {{ tab.label }}
          <div v-if="filters.status === tab.value" class="absolute bottom-0 left-0 w-full h-0.5 bg-[#3182ce] rounded-full"></div>
        </button>
      </div>

      <!-- Filters Section -->
      <div class="flex flex-col md:flex-row gap-4 mb-6">
        <div class="relative flex-1 max-w-md">
          <i class="fas fa-search absolute left-4 top-1/2 -translate-y-1/2 text-gray-400 text-sm"></i>
          <input type="text" v-model="filters.search" placeholder="Tìm tên khách, SĐT, mã đặt phòng..." 
            class="w-full bg-white border border-gray-200 rounded-xl pl-11 pr-4 py-2.5 outline-none focus:border-blue-400 transition-all text-sm font-medium shadow-sm">
        </div>
        
        <div class="relative w-full md:w-56">
          <i class="far fa-calendar-alt absolute left-4 top-1/2 -translate-y-1/2 text-gray-400 text-sm"></i>
          <select v-model="filters.timeRange" class="w-full bg-white border border-gray-200 rounded-xl pl-11 pr-4 py-2.5 outline-none focus:border-blue-400 transition-all text-sm font-bold shadow-sm appearance-none">
            <option value="all">Tất cả thời gian</option>
            <option value="today">Hôm nay</option>
            <option value="this-week">Tuần này</option>
            <option value="this-month">Tháng này</option>
          </select>
          <i class="fas fa-chevron-down absolute right-4 top-1/2 -translate-y-1/2 text-gray-300 text-[10px] pointer-events-none"></i>
        </div>
      </div>

      <!-- Bookings Table -->
      <div class="bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden">
        <div v-if="loading" class="p-16 text-center text-gray-400 font-bold">Đang tải danh sách đặt phòng...</div>
        <div v-else-if="filteredBookings.length === 0" class="p-16 text-center text-gray-400 font-bold">Không có đặt phòng phù hợp.</div>
        <div v-else class="overflow-x-auto">
          <table class="w-full text-left">
            <thead>
              <tr class="bg-gray-50 bg-opacity-50 border-b border-gray-100">
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider">Mã đặt phòng</th>
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider">Khách hàng</th>
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider">Phòng</th>
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider">Check-in</th>
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider">Check-out</th>
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider text-center">Trạng thái</th>
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider text-center">Thanh toán</th>
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider text-center">Thao tác</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-gray-50">
              <tr v-for="booking in filteredBookings" :key="booking.id" class="hover:bg-gray-50 hover:bg-opacity-30 transition-colors">
                <td class="px-6 py-5">
                  <span @click="openDetailModal(booking)" class="text-blue-600 font-bold cursor-pointer hover:underline">BK{{ String(booking.id).padStart(8, '0') }}</span>
                </td>
                <td class="px-6 py-5">
                  <div class="flex flex-col">
                    <span class="text-sm font-bold text-gray-800">{{ booking.guestFullName || booking.customer?.fullName }}</span>
                    <span class="text-[11px] text-gray-400 font-medium mt-0.5">{{ booking.guestPhone || booking.customer?.phone || 'N/A' }}</span>
                  </div>
                </td>
                <td class="px-6 py-5 text-sm font-bold text-gray-600">{{ booking.room?.roomNumber }}</td>
                <td class="px-6 py-5 text-sm font-medium text-gray-600">{{ formatDate(booking.checkInDate) }}</td>
                <td class="px-6 py-5 text-sm font-medium text-gray-600">{{ formatDate(booking.checkOutDate) }}</td>
                <td class="px-6 py-5 text-center">
                  <span :class="getStatusStyles(booking.status)" class="px-3 py-1 rounded-full text-[10px] font-bold flex items-center justify-center gap-1.5 w-fit mx-auto">
                    <div class="w-1.5 h-1.5 rounded-full" :class="getStatusDotClass(booking.status)"></div>
                    {{ formatStatus(booking.status) }}
                  </span>
                </td>
                <td class="px-6 py-5 text-center">
                  <span :class="getPaymentStatusStyles(booking.paymentStatus)" class="px-3 py-1 rounded-lg text-[10px] font-bold">
                    {{ formatPaymentStatus(booking.paymentStatus) }}
                  </span>
                </td>
                <td class="px-6 py-5">
                  <div class="flex items-center justify-center gap-2 flex-wrap">
                    <button @click="openDetailModal(booking)" class="w-8 h-8 rounded-lg bg-blue-50 text-blue-600 hover:bg-blue-600 hover:text-white transition-all flex items-center justify-center border border-blue-100" title="Xem chi tiết">
                      <i class="far fa-eye text-xs"></i>
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        
        <!-- Pagination -->
        <div class="p-6 border-t border-gray-50 flex items-center justify-between">
          <p class="text-xs text-gray-400 font-medium">Hiển thị 1 - {{ filteredBookings.length }} trong {{ bookings.length }} đặt phòng</p>
        </div>
      </div>
    </main>

    <!-- Modal Chi tiết đặt phòng -->
    <div v-if="showDetailModal" class="fixed inset-0 z-[100] flex items-center justify-center p-6 bg-slate-900 bg-opacity-40 backdrop-blur-sm overflow-y-auto">
      <div class="bg-white rounded-2xl max-w-2xl w-full shadow-2xl animate-in fade-in zoom-in duration-300 my-auto">
        <!-- Modal Header -->
        <div class="p-6 border-b border-gray-100 flex items-center justify-between sticky top-0 bg-white rounded-t-2xl z-10">
          <div class="flex items-center gap-3">
            <h3 class="text-lg font-bold text-gray-800">Chi tiết đặt phòng</h3>
            <span :class="getStatusStyles(selectedBooking?.status)" class="px-2 py-0.5 rounded-md text-[10px] font-bold">
              {{ formatStatus(selectedBooking?.status) }}
            </span>
          </div>
          <button @click="showDetailModal = false" class="w-8 h-8 flex items-center justify-center rounded-lg hover:bg-gray-100 transition-all text-gray-400">
            <i class="fas fa-times"></i>
          </button>
        </div>

        <!-- Modal Body -->
        <div class="p-8 space-y-8 max-h-[70vh] overflow-y-auto custom-scrollbar">
          <!-- Booking Basic Info -->
          <div class="flex items-center gap-5">
            <div class="w-14 h-14 bg-blue-50 rounded-xl flex items-center justify-center border border-blue-100">
              <i class="far fa-calendar-alt text-blue-500 text-2xl"></i>
            </div>
            <div>
              <h4 class="text-xl font-bold text-blue-600">BK{{ String(selectedBooking?.id).padStart(8, '0') }}</h4>
              <p class="text-[11px] text-gray-400 font-bold mt-1 uppercase tracking-wider">Ngày đặt: {{ formatDateTime(selectedBooking?.createdAt) }}</p>
              <p class="text-[11px] text-gray-400 font-bold mt-0.5 uppercase tracking-wider">Lễ tân: {{ staffName }}</p>
            </div>
          </div>

          <!-- Section: Thông tin khách hàng -->
          <div class="space-y-4">
            <div class="flex items-center justify-between">
              <h5 class="text-xs font-bold text-gray-400 uppercase tracking-widest">Thông tin khách hàng</h5>
            </div>
            <div class="grid grid-cols-2 gap-y-4 bg-gray-50 bg-opacity-50 p-6 rounded-2xl border border-gray-100">
              <div class="space-y-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Họ tên</span>
                <p class="text-sm font-bold text-gray-800">{{ selectedBooking?.guestFullName || selectedBooking?.customer?.fullName }}</p>
              </div>
              <div class="space-y-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">SĐT</span>
                <p class="text-sm font-bold text-gray-800">{{ selectedBooking?.guestPhone || selectedBooking?.customer?.phone }}</p>
              </div>
              <div class="space-y-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Email</span>
                <p class="text-sm font-bold text-gray-800">{{ selectedBooking?.guestEmail || selectedBooking?.customer?.email || '--' }}</p>
              </div>
              <div class="space-y-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Quốc tịch</span>
                <p class="text-sm font-bold text-gray-800">Việt Nam</p>
              </div>
              <div class="space-y-1 col-span-2">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Địa chỉ</span>
                <p class="text-sm font-bold text-gray-800">{{ selectedBooking?.guestAddress || '123 Đường ABC, Quận 1, TP. HCM' }}</p>
              </div>
            </div>
          </div>

          <!-- Section: Thông tin đặt phòng -->
          <div class="space-y-4">
            <div class="flex items-center justify-between">
              <h5 class="text-xs font-bold text-gray-400 uppercase tracking-widest">Thông tin đặt phòng</h5>
              <button v-if="selectedBooking?.status === 'CHECKED_IN'" @click="showAddService = true" class="text-blue-600 font-bold text-[10px] hover:underline px-3 py-1 bg-blue-50 rounded-lg flex items-center gap-1.5">
                <i class="fas fa-plus-circle"></i>
                Thêm dịch vụ
              </button>
            </div>
            <div class="grid grid-cols-2 gap-x-8 gap-y-4 bg-gray-50 bg-opacity-50 p-6 rounded-2xl border border-gray-100">
              <div class="flex justify-between items-center border-b border-gray-100 pb-2 col-span-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Phòng</span>
                <span class="text-sm font-bold text-gray-800">{{ selectedBooking?.room?.roomNumber }} - {{ selectedBooking?.room?.category }}</span>
              </div>
              <div class="flex justify-between items-center border-b border-gray-100 pb-2 col-span-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Loại giường</span>
                <span class="text-sm font-bold text-gray-800">1 giường đôi</span>
              </div>
              <div class="flex justify-between items-center border-b border-gray-100 pb-2 col-span-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Check-in</span>
                <span class="text-sm font-bold text-gray-800">{{ formatDate(selectedBooking?.checkInDate) }} (14:00)</span>
              </div>
              <div class="flex justify-between items-center border-b border-gray-100 pb-2 col-span-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Check-out</span>
                <span class="text-sm font-bold text-gray-800">{{ formatDate(selectedBooking?.checkOutDate) }} (12:00)</span>
              </div>
              <div class="flex justify-between items-center border-b border-gray-100 pb-2 col-span-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Số đêm</span>
                <span class="text-sm font-bold text-gray-800">{{ calculateNights(selectedBooking?.checkInDate, selectedBooking?.checkOutDate) }} đêm</span>
              </div>
              <div class="flex justify-between items-center border-b border-gray-100 pb-2 col-span-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Số khách</span>
                <span class="text-sm font-bold text-gray-800">{{ selectedBooking?.numberOfGuests }} người lớn, 0 trẻ em</span>
              </div>
              <div class="flex justify-between items-center pt-2 col-span-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Giá phòng / đêm</span>
                <span class="text-sm font-bold text-gray-800">{{ formatCurrency(selectedBooking?.room?.price) }}</span>
              </div>
              <div class="flex justify-between items-center pt-2 col-span-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Tổng tiền phòng</span>
                <span class="text-sm font-bold text-gray-800">{{ formatCurrency(selectedBooking?.totalPrice) }}</span>
              </div>
            </div>
          </div>

          <!-- Dịch vụ đã sử dụng -->
          <div v-if="selectedBooking?.status === 'CHECKED_IN' || selectedBooking?.status === 'COMPLETED'" class="space-y-4">
            <div class="flex items-center justify-between">
              <h5 class="text-xs font-bold text-gray-400 uppercase tracking-widest">Dịch vụ đã sử dụng</h5>
              <button v-if="selectedBooking?.status === 'CHECKED_IN'" @click="showAddService = true" class="text-blue-600 font-bold text-[10px] hover:underline px-3 py-1 bg-blue-50 rounded-lg flex items-center gap-1.5">
                <i class="fas fa-plus-circle"></i>
                Thêm dịch vụ
              </button>
            </div>
            <div v-if="currentServices.length === 0" class="text-center py-6 bg-gray-50 bg-opacity-50 rounded-2xl border border-dashed border-gray-200">
              <p class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Chưa sử dụng dịch vụ</p>
            </div>
            <div v-else class="space-y-2">
              <div v-for="service in currentServices" :key="service.id" class="flex justify-between items-center bg-gray-50 bg-opacity-50 p-4 rounded-2xl border border-gray-100">
                <div class="flex items-center gap-3">
                  <div class="w-8 h-8 rounded-lg bg-white flex items-center justify-center text-blue-500 shadow-sm border border-gray-100">
                    <i class="fas fa-concierge-bell text-xs"></i>
                  </div>
                  <div>
                    <p class="text-sm font-bold text-gray-800">{{ service.service?.name }}</p>
                    <div class="flex items-center gap-2">
                      <p class="text-[9px] font-bold text-gray-400 uppercase tracking-widest">SL: {{ service.quantity }}</p>
                      <span :class="getStatusClass(service.status)" class="px-1.5 py-0.5 rounded text-[8px] font-black uppercase">
                        {{ formatStatus(service.status) }}
                      </span>
                    </div>
                  </div>
                </div>
                <div class="text-right">
                  <p class="text-sm font-bold text-gray-800">{{ formatCurrency(service.service?.price * service.quantity) }}</p>
                  <p class="text-[9px] font-bold text-gray-400 uppercase tracking-widest">{{ formatDateTime(service.usedDate) }}</p>
                </div>
              </div>
            </div>
          </div>

          <!-- Section: Thanh toán -->
          <div class="space-y-4">
            <h5 class="text-xs font-bold text-gray-400 uppercase tracking-widest flex items-center gap-2">
              Thanh toán & Hóa đơn
              <span :class="getPaymentStatusStyles(selectedBooking?.paymentStatus)" class="px-2 py-0.5 rounded text-[9px] font-bold">
                {{ formatPaymentStatus(selectedBooking?.paymentStatus) }}
              </span>
            </h5>
            <div v-if="invoiceLoading" class="text-center text-xs text-gray-400 py-4">Đang tải hóa đơn...</div>
            <div v-else class="bg-gray-50 bg-opacity-50 p-6 rounded-2xl border border-gray-100 space-y-3">
              <div class="flex justify-between items-center">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Phương thức</span>
                <span class="text-sm font-bold text-gray-800">{{ selectedBooking?.paymentMethod || 'Tiền mặt' }}</span>
              </div>
              <div class="flex justify-between items-center">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Tiền phòng</span>
                <span class="text-sm font-bold text-gray-800">{{ formatCurrency(invoice?.roomCharges) }}</span>
              </div>
              <div class="flex justify-between items-center">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Dịch vụ (đã hoàn thành)</span>
                <span class="text-sm font-bold text-gray-800">{{ formatCurrency(invoice?.serviceCharges) }}</span>
              </div>
              <div class="flex justify-between items-center pt-3 border-t border-gray-100">
                <span class="text-[11px] font-bold text-gray-800 uppercase tracking-widest">Tổng thanh toán</span>
                <span class="text-lg font-bold text-emerald-600">{{ formatCurrency(invoice?.totalAmount ?? selectedBooking?.totalPrice) }}</span>
              </div>
              <div v-if="selectedBooking?.paymentStatus === 'PAID' && invoice?.paymentDate" class="flex justify-between items-center text-xs">
                <span class="text-gray-400 font-medium">Ngày thanh toán</span>
                <span class="font-bold text-gray-700">{{ formatDateTime(invoice.paymentDate) }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- Modal Footer -->
        <div class="p-6 border-t border-gray-100 flex flex-wrap gap-3 sticky bottom-0 bg-white rounded-b-2xl">
          <button v-if="selectedBooking?.paymentStatus === 'PAID'" @click="openInvoiceModal" class="flex-1 min-w-[140px] bg-emerald-600 hover:bg-emerald-700 text-white py-3 rounded-xl font-bold text-sm transition-all flex items-center justify-center gap-2">
            <i class="fas fa-file-invoice"></i>
            In hóa đơn
          </button>
          <button v-if="selectedBooking?.paymentStatus !== 'PAID' && (selectedBooking?.status === 'CHECKED_IN' || selectedBooking?.status === 'CONFIRMED' || selectedBooking?.status === 'PENDING')" @click="handlePayment(selectedBooking.id)" class="flex-1 min-w-[140px] bg-yellow-500 hover:bg-yellow-600 text-white py-3 rounded-xl font-bold text-sm transition-all flex items-center justify-center gap-2">
            <i class="fas fa-credit-card"></i>
            {{ selectedBooking?.status === 'CHECKED_IN' ? 'Quyết toán & Thu tiền' : 'Xác nhận thanh toán' }}
          </button>
          <button v-if="selectedBooking?.status === 'PENDING'" @click="handleConfirm(selectedBooking.id)" class="flex-1 bg-green-500 hover:bg-green-600 text-white py-3 rounded-xl font-bold text-sm transition-all flex items-center justify-center gap-2">
            <i class="fas fa-check"></i>
            Xác nhận
          </button>
          <button v-if="selectedBooking?.status !== 'CANCELLED'" @click="handleCancel(selectedBooking.id)" class="flex-1 border border-rose-200 hover:bg-rose-50 text-rose-500 py-3 rounded-xl font-bold text-sm transition-all flex items-center justify-center gap-2">
            <i class="far fa-trash-alt"></i>
            Hủy đặt phòng
          </button>
        </div>
      </div>
    </div>

    <!-- Modal In hóa đơn -->
    <div v-if="showInvoiceModal" class="fixed inset-0 z-[120] flex items-center justify-center p-6 bg-slate-900/50 backdrop-blur-sm overflow-y-auto">
      <div class="bg-white rounded-2xl max-w-md w-full shadow-2xl my-auto border border-gray-100 overflow-hidden">
        <div class="p-4 border-b border-gray-50 flex items-center justify-between">
          <h3 class="text-sm font-bold text-gray-800">Hóa đơn thanh toán</h3>
          <button @click="showInvoiceModal = false" class="w-8 h-8 flex items-center justify-center rounded-lg hover:bg-gray-100 text-gray-400">
            <i class="fas fa-times"></i>
          </button>
        </div>

        <div id="invoice-print-area" class="p-8 space-y-6 text-gray-800">
          <div class="flex items-center gap-4 pb-6 border-b border-dashed border-gray-200">
            <div class="w-12 h-12 bg-emerald-800 rounded-xl flex items-center justify-center text-white">
              <i class="fas fa-hotel text-xl"></i>
            </div>
            <div>
              <h4 class="font-black text-sm uppercase tracking-tight">Grand Hotel</h4>
              <p class="text-[9px] text-gray-500 mt-1">170 An Dương Vương, Quy Nhơn Nam, Gia Lai</p>
              <p class="text-[9px] text-gray-500">Hotline: +84 123 456 789</p>
            </div>
          </div>

          <div class="text-center py-2">
            <h2 class="text-lg font-black uppercase tracking-widest">Hóa đơn thanh toán</h2>
            <p class="text-emerald-700 font-black text-xs mt-1">HD{{ String(invoice?.id || selectedBooking?.id).padStart(8, '0') }}</p>
            <p class="text-blue-600 font-bold text-[10px] mt-0.5">BK{{ String(selectedBooking?.id).padStart(8, '0') }}</p>
          </div>

          <div class="space-y-2 text-xs">
            <h5 class="text-[10px] font-black uppercase text-gray-400 border-b pb-1">Khách hàng</h5>
            <p><span class="text-gray-400">Họ tên:</span> <strong>{{ selectedBooking?.guestFullName || selectedBooking?.customer?.fullName }}</strong></p>
            <p><span class="text-gray-400">SĐT:</span> <strong>{{ selectedBooking?.guestPhone || selectedBooking?.customer?.phone }}</strong></p>
            <p><span class="text-gray-400">Phòng:</span> <strong>{{ selectedBooking?.room?.roomNumber }} - {{ selectedBooking?.room?.category }}</strong></p>
            <p><span class="text-gray-400">Lưu trú:</span> <strong>{{ formatDate(selectedBooking?.checkInDate) }} → {{ formatDate(selectedBooking?.checkOutDate) }}</strong></p>
          </div>

          <div class="bg-gray-50 rounded-2xl p-5 space-y-2.5 text-xs">
            <div class="flex justify-between">
              <span class="text-gray-500">Tiền phòng</span>
              <span class="font-bold">{{ formatCurrency(invoice?.roomCharges) }}</span>
            </div>
            <div class="flex justify-between">
              <span class="text-gray-500">Dịch vụ</span>
              <span class="font-bold">{{ formatCurrency(invoice?.serviceCharges) }}</span>
            </div>
            <div class="flex justify-between pt-2 border-t border-gray-200">
              <span class="font-black uppercase">Tổng cộng</span>
              <span class="font-black text-emerald-700">{{ formatCurrency(invoice?.totalAmount) }}</span>
            </div>
            <div class="flex justify-between">
              <span class="text-gray-500">Phương thức</span>
              <span class="font-bold">{{ formatPaymentMethod(selectedBooking?.paymentMethod) }}</span>
            </div>
            <div class="flex justify-between">
              <span class="text-gray-500">Ngày thanh toán</span>
              <span class="font-bold">{{ formatDateTime(invoice?.paymentDate) }}</span>
            </div>
            <div class="flex justify-between">
              <span class="text-gray-500">Trạng thái</span>
              <span class="px-2 py-0.5 bg-emerald-100 text-emerald-700 rounded font-black text-[9px]">Đã thanh toán</span>
            </div>
          </div>

          <p class="text-[9px] text-center text-gray-400 italic">Cảm ơn quý khách đã lưu trú tại Grand Hotel!</p>
        </div>

        <div class="p-6 border-t border-gray-100 flex gap-3">
          <button @click="printInvoice" class="flex-1 bg-emerald-600 hover:bg-emerald-700 text-white py-3 rounded-xl font-bold text-xs flex items-center justify-center gap-2">
            <i class="fas fa-print"></i>
            In hóa đơn
          </button>
          <button @click="downloadInvoicePDF" class="flex-1 bg-gray-100 hover:bg-gray-200 text-gray-600 py-3 rounded-xl font-bold text-xs flex items-center justify-center gap-2">
            <i class="fas fa-download"></i>
            Tải PDF
          </button>
          <button @click="showInvoiceModal = false" class="bg-gray-100 hover:bg-gray-200 text-gray-600 px-5 py-3 rounded-xl font-bold text-xs">Đóng</button>
        </div>
      </div>
    </div>

    <!-- Modal Tạo/Cập nhật Booking -->
    <div v-if="showModal" class="fixed inset-0 z-[110] flex items-center justify-center p-6 bg-slate-900 bg-opacity-40 backdrop-blur-sm overflow-y-auto">
      <div class="bg-white rounded-[2.5rem] max-w-xl w-full shadow-2xl animate-in fade-in zoom-in duration-300 my-auto overflow-hidden">
        <div class="bg-[#3182ce] p-8 text-white relative">
          <button @click="closeModal" class="absolute top-6 right-6 w-10 h-10 flex items-center justify-center rounded-xl bg-white bg-opacity-10 hover:bg-white hover:bg-opacity-20 transition-all">
            <i class="fas fa-times"></i>
          </button>
          <h3 class="text-2xl font-bold uppercase tracking-tight">{{ isEditing ? 'Cập nhật đặt phòng' : 'Tạo đặt phòng mới' }}</h3>
          <p class="text-white text-opacity-70 text-sm font-medium mt-1">Vui lòng điền đầy đủ thông tin bên dưới</p>
        </div>

        <form @submit.prevent="handleSubmit" class="p-8 space-y-6">
          <div class="grid grid-cols-2 gap-6">
            <div class="space-y-2">
              <label class="block text-[10px] font-bold uppercase tracking-widest text-gray-400 ml-1">Tên khách hàng</label>
              <input v-model="form.guestFullName" type="text" required placeholder="Họ và tên..." class="w-full bg-gray-50 border border-gray-100 rounded-xl px-4 py-3 outline-none focus:border-blue-400 transition-all font-bold text-sm">
            </div>
            <div class="space-y-2">
              <label class="block text-[10px] font-bold uppercase tracking-widest text-gray-400 ml-1">SĐT / Email</label>
              <input v-model="form.guestContact" type="text" required placeholder="Liên hệ..." class="w-full bg-gray-50 border border-gray-100 rounded-xl px-4 py-3 outline-none focus:border-blue-400 transition-all font-bold text-sm">
            </div>
          </div>

          <div class="grid grid-cols-2 gap-6">
            <div class="space-y-2">
              <label class="block text-[10px] font-bold uppercase tracking-widest text-gray-400 ml-1">Ngày nhận phòng</label>
              <input v-model="form.checkInDate" type="date" required class="w-full bg-gray-50 border border-gray-100 rounded-xl px-4 py-3 outline-none focus:border-blue-400 transition-all font-bold text-sm">
            </div>
            <div class="space-y-2">
              <label class="block text-[10px] font-bold uppercase tracking-widest text-gray-400 ml-1">Ngày trả phòng</label>
              <input v-model="form.checkOutDate" type="date" required class="w-full bg-gray-50 border border-gray-100 rounded-xl px-4 py-3 outline-none focus:border-blue-400 transition-all font-bold text-sm">
            </div>
          </div>

          <div class="grid grid-cols-2 gap-6">
            <div class="space-y-2">
              <label class="block text-[10px] font-bold uppercase tracking-widest text-gray-400 ml-1">Số người</label>
              <input v-model.number="form.numberOfGuests" type="number" min="1" required class="w-full bg-gray-50 border border-gray-100 rounded-xl px-4 py-3 outline-none focus:border-blue-400 transition-all font-bold text-sm">
            </div>
            <div class="space-y-2">
              <label class="block text-[10px] font-bold uppercase tracking-widest text-gray-400 ml-1">Chọn phòng</label>
              <select v-model="form.roomId" required class="w-full bg-gray-50 border border-gray-100 rounded-xl px-4 py-3 outline-none focus:border-blue-400 transition-all font-bold text-sm">
                <option value="">-- Chọn phòng trống --</option>
                <option v-for="room in availableRooms" :key="room.id" :value="room.id">
                  P.{{ room.roomNumber }} - {{ room.category }}
                </option>
              </select>
            </div>
          </div>

          <button type="submit" class="w-full bg-[#3182ce] hover:bg-[#2b6cb0] text-white py-4 rounded-xl font-bold uppercase tracking-widest text-sm transition-all shadow-lg shadow-blue-100 flex items-center justify-center gap-3">
            <i class="fas fa-check-circle"></i>
            {{ isEditing ? 'Lưu thay đổi' : 'Xác nhận đặt phòng' }}
          </button>
        </form>
      </div>
    </div>

    <!-- Modal Thêm dịch vụ (New Modal) -->
    <div v-if="showAddService" class="fixed inset-0 z-[120] flex items-center justify-center p-6 bg-slate-900/60 backdrop-blur-md font-sans">
      <div class="bg-white rounded-[2.5rem] max-w-md w-full overflow-hidden shadow-2xl animate-in fade-in zoom-in duration-300 my-auto border border-gray-100">
        <div class="bg-[#3182ce] p-8 text-white flex justify-between items-center">
          <div>
            <h3 class="text-2xl font-bold uppercase tracking-tight">Thêm dịch vụ</h3>
            <p class="text-white/60 text-[10px] font-bold uppercase tracking-widest mt-1">Phòng {{ selectedBooking?.room?.roomNumber }}</p>
          </div>
          <button @click="showAddService = false" class="w-10 h-10 flex items-center justify-center rounded-xl bg-white/10 text-white hover:bg-rose-500 transition-all">
            <i class="fas fa-times"></i>
          </button>
        </div>
        
        <form @submit.prevent="submitAddService" class="p-8 space-y-6">
          <div class="space-y-2">
            <label class="text-[10px] font-bold text-gray-400 uppercase tracking-widest ml-1">Chọn dịch vụ</label>
            <select v-model="serviceForm.serviceId" required class="w-full bg-gray-50 border border-gray-100 rounded-xl px-4 py-3 outline-none focus:border-blue-400 transition-all text-sm font-bold shadow-sm">
              <option value="">-- Chọn dịch vụ --</option>
              <option v-for="s in services" :key="s.id" :value="s.id">{{ s.name }} ({{ formatCurrency(s.price) }})</option>
            </select>
          </div>

          <div class="space-y-2">
            <label class="text-[10px] font-bold text-gray-400 uppercase tracking-widest ml-1">Số lượng</label>
            <div class="flex items-center gap-4 bg-gray-50 rounded-xl p-2 border border-gray-100">
              <button type="button" @click="serviceForm.quantity > 1 && serviceForm.quantity--" class="w-10 h-10 rounded-xl bg-white shadow-sm flex items-center justify-center text-gray-400 hover:text-blue-600 transition-all">
                <i class="fas fa-minus text-[10px]"></i>
              </button>
              <input type="number" v-model.number="serviceForm.quantity" min="1" class="flex-1 bg-transparent border-0 text-center text-sm font-bold text-gray-800 outline-none">
              <button type="button" @click="serviceForm.quantity++" class="w-10 h-10 rounded-xl bg-white shadow-sm flex items-center justify-center text-gray-400 hover:text-blue-600 transition-all">
                <i class="fas fa-plus text-[10px]"></i>
              </button>
            </div>
          </div>

          <div class="space-y-2">
            <label class="text-[10px] font-bold text-gray-400 uppercase tracking-widest ml-1">Ghi chú</label>
            <textarea v-model="serviceForm.note" rows="3" placeholder="Yêu cầu đặc biệt..." class="w-full bg-gray-50 border border-gray-100 rounded-xl px-4 py-3 outline-none focus:border-blue-400 transition-all text-sm font-bold shadow-sm resize-none"></textarea>
          </div>

          <button type="submit" class="w-full bg-[#3182ce] hover:bg-[#2b6cb0] text-white py-5 rounded-xl font-bold uppercase tracking-widest text-xs transition-all shadow-xl shadow-blue-100 mt-4">
            Xác nhận thêm
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from '../api/axios'
import StaffSidebar from '../components/StaffSidebar.vue'
import AdminSidebar from '../components/AdminSidebar.vue'
import jsPDF from 'jspdf'

const router = useRouter()
const isAdmin = computed(() => {
  const user = JSON.parse(localStorage.getItem('user') || '{}')
  return user.role === 'ADMIN'
})

const staffName = computed(() => {
  const user = JSON.parse(localStorage.getItem('user') || '{}')
  return user.fullName || 'Nhân viên'
})

const bookings = ref([])
const availableRooms = ref([])
const loading = ref(true)
const showModal = ref(false)
const showDetailModal = ref(false)
const showAddService = ref(false)
const isEditing = ref(false)
const selectedBooking = ref(null)
const invoice = ref(null)
const invoiceLoading = ref(false)
const showInvoiceModal = ref(false)
const services = ref([])
const currentServices = ref([])

const serviceForm = ref({
  serviceId: '',
  quantity: 1,
  note: ''
})

const fetchServices = async () => {
  try {
    const response = await axios.get('/hotel-services')
    services.value = response.data
  } catch (error) {
    console.error('Lỗi tải dịch vụ:', error)
  }
}

const fetchCurrentServices = async (bookingId) => {
  try {
    const response = await axios.get(`/hotel-services/booking/${bookingId}`)
    currentServices.value = response.data
  } catch (error) {
    console.error('Lỗi tải dịch vụ của khách:', error)
  }
}

const submitAddService = async () => {
   try {
     await axios.post('/hotel-services/add-to-booking', null, {
       params: {
         bookingId: selectedBooking.value.id,
         serviceId: serviceForm.value.serviceId,
         quantity: serviceForm.value.quantity,
         note: serviceForm.value.note
       }
     })
     showAddService.value = false
     serviceForm.value = { serviceId: '', quantity: 1, note: '' }
     fetchCurrentServices(selectedBooking.value.id)
     fetchInvoice(selectedBooking.value.id)
     alert('Thêm dịch vụ thành công!')
   } catch (error) {
     alert('Lỗi: ' + (error.response?.data?.message || 'Không thể thêm dịch vụ'))
   }
 }

// Notifications Logic
const showNotifications = ref(false)
const notifications = ref([
  { message: '🔔 Có khách vừa đặt phòng mới', time: '5 phút trước', read: false },
  { message: '🔔 Có booking chờ xác nhận', time: '10 phút trước', read: false },
  { message: '🔔 Khách vừa hủy đặt phòng', time: '1 giờ trước', read: true },
  { message: '🔔 Đặt phòng sắp đến ngày check-in', time: '2 giờ trước', read: false }
])

const unreadNotificationsCount = computed(() => notifications.value.filter(n => !n.read).length)

const toggleNotifications = () => {
  showNotifications.value = !showNotifications.value
}

const markAllAsRead = () => {
  notifications.value.forEach(n => n.read = true)
}

// Tabs Logic - Only allowed statuses
const tabItems = [
  { label: 'Tất cả', value: '' },
  { label: 'Chờ xác nhận', value: 'PENDING' },
  { label: 'Đã xác nhận', value: 'CONFIRMED' },
  { label: 'Đã nhận phòng', value: 'CHECKED_IN' },
  { label: 'Đã hoàn thành', value: 'COMPLETED' },
  { label: 'Đã hủy', value: 'CANCELLED' }
]

const filters = ref({
  search: '',
  timeRange: 'all',
  status: ''
})

const fetchBookings = async () => {
  loading.value = true
  try {
    const response = await axios.get('/bookings')
    bookings.value = response.data
  } catch (error) {
    console.error('Error fetching bookings:', error)
  } finally {
    loading.value = false
  }
}

// Only show allowed statuses (including CHECKED_IN for payment purposes)
const allowedStatuses = ['PENDING', 'CONFIRMED', 'CANCELLED', 'CHECKED_IN', 'COMPLETED']

const filteredBookings = computed(() => {
  return bookings.value.filter(b => {
    // Only include allowed statuses
    if (!allowedStatuses.includes(b.status)) return false
    
    const searchStr = filters.value.search.toLowerCase()
    const matchSearch = !filters.value.search || 
      (b.guestFullName || b.customer?.fullName || '').toLowerCase().includes(searchStr) ||
      (b.guestPhone || b.customer?.phone || '').includes(searchStr) ||
      String(b.id).includes(searchStr)
    
    const matchStatus = !filters.value.status || b.status === filters.value.status
    
    // Time range filter (simplified)
    const matchTime = true 
    
    return matchSearch && matchStatus && matchTime
  })
})

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const d = new Date(dateStr)
  return d.toLocaleDateString('vi-VN')
}

const formatDateTime = (dateStr) => {
  if (!dateStr) return '--'
  const d = new Date(dateStr)
  return d.toLocaleString('vi-VN', { 
    day: '2-digit', month: '2-digit', year: 'numeric',
    hour: '2-digit', minute: '2-digit'
  })
}

const formatCurrency = (amount) => {
  if (!amount) return '0 đ'
  return new Intl.NumberFormat('vi-VN').format(amount * 25000) + ' đ'
}

const getStatusClass = (status) => {
  switch (status) {
    case 'PENDING': return 'bg-amber-100 text-amber-700'
    case 'IN_PROGRESS': return 'bg-blue-100 text-blue-700'
    case 'COMPLETED': return 'bg-emerald-100 text-emerald-700'
    default: return 'bg-gray-100 text-gray-700'
  }
}

const getServiceIcon = (name) => {
  const n = name?.toLowerCase() || ''
  if (n.includes('breakfast') || n.includes('ăn')) return 'fas fa-utensils'
  if (n.includes('laundry') || n.includes('giặt')) return 'fas fa-tshirt'
  if (n.includes('drink') || n.includes('nước')) return 'fas fa-glass-martini-alt'
  return 'fas fa-concierge-bell'
}

const calculateNights = (checkIn, checkOut) => {
  if (!checkIn || !checkOut) return 0
  const start = new Date(checkIn)
  const end = new Date(checkOut)
  const diff = end - start
  return Math.max(1, Math.ceil(diff / (1000 * 60 * 60 * 24)))
}

const formatStatus = (status) => {
  switch (status) {
    case 'PENDING': return 'Chờ xác nhận'
    case 'CONFIRMED': return 'Đã xác nhận'
    case 'CHECKED_IN': return 'Đã nhận phòng'
    case 'COMPLETED': return 'Đã hoàn thành'
    case 'CANCELLED': return 'Đã hủy'
    default: return status
  }
}

const getStatusStyles = (status) => {
  switch (status) {
    case 'PENDING': return 'bg-amber-50 text-amber-600 border border-amber-100'
    case 'CONFIRMED': return 'bg-blue-50 text-blue-600 border border-blue-100'
    case 'CHECKED_IN': return 'bg-emerald-50 text-emerald-600 border border-emerald-100'
    case 'COMPLETED': return 'bg-purple-50 text-purple-600 border border-purple-100'
    case 'CANCELLED': return 'bg-rose-50 text-rose-600 border border-rose-100'
    default: return 'bg-gray-50 text-gray-600 border border-gray-100'
  }
}

const getStatusDotClass = (status) => {
  switch (status) {
    case 'PENDING': return 'bg-amber-500'
    case 'CONFIRMED': return 'bg-blue-500'
    case 'CHECKED_IN': return 'bg-emerald-500'
    case 'COMPLETED': return 'bg-purple-500'
    case 'CANCELLED': return 'bg-rose-500'
    default: return 'bg-gray-400'
  }
}

const formatPaymentStatus = (status) => {
  return status === 'PAID' ? 'Đã thanh toán' : 'Chưa thanh toán'
}

const formatPaymentMethod = (method) => {
  switch (method) {
    case 'VNPAY': return 'VNPay'
    case 'MOMO': return 'MoMo'
    case 'COD': return 'Tiền mặt'
    default: return method || 'Tiền mặt'
  }
}

const openInvoiceModal = async () => {
  if (!selectedBooking.value?.id) return
  await fetchInvoice(selectedBooking.value.id)
  showInvoiceModal.value = true
}

const printInvoice = () => {
  window.print()
}

const downloadInvoicePDF = () => {
  const doc = new jsPDF()
  const b = selectedBooking.value
  const inv = invoice.value
  doc.setFontSize(18)
  doc.text('HOA DON THANH TOAN', 105, 20, { align: 'center' })
  doc.setFontSize(11)
  doc.text(`Ma HD: HD${String(inv?.id || b?.id).padStart(8, '0')}`, 20, 35)
  doc.text(`Ma dat phong: BK${String(b?.id).padStart(8, '0')}`, 20, 42)
  doc.text(`Khach: ${b?.guestFullName || b?.customer?.fullName || ''}`, 20, 52)
  doc.text(`Phong: ${b?.room?.roomNumber} - ${b?.room?.category || ''}`, 20, 59)
  doc.text(`Tien phong: ${formatCurrency(inv?.roomCharges)}`, 20, 72)
  doc.text(`Dich vu: ${formatCurrency(inv?.serviceCharges)}`, 20, 79)
  doc.text(`Tong: ${formatCurrency(inv?.totalAmount)}`, 20, 86)
  doc.text(`Ngay TT: ${formatDateTime(inv?.paymentDate)}`, 20, 93)
  doc.save(`HoaDon_BK${String(b?.id).padStart(8, '0')}.pdf`)
}

const getPaymentStatusStyles = (status) => {
  return status === 'PAID' 
    ? 'bg-emerald-50 text-emerald-600' 
    : 'bg-amber-50 text-amber-600'
}

const fetchInvoice = async (bookingId) => {
  invoiceLoading.value = true
  invoice.value = null
  try {
    const response = await axios.get(`/invoices/booking/${bookingId}`)
    invoice.value = response.data
  } catch (error) {
    console.error('Error fetching invoice:', error)
  } finally {
    invoiceLoading.value = false
  }
}

const openDetailModal = (booking) => {
     selectedBooking.value = booking
     showDetailModal.value = true
     fetchCurrentServices(booking.id)
     fetchInvoice(booking.id)
   }

const fetchRooms = async () => {
  try {
    const response = await axios.get('/rooms')
    availableRooms.value = response.data.filter(r => r.status === 'AVAILABLE')
  } catch (error) {
    console.error('Error fetching rooms:', error)
  }
}

const form = ref({
  guestFullName: '',
  guestContact: '',
  checkInDate: '',
  checkOutDate: '',
  roomId: '',
  numberOfGuests: 1
})

const openCreateModal = () => {
  isEditing.value = false
  form.value = {
    guestFullName: '',
    guestContact: '',
    checkInDate: '',
    checkOutDate: '',
    roomId: '',
    numberOfGuests: 1
  }
  showModal.value = true
  fetchRooms()
}

const openUpdateModal = (booking) => {
  isEditing.value = true
  selectedBooking.value = booking
  form.value = {
    guestFullName: booking.guestFullName || booking.customer?.fullName,
    guestContact: booking.guestPhone || booking.customer?.phone || booking.guestEmail || booking.customer?.email,
    checkInDate: booking.checkInDate,
    checkOutDate: booking.checkOutDate,
    roomId: booking.room?.id,
    numberOfGuests: booking.numberOfGuests || 1
  }
  showModal.value = true
  fetchRooms()
  if (booking.room && !availableRooms.value.find(r => r.id === booking.room.id)) {
    availableRooms.value.push(booking.room)
  }
}

const closeModal = () => {
  showModal.value = false
  selectedBooking.value = null
}

const handleSubmit = async () => {
  try {
    const user = JSON.parse(localStorage.getItem('user'))
    const payload = {
      ...form.value,
      userId: user.id,
      guestPhone: form.value.guestContact.includes('@') ? '' : form.value.guestContact,
      guestEmail: form.value.guestContact.includes('@') ? form.value.guestContact : ''
    }

    if (isEditing.value) {
      await axios.put(`/bookings/${selectedBooking.value.id}/update`, payload)
      if (form.value.roomId !== selectedBooking.value.room?.id) {
        await axios.put(`/bookings/${selectedBooking.value.id}/change-room?newRoomId=${form.value.roomId}`)
      }
    } else {
      await axios.post('/bookings', payload)
    }
    
    showModal.value = false
    fetchBookings()
    alert(isEditing.value ? 'Cập nhật thành công!' : 'Đã tạo booking thành công!')
  } catch (error) {
    alert('Lỗi: ' + (error.response?.data?.message || 'Không thể thực hiện'))
  }
}

const handleConfirm = async (id) => {
  if (!confirm('Xác nhận đặt phòng này?')) return
  try {
    await axios.put(`/bookings/${id}/confirm`)
    fetchBookings()
    if (showDetailModal.value) {
      // Refresh the selected booking
      const updatedBooking = bookings.value.find(b => b.id === id)
      if (updatedBooking) {
        selectedBooking.value = updatedBooking
      }
    }
    alert('Xác nhận đặt phòng thành công!')
  } catch (error) {
    alert('Lỗi: ' + (error.response?.data?.message || 'Không thể xác nhận'))
  }
}

const handlePayment = async (id) => {
  await fetchInvoice(id)
  const total = invoice.value?.totalAmount ?? selectedBooking.value?.totalPrice ?? 0
  const room = invoice.value?.roomCharges ?? 0
  const services = invoice.value?.serviceCharges ?? 0
  const msg = `Xác nhận thu tiền?\n\nTiền phòng: ${formatCurrency(room)}\nDịch vụ: ${formatCurrency(services)}\nTổng: ${formatCurrency(total)}`
  if (!confirm(msg)) return
  try {
    await axios.put(`/bookings/${id}/mark-paid`)
    await fetchBookings()
    if (showDetailModal.value) {
      const updatedBooking = bookings.value.find(b => b.id === id)
      if (updatedBooking) {
        selectedBooking.value = updatedBooking
        await fetchInvoice(id)
      }
    }
    alert('Thanh toán thành công! Bạn có thể in hóa đơn ngay bây giờ.')
    await openInvoiceModal()
  } catch (error) {
    alert('Lỗi: ' + (error.response?.data?.message || 'Không thể thanh toán'))
  }
}

const handleCancel = async (id) => {
  if (!confirm('Xác nhận hủy đặt phòng?')) return
  try {
    const user = JSON.parse(localStorage.getItem('user'))
    await axios.put(`/bookings/${id}/cancel?userId=${user.id}&isAdmin=true`)
    fetchBookings()
    if (showDetailModal.value) {
      // Refresh the selected booking
      const updatedBooking = bookings.value.find(b => b.id === id)
      if (updatedBooking) {
        selectedBooking.value = updatedBooking
      }
    }
  } catch (error) {
    console.error('Cancel failed:', error)
  }
}

onMounted(() => {
  fetchBookings()
  fetchServices()
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
  #invoice-print-area,
  #invoice-print-area * {
    visibility: visible;
  }
  #invoice-print-area {
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
  }
}
</style>
