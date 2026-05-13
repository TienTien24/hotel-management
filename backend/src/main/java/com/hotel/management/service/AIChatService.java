package com.hotel.management.service;

import com.hotel.management.model.Room;
import com.hotel.management.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AIChatService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Autowired
    private RoomRepository roomRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getAIResponse(String userMessage) {
        try {
            List<Room> rooms = roomRepository.findAll();
            String roomContext = rooms.stream()
                    .map(r -> String.format("- Phòng %s (%s): Giá %.0f VND, Trạng thái: %s", 
                            r.getRoomNumber(), r.getCategory(), r.getPrice(), r.getStatus()))
                    .collect(Collectors.joining("\n"));

            String systemPrompt = "Bạn là trợ lý ảo thông minh của Grand Hotel. " +
                    "Dưới đây là thông tin hiện tại về các phòng của khách sạn:\n" + roomContext + "\n\n" +
                    "Hãy trả lời khách hàng một cách lịch sự, chuyên nghiệp bằng tiếng Việt. " +
                    "Nếu khách hỏi về giá hoặc tình trạng phòng, hãy dựa vào dữ liệu trên để trả lời chính xác. " +
                    "Nếu khách muốn đặt phòng, hãy hướng dẫn họ vào mục 'Loại Phòng' trên website.";

            Map<String, Object> requestBody = new HashMap<>();
            List<Map<String, Object>> contents = new ArrayList<>();
            Map<String, Object> content = new HashMap<>();
            List<Map<String, Object>> parts = new ArrayList<>();
            Map<String, Object> part = new HashMap<>();
            
            part.put("text", systemPrompt + "\n\nKhách hàng: " + userMessage);
            parts.add(part);
            content.put("parts", parts);
            contents.add(content);
            requestBody.put("contents", contents);

            String fullUrl = apiUrl + "?key=" + apiKey;
            
            Map<String, Object> response = restTemplate.postForObject(fullUrl, requestBody, Map.class);
            
            if (response != null && response.containsKey("candidates")) {
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
                if (!candidates.isEmpty()) {
                    Map<String, Object> firstCandidate = candidates.get(0);
                    Map<String, Object> contentResp = (Map<String, Object>) firstCandidate.get("content");
                    List<Map<String, Object>> partsResp = (List<Map<String, Object>>) contentResp.get("parts");
                    if (!partsResp.isEmpty()) {
                        return (String) partsResp.get(0).get("text");
                    }
                }
            }
            
            return "Xin lỗi, tôi đang gặp chút khó khăn khi kết nối. Bạn có thể thử lại sau hoặc liên hệ trực tiếp với chúng tôi nhé!";
        } catch (Exception e) {
            System.err.println("Error calling Gemini API: " + e.getMessage());
            return "Chào bạn! Hiện tại tôi đang được bảo trì để thông minh hơn. Bạn có thể tham khảo giá phòng trực tiếp tại mục 'Loại Phòng' nhé!";
        }
    }
}
