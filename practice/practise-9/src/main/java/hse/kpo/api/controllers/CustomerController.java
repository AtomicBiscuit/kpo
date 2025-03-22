package hse.kpo.api.controllers;

import hse.kpo.api.requests.CustomerRequest;
import hse.kpo.domains.Customer;
import hse.kpo.facade.Hse;
import hse.kpo.storages.CustomerStorage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Покупатели", description = "Управление покупателями")
public class CustomerController {
    private final CustomerStorage customerStorage;

    private final Hse hseFacade;

    @GetMapping("/{name}")
    @Operation(summary = "Получить пользователя по имени")
    public ResponseEntity<Customer> getCustomerByName(@PathVariable String name) {
        return customerStorage.getCustomers()
                              .stream()
                              .filter(customer -> Objects.equals(customer.getName(), name))
                              .findFirst()
                              .map(ResponseEntity::ok)
                              .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать пользователя")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerRequest request,
                                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    bindingResult.getAllErrors().getFirst().getDefaultMessage()
            );
        }

        var customer = hseFacade.addCustomer(request.name(), request.legPower(), request.handPower(), request.iq());

        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @PostMapping("/buy")
    @Operation(summary = "Купить весь доступный транспорт")
    public ResponseEntity<Void> buyAllTransport() {
        hseFacade.sell();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{name}")
    @Operation(summary = "Обновить покупателя")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String name,
                                                   @Valid @RequestBody CustomerRequest request) {
        var optionalCustomer = customerStorage.getCustomers()
                                              .stream()
                                              .filter(customer -> Objects.equals(customer.getName(), name))
                                              .findFirst();
        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var customer = optionalCustomer.get();
        customer.setIq(request.iq());
        customer.setHandPower(request.handPower());
        customer.setLegPower(request.legPower());

        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{name}")
    @Operation(summary = "Удалить покупателя")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String name) {
        boolean removed = customerStorage.getCustomers().removeIf(customer -> Objects.equals(customer.getName(), name));
        return removed ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}