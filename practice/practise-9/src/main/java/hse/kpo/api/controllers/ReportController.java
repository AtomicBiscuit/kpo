package hse.kpo.api.controllers;

import hse.kpo.api.requests.SalesExportRequest;
import hse.kpo.api.requests.TransportExportRequest;
import hse.kpo.enums.ReportFormat;
import hse.kpo.facade.Hse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Отчёты", description = "Получение отчётов по операциям")
public class ReportController {
    private final Hse hseFacade;

    @PostMapping("/sales")
    @Operation(summary = "Сгенерировать отчёт о продажах")
    public ResponseEntity<String> getSalesReport(@Valid @RequestBody SalesExportRequest request,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    bindingResult.getAllErrors().getFirst().getDefaultMessage()
            );
        }

        var format = ReportFormat.valueOf(request.reportType());

        var buf = new ByteArrayOutputStream();

        hseFacade.exportReport(format, new PrintWriter(buf));

        return ResponseEntity.ok(buf.toString());
    }

    @PostMapping("/transport")
    @Operation(summary = "Сгенерировать транспортный отчёт")
    public ResponseEntity<String> getTransportReport(@Valid @RequestBody TransportExportRequest request,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    bindingResult.getAllErrors().getFirst().getDefaultMessage()
            );
        }

        var format = ReportFormat.valueOf(request.reportType());

        var buf = new ByteArrayOutputStream();

        hseFacade.exportTransport(format, new PrintWriter(buf));

        return ResponseEntity.ok(buf.toString());
    }
}