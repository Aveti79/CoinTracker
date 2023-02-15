package com.aveti.CoinTracker.view;

import com.aveti.CoinTracker.logic.GainsTableService;
import com.aveti.CoinTracker.logic.TransactionService;
import com.aveti.CoinTracker.model.projection.TransactionWriteModel;
import com.aveti.CoinTracker.model.repository.CoinDetailsRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ViewController {

    private final GainsTableService gainsTableService;
    private final TransactionService transactionService;
    private final CoinDetailsRepository coinDetailsRepository;

    ViewController(final GainsTableService gainsTableService, final TransactionService transactionService,
                   final CoinDetailsRepository coinDetailsRepository) {
        this.gainsTableService = gainsTableService;
        this.transactionService = transactionService;
        this.coinDetailsRepository = coinDetailsRepository;
    }

    @GetMapping
    String viewDashboard(Model model) {
        model.addAttribute("coinRows",
                coinDetailsRepository.findAll(PageRequest.of(0,100, Sort.by("marketCap")
                        .descending())).toList()
        );
        return "dashboard";
    }

    @GetMapping("/transactions")
    String viewTransactionsList(Model model) {
        model.addAttribute("transactions", transactionService.readAllTransactionsWithIcons());
        return "transactionList";
    }

    @GetMapping("/addTransaction")
    String viewAddTransactionForm(Model model) {
        model.addAttribute("transaction", new TransactionWriteModel());
        return "addTransactionPanel";
    }

    @PostMapping(path = "/addTransaction", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    String addTransaction(@ModelAttribute("transaction") @Valid TransactionWriteModel toPost,
                          BindingResult result,
                          Model model) {
        if (result.hasErrors()) {
            return "addTransactionPanel";
        }
        transactionService.createTransaction(toPost);
        return "redirect:/addTransaction";
    }

    @GetMapping("/gains")
    String viewGainsAndLossTable(Model model) {
        var tableRows = gainsTableService.getTableRows();
        model.addAttribute("gainTableRows", tableRows);
        model.addAttribute("gainTableRowsSum", gainsTableService.getTableRowsSum(tableRows));
        return "gainsTable";
    }

    @ModelAttribute("transaction")
    public TransactionWriteModel getTransaction() {
        return new TransactionWriteModel();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> IllegalArgumentExceptionHandler(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
