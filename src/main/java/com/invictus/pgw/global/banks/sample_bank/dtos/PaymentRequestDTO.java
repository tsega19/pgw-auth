package com.dacloud.pgw.global.banks.sample_bank.dtos;

public record PaymentRequestDTO(
      double amount,
      String nonce,
      String redirectUrl,
      String successUrl,
      String errorUrl,
      String cancelUrl,
      String description
) {
}
