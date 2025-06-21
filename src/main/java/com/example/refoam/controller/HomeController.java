package com.example.refoam.controller;

import com.example.refoam.domain.Employee;
import com.example.refoam.domain.MaterialName;
import com.example.refoam.domain.Orders;
import com.example.refoam.domain.QualityCheck;
import com.example.refoam.dto.LoginForm;
import com.example.refoam.dto.ProductionMonitoring;
import com.example.refoam.repository.OrderRepository;
import com.example.refoam.repository.QualityCheckRepository;
import com.example.refoam.service.LoginService;
import com.example.refoam.service.MaterialService;
import com.example.refoam.service.MonitoringService;
import com.example.refoam.service.OpenAiService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.function.Function;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final LoginService loginService;
    private final MonitoringService monitoringService;

    @GetMapping("/")
    public String home(HttpSession session, Model model,
                       @RequestParam(value = "redirectURL", defaultValue = "/main") String redirectURL) {
        if (session.getAttribute(SessionConst.LOGIN_MEMBER) != null) {
            return "redirect:" + redirectURL;
        }
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("redirectURL", redirectURL);
        return "home";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, @RequestParam(value = "redirectURL", defaultValue = "/main")String redirectURL, HttpServletRequest request){
        if (bindingResult.hasErrors()){
            return "home";
        }

        Employee loginMember = loginService.login(loginForm.getLoginId(),loginForm.getPassword());

        if (loginMember == null){
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
            return "home";
        }
        // 로그인 성공
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER,loginMember);
        log.info("직위: {}", loginMember.getPosition());

        // 원래 가려던 URL로 이동
        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);

        if (session != null){
            session.invalidate();
        }
        // 로그아웃 후 로그인 페이지에서 로그인 할 시 새로고침 한 번 일어나는거 때문에 /로 다시 바꿈
        return "redirect:/";
    }

    @GetMapping("/main")
    public String main(Model model){
        Map<String, Integer> kpiMap = monitoringService.targetAchievement(150, 250);

        int targetQuantity = monitoringService.getTodayTarget(150,250);
        int okCount = kpiMap.getOrDefault("okCount", 0);
        int achievementRate = kpiMap.getOrDefault("achievementRate", 0);

        model.addAttribute("achievementRate", achievementRate);
        model.addAttribute("targetRate", 80); // 목표달성률 80 고정
        model.addAttribute("targetQuantity", targetQuantity);
        model.addAttribute("okCount", okCount);

        // targetAchieveQuantity는 Map에 없으니 따로 계산하거나 제거
        model.addAttribute("targetAchieveQuantity", targetQuantity > 0 ? okCount : 0);
        return "main";
    }

    // 로그아웃 후 다른 아이디로 로그인했을 때 404 에러 뜨는거 방지용으로 만듦
    @GetMapping("/home")
    public String homeRedirect(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "home";
    }
}
