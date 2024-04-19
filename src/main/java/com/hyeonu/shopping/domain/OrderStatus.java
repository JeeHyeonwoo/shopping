package com.hyeonu.shopping.domain;

public enum OrderStatus {
    CANCEL(-1), // 주문 취소
    Before(0),  // 입금 전
    After(1),   // 입금 후
    READY(2),   // 배송 준비
    START(3),   // 배송 출발
    ARRIVED(4); //배송 도착

    private int status;

    OrderStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
