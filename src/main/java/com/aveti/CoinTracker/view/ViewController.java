package com.aveti.CoinTracker.view;

import com.aveti.CoinTracker.logic.GainsTableService;
import com.aveti.CoinTracker.logic.TransactionService;
import com.aveti.CoinTracker.model.Transaction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;

@Controller
public class ViewController {

    private final GainsTableService gainsTableService;
    private final TransactionService transactionService;

    ViewController(final GainsTableService gainsTableService, final TransactionService transactionService) {
        this.gainsTableService = gainsTableService;
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions")
    String viewTransactionsList(Model model) {
        model.addAttribute("transactions", transactionService.readAllTransactions());
        return "transactionList";
    }

    @GetMapping("/addTransaction")
    String viewAddTransactionForm(Model model) {
        model.addAttribute("transaction", new Transaction());
        return "addTransactionPanel";
    }

    @PostMapping(path = "/addTransaction", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    String addTransaction(@ModelAttribute("transaction") @Valid Transaction toPost,
                          BindingResult result,
                          Model model) {
        if (result.hasErrors()) {
            return "addTransactionPanel";
        }
        transactionService.createTransaction(toPost);
        return "addTransactionPanel";
    }

    @GetMapping("/gains")
    String viewGainsAndLossTable(Model model) {
        model.addAttribute("gainTableRows", gainsTableService.getTableRows());
        return "gainsTable";
    }

    @ModelAttribute("transaction")
    public Transaction getTransaction() {
        return new Transaction();
    }

}
