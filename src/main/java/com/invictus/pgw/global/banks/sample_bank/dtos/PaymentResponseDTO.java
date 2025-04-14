package com.dacloud.pgw.global.banks.sample_bank.dtos;

public record PaymentResponseDTO(
      String status,
      String paymentUrl
) {
}
