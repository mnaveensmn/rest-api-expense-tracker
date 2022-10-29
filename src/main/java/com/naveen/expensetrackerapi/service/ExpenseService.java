package com.naveen.expensetrackerapi.service;

import com.naveen.expensetrackerapi.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

public interface ExpenseService {
    Page<Expense> getAllExpenses(Pageable page);

    Expense getExpenseById(Long id);

    void deleteExpenseById(Long id);

    Expense saveExpense(Expense expense);

    Expense updateExpense(Long id, Expense expense);

    List<Expense> findByCategory(String category, Pageable page);

    List<Expense> findByName(String name, Pageable page);

    List<Expense> findByDate(Date startDate,Date endDate, Pageable page);

}
