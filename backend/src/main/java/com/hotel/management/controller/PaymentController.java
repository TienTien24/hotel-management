package com.hotel.management.controller;

import com.hotel.management.enums.PaymentStatus;
import com.hotel.management.model.Booking;
import com.hotel.management.repository.BookingRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private BookingRepository bookingRepository;

    private static final String VNP_TMN_CODE = "YOUR_VNP_TMN_CODE";
    private static final String VNP_HASH_SECRET = "YOUR_VNP_HASH_SECRET";
    private static final String VNP_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    private static final String VNP_RETURN_URL = "http://localhost:8080/api/payments/vnpay-return";

    private static final String MOMO_ENDPOINT = "https://test-payment.momo.vn/v2/gateway/api/create";
    private static final String MOMO_PARTNER_CODE = "MOMO_PARTNER_CODE";
    private static final String MOMO_ACCESS_KEY = "MOMO_ACCESS_KEY";
    private static final String MOMO_SECRET_KEY = "MOMO_SECRET_KEY";
    private static final String MOMO_RETURN_URL = "http://localhost:8080/api/payments/momo-return";

    @PostMapping("/vnpay/{bookingId}")
    public ResponseEntity<Map<String, String>> createVnpayPayment(@PathVariable Long bookingId,
                                                                   HttpServletRequest request) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        long amount = (long) (booking.getTotalPrice() * 100);
        String bankCode = request.getParameter("bankCode");

        Map<String, String> vnpParams = new TreeMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", VNP_TMN_CODE);
        vnpParams.put("vnp_Amount", String.valueOf(amount));
        vnpParams.put("vnp_BankCode", bankCode != null ? bankCode : "");
        vnpParams.put("vnp_CreateDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        vnpParams.put("vnp_CurrCode", "VND");
        vnpParams.put("vnp_IpAddr", getClientIp(request));
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_OrderInfo", "Thanh toan don hang #" + bookingId);
        vnpParams.put("vnp_OrderType", "billpayment");
        vnpParams.put("vnp_ReturnUrl", VNP_RETURN_URL);
        vnpParams.put("vnp_TxnRef", String.valueOf(bookingId));

        String query = buildQuery(vnpParams);
        String signValue = hmacSHA512(VNP_HASH_SECRET, query);
        String paymentUrl = VNP_URL + "?" + query + "&vnp_SecureHashType=HmacSHA512&vnp_SecureHash=" + signValue;

        Map<String, String> response = new HashMap<>();
        response.put("paymentUrl", paymentUrl);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/vnpay-return")
    public void vnpayReturn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String vnpResponseCode = request.getParameter("vnp_ResponseCode");
        String bookingId = request.getParameter("vnp_TxnRef");

        if ("00".equals(vnpResponseCode)) {
            bookingRepository.findById(Long.parseLong(bookingId)).ifPresent(booking -> {
                booking.setPaymentStatus(PaymentStatus.PAID);
                bookingRepository.save(booking);
            });
            response.sendRedirect("http://localhost:3002/bookings?payment=success");
        } else {
            response.sendRedirect("http://localhost:3002/bookings?payment=failed");
        }
    }

    @PostMapping("/momo/{bookingId}")
    public ResponseEntity<Map<String, String>> createMomoPayment(@PathVariable Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        String orderId = "MOMO_" + bookingId + "_" + System.currentTimeMillis();
        String requestId = String.valueOf(System.currentTimeMillis());
        long amount = (long) (booking.getTotalPrice() * 25000);

        Map<String, String> params = new HashMap<>();
        params.put("partnerCode", MOMO_PARTNER_CODE);
        params.put("accessKey", MOMO_ACCESS_KEY);
        params.put("requestId", requestId);
        params.put("amount", String.valueOf(amount));
        params.put("orderId", orderId);
        params.put("orderInfo", "Thanh toan don hang #" + bookingId);
        params.put("returnUrl", MOMO_RETURN_URL);
        params.put("notifyUrl", MOMO_RETURN_URL);
        params.put("requestType", "payWithATM");

        String signature = hmacSHA256(params, MOMO_SECRET_KEY);
        params.put("signature", signature);

        Map<String, String> response = new HashMap<>();
        response.put("payUrl", MOMO_ENDPOINT + "?" + buildQuery(params));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/momo-return")
    public void momoReturn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String resultCode = request.getParameter("resultCode");

        if ("0".equals(resultCode)) {
            String[] parts = request.getParameter("orderId").split("_");
            if (parts.length >= 2) {
                Long bookingId = Long.parseLong(parts[1]);
                bookingRepository.findById(bookingId).ifPresent(booking -> {
                    booking.setPaymentStatus(PaymentStatus.PAID);
                    bookingRepository.save(booking);
                });
            }
            response.sendRedirect("http://localhost:3002/bookings?payment=success");
        } else {
            response.sendRedirect("http://localhost:3002/bookings?payment=failed");
        }
    }

    private String buildQuery(Map<String, String> params) {
        List<String> fields = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                try {
                    fields.add(entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
                } catch (UnsupportedEncodingException e) {
                    fields.add(entry.getKey() + "=" + entry.getValue());
                }
            }
        }
        return String.join("&", fields);
    }

    private String hmacSHA512(String key, String data) {
        try {
            javax.crypto.Mac hmac = javax.crypto.Mac.getInstance("HmacSHA512");
            javax.crypto.spec.SecretKeySpec secretKeySpec = new javax.crypto.spec.SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac.init(secretKeySpec);
            byte[] hash = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            return "";
        }
    }

    private String hmacSHA256(Map<String, String> params, String secretKey) {
        StringBuilder data = new StringBuilder();
        params.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    if (!entry.getKey().equals("signature")) {
                        try {
                            data.append(entry.getKey());
                            data.append("=");
                            data.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
                            data.append("&");
                        } catch (UnsupportedEncodingException ignored) {}
                    }
                });
        data.deleteCharAt(data.length() - 1);
        return hmacSHA512(secretKey, data.toString());
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}