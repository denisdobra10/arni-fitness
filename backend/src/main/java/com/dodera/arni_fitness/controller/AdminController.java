package com.dodera.arni_fitness.controller;

import com.dodera.arni_fitness.dto.details.ClientDetails;
import com.dodera.arni_fitness.dto.details.MembershipDetails;
import com.dodera.arni_fitness.dto.details.SessionDetails;
import com.dodera.arni_fitness.dto.details.StatisticsDetails;
import com.dodera.arni_fitness.dto.request.ClassRequest;
import com.dodera.arni_fitness.dto.request.MembershipRequest;
import com.dodera.arni_fitness.dto.request.SessionRequest;
import com.dodera.arni_fitness.dto.response.AdminDetailsResponse;
import com.dodera.arni_fitness.dto.response.Response;
import com.dodera.arni_fitness.model.ClassEntity;
import com.dodera.arni_fitness.model.Coach;
import com.dodera.arni_fitness.model.Item;
import com.dodera.arni_fitness.model.Membership;
import com.dodera.arni_fitness.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // ADMIN

    // Endpoint pentru a lua toate detaliile
    @GetMapping("/details")
    public AdminDetailsResponse getDetails() {
        return adminService.getDetails();
    }

    @GetMapping("/statistics")
    public StatisticsDetails getStatistics() {
        return adminService.getStatistics();
    }

//    @GetMapping("/memberships")
//    public List<MembershipDetails> getMembership() {
//        return adminService.getMembershipsDetails();
//    }
//
//    @GetMapping("/classes")
//    public StatisticsDetails pulamea() {
//        return adminService.getStatistics();
//    }
//
//    @GetMapping("/coaches")
//    public StatisticsDetails pulata() {
//        return adminService.getStatistics();
//    }
//
//    @GetMapping("/sessions")
//    public List<SessionDetails> getSessions() {
//        return adminService.getSessions();
//    }
//
//    @GetMapping("/clients")
//    public List<ClientDetails> getClients() {
//        return adminService.getClients();
//    }
//
//    @GetMapping("/inventory")
//    public StatisticsDetails getStatistics() {
//        return adminService.getStatistics();
//    }

    //
    @PostMapping("/checkin/{pin}")
    public AdminDetailsResponse checkinUser(String pin) {
        adminService.checkinUser(pin);
        return adminService.getDetails();
    }

    // Endpoint-uri pentru creare de abonamente
    @PostMapping("/memberships")
    public Membership createMemberShip(@RequestBody  MembershipRequest membershipRequest) {
        return adminService.createMembership(membershipRequest);
    }

    @DeleteMapping("/memberships/{id}")
    public String deleteMembership(@PathVariable Long id) {
        return adminService.deleteMembership(id);
    }

    // Endpoint-uri pentru inventar
    @GetMapping("/items")
    public List<Item> getItems() {
        return adminService.getItems();
    }

    @PostMapping("/items")
    public Item addItem(@RequestBody Item item) {
        return adminService.addItem(item.getTitle(), item.getQuantity());
    }

    @PostMapping("/items/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody Item item) {
        adminService.updateItem(id, item.getTitle(), item.getQuantity());
        return new ResponseEntity<>(new Response("Item updated successfully"), HttpStatus.OK);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        adminService.deleteItem(id);
        return new ResponseEntity<>(new Response("Item deleted successfully"), HttpStatus.OK);
    }

    // Endpoint-uri pentru antrenori
    @PostMapping("/coaches")
    public AdminDetailsResponse addCoach(@RequestBody Coach coach) {
        adminService.addCoach(coach);
        return adminService.getDetails();
    }

    @GetMapping("/coaches")
    public List<Coach> getCoaches() {
        return adminService.getCoaches();
    }

    // Endpoint-uri pentru clase
    @GetMapping("/classes")
    public List<ClassEntity> getClasses() {
        return adminService.getClasses();
    }

    @PostMapping("/classes")
    public ClassEntity addClass(@RequestBody ClassRequest classRequest) {
        return adminService.addClass(classRequest);
    }

    @DeleteMapping("/classes/{id}")
    public String deleteClass(@PathVariable Long id) {
        return adminService.deleteClass(id);
    }

    // Endpoint-uri pentru antrenamente
    @PostMapping("/sessions")
    public AdminDetailsResponse createSession(@RequestBody SessionRequest sessionRequest) {
        return adminService.addSession(sessionRequest);
    }

    @DeleteMapping("/sessions/{id}")
    public AdminDetailsResponse deleteSession(@PathVariable Long id) {
        return adminService.deleteSession(id);
    }
}
