package com.dodera.arni_fitness.controller;

import com.dodera.arni_fitness.dto.details.*;
import com.dodera.arni_fitness.dto.request.ClassRequest;
import com.dodera.arni_fitness.dto.request.MembershipRequest;
import com.dodera.arni_fitness.dto.request.PinRequest;
import com.dodera.arni_fitness.dto.request.SessionRequest;
import com.dodera.arni_fitness.dto.response.ClassPageResponse;
import com.dodera.arni_fitness.dto.response.Response;
import com.dodera.arni_fitness.model.*;
import com.dodera.arni_fitness.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Endpoint pentru a lua toate detaliile
    @GetMapping("/statistics")
    public StatisticsDetails getStatistics() {
        return adminService.getStatistics();
    }

    @GetMapping("/memberships")
    public List<MembershipDetails> getMembership() {
        return adminService.getMembershipsDetails();
    }

    @GetMapping("/classes")
    public List<ClassDetails> getClassesDetails() {
        return adminService.getClassesDetails();
    }

    @GetMapping("/classes/coaches")
    public ClassPageResponse getClassDetails() {
        return adminService.getClassPageDetails();
    }

    @GetMapping("/coaches")
    public List<CoachDetails> getCoachesDetails() {
        return adminService.getCoachesDetails();
    }

    @GetMapping("/sessions")
    public List<SessionDetails> getSessions() {
        return adminService.getSessionsDetails();
    }

    @GetMapping("/clients")
    public List<ClientDetails> getClients() {
        return adminService.getClientsDetails();
    }

    @GetMapping("/inventory")
    public List<Item> getInventory() {
        return adminService.getItems();
    }

    @PostMapping("/checkin")
    public List<ClientDetails> checkinUser(@RequestBody PinRequest pinRequest) {
        adminService.checkinUser(pinRequest.pin());
        return adminService.getClientsDetails();
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

    // Endpoint-uri pentru clase
    @PostMapping("/classes")
    public ClassEntity addClass(@RequestBody ClassRequest classRequest) {
        return adminService.addClass(classRequest);
    }

    @PostMapping("/classes/assign")
    public List<ClassDetails> assignCoachToClass(@RequestBody AssignCoachRequest assignCoachRequest) {
        return adminService.assignCoachToClass(assignCoachRequest.classId(), assignCoachRequest.coachId());
    }

    @DeleteMapping("/classes/{id}")
    public String deleteClass(@PathVariable Long id) {
        return adminService.deleteClass(id);
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

    @PostMapping("/items/{id}/increase")
    public Item increaseQuantity(@PathVariable Long id) {
        return adminService.increaseQuantity(id);
    }

    @PostMapping("/items/{id}/decrease")
    public List<Item> decreaseQuantity(@PathVariable Long id) {
        adminService.decreaseQuantity(id);
        return adminService.getItems();
    }

    // Endpoint-uri pentru antrenori
    @PostMapping("/coaches")
    public List<CoachDetails> addCoach(@RequestBody Coach coach) {
        adminService.addCoach(coach);
        return adminService.getCoachesDetails();
    }

    @DeleteMapping("/coaches/{id}")
    public List<CoachDetails> deleteCoach(@PathVariable Long id) {
        adminService.deleteCoach(id);
        return adminService.getCoachesDetails();
    }

    // Endpoint-uri pentru antrenamente
    @PostMapping("/sessions")
    public List<SessionDetails> createSession(@RequestBody SessionRequest sessionRequest) {
        return adminService.addSession(sessionRequest);
    }

    @DeleteMapping("/sessions/{id}")
    public List<SessionDetails> deleteSession(@PathVariable Long id) {
        return adminService.deleteSession(id);
    }
}
