//package com.dacloud.pgw.global.banks.sample_bank;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.dacloud.pgw.checkout.entities.CheckoutSession;
//import com.dacloud.pgw.global.banks.sample_bank.dtos.PaymentRequestDTO;
//import com.dacloud.pgw.global.banks.sample_bank.dtos.PaymentResponseDTO;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//@Component
//public class SampleBank {
//   public static final String bankId = "smpl";
//   private static final ObjectMapper mapper = new ObjectMapper();
//
//   private final RestTemplate restTemplate = new RestTemplate();
//
//
//   public PaymentResponseDTO process_payment(CheckoutSession checkoutSession) {
//
//      final var redirectUrl_temp = checkoutSession.getRedirectUrl().replace("196.188.127.204", "196.189.61.66");
//
//      final var requestBodyValue = new PaymentRequestDTO(
//            checkoutSession.getAmount(),
//            checkoutSession.getId().toString(),
//            "http://196.189.61.66:5083/redirect/" + checkoutSession.getId(),
//            "http://196.189.61.66:5080/api/checkout/hooks/success/" + checkoutSession.getId(),
//            "http://196.189.61.66:5080/api/checkout/hooks/error/" + checkoutSession.getId(),
//            "http://196.189.61.66:5080/api/checkout/hooks/cancel/" + checkoutSession.getId(),
//            checkoutSession.getDescription()
//      );
//
//      String x;
//
//      try {
//         x = mapper.writeValueAsString(requestBodyValue);
//         System.out.println(x);
//      } catch (JsonProcessingException e) {
//         e.printStackTrace();
//         throw new RuntimeException(e);
//      }
//
//      final var requestHeaders = new HttpHeaders();
//      requestHeaders.setContentType(MediaType.APPLICATION_JSON);
//      requestHeaders.add("Accept", "application/json");
//
//      HttpHeaders headers = new HttpHeaders();
//      headers.setContentType(MediaType.APPLICATION_JSON);
//      HttpEntity<String> requestEntity = new HttpEntity<>(x, headers);
//
//      String url = "http://localhost:5086/api/demo/paymentRequest";
//      final var responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
//
//      String responseBody = responseEntity.getBody();
//      System.out.println(responseBody);
//
//      try {
//         return mapper.readValue(responseBody, PaymentResponseDTO.class);
//      } catch (JsonProcessingException e) {
//         e.printStackTrace();
//         throw new RuntimeException(e);
//      }
//   }
//
//   public String getId() {
//      return bankId;
//   }
//}
