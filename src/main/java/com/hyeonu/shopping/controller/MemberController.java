package com.hyeonu.shopping.controller;

import com.hyeonu.shopping.dto.request.SignupRequestDto;
import com.hyeonu.shopping.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @RequestMapping("/api/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequestDto dto, BindingResult result) {
        StringBuilder errors = new StringBuilder();
        if (result.hasErrors()) {
            for(FieldError error: result.getFieldErrors()) {
                errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append(", ");
            }
            return ResponseEntity.badRequest().body(errors.toString());
        }

        if (memberService.duplicationUsernameCheck(dto.getUsername())) {
            errors.append("username: 동일한 아이디가 존재합니다, ");
            return ResponseEntity.badRequest().body(errors.toString());
        }

        if (memberService.checkPassword((dto.getPassword()), dto.getCheckPassword())) {
            errors.append("password: 비밀번호가 동일하지 않습니다,");
            return ResponseEntity.badRequest().body(errors.toString());
        }

        if (memberService.signup(dto)) {
            return ResponseEntity.badRequest().body(errors.toString());
        }

        return ResponseEntity.ok().body("login");
    }
}
