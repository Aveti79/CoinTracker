package com.aveti.CoinTracker.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/addcoin")
    String viewAddCoinForm() {
        return "addCoinPanel";
    }
}
