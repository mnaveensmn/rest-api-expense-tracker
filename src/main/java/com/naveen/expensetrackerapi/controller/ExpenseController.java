package com.naveen.expensetrackerapi.controller;

import com.naveen.expensetrackerapi.entity.Expense;
import com.naveen.expensetrackerapi.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

@RestController
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/expenses")
    public List<Expense> getAllExpenses(Pageable page) {
        return expenseService.getAllExpenses(page).toList();
    }

    @GetMapping("/expenses/{id}")
    public Expense geExpenseById(@PathVariable Long id) throws Exception {
        return expenseService.getExpenseById(id);
    }

//    @GetMapping("/expenses")
//    public String geExpenseById(@RequestParam("id") Long id) {
//        return "The expense id is "+id;
//    }

    @GetMapping("/users/{userId}/expenses/{id}")
    public String getExpenseByUserId(@PathVariable("userId") Long user,
                                     @PathVariable Long id) {
        return "The expense id is " + id + ". User Id is " + user;
    }

//    @GetMapping("/users/expenses")
//    public String getExpenseByUserId(@RequestParam("userId") Long user,
//                                     @RequestParam Long id) {
//        return "The expense id is "+id+". User Id is "+user;
//    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/expenses")
    public void deleteExpenseById(@RequestParam Long id) {
        expenseService.deleteExpenseById(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/expenses")
    public Expense saveExpenseDetails(@Valid @RequestBody Expense expense) {
        return expenseService.saveExpense(expense);
    }

    @PutMapping("/expenses/{id}")
    public Expense updateExpenseDetails(@RequestBody Expense expense, @PathVariable Long id) {
        return expenseService.updateExpense(id, expense);
    }

    @GetMapping("/expenses/category")
    public List<Expense> getAllExpensesByCategory(@RequestParam String category, Pageable page) {
        return expenseService.findByCategory(category, page);
    }

    @GetMapping("/expenses/name")
    public List<Expense> getAllExpensesByName(@RequestParam String name, Pageable page) {
        return expenseService.findByName(name, page);
    }

    @GetMapping("/expenses/date")
    public List<Expense> getAllExpensesByDate(@RequestParam(required = false) Date startDate,
                                              @RequestParam(required = false) Date endDate,
                                              Pageable page) {
        return expenseService.findByDate(startDate, endDate, page);
    }
}